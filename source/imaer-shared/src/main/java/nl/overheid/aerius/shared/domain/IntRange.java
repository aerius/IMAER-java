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
package nl.overheid.aerius.shared.domain;

import java.util.function.Function;

/**
 * Object to use to check if a given value is within a range.
 * The range object can be created using a string representation of the range. like [1,10).
 */
public record IntRange(int low, boolean lowInclusive, int high,  boolean highInclusive) {

  public boolean inRange(final int value) {
    return (value > low() || (lowInclusive() && value == low()))
        && (value < high() || (highInclusive() && value == high()));
  }

  @Override
  public String toString() {
     final Function<Boolean, String> same = b -> b ? "=" : "";
     final boolean withLow = low != Integer.MIN_VALUE;
     final boolean withHigh = high != Integer.MAX_VALUE;

    return (withLow ?  ('>'  + same.apply(lowInclusive) + low) : "")
         + (withLow && withHigh ? " - " : "")
         + (withHigh ?  ('<'  + same.apply(highInclusive) + high) : "");
  }
}
