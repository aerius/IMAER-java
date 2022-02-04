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
package nl.overheid.aerius.gml.v5_0.measure;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.measure.v40.IsGmlSRM1RoadMeasure;
import nl.overheid.aerius.gml.v5_0.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.v2.source.road.RoadSpeedType;
import nl.overheid.aerius.shared.domain.v2.source.road.VehicleType;

/**
 *
 */
@XmlType(name = "SRM1RoadMeasureType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"vehicleType", "speedProfile", "reductions"})
public class SRM1RoadMeasure implements IsGmlSRM1RoadMeasure {

  private VehicleType vehicleType;
  private RoadSpeedType speedProfile;
  private List<EmissionReductionProperty> reductions = new ArrayList<>();

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public VehicleType getVehicleType() {
    return vehicleType;
  }

  public void setVehicleType(final VehicleType vehicleType) {
    this.vehicleType = vehicleType;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public RoadSpeedType getSpeedProfile() {
    return speedProfile;
  }

  public void setSpeedProfile(final RoadSpeedType speedProfile) {
    this.speedProfile = speedProfile;
  }

  @Override
  @XmlElement(name = "reduction", namespace = CalculatorSchema.NAMESPACE)
  public List<EmissionReductionProperty> getReductions() {
    return reductions;
  }

  public void setReductions(final List<EmissionReductionProperty> reductions) {
    this.reductions = reductions;
  }

}
