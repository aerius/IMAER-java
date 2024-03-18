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
package nl.overheid.aerius.gml.v6_0.metadata;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.Address;
import nl.overheid.aerius.gml.base.MetaData;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.scenario.SituationType;

/**
 *
 */
@XmlType(name = "AeriusCalculatorMetadataType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"projectMetadataProperty",
    "situationMetadataProperty", "calculationMetadataProperty", "versionMetadataProperty", "gmlCreator", "archiveMetadataProperty"})
public class MetaDataImpl implements MetaData {

  private SituationMetadata situation;
  private CalculationMetadata calculation;
  private ProjectMetadata project;
  private VersionMetadata version;
  private String gmlCreator;
  private ArchiveMetadata archive;

  @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "situation")
  public SituationMetadataProperty getSituationMetadataProperty() {
    return situation == null ? null : new SituationMetadataProperty(situation);
  }

  public void setSituationMetadataProperty(final SituationMetadataProperty situationProperty) {
    this.situation = situationProperty.getProperty();
  }

  @XmlTransient
  public SituationMetadata getSituation() {
    return situation;
  }

  public void setSituation(final SituationMetadata situation) {
    this.situation = situation;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "calculation")
  public CalculationMetadataProperty getCalculationMetadataProperty() {
    return calculation == null ? null : new CalculationMetadataProperty(calculation);
  }

  public void setCalculationMetadataProperty(final CalculationMetadataProperty calculationProperty) {
    this.calculation = calculationProperty.getProperty();
  }

  @XmlTransient
  @Override
  public CalculationMetadata getCalculation() {
    return calculation;
  }

  public void setCalculation(final CalculationMetadata calculation) {
    this.calculation = calculation;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "project")
  public ProjectMetadataProperty getProjectMetadataProperty() {
    return project == null ? null : new ProjectMetadataProperty(project);
  }

  public void setProjectMetadataProperty(final ProjectMetadataProperty projectProperty) {
    this.project = projectProperty.getProperty();
  }

  @XmlTransient
  public ProjectMetadata getProject() {
    return project;
  }

  public void setProject(final ProjectMetadata project) {
    this.project = project;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "version")
  public VersionMetadataProperty getVersionMetadataProperty() {
    return version == null ? null : new VersionMetadataProperty(version);
  }

  public void setVersionMetadataProperty(final VersionMetadataProperty versionProperty) {
    this.version = versionProperty.getProperty();
  }

  @XmlTransient
  public void setVersion(final VersionMetadata version) {
    this.version = version;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  @Override
  public String getGmlCreator() {
    return gmlCreator;
  }

  public void setGmlCreator(final String gmlCreator) {
    this.gmlCreator = gmlCreator;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "archive")
  public ArchiveMetadataProperty getArchiveMetadataProperty() {
    return archive == null ? null : new ArchiveMetadataProperty(archive);
  }

  public void setArchiveMetadataProperty(final ArchiveMetadataProperty archiveProperty) {
    this.archive = archiveProperty.getProperty();
  }

  @XmlTransient
  @Override
  public ArchiveMetadata getArchive() {
    return archive;
  }

  public void setArchive(final ArchiveMetadata archive) {
    this.archive = archive;
  }

  @Override
  public Integer getYear() {
    return project == null ? null : project.getYear();
  }

  @Override
  public String getVersion() {
    return version == null ? null : version.getAeriusVersion();
  }

  @Override
  public String getDatabaseVersion() {
    return version == null ? null : version.getDatabaseVersion();
  }

  @Override
  public String getSituationName() {
    return situation == null ? null : situation.getName();
  }

  @Override
  public SituationType getSituationType() {
    return situation == null || situation.getSituationType() == null ? null : situation.getSituationType().toSituationType();
  }

  @Override
  public Double getNettingFactor() {
    return situation == null ? null : situation.getNettingFactor();
  }

  @XmlTransient
  @Override
  public String getReference() {
    return situation == null ? null : situation.getReference();
  }

  @Override
  public void setReference(final String reference) {
    if (situation != null) {
      situation.setReference(reference);
    }
  }

  @Override
  public String getCorporation() {
    return project == null ? null : project.getCorporation();
  }

  @Override
  public String getProjectName() {
    return project == null ? null : project.getName();
  }

  @Override
  public Address getFacilityLocation() {
    return project == null ? null : project.getFacilityLocation();
  }

  @Override
  public String getDescription() {
    return project == null ? null : project.getDescription();
  }

}
