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
package nl.overheid.aerius.gml.v3_0.source.road;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.road.IsGmlRoadEmissionSource;
import nl.overheid.aerius.gml.v3_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v3_0.base.ReferenceType;
import nl.overheid.aerius.gml.v3_0.source.EmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.RoadManager;
import nl.overheid.aerius.shared.domain.v2.source.road.TrafficDirection;

/**
 *
 */
@XmlType(name = "RoadEmissionSourceType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"vehicles", "roadManager", "network"})
public abstract class RoadEmissionSource<T extends AbstractLinearReference> extends EmissionSource implements IsGmlRoadEmissionSource {

  private List<VehiclesProperty> vehicles = new ArrayList<>();
  private ReferenceType network;
  private RoadManager roadManager;

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public List<VehiclesProperty> getVehicles() {
    return vehicles;
  }

  public void setVehicles(final List<VehiclesProperty> vehicles) {
    this.vehicles = vehicles;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "inNetwork")
  public ReferenceType getNetwork() {
    return network;
  }

  public void setNetwork(final ReferenceType network) {
    this.network = network;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public RoadManager getRoadManager() {
    return roadManager;
  }

  public void setRoadManager(final RoadManager roadManager) {
    this.roadManager = roadManager;
  }

  @XmlTransient
  @Override
  public TrafficDirection getTrafficDirection() {
    // Not available in this version
    return null;
  }

}
