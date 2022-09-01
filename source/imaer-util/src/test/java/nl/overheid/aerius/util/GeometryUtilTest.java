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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Geometry;

import nl.aerius.shared.geo.RDNew;
import nl.aerius.shared.geo.WKTGeometry;
import nl.overheid.aerius.shared.domain.v2.geojson.GeometryType;
import nl.overheid.aerius.shared.domain.v2.geojson.LineString;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.geojson.Polygon;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.shared.geometry.GeometryCalculator;

/**
 * Util class for {@link GeometryUtil}.
 */
class GeometryUtilTest {

  private static double EXAMPLE_POINT_X = 7.6;
  private static double EXAMPLE_POINT_Y = 9.1;
  private static final GeometryCalculator GEOMETRY_CALCULATOR = new GeometryCalculatorImpl();

  @Test
  void testValidWKT() {
    assertTrue(GeometryUtil.validWKT(getExamplePoint().getWKT()), "Valid WKT");
    assertFalse(GeometryUtil.validWKT(getExamplePoint().getWKT().substring(1)), "Invalid WKT string");
    assertFalse(GeometryUtil.validWKT(null), "Invalid WKT null");
    assertFalse(GeometryUtil.validWKT(""), "Invalid WKT empty string");
    assertFalse(GeometryUtil.validWKT(" "), "Invalid WKT only space");
  }

  @Test
  void testHasIntersections() throws AeriusException {
    final int side = 4000;
    //create normal linestring with points (0,0), (0,4000), (4000,4000), (0,4000)
    final String normalLineStringWKT = "LineString(0 0," + side + " 0," + side + " " + side + ",0 " + side + ")";
    assertFalse(GeometryUtil.hasIntersections(normalLineStringWKT), "Normal linestring geometry shouldn't count as intersected");

    //create normal polygon with points (0,0), (0,4000), (4000,4000), (0,4000), (0,0)
    final String normalPolygonWKT = "POLYGON((0 0," + side + " 0," + side + " " + side + ",0 " + side + ",0 0))";
    assertFalse(GeometryUtil.hasIntersections(normalPolygonWKT), "Normal polygon geometry shouldn't count as intersected");

    //create Bow-Tie linestring with points (0,0), (4000,4000), (4000,0), (0,4000)
    final String bowtieLineStringWKT = "LINESTRING(0 0," + side + " " + side + "," + side + " 0,0 " + side + ")";
    assertFalse(GeometryUtil.hasIntersections(bowtieLineStringWKT), "Bowtie linestring geometry should not count as intersected");

    //create Bow-Tie polygon with points (0,0), (4000,4000), (4000,0), (0,4000), (0,0)
    final String bowtiePolygonWKT = "POLYGON((0 0," + side + " " + side + "," + side + " 0,0 " + side + ",0 0))";
    assertTrue(GeometryUtil.hasIntersections(bowtiePolygonWKT), "Bowtie polygon geometry should count as intersected");

    final String invalidWKT = "POLYGON((0 0," + side + " " + side + "," + side + " 0,0 " + side + ",0 0)";
    final AeriusException e = assertThrows(
        AeriusException.class,
        () -> GeometryUtil.hasIntersections(invalidWKT),
        "Incorrect WKT should throw an exception");
    assertEquals(ImaerExceptionReason.GEOMETRY_INVALID, e.getReason(), "Reason for exception");
  }

