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
import java.util.Optional;
import java.util.OptionalDouble;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.OffRoadMobileEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.offroad.CustomOffRoadMobileSource;
import nl.overheid.aerius.shared.domain.v2.source.offroad.OffRoadMobileSource;
import nl.overheid.aerius.shared.domain.v2.source.offroad.StandardOffRoadMobileSource;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
public class OffRoadMobileEmissionsCalculator {

  private final OffRoadMobileEmissionFactorSupplier emissionFactorSupplier;

  public OffRoadMobileEmissionsCalculator(final OffRoadMobileEmissionFactorSupplier emissionFactorSupplier) {
    this.emissionFactorSupplier = emissionFactorSupplier;
  }

  public Map<Substance, Double> calculateEmissions(final OffRoadMobileEmissionSource offRoadMobileEmissionSource) throws AeriusException {
    final Map<Substance, BigDecimal> summed = new EnumMap<>(Substance.class);
    for (final OffRoadMobileSource mobileSource : offRoadMobileEmissionSource.getSubSources()) {
      final Map<Substance, BigDecimal> emissionsForMobileSource = calculateEmissions(mobileSource);
      emissionsForMobileSource.forEach(
          (key, value) -> mobileSource.getEmissions().put(key, value.doubleValue()));
      emissionsForMobileSource.forEach(
          (key, value) -> summed.merge(key, value, (v1, v2) -> v1.add(v2)));
    }
    final Map<Substance, Double> result = new EnumMap<>(Substance.class);
    summed.forEach(
        (key, value) -> result.put(key, value.doubleValue()));
    return result;
  }

  private Map<Substance, BigDecimal> calculateEmissions(final OffRoadMobileSource mobileSource) throws AeriusException {
    if (mobileSource instanceof CustomOffRoadMobileSource) {
      return calculateEmissions((CustomOffRoadMobileSource) mobileSource);
    } else if (mobileSource instanceof StandardOffRoadMobileSource) {
      return calculateEmissions((StandardOffRoadMobileSource) mobileSource);
    } else {
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR, "Unknown OffRoadMobileSource type");
    }
  }

  Map<Substance, BigDecimal> calculateEmissions(final CustomOffRoadMobileSource customSource) {
    final Map<Substance, BigDecimal> results = new EnumMap<>(Substance.class);
    customSource.getEmissions().forEach(
        (key, value) -> results.put(key, BigDecimal.valueOf(value)));
    return results;
  }

  Map<Substance, BigDecimal> calculateEmissions(final StandardOffRoadMobileSource standardSource) {
    final String mobileSourceCode = standardSource.getOffRoadMobileSourceCode();

    final Optional<BigDecimal> literFuelPerYear = Optional.ofNullable(standardSource.getLiterFuelPerYear())
        .map(BigDecimal::valueOf);
    final Map<Substance, BigDecimal> emissions = determineFuelEmissions(mobileSourceCode, literFuelPerYear);

    final Map<Substance, BigDecimal> operatingEmissions = determineOperatingEmissions(mobileSourceCode, standardSource.getOperatingHoursPerYear());
    operatingEmissions.forEach(
        (key, value) -> emissions.merge(key, value, (v1, v2) -> v1.add(v2)));

    final Map<Substance, BigDecimal> adBlueEmissions =
        determineAdBlueEmissions(mobileSourceCode, literFuelPerYear, standardSource.getLiterAdBluePerYear());
    adBlueEmissions.forEach(
        (key, value) -> emissions.merge(key, value, (v1, v2) -> v1.add(v2)));

    // AdBlue factors are (/can be) negative. To ensure we don't end up with negative emissions, ensure all negative values are set to 0.
    // This should have been checked beforehand, so more of a precaution.
    for (final Substance substance : Substance.values()) {
      emissions.computeIfPresent(substance, (subst, value) -> value.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : value);
    }

    return emissions;
  }

  private Map<Substance, BigDecimal> determineFuelEmissions(final String mobileSourceCode, final Optional<BigDecimal> fuelLiterPerYear) {
    final Map<Substance, BigDecimal> emissions = new EnumMap<>(Substance.class);
    if (fuelLiterPerYear.isPresent()) {
      final BigDecimal fuelLiterPerYearBD = fuelLiterPerYear.get();
      final Map<Substance, Double> emissionFactorsPerLiterFuel = emissionFactorSupplier
          .getOffRoadMobileEmissionFactorsPerLiterFuel(mobileSourceCode);
      emissionFactorsPerLiterFuel.forEach(
          (key, value) -> emissions.put(key, BigDecimal.valueOf(value).multiply(fuelLiterPerYearBD)));
    }
    return emissions;
  }

  private Map<Substance, BigDecimal> determineOperatingEmissions(final String mobileSourceCode, final Integer operatingHoursPerYear) {
    final Map<Substance, BigDecimal> emissions = new EnumMap<>(Substance.class);
    if (operatingHoursPerYear != null) {
      final BigDecimal operatingHoursPerYearBD = BigDecimal.valueOf(operatingHoursPerYear);
      final Map<Substance, Double> emissionFactorsPerOperatingHour = emissionFactorSupplier
          .getOffRoadMobileEmissionFactorsPerOperatingHour(mobileSourceCode);
      emissionFactorsPerOperatingHour.forEach(
          (key, value) -> emissions.put(key, BigDecimal.valueOf(value).multiply(operatingHoursPerYearBD)));
    }
    return emissions;
  }

  private Map<Substance, BigDecimal> determineAdBlueEmissions(final String mobileSourceCode, final Optional<BigDecimal> fuelLiterPerYear,
      final Integer adBlueLiterPerYear) {
    final Map<Substance, BigDecimal> emissions = new EnumMap<>(Substance.class);
    if (adBlueLiterPerYear != null && fuelLiterPerYear.isPresent()) {
      final OptionalDouble maxAdBlueRatio = emissionFactorSupplier.getMaxAdBlueFuelRatio(mobileSourceCode);
      final BigDecimal maxAdBlue =
          fuelLiterPerYear.get().multiply(maxAdBlueRatio.isPresent() ? BigDecimal.valueOf(maxAdBlueRatio.getAsDouble()) : BigDecimal.ZERO);

      final BigDecimal adBlueLiterPerYearDB = BigDecimal.valueOf(adBlueLiterPerYear).min(maxAdBlue);
      final Map<Substance, Double> emissionFactorsPerLiterAdBlue = emissionFactorSupplier
          .getOffRoadMobileEmissionFactorsPerLiterAdBlue(mobileSourceCode);
      emissionFactorsPerLiterAdBlue.forEach(
          (key, value) -> emissions.put(key, BigDecimal.valueOf(value).multiply(adBlueLiterPerYearDB)));
    }
    return emissions;
  }

}
