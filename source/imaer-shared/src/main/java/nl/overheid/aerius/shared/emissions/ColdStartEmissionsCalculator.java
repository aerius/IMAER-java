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
import java.util.stream.Collectors;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.ColdStartEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardColdStartVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.Vehicles;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Calculation of cold start emission is as follows per vehicle cold starts (Ncs):
 *s
 * <pre>
 * E = (Ncs * ef * c) / 1000
 *
 * with following units:
 *
 * E: [kilogram / year]
 * Ncs: [cold-start / year]
 * ef: [gram / cold-start]
 * 1000: divide by 1000 to convert gram to kilogram
 * </pre>
 */
class ColdStartEmissionsCalculator extends BaseRoadEmissionsCalculator<ColdStartEmissionSource> {
  /**
   * Conversion from gram to kilogram.
   */
  private static final BigDecimal GRAM_TO_KG = BigDecimal.valueOf(1000L);

  private ColdStartEmissionFactorSupplier emissionFactorSupplier;

  public ColdStartEmissionsCalculator(final ColdStartEmissionFactorSupplier emissionFactorSupplier) {
    this.emissionFactorSupplier = emissionFactorSupplier;
  }

  public Map<Substance, Double> calculateEmissions(final ColdStartEmissionSource roadEmissionSource) throws AeriusException {
    return calculateEmissions(roadEmissionSource, BigDecimal.ONE);
  }

  @Override
  protected Double toTotalEmission(final ColdStartEmissionSource roadEmissionSource, final BigDecimal emission, final BigDecimal measure) {
    return emission.divide(GRAM_TO_KG).doubleValue();
  }

  @Override
  protected Map<Substance, Double> getRoadSpecificVehicleEmissionFactors(final String vehicleCode, final ColdStartEmissionSource roadEmissionSource) {
    return emissionFactorSupplier.getColdStartSpecificVehicleEmissionFactors(vehicleCode);
  }

  @Override
  public Map<Substance, BigDecimal> calculateColdStartEmissions(final StandardColdStartVehicles vehicles, final String standardVehicleCode,
      final Double coldStartsPerTimeUnit) {
    final BigDecimal numberOfColdStartsPerYear = getVehiclesPerTimeUnit(vehicles, coldStartsPerTimeUnit);

    return getStandardEmissionFactors(standardVehicleCode).entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().multiply(numberOfColdStartsPerYear)));
  }

  private Map<Substance, BigDecimal> getStandardEmissionFactors(final String standardVehicleCode) {
    return toBigDecimalMap(emissionFactorSupplier.getColdStartStandardVehicleEmissionFactors(standardVehicleCode));
  }

  private static Map<Substance, BigDecimal> toBigDecimalMap(final Map<Substance, Double> doubleMap) {
    final Map<Substance, BigDecimal> results = new EnumMap<>(Substance.class);

    doubleMap.forEach((key, value) -> results.put(key, BigDecimal.valueOf(value)));
    return results;
  }

  /**
   * Returns the total number of cold starts per year.
   */
  @Override
  protected BigDecimal getVehiclesPerTimeUnit(final Vehicles vehicles, final double vehiclesPerTimeUnit) {
    return BigDecimal.valueOf(vehicles.getTimeUnit().getPerYear(vehiclesPerTimeUnit));
  }
}
