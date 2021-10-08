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

import org.junit.jupiter.api.Test;

/**
 * Test class for {@link HexagonZoomLevel}.
 */
public class HexagonZoomLevelTest {

  private static final int SURFACE_LEVEL_1 = 10000;

  private static final Object[][] LEVELS = {
      {1, new HexagonZoomLevel(1, SURFACE_LEVEL_1), 62.04, 107.456},
      {2, new HexagonZoomLevel(2, SURFACE_LEVEL_1), 124.08, 214.912},
      {3, new HexagonZoomLevel(3, SURFACE_LEVEL_1), 248.16, 429.824},
      {4, new HexagonZoomLevel(4, SURFACE_LEVEL_1), 496.32, 859.648},
      {5, new HexagonZoomLevel(5, SURFACE_LEVEL_1), 992.64, 1719.311}};

  @Test
  public void levelFromScaleTest() {
    for (int i = 0; i < LEVELS.length; i++) {
      assertEquals((double) LEVELS[i][2], ((HexagonZoomLevel) LEVELS[i][1]).getHexagonRadius(), 0.01, "Hexagon Radius:" + LEVELS[i][0]);
      assertEquals((double) LEVELS[i][3], ((HexagonZoomLevel) LEVELS[i][1]).getHexagonHeight(), 0.01, "Hexagon Height:" + LEVELS[i][0]);
    }
  }
}
