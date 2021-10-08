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
package nl.overheid.aerius.gml.v3_1.source.road;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.road.IsGmlStandardVehicle;
import nl.overheid.aerius.gml.v3_1.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.v2.source.road.VehicleType;

/**
 *
 */
@XmlRootElement(name = "StandardVehicle", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "StandardVehicleType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"vehicleType", "stagnationFactor", "maximumSpeed",
    "strictEnforcement"})
public class StandardVehicle extends AbstractVehicle implements IsGmlStandardVehicle {

  private VehicleType vehicleType;
  private double stagnationFactor;
  private Integer maximumSpeed;
  private Boolean strictEnforcement;

  @Override
  @XmlAttribute
  public VehicleType getVehicleType() {
    return vehicleType;
  }

  public void setVehicleType(final VehicleType vehicleType) {
    this.vehicleType = vehicleType;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getStagnationFactor() {
    return stagnationFactor;
  }

  public void setStagnationFactor(final double stagnationFactor) {
    this.stagnationFactor = stagnationFactor;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Integer getMaximumSpeed() {
    return maximumSpeed;
  }

  public void setMaximumSpeed(final Integer maximumSpeed) {
    this.maximumSpeed = maximumSpeed;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Boolean isStrictEnforcement() {
    return strictEnforcement;
  }

  public void setStrictEnforcement(final Boolean strictEnforcement) {
    this.strictEnforcement = strictEnforcement;
  }
}
