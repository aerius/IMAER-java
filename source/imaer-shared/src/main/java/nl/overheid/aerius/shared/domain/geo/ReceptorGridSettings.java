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
package nl.overheid.aerius.shared.domain.geo;

import java.util.ArrayList;
import java.util.List;

import jsinterop.annotations.JsProperty;

import nl.overheid.aerius.geo.shared.BBox;
import nl.overheid.aerius.shared.geo.EPSG;

/**
 * Application settings related to the variables of the receptor grid used as basis of the application.
 */
public enum ReceptorGridSettings {
  /**
   * The Netherlands
   */
  NL(EPSG.RDNEW, 1529, 5, 10_000, new BBox(3_604.0, 296_800.0, 287_959.0, 629_300.0)),
  /**
   * United Kingdom
   */
  UK(EPSG.BNG, 1785, 7, 40_000, new BBox(-4_000.0, 4_000.0, 660_000.0, 1_222_000.0));

  private BBox boundingBox;
  private EPSG epsg;
  /**
   * Number of hexagons on the horizontal axis.
   * This should be calculated using the hexagon area:
   * Calculation of (((X_MAX - X_MIN) / (1.5 * R_HEX)) + 1) / 2
   */
  private int hexHor;
  private @JsProperty List<HexagonZoomLevel> hexagonZoomLevels;

  /**
   * Constructor
   *
   * @param epsg EPSG code of the receptor grid
   * @param hexagonHorizontal Number of horizontal hexagons
   * @param maxZoomLevels Maximum number of zoom levels
   * @param minSurfaceArea Minimum surface are of zoom level 1
   * @param boundingBox Bounding box of the receptor grid
   */
  ReceptorGridSettings(final EPSG epsg, final int hexagonHorizontal, final int maxZoomLevels, final int minSurfaceArea, final BBox boundingBox) {
    final List<HexagonZoomLevel> zoomLevels = new ArrayList<>();

    for (int i = 1; i <= maxZoomLevels; i++) {
      zoomLevels.add(new HexagonZoomLevel(i, minSurfaceArea));
    }
    this.epsg = epsg;
    this.hexHor = hexagonHorizontal;
    this.hexagonZoomLevels = zoomLevels;
    this.boundingBox = boundingBox;
  }

  public static ReceptorGridSettings valueByEPSG(final EPSG epsg) {
    for (final ReceptorGridSettings rgs : values()) {
      if (rgs.getEPSG() == epsg) {
        return rgs;
      }
    }
    return null;
  }

  /**
   * The bounding box of the receptor grid.
   */
  public BBox getBoundingBox() {
    return boundingBox;
  }

  /**
   * The geometric settings used for the receptor grid.
   */
  public EPSG getEPSG() {
    return epsg;
  }

  /**
   * Total number of Hexagons on the horizontal axis.
   */
  public int getHexHor() {
    return hexHor;
  }

  public HexagonZoomLevel getZoomLevel1() {
    return hexagonZoomLevels.get(0);
  }

  public List<HexagonZoomLevel> getHexagonZoomLevels() {
    return hexagonZoomLevels;
  }
}
