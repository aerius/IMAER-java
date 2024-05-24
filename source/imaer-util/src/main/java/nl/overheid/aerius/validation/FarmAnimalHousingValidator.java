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

import nl.overheid.aerius.shared.domain.v2.source.FarmAnimalHousingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.farm.AdditionalHousingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.CustomAdditionalHousingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.CustomFarmAnimalHousing;
import nl.overheid.aerius.shared.domain.v2.source.farm.FarmAnimalHousing;
import nl.overheid.aerius.shared.domain.v2.source.farm.StandardAdditionalHousingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.StandardFarmAnimalHousing;
import nl.overheid.aerius.shared.emissions.FarmEmissionFactorType;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

class FarmAnimalHousingValidator extends SourceValidator<FarmAnimalHousingEmissionSource> {

  private static final Set<FarmEmissionFactorType> EXPECTED_EMISSION_FACTOR_TYPES =
      EnumSet.of(FarmEmissionFactorType.PER_ANIMAL_PER_DAY, FarmEmissionFactorType.PER_ANIMAL_PER_YEAR);

  private final FarmAnimalHousingValidationHelper validationHelper;

  FarmAnimalHousingValidator(final List<AeriusException> errors, final List<AeriusException> warnings,
      final FarmAnimalHousingValidationHelper validationHelper) {
    super(errors, warnings);
    this.validationHelper = validationHelper;
  }

  @Override
  boolean validate(final FarmAnimalHousingEmissionSource source) {
    boolean valid = true;
    for (final FarmAnimalHousing subSource : source.getSubSources()) {
      if (subSource instanceof final StandardFarmAnimalHousing standardSubSource) {
        valid = validateStandardAnimalHousing(standardSubSource, source.getGmlId()) && valid;
      } else if (subSource instanceof final CustomFarmAnimalHousing customSubSource) {
        valid = validateCustomAnimalHousing(customSubSource, source.getGmlId()) && valid;
      } else {
        getErrors().add(new AeriusException(ImaerExceptionReason.INTERNAL_ERROR, "Unexpected animal housing implementation"));
        valid = false;
      }
    }
    return valid;
  }

  private boolean validateStandardAnimalHousing(final StandardFarmAnimalHousing subSource, final String sourceId) {
    final String housingCode = subSource.getAnimalHousingCode();
    final String animalCode = subSource.getAnimalTypeCode();
    boolean valid = true;
    if (!validationHelper.isValidFarmAnimalCode(animalCode)) {
      getErrors().add(new AeriusException(ImaerExceptionReason.GML_UNKNOWN_ANIMAL_HOUSING_CODE, sourceId, animalCode));
      valid = false;
    } else if (!validationHelper.isValidFarmAnimalHousingCode(housingCode)) {
      getErrors().add(new AeriusException(ImaerExceptionReason.GML_UNKNOWN_ANIMAL_HOUSING_CODE, sourceId, housingCode));
      valid = false;
    } else if (!validationHelper.isValidFarmAnimalHousingCombination(animalCode, housingCode)) {
      getErrors().add(new AeriusException(ImaerExceptionReason.GML_UNSUPPORTED_ANIMAL_HOUSING_COMBINATION, sourceId, animalCode, housingCode));
      valid = false;
    } else {
      valid = validateNumberOfDays(subSource, sourceId) && valid;
      valid = validateAdditionalSystems(subSource.getAdditionalSystems(), housingCode, sourceId) && valid;
    }
    return valid;
  }

  private boolean validateNumberOfDays(final StandardFarmAnimalHousing subSource, final String sourceId) {
    final String code = subSource.getAnimalHousingCode();
    final FarmEmissionFactorType emissionFactorType = validationHelper.getAnimalHousingEmissionFactorType(code);
    return FarmEmissionFactorTypeValidatorUtil.validate(EXPECTED_EMISSION_FACTOR_TYPES, emissionFactorType, subSource, sourceId, getErrors());
  }

  private boolean validateAdditionalSystems(final List<AdditionalHousingSystem> additionalSystems, final String housingCode, final String sourceId) {
    boolean valid = true;
    for (final AdditionalHousingSystem additionalSystem : additionalSystems) {
      if (additionalSystem instanceof final StandardAdditionalHousingSystem standardSystem) {
        valid = validateStandardAdditionalSystem(standardSystem, housingCode, sourceId) && valid;
      } else if (additionalSystem instanceof final CustomAdditionalHousingSystem customSystem) {
        valid = validateCustomAdditionalSystem(customSystem, sourceId) && valid;
      } else {
        getErrors().add(new AeriusException(ImaerExceptionReason.INTERNAL_ERROR, "Unexpected additional animal housing system implementation"));
        valid = false;
      }
    }
    return valid;
  }

  private boolean validateStandardAdditionalSystem(final StandardAdditionalHousingSystem standardSystem, final String housingCode,
      final String sourceId) {
    final String code = standardSystem.getAdditionalSystemCode();
    boolean valid = true;
    if (!validationHelper.isValidFarmAdditionalSystemCode(code)) {
      getErrors().add(new AeriusException(ImaerExceptionReason.GML_UNKNOWN_ANIMAL_HOUSING_CODE, sourceId, code));
      valid = false;
    } else if (!validationHelper.isValidFarmAdditionalSystemCombination(housingCode, code)) {
      getErrors().add(new AeriusException(ImaerExceptionReason.GML_UNSUPPORTED_ANIMAL_HOUSING_COMBINATION, sourceId, housingCode, code));
      valid = false;
    }
    return valid;
  }

  private boolean validateCustomAdditionalSystem(final CustomAdditionalHousingSystem customSystem, final String sourceId) {
    boolean valid = true;
    if (customSystem.getEmissionReductionFactors().isEmpty()
        || customSystem.getEmissionReductionFactors().values().stream().anyMatch(x -> x < 0)) {
      getErrors().add(new AeriusException(ImaerExceptionReason.GML_INCORRECT_CUSTOM_FACTORS, sourceId));
      valid = false;
    }
    return valid;
  }

  private boolean validateCustomAnimalHousing(final CustomFarmAnimalHousing subSource, final String sourceId) {
    final FarmEmissionFactorType emissionFactorType = subSource.getFarmEmissionFactorType();
    boolean valid =
        FarmEmissionFactorTypeValidatorUtil.validate(EXPECTED_EMISSION_FACTOR_TYPES, emissionFactorType, subSource, sourceId, getErrors());
    if (subSource.getEmissionFactors().isEmpty()
        || subSource.getEmissionFactors().values().stream().anyMatch(x -> x < 0)) {
      getErrors().add(new AeriusException(ImaerExceptionReason.GML_INCORRECT_CUSTOM_FACTORS, sourceId));
      valid = false;
    }
    return valid;
  }

}
