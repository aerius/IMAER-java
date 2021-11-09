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
package nl.overheid.aerius.schematron;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.helger.commons.io.resource.IReadableResource;
import com.helger.schematron.SchematronHelper;
import com.helger.schematron.testfiles.SchematronTestHelper;
import com.helger.xml.microdom.IMicroDocument;

import nl.overheid.aerius.shared.domain.Theme;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.util.ImaerFileUtil;

/**
 * Test class for class {@link ImaerSchematronValidator}.
 */
public final class ImaerSchematronValidatorTest {

  private static final String BASE_PATH = "/schematron/gml/";
  private static final FilenameFilter GML_FILE_FILTER = (d, n) -> n.endsWith(".gml");

  private final ImaerSchematronValidator validator;

  public ImaerSchematronValidatorTest() throws AeriusException {
    validator = new ImaerSchematronValidator(Theme.RBL);
  }

  @Test
  public void testValidSchematron() {
    // Check all documents from package
    for (final IReadableResource aRes : SchematronTestHelper.getAllValidSchematronFiles()) {
      final IMicroDocument aDoc = SchematronHelper.getWithResolvedSchematronIncludes(aRes);
      final boolean bIsValid = com.helger.schematron.validator.SchematronValidator.isValidSchematron(aDoc);

      assertTrue(bIsValid, aRes.getPath());
    }
  }

  @Test
  public void testInvalidMetaData() throws Exception {
    assertFiles("metadata/invalid", true);
  }

  @Test
  public void testValidSRM2Road() throws Exception {
    assertFiles("feature/SRM2Road/valid", false);
  }

  @Test
  public void testInValidSRM2Road() throws Exception {
    assertFiles("feature/SRM2Road/invalid", true);
  }

  @Test
  public void testValidOther() throws Exception {
    assertFiles("feature/other", false);
  }

  private void assertFiles(final String path, final boolean expectErrors) throws AeriusException, IOException {
    final File directory = new File(getClass().getResource(BASE_PATH).getFile() + path);
    final List<File> files = ImaerFileUtil.getFilesWithExtension(directory, GML_FILE_FILTER);

    for (final File file : files) {
      final List<AeriusException> errors = new ArrayList<>();
      final List<AeriusException> warnings = new ArrayList<>();
      try (InputStream inputStream = new FileInputStream(file)) {
        validator.validateXMLStream(inputStream, errors, warnings);

        if (expectErrors) {
          assertNotEquals(0, errors.size(), "File " + file.getName() + " should have errors ");
        } else {
          assertEquals(0, errors.size(), "File " + file.getName() + " should not have errors ");
        }
      }
    }
  }

}
