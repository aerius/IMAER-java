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
package nl.overheid.aerius.shared.domain.v2.geojson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nl.aerius.shared.domain.geojson.Point;

/**
 * Unit test for {@link Point}.
 */
public class PointTest {

  @Test
  public void testGetRoundedCmX() {
    assertRoundingCmx(100.009, 100, 100.01);
    assertRoundingCmx(100.002, 100, 100.0);
    assertRoundingCmx(100.2, 100, 100.2);
    assertRoundingCmx(0, 100, 0);
  }

  private void assertRoundingCmx(final double x, final double y, final double expected) {
    assertEquals(expected, new Point(x, y).getRoundedCmX(), 0.0001, "Should have correctly rounded x");
  }

  @Test
  public void testGetRoundedCmY() {
    assertRoundingCmY(100, 100.009, 100.01);
    assertRoundingCmY(100, 100.002, 100.0);
    assertRoundingCmY(100, 100.2, 100.2);
    assertRoundingCmY(100, 0, 0);
  }

  private void assertRoundingCmY(final double x, final double y, final double expected) {
    assertEquals(expected, new Point(x, y).getRoundedCmY(), 0.0001, "Should have correctly rounded y");
  }
}
