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

import org.junit.jupiter.api.Test;

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

  @Test
  public void testRoundingUp() throws AeriusException {
    final GmlPoint resultPoint = geo2gml.toXMLPoint(new Point(5.00015, -5.00015), createMockPoint());
    final String expectedX = "5.0";
    final String expectedY = "-5.0";
    assertCoordinates(expectedX, expectedY, resultPoint.getGmlPoint().getPos());
  }

  @Test
  public void testRoundingDown() throws AeriusException {
    final GmlPoint resultPoint = geo2gml.toXMLPoint(new Point(5.00014, -5.00014), createMockPoint());
    final String expectedX = "5.0";
    final String expectedY = "-5.0";
    assertCoordinates(expectedX, expectedY, resultPoint.getGmlPoint().getPos());
  }

  @Test
  public void testPrecisionHandling() throws AeriusException {
    final GmlPoint resultPoint = geo2gml.toXMLPoint(new Point(5.9999999, -5.9999999), createMockPoint());
    final String expectedX = "6.0";
    final String expectedY = "-6.0";
    assertCoordinates(expectedX, expectedY, resultPoint.getGmlPoint().getPos());
  }

  @Test
  public void testDivision() throws AeriusException {
    final double errorX = 0.1 + 0.2 - 0.3; // 5.551115123125783E-17
    final double errorY = 1.0 - 0.9; // 0.09999999999999998

    final GmlPoint resultPoint = geo2gml.toXMLPoint(new Point(errorX, errorY), createMockPoint());
    final String expectedX = "0.0";
    final String expectedY = "0.1";
    assertCoordinates(expectedX, expectedY, resultPoint.getGmlPoint().getPos());
  }

  @Test
  public void testZeroValue() throws AeriusException {
    final GmlPoint resultPoint = geo2gml.toXMLPoint(new Point(0.0, 0.0), createMockPoint());
    final String expectedX = "0.0";
    final String expectedY = "0.0";
    assertCoordinates(expectedX, expectedY, resultPoint.getGmlPoint().getPos());
  }

  @Test
  public void testInteger() throws AeriusException {
    final GmlPoint mockPoint = createMockPoint();
    final GmlPoint resultPoint = geo2gml.toXMLPoint(new Point(2, 3), mockPoint);
    // Questionable result (why not 2 and 3?), but OK
    final String expectedX = "2.0";
    final String expectedY = "3.0";
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
