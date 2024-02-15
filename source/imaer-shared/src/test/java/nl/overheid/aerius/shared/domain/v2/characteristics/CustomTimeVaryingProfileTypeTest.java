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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

/**
 *
 */
class CustomTimeVaryingProfileTypeTest {

  @Test
  void testSumDay() {
    final CustomTimeVaryingProfileType testingType = CustomTimeVaryingProfileType.DAY;
    final List<Double> values = IntStream.range(100, 124).asDoubleStream().boxed().collect(Collectors.toList());
    final int expected = IntStream.range(100, 124).sum();
    assertEquals(expected, testingType.sum(values), "Should use normal summation");
  }

  @Test
  void testSumThreeDay() {
    final CustomTimeVaryingProfileType testingType = CustomTimeVaryingProfileType.THREE_DAY;
    final List<Double> values = IntStream.range(100, 172).asDoubleStream().boxed().collect(Collectors.toList());
    final int expected = IntStream.range(100, 124).map(x -> x * 5).sum() + IntStream.range(124, 172).sum();
    assertEquals(expected, testingType.sum(values), "Should use special case summation");
  }

}
