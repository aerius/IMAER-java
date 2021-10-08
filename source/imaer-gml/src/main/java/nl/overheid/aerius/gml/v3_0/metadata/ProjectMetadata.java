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
package nl.overheid.aerius.gml.v3_0.metadata;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.v3_0.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "ProjectMetadataType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"year", "temporaryPeriod", "permitCalculationRadiusType",
    "name", "corporation", "facilityLocationProperty", "description"})
public class ProjectMetadata {

  private Integer year;
  private Integer temporaryPeriod;
  private String permitCalculationRadiusType;
  private String name;
  private String corporation;
  private AddressImpl facilityLocation;
  private String description;

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Integer getYear() {
    return year;
  }

  public void setYear(final Integer year) {
    this.year = year;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Integer getTemporaryPeriod() {
    return temporaryPeriod;
  }

  public void setTemporaryPeriod(final Integer temporaryPeriod) {
    this.temporaryPeriod = temporaryPeriod;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getPermitCalculationRadiusType() {
    return permitCalculationRadiusType;
  }

  public void setPermitCalculationRadiusType(final String permitCalculationRadiusType) {
    this.permitCalculationRadiusType = permitCalculationRadiusType;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getCorporation() {
    return corporation;
  }

  public void setCorporation(final String corporation) {
    this.corporation = corporation;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "facilityLocation")
  public AddressProperty getFacilityLocationProperty() {
    return facilityLocation == null ? null : new AddressProperty(facilityLocation);
  }

  public void setFacilityLocationProperty(final AddressProperty addressProperty) {
    this.facilityLocation = addressProperty.getProperty();
  }

  @XmlTransient
  public AddressImpl getFacilityLocation() {
    return facilityLocation;
  }

  public void setFacilityLocation(final AddressImpl facilityLocation) {
    this.facilityLocation = facilityLocation;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

}
