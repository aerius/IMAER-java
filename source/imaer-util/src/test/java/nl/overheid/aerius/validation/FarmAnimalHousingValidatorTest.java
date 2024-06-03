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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.overheid.aerius.shared.domain.Substance;
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

@ExtendWith(MockitoExtension.class)
class FarmAnimalHousingValidatorTest {

  private static final String SOURCE_ID = "OurSourceId";
  private static final String HOUSING_CODE = "SomeStandardHouse";

  @Mock FarmAnimalHousingValidationHelper validationHelper;

  @Test
  void testValidCustomHousing() {
    final FarmAnimalHousingEmissionSource source = constructSource();
    final CustomFarmAnimalHousing subSource = new CustomFarmAnimalHousing();
    subSource.setFarmEmissionFactorType(FarmEmissionFactorType.PER_ANIMAL_PER_DAY);
    subSource.setNumberOfDays(5);
    subSource.getEmissionFactors().put(Substance.NH3, 1.2);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmAnimalHousingValidator validator = new FarmAnimalHousingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertValidCase(valid, errors, warnings);
  }

  @Test
  void testInvalidCustomHousingMissingEmissionFactorType() {
    final FarmAnimalHousingEmissionSource source = constructSource();
    final CustomFarmAnimalHousing subSource = new CustomFarmAnimalHousing();
    subSource.setNumberOfDays(5);
    subSource.getEmissionFactors().put(Substance.NH3, 1.2);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmAnimalHousingValidator validator = new FarmAnimalHousingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertInvalidCase(valid, errors, warnings, ImaerExceptionReason.GML_UNKNOWN_FARM_EMISSION_FACTOR_TYPE, new Object[] {
        SOURCE_ID, "null"
    });
  }

  @Test
  void testInvalidCustomHousingMissingDays() {
    final FarmAnimalHousingEmissionSource source = constructSource();
    final CustomFarmAnimalHousing subSource = new CustomFarmAnimalHousing();
    subSource.setFarmEmissionFactorType(FarmEmissionFactorType.PER_ANIMAL_PER_DAY);
    subSource.getEmissionFactors().put(Substance.NH3, 1.2);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmAnimalHousingValidator validator = new FarmAnimalHousingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertInvalidCase(valid, errors, warnings, ImaerExceptionReason.MISSING_NUMBER_OF_DAYS, new Object[] {
        SOURCE_ID
    });
  }

  @Test
  void testInvalidCustomHousingMissingEmissionFactors() {
    final FarmAnimalHousingEmissionSource source = constructSource();
    final CustomFarmAnimalHousing subSource = new CustomFarmAnimalHousing();
    subSource.setFarmEmissionFactorType(FarmEmissionFactorType.PER_ANIMAL_PER_DAY);
    subSource.setNumberOfDays(5);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmAnimalHousingValidator validator = new FarmAnimalHousingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertInvalidCase(valid, errors, warnings, ImaerExceptionReason.GML_INCORRECT_CUSTOM_FACTORS, new Object[] {
        SOURCE_ID
    });
  }

  @Test
  void testInvalidCustomHousingNegativeEmissionFactors() {
    final FarmAnimalHousingEmissionSource source = constructSource();
    final CustomFarmAnimalHousing subSource = new CustomFarmAnimalHousing();
    subSource.setFarmEmissionFactorType(FarmEmissionFactorType.PER_ANIMAL_PER_DAY);
    subSource.setNumberOfDays(5);
    subSource.getEmissionFactors().put(Substance.NH3, -1.2);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmAnimalHousingValidator validator = new FarmAnimalHousingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertInvalidCase(valid, errors, warnings, ImaerExceptionReason.GML_INCORRECT_CUSTOM_FACTORS, new Object[] {
        SOURCE_ID
    });
  }

  @Test
  void testValidStandardHousing() {
    final FarmAnimalHousingEmissionSource source = constructSource();
    final StandardFarmAnimalHousing subSource = mockValidStandardHousing("SomeAnimalCode");
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmAnimalHousingValidator validator = new FarmAnimalHousingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertValidCase(valid, errors, warnings);
  }

