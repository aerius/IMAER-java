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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.overheid.aerius.shared.domain.IntRange;
import nl.overheid.aerius.shared.domain.v2.source.OffRoadMobileEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.offroad.CustomOffRoadMobileSource;
import nl.overheid.aerius.shared.domain.v2.source.offroad.StandardOffRoadMobileSource;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.util.IntRangeUtil;

/**
 * Test for {@link OffRoadValidator}.
 */
@ExtendWith(MockitoExtension.class)
class OffRoadValidatorTest {

  private enum MockCategory {
    POWER, FUEL, HOURS, ADBLUE
  }

  private static final String SUB_SOURCE_DESCRIPTION = "someSubsource";
  private static final String CODE = "mobile_source_1";

  @Mock
  OffRoadValidationHelper validationHelper;

  private List<AeriusException> errors = new ArrayList<>();
  private List<AeriusException> warnings = new ArrayList<>();
  private OffRoadValidator validator;

  @BeforeEach
  void beforeEach() {
    errors = new ArrayList<>();
    warnings = new ArrayList<>();
    validator = new OffRoadValidator(errors, warnings, validationHelper);
  }

  @Test
  void testValidSubSourceDeviating() {
    final OffRoadMobileEmissionSource source = new OffRoadMobileEmissionSource();
    final CustomOffRoadMobileSource subSource = new CustomOffRoadMobileSource();
    // Should probably have some other stuff set as well but since we're not validating this (yet)...
    source.getSubSources().add(subSource);

    assertNoErrorsOrWarnings(source);
  }

  @Test
  void testValidSubSourceOnlyPower() {
    when(validationHelper.getPowerRange(any())).thenReturn(Optional.of(IntRangeUtil.valueOf("[0,300]")));
    final OffRoadMobileEmissionSource source = createSource(200, null, null, null);
    mockCategory(MockCategory.POWER);

    assertNoErrorsOrWarnings(source);
  }

  @Test
  void testInValidPowerRange() {
    final IntRange powerRange = IntRangeUtil.valueOf("(,100]");
    when(validationHelper.getPowerRange(any())).thenReturn(Optional.of(powerRange));
    final OffRoadMobileEmissionSource source = createSource(200, null, null, null);
    mockCategory(MockCategory.POWER);

    assertValidate(source, 1, 0);
    assertFirstError(ImaerExceptionReason.MOBILE_SOURCE_POWER_NOT_WITHIN_RANGE, List.of(SUB_SOURCE_DESCRIPTION, powerRange.toString(), "200"));
  }

  @Test
  void testValidSubSourceOnlyLiterFuel() {
    final OffRoadMobileEmissionSource source = createSource(null, 10_000, null, null);

    mockCategory(MockCategory.FUEL);
    assertNoErrorsOrWarnings(source);
  }

  @Test
  void testValidSubSourceOnlyOperatingHours() {
    final OffRoadMobileEmissionSource source = createSource(null, null, 3_000, null);

    mockCategory(MockCategory.HOURS);
    assertNoErrorsOrWarnings(source);
  }

  @Test
  void testValidSubSourceOnlyAdBlue() {
    final OffRoadMobileEmissionSource source = createSource(null, null, null, 500);

    mockCategory(MockCategory.ADBLUE);
    assertNoErrorsOrWarnings(source);
  }

  @Test
  void testValidSubSourceAllRequired() {
    final OffRoadMobileEmissionSource source = createSource(null, 10_000, 3_000, 500);

    mockCategory(MockCategory.FUEL, MockCategory.HOURS, MockCategory.ADBLUE);
    assertNoErrorsOrWarnings(source);
  }

  /**
   * Test if correct error is given when power value is missing.
   *
   * @param power Test both with and without fuel value, should have the same results as no emission factors for fuel present
   */
  @ParameterizedTest
  @CsvSource({",", "10_000"})
  void testSubSourceMissingPower(final Integer fuel) {
    final OffRoadMobileEmissionSource source = createSource(null, fuel, 3_000, 500);

    mockCategory(MockCategory.POWER);

    assertValidate(source, 1, 0);
    assertFirstError(ImaerExceptionReason.MOBILE_SOURCE_MISSING_POWER_OR_LITER_FUEL, List.of(SUB_SOURCE_DESCRIPTION));
  }

  /**
   * Test if correct error is given when fuel value is missing.
   *
   * @param power Test both with and without power value, should have the same results as no emission factors for power present
   */
  @ParameterizedTest
  @CsvSource({",", "200"})
  void testSubSourceMissingLiterFuel(final Integer power) {
    final OffRoadMobileEmissionSource source = createSource(power, null, 3_000, 500);

    mockCategory(MockCategory.FUEL, MockCategory.HOURS, MockCategory.ADBLUE);

    assertValidate(source, 1, 0);
    assertFirstError(ImaerExceptionReason.MOBILE_SOURCE_MISSING_POWER_OR_LITER_FUEL, List.of(SUB_SOURCE_DESCRIPTION));
  }

