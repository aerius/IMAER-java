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
package nl.overheid.aerius.shared.domain.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nl.aerius.shared.domain.geojson.Point;
import nl.aerius.shared.domain.geojson.Polygon;

/**
 * Test class for {@link HexagonUtil}.
 */
public class HexagonUtilTest {

  private static final HexagonZoomLevel ZOOM_LEVEL_1 = new HexagonZoomLevel(1, 10000);

  @Test
  public void testCreateHexagon() {
    assertCreateHexagon();
    // Run twice to test if cache works.
    assertCreateHexagon();
  }

  private void assertCreateHexagon() {
    final Point point = new Point(ZOOM_LEVEL_1.getHexagonRadius(), ZOOM_LEVEL_1.getHexagonHeight() / 2);
    final Polygon polygon = HexagonUtil.createHexagon(point, ZOOM_LEVEL_1);

    Assertions.assertArrayEquals(new double[][][] {{{93, 107}, {124, 54}, {93, 0}, {31, 0}, {0, 54}, {31, 107}, {93, 107}}}, polygon.getCoordinates(),
        "createHexagon zoom level 1");
  }
}
