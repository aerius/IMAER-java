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

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import nl.overheid.aerius.shared.domain.v2.source.FarmlandEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.farmland.AbstractFarmlandActivity;
import nl.overheid.aerius.shared.domain.v2.source.farmland.StandardFarmlandActivity;
import nl.overheid.aerius.shared.emissions.FarmEmissionFactorType;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

class FarmlandValidator extends SourceValidator<FarmlandEmissionSource> {

  private static final Set<FarmEmissionFactorType> EXPECTED_EMISSION_FACTOR_TYPES = EnumSet.of(
      FarmEmissionFactorType.PER_ANIMAL_PER_DAY,
      FarmEmissionFactorType.PER_ANIMAL_PER_YEAR,
      FarmEmissionFactorType.PER_METERS_CUBED_PER_APPLICATION,
      FarmEmissionFactorType.PER_TONNES_PER_APPLICATION);

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
      final FarmEmissionFactorType emissionFactorType = validationHelper.getFarmSourceEmissionFactorType(standardCode);
      valid = FarmEmissionFactorTypeValidatorUtil.validate(EXPECTED_EMISSION_FACTOR_TYPES, emissionFactorType, activity, sourceId, getErrors());
    } else {
      getErrors().add(new AeriusException(ImaerExceptionReason.GML_UNKNOWN_FARMLAND_ACTIVITY_CODE, sourceId, standardCode));
      valid = false;
    }
    return valid;
  }

}
