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
package nl.overheid.aerius.shared.domain.v2.scenario;

import java.io.Serializable;

/**
 * Meta data for Scenario.
 */
public class ScenarioMetaData implements Serializable {

  private static final long serialVersionUID = 1L;

  private String reference;
  private String corporation;
  private String projectName;
  private String description;
  private String streetAddress;
  private String postcode;
  private String city;

  public String getCorporation() {
    return corporation;
  }

  public String getProjectName() {
    return projectName;
  }

  public String getStreetAddress() {
    return streetAddress;
  }

  public String getPostcode() {
    return postcode;
  }

  public String getCity() {
    return city;
  }

  public String getDescription() {
    return description;
  }

  public void setStreetAddress(final String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public void setPostcode(final String postcode) {
    this.postcode = postcode;
  }

  public void setCity(final String city) {
    this.city = city;
  }

  public void setCorporation(final String corporation) {
    this.corporation = corporation;
  }

  public void setProjectName(final String projectName) {
    this.projectName = projectName;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "ScenarioMetaData [reference=" + reference + ",corporation=" + corporation + ", projectName=" + projectName
        + ", location=" + streetAddress + ", " + postcode + ", " + city + ", description=" + description + "]";
  }
}
