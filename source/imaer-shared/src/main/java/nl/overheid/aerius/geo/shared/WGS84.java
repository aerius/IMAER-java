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
package nl.overheid.aerius.geo.shared;

/**
 * Constants for WGS84 (EPSG::4326).
 *
 * The coordinates are not fully supported in AERIUS. This is only available to set points with these coordinates from OPS brn files. This is used
 * only to calculated foreign depositions.
 */
public final class WGS84 {

  /**
   * SRID of EPSG:4326.
   */
  public static final int SRID = 4326;

  private WGS84() {
    // Only available for SRID reference.
  }
}