  @Test
  void testInvalidStandardHousingIncorrectAnimalCode() {
    final FarmAnimalHousingEmissionSource source = constructSource();
    final StandardFarmAnimalHousing subSource = new StandardFarmAnimalHousing();
    final String animalCode = "SomeAnimalCode";
    subSource.setAnimalTypeCode(animalCode);
    mockHousingCategory(HOUSING_CODE, animalCode);
    subSource.setAnimalHousingCode(HOUSING_CODE);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmAnimalHousingValidator validator = new FarmAnimalHousingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertInvalidCase(valid, errors, warnings, ImaerExceptionReason.GML_UNKNOWN_ANIMAL_HOUSING_CODE, new Object[] {
        SOURCE_ID, animalCode
    });
  }

  @Test
  void testInvalidStandardHousingIncorrectHousingCode() {
    final FarmAnimalHousingEmissionSource source = constructSource();
    final StandardFarmAnimalHousing subSource = new StandardFarmAnimalHousing();
    final String animalCode = "SomeAnimalCode";
    mockAnimalCategory(animalCode);
    subSource.setAnimalTypeCode(animalCode);
    subSource.setAnimalHousingCode(HOUSING_CODE);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmAnimalHousingValidator validator = new FarmAnimalHousingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertInvalidCase(valid, errors, warnings, ImaerExceptionReason.GML_UNKNOWN_ANIMAL_HOUSING_CODE, new Object[] {
        SOURCE_ID, HOUSING_CODE
    });
  }

  @Test
  void testInvalidStandardHousingIncorrectCombination() {
    final FarmAnimalHousingEmissionSource source = constructSource();
    final StandardFarmAnimalHousing subSource = new StandardFarmAnimalHousing();
    final String animalCode = "SomeAnimalCode";
    mockAnimalCategory(animalCode);
    subSource.setAnimalTypeCode(animalCode);
    mockHousingCategory(HOUSING_CODE, "otherAnimalCode");
    subSource.setAnimalHousingCode(HOUSING_CODE);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmAnimalHousingValidator validator = new FarmAnimalHousingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertInvalidCase(valid, errors, warnings, ImaerExceptionReason.GML_UNSUPPORTED_ANIMAL_HOUSING_COMBINATION, new Object[] {
        SOURCE_ID, animalCode, HOUSING_CODE
    });
  }

  @Test
  void testValidStandardAdditionalSystem() {
    final FarmAnimalHousingEmissionSource source = constructSource();
    final String animalCode = "SomeAnimalCode";
    final StandardFarmAnimalHousing subSource = mockValidStandardHousing(animalCode);

    final String additionalSystemCode = "SomeAdditionalCode";
    final StandardAdditionalHousingSystem additionalSystem = new StandardAdditionalHousingSystem();
    mockAdditionalSystemCategory(additionalSystemCode, HOUSING_CODE);
    additionalSystem.setAdditionalSystemCode(additionalSystemCode);
    subSource.getAdditionalSystems().add(additionalSystem);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmAnimalHousingValidator validator = new FarmAnimalHousingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertValidCase(valid, errors, warnings);
  }

  @Test
  void testInvalidStandardAdditionalSystemIncorrectCode() {
    final FarmAnimalHousingEmissionSource source = constructSource();
    final String animalCode = "SomeAnimalCode";
    final StandardFarmAnimalHousing subSource = mockValidStandardHousing(animalCode);

    final String additionalSystemCode = "SomeAdditionalCode";
    final StandardAdditionalHousingSystem additionalSystem = new StandardAdditionalHousingSystem();
    additionalSystem.setAdditionalSystemCode(additionalSystemCode);
    subSource.getAdditionalSystems().add(additionalSystem);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmAnimalHousingValidator validator = new FarmAnimalHousingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertInvalidCase(valid, errors, warnings, ImaerExceptionReason.GML_UNKNOWN_ANIMAL_HOUSING_CODE, new Object[] {
        SOURCE_ID, additionalSystemCode
    });
  }

