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
 * Method of calculation.
 */
public enum CalculationMethod {

  /**
   * Calculate with custom points (fixed set of receptors or user defined points).
   */
  CUSTOM_POINTS,
  /**
   * Calculate with custom receptors (maps user defined points to receptors on the predefined grid).
   */
  CUSTOM_RECEPTORS,
  /**
   * Calculates using calculation points and settings established by formal assessment.
   */
  FORMAL_ASSESSMENT,
  /**
   * Calculate deposition in nature areas.
   */
  NATURE_AREA,
  /**
   * Calculates using less detailed (background) data to get indicative results.
   */
  QUICK_RUN;

  /**
   * Safely returns a CalculationMethod. It is case independent and returns null in
   * case the input was null or the calculation method could not be found.
   *
   * @param value value to convert
   * @return CalculationMethod or null if no valid input
   */
  public static CalculationMethod safeValueOf(final String value) {
    try {
      return value == null ? null : valueOf(value.toUpperCase(Locale.ROOT));
    } catch (final IllegalArgumentException e) {
      return null;
    }
  }

  public String type() {
    return name();
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
