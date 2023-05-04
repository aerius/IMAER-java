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

import nl.overheid.aerius.shared.domain.ops.OPSLimits;
import nl.overheid.aerius.shared.domain.v2.characteristics.HeatContentType;
import nl.overheid.aerius.shared.domain.v2.characteristics.OPSSourceCharacteristics;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
class OPSCharacteristicsValidatorTest {

  private static final String SOURCE_ID = "SomeSourceId";

  @Test
  void testValidOPSCharacteristics() {
    final OPSSourceCharacteristics characteristics = new OPSSourceCharacteristics();
    characteristics.setHeatContentType(HeatContentType.FORCED);
    characteristics.setHeatContent(30.0);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final OPSCharacteristicsValidator validator = new OPSCharacteristicsValidator(errors, warnings, SOURCE_ID);

    final boolean valid = validator.validate(characteristics);

    assertTrue(valid, "Valid test case");
    assertTrue(errors.isEmpty(), "No errors");
    assertTrue(warnings.isEmpty(), "No warnings");
  }

  @Test
  void testOPSMissingHeatContentType() {
    final OPSSourceCharacteristics characteristics = new OPSSourceCharacteristics();
    characteristics.setHeatContentType(null);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final OPSCharacteristicsValidator validator = new OPSCharacteristicsValidator(errors, warnings, SOURCE_ID);

    final boolean valid = validator.validate(characteristics);

    assertFalse(valid, "Invalid test case");
    assertEquals(1, errors.size(), "Number of errors");
    assertEquals(ImaerExceptionReason.MISSING_HEAT_CONTENT, errors.get(0).getReason(), "Error reason");
    assertArrayEquals(new Object[] {
        SOURCE_ID
    }, errors.get(0).getArgs(), "Arguments");
  }

  @Test
  void testOPSMissingHeatContent() {
    final OPSSourceCharacteristics characteristics = new OPSSourceCharacteristics();
    characteristics.setHeatContentType(HeatContentType.FORCED);
    characteristics.setHeatContent(null);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final OPSCharacteristicsValidator validator = new OPSCharacteristicsValidator(errors, warnings, SOURCE_ID);

    final boolean valid = validator.validate(characteristics);

    assertFalse(valid, "Invalid test case");
    assertEquals(1, errors.size(), "Number of errors");
    assertEquals(ImaerExceptionReason.MISSING_HEAT_CONTENT, errors.get(0).getReason(), "Error reason");
    assertArrayEquals(new Object[] {
        SOURCE_ID
    }, errors.get(0).getArgs(), "Arguments");
  }

  @ParameterizedTest
  @MethodSource("casesForOPSHeatContent")
  void testOPSHeatContentLimits(final double heatContent, final boolean expectedValid) {
    final OPSSourceCharacteristics characteristics = new OPSSourceCharacteristics();
    characteristics.setHeatContentType(HeatContentType.FORCED);
    characteristics.setHeatContent(heatContent);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final OPSCharacteristicsValidator validator = new OPSCharacteristicsValidator(errors, warnings, SOURCE_ID);

    final boolean valid = validator.validate(characteristics);

    if (expectedValid) {
      assertTrue(valid, "Valid test case");
      assertTrue(errors.isEmpty(), "No errors");
      assertTrue(warnings.isEmpty(), "No warnings");
    } else {
      assertFalse(valid, "Invalid test case");
      assertEquals(1, errors.size(), "Number of errors");
      assertEquals(ImaerExceptionReason.HEAT_CONTENT_OUT_OF_RANGE, errors.get(0).getReason(), "Error reason");
      assertArrayEquals(new Object[] {
          SOURCE_ID, String.valueOf(heatContent), "0", "999"
      }, errors.get(0).getArgs(), "Arguments");
    }
  }

  private static Stream<Arguments> casesForOPSHeatContent() {
    return Stream.of(
        Arguments.of(OPSLimits.SOURCE_HEAT_CONTENT_SENSIBLE_MINIMUM - 0.001, false),
        Arguments.of(OPSLimits.SOURCE_HEAT_CONTENT_SENSIBLE_MINIMUM, true),
        Arguments.of(OPSLimits.SOURCE_HEAT_CONTENT_MAXIMUM, true),
        Arguments.of(OPSLimits.SOURCE_HEAT_CONTENT_MAXIMUM + 0.001, false),
        // Even though in OPS -999 is a valid value, that should only be used when another means of heat content is used (i.e. NOT_FORCED properties)
        Arguments.of(OPSLimits.SOURCE_HEAT_CONTENT_MINIMUM, false));
  }

  @Test
  void testOPSHeatContentNotForced() {
    final OPSSourceCharacteristics characteristics = new OPSSourceCharacteristics();
    characteristics.setHeatContentType(HeatContentType.NOT_FORCED);
    characteristics.setHeatContent(-0.001);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final OPSCharacteristicsValidator validator = new OPSCharacteristicsValidator(errors, warnings, SOURCE_ID);

    final boolean valid = validator.validate(characteristics);

    // Shouldn't be validating heat content when it's not used.
    assertTrue(valid, "Valid test case");
    assertTrue(errors.isEmpty(), "No errors");
    assertTrue(warnings.isEmpty(), "No warnings");
  }

}
