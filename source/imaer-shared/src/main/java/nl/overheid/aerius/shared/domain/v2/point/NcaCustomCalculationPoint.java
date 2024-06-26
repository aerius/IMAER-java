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
package nl.overheid.aerius.shared.domain.v2.point;

import java.util.ArrayList;
import java.util.List;

import nl.overheid.aerius.shared.domain.v2.geojson.IsFeature;
import nl.overheid.aerius.shared.exception.AeriusException;

public class NcaCustomCalculationPoint extends CustomCalculationPoint {

  private static final long serialVersionUID = 1L;

  private Double roadLocalFractionNO2;
  private List<EntityReference> entityReferences = new ArrayList<>();

  public Double getRoadLocalFractionNO2() {
    return roadLocalFractionNO2;
  }

  public void setRoadLocalFractionNO2(final Double roadLocalFractionNO2) {
    this.roadLocalFractionNO2 = roadLocalFractionNO2;
  }

  public List<EntityReference> getEntityReferences() {
    return entityReferences;
  }

  public void setEntityReferences(final List<EntityReference> entityReferences) {
    this.entityReferences = entityReferences;
  }

  @Override
  <T> T accept(final CalculationPointVisitor<T> visitor, final IsFeature feature) throws AeriusException {
    return visitor.visit(this, feature);
  }

}
