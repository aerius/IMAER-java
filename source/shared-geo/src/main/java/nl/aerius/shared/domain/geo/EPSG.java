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
package nl.aerius.shared.domain.geo;

import java.io.Serializable;

import nl.overheid.aerius.shared.domain.v2.geojson.Point;

/**
 * EPSG map constants.
 */
public abstract class EPSG implements Serializable {

  private static final long serialVersionUID = 1L;

  private static final String EPSG_PRE_TEXT = "EPSG:";

  private int srid;
  private BBox bounds;
  private int zoomLevel;

  private Point center;

  /**
   * Construct a EPSG object.
   * @param srid The SRID to set.
   * @param bounds The bounds to set.
   * @param center The center of the map
   * @param zoomLevel The zoomLevel to set.
   */
  public EPSG(final int srid, final BBox bounds, final Point center, final int zoomLevel) {
    this.srid = srid;
    this.bounds = bounds;
    this.center = center;
    this.zoomLevel = zoomLevel;
  }

  public String getEpsgCode() {
    return EPSG_PRE_TEXT + getSrid();
  }

  public int getSrid() {
    return srid;
  }

  public BBox getBounds() {
    return bounds;
  }

  public Point getCenter() {
    return center;
  }

  public int getZoomLevel() {
    return zoomLevel;
  }

}
