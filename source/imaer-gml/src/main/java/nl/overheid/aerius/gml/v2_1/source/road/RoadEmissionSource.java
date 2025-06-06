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
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.road.v40.IsGmlRoadEmissionSource;
import nl.overheid.aerius.gml.v2_1.base.CalculatorSchema;
import nl.overheid.aerius.gml.v2_1.base.ReferenceType;
import nl.overheid.aerius.gml.v2_1.source.EmissionSource;

/**
 *
 */
@XmlType(name = "RoadEmissionSourceType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"vehicles", "network"})
public abstract class RoadEmissionSource extends EmissionSource implements IsGmlRoadEmissionSource {

  private List<VehiclesProperty> vehicles = new ArrayList<>();
  private ReferenceType network;

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

}
