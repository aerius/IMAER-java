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
package nl.overheid.aerius.gml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import nl.overheid.aerius.shared.domain.v2.importer.ImportParcel;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.AeriusException.Reason;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Test class to check how the GML importer handles invalid input.
 */
class InvalidGMLTest {

  private static final String LATEST_VALIDATE = "latest/validate/";

  @Test
  void testInvalidOpsValue() throws IOException, AeriusException {
    assertResult("invalid_ops_values", "Invalid ops input values", ImaerExceptionReason.SOURCE_VALIDATION_FAILED);
  }

  @Test
  void testInvalidSector() throws IOException, AeriusException {
    assertResult("invalid_sector", "Unknown sector code", ImaerExceptionReason.GML_GENERIC_PARSE_ERROR);
  }

  @Test
  void testInvalidSubstance() throws IOException, AeriusException {
    assertResult("invalid_substance", "Expected invalid substance", ImaerExceptionReason.GML_VALIDATION_FAILED,
        e -> assertFalse(e.getArgs()[0].isEmpty(), "Should show list of possible options"));
  }

  @Test
  void testInvalidXML1() throws IOException, AeriusException {
    assertResult("invalid_xml_1", "Expected GML parse error, invalid tag", ImaerExceptionReason.GML_PARSE_ERROR,
        e -> {
          assertEquals("15", e.getArgs()[0], "Row");
          assertEquals("19", e.getArgs()[1], "Column");
          assertEquals("imaer:namespace", e.getArgs()[2], "Tag in error");
          assertEquals("</imaer:namespace>", e.getArgs()[3], "Tag expected");
        });
  }

  @Test
  void testInvalidXML2() throws IOException, AeriusException {
    assertResult("invalid_xml_2", "Expected GML parse error", ImaerExceptionReason.GML_PARSE_ERROR,
        e -> {
          assertEquals("13", e.getArgs()[0], "Row");
          assertEquals("23", e.getArgs()[1], "Column");
          assertEquals("imaer:NEN3610ID", e.getArgs()[2], "Tag in error");
          assertEquals("</imaer:NEN3610ID>", e.getArgs()[3], "Tag expected");
        });
  }

  @Test
  void testInvalidXML3() throws IOException, AeriusException {
    assertResult("invalid_xml_3", "Expected GML parse error", ImaerExceptionReason.GML_VALIDATION_FAILED);
  }

  @Test
  void testUnsupportedAeriusGMLVersion() throws IOException, AeriusException {
    assertResult("unsupported_aerius_gml_version", "", ImaerExceptionReason.GML_VERSION_NOT_SUPPORTED);
  }

  @Test
  void testUnsupportedSrsName() throws IOException, AeriusException {
    assertResult("unsupported_srs_name", "Invalid SRS name for geometry", ImaerExceptionReason.GML_SRS_NAME_UNSUPPORTED);
  }

  private void assertResult(final String fileName, final String expectedReasonTxt, final Reason expectedReason,
      final Consumer<AeriusException> check) throws IOException, AeriusException {
    check.accept(assertResult(fileName, expectedReasonTxt, expectedReason));
  }

  private AeriusException assertResult(final String fileName, final String expectedReasonTxt, final Reason expectedReason)
      throws IOException, AeriusException {
    final AeriusException exception = assertThrows(
        AeriusException.class,
        () -> {
          final ImportParcel result = getImportResult(LATEST_VALIDATE, fileName);

          if (!result.getExceptions().isEmpty()) {
            throw result.getExceptions().get(0);
          }
        },
        "Expected AeriusException");

    assertSame(expectedReason, exception.getReason(), expectedReasonTxt);
    return exception;
  }

  private ImportParcel getImportResult(final String relativePath, final String fileName)
      throws IOException, AeriusException {
    return AssertGML.getImportResult(relativePath, fileName);
  }
}
