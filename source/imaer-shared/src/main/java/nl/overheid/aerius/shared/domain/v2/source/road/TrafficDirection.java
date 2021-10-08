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
 * Direction of traffic with respect to the geometry (the draw direction).
 */
public enum TrafficDirection {

  /**
   * Traffic drives in both directions.
   */
  BOTH,

  /**
   * Traffic drives from the start of the linestring to the end of the linestring.
   */
  A_TO_B,

  /**
   * Traffic drives from the end of the linestring to the start of the linestring.
   */
  B_TO_A

}
