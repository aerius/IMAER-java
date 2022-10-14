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
package nl.overheid.aerius.validation;

import java.util.List;
import java.util.Set;

import nl.overheid.aerius.shared.emissions.FarmEmissionFactorType;
import nl.overheid.aerius.shared.emissions.IsFarmEmissionFactorTypeObject;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Local util class to help with validating objects using FarmEmissionFactorType.
 */
class FarmEmissionFactorTypeValidatorUtil {

  private FarmEmissionFactorTypeValidatorUtil() {
    // Util class
  }

  static boolean validate(final Set<FarmEmissionFactorType> expectedTypes, final FarmEmissionFactorType emissionFactorType,
      final IsFarmEmissionFactorTypeObject targetObject,
      final String sourceId, final List<AeriusException> errors) {
    return validateExpectedType(expectedTypes, emissionFactorType, sourceId, errors)
        && validateExpectedProperties(emissionFactorType, targetObject, sourceId, errors);
  }

  private static boolean validateExpectedType(final Set<FarmEmissionFactorType> expectedTypes, final FarmEmissionFactorType emissionFactorType,
      final String sourceId, final List<AeriusException> errors) {
    boolean valid = true;
    if (!expectedTypes.contains(emissionFactorType)) {
      errors.add(new AeriusException(ImaerExceptionReason.GML_UNKNOWN_FARM_EMISSION_FACTOR_TYPE, sourceId,
          emissionFactorType == null ? "null" : emissionFactorType.name()));
      valid = false;
    }
    return valid;
  }

  private static boolean validateExpectedProperties(final FarmEmissionFactorType emissionFactorType,
      final IsFarmEmissionFactorTypeObject targetObject,
      final String sourceId, final List<AeriusException> errors) {
    boolean valid = validateNumberOfAnimals(emissionFactorType, targetObject, sourceId, errors);
    valid = validateNumberOfDays(emissionFactorType, targetObject, sourceId, errors) && valid;
    valid = validateNumberOfApplications(emissionFactorType, targetObject, sourceId, errors) && valid;
    valid = validateMetersCubed(emissionFactorType, targetObject, sourceId, errors) && valid;
    valid = validateTonnes(emissionFactorType, targetObject, sourceId, errors) && valid;
    valid = validateMetersSquared(emissionFactorType, targetObject, sourceId, errors) && valid;
    return valid;
  }

  private static boolean validateNumberOfAnimals(final FarmEmissionFactorType emissionFactorType, final IsFarmEmissionFactorTypeObject targetObject,
      final String sourceId, final List<AeriusException> errors) {
    boolean valid = true;
    if (FarmEmissionFactorType.expectsNumberOfAnimals(emissionFactorType)) {
      if (targetObject.getNumberOfAnimals() == null) {
        errors.add(new AeriusException(ImaerExceptionReason.MISSING_NUMBER_OF_ANIMALS, sourceId));
        valid = false;
      }
    } else {
      targetObject.setNumberOfAnimals(null);
    }
    return valid;
  }

  private static boolean validateNumberOfDays(final FarmEmissionFactorType emissionFactorType, final IsFarmEmissionFactorTypeObject targetObject,
      final String sourceId, final List<AeriusException> errors) {
    boolean valid = true;
    if (FarmEmissionFactorType.expectsNumberOfDays(emissionFactorType)) {
      if (targetObject.getNumberOfDays() == null) {
        errors.add(new AeriusException(ImaerExceptionReason.MISSING_NUMBER_OF_DAYS, sourceId));
        valid = false;
      }
    } else {
      targetObject.setNumberOfDays(null);
    }
    return valid;
  }

  private static boolean validateNumberOfApplications(final FarmEmissionFactorType emissionFactorType,
      final IsFarmEmissionFactorTypeObject targetObject,
      final String sourceId, final List<AeriusException> errors) {
    boolean valid = true;
    if (FarmEmissionFactorType.expectsNumberOfApplications(emissionFactorType)) {
      if (targetObject.getNumberOfApplications() == null) {
        errors.add(new AeriusException(ImaerExceptionReason.MISSING_NUMBER_OF_APPLICATIONS, sourceId));
        valid = false;
      }
    } else {
      targetObject.setNumberOfApplications(null);
    }
    return valid;
  }

  private static boolean validateMetersCubed(final FarmEmissionFactorType emissionFactorType, final IsFarmEmissionFactorTypeObject targetObject,
      final String sourceId, final List<AeriusException> errors) {
    boolean valid = true;
    if (FarmEmissionFactorType.expectsMetersCubed(emissionFactorType)) {
      if (targetObject.getMetersCubed() == null) {
        errors.add(new AeriusException(ImaerExceptionReason.MISSING_METERS_CUBED, sourceId));
        valid = false;
      }
    } else {
      targetObject.setMetersCubed(null);
    }
    return valid;
  }

  private static boolean validateTonnes(final FarmEmissionFactorType emissionFactorType, final IsFarmEmissionFactorTypeObject targetObject,
      final String sourceId, final List<AeriusException> errors) {
    boolean valid = true;
    if (FarmEmissionFactorType.expectsTonnes(emissionFactorType)) {
      if (targetObject.getTonnes() == null) {
        errors.add(new AeriusException(ImaerExceptionReason.MISSING_TONNES, sourceId));
        valid = false;
      }
    } else {
      targetObject.setTonnes(null);
    }
    return valid;
  }

  private static boolean validateMetersSquared(final FarmEmissionFactorType emissionFactorType, final IsFarmEmissionFactorTypeObject targetObject,
      final String sourceId, final List<AeriusException> errors) {
    boolean valid = true;
    if (FarmEmissionFactorType.expectsMetersSquared(emissionFactorType)) {
      if (targetObject.getMetersSquared() == null) {
        errors.add(new AeriusException(ImaerExceptionReason.MISSING_METERS_SQUARED, sourceId));
        valid = false;
      }
    } else {
      targetObject.setMetersSquared(null);
    }
    return valid;
  }

}
