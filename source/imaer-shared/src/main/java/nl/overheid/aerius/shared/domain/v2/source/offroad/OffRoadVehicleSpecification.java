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
package nl.overheid.aerius.shared.domain.v2.source.offroad;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(property = "offRoadVehicleSpecificationType", use = Id.NAME)
@JsonSubTypes({
@Type(value = ConsumptionOffRoadVehicleSpecification.class, name = "CONSUMPTION"),
@Type(value = OperatingHoursOffRoadVehicleSpecification.class, name = "OPERATING_HOURS"),
})
public abstract class OffRoadVehicleSpecification implements Serializable {

  private static final long serialVersionUID = 1L;

  private String fuelCode;

  public String getFuelCode() {
    return fuelCode;
  }

  public void setFuelCode(final String fuelCode) {
    this.fuelCode = fuelCode;
  }

}
