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
package nl.overheid.aerius.shared.geo;

/**
 * Enum representing the web mercator projections supported by the application
 */
public enum EPSG {
  /**
   * WGS84 (EPSG::4326).
   */
  WGS84(4326),
  /**
   * British National Grid -- United Kingdom (EPSG:27700).
   */
  BNG(27700),
  /**
   * RD New grid (EPSG:28992).
   */
  RDNEW(28992),
  /**
   * Irish grid (EPSG:29902).
   */
  TM65(29902),
  /**
   * Northern Irish grid (EPSG:29903).
   */
  TM75(29903);

  private static final String EPSG_PREFIX = "EPSG:";

  private int srid;

  EPSG(final int srid) {
    this.srid = srid;
  }

  public static EPSG getEnumBySrid(final int srid) {
    for (final EPSG epsg : EPSG.values()) {
      if (epsg.srid == srid) {
        return epsg;
      }
    }
    return null;
  }

  public static EPSG getEnumByEpsg(final String epsgCode) {
    if (epsgCode == null || !epsgCode.startsWith(EPSG_PREFIX)) {
      return null;
    }

    try {
      final int srid = Integer.parseInt(epsgCode.substring(EPSG_PREFIX.length()));
      return getEnumBySrid(srid);
    } catch (final NumberFormatException e) {
      // Eat and return null
      return null;
    }
  }

  public static EPSG safeValueOf(final String epsg) {
    try {
      return "".equals(epsg) ? null : valueOf(epsg);
    } catch (final IllegalArgumentException e) {
      return null;
    }
  }

  public int getSrid() {
    return srid;
  }

  public String getEpsgCode() {
    return EPSG_PREFIX + srid;
  }
}
