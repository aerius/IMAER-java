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

import java.io.Serializable;

public class ADMSRoadSideBarrier implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Type of the barrier.
   */
  private ADMSRoadSideBarrierType barrierType;
  /**
   * Width of the barrier (along the road?).
   */
  private double width;
  /**
   * Maximum height of the barrier.
   */
  private double maximumHeight;
  /**
   * Average height of the barrier.
   */
  private double averageHeight;
  /**
   * Minimum height of the barrier.
   */
  private double minimumHeight;
  /**
   * The porosity of the barrier
   */
  private double porosity;

  public ADMSRoadSideBarrierType getBarrierType() {
    return barrierType;
  }

  public void setBarrierType(final ADMSRoadSideBarrierType barrierType) {
    this.barrierType = barrierType;
  }

  public double getWidth() {
    return width;
  }

  public void setWidth(final double width) {
    this.width = width;
  }

  public double getMaximumHeight() {
    return maximumHeight;
  }

  public void setMaximumHeight(final double maximumHeight) {
    this.maximumHeight = maximumHeight;
  }

  public double getAverageHeight() {
    return averageHeight;
  }

  public void setAverageHeight(final double averageHeight) {
    this.averageHeight = averageHeight;
  }

  public double getMinimumHeight() {
    return minimumHeight;
  }

  public void setMinimumHeight(final double minimumHeight) {
    this.minimumHeight = minimumHeight;
  }

  public double getPorosity() {
    return porosity;
  }

  public void setPorosity(final double porosity) {
    this.porosity = porosity;
  }

}
