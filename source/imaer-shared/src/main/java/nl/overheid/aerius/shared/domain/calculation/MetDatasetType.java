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

  OBS_RAW_GT_90PCT(false, "raw_gt_90pct_OBS"),
  OBS_RAW_GT_75PCT(false, "raw_gt_75pct_OBS"),
  NWP_3_KM2(true, "3km2_NWP");

  private final boolean nwp;
  private final String alternativeName;

  MetDatasetType(final boolean nwp, final String alternativeName) {
    this.nwp = nwp;
    this.alternativeName = alternativeName;
  }

  public boolean isObserved() {
    return !nwp;
  }

  public boolean isNwp() {
    return nwp;
  }

  public String getAlternativeName() {
    return alternativeName;
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
      return byAlternativeName(value);
    }
  }

  /**
   * Determine MetDatasetType by alternative name. It is case independent and returns null in
   * case the dataset type could not be found.
   *
   * @param value value to convert
   * @return MetDatasetType or null if no valid input
   */
  public static MetDatasetType byAlternativeName(final String value) {
    MetDatasetType correct = null;
    for (final MetDatasetType type : values()) {
      if (type.alternativeName.equalsIgnoreCase(value)) {
        correct = type;
        break;
      }
    }
    return correct;
  }

}
