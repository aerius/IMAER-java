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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vividsolutions.jts.algorithm.ConvexHull;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineSegment;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.util.AffineTransformation;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.linearref.LengthIndexedLine;
import com.vividsolutions.jts.operation.IsSimpleOp;

import nl.overheid.aerius.geo.shared.BBox;
import nl.overheid.aerius.geo.shared.Point;
import nl.overheid.aerius.geo.shared.WKTGeometry;
import nl.overheid.aerius.shared.domain.geo.OrientedEnvelope;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Util class for Geometric functions.
 */
public final class GeometryUtil {

  private static final Logger LOG = LoggerFactory.getLogger(GeometryUtil.class);

  private static final int SANELY_ROUNDING_DECIMALS = 3;

  private GeometryUtil() {
    //util class
  }

  /**
   * @param wktGeometry The geometry to determine the middle for.
   * @return The middle of the geometry.
   * @throws AeriusException In case the WKTgeometry did not define a right geometry.
   */
  public static Point middleOfGeometry(final WKTGeometry wktGeometry) throws AeriusException {
    final Geometry geometry = getGeometry(wktGeometry.getWKT());

    final Point midPoint;
    if (geometry instanceof com.vividsolutions.jts.geom.Point) {
      final com.vividsolutions.jts.geom.Point geomPoint = (com.vividsolutions.jts.geom.Point) geometry;
      midPoint = new Point(geomPoint.getX(), geomPoint.getY());
    } else if (geometry instanceof LineString) {
      midPoint = GeometryUtil.middleOfLineString((LineString) geometry);
    } else {
      midPoint = new Point(geometry.getCentroid().getX(), geometry.getCentroid().getY());
    }

    return midPoint;
  }

  /**
   * @param lineString The linestring to determine the middle for.
   * @return The middle point of the linestring (on the line).
   */
  private static Point middleOfLineString(final LineString lineString) {
    final double middle = lineString.getLength() / 2;
    Coordinate prevCoord = lineString.getCoordinateN(0);
    double curLength = 0;
    for (final Coordinate nextCoord : lineString.getCoordinates()) {
      final double d = prevCoord.distance(nextCoord);

      if (curLength + d >= middle) {
        final double posOnLine = (middle - curLength) / d;

        return new Point(prevCoord.x + (nextCoord.x - prevCoord.x) * posOnLine,
            prevCoord.y + (nextCoord.y - prevCoord.y) * posOnLine);
      }
      curLength += d;
      prevCoord = nextCoord;
    }
    return new Point(prevCoord.x, prevCoord.y);
  }

  /**
   * @param wkt The WKT to validate.
   * @return The (JTS) geometry that is contained in the WKT.
   */
  public static boolean validWKT(final String wkt) {
    //consider a null,empty or whitespace string as invalid.
    boolean valid = wkt != null && !wkt.isEmpty() && !wkt.trim().isEmpty();

    if (valid) {
      try {
        getGeometry(wkt);
      } catch (final AeriusException e) {
        valid = false;
      }
    }
    return valid;
  }

  /**
   * @param wkt The WKT to convert to a Point object.
   * @return The point that is contained in the WKT.
   * @throws AeriusException In case the WKT couldn't be converted to a valid point object.
   */
  public static Point getPoint(final String wkt) throws AeriusException {
    final Geometry geom = getGeometry(wkt);
    if (!(geom instanceof com.vividsolutions.jts.geom.Point)) {
      throw new AeriusException(ImaerExceptionReason.GEOMETRY_INVALID, wkt);
    }
    final com.vividsolutions.jts.geom.Point point = (com.vividsolutions.jts.geom.Point) geom;
    return new Point(point.getX(), point.getY());
  }

