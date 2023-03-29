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

public class CIMLKDispersionLine implements Serializable {

  private static final long serialVersionUID = 3L;

  private String roadGmlId;
  private String calculationPointGmlId;
  private Integer jurisdictionId;
  private String label;
  private CIMLKRoadProfile roadProfile;
  private CIMLKTreeProfile treeProfile;
  private String description;

  public String getRoadGmlId() {
    return roadGmlId;
  }

  public void setRoadGmlId(final String roadGmlId) {
    this.roadGmlId = roadGmlId;
  }

  public String getCalculationPointGmlId() {
    return calculationPointGmlId;
  }

  public void setCalculationPointGmlId(final String calculationPointGmlId) {
    this.calculationPointGmlId = calculationPointGmlId;
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

  public CIMLKRoadProfile getRoadProfile() {
    return roadProfile;
  }

  public void setRoadProfile(final CIMLKRoadProfile roadProfile) {
    this.roadProfile = roadProfile;
  }

  public CIMLKTreeProfile getTreeProfile() {
    return treeProfile;
  }

  public void setTreeProfile(final CIMLKTreeProfile treeProfile) {
    this.treeProfile = treeProfile;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "CIMLKDispersionLine [roadGmlId=" + roadGmlId + ", calculationPointGmlId=" + calculationPointGmlId + ", jurisdictionId=" + jurisdictionId
        + ", label=" + label + ", roadProfile=" + roadProfile + ", treeProfile=" + treeProfile + ", description=" + description + "]";
  }
}