  @Test
  void testInvalidStandardAdditionalSystemIncorrectCombination() {
    final FarmAnimalHousingEmissionSource source = constructSource();
    final String animalCode = "SomeAnimalCode";
    final StandardFarmAnimalHousing subSource = mockValidStandardHousing(animalCode);

    final String additionalSystemCode = "SomeAdditionalCode";
    mockAdditionalSystemCategory(additionalSystemCode, "OtherHousingCode");
    final StandardAdditionalHousingSystem additionalSystem = new StandardAdditionalHousingSystem();
    additionalSystem.setAdditionalSystemCode(additionalSystemCode);
    subSource.getAdditionalSystems().add(additionalSystem);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmAnimalHousingValidator validator = new FarmAnimalHousingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertInvalidCase(valid, errors, warnings, ImaerExceptionReason.GML_UNSUPPORTED_ANIMAL_HOUSING_COMBINATION, new Object[] {
        SOURCE_ID, HOUSING_CODE, additionalSystemCode
    });
  }

  private StandardFarmAnimalHousing mockValidStandardHousing(final String animalCode) {
    final StandardFarmAnimalHousing subSource = new StandardFarmAnimalHousing();
    mockAnimalCategory(animalCode);
    subSource.setAnimalTypeCode(animalCode);
    mockHousingCategory(HOUSING_CODE, animalCode);
    subSource.setAnimalHousingCode(HOUSING_CODE);
    return subSource;
  }

  @Test
  void testValidStandardAnimalHousingWithDays() {
    final FarmAnimalHousingEmissionSource source = constructSource();
    final StandardFarmAnimalHousing subSource = new StandardFarmAnimalHousing();
    final String animalCode = "SomeAnimalCode";
    mockAnimalCategory(animalCode);
    subSource.setAnimalTypeCode(animalCode);
    mockHousingCategory(HOUSING_CODE, animalCode);
    when(validationHelper.getAnimalHousingEmissionFactorType(HOUSING_CODE)).thenReturn(FarmEmissionFactorType.PER_ANIMAL_PER_DAY);
    subSource.setAnimalHousingCode(HOUSING_CODE);
    subSource.setNumberOfDays(40);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmAnimalHousingValidator validator = new FarmAnimalHousingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertValidCase(valid, errors, warnings);
  }

  @Test
  void testInvalidStandardAnimalHousingMissingDays() {
    final FarmAnimalHousingEmissionSource source = constructSource();
    final StandardFarmAnimalHousing subSource = new StandardFarmAnimalHousing();
    final String animalCode = "SomeAnimalCode";
    mockAnimalCategory(animalCode);
    subSource.setAnimalTypeCode(animalCode);
    mockHousingCategory(HOUSING_CODE, animalCode);
    when(validationHelper.getAnimalHousingEmissionFactorType(HOUSING_CODE)).thenReturn(FarmEmissionFactorType.PER_ANIMAL_PER_DAY);
    subSource.setAnimalHousingCode(HOUSING_CODE);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmAnimalHousingValidator validator = new FarmAnimalHousingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertInvalidCase(valid, errors, warnings, ImaerExceptionReason.MISSING_NUMBER_OF_DAYS, new Object[] {
        SOURCE_ID
    });
  }

  @Test
  void testValidCustomAdditionalSystem() {
    final FarmAnimalHousingEmissionSource source = constructSource();
    final String animalCode = "SomeAnimalCode";
    final StandardFarmAnimalHousing subSource = mockValidStandardHousing(animalCode);

    final CustomAdditionalHousingSystem additionalSystem = new CustomAdditionalHousingSystem();
    additionalSystem.getEmissionReductionFactors().put(Substance.NH3, 0.7);
    subSource.getAdditionalSystems().add(additionalSystem);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmAnimalHousingValidator validator = new FarmAnimalHousingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertValidCase(valid, errors, warnings);
  }

