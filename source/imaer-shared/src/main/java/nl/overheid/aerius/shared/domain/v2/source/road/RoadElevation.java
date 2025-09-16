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
 * The elevation type of a road.
 */
public enum RoadElevation {

  /**
   * The road has no elevation (or is elevated with angles < 20 degrees).
   */
  NORMAL(0.0),

  /**
   * The road lies on a normal wall or dyke (angles > 20 degrees and < 45 degrees).
   */
  NORMAL_DYKE(0.25),

  /**
   * The road lies on a steep wall or dyke (angles > 45 degrees).
   */
  STEEP_DYKE(0.5),

  /**
   * The road lies on a viaduct.
   */
  VIADUCT(1.0),

  /**
   * The road lies in a 'tunnelbak' (the part of the road just before/after a tunnel).
   */
  TUNNEL(0.25);

  private double heightFactor;

  RoadElevation(final double heightFactor) {
    this.heightFactor = heightFactor;
  }

  public double getHeightFactor() {
    return this.heightFactor;
  }

  /**
   * Default tunnelfactor = 1.0 (normal road).
   * 0 would mean it is actually a tunnel (no emission).
   * Any other value would mean the road(section) is connected to a tunnel.
   * Tunnel should be > 100m long however to get a tunnelfactor.
   */
  public static final double TUNNEL_FACTOR = 1L;
}
