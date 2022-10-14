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

import nl.overheid.aerius.shared.domain.v2.source.FarmLodgingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.farm.AdditionalLodgingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.CustomFarmLodging;
import nl.overheid.aerius.shared.domain.v2.source.farm.LodgingFodderMeasure;
import nl.overheid.aerius.shared.domain.v2.source.farm.ReductiveLodgingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.StandardFarmLodging;
import nl.overheid.aerius.shared.emissions.FarmEmissionFactorType;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

@ExtendWith(MockitoExtension.class)
class FarmLodgingValidatorTest {

  private static final String SOURCE_ID = "OurSourceId";

  @Mock FarmLodgingValidationHelper validationHelper;

  @Test
  void testValidSubSourceCustom() {
    final FarmLodgingEmissionSource source = constructSource();
    final CustomFarmLodging subSource = new CustomFarmLodging();
    subSource.setFarmEmissionFactorType(FarmEmissionFactorType.PER_ANIMAL_PER_DAY);
    subSource.setNumberOfDays(5);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmLodgingValidator validator = new FarmLodgingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertTrue(valid, "Valid test case");
    assertTrue(errors.isEmpty(), "No errors");
    assertTrue(warnings.isEmpty(), "No warnings");
  }

  @Test
  void testInvalidSubSourceCustom() {
    final FarmLodgingEmissionSource source = constructSource();
    final CustomFarmLodging subSource = new CustomFarmLodging();
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmLodgingValidator validator = new FarmLodgingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertFalse(valid, "Invalid test case");
    assertEquals(1, errors.size(), "Number of errors");
    assertEquals(ImaerExceptionReason.GML_UNKNOWN_FARM_EMISSION_FACTOR_TYPE, errors.get(0).getReason(), "Error reason");
    assertArrayEquals(new Object[] {
        SOURCE_ID, "null"
    }, errors.get(0).getArgs(), "Arguments");
    assertTrue(warnings.isEmpty(), "No warnings");
  }

  @Test
  void testInvalidSubSourceCustomWithoutDays() {
    final FarmLodgingEmissionSource source = constructSource();
    final CustomFarmLodging subSource = new CustomFarmLodging();
    subSource.setFarmEmissionFactorType(FarmEmissionFactorType.PER_ANIMAL_PER_DAY);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmLodgingValidator validator = new FarmLodgingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertFalse(valid, "Invalid test case");
    assertEquals(1, errors.size(), "Number of errors");
    assertEquals(ImaerExceptionReason.MISSING_NUMBER_OF_DAYS, errors.get(0).getReason(), "Error reason");
    assertArrayEquals(new Object[] {
        SOURCE_ID
    }, errors.get(0).getArgs(), "Arguments");
    assertTrue(warnings.isEmpty(), "No warnings");
  }

  @Test
  void testValidStandardLodgingCode() {
    final FarmLodgingEmissionSource source = constructSource();
    final StandardFarmLodging subSource = new StandardFarmLodging();
    final String lodgingCode = "SomeStandardLodging";
    mockLodgingCategory(lodgingCode);
    subSource.setFarmLodgingCode(lodgingCode);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmLodgingValidator validator = new FarmLodgingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertTrue(valid, "Valid test case");
    assertTrue(errors.isEmpty(), "No errors");
    assertTrue(warnings.isEmpty(), "No warnings");
  }

  @Test
  void testInvalidStandardLodgingCode() {
    final FarmLodgingEmissionSource source = constructSource();
    final StandardFarmLodging subSource = new StandardFarmLodging();
    final String lodgingCode = "SomeStandardLodging";
    subSource.setFarmLodgingCode(lodgingCode);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmLodgingValidator validator = new FarmLodgingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertFalse(valid, "Invalid test case");
    assertEquals(1, errors.size(), "Number of errors");
    assertEquals(ImaerExceptionReason.GML_UNKNOWN_RAV_CODE, errors.get(0).getReason(), "Error reason");
    assertArrayEquals(new Object[] {
        SOURCE_ID, lodgingCode
    }, errors.get(0).getArgs(), "Arguments");
    assertTrue(warnings.isEmpty(), "No warnings");
  }

  @Test
  void testValidAdditionalCode() {
    final FarmLodgingEmissionSource source = constructSource();
    final StandardFarmLodging subSource = new StandardFarmLodging();
    final String lodgingCode = "SomeStandardLodging";
    mockLodgingCategory(lodgingCode);
    subSource.setFarmLodgingCode(lodgingCode);
    source.getSubSources().add(subSource);
    final String additionalSystemCode = "SomeAdditionalCode";
    final AdditionalLodgingSystem additionalSystem = new AdditionalLodgingSystem();
    mockAdditionalSystemCategory(additionalSystemCode);
    additionalSystem.setLodgingSystemCode(additionalSystemCode);
    subSource.getAdditionalLodgingSystems().add(additionalSystem);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmLodgingValidator validator = new FarmLodgingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertTrue(valid, "Valid test case");
    assertTrue(errors.isEmpty(), "No errors");
    assertTrue(warnings.isEmpty(), "No warnings");
  }

