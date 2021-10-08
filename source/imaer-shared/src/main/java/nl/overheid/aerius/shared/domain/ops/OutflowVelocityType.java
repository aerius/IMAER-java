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

import java.util.Locale;

public enum OutflowVelocityType {
  ACTUAL_FLOW, NORMALISED_FLOW;

  /**
   * Safely returns a OutflowVelocityType. It is case independent and returns null in
   * case the input was null or the OutflowVelocityType type could not be found.
   *
   * @param value value to convert
   * @return OutflowVelocityType or null if no valid input
   */
  public static OutflowVelocityType safeValueOf(final String value) {
    try {
      return value == null ? null : valueOf(value.toUpperCase(Locale.ROOT));
    } catch (final IllegalArgumentException e) {
      return null;
    }
  }

  /**
   * Returns the name in lowercase.
   * @return name in lowercase
   */
  @Override
  public String toString() {
    return name().toLowerCase(Locale.ROOT);
  }

}
