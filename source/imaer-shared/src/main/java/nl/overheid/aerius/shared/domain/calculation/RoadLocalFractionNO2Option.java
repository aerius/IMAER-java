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
 * The option to use when determining the local fraction NO2 (as part of NOx) to use when determing concentrations.
 */
public enum RoadLocalFractionNO2Option {

  /**
   * Determine the fraction based on the location of the calculation point.
   */
  LOCATION_BASED,

  /**
   * One fraction specified that applies to all receptors and custom calculation points in the calculation.
   */
  ONE_CUSTOM_VALUE,

  /**
   * Use the local fractions specified for each custom calculation point.
   */
  INDIVIDUAL_CUSTOM_VALUES;

  public static RoadLocalFractionNO2Option safeValueOf(final String value) {
    try {
      return value == null ? null : valueOf(value.toUpperCase(Locale.ROOT));
    } catch (final IllegalArgumentException e) {
      return null;
    }
  }

}
