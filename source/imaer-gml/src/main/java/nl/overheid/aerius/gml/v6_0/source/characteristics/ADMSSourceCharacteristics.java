/*
 * Crown copyright
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
package nl.overheid.aerius.gml.v6_0.source.characteristics;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.characteristics.IsGmlADMSSourceCharacteristics;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.v2.characteristics.adms.BuoyancyType;
import nl.overheid.aerius.shared.domain.v2.characteristics.adms.EffluxType;
import nl.overheid.aerius.shared.domain.v2.characteristics.adms.SourceType;

/**
 *
 */
@XmlRootElement(name = "ADMSSourceCharacteristics", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "ADMSSourceCharacteristicsType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"height", "specificHeatCapacity",
    "sourceType", "diameter", "elevationAngle", "horizontalAngle", "width", "verticalDimension", "buoyancyType", "density",
    "temperature", "effluxType", "verticalVelocity", "volumetricFlowRate", "hourlyVariationProperty", "monthlyVariationProperty"})
public class ADMSSourceCharacteristics extends AbstractSourceCharacteristics implements IsGmlADMSSourceCharacteristics {

  private double height;
  private double specificHeatCapacity;
  private SourceType sourceType;
  private Double diameter;
  private Double elevationAngle;
  private Double horizontalAngle;
  private Double width;
  private Double verticalDimension;
  private BuoyancyType buoyancyType;
  private Double density;
  private Double temperature;
  private EffluxType effluxType;
  private Double verticalVelocity;
  private Double volumetricFlowRate;
  private AbstractTimeVaryingProfile hourlyVariation;
  private AbstractTimeVaryingProfile monthlyVariation;

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getHeight() {
    return height;
  }

  public void setHeight(final double height) {
    this.height = height;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getSpecificHeatCapacity() {
    return specificHeatCapacity;
  }

  public void setSpecificHeatCapacity(final double specificHeatCapacity) {
    this.specificHeatCapacity = specificHeatCapacity;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public SourceType getSourceType() {
    return sourceType;
  }

  public void setSourceType(final SourceType sourceType) {
    this.sourceType = sourceType;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Double getDiameter() {
    return diameter;
  }

  public void setDiameter(final Double diameter) {
    this.diameter = diameter;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Double getElevationAngle() {
    return elevationAngle;
  }

  public void setElevationAngle(final Double elevationAngle) {
    this.elevationAngle = elevationAngle;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Double getHorizontalAngle() {
    return horizontalAngle;
  }

  public void setHorizontalAngle(final Double horizontalAngle) {
    this.horizontalAngle = horizontalAngle;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Double getWidth() {
    return width;
  }

  public void setWidth(final Double width) {
    this.width = width;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Double getVerticalDimension() {
    return verticalDimension;
  }

  public void setVerticalDimension(final Double verticalDimension) {
    this.verticalDimension = verticalDimension;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public BuoyancyType getBuoyancyType() {
    return buoyancyType;
  }

  public void setBuoyancyType(final BuoyancyType buoyancyType) {
    this.buoyancyType = buoyancyType;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Double getDensity() {
    return density;
  }

  public void setDensity(final Double density) {
    this.density = density;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Double getTemperature() {
    return temperature;
  }

  public void setTemperature(final Double temperature) {
    this.temperature = temperature;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public EffluxType getEffluxType() {
    return effluxType;
  }

  public void setEffluxType(final EffluxType effluxType) {
    this.effluxType = effluxType;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Double getVerticalVelocity() {
    return verticalVelocity;
  }

  public void setVerticalVelocity(final Double verticalVelocity) {
    this.verticalVelocity = verticalVelocity;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Double getVolumetricFlowRate() {
    return volumetricFlowRate;
  }

  public void setVolumetricFlowRate(final Double volumetricFlowRate) {
    this.volumetricFlowRate = volumetricFlowRate;
  }

  @XmlElement(name = "hourlyVariation", namespace = CalculatorSchema.NAMESPACE)
  public TimeVaryingProfileProperty getHourlyVariationProperty() {
    return hourlyVariation == null ? null : new TimeVaryingProfileProperty(hourlyVariation);
  }

  public void setHourlyVariationProperty(final TimeVaryingProfileProperty hourlyVariation) {
    this.hourlyVariation = hourlyVariation == null ? null : hourlyVariation.getProperty();
  }

  @Override
  @XmlTransient
  public AbstractTimeVaryingProfile getHourlyTimeVaryingProfile() {
    return hourlyVariation;
  }

  public void setHourlyTimeVaryingProfile(final AbstractTimeVaryingProfile hourlyVariation) {
    this.hourlyVariation = hourlyVariation;
  }

  @XmlElement(name = "monthlyVariation", namespace = CalculatorSchema.NAMESPACE)
  public TimeVaryingProfileProperty getMonthlyVariationProperty() {
    return monthlyVariation == null ? null : new TimeVaryingProfileProperty(monthlyVariation);
  }

  public void setMonthlyVariationProperty(final TimeVaryingProfileProperty monthlyVariation) {
    this.monthlyVariation = monthlyVariation == null ? null : monthlyVariation.getProperty();
  }

  @Override
  @XmlTransient
  public AbstractTimeVaryingProfile getMonthlyTimeVaryingProfile() {
    return monthlyVariation;
  }

  public void setMonthlyTimeVaryingProfile(final AbstractTimeVaryingProfile monthlyVariation) {
    this.monthlyVariation = monthlyVariation;
  }


}
