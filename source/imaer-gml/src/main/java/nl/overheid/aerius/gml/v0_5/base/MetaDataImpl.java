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
package nl.overheid.aerius.gml.v0_5.base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.Address;
import nl.overheid.aerius.gml.base.MetaData;

/**
 *
 */
@XmlType(name = "AeriusCalculatorMetaDataType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"year", "version", "databaseVersion",
    "situationName", "reference", "projectName", "corporation",
    "temporaryPeriod", "description",})
public class MetaDataImpl implements MetaData {

  private Integer year;
  private String version;
  private String databaseVersion;
  private String situationName;
  private String reference;
  private String corporation;
  private String projectName;
  private Integer temporaryPeriod;
  private String description;

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Integer getYear() {
    return year;
  }

  public void setYear(final Integer year) {
    this.year = year;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getVersion() {
    return version;
  }

  public void setVersion(final String version) {
    this.version = version;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getDatabaseVersion() {
    return databaseVersion;
  }

  public void setDatabaseVersion(final String databaseVersion) {
    this.databaseVersion = databaseVersion;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getSituationName() {
    return situationName;
  }

  public void setSituationName(final String name) {
    this.situationName = name;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getReference() {
    return reference;
  }

  @Override
  public void setReference(final String reference) {
    this.reference = reference;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getCorporation() {
    return corporation;
  }

  public void setCorporation(final String corporation) {
    this.corporation = corporation;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(final String projectName) {
    this.projectName = projectName;
  }

  @Override
  public Address getFacilityLocation() {
    return null;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Integer getTemporaryPeriod() {
    return temporaryPeriod;
  }

  public void setTemporaryPeriod(final Integer temporaryPeriod) {
    this.temporaryPeriod = temporaryPeriod;
  }

  @Override
  public String getPermitCalculationRadiusType() {
    return null;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

}
