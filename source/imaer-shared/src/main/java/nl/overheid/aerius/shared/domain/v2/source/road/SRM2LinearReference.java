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

import nl.overheid.aerius.shared.domain.v2.base.LinearReference;

public class SRM2LinearReference extends LinearReference {

  private static final long serialVersionUID = 1L;

  private Double tunnelFactor;
  private RoadElevation elevation;
  private Integer elevationHeight;
  private SRM2RoadSideBarrier barrierLeft;
  private SRM2RoadSideBarrier barrierRight;

  public Double getTunnelFactor() {
    return tunnelFactor;
  }

  public void setTunnelFactor(final Double tunnelFactor) {
    this.tunnelFactor = tunnelFactor;
  }

  public RoadElevation getElevation() {
    return elevation;
  }

  public void setElevation(final RoadElevation elevation) {
    this.elevation = elevation;
  }

  public Integer getElevationHeight() {
    return elevationHeight;
  }

  public void setElevationHeight(final Integer elevationHeight) {
    this.elevationHeight = elevationHeight;
  }

  public SRM2RoadSideBarrier getBarrierLeft() {
    return barrierLeft;
  }

  public void setBarrierLeft(final SRM2RoadSideBarrier barrierLeft) {
    this.barrierLeft = barrierLeft;
  }

  public SRM2RoadSideBarrier getBarrierRight() {
    return barrierRight;
  }

  public void setBarrierRight(final SRM2RoadSideBarrier barrierRight) {
    this.barrierRight = barrierRight;
  }

}
