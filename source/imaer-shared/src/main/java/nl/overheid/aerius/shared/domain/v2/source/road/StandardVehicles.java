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
package nl.overheid.aerius.shared.domain.v2.source.road;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class StandardVehicles extends Vehicles {

  private static final long serialVersionUID = 1L;

  private VehicleType standardVehicleType;
  private double stagnationFraction;
  private Integer maximumSpeed;
  private Boolean strictEnforcement;

  private List<StandardVehicleMeasure> measures = new ArrayList<>();

  public VehicleType getStandardVehicleType() {
    return standardVehicleType;
  }

  public void setStandardVehicleType(final VehicleType standardVehicleType) {
    this.standardVehicleType = standardVehicleType;
  }

  public double getStagnationFraction() {
    return stagnationFraction;
  }

  public void setStagnationFraction(final double stagnationFraction) {
    this.stagnationFraction = stagnationFraction;
  }

  public Integer getMaximumSpeed() {
    return maximumSpeed;
  }

  public void setMaximumSpeed(final Integer maximumSpeed) {
    this.maximumSpeed = maximumSpeed;
  }

  public Boolean getStrictEnforcement() {
    return strictEnforcement;
  }

  public void setStrictEnforcement(final Boolean strictEnforcement) {
    this.strictEnforcement = strictEnforcement;
  }

  @JsonIgnore
  public List<StandardVehicleMeasure> getMeasures() {
    return measures;
  }

  public void setMeasures(final List<StandardVehicleMeasure> measures) {
    this.measures = measures;
  }

}
