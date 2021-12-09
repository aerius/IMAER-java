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
 * Simple point with x and y coordinate.
 */
public class Point extends nl.overheid.aerius.shared.domain.v2.geojson.Point implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * If the SRID is not set then the system default SRID applies.
   */
  private static final int DEFAULT_SRID = 0;

  private int srid;

  // Needed for GWT.
  public Point() {
  }

  public Point(final double x, final double y) {
    this(x, y, DEFAULT_SRID);
  }

  public Point(final double x, final double y, final int srid) {
    super(x, y);
    this.srid = srid;
  }

  /**
   * Returns the distance of this Point to the provided Point.
   * @param other other point to measure distance
   * @return distance to other point
   */
  public double distance(final Point other) {
    final double tx = getX() - other.getX();
    final double ty = getY() - other.getY();

    return Math.sqrt(tx * tx + ty * ty);
  }

  @Override
  public boolean equals(final Object obj) {
    return obj instanceof Point && srid == ((Point) obj).srid
        && getRoundedX() == ((Point) obj).getRoundedX() && getRoundedY() == ((Point) obj).getRoundedY();
  }

  /**
   * @param systemSrid system SRID
   * @return Returns true if this point has SRID value 0 or equals to the given system SRID value.
   */
  public boolean isSystemSrid(final int systemSrid) {
    return srid == 0 || srid == systemSrid;
  }

  /**
   * Returns the srid.
   * NOTE: Do not test if this equals the system srid as it can be 0 in that case. Use: {@link #isSystemSrid(int)}.
   */
  public int getSrid() {
    return srid;
  }

  /**
   * Get the rounded value of x.
   * @return rounded x.
   */
  public int getRoundedX() {
    return (int) Math.round(getX());
  }

  /**
   * Get the value of x rounded on centimeters.
   * @return x rounded on centimeters
   */
  public double getRoundedCmX() {
    return ((int) Math.round(getX() * 100)) / 100.0;
  }

  /**
   * Get the rounded value of y.
   * @return rounded y.
   */
  public int getRoundedY() {
    return (int) Math.round(getY());
  }

  /**
   * Get the value of y rounded on centimeters.
   * @return y rounded on centimeters
   */
  public double getRoundedCmY() {
    return ((int) Math.round(getY() * 100)) / 100.0;
  }

  @Override
  public int hashCode() {
    return super.hashCode() * srid;
  }

  public void setSrid(final int srid) {
    this.srid = srid;
  }

  /**
   * Returns the point as WKT string. Coordinates are rounded to the nearest integer.
   *
   * @return the point as WKT string
   */
  public String toWKT() {
    return "POINT(" + getRoundedX() + " " + getRoundedX() + ")";
  }

  /**
   * Returns the point as WKT string. Coordinates are not rounded.
   *
   * @return the point as WKT string
   */
  public String toUnroundedWKT() {
    return "POINT(" + getX() + " " + getY() + ")";
  }

  @Override
  public String toString() {
    return "Point [x=" + getX() + ", y=" + getY() + "]";
  }
}
