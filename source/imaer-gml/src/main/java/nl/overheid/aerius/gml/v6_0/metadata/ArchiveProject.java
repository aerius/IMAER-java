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

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.IsArchiveProject;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v6_0.geo.Point;
import nl.overheid.aerius.gml.v6_0.source.EmissionProperty;

/**
 *
 */
@XmlType(name = "ArchiveProjectType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"id", "name", "aeriusVersion",
    "projectType", "permitReference", "planningReference", "netEmissions", "centroid"})
public class ArchiveProject implements IsArchiveProject {

  private String id;
  private String name;
  private String aeriusVersion;
  private String projectType;
  private String permitReference;
  private String planningReference;
  private Point centroid;
  private List<EmissionProperty> netEmissions = new ArrayList<>();

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getAeriusVersion() {
    return aeriusVersion;
  }

  public void setAeriusVersion(final String aeriusVersion) {
    this.aeriusVersion = aeriusVersion;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  @Override
  public String getProjectType() {
    return projectType;
  }

  public void setProjectType(final String projectType) {
    this.projectType = projectType;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  @Override
  public String getPermitReference() {
    return permitReference;
  }

  public void setPermitReference(final String permitReference) {
    this.permitReference = permitReference;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  @Override
  public String getPlanningReference() {
    return planningReference;
  }

  public void setPlanningReference(final String planningReference) {
    this.planningReference = planningReference;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  @Override
  public Point getCentroid() {
    return centroid;
  }

  public void setCentroid(final Point point) {
    this.centroid = point;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "netEmission")
  @Override
  public List<EmissionProperty> getNetEmissions() {
    return netEmissions;
  }

  public void setNetEmissions(final List<EmissionProperty> netEmissions) {
    this.netEmissions = netEmissions;
  }

}
