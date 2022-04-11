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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import nl.overheid.aerius.importer.ImaerImporter;
import nl.overheid.aerius.importer.ImportOption;
import nl.overheid.aerius.shared.domain.v2.importer.ImportParcel;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.AeriusException.Reason;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.util.ImaerFileUtil;

/**
 * Verifies that validation exceptions are thrown when unsupported features are supplied in an gml file. All gml files
 * in src/test/resources/nl/overheid/aerius/gml/latest/validate/unsupportedFeatures are tested.
 */
public class UnsupportedFeaturesTest {
  private static final String RESOURCE_PATH = "latest/validate/unsupportedFeatures/";

  private static final FilenameFilter GML_FILENAME_FILTER = new FilenameFilter() {
    @Override
    public boolean accept(final File dir, final String name) {
      return name != null && name.endsWith(".gml");
    }
  };

  private static List<File> getGMLFiles() throws FileNotFoundException {
    final File basePath = new File(UnsupportedFeaturesTest.class.getResource("/gml/").getPath(), RESOURCE_PATH);

    return ImaerFileUtil.getFilesWithExtension(basePath, GML_FILENAME_FILTER);
  }

  public static List<Object[]> data() throws FileNotFoundException {
    final List<Object[]> parameters = new ArrayList<>();

    for (final File file : getGMLFiles()) {
      parameters.add(new Object[] {file.getName(), file});
    }

    return parameters;
  }

  /**
   * Asserts that either an AeriusException is thrown or that the import result contains at least one AeriusException
   * and that the exception has {@link Reason#GML_VALIDATION_FAILED} as reason.
   *
   * @throws FileNotFoundException
   *           Thrown if the expected resource path cannot be found.
   * @throws IOException
   *           Thrown if an error occurred while re-accessing the test file.
   * @throws AeriusException
   */
  @ParameterizedTest(name = "{0}")
  @MethodSource("data")
  public void testValidationFailure(final String fileName, final File file) throws FileNotFoundException, IOException, AeriusException {
    final ImaerImporter importer = new ImaerImporter(AssertGML.mockGMLHelper());

    try (final InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
      final ImportParcel result = new ImportParcel();
      importer.importStream(inputStream, ImportOption.getDefaultOptions(), result);

      assertNotNull(result, "Import result cannot be null.");
      assertFalse(result.getExceptions().isEmpty(), "No validation errors found.");

      final AeriusException exception = result.getExceptions().get(0);
      assertNotNull(exception, "Exception cannot be null.");
      assertEquals(ImaerExceptionReason.GML_VALIDATION_FAILED, exception.getReason(), "Invalid reason.");
    } catch (final AeriusException e) {
      assertEquals(ImaerExceptionReason.GML_VALIDATION_FAILED, e.getReason(), "Invalid reason.");
    }
  }
}
