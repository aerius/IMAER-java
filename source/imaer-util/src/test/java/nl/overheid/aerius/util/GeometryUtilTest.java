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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nl.overheid.aerius.geo.shared.BBox;
import nl.overheid.aerius.geo.shared.Point;
import nl.overheid.aerius.geo.shared.WKTGeometry;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
* Util class for
*/
public class GeometryUtilTest {

  private static double EXAMPLE_POINT_X = 7.6;
  private static double EXAMPLE_POINT_Y = 9.1;

  @Test
  public void testMiddleOfGeometry() throws AeriusException {
    final Point pointMid = GeometryUtil.middleOfGeometry(getExamplePoint());
    assertEquals(7.6, pointMid.getX(), 0.001, "Middlepoint of point X");
    assertEquals(9.1, pointMid.getY(), 0.001, "Middlepoint of point Y");
    //for linestring, the point should be on the line.
    final Point lineStringMid = GeometryUtil.middleOfGeometry(new WKTGeometry("LINESTRING(100 0,100 25,175 25)", 100));
    assertEquals(125, lineStringMid.getX(), 0.001, "Middlepoint of linestring X");
    assertEquals(25, lineStringMid.getY(), 0.001, "Middlepoint of linestring Y");
    //linestring: check short (length < 1) linestrings
    final Point lineStringShortMid = GeometryUtil.middleOfGeometry(new WKTGeometry("LINESTRING(0.25 0.25,0.75 0.75)", 100));
    assertEquals( 0.5, lineStringShortMid.getX(), 0.001, "Middlepoint of short linestring X");
    assertEquals(0.5, lineStringShortMid.getY(), 0.001, "Middlepoint of short linestring Y");
    final Point polygonMid = GeometryUtil.middleOfGeometry(new WKTGeometry("POLYGON((1000 0,1100 100,1200 100,1100 0,1000 0))", 100 * 100));
    assertEquals(1100, polygonMid.getX(), 0.001, "Middlepoint of polygon X");
    assertEquals(50, polygonMid.getY(), 0.001, "Middlepoint of polygon Y");
  }

  @Test
  public void testValidWKT() {
    assertTrue(GeometryUtil.validWKT(getExamplePoint().getWKT()), "Valid WKT");
    assertFalse(GeometryUtil.validWKT(getExamplePoint().getWKT().substring(1)), "Invalid WKT string");
    assertFalse(GeometryUtil.validWKT(null), "Invalid WKT null");
    assertFalse(GeometryUtil.validWKT(""), "Invalid WKT empty string");
    assertFalse(GeometryUtil.validWKT(" "), "Invalid WKT only space");
  }

  @Test
  public void testGetPoint() throws AeriusException {
    final Point point = GeometryUtil.getPoint(getExamplePoint().getWKT());
    assertNotNull(point, "Point returned");
    assertEquals(EXAMPLE_POINT_X, point.getX(), 0.0001, "X-coord");
    assertEquals(EXAMPLE_POINT_Y, point.getY(), 0.0001, "Y-coord");
    final AeriusException e1 = assertThrows(
        AeriusException.class,
        () -> GeometryUtil.getPoint("POINT(7.7 9.1"),
        "Incorrect WKT should throw an exception");
    assertEquals(ImaerExceptionReason.GEOMETRY_INVALID, e1.getReason(), "Reason for exception");

    final AeriusException e2 = assertThrows(
        AeriusException.class,
        () -> GeometryUtil.getPoint(getExampleLineString().getWKT()),
        "Linestring WKT should throw an exception");
    assertEquals(ImaerExceptionReason.GEOMETRY_INVALID, e2.getReason(), "Reason for exception");
  }

