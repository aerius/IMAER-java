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

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Utility to help with calculating emissions for farm source based objects.
 */
final class FarmSourceEmissionsUtil {

  private FarmSourceEmissionsUtil() {
    // Util class
  }

  static Map<Substance, BigDecimal> calculate(final IsFarmEmissionFactorTypeObject object, final Map<Substance, Double> emissionFactors,
      final FarmEmissionFactorType emissionFactorType) throws AeriusException {
    final Map<Substance, BigDecimal> objectEmissions;

    switch (emissionFactorType) {
    case PER_YEAR:
      objectEmissions = calculatePerYear(object, emissionFactors);
      break;
    case PER_ANIMAL_PER_DAY:
      objectEmissions = calculatePerAnimalPerDay(object, emissionFactors);
      break;
    case PER_ANIMAL_PER_YEAR:
      objectEmissions = calculatePerAnimalPerYear(object, emissionFactors);
      break;
    case PER_METERS_CUBED_PER_APPLICATION:
      objectEmissions = calculatePerMetersCubedPerApplication(object, emissionFactors);
      break;
    case PER_TONNES_PER_APPLICATION:
      objectEmissions = calculatePerTonnesPerApplication(object, emissionFactors);
      break;
    case PER_TONNES_PER_YEAR:
      objectEmissions = calculatePerTonnesPerYear(object, emissionFactors);
      break;
    case PER_METERS_SQUARED_PER_YEAR:
      objectEmissions = calculatePerMetersSquaredPerYear(object, emissionFactors);
      break;
    case PER_METERS_SQUARED_PER_DAY:
      objectEmissions = calculatePerMetersSquaredPerDay(object, emissionFactors);
      break;
    default:
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR, "Invalid emission factor type: " + emissionFactorType);
    }

    return objectEmissions;
  }

  private static Map<Substance, BigDecimal> calculatePerYear(final IsFarmEmissionFactorTypeObject object,
      final Map<Substance, Double> emissionFactors) {
    final Map<Substance, BigDecimal> objectEmissions = new EnumMap<>(Substance.class);
    emissionFactors.forEach((key, value) -> objectEmissions.put(key,
        BigDecimal.valueOf(value)));
    return objectEmissions;
  }

  private static Map<Substance, BigDecimal> calculatePerAnimalPerDay(final IsFarmEmissionFactorTypeObject object,
      final Map<Substance, Double> emissionFactors) {
    final Map<Substance, BigDecimal> objectEmissions = new EnumMap<>(Substance.class);
    emissionFactors.forEach((key, value) -> objectEmissions.put(key,
        BigDecimal.valueOf(value)
            .multiply(BigDecimal.valueOf(object.getNumberOfDays()))
            .multiply(BigDecimal.valueOf(object.getNumberOfAnimals()))));
    return objectEmissions;
  }

  private static Map<Substance, BigDecimal> calculatePerAnimalPerYear(final IsFarmEmissionFactorTypeObject object,
      final Map<Substance, Double> emissionFactors) {
    final Map<Substance, BigDecimal> objectEmissions = new EnumMap<>(Substance.class);
    emissionFactors.forEach((key, value) -> objectEmissions.put(key,
        BigDecimal.valueOf(value)
            .multiply(BigDecimal.valueOf(object.getNumberOfAnimals()))));
    return objectEmissions;
  }

  private static Map<Substance, BigDecimal> calculatePerMetersCubedPerApplication(final IsFarmEmissionFactorTypeObject object,
      final Map<Substance, Double> emissionFactors) {
    final Map<Substance, BigDecimal> objectEmissions = new EnumMap<>(Substance.class);
    emissionFactors.forEach((key, value) -> objectEmissions.put(key,
        BigDecimal.valueOf(value)
            .multiply(BigDecimal.valueOf(object.getMetersCubed()))
            .multiply(BigDecimal.valueOf(object.getNumberOfApplications()))));
    return objectEmissions;
  }

  private static Map<Substance, BigDecimal> calculatePerTonnesPerApplication(final IsFarmEmissionFactorTypeObject object,
      final Map<Substance, Double> emissionFactors) {
    final Map<Substance, BigDecimal> objectEmissions = new EnumMap<>(Substance.class);
    emissionFactors.forEach((key, value) -> objectEmissions.put(key,
        BigDecimal.valueOf(value)
            .multiply(BigDecimal.valueOf(object.getTonnes()))
            .multiply(BigDecimal.valueOf(object.getNumberOfApplications()))));
    return objectEmissions;
  }

  private static Map<Substance, BigDecimal> calculatePerTonnesPerYear(final IsFarmEmissionFactorTypeObject object,
      final Map<Substance, Double> emissionFactors) {
    final Map<Substance, BigDecimal> objectEmissions = new EnumMap<>(Substance.class);
    emissionFactors.forEach((key, value) -> objectEmissions.put(key,
        BigDecimal.valueOf(value)
            .multiply(BigDecimal.valueOf(object.getTonnes()))));
    return objectEmissions;
  }

  private static Map<Substance, BigDecimal> calculatePerMetersSquaredPerYear(final IsFarmEmissionFactorTypeObject object,
      final Map<Substance, Double> emissionFactors) {
    final Map<Substance, BigDecimal> objectEmissions = new EnumMap<>(Substance.class);
    emissionFactors.forEach((key, value) -> objectEmissions.put(key,
        BigDecimal.valueOf(value)
            .multiply(BigDecimal.valueOf(object.getMetersSquared()))));
    return objectEmissions;
  }

  private static Map<Substance, BigDecimal> calculatePerMetersSquaredPerDay(final IsFarmEmissionFactorTypeObject object,
      final Map<Substance, Double> emissionFactors) {
    final Map<Substance, BigDecimal> objectEmissions = new EnumMap<>(Substance.class);
    emissionFactors.forEach((key, value) -> objectEmissions.put(key,
        BigDecimal.valueOf(value)
            .multiply(BigDecimal.valueOf(object.getMetersSquared()))
            .multiply(BigDecimal.valueOf(object.getNumberOfDays()))));
    return objectEmissions;
  }
}