  /**
   * Returns the The (JTS) geometry that is contained in the AERIUS geometry.
   * @param aeriusGeometry AERIUS geometry object.
   * @return The (JTS) geometry that is contained in the WKT.
   * @throws AeriusException exception in case of geometry problems
   */
  public static Geometry getGeometry(final nl.overheid.aerius.geo.shared.Geometry aeriusGeometry) throws AeriusException {
    final Geometry geometry = getGeometry(aeriusGeometry instanceof WKTGeometry ? ((WKTGeometry) aeriusGeometry).getWKT()
        : aeriusGeometry instanceof Point ? ((Point) aeriusGeometry).toUnroundedWKT() : null);
    if (geometry == null) {
      throw new AeriusException(ImaerExceptionReason.GEOMETRY_INVALID, String.valueOf(aeriusGeometry));
    }
    return geometry;
  }

  /**
   * @param wkt The WKT to convert to a JTS Geometry object.
   * @return The (JTS) geometry that is contained in the WKT.
   * @throws AeriusException In case the WKT couldn't be converted to a valid JTS Geometry object.
   */
  public static Geometry getGeometry(final String wkt) throws AeriusException {
    Geometry geom = null;
    final WKTReader reader = new WKTReader();
    try {
      geom = reader.read(wkt);
    } catch (final ParseException | IllegalArgumentException e) {
      LOG.trace("WKT parse expection, rethrown as AeriusException", e);
      throw new AeriusException(ImaerExceptionReason.GEOMETRY_INVALID, wkt);
    }
    return geom;
  }

  /**
   * Returns the (JTS) geometry that is contained in the AERIUS geometry.
   * @param aeriusGeometry AERIUS geometry object.
   * @return The (JTS) geometry that is contained in the WKT.
   * @throws AeriusException exception in case of geometry problems
   */
  public static Geometry getGeometry(final nl.overheid.aerius.shared.domain.v2.geojson.Geometry aeriusGeometry) throws AeriusException {
    final Geometry geometry;
    if (aeriusGeometry instanceof nl.overheid.aerius.shared.domain.v2.geojson.Point) {
      geometry = toJtsPoint((nl.overheid.aerius.shared.domain.v2.geojson.Point) aeriusGeometry);
    } else if (aeriusGeometry instanceof nl.overheid.aerius.shared.domain.v2.geojson.LineString) {
      geometry = toJtsLineString((nl.overheid.aerius.shared.domain.v2.geojson.LineString) aeriusGeometry);
    } else if (aeriusGeometry instanceof nl.overheid.aerius.shared.domain.v2.geojson.Polygon) {
      geometry = toJtsPolygon((nl.overheid.aerius.shared.domain.v2.geojson.Polygon) aeriusGeometry);
    } else {
      throw new AeriusException(ImaerExceptionReason.GEOMETRY_INVALID, String.valueOf(aeriusGeometry));
    }
    return geometry;
  }

  private static com.vividsolutions.jts.geom.Point toJtsPoint(final nl.overheid.aerius.shared.domain.v2.geojson.Point aeriusPoint)
      throws AeriusException {
    final GeometryFactory geometryFactory = new GeometryFactory();
    final Coordinate coordinate = toJtsCoordinate(aeriusPoint.getCoordinates(), aeriusPoint);
    return geometryFactory.createPoint(coordinate);
  }

  private static LineString toJtsLineString(final nl.overheid.aerius.shared.domain.v2.geojson.LineString aeriusLineString) throws AeriusException {
    final GeometryFactory geometryFactory = new GeometryFactory();
    final int numberOfCoordinates = aeriusLineString.getCoordinates().length;
    final Coordinate[] coordinates = new Coordinate[numberOfCoordinates];
    for (int i = 0; i < numberOfCoordinates; i++) {
      final Coordinate coordinate = toJtsCoordinate(aeriusLineString.getCoordinates()[i], aeriusLineString);
      coordinates[i] = coordinate;
    }
    return geometryFactory.createLineString(coordinates);
  }

  private static Polygon toJtsPolygon(final nl.overheid.aerius.shared.domain.v2.geojson.Polygon aeriusPolygon) throws AeriusException {
    final GeometryFactory geometryFactory = new GeometryFactory();
    if (aeriusPolygon.getCoordinates().length < 1) {
      throw new AeriusException(ImaerExceptionReason.GEOMETRY_INVALID, String.valueOf(aeriusPolygon));
    }
    final LinearRing shell = toJtsLinearRing(geometryFactory, aeriusPolygon.getCoordinates()[0], aeriusPolygon);
    final int numberOfShells = aeriusPolygon.getCoordinates().length - 1;
    final LinearRing[] holes = new LinearRing[numberOfShells];
    for (int i = 1; i <= numberOfShells; i++) {
      final LinearRing hole = toJtsLinearRing(geometryFactory, aeriusPolygon.getCoordinates()[i], aeriusPolygon);
      holes[i] = hole;
    }

    return geometryFactory.createPolygon(shell, holes);
  }