  @Test
  public void testHasIntersections() throws AeriusException {
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
  public void testDetermineLength() {
    final WKTGeometry lineString = getExampleLineString();
    final double determinedLength = GeometryUtil.determineLength(lineString.getWKT());
    assertEquals(lineString.getMeasure(), determinedLength, 0.500, "Length of a line");
  }

  @Test
  public void testDetermineLengthNullArgument() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      GeometryUtil.determineLength(null);
    });
  }

  @Test
  public void testDetermineArea() {
    final WKTGeometry polygon = getExampleWKTPolygon();
    final double determinedArea = GeometryUtil.determineArea(polygon.getWKT());
    assertEquals(polygon.getMeasure(), determinedArea, 0.001, "Area of a surface");
  }

  @Test
  public void testLastPointFromWKT() throws AeriusException {
    Point point = GeometryUtil.lastPointFromWKT("LINESTRING(1 2,3 4,2 2)");
    assertEquals(2, point.getX(), 0.001, "X coordinate");
    assertEquals(2, point.getY(), 0.001, "Y coordinate");
    point = GeometryUtil.lastPointFromWKT("POINT(3 4)");
    assertEquals(3, point.getX(), 0.001, "X coordinate");
    assertEquals(4, point.getY(), 0.001, "Y coordinate");
    point = GeometryUtil.lastPointFromWKT("POLYGON((8 2,7 9,6 5,8 2))");
    assertEquals(8, point.getX(), 0.001, "X coordinate");
    assertEquals(2, point.getY(), 0.001, "Y coordinate");
  }

  @Test
  public void testLastPointFromWKTIncorrectWKT() throws AeriusException {
    Assertions.assertThrows(AeriusException.class, () -> {
      GeometryUtil.lastPointFromWKT("LINESTRNG(1 2,4 5)");
    });
  }

  @Test
  public void testConvertToPoints() throws AeriusException {
    //create linestring with (0,0), (0,4000), (0,8000), (0,12000), (0,16000)
    final int numberOfCoordinates = 5;
    final StringBuilder lineString = new StringBuilder();
    lineString.append("LINESTRING(0 0");
    for (int i = 0; i < numberOfCoordinates; i++) {
      lineString.append(",0 ");
      lineString.append(4000 * i);
    }
    lineString.append(')');
    final WKTGeometry geometry = new WKTGeometry(lineString.toString());
    final double length = GeometryUtil.determineLength(geometry.getWKT());
    assertEquals((numberOfCoordinates - 1) * 4000, length, 1E-5, "Length");
    final Double maxSegementSize = 76.4;
    final List<Point> convertedPoints = GeometryUtil.convertToPoints(geometry, maxSegementSize);
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
  public void testConvertToPointsZigZag() throws AeriusException {
    //create linestring that zigs n zags
    final WKTGeometry geometry = new WKTGeometry("LINESTRING(0 0,0 4000,1000 4000,1000 12000,3000 12000)");
    final double length = GeometryUtil.determineLength(geometry.getWKT());
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
  public void testDetermineBoundingBox() {
    final List<Point> points = new ArrayList<Point>();
    //no points
    BBox boundingBox = GeometryUtil.determineBBox(points);
    assertNotNull(boundingBox, "empty list shouldn't return null");
    assertEquals(0.0, boundingBox.getMaxX(), 1E-3, "No point maxX");
    assertEquals(0.0, boundingBox.getMaxY(), 1E-3, "No point maxY");
    assertEquals(0.0, boundingBox.getMinX(), 1E-3, "No point minX");
    assertEquals(0.0, boundingBox.getMinY(), 1E-3, "No point minY");
    //one point
    points.add(new Point(1053.4, 498.1));
    boundingBox = GeometryUtil.determineBBox(points);
    assertEquals(1053.4, boundingBox.getMaxX(), 1E-3, "One point maxX");
    assertEquals(498.1, boundingBox.getMaxY(), 1E-3, "One point maxY");
    assertEquals(1053.4, boundingBox.getMinX(), 1E-3, "One point minX");
    assertEquals(498.1, boundingBox.getMinY(), 1E-3, "One point minY");
    //multiple points
    points.add(new Point(345.5, 98707.1));
    points.add(new Point(773.40, 8.1));
    points.add(new Point(984.4, -498.1));
    points.add(new Point(1234.5, 870.5));
    points.add(new Point(1053.4, 498.1));
    points.add(new Point(955, 38.1));
    boundingBox = GeometryUtil.determineBBox(points);
    assertEquals(1234.5, boundingBox.getMaxX(), 1E-3, "Multiple point maxX");
    assertEquals(98707.1, boundingBox.getMaxY(), 1E-3, "Multiple point maxY");
    assertEquals(345.5, boundingBox.getMinX(), 1E-3, "Multiple point minX");
    assertEquals(-498.1, boundingBox.getMinY(), 1E-3, "Multiple point minY");
  }

  private WKTGeometry getExamplePoint() {
    return new WKTGeometry(
        "POINT(" + EXAMPLE_POINT_X + " " + EXAMPLE_POINT_Y + ")", 1);
  }

  private WKTGeometry getExampleLineString() {
    final double xCoord0 = 7.80;
    final double yCoord0 = 4.55;
    final double xCoord1 = 44.77;
    final double yCoord1 = 2.4;
    final String wktLineString = "LINESTRING ("
        + xCoord0 + " " + yCoord0 + ","
        + xCoord1 + " " + yCoord1 + ")";
    final double length = Math.sqrt(
        Math.pow(xCoord0 - xCoord1, 2)
            + Math.pow(yCoord0 - yCoord1, 2));
    return new WKTGeometry(wktLineString, (int) Math.round(length));
  }

  private WKTGeometry getExampleWKTPolygon() {
    return new WKTGeometry("POLYGON ((0 0,20 0,20 100,0 100, 0 0))", 20 * 100);
  }
}
