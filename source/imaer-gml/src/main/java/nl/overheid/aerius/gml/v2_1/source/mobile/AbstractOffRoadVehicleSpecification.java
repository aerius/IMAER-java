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
package nl.overheid.aerius.gml.v2_1.source.mobile;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.mobile.v31.IsGmlOffRoadVehicleSpecification;
import nl.overheid.aerius.gml.v2_1.base.CalculatorSchema;

/**
 *
 */
@XmlSeeAlso({ConsumptionOffRoadVehicleSpecification.class, OperatingHoursOffRoadVehicleSpecification.class})
@XmlType(name = "OffRoadVehicleSpecificationType", namespace = CalculatorSchema.NAMESPACE)
public class AbstractOffRoadVehicleSpecification implements IsGmlOffRoadVehicleSpecification {

  private String fuelCode;

  @Override
  @XmlElement(name = "fuelType", namespace = CalculatorSchema.NAMESPACE)
  public String getFuelCode() {
    return fuelCode;
  }

  public void setFuelCode(final String fuelCode) {
    this.fuelCode = fuelCode;
  }

}
