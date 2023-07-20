/*
 * Crown copyright
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
package nl.overheid.aerius.shared.domain.v2.characteristics.adms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nl.overheid.aerius.shared.domain.v2.characteristics.adms.NcaPointHeightSupplier;
import nl.overheid.aerius.shared.domain.v2.point.AssessmentCategory;
import nl.overheid.aerius.shared.domain.v2.point.NcaCustomCalculationPoint;

/**
 * Test class for {@link NcaPointHeightSupplier}.
 */
class NcaPointHeightSupplierTest {

  @Test
  void testPoint() {
    final NcaCustomCalculationPoint point = new NcaCustomCalculationPoint();
    assertEquals(1.5, NcaPointHeightSupplier.getHeight(point), "Points should be at default height 1.5");
  }

  @Test
  void testPointWithCategory() {
    final NcaCustomCalculationPoint point = new NcaCustomCalculationPoint();
    point.setAssessmentCategory(AssessmentCategory.ECOLOGY);
    assertEquals(1.0, NcaPointHeightSupplier.getHeight(point), "Ecology should be at height 1.0");
  }

  @Test
  void testPointWithCategoryAndHeight() {
    final NcaCustomCalculationPoint point = new NcaCustomCalculationPoint();
    point.setAssessmentCategory(AssessmentCategory.ECOLOGY);
    point.setHeight(1.337);
    assertEquals(1.337, NcaPointHeightSupplier.getHeight(point), "Point with custom height should be at that height");
  }
}
