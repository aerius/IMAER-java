/*
 * Copyright (c) Contributors to the project
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

import nl.overheid.aerius.shared.domain.IntRange;
import nl.overheid.aerius.shared.domain.v2.source.OffRoadMobileEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.offroad.OffRoadMobileSource;
import nl.overheid.aerius.shared.domain.v2.source.offroad.StandardOffRoadMobileSource;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
class OffRoadValidator extends SourceValidator<OffRoadMobileEmissionSource> {

  private enum EmissionMethod {
    POWER, FUEL, NOT_REQUIRED, MISSING
  }

  private final OffRoadValidationHelper validationHelper;

  OffRoadValidator(final List<AeriusException> errors, final List<AeriusException> warnings, final OffRoadValidationHelper validationHelper) {
    super(errors, warnings);
    this.validationHelper = validationHelper;
  }

  @Override
  boolean validate(final OffRoadMobileEmissionSource source) {
    boolean valid = true;
    for (final OffRoadMobileSource subSource : source.getSubSources()) {
      if (subSource instanceof StandardOffRoadMobileSource) {
        valid = validateStandardOffRoad((StandardOffRoadMobileSource) subSource, source.getGmlId()) && valid;
      }
    }
    return valid;
  }

  private boolean validateStandardOffRoad(final StandardOffRoadMobileSource subSource, final String sourceId) {
    final String code = subSource.getOffRoadMobileSourceCode();
    final boolean valid;
    if (validationHelper.isValidOffRoadMobileSourceCode(code)) {
      valid = validateOffRoadProperties(subSource);
    } else {
      getErrors().add(new AeriusException(ImaerExceptionReason.GML_UNKNOWN_MOBILE_SOURCE_CODE, sourceId, code));
      valid = false;
    }
    return valid;
  }

  private boolean validateOffRoadProperties(final StandardOffRoadMobileSource subSource) {
    // Combine all validations in separate statements to make sure each validation is run to collect all validation warnings/errors.
    final boolean valid = switch (determineEmissionMethod(subSource)) {
      case POWER -> validatePowerBased(subSource);
      case FUEL -> validateFuelBased(subSource);
      case NOT_REQUIRED -> validateNotRequired(subSource);
      case MISSING -> {
        getErrors().add(new AeriusException(ImaerExceptionReason.MOBILE_SOURCE_MISSING_POWER_OR_LITER_FUEL, subSource.getDescription()));
        yield false;
      }
    };
    return validateOffRoadOperatingHours(subSource) && valid;
  }

  private EmissionMethod determineEmissionMethod(final StandardOffRoadMobileSource subSource) {
    final String code = subSource.getOffRoadMobileSourceCode();
    final boolean hasPower = subSource.getPower() != null && subSource.getPower() > 0;
    final boolean hasFuel = subSource.getLiterFuelPerYear() != null && subSource.getLiterFuelPerYear() > 0;
    // A category can accept power, fuel or neither.
    if (hasPower && validationHelper.expectsPower(code)) {
      return EmissionMethod.POWER;
    } else if (hasFuel && validationHelper.expectsLiterFuelPerYear(code)) {
      return EmissionMethod.FUEL;
    } else if (!validationHelper.expectsPower(code) && !validationHelper.expectsLiterFuelPerYear(code)) {
      return EmissionMethod.NOT_REQUIRED;
    } else {
      return EmissionMethod.MISSING;
    }
  }

  private boolean validateNotRequired(final StandardOffRoadMobileSource subSource) {
    // Category expects neither power nor fuel; clear those inputs.
    subSource.setPower(null);
    subSource.setLiterFuelPerYear(null);
    return validateOffRoadLiterAdBlue(subSource);
  }

  private boolean validatePowerBased(final StandardOffRoadMobileSource subSource) {
    // Power "!= null && > 0" checks have been handled in determineEmissionMethod already

    // Unused for power based; Set to null
    subSource.setLiterFuelPerYear(null);
    subSource.setLiterAdBluePerYear(null);

    return validateOffRoadPowerRange(subSource);
  }

  private boolean validateFuelBased(final StandardOffRoadMobileSource subSource) {
    boolean valid = true;
    final String code = subSource.getOffRoadMobileSourceCode();
    final boolean expectsFuel = validationHelper.expectsLiterFuelPerYear(code);

    if (expectsFuel && subSource.getLiterFuelPerYear() == null) {
      getErrors().add(new AeriusException(ImaerExceptionReason.MOBILE_SOURCE_MISSING_POWER_OR_LITER_FUEL, subSource.getDescription()));
      valid = false;
    }
    if (!expectsFuel) {
      subSource.setLiterFuelPerYear(null);
    }
    // Fallback in case custom GML is passed with power = 0
    subSource.setPower(null);

    valid = validateOffRoadLiterAdBlue(subSource) && valid;
    return valid;
  }

  private boolean validateOffRoadPowerRange(final StandardOffRoadMobileSource subSource) {
    final String code = subSource.getOffRoadMobileSourceCode();

    if (validationHelper.expectsPower(code)) {
      final Optional<IntRange> powerRange = validationHelper.getPowerRange(code);

      if (!powerRange.map(range -> range.inRange(subSource.getPower())).orElse(false)) {
        getErrors().add(new AeriusException(ImaerExceptionReason.MOBILE_SOURCE_POWER_NOT_WITHIN_RANGE, subSource.getDescription(),
            powerRange.get().toString(), String.valueOf(subSource.getPower())));
        return false;
      }
    }
    return true;
  }

  private boolean validateOffRoadOperatingHours(final StandardOffRoadMobileSource subSource) {
    boolean valid = true;
    if (validationHelper.expectsOperatingHoursPerYear(subSource.getOffRoadMobileSourceCode()) && subSource.getOperatingHoursPerYear() == null) {
      getErrors().add(new AeriusException(ImaerExceptionReason.MOBILE_SOURCE_MISSING_OPERATING_HOURS, subSource.getDescription()));
      valid = false;
    } else if (subSource.getOperatingHoursPerYear() != null && subSource.getOperatingHoursPerYear() == 0) {
      subSource.setOperatingHoursPerYear(null);
    }
    return valid;
  }

  private boolean validateOffRoadLiterAdBlue(final StandardOffRoadMobileSource subSource) {
    boolean valid = true;
    if (validationHelper.expectsLiterAdBluePerYear(subSource.getOffRoadMobileSourceCode())) {
      if (subSource.getLiterAdBluePerYear() == null) {
        getErrors().add(new AeriusException(ImaerExceptionReason.MOBILE_SOURCE_MISSING_LITER_ADBLUE, subSource.getDescription()));
        valid = false;
      } else if (subSource.getLiterFuelPerYear() != null) {
        validateAdBlueFuelRatio(subSource);
      }
    } else if (subSource.getLiterAdBluePerYear() != null && subSource.getLiterAdBluePerYear() == 0) {
      subSource.setLiterAdBluePerYear(null);
    }
    return valid;
  }

  private void validateAdBlueFuelRatio(final StandardOffRoadMobileSource subSource) {
    final OptionalDouble maxRatio = validationHelper.getMaxAdBlueFuelRatio(subSource.getOffRoadMobileSourceCode());
    if (maxRatio.isPresent()) {
      final BigDecimal fuel = BigDecimal.valueOf(subSource.getLiterFuelPerYear());
      final BigDecimal maxAdBlue = fuel.multiply(BigDecimal.valueOf(maxRatio.getAsDouble()));
      if (subSource.getLiterAdBluePerYear() > maxAdBlue.doubleValue()) {
        getWarnings().add(new AeriusException(ImaerExceptionReason.MOBILE_SOURCE_HIGH_ADBLUE_FUEL_RATIO, subSource.getDescription(),
            maxAdBlue.toString(), String.valueOf(subSource.getLiterAdBluePerYear())));
      }
    }
  }

}
