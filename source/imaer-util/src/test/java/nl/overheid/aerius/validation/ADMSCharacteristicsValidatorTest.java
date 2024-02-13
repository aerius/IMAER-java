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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import nl.overheid.aerius.shared.domain.v2.characteristics.ADMSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.characteristics.adms.ADMSLimits;
import nl.overheid.aerius.shared.domain.v2.characteristics.adms.SourceType;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
class ADMSCharacteristicsValidatorTest {

  private static final String SOURCE_ID = "SomeSourceId";

  @Test
  void testValidADMSCharacteristics() {
    final ADMSSourceCharacteristics characteristics = new ADMSSourceCharacteristics();
    characteristics.setSourceType(SourceType.POINT);
    characteristics.setSpecificHeatCapacity(30.0);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final ADMSCharacteristicsValidator validator = new ADMSCharacteristicsValidator(errors, warnings, SOURCE_ID);

    final boolean valid = validator.validate(characteristics);

    assertTrue(valid, "Valid test case");
    assertEquals(List.of(), errors, "No errors");
    assertEquals(List.of(), warnings, "No warnings");
  }

  @ParameterizedTest
  @MethodSource("casesForADMSHeatCapacity")
  void testADMSHeatCapacityLimits(final SourceType sourceType, final double heatCapacity, final boolean expectedValid) {
    final ADMSSourceCharacteristics characteristics = new ADMSSourceCharacteristics();
    characteristics.setSourceType(sourceType);
    characteristics.setSpecificHeatCapacity(heatCapacity);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final ADMSCharacteristicsValidator validator = new ADMSCharacteristicsValidator(errors, warnings, SOURCE_ID);

    final boolean valid = validator.validate(characteristics);

    if (expectedValid) {
      assertTrue(valid, "Valid test case");
      assertEquals(List.of(), errors, "No errors");
      assertEquals(List.of(), warnings, "No warnings");
    } else {
      assertFalse(valid, "Invalid test case");
      assertEquals(1, errors.size(), "Number of errors");
      assertEquals(ImaerExceptionReason.HEAT_CAPACITY_OUT_OF_RANGE, errors.get(0).getReason(), "Error reason");
      assertArrayEquals(new Object[] {
          SOURCE_ID, String.valueOf(heatCapacity), "1", "100000"
      }, errors.get(0).getArgs(), "Arguments");
    }
  }

  private static Stream<Arguments> casesForADMSHeatCapacity() {
    return Stream.of(
        Arguments.of(SourceType.POINT, ADMSLimits.SOURCE_SPECIFIC_HEAT_CAPACITY_MINIMUM - 0.001, false),
        Arguments.of(SourceType.POINT, ADMSLimits.SOURCE_SPECIFIC_HEAT_CAPACITY_MINIMUM, true),
        Arguments.of(SourceType.POINT, ADMSLimits.SOURCE_SPECIFIC_HEAT_CAPACITY_DEFAULT, true),
        Arguments.of(SourceType.POINT, ADMSLimits.SOURCE_SPECIFIC_HEAT_CAPACITY_MAXIMUM, true),
        Arguments.of(SourceType.POINT, ADMSLimits.SOURCE_SPECIFIC_HEAT_CAPACITY_MAXIMUM + 0.001, false),
        // volume and road don't use heat capacity, rest does
        Arguments.of(SourceType.LINE, ADMSLimits.SOURCE_SPECIFIC_HEAT_CAPACITY_MINIMUM - 0.001, false),
        Arguments.of(SourceType.AREA, ADMSLimits.SOURCE_SPECIFIC_HEAT_CAPACITY_MINIMUM - 0.001, false),
        Arguments.of(SourceType.JET, ADMSLimits.SOURCE_SPECIFIC_HEAT_CAPACITY_MINIMUM - 0.001, false),
        Arguments.of(SourceType.VOLUME, ADMSLimits.SOURCE_SPECIFIC_HEAT_CAPACITY_MINIMUM - 0.001, true),
        Arguments.of(SourceType.ROAD, ADMSLimits.SOURCE_SPECIFIC_HEAT_CAPACITY_MINIMUM - 0.001, true));
  }

  @ParameterizedTest
  @MethodSource("casesForADMSVerticalDimensionHeight")
  void testValidateADMSVerticalDimensionHeight(final SourceType sourceType, final double height, final double verticalDimension,
      final boolean warning) {
    final ADMSSourceCharacteristics characteristics = new ADMSSourceCharacteristics();
    characteristics.setSourceType(sourceType);
    characteristics.setHeight(height);
    characteristics.setVerticalDimension(verticalDimension);
    characteristics.setSpecificHeatCapacity(10.0); // Set valid heat capacity to not trigger heat capacity validation

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final ADMSCharacteristicsValidator validator = new ADMSCharacteristicsValidator(errors, warnings, SOURCE_ID);

    assertTrue(validator.validate(characteristics), "Should not give validation error on height check");
    if (warning) {
      assertEquals(1, warnings.size(), "Number of warning");
      assertEquals(ImaerExceptionReason.SOURCE_VOLUME_FLOATING, warnings.get(0).getReason(), "Expected source volume floating warning.");
    } else {
      assertTrue(warnings.isEmpty(), "Didn't expect any warnings");
    }
  }

  private static Stream<Arguments> casesForADMSVerticalDimensionHeight() {
    return Stream.of(
        //           source type, height, vertical dimension, warning
        Arguments.of(SourceType.POINT, 10.0, 10.0, false),
        Arguments.of(SourceType.VOLUME, 1.0, 20.0, false),
        Arguments.of(SourceType.VOLUME, 2.0, 4.0, false),
        Arguments.of(SourceType.VOLUME, 10.0, 2.0, true));
  }
}
