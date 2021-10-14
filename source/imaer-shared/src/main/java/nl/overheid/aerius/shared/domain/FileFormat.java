/*
 * Copyright the State of the Netherlands
 * Crown copyright
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
package nl.overheid.aerius.shared.domain;

/**
 * Supported file formats.
 */
public enum FileFormat {

  /**
   * GML (Geography Markup Language) format.
   */
  GML,
  /**
   * PDF (Portable Document Format) format.
   */
  PDF,
  /**
   * CSV (Comma Separated Values) format.
   */
  CSV,
  /**
   * ZIP (archive file) format.
   */
  ZIP,
  /**
   * RCP (OPS receptor file) format.
   */
  RCP,
  /**
   * BRN (OPS source file) format.
   */
  BRN,
  /**
   * APL (ADMS 5 industrial air pollution model file) format.
   */
  APL,
  /**
   * UPL (ADMS-Urban model file) format.
   */
  UPL;

  /**
   * Get the extension.
   * @return The extension.
   */
  public String getExtension() {
    return name().toLowerCase();
  }

  /**
   * Get the extension including the dot in the beginning.
   * @return The dotted extension, e.g. ".csv", ".gml".
   */
  public String getDottedExtension() {
    return "." + getExtension();
  }

}
