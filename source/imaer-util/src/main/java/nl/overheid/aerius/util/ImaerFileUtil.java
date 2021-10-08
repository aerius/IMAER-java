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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Util class for constructing a file name.
 */
public class ImaerFileUtil {

  static final int MAX_OPTIONAL_FILENAME_LENGTH = 128;

  private static final String UNSAFE_FILENAME_CHARACTERS_REGEX = "\\W+";
  private static final String DATEFORMAT_FILENAME = "yyyyMMddHHmmss";
  private static final String FILENAME_SEPARATOR = "_";

  /**
   * Finds all files with fileName in the directory, recursively.
   * @param directory The directory to search for files.
   * @param fileName The filename to search for.
   * @return List of files in the directory that have that extension.
   * @throws FileNotFoundException When the directory does not exist or is not a directory.
   */
  public static List<File> getFiles(final String directory, final String fileName) throws FileNotFoundException {
    final File file = new File(directory);
    if (!file.exists() || !file.isDirectory()) {
      throw new FileNotFoundException(directory);
    }
    final File[] listFiles = file.listFiles();
    if (listFiles == null) {
      throw new FileNotFoundException(file.toString());
    }

    final List<File> files = new ArrayList<>();
    for (final File fileInDir : listFiles) {
      if (fileInDir.isDirectory()) {
        files.addAll(getFiles(fileInDir.getAbsolutePath(), fileName));
      } else if (fileInDir.isFile() && fileInDir.getName().equalsIgnoreCase(fileName)) {
        files.add(fileInDir);
      }
    }
    return files;
  }

  /**
   * Finds all files with a filename that has the right extension in the directory, recursively.
   * @param path The directory to search for files.
   * @param filenameFilter The filter to limit extensions to search for. (without .)
   * @return List of files in the directory that have that extension.
   * @throws FileNotFoundException When the directory does not exist or is not a directory.
   */
  public static List<File> getFilesWithExtension(final File path, final FilenameFilter filenameFilter) throws FileNotFoundException {
    if (!path.exists()) {
      throw new FileNotFoundException(path.toString());
    }
    final List<File> files = new ArrayList<>();
    if (path.isDirectory()) {
      files.addAll(getFilesInDirectoryWithExtension(path, filenameFilter));
    } else {
      files.add(path);
    }
    return files;
  }

  private static List<File> getFilesInDirectoryWithExtension(final File path, final FilenameFilter filenameFilter) throws FileNotFoundException {
    final File[] listFiles = path.listFiles((dir, name) -> {
      final File file = new File(dir, name);
      return file.isDirectory() || (file.isFile() && (filenameFilter == null || filenameFilter.accept(file, name)));
    });
    final List<File> files = new ArrayList<>();
    if (listFiles != null) {
      for (final File fileInDir : listFiles) {
        if (fileInDir.isDirectory()) {
          files.addAll(getFilesWithExtension(fileInDir, filenameFilter));
        } else {
          files.add(fileInDir);
        }
      }
    }
    return files;
  }

  /**
   * Get an (Aerius) file name without extension.
   * Format: prefix_datestring[_optionalName]
   * @param prefix Prefix to use in the filename.
   * @param optionalName The optional name to use in the filename.
   * @param optionalDate The optional date to use for the datestring. If null, current time will be used.
   * @return The file name that can be used without extension.
   */
  public static String getFileName(final String prefix, final String optionalName, final Date optionalDate) {
    if (prefix == null) {
      throw new IllegalArgumentException("Prefix not allowed to be null.");
    }
    return getActualFileName(prefix, null, optionalName, optionalDate);
  }

  /**
   * Get an (Aerius) file name.
   * Format: prefix_datestring[_optionalName][.]extension
   * @param prefix Prefix to use in the filename.
   * @param extension Extension to use for the filename. Will be prefixed by a . if not in the string.
   * @param optionalName The optional name to use in the filename.
   * @param optionalDate The optional date to use for the datestring. If null, current time will be used.
   * @return The file name that can be used.
   */
  public static String getFileName(final String prefix, final String extension, final String optionalName,
      final Date optionalDate) {
    if (prefix == null || extension == null) {
      throw new IllegalArgumentException("Prefix or extension not allowed to be null. Prefix: " + prefix + ", extension: " + extension);
    }
    return getActualFileName(prefix, extension, optionalName, optionalDate);
  }

  private static String getActualFileName(final String prefix, final String extension, final String optionalName,
      final Date optionalDate) {
    final StringBuilder fileNameBuilder = new StringBuilder();
    fileNameBuilder.append(prefix);
    fileNameBuilder.append(FILENAME_SEPARATOR);
    fileNameBuilder.append(new SimpleDateFormat(DATEFORMAT_FILENAME).format(
        optionalDate == null ? Calendar.getInstance().getTime() : optionalDate));
    if (optionalName != null && !optionalName.isEmpty()) {
      fileNameBuilder.append(FILENAME_SEPARATOR);
      fileNameBuilder.append(getSafeOptionalName(optionalName));
    }
    if (extension != null && !extension.isEmpty()) {
      fileNameBuilder.append(extension.charAt(0) == '.' ? extension : ('.' + extension));
    }
    return fileNameBuilder.toString();
  }

  private static String getSafeOptionalName(final String optionalName) {
    //optional name can be user-input, so assure it's safe and not too long
    final String safeOptionalName = getSafeFilename(optionalName);
    return safeOptionalName != null && safeOptionalName.length() > MAX_OPTIONAL_FILENAME_LENGTH
        ? safeOptionalName.substring(0, MAX_OPTIONAL_FILENAME_LENGTH)
        : safeOptionalName;
  }

  /**
   * Strips possibly invalid characters from the filename to ensure a valid filename.
   *
   * @param name The possibly invalid filename
   * @return Valid filename
   */
  public static String getSafeFilename(final String name) {
    return name == null ? null : name.replaceAll(UNSAFE_FILENAME_CHARACTERS_REGEX, "");
  }

}