  @Test
  void testInvalidAdditionalCode() {
    final FarmLodgingEmissionSource source = constructSource();
    final StandardFarmLodging subSource = new StandardFarmLodging();
    final String lodgingCode = "SomeStandardLodging";
    mockLodgingCategory(lodgingCode);
    subSource.setFarmLodgingCode(lodgingCode);
    source.getSubSources().add(subSource);
    final String additionalSystemCode = "SomeAdditionalCode";
    final AdditionalLodgingSystem additionalSystem = new AdditionalLodgingSystem();
    additionalSystem.setLodgingSystemCode(additionalSystemCode);
    subSource.getAdditionalLodgingSystems().add(additionalSystem);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmLodgingValidator validator = new FarmLodgingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertFalse(valid, "Invalid test case");
    assertEquals(1, errors.size(), "Number of errors");
    assertEquals(ImaerExceptionReason.GML_UNKNOWN_RAV_CODE, errors.get(0).getReason(), "Error reason");
    assertArrayEquals(new Object[] {
        SOURCE_ID, additionalSystemCode
    }, errors.get(0).getArgs(), "Arguments");
    assertTrue(warnings.isEmpty(), "No warnings");
  }

  @Test
  void testValidReductiveCode() {
    final FarmLodgingEmissionSource source = constructSource();
    final StandardFarmLodging subSource = new StandardFarmLodging();
    final String lodgingCode = "SomeStandardLodging";
    mockLodgingCategory(lodgingCode);
    subSource.setFarmLodgingCode(lodgingCode);
    source.getSubSources().add(subSource);
    final String reductiveSystemCode = "SomeReductiveCode";
    final ReductiveLodgingSystem reductiveSystem = new ReductiveLodgingSystem();
    mockReductiveSystemCategory(reductiveSystemCode);
    reductiveSystem.setLodgingSystemCode(reductiveSystemCode);
    subSource.getReductiveLodgingSystems().add(reductiveSystem);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmLodgingValidator validator = new FarmLodgingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertTrue(valid, "Valid test case");
    assertTrue(errors.isEmpty(), "No errors");
    assertTrue(warnings.isEmpty(), "No warnings");
  }

  @Test
  void testInvalidReductiveCode() {
    final FarmLodgingEmissionSource source = constructSource();
    final StandardFarmLodging subSource = new StandardFarmLodging();
    final String lodgingCode = "SomeStandardLodging";
    mockLodgingCategory(lodgingCode);
    subSource.setFarmLodgingCode(lodgingCode);
    source.getSubSources().add(subSource);
    final String reductiveSystemCode = "SomeReductiveCode";
    final ReductiveLodgingSystem reductiveSystem = new ReductiveLodgingSystem();
    reductiveSystem.setLodgingSystemCode(reductiveSystemCode);
    subSource.getReductiveLodgingSystems().add(reductiveSystem);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmLodgingValidator validator = new FarmLodgingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertFalse(valid, "Invalid test case");
    assertEquals(1, errors.size(), "Number of errors");
    assertEquals(ImaerExceptionReason.GML_UNKNOWN_RAV_CODE, errors.get(0).getReason(), "Error reason");
    assertArrayEquals(new Object[] {
        SOURCE_ID, reductiveSystemCode
    }, errors.get(0).getArgs(), "Arguments");
    assertTrue(warnings.isEmpty(), "No warnings");
  }

  @Test
  void testValidFodderMeasureCode() {
    final FarmLodgingEmissionSource source = constructSource();
    final StandardFarmLodging subSource = new StandardFarmLodging();
    final String lodgingCode = "SomeStandardLodging";
    mockLodgingCategory(lodgingCode);
    subSource.setFarmLodgingCode(lodgingCode);
    source.getSubSources().add(subSource);
    final String fodderMeasureCode = "SomeFodderMeasureCode";
    final LodgingFodderMeasure fodderMeasure = new LodgingFodderMeasure();
    mockFodderMeasure(fodderMeasureCode);
    mockFodderMeasureCanApplyTo(fodderMeasureCode, lodgingCode);
    fodderMeasure.setFodderMeasureCode(fodderMeasureCode);
    subSource.getFodderMeasures().add(fodderMeasure);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmLodgingValidator validator = new FarmLodgingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertTrue(valid, "Valid test case");
    assertTrue(errors.isEmpty(), "No errors");
    assertTrue(warnings.isEmpty(), "No warnings");
  }

