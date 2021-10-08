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
package nl.overheid.aerius.gml.base;

/**
 * Using GML3.2.
 */
public final class GMLSchema {

  /**
   * Namespace for the schema.
   */
  public static final String NAMESPACE = "http://www.opengis.net/gml/3.2";

  /**
   * Prefix for elements.
   */
  public static final String PREFIX = "gml";

  /**
   * Coordinate reference system name string prefix.
   */
  private static final String SRS_NAME_PREFIX = "urn:ogc:def:crs:EPSG::";

  private GMLSchema() {
    //utility class
  }

  public static String getSRSName(final int srid) {
    return SRS_NAME_PREFIX + srid;
  }
}