  private static Coordinate toJtsCoordinate(final double[] coordinate, final nl.overheid.aerius.shared.domain.v2.geojson.Geometry originalGeometry)
      throws AeriusException {
    if (coordinate == null || coordinate.length != 2) {
      throw new AeriusException(ImaerExceptionReason.GEOMETRY_INVALID, String.valueOf(originalGeometry));
    }
    return new Coordinate(coordinate[0], coordinate[1]);
  }

  private static LinearRing toJtsLinearRing(final GeometryFactory geometryFactory, final double[][] coordinates,
      final nl.overheid.aerius.shared.domain.v2.geojson.Geometry originalGeometry) throws AeriusException {
    final int numberOfCoordinates = coordinates.length;
    final Coordinate[] coordinatesJts = new Coordinate[numberOfCoordinates];
    for (int i = 0; i < numberOfCoordinates; i++) {
      final Coordinate coordinate = toJtsCoordinate(coordinates[i], originalGeometry);
      coordinatesJts[i] = coordinate;
    }
    return geometryFactory.createLinearRing(coordinatesJts);
  }

  /**
   * Returns the (JTS) geometry that is contained in the AERIUS geometry.
   * @param aeriusGeometry AERIUS geometry object.
   * @return The (JTS) geometry that is contained in the WKT.
   * @throws AeriusException exception in case of geometry problems
   */
  public static final nl.overheid.aerius.shared.domain.v2.geojson.Geometry getAeriusGeometry(final Geometry jtsGeometry) throws AeriusException {
    final nl.overheid.aerius.shared.domain.v2.geojson.Geometry aeriusGeometry;
    if (jtsGeometry instanceof com.vividsolutions.jts.geom.Point) {
      aeriusGeometry = toAeriusPoint((com.vividsolutions.jts.geom.Point) jtsGeometry);
    } else if (jtsGeometry instanceof com.vividsolutions.jts.geom.LineString) {
      aeriusGeometry = toAeriusLineString((com.vividsolutions.jts.geom.LineString) jtsGeometry);
    } else if (jtsGeometry instanceof com.vividsolutions.jts.geom.Polygon) {
      aeriusGeometry = toAeriusPolygon((com.vividsolutions.jts.geom.Polygon) jtsGeometry);
    } else {
      throw new AeriusException(ImaerExceptionReason.GEOMETRY_INVALID, String.valueOf(jtsGeometry));
    }
    return aeriusGeometry;
  }

  private static nl.overheid.aerius.shared.domain.v2.geojson.Point toAeriusPoint(final com.vividsolutions.jts.geom.Point jtsPoint) {
    return new nl.overheid.aerius.shared.domain.v2.geojson.Point(jtsPoint.getX(), jtsPoint.getY());
  }

  private static nl.overheid.aerius.shared.domain.v2.geojson.LineString toAeriusLineString(
      final com.vividsolutions.jts.geom.LineString jtsLineString) {
    final nl.overheid.aerius.shared.domain.v2.geojson.LineString aeriusLinestring = new nl.overheid.aerius.shared.domain.v2.geojson.LineString();
    final double[][] aeriusCoordinates = toAeriusCoordinates(jtsLineString);
    aeriusLinestring.setCoordinates(aeriusCoordinates);
    return aeriusLinestring;
  }