  @Test
  void testInvalidFodderMeasureCode() {
    final FarmLodgingEmissionSource source = constructSource();
    final StandardFarmLodging subSource = new StandardFarmLodging();
    final String lodgingCode = "SomeStandardLodging";
    mockLodgingCategory(lodgingCode);
    subSource.setFarmLodgingCode(lodgingCode);
    source.getSubSources().add(subSource);
    final String fodderMeasureCode = "SomeFodderMeasureCode";
    final LodgingFodderMeasure fodderMeasure = new LodgingFodderMeasure();
    fodderMeasure.setFodderMeasureCode(fodderMeasureCode);
    subSource.getFodderMeasures().add(fodderMeasure);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmLodgingValidator validator = new FarmLodgingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertFalse(valid, "Invalid test case");
    assertEquals(1, errors.size(), "Number of errors");
    assertEquals(ImaerExceptionReason.GML_UNKNOWN_PAS_MEASURE_CODE, errors.get(0).getReason(), "Error reason");
    assertArrayEquals(new Object[] {
        SOURCE_ID, fodderMeasureCode
    }, errors.get(0).getArgs(), "Arguments");
    assertTrue(warnings.isEmpty(), "No warnings");
  }

  @Test
  void testValidFodderMeasureCodeCantApply() {
    final FarmLodgingEmissionSource source = constructSource();
    final StandardFarmLodging subSource = new StandardFarmLodging();
    final String lodgingCode = "SomeStandardLodging";
    mockLodgingCategory(lodgingCode);
    subSource.setFarmLodgingCode(lodgingCode);
    source.getSubSources().add(subSource);
    final String fodderMeasureCode = "SomeFodderMeasureCode";
    final LodgingFodderMeasure fodderMeasure = new LodgingFodderMeasure();
    mockFodderMeasure(fodderMeasureCode);
    fodderMeasure.setFodderMeasureCode(fodderMeasureCode);
    subSource.getFodderMeasures().add(fodderMeasure);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmLodgingValidator validator = new FarmLodgingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertFalse(valid, "Invalid test case");
    assertEquals(1, errors.size(), "Number of errors");
    assertEquals(ImaerExceptionReason.GML_INVALID_PAS_MEASURE_CATEGORY, errors.get(0).getReason(), "Error reason");
    assertArrayEquals(new Object[] {
        SOURCE_ID, fodderMeasureCode, lodgingCode
    }, errors.get(0).getArgs(), "Arguments");
    assertTrue(warnings.isEmpty(), "No warnings");
  }

  @Test
  void testValidStandardLodgingCodeWithDays() {
    final FarmLodgingEmissionSource source = constructSource();
    final StandardFarmLodging subSource = new StandardFarmLodging();
    final String lodgingCode = "SomeStandardLodging";
    mockLodgingCategory(lodgingCode);
    when(validationHelper.getLodgingEmissionFactorType(lodgingCode)).thenReturn(FarmEmissionFactorType.PER_ANIMAL_PER_DAY);

    subSource.setFarmLodgingCode(lodgingCode);
    subSource.setNumberOfDays(40);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmLodgingValidator validator = new FarmLodgingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertTrue(valid, "Valid test case");
    assertTrue(errors.isEmpty(), "No errors");
    assertTrue(warnings.isEmpty(), "No warnings");
  }

  @Test
  void testInvalidStandardLodgingCodeWithDays() {
    final FarmLodgingEmissionSource source = constructSource();
    final StandardFarmLodging subSource = new StandardFarmLodging();
    final String lodgingCode = "SomeStandardLodging";
    mockLodgingCategory(lodgingCode);
    when(validationHelper.getLodgingEmissionFactorType(lodgingCode)).thenReturn(FarmEmissionFactorType.PER_ANIMAL_PER_DAY);

    subSource.setFarmLodgingCode(lodgingCode);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmLodgingValidator validator = new FarmLodgingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertFalse(valid, "Invalid test case");
    assertEquals(1, errors.size(), "Number of errors");
    assertEquals(ImaerExceptionReason.MISSING_NUMBER_OF_DAYS, errors.get(0).getReason(), "Error reason");
    assertArrayEquals(new Object[] {
        SOURCE_ID
    }, errors.get(0).getArgs(), "Arguments");
    assertTrue(warnings.isEmpty(), "No warnings");
  }

  private FarmLodgingEmissionSource constructSource() {
    final FarmLodgingEmissionSource source = new FarmLodgingEmissionSource();
    source.setGmlId(SOURCE_ID);
    source.setLabel("Source label");
    return source;
  }

  private void mockLodgingCategory(final String code) {
    when(validationHelper.isValidFarmLodgingCode(code)).thenReturn(true);
    when(validationHelper.getLodgingEmissionFactorType(code)).thenReturn(FarmEmissionFactorType.PER_ANIMAL_PER_YEAR);
  }

  private void mockAdditionalSystemCategory(final String code) {
    when(validationHelper.isValidFarmLodgingAdditionalSystemCode(code)).thenReturn(true);
  }

  private void mockReductiveSystemCategory(final String code) {
    when(validationHelper.isValidFarmLodgingReductiveSystemCode(code)).thenReturn(true);
  }

  private void mockFodderMeasure(final String code) {
    when(validationHelper.isValidFarmLodgingFodderMeasureCode(code)).thenReturn(true);
  }

  private void mockFodderMeasureCanApplyTo(final String fodderCode, final String lodgingCode) {
    when(validationHelper.canFodderApplyToLodging(fodderCode, lodgingCode)).thenReturn(true);
  }

}
