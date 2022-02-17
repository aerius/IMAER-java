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
package nl.overheid.aerius.gml.v2_0.source.road;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.road.IsGmlSRM2RoadLinearReference;
import nl.overheid.aerius.gml.v2_0.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.v2.source.road.RoadElevation;

/**
 *
 */
@XmlRootElement(name = "SRM2LinearReference", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "SRM2LinearReferenceType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"freeway", "tunnelFactor", "elevation",
    "elevationHeight", "barrierLeft", "barrierRight"})
public class SRM2RoadLinearReference extends AbstractLinearReference implements IsGmlSRM2RoadLinearReference {

  private Boolean freeway;
  private Double tunnelFactor;
  private RoadElevation elevation;
  private Integer elevationHeight;
  private RoadSideBarrierProperty barrierLeft;
  private RoadSideBarrierProperty barrierRight;

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "isFreeway")
  public Boolean isFreeway() {
    return freeway;
  }

  public void setFreeway(final Boolean freeway) {
    this.freeway = freeway;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Double getTunnelFactor() {
    return tunnelFactor;
  }

  public void setTunnelFactor(final Double tunnelFactor) {
    this.tunnelFactor = tunnelFactor;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public RoadElevation getElevation() {
    return elevation;
  }

  public void setElevation(final RoadElevation elevation) {
    this.elevation = elevation;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Integer getElevationHeight() {
    return elevationHeight;
  }

  public void setElevationHeight(final Integer elevationHeight) {
    this.elevationHeight = elevationHeight;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public RoadSideBarrierProperty getBarrierLeft() {
    return barrierLeft;
  }

  public void setBarrierLeft(final RoadSideBarrierProperty barrierLeft) {
    this.barrierLeft = barrierLeft;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public RoadSideBarrierProperty getBarrierRight() {
    return barrierRight;
  }

  public void setBarrierRight(final RoadSideBarrierProperty barrierRight) {
    this.barrierRight = barrierRight;
  }
}
