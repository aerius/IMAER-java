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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.geojson.Polygon;

/**
 * Test class for {@link HexagonUtil}.
 */
class HexagonUtilTest {

  private static final HexagonZoomLevel ZOOM_LEVEL_1 = new HexagonZoomLevel(1, 10000);

  @ParameterizedTest
  @MethodSource("createHexagon")
  void testCreateHexagon(final boolean useDefaultMethod, final double[][][] results) {
    final Point point = new Point(ZOOM_LEVEL_1.getHexagonRadius(), ZOOM_LEVEL_1.getHexagonHeight() / 2);
    // When round use the default createHexagon method to test, otherwise the version with roundingFunction
    final Polygon polygon = useDefaultMethod
        ? HexagonUtil.createHexagon(point, ZOOM_LEVEL_1)
        : HexagonUtil.createHexagon(point, ZOOM_LEVEL_1, d -> BigDecimal.valueOf(d).setScale(3, RoundingMode.HALF_UP).doubleValue());

    Assertions.assertArrayEquals(results, polygon.getCoordinates(), "createHexagon zoom level 1");
  }

  private static List<Arguments> createHexagon() {
    return List.of(
        Arguments.of(false,
            new double[][][] {{{93.06, 107.457}, {124.081, 53.728}, {93.06, 0.0}, {31.02, 0.0}, {0.0, 53.728}, {31.02, 107.457}, {93.06, 107.457}}}),
        Arguments.of(true, new double[][][] {{{93, 107}, {124, 54}, {93, 0}, {31, 0}, {0, 54}, {31, 107}, {93, 107}}}));
  }

  @Test
  void testGetDistanceLevelForCircle() {
    assertEquals(2.0, HexagonUtil.getDistanceLevelForCircle(100, ZOOM_LEVEL_1), 1E-7, "Not the expected distance level for circle");
  }
}
