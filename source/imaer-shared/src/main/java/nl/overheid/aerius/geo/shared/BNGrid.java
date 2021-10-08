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
 * Constants for British National Grid -- United Kingdom (EPSG:27700).
 * Derived from the settings as provided by the jncc.
 */
public class BNGrid extends EPSG {
  /**
   * SRID of EPSG:27700.
   */
  public static final int SRID = 27700;

  private static final long serialVersionUID = 1L;

  private static final double[] RESOLUTIONS = {2500, 1000, 500, 200, 100, 50, 25, 10, 5, 2, 1, 0.5, 0.25};
  private static final float MAX_RESOLUTION = 0;
  private static final int ZOOM_LEVEL = 13;
  private static final String UNIT = "km";
  /**
   * Official boundaries of the BN grid extent of the map.
   */
  private static final BBox BOUNDS = new BBox(-90619.29, 10097.13, 612435.55, 1234954.16);
// TODO Original boundary used. Check it this one it still needed. private static final BBox BOUNDS = new BBox(0, 0, 800000, 1300000);
  private static final Point CENTER = new Point(308188.48, 608846.16);

  BNGrid() {
    super(SRID, BOUNDS, CENTER, RESOLUTIONS, MAX_RESOLUTION, UNIT, ZOOM_LEVEL);
  }
}
