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
package nl.overheid.aerius.gml.v0_5.source;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.building.IsGmlBuildingV31;
import nl.overheid.aerius.gml.base.characteristics.IsGmlHeatContent;
import nl.overheid.aerius.gml.base.characteristics.IsGmlOPSSourceCharacteristicsV31;
import nl.overheid.aerius.gml.base.characteristics.IsGmlSpecifiedHeatContent;
import nl.overheid.aerius.gml.v0_5.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.ops.DiurnalVariation;

/**
 *
 */
@XmlType(name = "EmissionSourceCharacteristicsType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"heatContentValue", "emissionHeight",
    "buildingHeight", "spread", "diurnalVariationValue"})
public class EmissionSourceCharacteristics implements IsGmlOPSSourceCharacteristicsV31 {
  private double heatContentValue;
  private double emissionHeight;
  private Double buildingHeight;
  private Double spread;
  private DiurnalVariationEnum diurnalVariation;

  @XmlElement(name = "heatContent", namespace = CalculatorSchema.NAMESPACE)
  public double getHeatContentValue() {
    return heatContentValue;
  }

  public void setHeatContentValue(final double heatContentValue) {
    this.heatContentValue = heatContentValue;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getEmissionHeight() {
    return emissionHeight;
  }

  public void setEmissionHeight(final double emissionHeight) {
    this.emissionHeight = emissionHeight;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Double getBuildingHeight() {
    return buildingHeight;
  }

  public void setBuildingHeight(final Double buildingHeight) {
    this.buildingHeight = buildingHeight;
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
  public DiurnalVariationEnum getDiurnalVariationValue() {
    return diurnalVariation;
  }

  public void setDiurnalVariationValue(final DiurnalVariationEnum diurnalVariation) {
    this.diurnalVariation = diurnalVariation;
  }

  @XmlTransient
  @Override
  public IsGmlHeatContent getHeatContent() {
    // Don't use HeatContentSpecification: it doesn't contain enough information to end up with a calculated heat content
    return new IsGmlSpecifiedHeatContent() {

      @Override
      public double getValue() {
        return heatContentValue;
      }
    };
  }

  @XmlTransient
  @Override
  public IsGmlBuildingV31 getBuilding() {
    // Not available in this version
    return null;
  }

  @XmlTransient
  @Override
  public String getDiurnalVariation() {
    return diurnalVariation == null ? null : DiurnalVariation.safeValueOf(diurnalVariation.getOption()).name();
  }

}
