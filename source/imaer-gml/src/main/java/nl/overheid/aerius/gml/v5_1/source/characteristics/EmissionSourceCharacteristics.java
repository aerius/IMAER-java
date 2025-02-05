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
package nl.overheid.aerius.gml.v5_1.source.characteristics;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.characteristics.IsGmlOPSSourceCharacteristics;
import nl.overheid.aerius.gml.v5_1.base.CalculatorSchema;

/**
 *
 */
@XmlRootElement(name = "EmissionSourceCharacteristics", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "EmissionSourceCharacteristicsType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"heatContentProperty", "emissionHeight",
    "spread", "diurnalVariationProperty"})
public class EmissionSourceCharacteristics extends AbstractSourceCharacteristics implements IsGmlOPSSourceCharacteristics {

  private AbstractHeatContent heatContent;
  private double emissionHeight;
  private Double spread;
  private AbstractDiurnalVariation diurnalVariation;

  @XmlElement(name = "heatContent", namespace = CalculatorSchema.NAMESPACE)
  public HeatContentProperty getHeatContentProperty() {
    return heatContent == null ? null : new HeatContentProperty(heatContent);
  }

  public void setHeatContentProperty(final HeatContentProperty heatContent) {
    this.heatContent = heatContent == null ? null : heatContent.getProperty();
  }

  @Override
  @XmlTransient
  public AbstractHeatContent getHeatContent() {
    return heatContent;
  }

  public void setHeatContent(final AbstractHeatContent heatContent) {
    this.heatContent = heatContent;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getEmissionHeight() {
    return emissionHeight;
  }

  public void setEmissionHeight(final double emissionHeight) {
    this.emissionHeight = emissionHeight;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Double getSpread() {
    return spread;
  }

  public void setSpread(final Double spread) {
    this.spread = spread;
  }

  @XmlElement(name = "diurnalVariation", namespace = CalculatorSchema.NAMESPACE)
  public DiurnalVariationProperty getDiurnalVariationProperty() {
    return diurnalVariation == null ? null : new DiurnalVariationProperty(diurnalVariation);
  }

  public void setDiurnalVariationProperty(final DiurnalVariationProperty diurnalVariation) {
    this.diurnalVariation = diurnalVariation == null ? null : diurnalVariation.getProperty();
  }

  @Override
  @XmlTransient
  public AbstractDiurnalVariation getTimeVaryingProfile() {
    return diurnalVariation;
  }

  public void setDiurnalVariation(final AbstractDiurnalVariation diurnalVariation) {
    this.diurnalVariation = diurnalVariation;
  }

}
