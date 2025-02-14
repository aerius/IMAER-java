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
package nl.overheid.aerius.gml.v3_1.source.mobile;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.mobile.v31.IsGmlConsumptionOffRoadVehicleSpecification;
import nl.overheid.aerius.gml.v3_1.base.CalculatorSchema;
import nl.overheid.aerius.gml.v3_1.source.EmissionProperty;

/**
 *
 */
@XmlRootElement(name = "ConsumptionOffRoadVehicleSpecification", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "ConsumptionOffRoadVehicleSpecificationType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"emissionFactors",
    "energyEfficiency", "consumption"})
public class ConsumptionOffRoadVehicleSpecification extends AbstractOffRoadVehicleSpecification
    implements IsGmlConsumptionOffRoadVehicleSpecification {

  private List<EmissionProperty> emissionFactors = new ArrayList<>();
  private int energyEfficiency;
  private int consumption;

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
  public int getEnergyEfficiency() {
    return energyEfficiency;
  }

  public void setEnergyEfficiency(final int energyEfficiency) {
    this.energyEfficiency = energyEfficiency;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public int getConsumption() {
    return consumption;
  }

  public void setConsumption(final int consumption) {
    this.consumption = consumption;
  }

}
