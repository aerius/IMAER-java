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
package nl.overheid.aerius.shared.domain.v2.source;

import nl.overheid.aerius.shared.domain.v2.geojson.IsFeature;
import nl.overheid.aerius.shared.domain.v2.source.road.ADMSRoadSideBarrier;
import nl.overheid.aerius.shared.exception.AeriusException;

public class ADMSRoadEmissionSource extends RoadEmissionSource {

  private static final long serialVersionUID = 1L;

  private double width;
  private double elevation;
  private double gradient;
  private double coverage;

  private ADMSRoadSideBarrier barrierLeft;
  private ADMSRoadSideBarrier barrierRight;

  public double getWidth() {
    return width;
  }

  public void setWidth(final double width) {
    this.width = width;
  }

  public double getElevation() {
    return elevation;
  }

  public void setElevation(final double elevation) {
    this.elevation = elevation;
  }

  public double getGradient() {
    return gradient;
  }

  public void setGradient(final double gradient) {
    this.gradient = gradient;
  }

  public double getCoverage() {
    return coverage;
  }

  public void setCoverage(final double coverage) {
    this.coverage = coverage;
  }

  public ADMSRoadSideBarrier getBarrierLeft() {
    return barrierLeft;
  }

  public void setBarrierLeft(final ADMSRoadSideBarrier barrierLeft) {
    this.barrierLeft = barrierLeft;
  }

  public ADMSRoadSideBarrier getBarrierRight() {
    return barrierRight;
  }

  public void setBarrierRight(final ADMSRoadSideBarrier barrierRight) {
    this.barrierRight = barrierRight;
  }

  @Override
  <T> T accept(final EmissionSourceVisitor<T> visitor, final IsFeature feature) throws AeriusException {
    return visitor.visit(this, feature);
  }

}
