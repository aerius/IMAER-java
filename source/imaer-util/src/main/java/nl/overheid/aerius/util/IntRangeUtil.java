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
package nl.overheid.aerius.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.overheid.aerius.shared.domain.IntRange;

/**
 * Util class for {@link IntRange}.
 */
public class IntRangeUtil {

  private static final Pattern RANGE_PATTERN = Pattern.compile("([\\[\\(])(\\d*),(\\d*)([\\]\\)])");

  public static IntRange valueOf(final String range) {
    final Matcher matcher = RANGE_PATTERN.matcher(range);

    if (!matcher.matches() && matcher.groupCount() == 4) {
      throw new IllegalArgumentException("Not a valid range pattern: " + range);
    }
    final boolean lowInclusive = matcher.group(1).charAt(0) == '[';
    final int low = matcher.group(2).isEmpty() ? Integer.MIN_VALUE : Integer.parseInt(matcher.group(2));
    final int high = matcher.group(3).isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(matcher.group(3));
    final boolean highInclusive = matcher.group(4).charAt(0) == ']';

    return new IntRange(low, lowInclusive, high, highInclusive);
  }
}
