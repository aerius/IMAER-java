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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.overheid.aerius.shared.domain.v2.source.ManureStorageEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.manure.CustomManureStorage;
import nl.overheid.aerius.shared.domain.v2.source.manure.StandardManureStorage;
import nl.overheid.aerius.shared.emissions.FarmEmissionFactorType;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

@ExtendWith(MockitoExtension.class)
class ManureStorageValidatorTest {

  private static final String SOURCE_ID = "OurSourceId";

  @Mock ManureStorageValidationHelper validationHelper;

  @Test
  void testValidCustomMetersSquaredDay() {
    final ManureStorageEmissionSource source = constructSource();
    final CustomManureStorage subSource = new CustomManureStorage();
    subSource.setFarmEmissionFactorType(FarmEmissionFactorType.PER_METERS_SQUARED_PER_DAY);
    subSource.setMetersSquared(3.2);
    subSource.setNumberOfDays(40);
    source.getSubSources().add(subSource);

    assertCorrectCase(source);
  }

  @Test
  void testValidCustomMetersSquared() {
    final ManureStorageEmissionSource source = constructSource();
    final CustomManureStorage subSource = new CustomManureStorage();
    subSource.setFarmEmissionFactorType(FarmEmissionFactorType.PER_METERS_SQUARED_PER_YEAR);
    subSource.setMetersSquared(3.2);
    source.getSubSources().add(subSource);

    assertCorrectCase(source);
  }

  @Test
  void testValidCustomTonnes() {
    final ManureStorageEmissionSource source = constructSource();
    final CustomManureStorage subSource = new CustomManureStorage();
    subSource.setFarmEmissionFactorType(FarmEmissionFactorType.PER_TONNES_PER_YEAR);
    subSource.setTonnes(3.2);
    source.getSubSources().add(subSource);

    assertCorrectCase(source);
  }

  @Test
  void testValidCustom() {
    final ManureStorageEmissionSource source = constructSource();
    final CustomManureStorage subSource = new CustomManureStorage();
    subSource.setFarmEmissionFactorType(FarmEmissionFactorType.PER_YEAR);
    source.getSubSources().add(subSource);

    assertCorrectCase(source);
  }

  @Test
  void testInvalidCustomEmissionFactorType() {
    final ManureStorageEmissionSource source = constructSource();
    final CustomManureStorage subSource = new CustomManureStorage();
    subSource.setFarmEmissionFactorType(FarmEmissionFactorType.PER_ANIMAL_PER_DAY);
    subSource.setMetersSquared(3.2);
    subSource.setNumberOfDays(40);
    source.getSubSources().add(subSource);

    assertInvalidCase(source, ImaerExceptionReason.GML_UNKNOWN_FARM_EMISSION_FACTOR_TYPE, new Object[] {
        SOURCE_ID, "PER_ANIMAL_PER_DAY"
    });
  }

  @Test
  void testValidStandardManureStorageFull() {
    final ManureStorageEmissionSource source = constructSource();
    final StandardManureStorage subSource = new StandardManureStorage();
    final String standardCode = "SomethingStandard";
    mockStandardCategory(standardCode, FarmEmissionFactorType.PER_METERS_SQUARED_PER_YEAR);
    subSource.setManureStorageCode(standardCode);
    subSource.setMetersSquared(40.2);

    source.getSubSources().add(subSource);

    assertCorrectCase(source);
  }

  @Test
  void testInvalidStandardManureStorageCode() {
    final ManureStorageEmissionSource source = constructSource();
    final StandardManureStorage subSource = new StandardManureStorage();
    final String standardCode = "SomethingStandard";
    subSource.setManureStorageCode(standardCode);
    subSource.setMetersSquared(40.2);

    source.getSubSources().add(subSource);

    assertInvalidCase(source, ImaerExceptionReason.GML_UNKNOWN_MANURE_STORAGE_CODE, new Object[] {
        SOURCE_ID, standardCode
    });
  }

  @Test
  void testStandardMissingMetersSquared() {
    final ManureStorageEmissionSource source = constructSource();
    final StandardManureStorage subSource = new StandardManureStorage();
    final String standardCode = "SomethingStandard";
    mockStandardCategory(standardCode, FarmEmissionFactorType.PER_METERS_SQUARED_PER_YEAR);
    subSource.setManureStorageCode(standardCode);

    source.getSubSources().add(subSource);

    assertInvalidCase(source, ImaerExceptionReason.MISSING_METERS_SQUARED, new Object[] {
        SOURCE_ID
    });
  }

  @Test
  void testStandardMissingDays() {
    final ManureStorageEmissionSource source = constructSource();
    final StandardManureStorage subSource = new StandardManureStorage();
    final String standardCode = "SomethingStandard";
    mockStandardCategory(standardCode, FarmEmissionFactorType.PER_METERS_SQUARED_PER_DAY);
    subSource.setManureStorageCode(standardCode);
    subSource.setMetersSquared(40.2);

    source.getSubSources().add(subSource);

    assertInvalidCase(source, ImaerExceptionReason.MISSING_NUMBER_OF_DAYS, new Object[] {
        SOURCE_ID
    });
  }

  @Test
  void testStandardMissingTonnes() {
    final ManureStorageEmissionSource source = constructSource();
    final StandardManureStorage subSource = new StandardManureStorage();
    final String standardCode = "SomethingStandard";
    mockStandardCategory(standardCode, FarmEmissionFactorType.PER_TONNES_PER_YEAR);
    subSource.setManureStorageCode(standardCode);

    source.getSubSources().add(subSource);

    assertInvalidCase(source, ImaerExceptionReason.MISSING_TONNES, new Object[] {
        SOURCE_ID
    });
  }

  private void assertCorrectCase(final ManureStorageEmissionSource source) {
    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final ManureStorageValidator validator = new ManureStorageValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertTrue(valid, "Valid test case");
    assertTrue(errors.isEmpty(), "No errors");
    assertTrue(warnings.isEmpty(), "No warnings");
  }

  private void assertInvalidCase(final ManureStorageEmissionSource source, final ImaerExceptionReason errorReason, final Object[] errorArguments) {
    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final ManureStorageValidator validator = new ManureStorageValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertFalse(valid, "Invalid test case");
    assertEquals(1, errors.size(), "Number of errors");
    assertEquals(errorReason, errors.get(0).getReason(), "Error reason");
    assertArrayEquals(errorArguments, errors.get(0).getArgs(), "Arguments");
    assertTrue(warnings.isEmpty(), "No warnings");
  }

  private ManureStorageEmissionSource constructSource() {
    final ManureStorageEmissionSource source = new ManureStorageEmissionSource();
    source.setGmlId(SOURCE_ID);
    source.setLabel("Source label");
    return source;
  }

  private void mockStandardCategory(final String code, final FarmEmissionFactorType emissionFactorType) {
    when(validationHelper.isValidManureStorageCode(code)).thenReturn(true);
    when(validationHelper.getManureStorageEmissionFactorType(code)).thenReturn(emissionFactorType);
  }

}
