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

import nl.overheid.aerius.shared.domain.v2.point.AssessmentCategory;
import nl.overheid.aerius.shared.domain.v2.point.CustomCalculationPoint;
import nl.overheid.aerius.shared.domain.v2.point.NcaCustomCalculationPoint;

public final class NcaPointHeightSupplier {

  public static final double DEFAULT_CUSTOM_POINT_HEIGHT = 1.5;

  private NcaPointHeightSupplier() {
  }

  /**
   * Returns the height of an NCA point according to its assessment category or its custom height.
   *
   * @param point calculation point
   * @return height of the calculation point
   */
  public static double getHeight(final CustomCalculationPoint point) {
    return getHeight(point instanceof NcaCustomCalculationPoint ? point.getAssessmentCategory() : null, point.getHeight());
  }

  /**
   * Returns the given height or if the height is null it returns the height based on the given assessment category.
   *
   * @param assessmentCategory category to determine height in case given height is null
   * @param height height
   * @return calculated height
   */
  public static Double getHeight(final AssessmentCategory assessmentCategory, final Double height) {
    if (height == null) {
      return assessmentCategory == null ? DEFAULT_CUSTOM_POINT_HEIGHT : assessmentCategory.getDefaultHeight();
    } else {
      return height;
    }
  }
}
