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

import java.io.Serializable;
import java.util.Locale;

/**
 * {@link VehicleType} containing the different types of traffic.
 */
public enum VehicleType implements Serializable {
  // light traffic.
  LIGHT_TRAFFIC,
  // normal freight traffic.
  NORMAL_FREIGHT,
  // heavy freight traffic.
  HEAVY_FREIGHT,
  // auto bus traffic.
  AUTO_BUS;

  /**
   * Returns the name in lower case.
   *
   * @return name in lower case.
   */
  @Override
  public String toString() {
    return name().toLowerCase();
  }

  public static final VehicleType safeValueOf(final String value) {
    try {
      return value == null ? null : valueOf(value.toUpperCase(Locale.ROOT));
    } catch (final IllegalArgumentException e) {
      return null;
    }
  }

  public String getStandardVehicleCode() {
    return name();
  }
}
