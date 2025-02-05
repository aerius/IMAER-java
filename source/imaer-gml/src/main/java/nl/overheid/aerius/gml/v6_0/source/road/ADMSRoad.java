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
package nl.overheid.aerius.gml.v6_0.source.road;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.road.IsGmlADMSRoad;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v6_0.source.characteristics.AbstractTimeVaryingProfile;
import nl.overheid.aerius.gml.v6_0.source.characteristics.TimeVaryingProfileProperty;

/**
 *
 */
@XmlType(name = "ADMSRoad", namespace = CalculatorSchema.NAMESPACE, propOrder = {"width", "elevation", "gradient", "coverage",
    "barrierLeft", "barrierRight", "hourlyVariationProperty", "monthlyVariationProperty"})
public class ADMSRoad extends RoadEmissionSource implements IsGmlADMSRoad {

  private double width;
  private double elevation;
  private double gradient;
  private double coverage;
  private ADMSRoadSideBarrierProperty barrierLeft;
  private ADMSRoadSideBarrierProperty barrierRight;
  private AbstractTimeVaryingProfile hourlyVariation;
  private AbstractTimeVaryingProfile monthlyVariation;

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getWidth() {
    return width;
  }

  public void setWidth(final double width) {
    this.width = width;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getElevation() {
    return elevation;
  }

  public void setElevation(final double elevation) {
    this.elevation = elevation;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getGradient() {
    return gradient;
  }

  public void setGradient(final double gradient) {
    this.gradient = gradient;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getCoverage() {
    return coverage;
  }

  public void setCoverage(final double coverage) {
    this.coverage = coverage;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public ADMSRoadSideBarrierProperty getBarrierLeft() {
    return barrierLeft;
  }

  public void setBarrierLeft(final ADMSRoadSideBarrierProperty barrierLeft) {
    this.barrierLeft = barrierLeft;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public ADMSRoadSideBarrierProperty getBarrierRight() {
    return barrierRight;
  }

  public void setBarrierRight(final ADMSRoadSideBarrierProperty barrierRight) {
    this.barrierRight = barrierRight;
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