  @Test
  void testConvertToPoints() throws AeriusException {
    //create linestring with (0,0), (0,4000), (0,8000), (0,12000), (0,16000)
    final LineString geometry = new LineString();
    geometry.setCoordinates(new double[][] {{0, 0}, {0, 4000}, {0, 8000}, {0, 12000}, {0, 16000}});
    final Double maxSegementSize = 76.4;
    final List<Point> convertedPoints = GeometryUtil.convertToPoints(geometry, maxSegementSize);
    final double length = GEOMETRY_CALCULATOR.determineMeasure(geometry);

    assertEquals(new BigDecimal(length).divide(new BigDecimal(maxSegementSize),
        RoundingMode.UP).intValue(),
        convertedPoints.size(),
        "Number of segments");
    final double segmentSize = new BigDecimal(length).divide(new BigDecimal(convertedPoints.size()), 4,
        RoundingMode.HALF_UP).doubleValue();
    for (int i = 0; i < convertedPoints.size(); i++) {
      final Point convertedPoint = convertedPoints.get(i);
      assertEquals(0, convertedPoint.getX(), 1E-2, "X-coord of point " + i);
      assertEquals(i * segmentSize + segmentSize / 2.0, convertedPoint.getY(), 1E-2, "Y-coord of point " + i);
    }
  }

  @Test
  void testConvertToPointsZigZag() throws AeriusException {
    //create linestring that zigs n zags
    final LineString geometry = new LineString();
    geometry.setCoordinates(new double[][] {{0, 0}, {0, 4000}, {1000, 4000}, {1000, 12000}, {3000, 12000}});
    final double length = GEOMETRY_CALCULATOR.determineMeasure(geometry);
    final Double maxSegementSize = 76.4;
    final List<Point> convertedPoints = GeometryUtil.convertToPoints(geometry, maxSegementSize);
    assertEquals(new BigDecimal(length).divide(new BigDecimal(maxSegementSize), 0,
        RoundingMode.UP).intValue(),
        convertedPoints.size(),
        "Number of segments");
    for (int i = 0; i < convertedPoints.size(); i++) {
      final Point convertedPoint = convertedPoints.get(i);
      if (convertedPoint.getX() == 0) {
        assertTrue(convertedPoint.getY() >= 0, "Point wasn't on the line " + i + ", point: " + convertedPoint);
        assertTrue(convertedPoint.getY() <= 4000, "Point wasn't on the line " + i + ", point: " + convertedPoint);
      } else if (convertedPoint.getY() == 4000) {
        assertTrue(convertedPoint.getX() >= 0, "Point wasn't on the line " + i + ", point: " + convertedPoint);
        assertTrue(convertedPoint.getX() <= 1000, "Point wasn't on the line " + i + ", point: " + convertedPoint);
      } else if (convertedPoint.getX() == 1000) {
        assertTrue(convertedPoint.getY() >= 4000, "Point wasn't on the line " + i + ", point: " + convertedPoint);
        assertTrue(convertedPoint.getY() <= 12000, "Point wasn't on the line " + i + ", point: " + convertedPoint);
      } else if (convertedPoint.getY() == 12000) {
        assertTrue(convertedPoint.getX() >= 1000, "Point wasn't on the line " + i + ", point: " + convertedPoint);
        assertTrue(convertedPoint.getX() <= 3000, "Point wasn't on the line " + i + ", point: " + convertedPoint);
      } else {
        fail("Point wasn't on the line " + i + ", point: " + convertedPoint);
      }
    }
  }

  @Test
  void testToConvexHull() throws AeriusException {
    final Polygon geometry = (Polygon) GeometryUtil.getAeriusGeometry(GeometryUtil.getGeometry("POLYGON ((0 0, 0 100, 20 100, 15 50, 20 0, 0 0))"));
    final Polygon convexHull = GeometryUtil.toConvexHull(geometry, RDNew.SRID);
    final Polygon expectedGeometry = (Polygon) GeometryUtil.getAeriusGeometry(GeometryUtil.getGeometry("POLYGON ((0 0, 0 100, 20 100, 20 0, 0 0))"));

    assertEquals(expectedGeometry, convexHull, "Should be expected convex geometry");
  }

