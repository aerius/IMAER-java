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

import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.source.ColdStartEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.CustomVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.SpecificVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardColdStartVehicles;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

@ExtendWith(MockitoExtension.class)
class ColdStartValidatorTest {

  private static final String SOURCE_ID = "OurSourceId";
  private static final String SOURCE_LABEL = "Source label";

  private static final String SPECIFIC_CODE = "SomeSpecificVehicleCode";
  private static final String STANDARD_CODE = "SomeStandardVehicleType";

  @Mock ColdStartValidationHelper validationHelper;

  @Test
  void testValidSpecificCode() {
    final ColdStartEmissionSource source = constructSource();
    final SpecificVehicles subSource = new SpecificVehicles();
    mockSpecificVehicleCategory(SPECIFIC_CODE);
    subSource.setVehicleCode(SPECIFIC_CODE);
    subSource.setTimeUnit(TimeUnit.DAY);
    subSource.setVehiclesPerTimeUnit(200);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final ColdStartValidator validator = new ColdStartValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertValidCase(valid, errors, warnings);
  }

  @Test
  void testInvalidSpecificCode() {
    final ColdStartEmissionSource source = constructSource();
    final SpecificVehicles subSource = new SpecificVehicles();
    subSource.setVehicleCode(SPECIFIC_CODE);
    subSource.setTimeUnit(TimeUnit.DAY);
    subSource.setVehiclesPerTimeUnit(200);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final ColdStartValidator validator = new ColdStartValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertInvalidCase(valid, errors, warnings, ImaerExceptionReason.GML_UNKNOWN_MOBILE_SOURCE_CODE, new Object[] {
        SOURCE_LABEL,
        SPECIFIC_CODE
    });
  }

  @Test
  void testValidStandardCode() {
    final ColdStartEmissionSource source = constructSource();
    final StandardColdStartVehicles subSource = new StandardColdStartVehicles();
    mockStandardVehicleCategory(STANDARD_CODE);
    subSource.setTimeUnit(TimeUnit.DAY);
    subSource.getValuesPerVehicleTypes().put(STANDARD_CODE, 200.0);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final ColdStartValidator validator = new ColdStartValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertValidCase(valid, errors, warnings);
  }

  @Test
  void testInvalidStandardCode() {
    final ColdStartEmissionSource source = constructSource();
    final StandardColdStartVehicles subSource = new StandardColdStartVehicles();
    subSource.setTimeUnit(TimeUnit.DAY);
    subSource.getValuesPerVehicleTypes().put(STANDARD_CODE, 200.0);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final ColdStartValidator validator = new ColdStartValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertInvalidCase(valid, errors, warnings, ImaerExceptionReason.GML_UNKNOWN_COLD_START_CATEGORY, new Object[] {
        SOURCE_LABEL,
        STANDARD_CODE
    });
  }

  @Test
  void testCustomNotVehicleBasedCharacteristics() {
    final ColdStartEmissionSource source = constructSource();
    final CustomVehicles subSource = new CustomVehicles();
    subSource.setTimeUnit(TimeUnit.DAY);
    source.getSubSources().add(subSource);
    source.setVehicleBasedCharacteristics(false);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final ColdStartValidator validator = new ColdStartValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertValidCase(valid, errors, warnings);
  }

  @Test
  void testValidStandardCodeForCustom() {
    final ColdStartEmissionSource source = constructSource();
    final CustomVehicles subSource = new CustomVehicles();
    mockStandardVehicleCategory(STANDARD_CODE);
    subSource.setTimeUnit(TimeUnit.DAY);
    subSource.setVehicleType(STANDARD_CODE);
    source.getSubSources().add(subSource);
    source.setVehicleBasedCharacteristics(true);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final ColdStartValidator validator = new ColdStartValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertValidCase(valid, errors, warnings);
  }

  @Test
  void testInvalidStandardCodeForCustom() {
    final ColdStartEmissionSource source = constructSource();
    final CustomVehicles subSource = new CustomVehicles();
    subSource.setTimeUnit(TimeUnit.DAY);
    subSource.setVehicleType(STANDARD_CODE);
    source.getSubSources().add(subSource);
    source.setVehicleBasedCharacteristics(true);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final ColdStartValidator validator = new ColdStartValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertInvalidCase(valid, errors, warnings, ImaerExceptionReason.GML_UNKNOWN_COLD_START_CATEGORY, new Object[] {
        SOURCE_LABEL,
        STANDARD_CODE
    });
  }

  private static void assertValidCase(final boolean valid, final List<AeriusException> errors, final List<AeriusException> warnings) {
    assertTrue(valid, "Valid test case");
    assertEquals(List.of(), errors, "No errors");
    assertEquals(List.of(), warnings, "No warnings");
  }

  private static void assertInvalidCase(final boolean valid, final List<AeriusException> errors, final List<AeriusException> warnings,
      final ImaerExceptionReason expectedReason, final Object[] expectedArguments) {
    assertFalse(valid, "Invalid test case");
    assertEquals(1, errors.size(), "Nr of errors");
    assertEquals(expectedReason, errors.get(0).getReason(), "Error reason");
    assertArrayEquals(expectedArguments, errors.get(0).getArgs(), "Arguments");
    assertEquals(List.of(), warnings, "No warnings");
  }

  private ColdStartEmissionSource constructSource() {
    final ColdStartEmissionSource source = new ColdStartEmissionSource();
    source.setGmlId(SOURCE_ID);
    source.setLabel(SOURCE_LABEL);
    return source;
  }

  private void mockSpecificVehicleCategory(final String code) {
    when(validationHelper.isValidColdStartSpecificVehicleCode(code)).thenReturn(true);
  }

  private void mockStandardVehicleCategory(final String code) {
    when(validationHelper.isValidColdStartStandardVehicleCode(code)).thenReturn(true);
  }

}
