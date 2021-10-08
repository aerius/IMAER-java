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
package nl.overheid.aerius.shared.domain.v2.source.road;

/**
 * The type of a barrier on the side of a road.
 */
public enum RoadSideBarrierType {

  /**
   * The barrier is a screen (example: 'Geluidsscherm').
   */
  SCREEN(0.5),

  /**
   * The barrier is a wall (example: 'Geluidswal').
   */
  WALL(0.25);

  private final double heightFactor;

  RoadSideBarrierType(final double heightFactor) {
    this.heightFactor = heightFactor;
  }

  public double getHeightFactor() {
    return this.heightFactor;
  }
}
