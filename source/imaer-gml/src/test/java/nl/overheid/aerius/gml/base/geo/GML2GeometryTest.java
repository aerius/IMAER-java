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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import net.opengis.gml.v_3_2.DirectPositionListType;
import net.opengis.gml.v_3_2.LineStringType;
import net.opengis.gml.v_3_2.ObjectFactory;
import net.opengis.gml.v_3_2.PointType;
import net.opengis.gml.v_3_2.PolygonType;

import nl.overheid.aerius.gml.base.FeatureMember;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.shared.geo.EPSG;

/**
 * Test class for {@link GML2Geometry}.
 */
@ExtendWith(MockitoExtension.class)
class GML2GeometryTest {
  final ObjectFactory factory = new ObjectFactory();

  @Mock FeatureMember featureMember;
  @Mock GmlEmissionSourceGeometry gmlEmissionSourceGeometry;
  @Mock GmlPoint gmlPoint;
  @Mock GmlLineString gmlLineString;
  @Mock GmlPolygon gmlPolygon;

  private final GML2Geometry gml2Geometry = new GML2Geometry(EPSG.RDNEW.getSrid());

  @BeforeEach
  void beforeEach() {
    doReturn(gmlEmissionSourceGeometry).when(featureMember).getEmissionSourceGeometry();
  }

  @Test
  void testPointIncomplete() {
    mockPoint(List.of(1.0), true);

    assertGeoFail(ImaerExceptionReason.GML_GEOMETRY_INVALID, "Expected to give the reason invalid point geometry.");
  }

  @Test
  void testPointInvalid() {
    mockPoint(List.of(1.0, 2.0), false);

    assertGeoFail(ImaerExceptionReason.GML_GEOMETRY_NOT_PERMITTED, "Expected to give the reason point geometry not permitted.");
  }

  @Test
  void testLineStringIncomplete() {
    mockLineString(List.of(1.0, 2.0), true);

    assertGeoFail(ImaerExceptionReason.GML_GEOMETRY_INVALID, "Expected to give the reason invalid line geometry.");
  }

  @Test
  void testLineStringInvalid() {
    mockLineString(List.of(1.0, 2.0, 2.0, 1.0), false);

    assertGeoFail(ImaerExceptionReason.GML_GEOMETRY_NOT_PERMITTED, "Expected to give the reason line geometry not permitted.");
  }

  @Test
  void testPolygonIncomplete() {
    mockPolygon(List.of(1.0, 2.0), true);

    assertGeoFail(ImaerExceptionReason.GML_GEOMETRY_INVALID, "Expected to give the reason invalid polygon geometry.");
  }

  @Test
  void testPolygonInvalid() {
    mockPolygon(List.of(1.0, 2.0, 2.0, 1.0, 1.0, 2.0), false);

    assertGeoFail(ImaerExceptionReason.GML_GEOMETRY_NOT_PERMITTED, "Expected to give the reason geometry not permitted.");
  }

  private void assertGeoFail(final ImaerExceptionReason expectedReason, final String message) {
    final AeriusException exception = assertThrows(AeriusException.class, () -> gml2Geometry.getGeometry(featureMember));
    assertEquals(expectedReason, exception.getReason(), message);
  }

  private void mockPoint(final List<Double> numbers, final boolean valid) {
    final PointType pointType = Geo2GeometryUtilTest.createPoint(numbers);

    lenient().doReturn(valid).when(featureMember).isValidGeometry(any());
    lenient().doReturn(gmlPoint).when(gmlEmissionSourceGeometry).getPoint();
    doReturn(pointType).when(gmlPoint).getGmlPoint();
  }

  private void mockLineString(final List<Double> numbers, final boolean valid) {
    final LineStringType lineStringType = factory.createLineStringType();
    final DirectPositionListType list = new DirectPositionListType();
    list.setValue(numbers);
    lineStringType.setPosList(list);

    lenient().doReturn(valid).when(featureMember).isValidGeometry(any());
    lenient().doReturn(gmlLineString).when(gmlEmissionSourceGeometry).getLineString();
    doReturn(lineStringType).when(gmlLineString).getGMLLineString();
  }

  private void mockPolygon(final List<Double> numbers, final boolean valid) {
    final PolygonType polygonType = Geo2GeometryUtilTest.createPolygon(numbers);

    lenient().doReturn(valid).when(featureMember).isValidGeometry(any());
    lenient().doReturn(gmlPolygon).when(gmlEmissionSourceGeometry).getPolygon();
    doReturn(polygonType).when(gmlPolygon).getGmlPolygon();
  }
}
