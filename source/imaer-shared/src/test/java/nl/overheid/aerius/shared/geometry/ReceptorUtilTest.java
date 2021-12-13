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
package nl.overheid.aerius.shared.geometry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import nl.overheid.aerius.geo.shared.BBox;
import nl.overheid.aerius.geo.shared.EPSGProxy;
import nl.overheid.aerius.shared.domain.geo.HexagonZoomLevel;
import nl.overheid.aerius.shared.domain.geo.ReceptorGridSettings;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.point.ReceptorPoint;

/**
 * Test class for {@link ReceptorUtil}.
 */
class ReceptorUtilTest {
  private static final double[][] RPS = {
      {1, 3604, 296800},
      {800, 152314.656485, 296800},
      {1529, 287996.844942, 296800},
      {1530, 3697.06048591021, 296853.72849659115},
      {3058, 288089.905428, 296853.72849659115},
      {3059, 3604, 296907.45699318236},
      {3060, 3790.12097182, 296907.45699318236},
      {3061, 3976.24194364, 296907.45699318236},
      {291550, 196983.689721416, 307008.41435234},
      {293046, 190934.758137253, 307062.142848915},
      {4706906, 123279.78488053002, 462176.3125076431},
      {9184376, 227135.287156324, 619493.35052661}};

  private static final HexagonZoomLevel ZOOM_LEVEL_1 = new HexagonZoomLevel(1, 10_000);
  private static final BBox RECEPTOR_BBOX = new BBox(3604, 296800, 287959, 629300);

  private static final ArrayList<HexagonZoomLevel> hexagonZoomLevels = createZoomLevels();
  private static final ReceptorGridSettings RGS = new ReceptorGridSettings(RECEPTOR_BBOX, EPSGProxy.defaultEpsg(), 1529, hexagonZoomLevels);
  private static final ReceptorUtil RECEPTOR_UTIL = new ReceptorUtil(RGS);

  @Test
  void testRPFromAndToRandom() {
    final Random random = new Random();
    random.setSeed(1337);

    for (int i = 0; i < 1000; i++) {
      final int receptorId1 = random.nextInt(9184376);
      final Point r = RECEPTOR_UTIL.getPointFromReceptorId(receptorId1);
      final int receptorId2 = RECEPTOR_UTIL.getReceptorIdFromPoint(r);
      final int receptorId3 = RECEPTOR_UTIL.getReceptorIdFromPoint(r);
      Assertions.assertEquals(receptorId1, receptorId2, "Random check for values (exact)");
      Assertions.assertEquals(receptorId1, receptorId3, "Random check for values");
    }

    for (int i = 0; i < 1000; i++) {
      final double xCoord = 3604 + random.nextDouble() * (227135 - 3604 + 1);
      final double yCoord = 296800 + random.nextDouble() * (619493 - 296800 + 1);
      final Point point = new Point(xCoord, yCoord);
      final int r1 = RECEPTOR_UTIL.getReceptorIdFromPoint(point);
      final int r2 = RECEPTOR_UTIL.getReceptorIdFromPoint(point);

      final Point r3 = RECEPTOR_UTIL.getPointFromReceptorId(r1);
      final Point r4 = RECEPTOR_UTIL.getPointFromReceptorId(r2);

      Assertions.assertEquals(r3.getX(), r3.getX(), ZOOM_LEVEL_1.getHexagonRadius(), "Random check for values X coord");
      Assertions.assertEquals(r3.getY(), r3.getY(), ZOOM_LEVEL_1.getHexagonHeight() / 2, "Random check for values Y coord");

      Assertions.assertEquals(r4.getX(), r4.getX(), ZOOM_LEVEL_1.getHexagonRadius(), "Random check for values X coord (exact)");
      Assertions.assertEquals(r4.getY(), r4.getY(), ZOOM_LEVEL_1.getHexagonHeight() / 2, "Random check for values Y coord (exact)");
    }
  }

  private static ArrayList<HexagonZoomLevel> createZoomLevels() {
    final ArrayList<HexagonZoomLevel> zoomLevels = new ArrayList<>();
    zoomLevels.add(ZOOM_LEVEL_1);
    zoomLevels.add(new HexagonZoomLevel(2, 40_000));
    zoomLevels.add(new HexagonZoomLevel(3, 160_000));
    zoomLevels.add(new HexagonZoomLevel(4, 640_000));
    zoomLevels.add(new HexagonZoomLevel(5, 2_560_000));
    return zoomLevels;
  }

  @Test
  void setAeriusPointFromIdTest() {
    for (final double[] element : RPS) {
      final Point rp = RECEPTOR_UTIL.getPointFromReceptorId((int) element[0]);

      Assertions.assertEquals(element[1], rp.getX(), 0.000001, "Receptor(" + element[0] + ") X:");
      Assertions.assertEquals(element[2], rp.getY(), 0.000001, "Receptor(" + element[0] + ") Y:");
    }
  }

  @Test
  void setReceptorIdFromPointTest() {
    for (int i = 0; i < RPS.length; i++) {
      final Point point = new Point(RPS[i][1], RPS[i][2]);
      final int rp = RECEPTOR_UTIL.getReceptorIdFromPoint(point);

      Assertions.assertEquals((int) RPS[i][0], rp, "Receptor(" + i + ":" + RPS[i][1] + ", " + RPS[i][2] + ") ID:");
    }
  }

