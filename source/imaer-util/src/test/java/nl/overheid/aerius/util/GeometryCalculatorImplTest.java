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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import nl.overheid.aerius.shared.domain.v2.geojson.LineString;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.geojson.Polygon;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.geometry.GeometryCalculator;

class GeometryCalculatorImplTest {

  @Test
  void testDetermineMeasureNullGeometry() {
    final GeometryCalculator calculator = new GeometryCalculatorImpl();
    assertEquals(0.0, calculator.determineMeasure(null));
  }

  @Test
  void testDetermineMeasurePoint() {
    final GeometryCalculator calculator = new GeometryCalculatorImpl();
    assertEquals(1.0, calculator.determineMeasure(new Point(432, 23)));
  }

  @Test
  void testDetermineMeasureLineString() {
    final GeometryCalculator calculator = new GeometryCalculatorImpl();
    final LineString lineString = new LineString();
    lineString.setCoordinates(new double[][] {{100, 100}, {100, 300}});
    assertEquals(200.0, calculator.determineMeasure(lineString));
  }

  @Test
  void testDetermineMeasureLineStringIncomplete() {
    final GeometryCalculator calculator = new GeometryCalculatorImpl();
    final LineString lineString = new LineString();
    lineString.setCoordinates(new double[][] {{100, 100}});
    assertEquals(0.0, calculator.determineMeasure(lineString));
  }

  @Test
  void testDetermineMeasurePolygon() {
    final GeometryCalculator calculator = new GeometryCalculatorImpl();
    final Polygon polygon = new Polygon();
    polygon.setCoordinates(new double[][][] {{{100, 100}, {100, 300}, {200, 300}, {200, 100}, {100, 100}}});
    assertEquals(200.0 * 100.0, calculator.determineMeasure(polygon));
  }

  @Test
  void testDetermineMeasurePolygonIncomplete() {
    final GeometryCalculator calculator = new GeometryCalculatorImpl();
    final Polygon polygon = new Polygon();
    polygon.setCoordinates(new double[][][] {{{100, 100}, {100, 300}}});
    assertEquals(0.0, calculator.determineMeasure(polygon));
  }

  @Test
  void testDetermingBoundingBoxEmptyList() throws AeriusException {
    final GeometryCalculator calculator = new GeometryCalculatorImpl();

    assertNull(calculator.determineBoundingBox(List.of()), "Bounding box of empty list should be null");
  }

  @Test
  void testDetermineBoundingBoxForPoint() throws AeriusException {
    final GeometryCalculator calculator = new GeometryCalculatorImpl();
    final Point p = new Point(0, 0);

    assertEquals(p, calculator.determineBoundingBox(List.of(p)),"The bounding box of a single point should be that point.");
  }

  @Test
  void testDetermineBoundingBoxForLine() throws AeriusException {
    final GeometryCalculator calculator = new GeometryCalculatorImpl();
    final LineString l = new LineString();
    l.setCoordinates(new double[][] {{0, 0}, {1, 1}});

    final Polygon expectedEnvelope = new Polygon();
    expectedEnvelope.setCoordinates(new double[][][] {{{0, 0}, {0, 1}, {1, 1}, {1, 0}, {0, 0}}});
    assertEquals(expectedEnvelope, calculator.determineBoundingBox(List.of(l)),
        "The bounding box of a line should be a polygon around that line aligned with the grid");
  }

  @Test
  void testDetermineBoundingBoxForPolygon() throws AeriusException {
    final GeometryCalculator calculator = new GeometryCalculatorImpl();
    final Polygon p = new Polygon();
    p.setCoordinates(new double[][][] {{{0.5, 0}, {0, 0.5}, {0.5, 1}, {1, 0.5}, {0.5, 0}}});

    final Polygon expectedEnvelope = new Polygon();
    expectedEnvelope.setCoordinates(new double[][][] {{{0, 0}, {0, 1}, {1, 1}, {1, 0}, {0, 0}}});
    assertEquals(expectedEnvelope, calculator.determineBoundingBox(List.of(p)),
        "The bounding box of an angled polygon should align with grid");
  }

