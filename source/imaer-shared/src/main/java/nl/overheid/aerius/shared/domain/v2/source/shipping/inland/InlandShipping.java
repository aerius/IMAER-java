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
package nl.overheid.aerius.shared.domain.v2.source.shipping.inland;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.source.shipping.base.AbstractShipping;

@JsonTypeInfo(property = "inlandShippingType", use = Id.NAME)
@JsonSubTypes({
    @Type(value = CustomInlandShipping.class, name = "CUSTOM"),
    @Type(value = StandardInlandShipping.class, name = "STANDARD"),
})
public abstract class InlandShipping extends AbstractShipping {

  private static final long serialVersionUID = 1L;

  private TimeUnit timeUnitAtoB;
  private TimeUnit timeUnitBtoA;
  private Integer movementsAtoBPerTimeUnit;
  private Integer movementsBtoAPerTimeUnit;
  private Integer percentageLadenAtoB;
  private Integer percentageLadenBtoA;

  public TimeUnit getTimeUnitAtoB() {
    return timeUnitAtoB;
  }

  public void setTimeUnitAtoB(final TimeUnit timeUnitAtoB) {
    this.timeUnitAtoB = timeUnitAtoB;
  }

  public TimeUnit getTimeUnitBtoA() {
    return timeUnitBtoA;
  }

  public void setTimeUnitBtoA(final TimeUnit timeUnitBtoA) {
    this.timeUnitBtoA = timeUnitBtoA;
  }

  public Integer getMovementsAtoBPerTimeUnit() {
    return movementsAtoBPerTimeUnit;
  }

  public void setMovementsAtoBPerTimeUnit(final Integer movementsAtoBPerTimeUnit) {
    this.movementsAtoBPerTimeUnit = movementsAtoBPerTimeUnit;
  }

  public Integer getMovementsBtoAPerTimeUnit() {
    return movementsBtoAPerTimeUnit;
  }

  public void setMovementsBtoAPerTimeUnit(final Integer movementsBtoAPerTimeUnit) {
    this.movementsBtoAPerTimeUnit = movementsBtoAPerTimeUnit;
  }

  public Integer getPercentageLadenAtoB() {
    return percentageLadenAtoB;
  }

  public void setPercentageLadenAtoB(final Integer percentageLadenAtoB) {
    this.percentageLadenAtoB = percentageLadenAtoB;
  }

  public Integer getPercentageLadenBtoA() {
    return percentageLadenBtoA;
  }

  public void setPercentageLadenBtoA(final Integer percentageLadenBtoA) {
    this.percentageLadenBtoA = percentageLadenBtoA;
  }

}
