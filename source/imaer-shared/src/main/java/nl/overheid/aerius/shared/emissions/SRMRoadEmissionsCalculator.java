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
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.OptionalDouble;

import nl.overheid.aerius.shared.domain.Substance;
import nl.aerius.shared.domain.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.source.RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.SRM1RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.SRM2RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.CustomVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.RoadStandardEmissionFactorsKey;
import nl.overheid.aerius.shared.domain.v2.source.road.SpecificVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardVehicleMeasure;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.ValuesPerVehicleType;
import nl.overheid.aerius.shared.domain.v2.source.road.Vehicles;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.shared.geometry.GeometryCalculator;

public class SRMRoadEmissionsCalculator {

  /**
   * Conversion from gram/meter to kilogram/kilometer.
   */
  private static final BigDecimal GRAM_PER_KM_TO_KG_PER_METER = BigDecimal.valueOf(1000L * 1000L);

  private final RoadEmissionFactorSupplier emissionFactorSupplier;
  private final GeometryCalculator geometryCalculator;

  public SRMRoadEmissionsCalculator(final RoadEmissionFactorSupplier emissionFactorSupplier, final GeometryCalculator geometryCalculator) {
    this.emissionFactorSupplier = emissionFactorSupplier;
    this.geometryCalculator = geometryCalculator;
  }

  public Map<Substance, Double> calculateEmissions(final SRM1RoadEmissionSource roadEmissionSource, final Geometry geometry) throws AeriusException {
    final BigDecimal measure = BigDecimal.valueOf(geometryCalculator.determineMeasure(geometry));
    final BigDecimal tunnelFactor = BigDecimal.valueOf(roadEmissionSource.getTunnelFactor());
    final Map<Substance, BigDecimal> summed = new EnumMap<>(Substance.class);
    for (final Vehicles vehicles : roadEmissionSource.getSubSources()) {
      final Map<Substance, BigDecimal> emissionsForVehicles = calculateEmissions(roadEmissionSource, vehicles);
      emissionsForVehicles.forEach(
          (key, value) -> vehicles.getEmissions().put(key, toTotalEmission(value, measure, tunnelFactor)));
      emissionsForVehicles.forEach(
          (key, value) -> summed.merge(key, value, (v1, v2) -> v1.add(v2)));
    }
    final Map<Substance, Double> result = new EnumMap<>(Substance.class);
    summed.forEach(
        (key, value) -> result.put(key, toTotalEmission(value, measure, tunnelFactor)));
    return result;
  }

  public Map<Substance, Double> calculateEmissions(final SRM2RoadEmissionSource roadEmissionSource, final Geometry geometry) throws AeriusException {
    final BigDecimal measure = BigDecimal.valueOf(geometryCalculator.determineMeasure(geometry));
    final BigDecimal tunnelFactor = BigDecimal.valueOf(roadEmissionSource.getTunnelFactor());
    final Map<Substance, BigDecimal> summed = new EnumMap<>(Substance.class);
    for (final Vehicles vehicles : roadEmissionSource.getSubSources()) {
      final Map<Substance, BigDecimal> emissionsForVehicles = calculateEmissions(roadEmissionSource, vehicles);
      emissionsForVehicles.forEach(
          (key, value) -> vehicles.getEmissions().put(key, toTotalEmission(value, measure, tunnelFactor)));
      emissionsForVehicles.forEach(
          (key, value) -> summed.merge(key, value, (v1, v2) -> v1.add(v2)));
    }
    final Map<Substance, Double> result = new EnumMap<>(Substance.class);
    summed.forEach(
        (key, value) -> result.put(key, toTotalEmission(value, measure, tunnelFactor)));
    return result;
  }