  @Test
  void testInvalidCustomAdditionalSystemNoFactors() {
    final FarmAnimalHousingEmissionSource source = constructSource();
    final String animalCode = "SomeAnimalCode";
    final StandardFarmAnimalHousing subSource = mockValidStandardHousing(animalCode);

    final CustomAdditionalHousingSystem additionalSystem = new CustomAdditionalHousingSystem();
    subSource.getAdditionalSystems().add(additionalSystem);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmAnimalHousingValidator validator = new FarmAnimalHousingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertInvalidCase(valid, errors, warnings, ImaerExceptionReason.GML_INCORRECT_CUSTOM_FACTORS, new Object[] {
        SOURCE_ID
    });
  }

  @Test
  void testInvalidCustomAdditionalSystemNegativeFactors() {
    final FarmAnimalHousingEmissionSource source = constructSource();
    final String animalCode = "SomeAnimalCode";
    final StandardFarmAnimalHousing subSource = mockValidStandardHousing(animalCode);

    final CustomAdditionalHousingSystem additionalSystem = new CustomAdditionalHousingSystem();
    additionalSystem.getEmissionReductionFactors().put(Substance.NH3, -0.7);
    subSource.getAdditionalSystems().add(additionalSystem);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmAnimalHousingValidator validator = new FarmAnimalHousingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertInvalidCase(valid, errors, warnings, ImaerExceptionReason.GML_INCORRECT_CUSTOM_FACTORS, new Object[] {
        SOURCE_ID
    });
  }

  @Test
  void testInvalidHousingUnknown() {
    final FarmAnimalHousingEmissionSource source = constructSource();
    source.getSubSources().add(new FarmAnimalHousing() {
      private static final long serialVersionUID = 1L;
    });

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmAnimalHousingValidator validator = new FarmAnimalHousingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertInvalidCase(valid, errors, warnings, ImaerExceptionReason.INTERNAL_ERROR, new Object[] {
        "Unexpected animal housing implementation"
    });
  }

  @Test
  void testInvalidAdditionalHousingUnknown() {
    final FarmAnimalHousingEmissionSource source = constructSource();
    final StandardFarmAnimalHousing subSource = mockValidStandardHousing("SomeAnimalCode");
    subSource.getAdditionalSystems().add(new AdditionalHousingSystem() {
      private static final long serialVersionUID = 1L;
    });
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final FarmAnimalHousingValidator validator = new FarmAnimalHousingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertInvalidCase(valid, errors, warnings, ImaerExceptionReason.INTERNAL_ERROR, new Object[] {
        "Unexpected additional animal housing system implementation"
    });
  }

  private FarmAnimalHousingEmissionSource constructSource() {
    final FarmAnimalHousingEmissionSource source = new FarmAnimalHousingEmissionSource();
    source.setGmlId(SOURCE_ID);
    source.setLabel("Source label");
    return source;
  }

  private void mockAnimalCategory(final String code) {
    when(validationHelper.isValidFarmAnimalCode(code)).thenReturn(true);
  }

  private void mockHousingCategory(final String code, final String animalCode) {
    lenient().when(validationHelper.isValidFarmAnimalHousingCode(code)).thenReturn(true);
    lenient().when(validationHelper.isValidFarmAnimalHousingCombination(animalCode, code)).thenReturn(true);
    lenient().when(validationHelper.getAnimalHousingEmissionFactorType(code)).thenReturn(FarmEmissionFactorType.PER_ANIMAL_PER_YEAR);
  }

  private void mockAdditionalSystemCategory(final String code, final String housingCode) {
    lenient().when(validationHelper.isValidFarmAdditionalSystemCode(code)).thenReturn(true);
    lenient().when(validationHelper.isValidFarmAdditionalSystemCombination(housingCode, code)).thenReturn(true);
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

}
