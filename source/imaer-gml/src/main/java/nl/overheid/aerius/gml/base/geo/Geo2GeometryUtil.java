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
package nl.overheid.aerius.gml.base.geo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jakarta.xml.bind.JAXBElement;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

import net.opengis.gml.v_3_2.AbstractGeometryType;
import net.opengis.gml.v_3_2.AbstractRingPropertyType;
import net.opengis.gml.v_3_2.LineStringType;
import net.opengis.gml.v_3_2.LinearRingType;
import net.opengis.gml.v_3_2.PointType;
import net.opengis.gml.v_3_2.PolygonType;

import nl.overheid.aerius.gml.base.GMLSchema;
import nl.overheid.aerius.shared.domain.v2.geojson.LineString;
import nl.overheid.aerius.shared.domain.v2.geojson.Polygon;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.util.GeometryUtil;

/**
 * Class to convert gml geometry objects to data geometry objects.
 */
public final class Geo2GeometryUtil {

  private final int srid;
  private final GeometryFactory geometryFactory;

  public Geo2GeometryUtil(final int srid) {
    this.srid = srid;
    geometryFactory = new GeometryFactory(new PrecisionModel(), srid);
  }

  /**
   * Convert a GML-object to a Geometry containing a POINT.
   * @param pointType point
   * @return The Geometry containing POINT.
   * @throws AeriusException When no valid POINT could be made from the gml-object.
   */
  public nl.overheid.aerius.shared.domain.v2.geojson.Point fromXMLPoint(final PointType pointType) throws AeriusException {
    validateSrs(srid, pointType);
    final List<Double> values = pointType.getPos().getValue();
    final Point point = geometryFactory.createPoint(new Coordinate(values.get(0), values.get(1)));
    final nl.overheid.aerius.shared.domain.v2.geojson.Point aeriusPoint = new nl.overheid.aerius.shared.domain.v2.geojson.Point();
    aeriusPoint.setCoordinates(coordinate2AeriusCoordinate(point.getCoordinate()));
    return aeriusPoint;
  }

  /**
   * Convert a GML-object to a Geometry containing a LINESTRING.
   * @param lineStringType lineStringType
   * @return The Geometry containing LINESTRING.
   * @throws AeriusException When no valid LINESTRING could be made from the gml-object.
   */
  public LineString fromXMLLineString(final LineStringType lineStringType) throws AeriusException {
    validateSrs(srid, lineStringType);
    final Coordinate[] coordinates = lineString2Coordinates(lineStringType.getPosList().getValue());
    final String wktString = geometryFactory.createLineString(coordinates).toText();
    validateWKTGeometryString(wktString);
    final LineString lineString = new LineString();
    lineString.setCoordinates(coordinates2AeriusCoordinates(coordinates));
    return lineString;
  }

  /**
   * Convert a GML-object Polygon geometries.
   * @param polygonType polygon
   * @return The WKTGeometry containing POLYGON.
   * @throws AeriusException When no valid POLYGON could be made from the gml-object.
   */
  @SuppressWarnings("unchecked")
  public Polygon fromXMLPolygon(final PolygonType polygonType) throws AeriusException {
    validateSrs(srid, polygonType);
    final JAXBElement<LinearRingType> exteriorRing = (JAXBElement<LinearRingType>) polygonType.getExterior().getAbstractRing();
    final List<Double> values = exteriorRing.getValue().getPosList().getValue();
    final Coordinate[] shellCoordinates = lineString2Coordinates(values);
    LinearRing shell;
    try {
      shell = geometryFactory.createLinearRing(shellCoordinates);
      // can occur when the polygon is not closed
    } catch (final IllegalArgumentException e) {
      throw new AeriusException(ImaerExceptionReason.GML_GEOMETRY_INVALID);
    }

    final List<LinearRing> holes = new ArrayList<>();
    final List<Coordinate[]> holesCoordinates = new ArrayList<>();
    for (final Iterator<AbstractRingPropertyType> iterator = polygonType.getInterior().iterator(); iterator.hasNext();) {
      final JAXBElement<LinearRingType> innerRing = (JAXBElement<LinearRingType>) iterator.next().getAbstractRing();
      final List<Double> valuesInner = innerRing.getValue().getPosList().getValue();
      final Coordinate[] holeCoordinates = lineString2Coordinates(valuesInner);
      holes.add(geometryFactory.createLinearRing(holeCoordinates));
      holesCoordinates.add(holeCoordinates);
    }

    final String wktString = geometryFactory.createPolygon(shell, holes.toArray(new LinearRing[holes.size()])).toText();
    validateWKTGeometryString(wktString);
    return createPolygon(shellCoordinates, holesCoordinates);
  }

  private static Polygon createPolygon(final Coordinate[] shellCoordinates, final List<Coordinate[]> holesCoordinates) {
    final double[][] aeriusShell = coordinates2AeriusCoordinates(shellCoordinates);
    final double[][][] polygonContents = new double[1 + holesCoordinates.size()][][];
    polygonContents[0] = aeriusShell;
    for (int i = 0; i < holesCoordinates.size(); i++) {
      polygonContents[i + 1] = coordinates2AeriusCoordinates(holesCoordinates.get(i));
    }
    final Polygon polygon = new Polygon();
    polygon.setCoordinates(polygonContents);
    return polygon;
  }

  private static Coordinate[] lineString2Coordinates(final List<Double> values) {
    final Coordinate[] coordinates = new Coordinate[values.size() / 2];
    for (int i = 0; i < values.size(); i += 2) {
      coordinates[i / 2] = new Coordinate(values.get(i), values.get(i + 1));
    }
    return coordinates;
  }

  private static double[][] coordinates2AeriusCoordinates(final Coordinate[] coordinates) {
    final double[][] aeriusCoordinates = new double[coordinates.length][];
    for (int i = 0; i < coordinates.length; i++) {
      aeriusCoordinates[i] = coordinate2AeriusCoordinate(coordinates[i]);
    }
    return aeriusCoordinates;
  }

  private static double[] coordinate2AeriusCoordinate(final Coordinate coordinate) {
    return new double[] {coordinate.x, coordinate.y};
  }

  /**
   * Validates it the srs is the same as the default srs.
   * It won't trigger on empty srs. It's intended to make srs required in future IMAER versions, the converter then should still allow it for
   * older versions.
   *
   * @param type data to check.
   * @throws AeriusException exception when not the supported srs
   */
  private static void validateSrs(final int srid, final AbstractGeometryType type) throws AeriusException {
    final String expectedSrsName = GMLSchema.getSRSName(srid);
    final String srsName = type.getSrsName();
    if (srsName != null && !srsName.isEmpty() && !expectedSrsName.equalsIgnoreCase(srsName)) {
      throw new AeriusException(ImaerExceptionReason.GML_SRS_NAME_UNSUPPORTED, srsName);
    }
  }

  private static void validateWKTGeometryString(final String wkt) throws AeriusException {
    if (!GeometryUtil.validWKT(wkt)) {
      throw new AeriusException(ImaerExceptionReason.GML_GEOMETRY_INVALID, wkt);
    }
  }
}
