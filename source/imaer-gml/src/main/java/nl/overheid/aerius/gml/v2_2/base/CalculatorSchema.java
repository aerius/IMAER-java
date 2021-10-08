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
package nl.overheid.aerius.gml.v2_2.base;

/**
 * GML location and naming.
 */
public final class CalculatorSchema {

  /**
   * IMAER version.
   */
  public static final String IMAER_VERSION = "2.2";

  /**
   * Namespace for the schema.
   */
  public static final String NAMESPACE = "http://imaer.aerius.nl/" + IMAER_VERSION;

  /**
   * Prefix for elements.
   */
  public static final String PREFIX = "imaer";

  /**
   * Name of the XSD file.
   */
  public static final String XSD_FILENAME = "IMAER.xsd";

  /**
   * location of the XSD file.
   */
  public static final String PUBLIC_SCHEMA_LOCATION = NAMESPACE + "/" + XSD_FILENAME;

  /**
   * Schema location.
   */
  public static final String SCHEMA_LOCATION = "/imaer/" + IMAER_VERSION + "/" + XSD_FILENAME;

  /**
   * prefix used for id's in GML.
   */
  public static final String GML_ID_NAMESPACE = "NL.IMAER";

  private CalculatorSchema() {
    // utility class.
  }

}
