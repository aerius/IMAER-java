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
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.overheid.aerius.shared.domain.v2.source.OffRoadMobileEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.offroad.CustomOffRoadMobileSource;
import nl.overheid.aerius.shared.domain.v2.source.offroad.StandardOffRoadMobileSource;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
@ExtendWith(MockitoExtension.class)
class OffRoadValidatorTest {

  private static final String SUB_SOURCE_DESCRIPTION = "someSubsource";

  @Mock OffRoadValidationHelper validationHelper;

  @Test
  void testValidSubSourceDeviating() {
    final OffRoadMobileEmissionSource source = constructSource();
    final CustomOffRoadMobileSource subSource = new CustomOffRoadMobileSource();
    // Should probably have some other stuff set as well but since we're not validating this (yet)...
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final OffRoadValidator validator = new OffRoadValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertTrue(valid, "Valid test case");
    assertEquals(List.of(), errors, "No errors");
    assertEquals(List.of(), warnings, "No warnings");
  }

  @Test
  void testValidSubSourceOnlyLiterFuel() {
    final OffRoadMobileEmissionSource source = constructSource();
    final StandardOffRoadMobileSource subSource = new StandardOffRoadMobileSource();
    final String code = "LiterFuelOnly";
    mockCategoryExpectingLiterFuel(code);
    subSource.setDescription(SUB_SOURCE_DESCRIPTION);
    subSource.setOffRoadMobileSourceCode(code);
    subSource.setLiterFuelPerYear(10000);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final OffRoadValidator validator = new OffRoadValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertTrue(valid, "Valid test case");
    assertEquals(List.of(), errors, "No errors");
    assertEquals(List.of(), warnings, "No warnings");
  }

  @Test
  void testValidSubSourceOnlyOperatingHours() {
    final OffRoadMobileEmissionSource source = constructSource();
    final StandardOffRoadMobileSource subSource = new StandardOffRoadMobileSource();
    final String code = "OperatingHoursOnly";
    mockCategoryExpectingOperatingHours(code);
    subSource.setDescription(SUB_SOURCE_DESCRIPTION);
    subSource.setOffRoadMobileSourceCode(code);
    subSource.setOperatingHoursPerYear(3000);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final OffRoadValidator validator = new OffRoadValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertTrue(valid, "Valid test case");
    assertEquals(List.of(), errors, "No errors");
    assertEquals(List.of(), warnings, "No warnings");
  }

  @Test
  void testValidSubSourceOnlyAdBlue() {
    final OffRoadMobileEmissionSource source = constructSource();
    final StandardOffRoadMobileSource subSource = new StandardOffRoadMobileSource();
    final String code = "OperatingHoursOnly";
    mockCategoryExpectingLiterAdBlue(code);
    subSource.setDescription(SUB_SOURCE_DESCRIPTION);
    subSource.setOffRoadMobileSourceCode(code);
    subSource.setLiterAdBluePerYear(500);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final OffRoadValidator validator = new OffRoadValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertTrue(valid, "Valid test case");
    assertEquals(List.of(), errors, "No errors");
    assertEquals(List.of(), warnings, "No warnings");
  }

  @Test
  void testValidSubSourceAllRequired() {
    final OffRoadMobileEmissionSource source = constructSource();
    final StandardOffRoadMobileSource subSource = new StandardOffRoadMobileSource();
    final String code = "AllProperties";
    mockCategoryExpectingAll(code);
    subSource.setDescription(SUB_SOURCE_DESCRIPTION);
    subSource.setOffRoadMobileSourceCode(code);
    subSource.setLiterFuelPerYear(10000);
    subSource.setOperatingHoursPerYear(3000);
    subSource.setLiterAdBluePerYear(500);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final OffRoadValidator validator = new OffRoadValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertTrue(valid, "Valid test case");
    assertEquals(List.of(), errors, "No errors");
    assertEquals(List.of(), warnings, "No warnings");
  }

  @Test
  void testSubSourceMissingLiterFuel() {
    final OffRoadMobileEmissionSource source = constructSource();
    final StandardOffRoadMobileSource subSource = new StandardOffRoadMobileSource();
    final String code = "AllProperties";
    mockCategoryExpectingAll(code);
    subSource.setDescription(SUB_SOURCE_DESCRIPTION);
    subSource.setOffRoadMobileSourceCode(code);
    subSource.setOperatingHoursPerYear(3000);
    subSource.setLiterAdBluePerYear(500);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final OffRoadValidator validator = new OffRoadValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertFalse(valid, "Invalid test case");
    assertEquals(1, errors.size(), "Nr of errors");
    assertEquals(ImaerExceptionReason.MOBILE_SOURCE_MISSING_LITER_FUEL, errors.get(0).getReason(), "Specific error");
    assertArrayEquals(new Object[] {
        SUB_SOURCE_DESCRIPTION
    }, errors.get(0).getArgs(), "Arguments");
    assertEquals(List.of(), warnings, "No warnings");
  }

