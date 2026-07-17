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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import nl.overheid.aerius.shared.domain.IntRange;

/**
 * Test class for IntRangeUtil {@link IntRange}.
 */
class IntRangeTest {

  @ParameterizedTest
  @MethodSource("inRange")
  void testInRange(final String range, final int value) {
    final IntRange intRange = IntRangeUtil.valueOf(range);

    assertTrue(intRange.inRange(value), "Value expected to be in the range.");
  }

  static Stream<Arguments> inRange() {
    return Stream.of(
        Stream.of(1, 19).map(i -> Arguments.of("(,20)", i)),
        Stream.of(1, 19, 20).map(i -> Arguments.of("(,20]", i)),
        Stream.of(10, 11, 19, 20).map(i -> Arguments.of("[10,20]", i)),
        Stream.of(11, 19, 20).map(i -> Arguments.of("(10,20]", i)),
        Stream.of(11, 19).map(i -> Arguments.of("(10,20)", i)),
        Stream.of(10, 11, 19).map(i -> Arguments.of("[10,20)", i)),
        Stream.of(10, 11, 200).map(i -> Arguments.of("[10,)", i)),
        Stream.of(11, 200).map(i -> Arguments.of("(10,)", i)))
        .flatMap(Function.identity());
  }

  @ParameterizedTest
  @MethodSource("outsideRange")
  void testOutsideRange(final String range, final int value) {
    final IntRange intRange = IntRangeUtil.valueOf(range);

    assertFalse(intRange.inRange(value), "Value not expected to be in the range.");
  }

  static Stream<Arguments> outsideRange() {
    return Stream.of(
        Stream.of(20).map(i -> Arguments.of("(,20)", i)),
        Stream.of(21).map(i -> Arguments.of("[,20]", i)),
        Stream.of(9, 21).map(i -> Arguments.of("[10,20]", i)),
        Stream.of(9, 10, 21).map(i -> Arguments.of("(10,20]", i)),
        Stream.of(10, 20).map(i -> Arguments.of("(10,20)", i)),
        Stream.of(9, 20).map(i -> Arguments.of("[10,20)", i)),
        Stream.of(9).map(i -> Arguments.of("[10,)", i)),
        Stream.of(10).map(i -> Arguments.of("(10,)", i)))
        .flatMap(Function.identity());
  }

  @ParameterizedTest
  @MethodSource("toStringData")
  void testToString(final String range, final String expectedString) {
    final IntRange intRange = IntRangeUtil.valueOf(range);

    assertEquals(expectedString, intRange.toString(), "Not the expected to String value");
  }

  static List<Arguments> toStringData() {
    return List.of(
        Arguments.of("[,20)", "<20"),
        Arguments.of("[,20]", "<=20"),
        Arguments.of("[10,20]", ">=10 - <=20"),
        Arguments.of("(10,20]", ">10 - <=20"),
        Arguments.of("(10,20)", ">10 - <20"),
        Arguments.of("[10,20)", ">=10 - <20"),
        Arguments.of("[10,)", ">=10"),
        Arguments.of("(10,)", ">10"));
  }
}
