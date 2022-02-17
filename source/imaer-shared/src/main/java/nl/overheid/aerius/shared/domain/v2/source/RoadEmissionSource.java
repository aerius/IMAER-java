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

import nl.overheid.aerius.shared.domain.v2.source.road.RoadManager;
import nl.overheid.aerius.shared.domain.v2.source.road.TrafficDirection;
import nl.overheid.aerius.shared.domain.v2.source.road.Vehicles;

public abstract class RoadEmissionSource extends EmissionSourceWithSubSources<Vehicles> {

  private static final long serialVersionUID = 2L;

  /**
   * The code of the (road-specific) area that the road belongs to.
   * Used to determine the correct emissions.
   */
  private String roadAreaCode;

  /**
   * The code of the road type for this source.
   * Used to determine the correct emissions.
   */
  private String roadTypeCode;

  /**
   * Default tunnelfactor = 1.0 (normal road).
   * 0 would mean it is actually a tunnel (no emission).
   * Any other value would mean the road(section) is connected to a tunnel.
   * Tunnel should be > 100m long however to get a tunnelfactor.
   */
  private double tunnelFactor = 1.0;

  private RoadManager roadManager;

  private TrafficDirection trafficDirection;

  public String getRoadAreaCode() {
    return roadAreaCode;
  }

  public void setRoadAreaCode(final String roadAreaCode) {
    this.roadAreaCode = roadAreaCode;
  }

  public String getRoadTypeCode() {
    return roadTypeCode;
  }

  public void setRoadTypeCode(final String roadTypeCode) {
    this.roadTypeCode = roadTypeCode;
  }

  public RoadManager getRoadManager() {
    return roadManager;
  }

  public void setRoadManager(final RoadManager roadManager) {
    this.roadManager = roadManager;
  }

  public void setTunnelFactor(final double tunnelFactor) {
    this.tunnelFactor = tunnelFactor;
  }

  public double getTunnelFactor() {
    return tunnelFactor;
  }

  public TrafficDirection getTrafficDirection() {
    return trafficDirection;
  }

  public void setTrafficDirection(final TrafficDirection trafficDirection) {
    this.trafficDirection = trafficDirection;
  }

}
