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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nl.aerius.shared.geo.BBox;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;

/**
 * Test class for {@link BBox}.
 */
public class BBoxTest {

  /**
   * Bounding box of the Netherlands receptor grid.
   */
  private static final BBox RECEPTOR_BBOX = new BBox(3604, 296800, 287959, 629300);

  @Test
  public void testIsPointWithinBoundingBox() {
    final Point point1 = new Point(0, 0);
    Assertions.assertFalse(RECEPTOR_BBOX.isPointWithinBoundingBox(point1), "New point shouldn't be");
    point1.setX(135583);

    Assertions.assertFalse(RECEPTOR_BBOX.isPointWithinBoundingBox(point1), "Both coords need to be set");

    final Point point2 = new Point(0, 0);
    point2.setY(455387);
    Assertions.assertFalse(RECEPTOR_BBOX.isPointWithinBoundingBox(point2), "Both coords need to be set");
    point2.setX(135583);
    Assertions.assertTrue(RECEPTOR_BBOX.isPointWithinBoundingBox(point2), "Both set");

    point2.setX(RECEPTOR_BBOX.getMinX());
    point2.setY(RECEPTOR_BBOX.getMinY());
    Assertions.assertTrue(RECEPTOR_BBOX.isPointWithinBoundingBox(point2), "minimum should be included");
    point2.setX(RECEPTOR_BBOX.getMaxX());
    point2.setY(RECEPTOR_BBOX.getMaxY());
    Assertions.assertTrue(RECEPTOR_BBOX.isPointWithinBoundingBox(point2), "maximum should be included");
  }
}
