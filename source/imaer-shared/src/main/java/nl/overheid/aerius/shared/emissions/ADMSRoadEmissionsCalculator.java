/*
 * Copyright the State of the Netherlands
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package nl.overheid.aerius.shared.emissions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.source.ADMSRoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.CustomVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.RoadStandardEmissionFactorsKey;
import nl.overheid.aerius.shared.domain.v2.source.road.RoadStandardsInterpolationValues;
import nl.overheid.aerius.shared.domain.v2.source.road.SpecificVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.TrafficDirection;
import nl.overheid.aerius.shared.domain.v2.source.road.ValuesPerVehicleType;
import nl.overheid.aerius.shared.domain.v2.source.road.Vehicles;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.shared.geometry.GeometryCalculator;

public class ADMSRoadEmissionsCalculator {

  /**
   * Conversion from gram/meter to kilogram/kilometer.
   */
  private static final BigDecimal GRAM_PER_KM_TO_KG_PER_METER = BigDecimal.valueOf(1000 * 1000);

  private static final BigDecimal HALF_FACTOR = BigDecimal.valueOf(0.5);

  private final RoadEmissionFactorSupplier emissionFactorSupplier;
  private final GeometryCalculator geometryCalculator;

  public ADMSRoadEmissionsCalculator(final RoadEmissionFactorSupplier emissionFactorSupplier, final GeometryCalculator geometryCalculator) {
    this.emissionFactorSupplier = emissionFactorSupplier;
    this.geometryCalculator = geometryCalculator;
  }

  public Map<Substance, Double> calculateEmissions(final ADMSRoadEmissionSource roadEmissionSource, final Geometry geometry) throws AeriusException {
    final BigDecimal measure = BigDecimal.valueOf(geometryCalculator.determineMeasure(geometry));
    final Map<Substance, BigDecimal> summed = new EnumMap<>(Substance.class);
    for (final Vehicles vehicles : roadEmissionSource.getSubSources()) {
      final Map<Substance, BigDecimal> emissionsForVehicles = calculateEmissions(roadEmissionSource, vehicles);
      emissionsForVehicles.forEach(
          (key, value) -> vehicles.getEmissions().put(key, toTotalEmission(value, measure)));
      emissionsForVehicles.forEach(
          (key, value) -> summed.merge(key, value, (v1, v2) -> v1.add(v2)));
    }
    final Map<Substance, Double> result = new EnumMap<>(Substance.class);
    summed.forEach(
        (key, value) -> result.put(key, toTotalEmission(value, measure)));
    return result;
  }

  private Map<Substance, BigDecimal> calculateEmissions(final ADMSRoadEmissionSource roadEmissionSource, final Vehicles vehicles)
      throws AeriusException {
    // Determine emissions per km
    if (vehicles instanceof CustomVehicles) {
      return calculateEmissions((CustomVehicles) vehicles);
    } else if (vehicles instanceof SpecificVehicles) {
      return calculateEmissions((SpecificVehicles) vehicles, roadEmissionSource.getRoadTypeCode());
    } else if (vehicles instanceof StandardVehicles) {
      return calculateEmissions((StandardVehicles) vehicles, roadEmissionSource);
    } else {
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR, "Unknown Vehicles type");
    }
  }

  Map<Substance, BigDecimal> calculateEmissions(final CustomVehicles customVehicles) {
    final Map<Substance, BigDecimal> results = new EnumMap<>(Substance.class);
    final BigDecimal numberOfVehiclesPerDay = getVehiclesPerDay(customVehicles, customVehicles.getVehiclesPerTimeUnit());
    customVehicles.getEmissionFactors().forEach(
        (key, value) -> results.put(key, BigDecimal.valueOf(value).multiply(numberOfVehiclesPerDay)));
    return results;
  }

  Map<Substance, BigDecimal> calculateEmissions(final SpecificVehicles specificVehicles, final String roadTypeCode) {
    final Map<Substance, BigDecimal> results = new EnumMap<>(Substance.class);
    final BigDecimal numberOfVehiclesPerDay = getVehiclesPerDay(specificVehicles, specificVehicles.getVehiclesPerTimeUnit());
    final Map<Substance, Double> emissionFactors = emissionFactorSupplier
        .getRoadSpecificVehicleEmissionFactors(specificVehicles.getVehicleCode(), roadTypeCode);
    emissionFactors.forEach(
        (key, value) -> results.put(key, BigDecimal.valueOf(value).multiply(numberOfVehiclesPerDay)));

    return results;
  }

  Map<Substance, BigDecimal> calculateEmissions(final StandardVehicles standardVehicles, final ADMSRoadEmissionSource roadEmissionSource) {
    final Map<Substance, BigDecimal> results = new EnumMap<>(Substance.class);
    for (final Entry<String, ValuesPerVehicleType> entry : standardVehicles.getValuesPerVehicleTypes().entrySet()) {
      final Map<Substance, BigDecimal> emissionsForVehicles =
          calculateEmissions(standardVehicles, entry.getKey(), entry.getValue(), roadEmissionSource);
      emissionsForVehicles.forEach(
          (key, value) -> results.merge(key, value, (v1, v2) -> v1.add(v2)));
    }

    return results;
  }

  Map<Substance, BigDecimal> calculateEmissions(final StandardVehicles standardVehicles, final String standardVehicleCode,
      final ValuesPerVehicleType valuesPerVehicleType, final ADMSRoadEmissionSource roadEmissionSource) {
    final Map<Substance, BigDecimal> results;
    final BigDecimal numberOfVehiclesPerDay = getVehiclesPerDay(standardVehicles, valuesPerVehicleType.getVehiclesPerTimeUnit());

    final TrafficDirection direction = roadEmissionSource.getTrafficDirection();
    // ADMSRoad does not use stagnation (this is incorporated into the speed used).
    // However, the gradient depends on the direction
    if (direction == TrafficDirection.A_TO_B) {
      // If A to B, all vehicles go that way, calculate appropriately
      results = calculateAtoBEmissions(standardVehicles, standardVehicleCode, numberOfVehiclesPerDay, roadEmissionSource);
    } else if (direction == TrafficDirection.B_TO_A) {
      // If B to A, all vehicles go that way, calculate appropriately (reverse gradient somewhere)
      results = calculateBtoAEmissions(standardVehicles, standardVehicleCode, numberOfVehiclesPerDay, roadEmissionSource);
    } else {
      final BigDecimal vehiclesAtoB = numberOfVehiclesPerDay.multiply(HALF_FACTOR);
      final BigDecimal vehiclesBtoA = numberOfVehiclesPerDay.multiply(HALF_FACTOR);
      results = new EnumMap<>(Substance.class);
      calculateAtoBEmissions(standardVehicles, standardVehicleCode, vehiclesAtoB, roadEmissionSource).forEach(
          (key, value) -> results.merge(key, value, (v1, v2) -> v1.add(v2)));
      calculateBtoAEmissions(standardVehicles, standardVehicleCode, vehiclesBtoA, roadEmissionSource).forEach(
          (key, value) -> results.merge(key, value, (v1, v2) -> v1.add(v2)));
    }

    return results;
  }

  private Map<Substance, BigDecimal> calculateAtoBEmissions(final StandardVehicles standardVehicles, final String standardVehicleCode,
      final BigDecimal numberOfVehiclesPerDay, final ADMSRoadEmissionSource roadEmissionSource) {
    final Map<Substance, BigDecimal> results = new EnumMap<>(Substance.class);
    final Map<Substance, BigDecimal> emissions = calculateEmissionsPerVehicle(standardVehicles, standardVehicleCode,
        roadEmissionSource.getRoadAreaCode(), roadEmissionSource.getRoadTypeCode(), roadEmissionSource.getGradient());
    emissions.forEach(
        (key, value) -> results.put(key, value.multiply(numberOfVehiclesPerDay)));
    return results;
  }

  private Map<Substance, BigDecimal> calculateBtoAEmissions(final StandardVehicles standardVehicles, final String standardVehicleCode,
      final BigDecimal numberOfVehiclesPerDay, final ADMSRoadEmissionSource roadEmissionSource) {
    final Map<Substance, BigDecimal> results = new EnumMap<>(Substance.class);
    // B to A, so reverse gradient
    final Map<Substance, BigDecimal> emissions = calculateEmissionsPerVehicle(standardVehicles, standardVehicleCode,
        roadEmissionSource.getRoadAreaCode(), roadEmissionSource.getRoadTypeCode(), -roadEmissionSource.getGradient());
    emissions.forEach(
        (key, value) -> results.put(key, value.multiply(numberOfVehiclesPerDay)));
    return results;
  }

  private Map<Substance, BigDecimal> calculateEmissionsPerVehicle(final StandardVehicles standardVehicles,
      final String standardVehicleCode, final String roadAreaCode, final String roadTypeCode, final double gradient) {
    final RoadStandardEmissionFactorsKey targetKey = new RoadStandardEmissionFactorsKey(roadAreaCode, roadTypeCode, standardVehicleCode,
        standardVehicles.getMaximumSpeed(), standardVehicles.getStrictEnforcement(), gradient);
    final RoadStandardsInterpolationValues interpolationValues = emissionFactorSupplier.getRoadStandardVehicleInterpolationValues(targetKey);
    return interpolate(targetKey, interpolationValues);
  }

  private BigDecimal getVehiclesPerDay(final Vehicles vehicles, final double vehiclesPerTimeUnit) {
    return vehicles.getTimeUnit().toUnit(BigDecimal.valueOf(vehiclesPerTimeUnit), TimeUnit.DAY);
  }

  private double toTotalEmission(final BigDecimal emissionPerMeter, final BigDecimal measure) {
    return emissionPerMeter.multiply(measure).divide(GRAM_PER_KM_TO_KG_PER_METER, 5, RoundingMode.HALF_UP).doubleValue();
  }

  private Map<Substance, BigDecimal> interpolate(final RoadStandardEmissionFactorsKey targetKey,
      final RoadStandardsInterpolationValues interpolationValues) {
    final Map<Substance, BigDecimal> speedFloorGradientFloor =
        getEmissionFactors(targetKey, interpolationValues.getSpeedFloor(), interpolationValues.getGradientFloor());
    if (interpolationValues.speedMatches() && interpolationValues.gradientMatches()) {
      return speedFloorGradientFloor;
    }
    final Map<Substance, BigDecimal> speedCeilingGradientFloor = interpolationValues.speedMatches()
        ? speedFloorGradientFloor
        : getEmissionFactors(targetKey, interpolationValues.getSpeedCeiling(), interpolationValues.getGradientFloor());
    final Map<Substance, BigDecimal> speedFloorGradientCeiling = interpolationValues.gradientMatches()
        ? speedFloorGradientFloor
        : getEmissionFactors(targetKey, interpolationValues.getSpeedFloor(), interpolationValues.getGradientCeiling());
    final Map<Substance, BigDecimal> speedCeilingGradientCeiling = interpolationValues.gradientMatches()
        ? speedCeilingGradientFloor
        : getEmissionFactors(targetKey, interpolationValues.getSpeedCeiling(), interpolationValues.getGradientCeiling());

    final Map<Substance, BigDecimal> results = new EnumMap<>(Substance.class);
    // Assume same substances are available for all sets
    for (final Substance substance : speedFloorGradientFloor.keySet()) {
      final BigDecimal targetSpeed = Optional.ofNullable(targetKey.getMaximumSpeed())
          .map(BigDecimal::valueOf)
          .orElse(BigDecimal.ZERO);
      final BigDecimal targetGradient = Optional.ofNullable(targetKey.getGradient())
          .map(BigDecimal::valueOf)
          .orElse(BigDecimal.ZERO);
      final BigDecimal speedInterpolatedGradientFloor = interpolate(
          interpolationValues.getSpeedFloorBD(), interpolationValues.getSpeedCeilingBD(), speedFloorGradientFloor.get(substance),
          speedCeilingGradientFloor.get(substance), targetSpeed);
      final BigDecimal speedInterpolatedGradientCeiling = interpolate(
          interpolationValues.getSpeedFloorBD(), interpolationValues.getSpeedCeilingBD(), speedFloorGradientCeiling.get(substance),
          speedCeilingGradientCeiling.get(substance), targetSpeed);
      results.put(substance, interpolate(interpolationValues.getGradientFloorBD(), interpolationValues.getGradientCeilingDB(),
          speedInterpolatedGradientFloor, speedInterpolatedGradientCeiling, targetGradient));
    }
    return results;
  }

  private Map<Substance, BigDecimal> getEmissionFactors(final RoadStandardEmissionFactorsKey targetKey, final int speed, final double floor) {
    return toBigDecimalMap(emissionFactorSupplier.getRoadStandardVehicleEmissionFactors(targetKey.copyWith(speed, floor)));
  }

  private static Map<Substance, BigDecimal> toBigDecimalMap(final Map<Substance, Double> doubleMap) {
    final Map<Substance, BigDecimal> results = new EnumMap<>(Substance.class);
    doubleMap.forEach(
        (key, value) -> results.put(key, BigDecimal.valueOf(value)));
    return results;
  }

  /**
   * Linear interpolate:
   * y = y1 + ((targetX - x1) / (x2 - x1)) * (y2 - y1)
   * or
   * y = y1 + part1 * part2
   * @param x1 X lower bound
   * @param x2 X upper bound
   * @param y1 Y value belonging to X lower bound
   * @param y2 Y value belonging to X upper bound
   * @param targetX The X to get the interpolation value for.
   * @return The interpolated Y value.
   */
  private static BigDecimal interpolate(final BigDecimal x1, final BigDecimal x2, final BigDecimal y1, final BigDecimal y2,
      final BigDecimal targetX) {
    if (x1.equals(x2)) {
      return y1;
    } else {
      final BigDecimal part1 = targetX.subtract(x1).divide(x2.subtract(x1), 5, RoundingMode.HALF_UP);
      final BigDecimal part2 = y2.subtract(y1);
      return y1.add(part1.multiply(part2));
    }
  }

}
