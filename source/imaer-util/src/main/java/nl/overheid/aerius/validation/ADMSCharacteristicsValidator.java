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

import nl.overheid.aerius.shared.domain.v2.characteristics.ADMSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.characteristics.adms.ADMSLimits;
import nl.overheid.aerius.shared.domain.v2.characteristics.adms.SourceType;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Class to validate ADMS source characteristics
 */
class ADMSCharacteristicsValidator extends CharacteristicsValidator<ADMSSourceCharacteristics> {

  ADMSCharacteristicsValidator(final List<AeriusException> errors, final List<AeriusException> warnings, final String sourceId) {
    super(errors, warnings, sourceId);
  }

  @Override
  boolean validate(final ADMSSourceCharacteristics characteristics) {
    boolean valid = true;
    if (characteristics.getSourceType() != SourceType.VOLUME && characteristics.getSourceType() != SourceType.ROAD) {
      valid = validateADMSHeatContent(characteristics);
    }
    validateADMSVolumeHeight(characteristics);
    return valid;
  }


  private boolean validateADMSHeatContent(final ADMSSourceCharacteristics characteristics) {
    boolean valid = true;
    if (characteristics.getSpecificHeatCapacity() < ADMSLimits.SOURCE_SPECIFIC_HEAT_CAPACITY_MINIMUM
        || characteristics.getSpecificHeatCapacity() > ADMSLimits.SOURCE_SPECIFIC_HEAT_CAPACITY_MAXIMUM) {
      errors.add(new AeriusException(ImaerExceptionReason.HEAT_CAPACITY_OUT_OF_RANGE, sourceId,
          String.valueOf(characteristics.getSpecificHeatCapacity()), String.valueOf(ADMSLimits.SOURCE_SPECIFIC_HEAT_CAPACITY_MINIMUM),
          String.valueOf(ADMSLimits.SOURCE_SPECIFIC_HEAT_CAPACITY_MAXIMUM)));
      valid = false;
    }
    return valid;
  }

  /**
   * Warn if the height is larger than half the vertical height (or 2 * height is larger than the vertical dimension).
   *
   * @param characteristics
   */
  private void validateADMSVolumeHeight(final ADMSSourceCharacteristics characteristics) {
    if (characteristics.getSourceType() == SourceType.VOLUME && ((characteristics.getHeight() * 2) - characteristics.getVerticalDimension()) > 0) {
      warnings.add(new AeriusException(ImaerExceptionReason.SOURCE_VOLUME_FLOATING, sourceId));
    }
  }
}
