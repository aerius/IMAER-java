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
package nl.overheid.aerius.gml.v2_1.source.characteristics;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.v2_1.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "HeatContentSpecificationType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"emissionTemperature", "outflowSurface",
    "outflowVelocity"})
public class HeatContentSpecification {

  private double emissionTemperature;
  private double outflowSurface;
  private double outflowVelocity;

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getEmissionTemperature() {
    return emissionTemperature;
  }

  public void setEmissionTemperature(final double emissionTemperature) {
    this.emissionTemperature = emissionTemperature;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getOutflowSurface() {
    return outflowSurface;
  }

  public void setOutflowSurface(final double outflowSurface) {
    this.outflowSurface = outflowSurface;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getOutflowVelocity() {
    return outflowVelocity;
  }

  public void setOutflowVelocity(final double outflowVelocity) {
    this.outflowVelocity = outflowVelocity;
  }
}
