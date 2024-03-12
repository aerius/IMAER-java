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

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import nl.overheid.aerius.gml.base.IsArchiveMetadata;
import nl.overheid.aerius.gml.base.IsArchiveProject;
import nl.overheid.aerius.gml.base.OffsetDateTimeAdapter;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "ArchiveMetadataType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"retrievalDateTime", "archiveProjectProperties"})
public class ArchiveMetadata implements IsArchiveMetadata {

  private OffsetDateTime retrievalDateTime;
  private List<ArchiveProjectProperty> archiveProjects = new ArrayList<>();

  @Override
  @XmlJavaTypeAdapter(OffsetDateTimeAdapter.class)
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public OffsetDateTime getRetrievalDateTime() {
    return retrievalDateTime;
  }

  public void setRetrievalDateTime(final OffsetDateTime retrievalDateTime) {
    this.retrievalDateTime = retrievalDateTime;
  }

  @XmlElement(name = "project", namespace = CalculatorSchema.NAMESPACE)
  public List<ArchiveProjectProperty> getArchiveProjectProperties() {
    return archiveProjects;
  }

  public void setArchiveProjectProperties(final List<ArchiveProjectProperty> archiveProjects) {
    this.archiveProjects = archiveProjects;
  }

  @Override
  @XmlTransient
  public List<IsArchiveProject> getArchiveProjects() {
    return archiveProjects == null ? new ArrayList<>()
        : archiveProjects.stream()
            .map(x -> x.getProperty())
            .collect(Collectors.toList());
  }

  public void setArchiveProjects(final List<ArchiveProject> archiveProjects) {
    this.archiveProjects = archiveProjects == null ? new ArrayList<>()
        : archiveProjects.stream()
            .map(x -> new ArchiveProjectProperty(x))
            .collect(Collectors.toList());
  }

}