  private static nl.overheid.aerius.shared.domain.v2.geojson.Polygon toAeriusPolygon(
      final com.vividsolutions.jts.geom.Polygon jtsPolygon) {
    final nl.overheid.aerius.shared.domain.v2.geojson.Polygon aeriusPolygon = new nl.overheid.aerius.shared.domain.v2.geojson.Polygon();
    final double[][][] aeriusCoordinates = new double[jtsPolygon.getNumInteriorRing() + 1][][];
    aeriusCoordinates[0] = toAeriusCoordinates(jtsPolygon.getExteriorRing());
    for (int i = 0; i < jtsPolygon.getNumInteriorRing(); i++) {
      aeriusCoordinates[i + 1] = toAeriusCoordinates(jtsPolygon.getInteriorRingN(i));
    }
    aeriusPolygon.setCoordinates(aeriusCoordinates);
    return aeriusPolygon;
  }

  private static double[][] toAeriusCoordinates(final com.vividsolutions.jts.geom.LineString jtsLineString) {
    final Coordinate[] jtsCoordinates = jtsLineString.getCoordinates();
    final double[][] aeriusCoordinates = new double[jtsCoordinates.length][];
    for (int i = 0; i < jtsCoordinates.length; i++) {
      final Coordinate coordinate = jtsCoordinates[i];
      aeriusCoordinates[i] = new double[] {coordinate.x, coordinate.y};
    }
    return aeriusCoordinates;
  }

  /**
   * Check if a wkt has intersections.
   * @param wkt The WKT to check for intersections.
   * @return true if invalid, false if not.
   * @throws AeriusException In case the WKT was incorrect.
   */
  public static boolean hasIntersections(final String wkt) throws AeriusException {
    final Geometry geometry = getGeometry(wkt);
    return hasIntersections(geometry);
  }

  /**
   * Check if a geometry has intersections.
   * @param geometry The JTS geometry to check for intersections.
   * @return true if invalid, false if not.
   */
  public static boolean hasIntersections(final Geometry geometry) {
    final IsSimpleOp isSimpleOp = new IsSimpleOp(geometry);
    //isSimple implementation for polygons always returns true because if
    //isValid returns true it's by definition simple. Therefore both test are
    //done here, as isSimple is needed for checking lines.
    return (geometry instanceof Polygon || geometry instanceof MultiPolygon)
        && (!isSimpleOp.isSimple() || !geometry.isValid());
  }

  /**
   * Determine the length of a geometry.
   * @param wkt The (WKT) geometry to determine the length for (must be a linestring).
   * @return The length of the linestring (in m).
   */
  public static double determineLength(final String wkt) {
    if (wkt == null) {
      throw new IllegalArgumentException("WKT can't be null");
    }
    Geometry jtsGeometry = null;
    try {
      jtsGeometry = getGeometry(wkt);
    } catch (final AeriusException e) {
      throw new IllegalArgumentException("Could not parse WKT (for linestring): " + wkt);
    }
    if (!(jtsGeometry instanceof LineString)) { // no null check needed, as instanceof will return false if object is null
      throw new IllegalArgumentException("WKT did not describe a linestring: " + wkt);
    }
    return ((LineString) jtsGeometry).getLength();
  }

  /**
   * Determine the surface of a geometry.
   * @param wkt The (WKT) geometry to determine the surface for (must be a polygon).
   * @return The surface of the polygon (in m^2).
   */
  public static double determineArea(final String wkt) {
    if (wkt == null) {
      throw new IllegalArgumentException("WKT can't be null");
    }
    Geometry jtsGeometry = null;
    try {
      jtsGeometry = getGeometry(wkt);
    } catch (final AeriusException e) {
      throw new IllegalArgumentException("Could not parse WKT (for polygon): " + wkt);
    }
    if (!(jtsGeometry instanceof Polygon)) { // no null check needed, as instanceof will return false if object is null
      throw new IllegalArgumentException("WKT did not describe a polygon: " + wkt);
    }
    final Polygon polygon = (Polygon) jtsGeometry;
    return polygon.getArea();
  }

  /**
   * @param wkt The WKT string containing LINESTRING
   * @return the point representing the last point of the linestring.
   * @throws AeriusException When no valid LINESTRING could be made from the WKT.
   */
  public static Point lastPointFromWKT(final String wkt) throws AeriusException {
    final Point point = new Point();
    final Geometry lineString = getGeometry(wkt);
    final Coordinate lastCoordinate = lineString.getCoordinates()[lineString.getCoordinates().length - 1];
    point.setX(lastCoordinate.getOrdinate(Coordinate.X));
    point.setY(lastCoordinate.getOrdinate(Coordinate.Y));
    return point;
  }

