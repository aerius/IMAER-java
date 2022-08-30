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
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;

import net.opengis.gml.v_3_2_1.AbstractRingPropertyType;
import net.opengis.gml.v_3_2_1.DirectPositionListType;
import net.opengis.gml.v_3_2_1.DirectPositionType;
import net.opengis.gml.v_3_2_1.LineStringType;
import net.opengis.gml.v_3_2_1.LinearRingType;
import net.opengis.gml.v_3_2_1.ObjectFactory;
import net.opengis.gml.v_3_2_1.PointType;
import net.opengis.gml.v_3_2_1.PolygonType;

import nl.aerius.shared.domain.geojson.LineString;
import nl.aerius.shared.domain.geojson.Point;
import nl.aerius.shared.domain.geojson.Polygon;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.util.GeometryUtil;

/**
 * Utility class to convert geometry objects to GML objects.
 */
public final class Geometry2GML {

  private final String srsName;

  public Geometry2GML(final String srsName) {
    this.srsName = srsName;
  }

  /**
   * Convert a WKTGeometry containing a POINT to a GML-object.
   * Does NOT check if WKT is actually a valid point.
   * @param point The WKTGeometry containing POINT.
   * @return The GML-object representing the point.
   * @throws AeriusException problem with point
   */
  public <T extends GmlPoint> T toXMLPoint(final Point point, final T returnPoint) throws AeriusException {
    final PointType pointType = new PointType();
    pointType.setSrsName(srsName);
    final DirectPositionType pos = new DirectPositionType();
    final Geometry point2 = GeometryUtil.getGeometry(point);
    final Coordinate coordinate = point2.getCoordinate();
    pos.getValue().add(coordinate.x);
    pos.getValue().add(coordinate.y);
    pointType.setPos(pos);
    returnPoint.setGmlPoint(pointType);
    return returnPoint;
  }

  /**
   * Convert a WKTGeometry containing a LINESTRING to a GML-object.
   * Does NOT check if WKT is actually a valid linestring.
   * @param lineString The WKTGeometry containing LINESTRING.
   * @return The GML-object representing the linestring.
   * @throws AeriusException problem with line
   */
  public <T extends GmlLineString> T toXMLLineString(final LineString lineString, final T returnLineString) throws AeriusException {
    final LineStringType lineStringType = new LineStringType();
    lineStringType.setSrsName(srsName);
    final Geometry line = GeometryUtil.getGeometry(lineString);
    lineStringType.setPosList(getCoordinates(line.getCoordinates()));
    returnLineString.setGMLLineString(lineStringType);
    return returnLineString;
  }

  /**
   * Convert a WKTGeometry containing a POLYGON to a GML-object.
   * Does NOT check if WKT is actually a valid polygon.
   * @param polygon The WKTGeometry containing POLYGON.
   * @return The GML-object representing the polygon.
   * @throws AeriusException polygon problem
   */
  public <T extends GmlPolygon> T toXMLPolygon(final Polygon polygon, final T returnPolygon) throws AeriusException {
    final ObjectFactory factory = new ObjectFactory();
    final PolygonType polygonType = factory.createPolygonType();
    polygonType.setSrsName(srsName);
    final Geometry poly = GeometryUtil.getGeometry(polygon);

    if (poly instanceof org.locationtech.jts.geom.Polygon) {
      final org.locationtech.jts.geom.Polygon polygonJts = (org.locationtech.jts.geom.Polygon) poly;
      final AbstractRingPropertyType exterior = setCoordinates(factory, getCoordinates(polygonJts.getExteriorRing().getCoordinates()));
      polygonType.setExterior(exterior);
      final List<AbstractRingPropertyType> interiors = new ArrayList<>();
      for (int i = 0; i < polygonJts.getNumInteriorRing(); i++) {
        final AbstractRingPropertyType inner = setCoordinates(factory, getCoordinates(polygonJts.getInteriorRingN(i).getCoordinates()));
        interiors.add(inner);
      }
      polygonType.setInterior(interiors);
    }
    returnPolygon.setGmlPolygon(polygonType);
    return returnPolygon;
  }

  private static AbstractRingPropertyType setCoordinates(final ObjectFactory factory, final DirectPositionListType coordinates) {
    final JAXBElement<LinearRingType> linearRingType = factory.createLinearRing(factory.createLinearRingType());
    final AbstractRingPropertyType inner = factory.createAbstractRingPropertyType();
    inner.setAbstractRing(linearRingType);
    linearRingType.getValue().setPosList(coordinates);
    return inner;
  }

  /**
   * Set coordinates from wkt line string to gml LineStringType
   * @param lineString
   * @param lineStringType
   * @return
   */
  private static DirectPositionListType getCoordinates(final Coordinate... coordinates) {
    final DirectPositionListType dplt = new DirectPositionListType();
    for (final Coordinate coordinate : coordinates) {
      dplt.getValue().add(coordinate.x);
      dplt.getValue().add(coordinate.y);
    }
    return dplt;
  }
}
