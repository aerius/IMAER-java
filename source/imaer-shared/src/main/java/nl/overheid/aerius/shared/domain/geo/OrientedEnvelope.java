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

/**
 * Envelop with length, width and clockwise North orientation of an object.
 */
public class OrientedEnvelope {

  private final double length;
  private final double width;
  private final double orientation;

  public OrientedEnvelope(final double length, final double width, final double orientation) {
    this.length = length;
    this.width = width;
    this.orientation = orientation;
  }

  /**
   * Length of the building in meters
   */
  public double getLength() {
    return length;
  }

  /**
   * Width of the building in meters
   */
  public double getWidth() {
    return width;
  }

  /**
   * Orientation in degrees clockwise relative to the North.
   */
  public double getOrientation() {
    return orientation;
  }

}
