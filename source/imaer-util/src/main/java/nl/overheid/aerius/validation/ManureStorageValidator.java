/*
 * Crown copyright
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

import nl.overheid.aerius.shared.domain.v2.source.ManureStorageEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.manure.AbstractManureStorage;
import nl.overheid.aerius.shared.domain.v2.source.manure.CustomManureStorage;
import nl.overheid.aerius.shared.domain.v2.source.manure.StandardManureStorage;
import nl.overheid.aerius.shared.emissions.FarmEmissionFactorType;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

class ManureStorageValidator extends SourceValidator<ManureStorageEmissionSource> {

  private static final Set<FarmEmissionFactorType> EXPECTED_EMISSION_FACTOR_TYPES = EnumSet.of(
      FarmEmissionFactorType.PER_TONNES_PER_YEAR,
      FarmEmissionFactorType.PER_METERS_SQUARED_PER_YEAR,
      FarmEmissionFactorType.PER_METERS_SQUARED_PER_DAY,
      FarmEmissionFactorType.PER_YEAR);

  private final ManureStorageValidationHelper validationHelper;

  ManureStorageValidator(final List<AeriusException> errors, final List<AeriusException> warnings,
      final ManureStorageValidationHelper validationHelper) {
    super(errors, warnings);
    this.validationHelper = validationHelper;
  }

  @Override
  boolean validate(final ManureStorageEmissionSource source) {
    boolean valid = true;
    for (final AbstractManureStorage subSource : source.getSubSources()) {
      valid = validateManureStorage(subSource, source.getGmlId()) && valid;
    }
    return valid;
  }

  private boolean validateManureStorage(final AbstractManureStorage subSource, final String sourceId) {
    boolean valid = true;
    if (subSource instanceof StandardManureStorage) {
      valid = validateStandard((StandardManureStorage) subSource, sourceId) && valid;
    } else if (subSource instanceof CustomManureStorage) {
      valid = validateCustom((CustomManureStorage) subSource, sourceId) && valid;
    }
    return valid;
  }

  private boolean validateStandard(final StandardManureStorage manureStorage, final String sourceId) {
    boolean valid = true;
    final String standardCode = manureStorage.getManureStorageCode();
    if (validationHelper.isValidManureStorageCode(standardCode)) {
      final FarmEmissionFactorType emissionFactorType = validationHelper.getManureStorageEmissionFactorType(standardCode);
      valid = FarmEmissionFactorTypeValidatorUtil.validate(EXPECTED_EMISSION_FACTOR_TYPES, emissionFactorType, manureStorage, sourceId, getErrors());
    } else {
      getErrors().add(new AeriusException(ImaerExceptionReason.GML_UNKNOWN_MANURE_STORAGE_CODE, sourceId, standardCode));
      valid = false;
    }
    return valid;
  }

  private boolean validateCustom(final CustomManureStorage manureStorage, final String sourceId) {
    return FarmEmissionFactorTypeValidatorUtil.validate(EXPECTED_EMISSION_FACTOR_TYPES, manureStorage.getFarmEmissionFactorType(), manureStorage,
        sourceId, getErrors());
  }

}
