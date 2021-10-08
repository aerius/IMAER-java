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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import nl.overheid.aerius.shared.domain.geo.OrientedEnvelope;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.geojson.Polygon;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 *
 */
class ConstructPolygonTest {

  @ParameterizedTest
  @MethodSource("provideTestCases")
  void testConstructPolygonFromDimensions(final Point point,
      final double width, final double length, final double orientation, final double[][] expectedCoordinates) throws AeriusException {
    final double testDelta = 0.001;
    final Polygon polygon = GeometryUtil.constructPolygonFromDimensions(point, length, width, orientation);
    assertEquals(1, polygon.getCoordinates().length, "Only outer ring");
    assertEquals(5, polygon.getCoordinates()[0].length, "5 coordinates");
    for (final double[] coordinate : polygon.getCoordinates()[0]) {
      assertEquals(2, coordinate.length, "Each coordinate should have x and y");
      boolean found = false;
      for (final double[] expectedCoordinate : expectedCoordinates) {
        if (Math.abs(coordinate[0] - expectedCoordinate[0]) < testDelta
            && Math.abs(coordinate[1] - expectedCoordinate[1]) < testDelta) {
          found = true;
        }
      }
      assertTrue(found, "Unexpected coordinate: " + Arrays.toString(coordinate));
    }
  }

  @ParameterizedTest
  @MethodSource("provideTestCases")
  void testDetermineOrientedEnvelope(final Point point,
      final double expectedWidth, final double expectedLength, final double expectedOrientation, final double[][] coordinates)
      throws AeriusException {
    final double testDelta = 0.001;
    final Polygon polygon = new Polygon();
    polygon.setCoordinates(new double[][][] {coordinates});
    final OrientedEnvelope orientedEnvelope = GeometryUtil.determineOrientedEnvelope(polygon);
    assertEquals(expectedLength, orientedEnvelope.getLength(), testDelta, "Length of oriented envelope");
    assertEquals(expectedWidth, orientedEnvelope.getWidth(), testDelta, "Width of oriented envelope");
    assertEquals(expectedOrientation, orientedEnvelope.getOrientation(), testDelta, "Orientation of oriented envelope");
  }

  private static Stream<Arguments> provideTestCases() {
    return Stream.of(
        testCase(2.5, 5, 5, 10, 90,
            new double[][] {{0.0, 0.0}, {5.0, 0.0}, {0.0, 10.0}, {5.0, 10.0}, {0.0, 0.0}}),
        testCase(5, 2.5, 5, 10, 0,
            new double[][] {{0.0, 0.0}, {10.0, 0.0}, {0.0, 5.0}, {10.0, 5.0}, {0.0, 0.0}}),
        testCase(5, 5.5, 5, 10, 53.130,
            new double[][] {{0.0, 3.0}, {4.0, 0.0}, {10.0, 8.0}, {6.0, 11.0}, {0.0, 3.0}}),
        testCase(5.5, 5, 5, 10, 143.130,
            new double[][] {{0.0, 6.0}, {8.0, 0.0}, {11.0, 4.0}, {3.0, 10.0}, {0.0, 6.0}}));
  }

  private static Arguments testCase(final double centerX, final double centerY, final double width, final double length, final double orientation,
      final double[][] expectedCoordinates) {
    final nl.overheid.aerius.shared.domain.v2.geojson.Point point = new nl.overheid.aerius.shared.domain.v2.geojson.Point(centerX, centerY);
    return Arguments.of(point, width, length, orientation, expectedCoordinates);
  }

}
