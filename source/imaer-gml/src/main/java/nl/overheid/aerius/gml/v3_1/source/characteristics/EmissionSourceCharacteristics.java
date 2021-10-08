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
package nl.overheid.aerius.gml.v3_1.source.characteristics;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.characteristics.IsGmlEmissionSourceCharacteristicsV31;
import nl.overheid.aerius.gml.v3_1.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "EmissionSourceCharacteristicsType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"heatContentProperty", "emissionHeight",
    "spread", "diurnalVariation", "buildingProperty"})
public class EmissionSourceCharacteristics implements IsGmlEmissionSourceCharacteristicsV31 {

  private AbstractHeatContent heatContent;
  private double emissionHeight;
  private Double spread;
  private String diurnalVariation;
  private Building building;

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

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getDiurnalVariation() {
    return diurnalVariation;
  }

  public void setDiurnalVariation(final String diurnalVariation) {
    this.diurnalVariation = diurnalVariation;
  }

  @XmlElement(name = "building", namespace = CalculatorSchema.NAMESPACE)
  public BuildingProperty getBuildingProperty() {
    return building == null ? null : new BuildingProperty(building);
  }

  public void setBuildingProperty(final BuildingProperty building) {
    this.building = building == null ? null : building.getProperty();
  }

  @Override
  @XmlTransient
  public Building getBuilding() {
    return building;
  }

  public void setBuilding(final Building building) {
    this.building = building;
  }

}
