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
package nl.overheid.aerius.shared.domain.v2.cimlk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nl.overheid.aerius.shared.domain.v2.source.road.StandardVehicleMeasure;

public class CIMLKMeasure implements Serializable {

  private static final long serialVersionUID = 3L;

  private String gmlId;
  private Integer jurisdictionId;
  private String label;
  private String description;

  private List<StandardVehicleMeasure> vehicleMeasures = new ArrayList<>();

  public String getGmlId() {
    return gmlId;
  }

  public void setGmlId(final String gmlId) {
    this.gmlId = gmlId;
  }

  public Integer getJurisdictionId() {
    return jurisdictionId;
  }

  public void setJurisdictionId(final Integer jurisdictionId) {
    this.jurisdictionId = jurisdictionId;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(final String label) {
    this.label = label;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public List<StandardVehicleMeasure> getVehicleMeasures() {
    return vehicleMeasures;
  }

  public void setVehicleMeasures(final List<StandardVehicleMeasure> vehicleMeasures) {
    this.vehicleMeasures = vehicleMeasures;
  }

}
