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
package nl.overheid.aerius.gml.v5_0.source.road;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.road.IsGmlADMSRoad;
import nl.overheid.aerius.gml.v5_0.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "ADMSRoad", namespace = CalculatorSchema.NAMESPACE, propOrder = {"width", "elevation", "gradient", "coverage",
    "barrierLeft", "barrierRight"})
public class ADMSRoad extends RoadEmissionSource implements IsGmlADMSRoad {

  private double width;
  private double elevation;
  private double gradient;
  private double coverage;
  private ADMSRoadSideBarrierProperty barrierLeft;
  private ADMSRoadSideBarrierProperty barrierRight;

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

}
