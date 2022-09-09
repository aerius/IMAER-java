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
package nl.overheid.aerius.gml.base;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;
import java.util.Arrays;

import javax.xml.bind.JAXBElement;

import org.junit.jupiter.api.Test;

import net.opengis.gml.v_3_2_1.AbstractRingPropertyType;
import net.opengis.gml.v_3_2_1.DirectPositionListType;
import net.opengis.gml.v_3_2_1.DirectPositionType;
import net.opengis.gml.v_3_2_1.LinearRingType;
import net.opengis.gml.v_3_2_1.ObjectFactory;
import net.opengis.gml.v_3_2_1.PointType;
import net.opengis.gml.v_3_2_1.PolygonType;

import nl.overheid.aerius.gml.base.geo.Geo2GeometryUtil;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.geojson.Polygon;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.shared.geo.EPSG;

/**
 * Test class for {@link Geo2GeometryUtil}.
 */
public class Geo2GeometryUtilTest {

  private final Geo2GeometryUtil geo2GeometryUtil = new Geo2GeometryUtil(EPSG.RDNEW.getSrid());

  @Test
  public void testPoint() throws AeriusException {
    final PointType pointType = createPoint();
    final Geometry geometry = geo2GeometryUtil.fromXMLPoint(pointType);
    assertTrue(geometry instanceof Point, "Not a point");
  }

  @Test
  public void testPolygon() throws AeriusException {
    final DirectPositionListType coordList = new DirectPositionListType();

    // this polygon is closed
    coordList.getValue().add(100.0); // X
    coordList.getValue().add(20.0); // Y
    coordList.getValue().add(400.0);
    coordList.getValue().add(50.0);
    coordList.getValue().add(600.0);
    coordList.getValue().add(70.0);
    coordList.getValue().add(100.0);
    coordList.getValue().add(20.0);

    final PolygonType polygonType = createPolygon(coordList);
    final Geometry geometry = geo2GeometryUtil.fromXMLPolygon(polygonType);
    assertTrue(geometry instanceof Polygon, "Not a polygon");
  }

  @Test
  public void testPolygonNotClosed() {
    final DirectPositionListType coordList = new DirectPositionListType();

    // this polygon is not closed
    coordList.getValue().add(100.0); // X
    coordList.getValue().add(20.0); // Y
    coordList.getValue().add(400.0);
    coordList.getValue().add(50.0);
    coordList.getValue().add(600.0);
    coordList.getValue().add(70.0);

    final PolygonType polygonType = createPolygon(coordList);
    assertThrows(AeriusException.class, () -> geo2GeometryUtil.fromXMLPolygon(polygonType));
  }

  @Test
  public void testInvalidSRS() throws AeriusException {
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
  public void testEmptySRS() throws AeriusException {
    final PointType pointType = createPoint();
    pointType.setSrsDimension(BigInteger.TEN);
    pointType.setSrsName(null);
    assertNotNull(geo2GeometryUtil.fromXMLPoint(pointType), "Should just return the point no srs");
    pointType.setSrsName("");
    assertNotNull(geo2GeometryUtil.fromXMLPoint(pointType), "Should just return the point on empty");
  }

  private PointType createPoint() {
    final PointType pointType = new PointType();
    final DirectPositionType value = new DirectPositionType();
    value.setValue(Arrays.asList(new Double[] {1.0, 1.0}));
    pointType.setPos(value);
    return pointType;
  }

  private PolygonType createPolygon(final DirectPositionListType directPositionListType) {
    final ObjectFactory factory = new ObjectFactory();

    final PolygonType polygonType = new PolygonType();
    final AbstractRingPropertyType abstractRingPropertyType = factory.createAbstractRingPropertyType();
    final JAXBElement<LinearRingType> linearRingType = factory.createLinearRing(factory.createLinearRingType());

    linearRingType.getValue().setPosList(directPositionListType);
    abstractRingPropertyType.setAbstractRing(linearRingType);
    polygonType.setExterior(abstractRingPropertyType);
    return polygonType;
  }
}
