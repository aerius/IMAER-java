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
package nl.overheid.aerius.gml.v6_0.source.mobile;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.mobile.v40.IsGmlOperatingHoursOffRoadVehicleSpecification;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v6_0.source.EmissionProperty;

/**
 *
 */
@XmlRootElement(name = "OperatingHoursOffRoadVehicleSpecification", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "OperatingHoursOffRoadVehicleSpecificationType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"emissionFactors", "load",
    "power", "operatingHours"})
public class OperatingHoursOffRoadVehicleSpecification extends AbstractOffRoadVehicleSpecification
    implements IsGmlOperatingHoursOffRoadVehicleSpecification {

  private List<EmissionProperty> emissionFactors = new ArrayList<>();
  private int load;
  private int power;
  private int operatingHours;

  @Override
  @XmlElement(name = "emissionFactor", namespace = CalculatorSchema.NAMESPACE)
  public List<EmissionProperty> getEmissionFactors() {
    return emissionFactors;
  }

  public void setEmissionFactors(final List<EmissionProperty> emissionFactors) {
    this.emissionFactors = emissionFactors;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public int getLoad() {
    return load;
  }

  public void setLoad(final int load) {
    this.load = load;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public int getPower() {
    return power;
  }

  public void setPower(final int power) {
    this.power = power;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public int getOperatingHours() {
    return operatingHours;
  }

  public void setOperatingHours(final int operatingHours) {
    this.operatingHours = operatingHours;
  }

}
