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
package nl.overheid.aerius.shared.domain.result;

import java.io.Serializable;
import java.util.Locale;

/**
 * Type of phenomenon that is caused by an emission. E.g. NOx causes concentration and deposition.
 * PM10 causes concentration and number of excess days.
 */
public enum EmissionResultType implements Serializable {

  /**
   * Concentration.
   */
  CONCENTRATION,
  /**
   * Direct Concentration
   */
  DIRECT_CONCENTRATION,
  /**
   * Deposition.
   */
  DEPOSITION,
  /**
   * Number of days per year when the concentration exceeds the allowable limit.
   */
  EXCEEDANCE_DAYS,
  /**
   * Number of hours per year when the concentration exceeds the allowable limit.
   */
  EXCEEDANCE_HOURS,
  /**
   * Dry deposition.
   */
  DRY_DEPOSITION,
  /**
   * Wet deposition.
   */
  WET_DEPOSITION;

  private EmissionResultType() {
  }

  public static EmissionResultType safeValueOf(final String value) {
    try {
      return value == null ? null : valueOf(value.toUpperCase(Locale.ENGLISH));
    } catch (final IllegalArgumentException e) {
      return null;
    }
  }

  /**
   * Returns the emission result type as the value used in the database. Use this method when querying for emission result type.
   * @return Emission result type as value as used in the database
   */
  public String toDatabaseString() {
    return name().toLowerCase();
  }
}
