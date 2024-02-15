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
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import net.opengis.gml.v_3_2_1.DirectPositionType;
import net.opengis.gml.v_3_2_1.PointType;

import nl.overheid.aerius.gml.base.GMLSchema;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.geo.EPSG;

class Geometry2GMLTest {
  private final Geometry2GML geo2gml = new Geometry2GML(GMLSchema.getSRSName(EPSG.RDNEW.getSrid()));

  private GmlPoint createMockPoint() {
    return new GmlPoint() {
      private PointType gmlPoint = null;

      @Override
      public PointType getGmlPoint() {
        return gmlPoint;
      }

      @Override
      public void setGmlPoint(final PointType gmlPoint) {
        this.gmlPoint = gmlPoint;
      }
    };
  }

  static Stream<Arguments> pointProvider() {
    return Stream.of(
        Arguments.of("Rounding up", new Point(5.00015, -5.00015), "5.0", "-5.0"),
        Arguments.of("Rounding down", new Point(5.00014, -5.00014), "5.0", "-5.0"),
        Arguments.of("Precision floats with rounding", new Point(5.9999999, -5.9999999), "6.0", "-6.0"),
        Arguments.of("Floating-point errors", new Point(0.1 + 0.2 - 0.3, 1.0 - 0.9), "0.0", "0.1"),
        Arguments.of("Zero value input", new Point(0.0, 0.0), "0.0", "0.0"),
        Arguments.of("Integer inputs converted to float", new Point(2, 3), "2.0", "3.0"));
  }

  @ParameterizedTest(name = "{0}: Expected X={2} and Y={3}")
  @MethodSource("pointProvider")
  void testPointConversion(final String message, final Point inputPoint, final String expectedX, final String expectedY) throws AeriusException {
    final GmlPoint resultPoint = geo2gml.toXMLPoint(inputPoint, createMockPoint());
    assertCoordinates(expectedX, expectedY, resultPoint.getGmlPoint().getPos());
  }

  private void assertCoordinates(final String expectedX, final String expectedY, final DirectPositionType position) {
    assertNotNull(position, "Position should not be null");
    final List<Double> coords = position.getValue();
    assertEquals(2, coords.size(), "Incorrect number of coordinates");
    assertEquals(expectedX, String.valueOf(coords.get(0)), "X coordinate mismatch");
    assertEquals(expectedY, String.valueOf(coords.get(1)), "Y coordinate mismatch");
  }
}
