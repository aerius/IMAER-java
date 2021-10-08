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
 * NSL road speed types.
 */
public enum RoadSpeedType {
  /**
   * "buitenweg algemeen".
   *
   * Typisch buitenwegverkeer, een gemiddelde snelheid van ongeveer 60 km/h, gemiddeld ca. 0,2 stops per afgelegde km.
   */
  NON_URBAN_TRAFFIC("B"),
  /**
   * "normaal stadsverkeer".
   *
   * Typisch stadsverkeer met een redelijke mate van congestie, een gemiddelde snelheid tussen de 15 en 30 km/h,
   * gemiddeld ca. 2 stops per afgelegde km.
   */
  URBAN_TRAFFIC_NORMAL("C"),
  /**
   * "stagnerend stadsverkeer".
   *
   * Stadsverkeer met een grote mate van congestie, een gemiddelde snelheid kleiner dan 15 km/h, gemiddeld ca. 10 stops per afgelegde km.
   */
  URBAN_TRAFFIC_STAGNATING("D"),
  /**
   * "stadsverkeer met minder congestie".
   *
   * Stadsverkeer met een relatief groter aandeel "free-flow" rijgedrag, een gemiddelde snelheid tussen de 30 en 45 km/h,
   * gemiddeld ca. 1,5 stop per afgelegde km.
   */
  URBAN_TRAFFIC_FREE_FLOW("E"),
  /**
   * "buitenweg nationale weg".
   *
   * Typisch buitenwegverkeer op een nationale weg.
   */
  NATIONAL_ROAD("NATIONAL_ROAD");

  private final String legacyValue;

  private RoadSpeedType(final String legacyValue) {
    this.legacyValue = legacyValue;
  }

  public static RoadSpeedType safeLegacyValueOf(final String snelheid) {
    RoadSpeedType result = null;
    for (final RoadSpeedType speedType : values()) {
      if (speedType.legacyValue.equalsIgnoreCase(snelheid)) {
        result = speedType;
      }
    }
    return result;
  }

}