  @Test
  void setReceptorIdFromPointRandomTest() {
    final Random random = new Random();
    random.setSeed(1337);

    for (int i = 0; i < RPS.length; i++) {
      final int sign = random.nextInt() % 2 == 0 ? 1 : -1;
      final Point point = new Point(
          RPS[i][1] + sign * random.nextDouble() * ZOOM_LEVEL_1.getHexagonRadius() / 2,
          RPS[i][2] + sign * random.nextDouble() * ZOOM_LEVEL_1.getHexagonHeight() / 2);
      final int rp = RECEPTOR_UTIL.getReceptorIdFromPoint(point);

      Assertions.assertEquals((int) RPS[i][0], rp, "Receptor(" + i + ":" + point.getX() + ", " + point.getY() + ") ID:");
    }
  }

  @Test
  void setReceptorIdFromPointMaximums() {
    final Random random = new Random();
    random.setSeed(1337);
    final double divergeFromMaximum = 0.000001;

    // left-right middle corners
    for (int i = 0; i < RPS.length; i++) {
      final int sign = random.nextInt() % 2 == 0 ? 1 : -1;
      final Point point = new Point(
          RPS[i][1] + sign * (ZOOM_LEVEL_1.getHexagonRadius() - divergeFromMaximum),
          RPS[i][2]);
      final int rp = RECEPTOR_UTIL.getReceptorIdFromPoint(point);

      Assertions.assertEquals((int) RPS[i][0], rp, "Receptor(" + i + ":" + point.getX() + ", " + point.getY() + ") ID:");
    }
    // left-right top corners
    for (int i = 0; i < RPS.length; i++) {
      final int sign = random.nextInt() % 2 == 0 ? 1 : -1;
      final Point point = new Point(
          RPS[i][1] + sign * (ZOOM_LEVEL_1.getHexagonRadius() / 2 - divergeFromMaximum),
          RPS[i][2] + (ZOOM_LEVEL_1.getHexagonHeight() / 2 - divergeFromMaximum));
      final int rp = RECEPTOR_UTIL.getReceptorIdFromPoint(point);

      Assertions.assertEquals((int) RPS[i][0], rp, "Receptor(" + i + ":" + point.getX() + ", " + point.getY() + ") ID:");
    }
    // left-right bottom corners
    for (int i = 0; i < RPS.length; i++) {
      final int sign = random.nextInt() % 2 == 0 ? 1 : -1;
      final Point point = new Point(
          RPS[i][1] + sign * (ZOOM_LEVEL_1.getHexagonRadius() / 2 - divergeFromMaximum),
          RPS[i][2] - (ZOOM_LEVEL_1.getHexagonHeight() / 2 - divergeFromMaximum));
      final int rp = RECEPTOR_UTIL.getReceptorIdFromPoint(point);

      Assertions.assertEquals((int) RPS[i][0], rp, "Receptor(" + i + ":" + point.getX() + ", " + point.getY() + ") ID:");
    }
  }

  @Test
  void testIsReceptorAtZoomLevel() {
    final Point inPoint = new Point(135378, 462284);
    final Point outPoint = new Point(134354, 462552);

    for (int i = 2; i < 5; i++) {
      final HexagonZoomLevel zoomLevel = new HexagonZoomLevel(i, ZOOM_LEVEL_1.getSurfaceLevel1());
      assertTrue(RECEPTOR_UTIL.isReceptorAtZoomLevel(inPoint, zoomLevel), "Point should be return as a receptor at zoomLevel " + i);
      assertFalse(RECEPTOR_UTIL.isReceptorAtZoomLevel(outPoint, zoomLevel), "Point should be return not as a receptor at zoomLevel " + i);
    }
  }

  @Test
  void testAttachReceptor() {
    final Random random = new Random();
    random.setSeed(1337);

    for (int i = 0; i < 1000; i++) {
      final double xCoord = 3604 + random.nextDouble() * (227135 - 3604 + 1);
      final double yCoord = 296800 + random.nextDouble() * (619493 - 296800 + 1);
      final Point p1 = new Point(xCoord, yCoord);
      final Point p2 = new Point(xCoord, yCoord);
      final CalculationPointFeature feature = new CalculationPointFeature();
      feature.setGeometry(p2);
      feature.setProperties(new ReceptorPoint());
      RECEPTOR_UTIL.attachReceptorToGrid(feature);

      Assertions.assertEquals(0, distance(p1, feature.getGeometry()), ZOOM_LEVEL_1.getHexagonRadius());
    }
  }

  private double distance(final Point p1, final Point p2) {
    final double tx = p1.getX() - p2.getX();
    final double ty = p1.getY() - p2.getY();

    return Math.sqrt(tx * tx + ty * ty);
  }

  @ParameterizedTest
  @MethodSource("zoomLevelReceptors")
  void testGetZoomLevelForReceptor(final int id, final int expectedZoomLevel) {
    assertEquals(expectedZoomLevel, RECEPTOR_UTIL.getZoomLevelForReceptor(id), "Not the expected zoomlevel for id " + id);
  }

  private static Object[][] zoomLevelReceptors() {
    return new Object[][] {{4512290, 1}, {4512011, 1}, {4512003, 1}, {4511888, 2}, {4502301, 3}, {4502425, 4}, {4502305, 5}};
  }
}
