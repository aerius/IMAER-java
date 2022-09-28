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

import nl.overheid.aerius.shared.domain.v2.source.FarmlandEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.farmland.AbstractFarmlandActivity;
import nl.overheid.aerius.shared.domain.v2.source.farmland.StandardFarmlandActivity;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

class FarmlandValidator extends SourceValidator<FarmlandEmissionSource> {

  private final FarmlandValidationHelper validationHelper;

  FarmlandValidator(final List<AeriusException> errors, final List<AeriusException> warnings, final FarmlandValidationHelper validationHelper) {
    super(errors, warnings);
    this.validationHelper = validationHelper;
  }

  @Override
  boolean validate(final FarmlandEmissionSource source) {
    boolean valid = true;
    for (final AbstractFarmlandActivity subSource : source.getSubSources()) {
      valid = validateActivity(subSource, source.getGmlId()) && valid;
    }
    return valid;
  }

  private boolean validateActivity(final AbstractFarmlandActivity subSource, final String sourceId) {
    final String code = subSource.getActivityCode();
    boolean valid = true;
    if (validationHelper.isValidFarmlandActivityCode(code)) {
      if (subSource instanceof StandardFarmlandActivity) {
        valid = validateStandard((StandardFarmlandActivity) subSource, sourceId) && valid;
      }
    } else {
      getErrors().add(new AeriusException(ImaerExceptionReason.GML_UNKNOWN_FARMLAND_ACTIVITY_CODE, sourceId, code));
      valid = false;
    }
    return valid;
  }

  private boolean validateStandard(final StandardFarmlandActivity activity, final String sourceId) {
    boolean valid = true;
    final String standardCode = activity.getFarmSourceCategoryCode();
    if (validationHelper.isValidFarmlandStandardActivityCode(standardCode)) {
      if (validationHelper.expectsFarmlandNumberOfAnimals(standardCode)) {
        if (activity.getNumberOfAnimals() == null) {
          getErrors().add(new AeriusException(ImaerExceptionReason.GML_MISSING_NUMBER_OF_ANIMALS, sourceId));
          valid = false;
        }
      } else {
        activity.setNumberOfAnimals(null);
      }
      if (validationHelper.expectsFarmlandNumberOfDays(standardCode)) {
        if (activity.getNumberOfDays() == null) {
          getErrors().add(new AeriusException(ImaerExceptionReason.GML_MISSING_NUMBER_OF_DAYS, sourceId));
          valid = false;
        }
      } else {
        activity.setNumberOfDays(null);
      }
    } else {
      getErrors().add(new AeriusException(ImaerExceptionReason.GML_UNKNOWN_FARMLAND_ACTIVITY_CODE, sourceId, standardCode));
      valid = false;
    }
    return valid;
  }

}
