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
package nl.overheid.aerius.shared.domain.v2.characteristics;

import java.util.List;

public enum CustomDiurnalVariationType {

  /**
   * Diurnal variation over a day, with 1 value for each hour.
   */
  DAY(24),

  /**
   * Diurnal variation for 3-day profile, with 1 value for each hour of the day.
   * First day represents weekdays, second day represents saturday and third day represents sunday.
   * For validation, the weekday values will be multiplied by 5, and the total should be (roughly) equal to 7x24.
   */
  THREE_DAY(72, 168) {

    @Override
    public int sum(final List<Integer> values) {
      // Special case here: the first day (24 values) should be multiplied by 5.
      if (values.size() != getExpectedNumberOfValues()) {
        throw new IllegalArgumentException("Unexpected number of values for 3-day profile: " + values.size());
      }
      final int sumWeekdays = values.subList(0, 24).stream().mapToInt(x -> x * 5).sum();
      final int sumOtherDays = values.subList(24, values.size()).stream().mapToInt(x -> x).sum();
      return sumWeekdays + sumOtherDays;
    }

  };

  private final int expectedNumberOfValues;
  private final int expectedTotalNumberOfValues;

  private CustomDiurnalVariationType(final int expectedNumberOfValues) {
    this(expectedNumberOfValues, expectedNumberOfValues);
  }

  private CustomDiurnalVariationType(final int expectedNumberOfValues, final int expectedTotalNumberOfValues) {
    this.expectedNumberOfValues = expectedNumberOfValues;
    this.expectedTotalNumberOfValues = expectedTotalNumberOfValues;
  }

  public int getExpectedNumberOfValues() {
    return expectedNumberOfValues;
  }

  public int getExpectedTotalNumberOfValues() {
    return expectedTotalNumberOfValues;
  }

  public int sum(final List<Integer> values) {
    return values.stream().mapToInt(x -> x).sum();
  }

  public static CustomDiurnalVariationType safeValueOf(final String code) {
    try {
      return code == null ? null : valueOf(code.toUpperCase());
    } catch (final IllegalArgumentException e) {
      return null;
    }
  }

}
