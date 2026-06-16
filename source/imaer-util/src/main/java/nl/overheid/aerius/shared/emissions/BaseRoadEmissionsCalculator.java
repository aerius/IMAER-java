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
import java.util.Map;
import java.util.Map.Entry;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceWithSubSources;
import nl.overheid.aerius.shared.domain.v2.source.road.CustomVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.SpecificVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardColdStartVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.ValuesPerVehicleType;
import nl.overheid.aerius.shared.domain.v2.source.road.Vehicles;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Abstract base class for Road and Coldstart emissions calculations.
 *
 * @param <R>
 */
abstract class BaseRoadEmissionsCalculator<R extends EmissionSourceWithSubSources<Vehicles>> {

  /**
   * Calculate the total emission of the source
   *
   * @param roadEmissionSource
   * @param measure
   * @return
   * @throws AeriusException
   */
  protected Map<Substance, Double> calculateEmissions(final R roadEmissionSource, final BigDecimal measure) throws AeriusException {
    final Map<Substance, BigDecimal> summed = new EnumMap<>(Substance.class);

    for (final Vehicles vehicles : roadEmissionSource.getSubSources()) {
      final Map<Substance, BigDecimal> emissionsForVehicles = calculateEmissions(roadEmissionSource, vehicles);

      emissionsForVehicles.forEach((key, value) -> vehicles.getEmissions().put(key, toTotalEmission(roadEmissionSource, value, measure)));
      emissionsForVehicles.forEach((key, value) -> summed.merge(key, value, BigDecimal::add));
    }
    final Map<Substance, Double> result = new EnumMap<>(Substance.class);

    summed.forEach((key, value) -> result.put(key, toTotalEmission(roadEmissionSource, value, measure)));
    return result;
  }

  protected abstract Double toTotalEmission(R roadEmissionSource, BigDecimal value, BigDecimal measure);

  public Map<Substance, BigDecimal> calculateEmissions(final R roadEmissionSource, final Vehicles vehicles)
      throws AeriusException {
    // Determine emissions per km
    if (vehicles instanceof CustomVehicles) {
      return calculateEmissions((CustomVehicles) vehicles);
    } else if (vehicles instanceof SpecificVehicles) {
      return calculateEmissions((SpecificVehicles) vehicles, roadEmissionSource);
    } else if (vehicles instanceof StandardVehicles) {
      return calculateEmissions((StandardVehicles) vehicles, roadEmissionSource);
    } else if (vehicles instanceof StandardColdStartVehicles) {
      return calculateEmissions((StandardColdStartVehicles) vehicles);
    } else {
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR, "Unknown Vehicles type");
    }
  }

  protected Map<Substance, BigDecimal> calculateEmissions(final CustomVehicles customVehicles) {
    final Map<Substance, BigDecimal> results = new EnumMap<>(Substance.class);
    final BigDecimal numberOfVehiclesPerUnit = getVehiclesPerTimeUnit(customVehicles, customVehicles.getVehiclesPerTimeUnit());
    customVehicles.getEmissionFactors().forEach(
        (key, value) -> results.put(key, BigDecimal.valueOf(value).multiply(numberOfVehiclesPerUnit)));
    return results;
  }

  protected Map<Substance, BigDecimal> calculateEmissions(final SpecificVehicles specificVehicles, final R roadEmissionSource) {
    final Map<Substance, BigDecimal> results = new EnumMap<>(Substance.class);
    final BigDecimal numberOfVehiclesPerUnit = getVehiclesPerTimeUnit(specificVehicles, specificVehicles.getVehiclesPerTimeUnit());
    final Map<Substance, Double> emissionFactors = getRoadSpecificVehicleEmissionFactors(specificVehicles.getVehicleCode(), roadEmissionSource);
    emissionFactors.forEach(
        (key, value) -> results.put(key, BigDecimal.valueOf(value).multiply(numberOfVehiclesPerUnit)));

    return results;
  }

  protected abstract Map<Substance, Double> getRoadSpecificVehicleEmissionFactors(String vehicleCode, R roadEmissionSource);

  Map<Substance, BigDecimal> calculateEmissions(final StandardVehicles standardVehicles, final R roadEmissionSource) {
    final Map<Substance, BigDecimal> results = new EnumMap<>(Substance.class);

    for (final Entry<String, ValuesPerVehicleType> entry : standardVehicles.getValuesPerVehicleTypes().entrySet()) {
      final Map<Substance, BigDecimal> emissionsForVehicles = calculateEmissions(standardVehicles, entry.getKey(), entry.getValue(),
          roadEmissionSource);

      emissionsForVehicles.forEach((key, value) -> results.merge(key, value, (v1, v2) -> v1.add(v2)));
    }

    return results;
  }

  /**
   * Calculate the emissions of {@link StandardVehicles}.
   *
   * @param standardVehicles
   * @param standardVehicleCode
   * @param valuesPerVehicleType
   * @param roadEmissionSource
   * @return
   */
  protected Map<Substance, BigDecimal> calculateEmissions(final StandardVehicles standardVehicles, final String standardVehicleCode,
      final ValuesPerVehicleType valuesPerVehicleType, final R roadEmissionSource) {
    throw new IllegalArgumentException("Calculation of StandardVehicles not supported");
  }

  /**
   * Calculate the emissions of {@link StandardColdStartVehicles}.
   *
   * @param standardVehicles
   * @return
   */
  Map<Substance, BigDecimal> calculateEmissions(final StandardColdStartVehicles vehicles) {
    final Map<Substance, BigDecimal> results = new EnumMap<>(Substance.class);

    for (final Entry<String, Double> entry : vehicles.getValuesPerVehicleTypes().entrySet()) {
      final Map<Substance, BigDecimal> emissionsForVehicles = calculateColdStartEmissions(vehicles, entry.getKey(), entry.getValue());

      emissionsForVehicles.forEach((key, value) -> results.merge(key, value, (v1, v2) -> v1.add(v2)));
    }

    return results;
  }

  protected Map<Substance, BigDecimal> calculateColdStartEmissions(final StandardColdStartVehicles vehicles, final String standardVehicleCode,
      final Double value) {
    throw new IllegalArgumentException("Calculation of StandardVehicles not supported");
  }

  /**
   * Returns the number of vehicles per time unit of the emission factor.
   *
   * @param vehicles
   * @param vehiclesPerTimeUnit
   * @return
   */
  protected abstract BigDecimal getVehiclesPerTimeUnit(final Vehicles vehicles, final double vehiclesPerTimeUnit);
}
