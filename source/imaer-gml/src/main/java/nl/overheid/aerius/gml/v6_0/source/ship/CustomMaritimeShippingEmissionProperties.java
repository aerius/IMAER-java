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
package nl.overheid.aerius.gml.v6_0.source.ship;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.ship.IsGmlCustomMaritimeShippingEmissionProperties;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v6_0.source.EmissionProperty;

@XmlType(name = "CustomMaritimeShippingEmissionPropertiesType", namespace = CalculatorSchema.NAMESPACE, propOrder = {
    "emissionFactors", "heatContent", "emissionHeight"})
public class CustomMaritimeShippingEmissionProperties implements IsGmlCustomMaritimeShippingEmissionProperties {

  private List<EmissionProperty> emissionFactors = new ArrayList<>();
  private double heatContent;
  private double emissionHeight;

  @Override
  @XmlElement(name = "emissionFactor", namespace = CalculatorSchema.NAMESPACE)
  public List<EmissionProperty> getEmissionFactors() {
    return emissionFactors;
  }

  public void setEmissionFactors(final List<EmissionProperty> emissionFactors) {
    this.emissionFactors = emissionFactors;
  }

  @Override
  @XmlElement(name = "heatContent", namespace = CalculatorSchema.NAMESPACE)
  public double getHeatContent() {
    return heatContent;
  }

  public void setHeatContent(final double heatContent) {
    this.heatContent = heatContent;
  }

  @Override
  @XmlElement(name = "emissionHeight", namespace = CalculatorSchema.NAMESPACE)
  public double getEmissionHeight() {
    return emissionHeight;
  }

  public void setEmissionHeight(final double emissionHeight) {
    this.emissionHeight = emissionHeight;
  }

}