  private Map<Substance, BigDecimal> calculateEmissions(final RoadEmissionSource roadEmissionSource, final Vehicles vehicles)
      throws AeriusException {
    // Determine emissions per km
    if (vehicles instanceof CustomVehicles) {
      return calculateEmissions((CustomVehicles) vehicles);
    } else if (vehicles instanceof SpecificVehicles) {
      return calculateEmissions((SpecificVehicles) vehicles, roadEmissionSource.getRoadTypeCode());
    } else if (vehicles instanceof StandardVehicles) {
      return calculateEmissions((StandardVehicles) vehicles, roadEmissionSource.getRoadAreaCode(), roadEmissionSource.getRoadTypeCode());
    } else {
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR, "Unknown Vehicles type");
    }
  }

  Map<Substance, BigDecimal> calculateEmissions(final CustomVehicles customVehicles) {
    final Map<Substance, BigDecimal> results = new EnumMap<>(Substance.class);
    final BigDecimal numberOfVehiclesPerYear = getVehiclesPerYear(customVehicles, customVehicles.getVehiclesPerTimeUnit());
    customVehicles.getEmissionFactors().forEach(
        (key, value) -> results.put(key, BigDecimal.valueOf(value).multiply(numberOfVehiclesPerYear)));
    return results;
  }

  Map<Substance, BigDecimal> calculateEmissions(final SpecificVehicles specificVehicles, final String roadTypeCode) {
    final Map<Substance, BigDecimal> results = new EnumMap<>(Substance.class);
    final BigDecimal numberOfVehiclesPerYear = getVehiclesPerYear(specificVehicles, specificVehicles.getVehiclesPerTimeUnit());
    final Map<Substance, Double> emissionFactors = emissionFactorSupplier
        .getRoadSpecificVehicleEmissionFactors(specificVehicles.getVehicleCode(), roadTypeCode);
    emissionFactors.forEach(
        (key, value) -> results.put(key, BigDecimal.valueOf(value).multiply(numberOfVehiclesPerYear)));

    return results;
  }

  Map<Substance, BigDecimal> calculateEmissions(final StandardVehicles standardVehicles, final String roadAreaCode, final String roadTypeCode) {
    final Map<Substance, BigDecimal> results = new EnumMap<>(Substance.class);
    for (final Entry<String, ValuesPerVehicleType> entry : standardVehicles.getValuesPerVehicleTypes().entrySet()) {
      final Map<Substance, BigDecimal> emissionsForVehicles =
          calculateEmissions(standardVehicles, entry.getKey(), entry.getValue(), roadAreaCode, roadTypeCode);
      emissionsForVehicles.forEach(
          (key, value) -> results.merge(key, value, (v1, v2) -> v1.add(v2)));
    }

    return results;
  }

  Map<Substance, BigDecimal> calculateEmissions(final StandardVehicles standardVehicles, final String standardVehicleCode,
      final ValuesPerVehicleType valuesPerVehicleType, final String roadAreaCode, final String roadTypeCode) {
    final Map<Substance, BigDecimal> results = new EnumMap<>(Substance.class);
    final BigDecimal numberOfVehiclesPerYear = getVehiclesPerYear(standardVehicles, valuesPerVehicleType.getVehiclesPerTimeUnit());

    final Map<Substance, BigDecimal> emissions = calculateNonStagnatedEmissionsPerVehicle(standardVehicles, standardVehicleCode, valuesPerVehicleType,
        roadAreaCode, roadTypeCode);
    final Map<Substance, BigDecimal> stagnatedEmissions = calculateStagnatedEmissionsPerVehicle(standardVehicles, standardVehicleCode,
        valuesPerVehicleType, roadAreaCode, roadTypeCode);
    stagnatedEmissions.forEach(
        (key, value) -> emissions.merge(key, value, (v1, v2) -> v1.add(v2)));
    final List<StandardVehicleMeasure> measures = standardVehicles.getMeasures();
    if (measures != null && !measures.isEmpty()) {
      emissions.forEach((substance, value) -> {
        final BigDecimal measureFactor = determineMeasureFactor(measures, standardVehicleCode, roadTypeCode, substance);
        emissions.put(substance, value.multiply(measureFactor));
      });
    }
    emissions.forEach(
        (key, value) -> results.put(key, value.multiply(numberOfVehiclesPerYear)));

    return results;
  }

  private BigDecimal determineMeasureFactor(final List<StandardVehicleMeasure> measures, final String standardVehicleCode,
      final String roadTypeCode, final Substance substance) {
    return BigDecimal.valueOf(measures.stream()
        .map(measure -> measure.determineFactor(standardVehicleCode, roadTypeCode, substance))
        .filter(OptionalDouble::isPresent)
        .mapToDouble(OptionalDouble::getAsDouble)
        .max().orElse(1));
  }

  private Map<Substance, BigDecimal> calculateNonStagnatedEmissionsPerVehicle(final StandardVehicles standardVehicles,
      final String standardVehicleCode, final ValuesPerVehicleType valuesPerVehicleType, final String roadAreaCode, final String roadTypeCode) {
    final Map<Substance, BigDecimal> results = new EnumMap<>(Substance.class);
    final BigDecimal nonStagnatedFraction = BigDecimal.ONE.subtract(BigDecimal.valueOf(valuesPerVehicleType.getStagnationFraction()));
    final Map<Substance, Double> emissionFactorsNotStagnated = emissionFactorSupplier.getRoadStandardVehicleEmissionFactors(
        new RoadStandardEmissionFactorsKey(roadAreaCode, roadTypeCode, standardVehicleCode,
            standardVehicles.getMaximumSpeed(), standardVehicles.getStrictEnforcement(), null));
    emissionFactorsNotStagnated.forEach(
        (key, value) -> results.put(key, BigDecimal.valueOf(value).multiply(nonStagnatedFraction)));
    return results;
  }

  private Map<Substance, BigDecimal> calculateStagnatedEmissionsPerVehicle(final StandardVehicles standardVehicles, final String standardVehicleCode,
      final ValuesPerVehicleType valuesPerVehicleType, final String roadAreaCode, final String roadTypeCode) {
    final Map<Substance, BigDecimal> results = new EnumMap<>(Substance.class);
    final BigDecimal stagnatedFraction = BigDecimal.valueOf(valuesPerVehicleType.getStagnationFraction());
    final Map<Substance, Double> emissionFactorsStagnated = emissionFactorSupplier.getRoadStandardVehicleStagnatedEmissionFactors(
        new RoadStandardEmissionFactorsKey(roadAreaCode, roadTypeCode, standardVehicleCode,
            standardVehicles.getMaximumSpeed(), standardVehicles.getStrictEnforcement(), null));
    emissionFactorsStagnated.forEach(
        (key, value) -> results.put(key, BigDecimal.valueOf(value).multiply(stagnatedFraction)));
    return results;
  }

  private BigDecimal getVehiclesPerYear(final Vehicles vehicles, final double vehiclesPerTimeUnit) {
    return BigDecimal.valueOf(vehicles.getTimeUnit().getPerYear(vehiclesPerTimeUnit));
  }

  private double toTotalEmission(final BigDecimal emissionPerMeter, final BigDecimal measure, final BigDecimal tunnelFactor) {
    return emissionPerMeter.multiply(tunnelFactor).multiply(measure).divide(GRAM_PER_KM_TO_KG_PER_METER).doubleValue();
  }

}