  @Test
  void testSubSourceMissingOperatingHours() {
    final OffRoadMobileEmissionSource source = createSource(null, 10_000, null, 500);

    mockCategory(MockCategory.FUEL, MockCategory.HOURS, MockCategory.ADBLUE);
    assertValidate(source, 1, 0);
    assertFirstError(ImaerExceptionReason.MOBILE_SOURCE_MISSING_OPERATING_HOURS, List.of(SUB_SOURCE_DESCRIPTION));
  }

  @Test
  void testSubSourceMissingLiterAdBlue() {
    final OffRoadMobileEmissionSource source = createSource(null, 10_000, 3_000, null);

    mockCategory(MockCategory.FUEL, MockCategory.HOURS, MockCategory.ADBLUE);
    assertValidate(source, 1, 0);
    assertFirstError(ImaerExceptionReason.MOBILE_SOURCE_MISSING_LITER_ADBLUE, List.of(SUB_SOURCE_DESCRIPTION));
  }

  @Test
  void testSubSourceMissingAll() {
    final OffRoadMobileEmissionSource source = createSource(null, null, null, null);

    mockCategory(MockCategory.POWER, MockCategory.FUEL, MockCategory.HOURS, MockCategory.ADBLUE);
    assertValidate(source, 3, 0);
  }

  @Test
  void testSubSourceTooMuchLiterAdBlue() {
    // Ratio mocked is 0.07, so 10% should trigger a warning.
    final OffRoadMobileEmissionSource source = createSource(null, 10_000, 3_000, 1_000);

    mockCategory(MockCategory.FUEL, MockCategory.HOURS, MockCategory.ADBLUE);
    assertValidate(source, 0, 1);
    assertAeriusException(ImaerExceptionReason.MOBILE_SOURCE_HIGH_ADBLUE_FUEL_RATIO, List.of(SUB_SOURCE_DESCRIPTION, "700.00", "1000"),
        warnings.get(0));
  }

  private static OffRoadMobileEmissionSource createSource(final Integer power, final Integer fuel, final Integer hours, final Integer adBlue) {
    final OffRoadMobileEmissionSource source = new OffRoadMobileEmissionSource();
    final StandardOffRoadMobileSource subSource = new StandardOffRoadMobileSource();

    source.setLabel("Source label");
    subSource.setDescription(SUB_SOURCE_DESCRIPTION);
    subSource.setOffRoadMobileSourceCode(CODE);
    subSource.setPower(power);
    subSource.setLiterFuelPerYear(fuel);
    subSource.setOperatingHoursPerYear(hours);
    subSource.setLiterAdBluePerYear(adBlue);
    source.getSubSources().add(subSource);
    return source;
  }

  private void mockCategory(final MockCategory... mockCategories) {
    final Set<MockCategory> mockCategoriesSet = Set.of(mockCategories);
    when(validationHelper.isValidOffRoadMobileSourceCode(CODE)).thenReturn(true);
    when(validationHelper.expectsPower(CODE)).thenReturn(mockCategoriesSet.contains(MockCategory.POWER));
    when(validationHelper.expectsLiterFuelPerYear(CODE)).thenReturn(mockCategoriesSet.contains(MockCategory.FUEL));
    when(validationHelper.expectsOperatingHoursPerYear(CODE)).thenReturn(mockCategoriesSet.contains(MockCategory.HOURS));
    when(validationHelper.expectsLiterAdBluePerYear(CODE)).thenReturn(mockCategoriesSet.contains(MockCategory.ADBLUE));
    lenient().when(validationHelper.getMaxAdBlueFuelRatio(CODE)).thenReturn(OptionalDouble.of(0.07));
  }

  private void assertNoErrorsOrWarnings(final OffRoadMobileEmissionSource source) {
    assertValidate(source, 0, 0);
  }

  private void assertValidate(final OffRoadMobileEmissionSource source, final int expectNrOfErrors, final int expectNrOfWarnings) {
    final boolean valid = validator.validate(source);

    if (expectNrOfErrors > 0) {
      assertFalse(valid, "Expected validator to return false");
      assertEquals(expectNrOfErrors, errors.size(), "Not the expected nr of errors");
    } else {
      assertTrue(valid, "Expected validator to return true");
      assertEquals(List.of(), errors, "Expected no errors");
    }
    assertEquals(expectNrOfWarnings, warnings.size(), "Not the expected nr of warnings");
  }

  private void assertFirstError(final ImaerExceptionReason expectedReason, final List<Object> expectedArguments) {
    assertAeriusException(expectedReason, expectedArguments, errors.get(0));
  }

  private static void assertAeriusException(final ImaerExceptionReason expectedReason, final List<Object> expectedArguments,
      final AeriusException actualException) {
    assertEquals(expectedReason, actualException.getReason(), "Not the expected reason in the exception.");
    assertArrayEquals(expectedArguments.toArray(), actualException.getArgs(), "Not the expected arguments");
  }
}
