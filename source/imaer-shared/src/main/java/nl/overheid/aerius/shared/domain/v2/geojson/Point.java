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
package nl.overheid.aerius.shared.domain.v2.geojson;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Point extends Geometry {

  private static final long serialVersionUID = 2L;

  private double[] coordinates;

  public Point() {
    // Default constructor for serialization.
  }

  public Point(final double x, final double y) {
    coordinates = new double[] {x, y};
  }

  public double[] getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(final double[] coordinates) {
    this.coordinates = coordinates;
  }

  @JsonIgnore
  public double getX() {
    return coordinates[0];
  }

  @JsonIgnore
  public double getY() {
    return coordinates[1];
  }

  @Override
  public GeometryType type() {
    return GeometryType.POINT;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(coordinates);
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    return this == obj
        || (obj != null && getClass() == obj.getClass() && Arrays.equals(coordinates, ((Point) obj).coordinates));
  }

}
