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

@JsonTypeInfo(property = "mooringInlandShippingType", use = Id.NAME)
@JsonSubTypes({
    @Type(value = CustomMooringInlandShipping.class, name = "CUSTOM"),
    @Type(value = StandardMooringInlandShipping.class, name = "STANDARD"),
})
public abstract class MooringInlandShipping extends AbstractShipping {

  private static final long serialVersionUID = 1L;

  private int shipsPerTimeUnit;
  private TimeUnit timeUnit;
  private int percentageLaden;
  private int averageResidenceTime;
  private double shorePowerFactor;

  public int getShipsPerTimeUnit() {
    return shipsPerTimeUnit;
  }

  public void setShipsPerTimeUnit(final int shipsPerTimeUnit) {
    this.shipsPerTimeUnit = shipsPerTimeUnit;
  }

  public TimeUnit getTimeUnit() {
    return timeUnit;
  }

  public void setTimeUnit(final TimeUnit timeUnit) {
    this.timeUnit = timeUnit;
  }

  public int getPercentageLaden() {
    return percentageLaden;
  }

  public void setPercentageLaden(final int percentageLaden) {
    this.percentageLaden = percentageLaden;
  }

  public double getShorePowerFactor() {
    return shorePowerFactor;
  }

  public void setShorePowerFactor(final double shorePowerFactor) {
    this.shorePowerFactor = shorePowerFactor;
  }

  public int getAverageResidenceTime() {
    return averageResidenceTime;
  }

  public void setAverageResidenceTime(final int averageResidenceTime) {
    this.averageResidenceTime = averageResidenceTime;
  }

}
