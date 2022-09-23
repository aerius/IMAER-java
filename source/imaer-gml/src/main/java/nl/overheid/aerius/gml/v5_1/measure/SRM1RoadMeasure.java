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
package nl.overheid.aerius.gml.v5_1.measure;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.measure.IsGmlSRM1RoadMeasure;
import nl.overheid.aerius.gml.v5_1.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "SRM1RoadMeasureType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"vehicleType", "roadType", "reductions"})
public class SRM1RoadMeasure implements IsGmlSRM1RoadMeasure {

  private String vehicleType;
  private String roadType;
  private List<EmissionReductionProperty> reductions = new ArrayList<>();

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getVehicleType() {
    return vehicleType;
  }

  public void setVehicleType(final String vehicleType) {
    this.vehicleType = vehicleType;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getRoadType() {
    return roadType;
  }

  public void setRoadType(final String roadType) {
    this.roadType = roadType;
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
