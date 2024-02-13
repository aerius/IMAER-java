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
package nl.overheid.aerius.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.Calendar;

import org.junit.jupiter.api.Test;

/**
 * Test class for {@link ImaerFileUtil}.
 */
public class ImaerFileUtilTest {

  private static final String TEST_FILE_NAME = "someFile";
  private static final String TEST_FILE_NAME_WITH_SPACES = "some Fil   e   ";
  private static final String TEST_FILE_PREFIX = "naam";
  private static final String TEST_FILE_NAME_EXTENSION = "test";
  private static final String TEST_LONG_FILE_NAME = "abcdefghijklmnopqrstuvwxyz_are_the_letters_in_the_alphabet";

  @Test
  void testGetFilesWithExtension() throws FileNotFoundException {
    final String file = ImaerFileUtilTest.class.getResource("").getFile();
    assertFalse(ImaerFileUtil.getFilesWithExtension(new File(file), null).isEmpty(), "Check if find files in directory with no filter");
    assertFalse(ImaerFileUtil.getFilesWithExtension(new File(file), new FilenameFilter() {
      @Override
      public boolean accept(final File dir, final String name) {
        return name.endsWith("class");
      }
    }).isEmpty(), "Check if find files in directory with");
    assertFalse(ImaerFileUtil.getFilesWithExtension(new File(file, ImaerFileUtilTest.class.getSimpleName() + ".class"), new FilenameFilter() {
      @Override
      public boolean accept(final File dir, final String name) {
        return name.endsWith("class");
      }
    }).isEmpty(), "Check if find this file");
  }

  @Test
  public void testGetSafeFilename() {
    String fileName = ImaerFileUtil.getSafeFilename(null);
    assertNull(fileName, "Supplying null should return null");
    fileName = ImaerFileUtil.getSafeFilename("");
    assertEquals("", fileName, "Supplying empty string should return empty string");
    fileName = ImaerFileUtil.getSafeFilename(TEST_FILE_NAME);
    assertEquals(TEST_FILE_NAME, fileName, "Correct file name should be returned as is");
    fileName = ImaerFileUtil.getSafeFilename(TEST_FILE_NAME_WITH_SPACES);
    assertEquals(TEST_FILE_NAME, fileName, "Spaces should be removed");
  }

  @Test
  public void testGetFileName() {
    String fileName = ImaerFileUtil.getFileName(TEST_FILE_PREFIX, TEST_FILE_NAME_EXTENSION, null, null);
    assertNotNull(fileName, "filename returned shouldn't be null");
    assertTrue(fileName.startsWith(TEST_FILE_PREFIX), "filename should start with prefix. " + fileName);
    assertTrue(fileName.endsWith("." + TEST_FILE_NAME_EXTENSION), "filename should end with extension. " + fileName);
    fileName = ImaerFileUtil.getFileName(TEST_FILE_PREFIX, "." + TEST_FILE_NAME_EXTENSION, TEST_FILE_NAME, null);
    assertNotNull(fileName, "filename returned shouldn't be null");
    assertTrue(fileName.startsWith(TEST_FILE_PREFIX), "filename should start with prefix. " + fileName);
    assertTrue(fileName.endsWith("." + TEST_FILE_NAME_EXTENSION), "filename should end with extension. " + fileName);
    assertFalse(fileName.endsWith(".." + TEST_FILE_NAME_EXTENSION), "filename shouldn't end with double .. before extension. " + fileName);
    assertTrue(fileName.contains(TEST_FILE_NAME), "filename should contain the optional name. " + fileName);
    final Calendar tryoutDate = Calendar.getInstance();
    tryoutDate.set(2013, 11, 30, 18, 21);
    fileName = ImaerFileUtil.getFileName(TEST_FILE_PREFIX, TEST_FILE_NAME_EXTENSION, TEST_FILE_NAME_WITH_SPACES, tryoutDate.getTime());
    assertTrue(fileName.contains("201312301821"), "filename should contain the right date format. " + fileName);
    assertTrue(fileName.contains(TEST_FILE_NAME), "filename should contain the optional name without spaces. " + fileName);
  }

  @Test
  public void testGetFileNameWithoutExtendsionNullPrefix() {
    assertThrows(
        IllegalArgumentException.class,
        () -> ImaerFileUtil.getFileName(null, null, null),
        "Expected IllegalArgumentException");
  }

  @Test
  public void testGetFileNameNullPrefix() {
    assertThrows(
        IllegalArgumentException.class,
        () -> ImaerFileUtil.getFileName(null, TEST_FILE_NAME_EXTENSION, null, null),
        "Expected IllegalArgumentException");
  }

  @Test
  public void testGetFileNameNullExtension() {
    assertThrows(
        IllegalArgumentException.class,
        () -> ImaerFileUtil.getFileName(TEST_FILE_NAME, null, null, null),
        "Expected IllegalArgumentException");
  }

  @Test
  public void testGetFileNameOptional() {
    String longFileName = TEST_LONG_FILE_NAME;
    while (longFileName.length() < ImaerFileUtil.MAX_OPTIONAL_FILENAME_LENGTH) {
      longFileName += TEST_LONG_FILE_NAME;
    }
    final String fileName = ImaerFileUtil.getFileName(TEST_FILE_PREFIX, TEST_FILE_NAME_EXTENSION, longFileName, null);
    assertTrue(fileName.contains(longFileName.substring(0, ImaerFileUtil.MAX_OPTIONAL_FILENAME_LENGTH)),
        "filename should contain the first X letters of the optional name. " + fileName);
    assertFalse(fileName.contains(longFileName.substring(0, ImaerFileUtil.MAX_OPTIONAL_FILENAME_LENGTH + 1)),
        "filename shouldn't contain more than the first X letters of the optional name. " + fileName);
  }

  @Test
  public void testGetFileNameEmpty() {
    final String fileName = ImaerFileUtil.getFileName(TEST_FILE_PREFIX, null, null);
    assertNotNull(fileName, "filename returned shouldn't be null");
    assertTrue(fileName.startsWith(TEST_FILE_PREFIX), "filename should start with prefix. " + fileName);
    assertFalse(fileName.endsWith("."), "filename should end with extension. " + fileName);
  }

}
