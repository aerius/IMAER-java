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
package nl.overheid.aerius.gml;

import java.time.LocalDate;
import java.util.stream.Collectors;

import nl.overheid.aerius.gml.base.FeatureCollection;
import nl.overheid.aerius.gml.base.IsArchiveMetadata;
import nl.overheid.aerius.gml.base.IsArchiveProject;
import nl.overheid.aerius.gml.base.MetaData;
import nl.overheid.aerius.shared.domain.v2.archive.ArchiveMetaData;
import nl.overheid.aerius.shared.domain.v2.archive.ArchiveProject;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioMetaData;

/**
 * Reads metadata from the feature collection.
 */
public class GMLMetaDataReader {

  private final FeatureCollection featureCollection;

  public GMLMetaDataReader(final FeatureCollection featureCollection) {
    this.featureCollection = featureCollection;
  }

  /**
   * Returns the {@link ScenarioMetaData} from the GML data.
   * @return ScenarioMetaData object
   */
  public ScenarioMetaData readMetaData() {
    final ScenarioMetaData smd = new ScenarioMetaData();
    if (checkFeatureCollection(featureCollection)) {
      final MetaData metaData = featureCollection.getMetaData();
      smd.setCorporation(metaData.getCorporation());
      smd.setProjectName(metaData.getProjectName());
      if (metaData.getFacilityLocation() != null) {
        smd.setStreetAddress(metaData.getFacilityLocation().getStreetAddress());
        smd.setPostcode(metaData.getFacilityLocation().getPostcode());
        smd.setCity(metaData.getFacilityLocation().getCity());
      }
      smd.setDescription(metaData.getDescription());
    }
    return smd;
  }

  /**
   * Returns the {@link ArchiveMetaData} from the GML data.
   * @return ArchiveMetaData object
   */
  public ArchiveMetaData readArchiveMetaData() {
    if (!hasArchiveMetadata(featureCollection)) {
      return null;
    }
    final ArchiveMetaData archiveMetaData = new ArchiveMetaData();
    final IsArchiveMetadata gmlMetaData = featureCollection.getMetaData().getArchive();
    archiveMetaData.setRetrievalDateTime(gmlMetaData.getRetrievalDateTime());
    archiveMetaData.setArchiveProjects(gmlMetaData.getArchiveProjects().stream()
        .map(GMLMetaDataReader::mapProject)
        .collect(Collectors.toList()));
    return archiveMetaData;
  }

  private static ArchiveProject mapProject(final IsArchiveProject gmlProject) {
    final ArchiveProject project = new ArchiveProject();
    project.setId(gmlProject.getId());
    project.setName(gmlProject.getName());
    project.setAeriusVersion(gmlProject.getAeriusVersion());
    return project;
  }

  /**
   * Reference specified in the GML data.
   * @return reference
   */
  public String readReference() {
    return checkFeatureCollection(featureCollection) ? featureCollection.getMetaData().getReference() : null;
  }

  /**
   * Year specified in the GML data.
   * @return year
   */
  public int readYear() {
    return checkFeatureCollection(featureCollection) && featureCollection.getMetaData().getYear() != null
        ? featureCollection.getMetaData().getYear()
        : LocalDate.now().getYear();
  }

  /**
   * Temporary project period specified in the GML data.
   * @return temporary period for the project (null if permanent project).
   */
  public Integer readTemporaryProjectPeriodYear() {
    return checkFeatureCollection(featureCollection) ? featureCollection.getMetaData().getTemporaryPeriod() : null;
  }

  /**
   * Permit Calculation Radius type specified in the GML data.
   * @return temporary period for the project (null if no calculation radius).
   */
  public String readPermitCalculationRadiusType() {
    return checkFeatureCollection(featureCollection) ? featureCollection.getMetaData().getPermitCalculationRadiusType() : null;
  }

  /**
   * AERIUS version specified in the GML data.
   * @return AERIUS version
   */
  public String readAeriusVersion() {
    return checkFeatureCollection(featureCollection) ? featureCollection.getMetaData().getVersion() : null;
  }

  /**
   * Database version specified in the GML data.
   * @return database version
   */
  public String readDatabaseVersion() {
    return checkFeatureCollection(featureCollection) ? featureCollection.getMetaData().getDatabaseVersion() : null;
  }

  /**
   * GML creator specified in the GML data.
   * @return GML creator
   */
  public String readGmlCreator() {
    return checkFeatureCollection(featureCollection) ? featureCollection.getMetaData().getGmlCreator() : null;
  }

  private boolean checkFeatureCollection(final FeatureCollection featureCollection) {
    return featureCollection != null && featureCollection.getMetaData() != null;
  }

  private boolean hasArchiveMetadata(final FeatureCollection featureCollection) {
    return featureCollection != null && featureCollection.getMetaData() != null && featureCollection.getMetaData().getArchive() != null;
  }
}
