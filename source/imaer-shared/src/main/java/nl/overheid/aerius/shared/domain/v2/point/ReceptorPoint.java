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
package nl.overheid.aerius.shared.domain.v2.point;

import com.fasterxml.jackson.annotation.JsonIgnore;

import nl.aerius.shared.domain.geojson.IsFeature;
import nl.overheid.aerius.shared.exception.AeriusException;

public class ReceptorPoint extends CalculationPoint {

  private static final long serialVersionUID = 1L;

  private int receptorId;
  private Boolean edgeEffect;

  public int getReceptorId() {
    return receptorId;
  }

  public void setReceptorId(final int receptorId) {
    this.receptorId = receptorId;
  }

  public Boolean getEdgeEffect() {
    return edgeEffect;
  }

  public void setEdgeEffect(final Boolean edgeEffect) {
    this.edgeEffect = edgeEffect;
  }

  @JsonIgnore
  @Override
  public int getId() {
    return receptorId;
  }

  @Override
  <T> T accept(final CalculationPointVisitor<T> visitor, final IsFeature feature) throws AeriusException {
    return visitor.visit(this, feature);
  }

}
