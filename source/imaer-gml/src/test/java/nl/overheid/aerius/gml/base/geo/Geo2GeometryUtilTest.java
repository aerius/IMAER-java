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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;
import java.util.List;

import jakarta.xml.bind.JAXBElement;

import org.junit.jupiter.api.Test;

import net.opengis.gml.v_3_2.AbstractRingPropertyType;
import net.opengis.gml.v_3_2.DirectPositionListType;
import net.opengis.gml.v_3_2.DirectPositionType;
import net.opengis.gml.v_3_2.LinearRingType;
import net.opengis.gml.v_3_2.ObjectFactory;
import net.opengis.gml.v_3_2.PointType;
import net.opengis.gml.v_3_2.PolygonType;

import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.geojson.Polygon;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.shared.geo.EPSG;

/**
 * Test class for {@link Geo2GeometryUtil}.
 */
class Geo2GeometryUtilTest {

  private final Geo2GeometryUtil geo2GeometryUtil = new Geo2GeometryUtil(EPSG.RDNEW.getSrid());

  @Test
  void testPoint() throws AeriusException {
    final PointType pointType = createPoint();
    final Geometry geometry = geo2GeometryUtil.fromXMLPoint(pointType);
    assertTrue(geometry instanceof Point, "Not a point");
  }

  @Test
  void testPolygon() throws AeriusException {
    final PolygonType polygonType = createPolygon(List.of(100.0, 20.0, 400.0, 50.0, 600.0, 70.0, 100.0, 20.0));
    final Geometry geometry = geo2GeometryUtil.fromXMLPolygon(polygonType);

    assertTrue(geometry instanceof Polygon, "Not a polygon");
  }

  @Test
  void testPolygonNotClosed() {
    final PolygonType polygonType = createPolygon(List.of(100.0, 20.0, 400.0, 50.0, 600.0, 70.0));

    assertThrows(AeriusException.class, () -> geo2GeometryUtil.fromXMLPolygon(polygonType));
  }

  @Test
  void testInvalidSRS() throws AeriusException {
    final PointType pointType = createPoint();
    pointType.setSrsName("unknown");
    pointType.setSrsDimension(BigInteger.TEN);
    final AeriusException thrown = assertThrows(
        AeriusException.class,
        () -> geo2GeometryUtil.fromXMLPoint(pointType),
        "Expected AeriusException to throw, but it didn't");
    assertSame(ImaerExceptionReason.GML_SRS_NAME_UNSUPPORTED, thrown.getReason(), "Must be exception GML_SRS_NAME_UNSUPPORTED");
  }

  @Test
  void testEmptySRS() throws AeriusException {
    final PointType pointType = createPoint();
    pointType.setSrsDimension(BigInteger.TEN);
    pointType.setSrsName(null);
    assertNotNull(geo2GeometryUtil.fromXMLPoint(pointType), "Should just return the point no srs");
    pointType.setSrsName("");
    assertNotNull(geo2GeometryUtil.fromXMLPoint(pointType), "Should just return the point on empty");
  }

  private static PointType createPoint() {
    return createPoint(List.of(1.0, 1.0));
  }

  public static PointType createPoint(final List<Double> numbers) {
    final PointType pointType = new PointType();
    final DirectPositionType value = new DirectPositionType();

    value.setValue(numbers);
    pointType.setPos(value);
    return pointType;
  }

  public static PolygonType createPolygon(final List<Double> numbers) {
    final ObjectFactory factory = new ObjectFactory();

    final PolygonType polygonType = new PolygonType();
    final AbstractRingPropertyType abstractRingPropertyType = factory.createAbstractRingPropertyType();
    final JAXBElement<LinearRingType> linearRingType = factory.createLinearRing(factory.createLinearRingType());
    final DirectPositionListType coordList = new DirectPositionListType();

    coordList.setValue(numbers);
    linearRingType.getValue().setPosList(coordList);
    abstractRingPropertyType.setAbstractRing(linearRingType);
    polygonType.setExterior(abstractRingPropertyType);
    return polygonType;
  }
}