  /**
   * Convert a Line (or LineString) into a list of points that represent the line.
   * Each point represents an equal part of the line.
   * @param line The line to convert.
   * @param maxSegmentSize The maximum size of the segments that should be returned.
   * @return The converted points that represent the line.
   * @throws AeriusException When the geometry wasn't right.
   */
  public static List<Point> convertToPoints(final WKTGeometry line, final double maxSegmentSize) throws AeriusException {
    final List<Point> points;
    final Geometry geometry = getGeometry(line.getWKT());
    if (geometry instanceof LineString) {
      points = convertToPoints((LineString) geometry, maxSegmentSize, (x, y) -> new Point(x, y));
    } else {
      LOG.error("Can't convert {}. It is not a valid linestring geometry.", line);
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR);
    }

    return points;
  }

  /**
   * Convert a Line (or LineString) into a list of points that represent the line.
   * Each point represents an equal part of the line.
   * @param line The line to convert.
   * @param maxSegmentSize The maximum size of the segments that should be returned.
   * @return The converted points that represent the line.
   * @throws AeriusException When the geometry wasn't right.
   */
  public static List<nl.overheid.aerius.shared.domain.v2.geojson.Point> convertToPoints(
      final nl.overheid.aerius.shared.domain.v2.geojson.LineString line, final double maxSegmentSize) throws AeriusException {
    final List<nl.overheid.aerius.shared.domain.v2.geojson.Point> points;
    final Geometry geometry = getGeometry(line);
    if (geometry instanceof LineString) {
      points = convertToPoints((LineString) geometry, maxSegmentSize, (x, y) -> new nl.overheid.aerius.shared.domain.v2.geojson.Point(x, y));
    } else {
      LOG.error("Can't convert {}. It is not a valid linestring geometry.", line);
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR);
    }

    return points;
  }

  private static <P> List<P> convertToPoints(final LineString lineString, final double maxSegmentSize,
      final BiFunction<Double, Double, P> pointCreator) {
    final List<P> points = new ArrayList<>();
    final double lineLength = lineString.getLength();
    final double numSegments = BigDecimal.valueOf(lineLength).divide(BigDecimal.valueOf(maxSegmentSize), 0, RoundingMode.CEILING).doubleValue();
    final double segmentSize = lineLength / numSegments;
    for (int i = 0; i < numSegments; i++) {
      final LengthIndexedLine lengthIndexedLine = new LengthIndexedLine(lineString);
      final Coordinate coord = lengthIndexedLine.extractPoint(i * segmentSize + segmentSize / 2);
      points.add(pointCreator.apply(coord.x, coord.y));
    }
    return points;
  }

  /**
   * @param points The points to determine the bounding box for.
   * @param <P> The type of the points.
   * @return the bounding box.
   */
  public static <P extends Point> BBox determineBBox(final List<P> points) {
    final BBox boundingBox = new BBox();
    if (!points.isEmpty()) {
      boundingBox.setMaxX(points.get(0).getX());
      boundingBox.setMinX(points.get(0).getX());
      boundingBox.setMaxY(points.get(0).getY());
      boundingBox.setMinY(points.get(0).getY());
      for (final Point point : points) {
        if (boundingBox.getMaxX() < point.getX()) {
          boundingBox.setMaxX(point.getX());
        }
        if (boundingBox.getMinX() > point.getX()) {
          boundingBox.setMinX(point.getX());
        }
        if (boundingBox.getMaxY() < point.getY()) {
          boundingBox.setMaxY(point.getY());
        }
        if (boundingBox.getMinY() > point.getY()) {
          boundingBox.setMinY(point.getY());
        }
      }
    }
    return boundingBox;
  }

  /**
   * As denoted by the following ASCII image:
   *    A        B
   *
   *        / /
   * ======= /
   * (= road, / / a bend in the road, A/B points)
   *
   * A is perpendicular along the line, B is not.
   *
   * @param wkt The wkt of the linestring.
   * @param point The point to determine if it's perpendicular along the line
   * @return True if it's perpendicular along the line  (or the WKT did not specify a linestring).
   * @throws AeriusException In case the WKTgeometry did not define a correct geometry.
   */
  public static boolean isPerpendicularAlongLine(final Geometry geometry, final nl.overheid.aerius.shared.domain.v2.geojson.Point point) {
    final Coordinate pointCoordinate = new Coordinate(point.getX(), point.getY());
    if (geometry instanceof LineString) {
      Coordinate firstCoordinate = null;
      Coordinate secondCoordinate = null;
      for (final Coordinate coordinate : geometry.getCoordinates()) {
        firstCoordinate = secondCoordinate;
        secondCoordinate = coordinate;
        if (firstCoordinate != null && secondCoordinate != null) {
          final LineSegment segment = new LineSegment(firstCoordinate, secondCoordinate);
          final double projectionFactor = segment.projectionFactor(pointCoordinate);
          if (projectionFactor >= 0.0 && projectionFactor <= 1.0) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * @param geometry Geometry to determine the centroid of, which will be use as a center of the polygon.
   * @param length The length that the polygon should get
   * @param width The width that the polygon should get
   * @param orientation The orientation with respect to the x-axis. That is, from X positive counter-clockwise.
   * @return The corresponding polygon.
   */
  public static nl.overheid.aerius.shared.domain.v2.geojson.Polygon constructPolygonFromDimensions(
      final nl.overheid.aerius.shared.domain.v2.geojson.Geometry geometry, final double length, final double width, final double orientation)
      throws AeriusException {
    final Geometry jtsGeometry = getGeometry(geometry);
    return constructPolygonFromDimensions(jtsGeometry.getCentroid(), length, width, orientation);
  }

  /**
   * @param point Center of the polygon
   * @param length The length that the polygon should get
   * @param width The width that the polygon should get
   * @param orientation The orientation with respect to the x-axis. That is, from X positive counter-clockwise.
   * @return The corresponding polygon.
   */
  private static nl.overheid.aerius.shared.domain.v2.geojson.Polygon constructPolygonFromDimensions(
      final com.vividsolutions.jts.geom.Point point, final double length, final double width, final double orientation)
      throws AeriusException {
    final nl.overheid.aerius.shared.domain.v2.geojson.Polygon polygon = toBasePolygon(length, width);
    final Geometry jtsPolygon = toJtsPolygon(polygon);
    final double orientationToNorth = orientationXToNorth(orientation);
    final double rotationInRadians = -Math.toRadians(orientationToNorth);
    final AffineTransformation rotate = AffineTransformation.rotationInstance(rotationInRadians);
    final Geometry rotatedPolygon = rotate.transform(jtsPolygon);
    final AffineTransformation translate = AffineTransformation.translationInstance(point.getX(), point.getY());
    final Geometry translatedPolygon = translate.transform(rotatedPolygon);
    return toAeriusPolygon((Polygon) translatedPolygon);
  }

  private static double orientationXToNorth(final double orientationX) {
    // switch from x-axis oriented (counter-clockwise) to north oriented (clockwise)
    // Formula being: (360 + 90 - orientation X) modulo 180
    // 360 value is used to ensure we end up with positive values
    // 90 value is used to go from y-axis (north) to x-axis
    // minus is used to go from counter-clockwise to clockwise
    // modulo 180 is used to ensure we get the lowest positive angle that represents the orientation.
    return BigDecimal.valueOf(450)
        .subtract(BigDecimal.valueOf(orientationX))
        .remainder(BigDecimal.valueOf(180))
        .doubleValue();
  }

  private static nl.overheid.aerius.shared.domain.v2.geojson.Polygon toBasePolygon(final double length, final double width) {
    final nl.overheid.aerius.shared.domain.v2.geojson.Polygon polygon =
        new nl.overheid.aerius.shared.domain.v2.geojson.Polygon();
    final double[][][] coordinates = new double[1][5][2];
    coordinates[0][0] = toBasePolygonCoordinate(length, width, true, true);
    coordinates[0][1] = toBasePolygonCoordinate(length, width, false, true);
    coordinates[0][2] = toBasePolygonCoordinate(length, width, false, false);
    coordinates[0][3] = toBasePolygonCoordinate(length, width, true, false);
    coordinates[0][4] = toBasePolygonCoordinate(length, width, true, true);
    polygon.setCoordinates(coordinates);
    return polygon;
  }

  private static double[] toBasePolygonCoordinate(final double length, final double width, final boolean east, final boolean north) {
    final double xCoord = BigDecimal.valueOf(width).divide(BigDecimal.valueOf(2), 4, RoundingMode.HALF_UP).doubleValue();
    final double yCoord = BigDecimal.valueOf(length).divide(BigDecimal.valueOf(2), 4, RoundingMode.HALF_UP).doubleValue();
    return new double[] {east ? xCoord : -xCoord, north ? yCoord : -yCoord};
  }

  public static OrientedEnvelope determineOrientedEnvelope(final nl.overheid.aerius.shared.domain.v2.geojson.Polygon polygon) throws AeriusException {
    final Polygon jtsPolygon = toJtsPolygon(polygon);
    return determineOrientedEnvelope(jtsPolygon);
  }

  public static OrientedEnvelope determineOrientedEnvelope(final Polygon polygon) throws AeriusException {
    final Polygon convexHull = toConvexHull(polygon);
    final Coordinate centroid = polygon.getCentroid().getCoordinate();
    final Coordinate[] coordinates = convexHull.getExteriorRing().getCoordinates();
    double minArea = Double.MAX_VALUE;
    double minAngle = 0.0;
    Polygon envelopeWithMinArea = null;
    for (int i = 0; i < coordinates.length - 1; i++) {
      final Coordinate currentCoordinate = coordinates[i];
      final Coordinate nextCoordinate = coordinates[i + 1];
      final double angle = -Math.atan2(nextCoordinate.y - currentCoordinate.y, nextCoordinate.x - currentCoordinate.x);
      final Polygon envelope = (Polygon) AffineTransformation.rotationInstance(angle, centroid.x, centroid.y).transform(convexHull).getEnvelope();
      final double area = envelope.getArea();
      if (area < minArea) {
        minArea = area;
        minAngle = angle;
        envelopeWithMinArea = envelope;
      }
    }
    if (envelopeWithMinArea == null) {
      LOG.error("Can't determine oriented envelope for {}. Somehow envelopeWithMinArea is null.", polygon);
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR);
    }

    return toOrientedEnvelope(envelopeWithMinArea, minAngle);
  }

  private static Polygon toConvexHull(final Polygon polygon) throws AeriusException {
    final Geometry hull = (new ConvexHull(polygon)).getConvexHull();
    if (!(hull instanceof Polygon)) {
      LOG.error("Can't create a convex hull for {}. It is not a valid polygon geometry.", polygon);
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR);
    }
    return (Polygon) hull;
  }

  private static OrientedEnvelope toOrientedEnvelope(final Polygon envelopeWithMinArea, final double minAngleRadian) {
    double length = envelopeWithMinArea.getEnvelopeInternal().getHeight();
    double width = envelopeWithMinArea.getEnvelopeInternal().getWidth();
    double minAngleDegrees = Math.toDegrees(minAngleRadian);
    // Height of envelope = difference between the maximum and minimum y values
    // Width of envelope = difference between the maximum and minimum x values
    // Our definition of length = max of those 2 (and width = min of those 2)
    // If we have to switch, be sure to update angle accordingly (rotate another 90 degrees)
    if (length < width) {
      final double tempLength = length;
      length = sanelyRounded(width);
      width = sanelyRounded(tempLength);
      minAngleDegrees = sanelyRounded(minAngleDegrees + 90);
    }

    final double orientation = orientationXToNorth(minAngleDegrees);

    return new OrientedEnvelope(length, width, orientation);
  }

  private static double sanelyRounded(final double value) {
    return BigDecimal.valueOf(value).setScale(SANELY_ROUNDING_DECIMALS, RoundingMode.HALF_UP).doubleValue();
  }

}
