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
package nl.overheid.aerius.gml.v2_2.source.characteristics;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.characteristics.IsGmlCalculatedHeatContent;
import nl.overheid.aerius.gml.v2_2.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.ops.OutflowDirectionType;
import nl.overheid.aerius.shared.domain.ops.OutflowVelocityType;

/**
 *
 */
@XmlRootElement(name = "CalculatedHeatContent", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "CalculatedHeatContentType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"emissionTemperature", "outflowDiameter",
    "outflowVelocity", "outflowDirection"})
public class CalculatedHeatContent extends AbstractHeatContent implements IsGmlCalculatedHeatContent {

  private double emissionTemperature;
  private OutflowDirectionType outflowDirection;
  private double outflowDiameter;
  private double outflowVelocity;

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Double getEmissionTemperature() {
    return emissionTemperature;
  }

  public void setEmissionTemperature(final Double emissionTemperature) {
    this.emissionTemperature = emissionTemperature;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public OutflowDirectionType getOutflowDirection() {
    return outflowDirection;
  }

  public void setOutflowDirection(final OutflowDirectionType outflowDirection) {
    this.outflowDirection = outflowDirection;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getOutflowDiameter() {
    return outflowDiameter;
  }

  public void setOutflowDiameter(final double outflowDiameter) {
    this.outflowDiameter = outflowDiameter;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getOutflowVelocity() {
    return outflowVelocity;
  }

  public void setOutflowVelocity(final double outflowVelocity) {
    this.outflowVelocity = outflowVelocity;
  }

  @Override
  @XmlTransient
  public OutflowVelocityType getOutflowVelocityType() {
    return OutflowVelocityType.ACTUAL_FLOW;
  }

}
