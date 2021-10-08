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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class StandardVehicles extends Vehicles {

  private static final long serialVersionUID = 1L;

  private Integer maximumSpeed;
  private Boolean strictEnforcement;
  private Map<VehicleType, ValuesPerVehicleType> valuesPerVehicleTypes = new HashMap<>();

  private List<StandardVehicleMeasure> measures = new ArrayList<>();

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

  public Map<VehicleType, ValuesPerVehicleType> getValuesPerVehicleTypes() {
    return valuesPerVehicleTypes;
  }

  public void setValuesPerVehicleTypes(final Map<VehicleType, ValuesPerVehicleType> valuesPerVehicleTypes) {
    this.valuesPerVehicleTypes = valuesPerVehicleTypes;
  }

  @JsonIgnore
  public List<StandardVehicleMeasure> getMeasures() {
    return measures;
  }

  public void setMeasures(final List<StandardVehicleMeasure> measures) {
    this.measures = measures;
  }

}