  @Test
  void testSubSourceMissingOperatingHours() {
    final OffRoadMobileEmissionSource source = constructSource();
    final StandardOffRoadMobileSource subSource = new StandardOffRoadMobileSource();
    final String code = "AllProperties";
    mockCategoryExpectingAll(code);
    subSource.setDescription(SUB_SOURCE_DESCRIPTION);
    subSource.setOffRoadMobileSourceCode(code);
    subSource.setLiterFuelPerYear(10000);
    subSource.setLiterAdBluePerYear(500);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final OffRoadValidator validator = new OffRoadValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertFalse(valid, "Invalid test case");
    assertEquals(1, errors.size(), "Nr of errors");
    assertEquals(ImaerExceptionReason.MOBILE_SOURCE_MISSING_OPERATING_HOURS, errors.get(0).getReason(), "Specific error");
    assertArrayEquals(new Object[] {
        SUB_SOURCE_DESCRIPTION
    }, errors.get(0).getArgs(), "Arguments");
    assertEquals(List.of(), warnings, "No warnings");
  }

  @Test
  void testSubSourceMissingLiterAdBlue() {
    final OffRoadMobileEmissionSource source = constructSource();
    final StandardOffRoadMobileSource subSource = new StandardOffRoadMobileSource();
    final String code = "AllProperties";
    mockCategoryExpectingAll(code);
    subSource.setDescription(SUB_SOURCE_DESCRIPTION);
    subSource.setOffRoadMobileSourceCode(code);
    subSource.setLiterFuelPerYear(10000);
    subSource.setOperatingHoursPerYear(3000);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final OffRoadValidator validator = new OffRoadValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertFalse(valid, "Invalid test case");
    assertEquals(1, errors.size(), "Nr of errors");
    assertEquals(ImaerExceptionReason.MOBILE_SOURCE_MISSING_LITER_ADBLUE, errors.get(0).getReason(), "Specific error");
    assertArrayEquals(new Object[] {
        SUB_SOURCE_DESCRIPTION
    }, errors.get(0).getArgs(), "Arguments");
    assertEquals(List.of(), warnings, "No warnings");
  }

  @Test
  void testSubSourceMissingAll() {
    final OffRoadMobileEmissionSource source = constructSource();
    final StandardOffRoadMobileSource subSource = new StandardOffRoadMobileSource();
    final String code = "AllProperties";
    mockCategoryExpectingAll(code);
    subSource.setDescription(SUB_SOURCE_DESCRIPTION);
    subSource.setOffRoadMobileSourceCode(code);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final OffRoadValidator validator = new OffRoadValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertFalse(valid, "Invalid test case");
    assertEquals(3, errors.size(), "Nr of errors");
    assertEquals(List.of(), warnings, "No warnings");
  }

  @Test
  void testSubSourceTooMuchLiterAdBlue() {
    final OffRoadMobileEmissionSource source = constructSource();
    final StandardOffRoadMobileSource subSource = new StandardOffRoadMobileSource();
    final String code = "AllProperties";
    mockCategoryExpectingAll(code);
    subSource.setDescription(SUB_SOURCE_DESCRIPTION);
    subSource.setOffRoadMobileSourceCode(code);
    subSource.setLiterFuelPerYear(10000);
    subSource.setOperatingHoursPerYear(3000);
    // Ratio mocked is 0.07, so 10% should trigger a warning.
    subSource.setLiterAdBluePerYear(1000);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final OffRoadValidator validator = new OffRoadValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertTrue(valid, "Valid test case");
    assertEquals(1, warnings.size(), "Nr of errors");
    assertEquals(ImaerExceptionReason.MOBILE_SOURCE_HIGH_ADBLUE_FUEL_RATIO, warnings.get(0).getReason(), "Specific error");
    assertArrayEquals(new Object[] {
        SUB_SOURCE_DESCRIPTION, "700.00", "1000"
    }, warnings.get(0).getArgs(), "Arguments");
    assertEquals(List.of(), errors, "No errors");
  }

  private OffRoadMobileEmissionSource constructSource() {
    final OffRoadMobileEmissionSource source = new OffRoadMobileEmissionSource();
    source.setLabel("Source label");
    return source;
  }

  private void mockCategoryExpectingAll(final String code) {
    mockCategoryExpectingLiterFuel(code);
    mockCategoryExpectingOperatingHours(code);
    mockCategoryExpectingLiterAdBlue(code);
  }

  private void mockCategoryExpectingLiterFuel(final String code) {
    when(validationHelper.isValidOffRoadMobileSourceCode(code)).thenReturn(true);
    when(validationHelper.expectsLiterFuelPerYear(code)).thenReturn(true);
  }

  private void mockCategoryExpectingOperatingHours(final String code) {
    when(validationHelper.isValidOffRoadMobileSourceCode(code)).thenReturn(true);
    when(validationHelper.expectsOperatingHoursPerYear(code)).thenReturn(true);
  }

  private void mockCategoryExpectingLiterAdBlue(final String code) {
    when(validationHelper.isValidOffRoadMobileSourceCode(code)).thenReturn(true);
    when(validationHelper.expectsLiterAdBluePerYear(code)).thenReturn(true);
    lenient().when(validationHelper.getMaxAdBlueFuelRatio(code)).thenReturn(OptionalDouble.of(0.07));
  }

}
