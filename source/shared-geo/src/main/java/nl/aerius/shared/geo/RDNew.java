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
package nl.aerius.shared.geo;

import nl.overheid.aerius.shared.domain.v2.geojson.Point;

/**
 * Constants for RD New coordinates (EPSG:28992).
 */
public final class RDNew extends EPSG {
  /**
   * SRID of EPSG:28992.
   */
  public static final int SRID = 28992;

  private static final long serialVersionUID = 1L;

  private static final int ZOOM_LEVEL = 14;
  private static final BBox BOUNDS = new BBox(-285401.920, 22598.080, 595401.920, 903401.920);
  private static final Point CENTER = new Point(155000, 463000);

  RDNew() {
    super(SRID, BOUNDS, CENTER, ZOOM_LEVEL);
  }
}
