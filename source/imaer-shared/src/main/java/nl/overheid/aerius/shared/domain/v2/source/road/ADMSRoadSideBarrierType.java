/*
 * Crown copyright
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
public enum ADMSRoadSideBarrierType {
  NONE,
  NOISE_BARRIER(5D),
  BRICK_WALL(5D),
  STREET_CANYON_TERRACED_HOUSES(20D),
  STREET_CANYON_SEMIDETACHED_HOUSES(40D),
  STREET_CANYON_DETACHED_HOUSES(50D),
  TREE_BARRIER_DENSE(80D),
  TREE_BARRIER_OPEN(95D),
  OTHER(0D);

  private Double defaultPorosity;

  private ADMSRoadSideBarrierType() {
    this(null);
  }

  /**
   * A default porosity value, in percentages (0-100)
   */
  private ADMSRoadSideBarrierType(final Double defaultPorosity) {
    this.defaultPorosity = defaultPorosity;
  }

  public Double getDefaultPorosity() {
    return defaultPorosity;
  }
}
