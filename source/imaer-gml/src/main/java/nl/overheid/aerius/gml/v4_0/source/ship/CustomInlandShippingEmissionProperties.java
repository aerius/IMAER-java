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
package nl.overheid.aerius.gml.v4_0.source.ship;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.ship.IsGmlCustomInlandShippingEmissionProperties;
import nl.overheid.aerius.gml.v4_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v4_0.source.EmissionProperty;

@XmlType(name = "CustomInlandShippingEmissionPropertiesType", namespace = CalculatorSchema.NAMESPACE, propOrder = {
    "emissionFactorsEmpty", "emissionFactorsLaden", "heatContentEmpty", "heatContentLaden", "emissionHeightEmpty", "emissionHeightLaden"})
public class CustomInlandShippingEmissionProperties implements IsGmlCustomInlandShippingEmissionProperties {

  private List<EmissionProperty> emissionFactorsEmpty = new ArrayList<>();
  private List<EmissionProperty> emissionFactorsLaden = new ArrayList<>();
  private double heatContentEmpty;
  private double heatContentLaden;
  private double emissionHeightEmpty;
  private double emissionHeightLaden;

  @Override
  @XmlElement(name = "emissionFactorEmpty", namespace = CalculatorSchema.NAMESPACE)
  public List<EmissionProperty> getEmissionFactorsEmpty() {
    return emissionFactorsEmpty;
  }

  public void setEmissionFactorsEmpty(final List<EmissionProperty> emissionFactorsEmpty) {
    this.emissionFactorsEmpty = emissionFactorsEmpty;
  }

  @Override
  @XmlElement(name = "emissionFactorLaden", namespace = CalculatorSchema.NAMESPACE)
  public List<EmissionProperty> getEmissionFactorsLaden() {
    return emissionFactorsLaden;
  }

  public void setEmissionFactorsLaden(final List<EmissionProperty> emissionFactorsLaden) {
    this.emissionFactorsLaden = emissionFactorsLaden;
  }

  @Override
  @XmlElement(name = "heatContentEmpty", namespace = CalculatorSchema.NAMESPACE)
  public double getHeatContentEmpty() {
    return heatContentEmpty;
  }

  public void setHeatContentEmpty(final double heatContentEmpty) {
    this.heatContentEmpty = heatContentEmpty;
  }

  @Override
  @XmlElement(name = "heatContentLaden", namespace = CalculatorSchema.NAMESPACE)
  public double getHeatContentLaden() {
    return heatContentLaden;
  }

  public void setHeatContentLaden(final double heatContentLaden) {
    this.heatContentLaden = heatContentLaden;
  }

  @Override
  @XmlElement(name = "emissionHeightEmpty", namespace = CalculatorSchema.NAMESPACE)
  public double getEmissionHeightEmpty() {
    return emissionHeightEmpty;
  }

  public void setEmissionHeightEmpty(final double emissionHeightEmpty) {
    this.emissionHeightEmpty = emissionHeightEmpty;
  }

  @Override
  @XmlElement(name = "emissionHeightLaden", namespace = CalculatorSchema.NAMESPACE)
  public double getEmissionHeightLaden() {
    return emissionHeightLaden;
  }

  public void setEmissionHeightLaden(final double emissionHeightLaden) {
    this.emissionHeightLaden = emissionHeightLaden;
  }

}
