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
package nl.overheid.aerius.gml.v2_0.source.mobile;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.mobile.v31.IsGmlConsumptionOffRoadVehicleSpecification;
import nl.overheid.aerius.gml.v2_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v2_0.source.Emission;
import nl.overheid.aerius.gml.v2_0.source.EmissionProperty;
import nl.overheid.aerius.shared.domain.Substance;

/**
 *
 */
@XmlRootElement(name = "ConsumptionOffRoadVehicleSpecification", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "ConsumptionOffRoadVehicleSpecificationType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"emissionFactor",
    "energyEfficiency", "consumption"})
public class ConsumptionOffRoadVehicleSpecification extends AbstractOffRoadVehicleSpecification
    implements IsGmlConsumptionOffRoadVehicleSpecification {

  private double emissionFactor;
  private int energyEfficiency;
  private int consumption;

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getEmissionFactor() {
    return emissionFactor;
  }

  public void setEmissionFactor(final double emissionFactor) {
    this.emissionFactor = emissionFactor;
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

  @XmlTransient
  @Override
  public List<EmissionProperty> getEmissionFactors() {
    // In this version the emission factors were always for NOx.
    final Emission emission = new Emission();
    emission.setSubstance(Substance.NOX);
    emission.setValue(emissionFactor);
    final EmissionProperty emissionProperty = new EmissionProperty(emission);
    return List.of(emissionProperty);
  }

}
