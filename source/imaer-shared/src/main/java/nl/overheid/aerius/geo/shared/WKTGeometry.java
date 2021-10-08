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

import java.io.Serializable;

/**
 * WKTGeometry is a geometry class to store the geometry as a standardized
 * string. This can be used to pass geometries through the application if no
 * specific manipulation is needed outside open layers and/or postgis related
 * server side operations.
 *
 * The class also can store a measuring value. For lines this is the length of
 * the line for polygons this is the area of the polygon. The units are assumed
 * to be in meters for lines or square meters for polygons.
 */
public class WKTGeometry extends Geometry implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Type of the geometry.
   */
  public enum TYPE {
    /**
     * Point geometry.
     */
    POINT,
    /**
     * Line geometry.
     */
    LINE,
    /**
     * Polygon geometry.
     */
    POLYGON;

    /**
     * Returns the type of the given WKT string or null if the type could not be matched.
     * @param wkt WKT string
     * @return type or null
     */
    public static TYPE getType(final String wkt) {
      TYPE returnType = null;
      if (wkt != null) {
        if (wkt.startsWith("POINT")) {
          returnType = TYPE.POINT;
        } else if (wkt.startsWith("POLYGON")) {
          returnType = TYPE.POLYGON;
        } else if (wkt.startsWith("LINE")) {
          returnType = TYPE.LINE;
        } // else fallthrough to null
      }
      return returnType;
    }
  }

  private String wkt;
  private double measure;
  private TYPE type;

  public WKTGeometry() {
    // Needed for GWT
  }

  public WKTGeometry(final String wkt) {
    this(wkt, 1);
  }

  public WKTGeometry(final String wkt, final double measure) {
    setWkt(wkt);
    this.measure = measure;
  }

  /**
   * Create a copy of this object.
   *
   * @return new copy of object
   */
  public WKTGeometry copy() {
    return new WKTGeometry(wkt, measure);
  }

  @Override
  public boolean equals(final Object obj) {
    return obj != null && this.getClass() == obj.getClass() && wkt.equals(((WKTGeometry) obj).wkt);
  }

  @Override
  public int hashCode() {
    return wkt == null ? 0 : wkt.hashCode();
  }

  public double getMeasure() {
    return measure;
  }

  public TYPE getType() {
    return type;
  }

  public String getWKT() {
    return wkt;
  }

  /**
   * Returns only the points of the wkt, without the type of the wkt.
   * @return list of points.
   */
  public String getPoints() {
    String pointsPart = null;
    if (wkt != null && wkt.indexOf('(') != -1) {
      final int pos = type == TYPE.POLYGON ? 2 : 1;
      pointsPart = wkt.substring(wkt.indexOf('(') + pos, wkt.length() - pos);
    }

    return pointsPart;
  }

  public void setMeasure(final int measure) {
    this.measure = measure;
  }

  public final void setWkt(final String wkt) {
    this.wkt = wkt;
    type = TYPE.getType(wkt);
  }

  @Override
  public String toString() {
    return "WKTGeometry [wkt=" + wkt + ", measure=" + measure + ", type=" + type + "]";
  }
}
