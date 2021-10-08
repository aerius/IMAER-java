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
package nl.overheid.aerius.geo.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test class for {@link WKTGeometry}.
 */
public class WKTGeometryTest {

  @Test
  public void testGetPoints() {
    assertEquals("1 2,3 4", new WKTGeometry("LINESTRING(1 2,3 4)").getPoints(), "Test line points");
    assertEquals("1 2,3 4", new WKTGeometry("POLYGON((1 2,3 4))").getPoints(), "Test polygon points");
    assertEquals(null, new WKTGeometry("L1 2,3 4)").getPoints(), "Test line points invalid");
    assertEquals(null, new WKTGeometry().getPoints(), "Test line points empty");
  }
}
