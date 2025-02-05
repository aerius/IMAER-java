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
package nl.overheid.aerius.gml.v1_1.source.mobile;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.mobile.v31.IsGmlOperatingHoursOffRoadVehicleSpecification;
import nl.overheid.aerius.gml.v1_1.base.CalculatorSchema;
import nl.overheid.aerius.gml.v1_1.source.Emission;
import nl.overheid.aerius.gml.v1_1.source.EmissionProperty;
import nl.overheid.aerius.shared.domain.Substance;

/**
 *
 */
@XmlRootElement(name = "OperatingHoursOffRoadVehicleSpecification", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "OperatingHoursOffRoadVehicleSpecificationType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"emissionFactor", "load",
    "power", "operatingHours"})
public class OperatingHoursOffRoadVehicleSpecification extends AbstractOffRoadVehicleSpecification
    implements IsGmlOperatingHoursOffRoadVehicleSpecification {

  private double emissionFactor;
  private int load;
  private int power;
  private int operatingHours;

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getEmissionFactor() {
    return emissionFactor;
  }

  public void setEmissionFactor(final double emissionFactor) {
    this.emissionFactor = emissionFactor;
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
