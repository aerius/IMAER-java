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
  CUSTOM_POINTS(CalculationMethod.CUSTOM_POINTS),
  /**
   * Calculate with custom receptors (maps user defined points to receptors on the predefined grid).
   */
  CUSTOM_RECEPTORS(CalculationMethod.CUSTOM_RECEPTORS),
  /**
   * Calculate deposition in nature areas.
   */
  NATURE_AREA(CalculationMethod.NATURE_AREA),
  /**
   * Calculates using calculation points and settings established by policy.
   */
  PERMIT(CalculationMethod.FORMAL_ASSESSMENT),
  /**
   * Calculate deposition in a radius around the sources.
   */
  RADIUS(null);

  private CalculationMethod calculationMethod;

  private CalculationType(final CalculationMethod calculationMethod) {
    this.calculationMethod = calculationMethod;
  }

  /**
   * Converts a old style calculation type to a calculation method object.
   *
   * @param value value to convert
   * @return calculation method object or null if could not be matched.
   */
  public static CalculationMethod toCalculationMethod(final String value) {
    final CalculationType ctype = safeValueOf(value);

    return ctype == null ? null : ctype.calculationMethod;
  }

  /**
   * Safely returns a CalculationMethod. It is case independent and returns null in
   * case the input was null or the calculation type could not be found.
   *
   * @param value value to convert
   * @return CalculationMethod or null if no valid input
   */
  public static CalculationType safeValueOf(final String value) {
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
