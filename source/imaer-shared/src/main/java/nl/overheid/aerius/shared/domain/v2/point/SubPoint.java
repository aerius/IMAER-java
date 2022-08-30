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

public class SubPoint extends CalculationPoint {

  private static final long serialVersionUID = 1L;

  private int subPointId;
  private int receptorId;
  private int level;

  public int getSubPointId() {
    return subPointId;
  }

  public void setSubPointId(final int subPointId) {
    this.subPointId = subPointId;
  }

  public int getReceptorId() {
    return receptorId;
  }

  public void setReceptorId(final int receptorId) {
    this.receptorId = receptorId;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(final int level) {
    this.level = level;
  }

  @JsonIgnore
  @Override
  public int getId() {
    return subPointId;
  }

  @Override
  <T> T accept(final CalculationPointVisitor<T> visitor, final IsFeature feature) throws AeriusException {
    return visitor.visit(this, feature);
  }

}
