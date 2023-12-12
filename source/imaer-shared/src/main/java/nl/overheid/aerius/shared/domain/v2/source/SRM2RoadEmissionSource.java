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

import java.util.List;

import nl.overheid.aerius.shared.domain.v2.geojson.IsFeature;
import nl.overheid.aerius.shared.domain.v2.source.road.RoadElevation;
import nl.overheid.aerius.shared.domain.v2.source.road.SRM2LinearReference;
import nl.overheid.aerius.shared.domain.v2.source.road.SRM2RoadSideBarrier;
import nl.overheid.aerius.shared.exception.AeriusException;

public class SRM2RoadEmissionSource extends RoadEmissionSource {

  private static final long serialVersionUID = 1L;

  private RoadElevation elevation = RoadElevation.NORMAL;
  private int elevationHeight;

  private SRM2RoadSideBarrier barrierLeft;
  private SRM2RoadSideBarrier barrierRight;

  private List<SRM2LinearReference> partialChanges;

  public void setElevation(final RoadElevation elevation) {
    this.elevation = elevation;
  }

  public RoadElevation getElevation() {
    return elevation;
  }

  public int getElevationHeight() {
    return elevationHeight;
  }

  public void setElevationHeight(final int elevationHeight) {
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

  public List<SRM2LinearReference> getPartialChanges() {
    return partialChanges;
  }

  public void setPartialChanges(final List<SRM2LinearReference> partialChanges) {
    this.partialChanges = partialChanges;
  }

  @Override
  <T> T accept(final EmissionSourceVisitor<T> visitor, final IsFeature feature) throws AeriusException {
    return visitor.visit(this, feature);
  }

}
