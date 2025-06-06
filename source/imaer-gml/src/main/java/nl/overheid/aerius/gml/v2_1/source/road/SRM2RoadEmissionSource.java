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
package nl.overheid.aerius.gml.v2_1.source.road;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.road.v40.IsGmlSRM2Road;
import nl.overheid.aerius.gml.v2_1.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.v2.source.road.RoadElevation;
import nl.overheid.aerius.shared.domain.v2.source.road.RoadManager;
import nl.overheid.aerius.shared.domain.v2.source.road.TrafficDirection;

/**
 *
 */
@XmlType(name = "SRM2Road", namespace = CalculatorSchema.NAMESPACE, propOrder = {"freeway", "tunnelFactor", "elevation", "elevationHeight",
    "barrierLeft", "barrierRight",
    "dynamicSegments"})
public class SRM2RoadEmissionSource extends RoadEmissionSource implements IsGmlSRM2Road {

  private boolean freeway;
  private Double tunnelFactor;
  private RoadElevation elevation;
  private Integer elevationHeight;
  private RoadSideBarrierProperty barrierLeft;
  private RoadSideBarrierProperty barrierRight;

  private List<SRM2RoadLinearReferenceProperty> dynamicSegments = new ArrayList<>();

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "isFreeway")
  public boolean isFreeway() {
    return freeway;
  }

  public void setFreeway(final boolean freeway) {
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

  @Override
  @XmlElement(name = "partialChange", namespace = CalculatorSchema.NAMESPACE)
  public List<SRM2RoadLinearReferenceProperty> getDynamicSegments() {
    return dynamicSegments;
  }

  public void setDynamicSegments(final List<SRM2RoadLinearReferenceProperty> dynamicSegments) {
    this.dynamicSegments = dynamicSegments;
  }

  @XmlTransient
  @Override
  public RoadManager getRoadManager() {
    // Not available in this version
    return null;
  }

  @XmlTransient
  @Override
  public TrafficDirection getTrafficDirection() {
    // Not available in this version
    return null;
  }

}
