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
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.road.IsGmlADMSRoadSideBarrier;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.v2.source.road.ADMSRoadSideBarrierType;

/**
 *
 */
@XmlRootElement(name = "ADMSRoadSideBarrier", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "ADMSRoadSideBarrierType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"barrierType", "distance",
    "averageHeight", "maximumHeight", "minimumHeight", "porosity"})
public class ADMSRoadSideBarrier implements IsGmlADMSRoadSideBarrier {

  private ADMSRoadSideBarrierType barrierType;
  private double distance;
  private double averageHeight;
  private double maximumHeight;
  private double minimumHeight;
  private double porosity;

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public ADMSRoadSideBarrierType getBarrierType() {
    return barrierType;
  }

  public void setBarrierType(final ADMSRoadSideBarrierType barrierType) {
    this.barrierType = barrierType;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getDistance() {
    return distance;
  }

  public void setDistance(final double distance) {
    this.distance = distance;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getAverageHeight() {
    return averageHeight;
  }

  public void setAverageHeight(final double averageHeight) {
    this.averageHeight = averageHeight;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getMaximumHeight() {
    return maximumHeight;
  }

  public void setMaximumHeight(final double maximumHeight) {
    this.maximumHeight = maximumHeight;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getMinimumHeight() {
    return minimumHeight;
  }

  public void setMinimumHeight(final double minimumHeight) {
    this.minimumHeight = minimumHeight;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getPorosity() {
    return porosity;
  }

  public void setPorosity(final double porosity) {
    this.porosity = porosity;
  }

}
