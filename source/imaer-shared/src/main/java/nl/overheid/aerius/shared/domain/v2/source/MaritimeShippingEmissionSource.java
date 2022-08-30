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
package nl.overheid.aerius.shared.domain.v2.source;

import com.fasterxml.jackson.annotation.JsonIgnore;

import nl.aerius.shared.domain.geojson.IsFeature;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.MaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.ShippingMovementType;
import nl.overheid.aerius.shared.exception.AeriusException;

public abstract class MaritimeShippingEmissionSource extends EmissionSourceWithSubSources<MaritimeShipping> {

  private static final long serialVersionUID = 1L;

  private ShippingMovementType movementType;
  private String mooringAId;
  private String mooringBId;

  protected MaritimeShippingEmissionSource(final ShippingMovementType movementType) {
    this.movementType = movementType;
  }

  @JsonIgnore
  public ShippingMovementType getMovementType() {
    return movementType;
  }

  public void setMovementType(final ShippingMovementType movementType) {
    this.movementType = movementType;
  }

  public String getMooringAId() {
    return mooringAId;
  }

  public void setMooringAId(final String mooringAId) {
    this.mooringAId = mooringAId;
  }

  public String getMooringBId() {
    return mooringBId;
  }

  public void setMooringBId(final String mooringBId) {
    this.mooringBId = mooringBId;
  }

  @Override
  <T> T accept(final EmissionSourceVisitor<T> visitor, final IsFeature feature) throws AeriusException {
    return visitor.visit(this, feature);
  }

}
