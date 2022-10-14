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

import nl.overheid.aerius.shared.domain.v2.source.FarmLodgingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.farm.AdditionalLodgingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.CustomFarmLodging;
import nl.overheid.aerius.shared.domain.v2.source.farm.FarmLodging;
import nl.overheid.aerius.shared.domain.v2.source.farm.LodgingFodderMeasure;
import nl.overheid.aerius.shared.domain.v2.source.farm.ReductiveLodgingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.StandardFarmLodging;
import nl.overheid.aerius.shared.emissions.FarmEmissionFactorType;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

class FarmLodgingValidator extends SourceValidator<FarmLodgingEmissionSource> {

  private static final Set<FarmEmissionFactorType> EXPECTED_EMISSION_FACTOR_TYPES =
      EnumSet.of(FarmEmissionFactorType.PER_ANIMAL_PER_DAY, FarmEmissionFactorType.PER_ANIMAL_PER_YEAR);

  private final FarmLodgingValidationHelper validationHelper;

  FarmLodgingValidator(final List<AeriusException> errors, final List<AeriusException> warnings, final FarmLodgingValidationHelper validationHelper) {
    super(errors, warnings);
    this.validationHelper = validationHelper;
  }

  @Override
  boolean validate(final FarmLodgingEmissionSource source) {
    boolean valid = true;
    for (final FarmLodging subSource : source.getSubSources()) {
      if (subSource instanceof StandardFarmLodging) {
        valid = validateStandardLodging((StandardFarmLodging) subSource, source.getGmlId()) && valid;
      } else if (subSource instanceof CustomFarmLodging) {
        valid = validateCustomLodging((CustomFarmLodging) subSource, source.getGmlId()) && valid;
      }
    }
    return valid;
  }

  private boolean validateStandardLodging(final StandardFarmLodging subSource, final String sourceId) {
    final String code = subSource.getFarmLodgingCode();
    boolean valid = true;
    if (validationHelper.isValidFarmLodgingCode(code)) {
      valid = validateNumberOfDays(subSource, sourceId) && valid;
      for (final AdditionalLodgingSystem additionalSystem : subSource.getAdditionalLodgingSystems()) {
        valid = validateAdditionalSystem(additionalSystem, sourceId) && valid;
      }
      for (final ReductiveLodgingSystem reductiveSystem : subSource.getReductiveLodgingSystems()) {
        valid = validateReductiveSystem(reductiveSystem, sourceId) && valid;
      }
      for (final LodgingFodderMeasure fodderMeasure : subSource.getFodderMeasures()) {
        valid = validateFodderMeasure(subSource, fodderMeasure, sourceId) && valid;
      }
    } else {
      getErrors().add(new AeriusException(ImaerExceptionReason.GML_UNKNOWN_RAV_CODE, sourceId, code));
      valid = false;
    }
    return valid;
  }

  private boolean validateNumberOfDays(final StandardFarmLodging subSource, final String sourceId) {
    final String code = subSource.getFarmLodgingCode();
    final FarmEmissionFactorType emissionFactorType = validationHelper.getLodgingEmissionFactorType(code);
    return FarmEmissionFactorTypeValidatorUtil.validate(EXPECTED_EMISSION_FACTOR_TYPES, emissionFactorType, subSource, sourceId, getErrors());
  }

  private boolean validateAdditionalSystem(final AdditionalLodgingSystem lodgingSystem, final String sourceId) {
    final String code = lodgingSystem.getLodgingSystemCode();
    boolean valid = true;
    if (!validationHelper.isValidFarmLodgingAdditionalSystemCode(code)) {
      getErrors().add(new AeriusException(ImaerExceptionReason.GML_UNKNOWN_RAV_CODE, sourceId, code));
      valid = false;
    }
    return valid;
  }

  private boolean validateReductiveSystem(final ReductiveLodgingSystem lodgingSystem, final String sourceId) {
    final String code = lodgingSystem.getLodgingSystemCode();
    boolean valid = true;
    if (!validationHelper.isValidFarmLodgingReductiveSystemCode(code)) {
      getErrors().add(new AeriusException(ImaerExceptionReason.GML_UNKNOWN_RAV_CODE, sourceId, code));
      valid = false;
    }
    return valid;
  }

  private boolean validateFodderMeasure(final StandardFarmLodging lodging, final LodgingFodderMeasure fodderMeasure, final String sourceId) {
    final String fodderMeasureCode = fodderMeasure.getFodderMeasureCode();
    final String lodgingCode = lodging.getFarmLodgingCode();
    boolean valid = true;
    if (!validationHelper.isValidFarmLodgingFodderMeasureCode(fodderMeasureCode)) {
      getErrors().add(new AeriusException(ImaerExceptionReason.GML_UNKNOWN_PAS_MEASURE_CODE, sourceId, fodderMeasureCode));
      valid = false;
    } else if (!validationHelper.canFodderApplyToLodging(fodderMeasureCode, lodging.getFarmLodgingCode())) {
      getErrors().add(new AeriusException(ImaerExceptionReason.GML_INVALID_PAS_MEASURE_CATEGORY, sourceId, fodderMeasureCode, lodgingCode));
      valid = false;
    }
    return valid;
  }

  private boolean validateCustomLodging(final CustomFarmLodging subSource, final String sourceId) {
    final FarmEmissionFactorType emissionFactorType = subSource.getFarmEmissionFactorType();
    return FarmEmissionFactorTypeValidatorUtil.validate(EXPECTED_EMISSION_FACTOR_TYPES, emissionFactorType, subSource, sourceId, getErrors());
  }

}
