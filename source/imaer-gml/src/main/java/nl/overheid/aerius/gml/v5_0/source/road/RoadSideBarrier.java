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

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.road.IsGmlRoadSideBarrier;
import nl.overheid.aerius.gml.v5_0.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.v2.source.road.SRM2RoadSideBarrierType;

/**
 *
 */
@XmlRootElement(name = "RoadSideBarrier", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "RoadSideBarrierType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"barrierType", "height", "distance"})
public class RoadSideBarrier implements IsGmlRoadSideBarrier {

  private SRM2RoadSideBarrierType barrierType;
  private double height;
  private double distance;

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public SRM2RoadSideBarrierType getBarrierType() {
    return barrierType;
  }

  public void setBarrierType(final SRM2RoadSideBarrierType barrierType) {
    this.barrierType = barrierType;
  }

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
  public double getDistance() {
    return distance;
  }

  public void setDistance(final double distance) {
    this.distance = distance;
  }

}
