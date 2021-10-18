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
package nl.overheid.aerius.shared.domain.v2.importer;

import nl.overheid.aerius.shared.domain.FileFormat;

/**
 * Various import file types supported.
 */
public enum ImportType {
  /**
   * Receptor point file.
   */
  RCP(FileFormat.RCP),
  /**
   * OPS source file.
   */
  BRN(FileFormat.BRN),
  /**
   * SRM2 csv file.
   */
  SRM2(FileFormat.CSV),
  /**
   * GML file.
   */
  GML(FileFormat.GML),
  /**
   * PDF as used by the PAA.
   */
  PAA(FileFormat.PDF),
  /**
   * CERC ADMS UPL file.
   */
  APL(FileFormat.APL),
  /**
   * CERC ADMS UPL file.
   */
  UPL(FileFormat.UPL),
  /**
   * ZIP file which can contain any of the other types.
   */
  ZIP(FileFormat.ZIP);

  private FileFormat fileFormat;

  private ImportType(final FileFormat fileFormat) {
    this.fileFormat = fileFormat;
  }

  public String getExtension() {
    return fileFormat.getExtension();
  }

  public FileFormat getFileFormat() {
    return fileFormat;
  }

  /**
   * Determine value by given extension (without the dot).
   * @param extension The extension to get import type for.
   * @return value if found, null if no  match.
   */
  public static ImportType determineByExtension(final String extension) {
    ImportType returnType = null;

    for (final ImportType type : values()) {
      if (type.getExtension().equalsIgnoreCase(extension)) {
        returnType = type;
        break;
      }
    }

    return returnType;
  }

  /**
   * Determine value by given filename. A path should also work.
   * @param filename The filename to get import type for.
   * @return value if found, null if no match or file doesn't contain an extension.
   */
  public static ImportType determineByFilename(final String filename) {
    final ImportType importType;
    if (filename == null) {
      importType = null;
    } else {
      final int posOfDot = filename.lastIndexOf('.');
      if (posOfDot == -1) {
        importType = null;
      } else {
        importType = determineByExtension(filename.substring(posOfDot + 1));
      }
    }
    return importType;
  }

}