  @Test
  void testGetGeometry() throws AeriusException {
    final Point point = new Point();
    point.setCoordinates(new double[] {1000, 4000});
    final Geometry convertedPoint = GeometryUtil.getGeometry(point);

    assertTrue(convertedPoint instanceof org.locationtech.jts.geom.Point, "Point should conver to JTS point");

    final LineString lineString = new LineString();
    lineString.setCoordinates(new double[][] {{0, 0}, {0, 4000}, {0, 8000}, {0, 12000}, {0, 16000}});
    final Geometry convertedLineString = GeometryUtil.getGeometry(lineString);

    assertTrue(convertedLineString instanceof org.locationtech.jts.geom.LineString, "LineString should conver to JTS LineString");

    final Polygon polygon = new Polygon();
    polygon.setCoordinates(new double[][][] {{{0, 0}, {0, 4000}, {1000, 4000}, {1000, 12000}, {3000, 12000}, {0, 0}}});
    final Geometry convertedPolygon = GeometryUtil.getGeometry(polygon);

    assertTrue(convertedPolygon instanceof org.locationtech.jts.geom.Polygon, "Polygon should conver to JTS polygon");

    final Polygon polygonWithHole = new Polygon();
    polygonWithHole.setCoordinates(new double[][][] {{{0, 0}, {0, 4000}, {1000, 4000}, {1000, 12000}, {3000, 12000}, {0, 0}},
        {{200, 300}, {400, 300}, {400, 600}, {200, 600}, {200, 300}}});
    final Geometry convertedPolygonWithHole = GeometryUtil.getGeometry(polygonWithHole);

    assertTrue(convertedPolygonWithHole instanceof org.locationtech.jts.geom.Polygon, "Polygon with hole should convert to JTS polygon");

    final nl.overheid.aerius.shared.domain.v2.geojson.Geometry anonymousGeometry = new nl.overheid.aerius.shared.domain.v2.geojson.Geometry() {
      private static final long serialVersionUID = 1L;

      @Override
      public GeometryType type() {
        return null;
      }
    };
    final AeriusException exceptionOnUnknownType = assertThrows(AeriusException.class, () -> GeometryUtil.getGeometry(anonymousGeometry));
    assertEquals(ImaerExceptionReason.GEOMETRY_INVALID, exceptionOnUnknownType.getReason(), "Reason for unknown type");

    final LineString lineStringWithoutCoords = new LineString();
    lineStringWithoutCoords.setCoordinates(new double[][] {{}});
    final AeriusException exceptionOnLineStringWithoutCoords = assertThrows(AeriusException.class, () -> GeometryUtil.getGeometry(lineStringWithoutCoords));
    assertEquals(ImaerExceptionReason.GEOMETRY_INVALID, exceptionOnLineStringWithoutCoords.getReason(), "Reason for linestring with 1 coord");

    final LineString lineStringWithIncorrectCoord = new LineString();
    lineStringWithIncorrectCoord.setCoordinates(new double[][] {{939, 32423, 234}});
    final AeriusException exceptionOnLineStringWithIncorrectCoord = assertThrows(AeriusException.class, () -> GeometryUtil.getGeometry(lineStringWithIncorrectCoord));
    assertEquals(ImaerExceptionReason.GEOMETRY_INVALID, exceptionOnLineStringWithIncorrectCoord.getReason(), "Reason for linestring with 1 coord");


    final Polygon polygonWithoutCoords = new Polygon();
    polygonWithoutCoords.setCoordinates(new double[][][] {});
    final AeriusException exceptionOnPolygonWithoutCoords = assertThrows(AeriusException.class, () -> GeometryUtil.getGeometry(polygonWithoutCoords));
    assertEquals(ImaerExceptionReason.GEOMETRY_INVALID, exceptionOnPolygonWithoutCoords.getReason(), "Reason for polygon with 2 coord");
  }

  private WKTGeometry getExamplePoint() {
    return new WKTGeometry(
        "POINT(" + EXAMPLE_POINT_X + " " + EXAMPLE_POINT_Y + ")", 1);
  }
}
