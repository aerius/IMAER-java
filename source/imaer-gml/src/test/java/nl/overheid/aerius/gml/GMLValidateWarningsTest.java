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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import nl.overheid.aerius.gml.base.AeriusGMLVersion;
import nl.overheid.aerius.shared.domain.v2.importer.ImportParcel;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Test class to check how the GML importer handles invalid input report these as warnings.
 */
public class GMLValidateWarningsTest {

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

  public static List<Object[]> data() throws FileNotFoundException {
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

  @ParameterizedTest
  @MethodSource("data")
  public void testVersionGML(final AeriusGMLVersion version, final TestFile testFile) throws IOException, AeriusException {
    final ImportParcel oldResult = getImportResult(version.name().toLowerCase(), testFile);
    if (testFile.expectWarning(version)) {
      assertFalse(oldResult.getWarnings().isEmpty(), "Expected warnings, but got none");
    } else {
      // warnings test on allowed
      for (final AeriusException warning : oldResult.getWarnings()) {
        assertSame(warning.getReason(),
            ImaerExceptionReason.GML_VERSION_NOT_LATEST, "Not expected warning, got " + warning.getReason() + " " + warning.getMessage());
      }
    }
  }

  private ImportParcel getImportResult(final String relativePath, final TestFile testFile)
      throws IOException, AeriusException {
    return AssertGML.getImportResult(relativePath, testFile.getFileName());
  }

}
