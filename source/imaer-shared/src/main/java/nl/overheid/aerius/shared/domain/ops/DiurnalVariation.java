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

package nl.overheid.aerius.shared.domain.ops;

/**
 * Enumeration of Diurnal Variation types.
 */
public enum DiurnalVariation {
  UNKNOWN(-1) {
    @Override
    public String getCode() {
      return null;
    }
  },
  CONTINUOUS(0),
  INDUSTRIAL_ACTIVITY(1),
  SPACE_HEATING(2),
  TRAFFIC(3),
  ANIMAL_HOUSING(4),
  FERTILISER(5),
  SPACE_HEATING_WITHOUT_SEASONAL_CORRECTION(7),
  LIGHT_DUTY_VEHICLES(31),
  HEAVY_DUTY_VEHICLES(32),
  BUSES(33);

  private int id;

  DiurnalVariation(final int id) {
    this.id = id;
  }

  public static DiurnalVariation safeValueOf(final String code) {
    try {
      return code == null ? UNKNOWN : valueOf(code.toUpperCase());
    } catch (final IllegalArgumentException e) {
      return UNKNOWN;
    }
  }

  public static DiurnalVariation safeValueOf(final int id) {
    for (final DiurnalVariation dv : values()) {
      if (dv.getId() == id) {
        return dv;
      }
    }
    return UNKNOWN;
  }

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  public String getCode() {
    return name();
  }

}
