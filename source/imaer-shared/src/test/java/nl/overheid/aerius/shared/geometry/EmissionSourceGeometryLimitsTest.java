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
package nl.overheid.aerius.shared.geometry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test class for {@link EmissionSourceLimits}.
 */
class EmissionSourceGeometryLimitsTest {

  @Test
  void testMaxLineLength() {
    final EmissionSourceLimits cl = new EmissionSourceLimits();
    cl.setMaxLineLength(10);
    assertTrue(cl.isWithinLineLengthLimit(5), "MaxLineLength");
    assertTrue(cl.isWithinLineLengthLimit(10), "MaxLineLength");
    assertFalse(cl.isWithinLineLengthLimit(11), "MaxLineLength");
    assertFalse(cl.isWithinLineLengthLimit(15), "MaxLineLength");
  }

  @Test
  void testMaxPolygonSurface() {
    final EmissionSourceLimits cl = new EmissionSourceLimits();
    cl.setMaxPolygonSurface(10);
    assertTrue(cl.isWithinPolygonSurfaceLimit(5), "MaxPolygonSurface");
    assertTrue(cl.isWithinPolygonSurfaceLimit(10), "MaxPolygonSurface");
    assertFalse(cl.isWithinPolygonSurfaceLimit(11), "MaxPolygonSurface");
    assertFalse(cl.isWithinPolygonSurfaceLimit(15), "MaxPolygonSurface");
  }

  @Test
  void testMaxSources() {
    final EmissionSourceLimits cl = new EmissionSourceLimits();
    cl.setMaxSources(10);
    assertTrue(cl.isWithinMaxSourcesLimit(5), "MaxSources");
    assertTrue(cl.isWithinMaxSourcesLimit(10), "MaxSources");
    assertFalse(cl.isWithinMaxSourcesLimit(11), "MaxSources");
    assertFalse(cl.isWithinMaxSourcesLimit(15), "MaxSources");
  }
}