  @Test
  void testDetermineBoundingBoxForGeometryCombination() throws AeriusException {
    final GeometryCalculator calculator = new GeometryCalculatorImpl();
    final Point p = new Point(0, 0);

    final LineString l = new LineString();
    l.setCoordinates(new double[][] {{1, 1}, {2, 2}});

    final Polygon polygon = new Polygon();
    polygon.setCoordinates(new double[][][] {{{2, 2}, {2, 3}, {3, 3}, {3, 2}, {2, 2}}});

    final Polygon expectedEnvelope = new Polygon();
    expectedEnvelope.setCoordinates(new double[][][] {{{0, 0}, {0, 3}, {3, 3}, {3, 0}, {0, 0}}});

    assertEquals(expectedEnvelope, calculator.determineBoundingBox(List.of(p, l, polygon)),
        "The bounding box of an multiple geometries should align with grid and contain all geometries.");
  }

  @Test
  void testScaleBoundingBoxPoint() throws AeriusException {
    final GeometryCalculator calculator = new GeometryCalculatorImpl();

    final Point point = new Point(0.5, 0.5);

    final double[][] expected = {{0, 0}, {0, 1}, {1, 1}, {1, 0}, {0, 0}};
    final Polygon actual = (Polygon) calculator.scaleBoundingBox(point, 2, 1);

    assertEquals(1, actual.getCoordinates().length, "Bounding box geometry does not contain holes");
    assertEquals(expected.length, actual.getCoordinates()[0].length, "Bounding box should contain 5 coordinates");
    assertTrue(Arrays.stream(actual.getCoordinates()[0]).allMatch(c1 -> Arrays.stream(expected).anyMatch(c2 -> c2[0] == c1[0] && c2[1] == c1[1])),
        "Scaling a zero area rectangle should result in a square of min dimensions around the coordinate.");
  }

  @Test
  void testScaleBoundingBoxMinDimensionExceeded() throws AeriusException {
    final GeometryCalculator calculator = new GeometryCalculatorImpl();

    // 1 x 1 square around (0.5 0.5)
    final Polygon polygon = new Polygon();
    polygon.setCoordinates(new double[][][] {{{0, 0}, {1, 0}, {1, 1}, {0, 1}, {0, 0}}});

    // 2 x 2 square around (0.5 0.5)
    final double[][] expected = {{-0.5, -0.5}, {-0.5, 1.5}, {1.5, 1.5}, {1.5, -0.5}, {-0.5, -0.5}};
    final Polygon actual = (Polygon) calculator.scaleBoundingBox(polygon, 2, 1);

    assertEquals(1, actual.getCoordinates().length, "Bounding box geometry does not contain holes");
    assertEquals(expected.length, actual.getCoordinates()[0].length, "Bounding box should contain 5 coordinates");
    assertTrue(Arrays.stream(actual.getCoordinates()[0]).allMatch(c1 -> Arrays.stream(expected).anyMatch(c2 -> c2[0] == c1[0] && c2[1] == c1[1])),
        "Scaling a 1 x 1 square by 2 should result in a 2 x 2 square around the original center.");
  }

  @Test
  void testScaleBoundingBoxMinDimensionNotExceeded() throws AeriusException {
    final GeometryCalculator calculator = new GeometryCalculatorImpl();

    // 1 x 1 square around (0.5 0.5)
    final Polygon polygon = new Polygon();
    polygon.setCoordinates(new double[][][] {{{0, 0}, {1, 0}, {1, 1}, {0, 1}, {0, 0}}});

    // 3 x 3 square around (0.5 0.5)
    final double[][] expected = {{-1, -1}, {-1, 2}, {2, 2}, {2, -1}, {-1, -1}};
    final Polygon actual = (Polygon) calculator.scaleBoundingBox(polygon, 2, 3);

    assertEquals(1, actual.getCoordinates().length, "Bounding box geometry does not contain holes");
    assertEquals(expected.length, actual.getCoordinates()[0].length, "Bounding box should contain 5 coordinates");
    assertTrue(Arrays.stream(actual.getCoordinates()[0]).allMatch(c1 -> Arrays.stream(expected).anyMatch(c2 -> c2[0] == c1[0] && c2[1] == c1[1])),
        "Scaling a 1 x 1 square by 2 and minimum dimension 3 should result in a 3 x 3 square around the original center.");
  }

  @Test
  void testScaleBoundingBoxInvalidGeometry() {
    final GeometryCalculator calculator = new GeometryCalculatorImpl();

    // Non-rectangle polygon
    final Polygon polygon = new Polygon();
    polygon.setCoordinates(new double[][][] {{{0, 0}, {2, 0}, {2, 2}, {1, 1}, {0, 2}, {0, 0}}});

    assertThrows(IllegalArgumentException.class, () -> calculator.scaleBoundingBox(polygon, 1, 1),
        "Scaling a non-rectangle should result in an illegalargumen exception.");
  }
}
