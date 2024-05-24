/*
 * Crown copyright
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
 * Types of Met(eorological) datasets used.
 */
public enum MetDatasetType {

  OBS_RAW_GT_90PCT(false),
  // Expect another observed set, but what the term will be exactly is unsure so uncommented for now.
//  OBS_RAW_GT_75PCT(false),
  NWP_3KM2(true);

  private final boolean nwp;

  MetDatasetType(final boolean nwp) {
    this.nwp = nwp;
  }

  public boolean isObserved() {
    return !nwp;
  }

  public boolean isNwp() {
    return nwp;
  }


  /**
   * Safely returns a MetDatasetType. It is case independent and returns null in
   * case the input was null or the dataset type could not be found.
   *
   * @param value value to convert
   * @return MetDatasetType or null if no valid input
   */
  public static MetDatasetType safeValueOf(final String value) {
    try {
      return value == null ? null : valueOf(value.toUpperCase(Locale.ROOT));
    } catch (final IllegalArgumentException e) {
      return null;
    }
  }

}
