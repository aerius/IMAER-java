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
package nl.overheid.aerius.shared.domain.calculation;

import java.util.Locale;

/**
 * Type of calculation. It be a radius from the source point or to only
 * calculate points within nature areas.
 */
public enum CalculationType {

  /**
   * Calculate with custom points (fixed set of receptors or user defined points).
   */
  CUSTOM_POINTS,
  /**
   * Calculate deposition in nature areas.
   */
  NATURE_AREA,
  /**
   * Calculates using calculation points and settings established by policy.
   */
  PERMIT,
  /**
   * Calculate deposition in a radius around the sources.
   */
  RADIUS;

  /**
   * Safely returns a CalculationType. It is case independent and returns null in
   * case the input was null or the calculation type could not be found.
   *
   * @param value value to convert
   * @return CalculationType or null if no valid input
   */
  public static CalculationType safeValueOf(final String value) {
    try {
      return value == null ? null : valueOf(value.toUpperCase());
    } catch (final IllegalArgumentException e) {
      return null;
    }
  }

  public String type() {
    return name().toString();
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
