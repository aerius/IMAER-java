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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import nl.overheid.aerius.gml.base.AeriusGMLVersion;
import nl.overheid.aerius.gml.base.GMLHelper;
import nl.overheid.aerius.importer.ImportOption;
import nl.overheid.aerius.shared.domain.v2.importer.ImportParcel;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.AeriusException.Reason;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Test class to check how the GML importer handles invalid input report these as warnings.
 */
class GMLValidateWarningsTest {

  private static final String LATEST_WARNINGS_VALIDATE = "latest/validate/warnings/";
  private static final Set<ImaerExceptionReason> ACCEPTED_WARNINGS = Set.of(
      ImaerExceptionReason.GML_VERSION_NOT_LATEST,
      ImaerExceptionReason.GML_CONVERTED_LODGING);

  private enum TestFile {
    WARNING_OLD_GML_VERSION;

    private final List<AeriusGMLVersion> warningsIn;

    private TestFile(final AeriusGMLVersion... warningsIn) {
      this.warningsIn = Arrays.asList(warningsIn);
    }

    public String getFileName() {
      return name().toLowerCase();
    }

    public boolean expectWarning(final AeriusGMLVersion version) {
      return warningsIn.contains(version);
    }

  }

  static List<Object[]> data() throws FileNotFoundException {
    final List<Object[]> files = new ArrayList<>();
    for (final AeriusGMLVersion version : AeriusGMLVersion.values()) {
      for (final TestFile file : TestFile.values()) {
        final Object[] f = new Object[2];
        f[0] = version;
        f[1] = file;
        files.add(f);
      }
    }
    return files;
  }

  @ParameterizedTest(name = "{0}-{1}")
  @MethodSource("data")
  void testVersionGML(final AeriusGMLVersion version, final TestFile testFile) throws IOException, AeriusException {
    final ImportParcel oldResult = getImportResult(version.name().toLowerCase(), testFile.getFileName());

    if (testFile.expectWarning(version)) {
      assertFalse(oldResult.getWarnings().isEmpty(), "Expected warnings, but got none");
    } else {
      // warnings test on allowed
      for (final AeriusException warning : oldResult.getWarnings()) {
        assertTrue(ACCEPTED_WARNINGS.contains(warning.getReason()), "Not expected warning, got " + warning.getReason() + " " + warning.getMessage());
      }
    }
  }

  @Test
  void testLineLengthExceedsLimit() throws IOException, AeriusException {
    assertResult("warning_1004_limit_line_length_exceeded", "GML line length exceeds limit", ImaerExceptionReason.LIMIT_LINE_LENGTH_EXCEEDED);
  }

  @Test
  void testPolygonSurfaceExceedsLimit() throws IOException, AeriusException {
    assertResult("warning_1005_limit_polygon_surface_exceeded", "GML polygon surface exceeds limit",
        ImaerExceptionReason.LIMIT_POLYGON_SURFACE_EXCEEDED);
  }
  
  @Test
  void testOldOffRoadMobileSector() throws IOException, AeriusException {
    assertResult("warning_5266_sector_out_of_date", "GML source sector is out of date", ImaerExceptionReason.GML_SECTOR_OUT_OF_DATE);
  }

  private static void assertResult(final String fileName, final String expectedReasonTxt, final Reason expectedReason)
      throws IOException, AeriusException {
    AeriusException foundWarning = null;
    final ImportParcel result = getImportResult(LATEST_WARNINGS_VALIDATE, fileName);

    Assertions.assertIterableEquals(List.of(), result.getExceptions(), "Should have no errors");
    for (final AeriusException warning : result.getWarnings()) {
      if (expectedReason.equals(warning.getReason())) {
        foundWarning = warning;
      }
    }
    assertNotNull(foundWarning, "Should have warning");
    assertEquals(expectedReason, foundWarning.getReason(), "Expected an AeriusException with reason " + expectedReasonTxt);
  }

  private static ImportParcel getImportResult(final String relativePath, final String filename) throws IOException, AeriusException {
    final GMLHelper mockGMLHelper = AssertGML.mockGMLHelper();
    mockGMLHelper.getEmissionSourceGeometryLimits().setMaxLineLength(1);
    mockGMLHelper.getEmissionSourceGeometryLimits().setMaxPolygonSurface(1);
    return AssertGML.getImportResult(relativePath, filename, EnumSet.of(ImportOption.WARNING_ON_GEOMETRY_LIMITS, ImportOption.INCLUDE_SOURCES),
        mockGMLHelper);
  }
}
