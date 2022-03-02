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

import java.io.Serializable;

public class SRM2RoadSideBarrier implements Serializable {

  private static final long serialVersionUID = 2L;

  /**
   * Type of the barrier.
   */
  private SRM2RoadSideBarrierType barrierType;
  /**
   * Height of the barrier.
   */
  private double height;
  /**
   * Distance from road to barrier.
   */
  private double distance;

  public SRM2RoadSideBarrierType getBarrierType() {
    return barrierType;
  }

  public void setBarrierType(final SRM2RoadSideBarrierType barrierType) {
    this.barrierType = barrierType;
  }

  public double getHeight() {
    return height;
  }

  public void setHeight(final double height) {
    this.height = height;
  }

  public double getDistance() {
    return distance;
  }

  public void setDistance(final double distance) {
    this.distance = distance;
  }

}
