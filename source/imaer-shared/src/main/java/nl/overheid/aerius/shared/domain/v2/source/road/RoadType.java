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

import nl.overheid.aerius.shared.domain.HasId;

/**
 * {@link RoadType} containing the different types of road.
 */
public enum RoadType implements HasId {
  /**
   * Roads within residential areas.
   */
  URBAN_ROAD(3113),
  /**
   * Non urban roads.
   */
  NON_URBAN_ROAD(3112),
  /**
   * High ways.
   */
  FREEWAY(3111);

  private int sectorId;

  private RoadType(final int sectorId) {
    this.sectorId = sectorId;
  }

  public int getSectorId() {
    return sectorId;
  }

  /**
   * Returns a {@link RoadType} from a sector id or null if it doesn't match.
   * @param sectorId sector id to find road type
   * @return road type or null
   */
  public static RoadType valueFromSectorId(final int sectorId) {
    for (final RoadType rt : values()) {
      if (rt.getSectorId() == sectorId) {
        return rt;
      }
    }
    return null;
  }

  /**
   * Returns the name in lower case.
   *
   * @return name in lower case.
   */
  @Override
  public String toString() {
    return name().toLowerCase();
  }

  @Override
  public int getId() {
    return sectorId;
  }

  @Override
  public void setId(final int id) {
    // no-op
  }
}
