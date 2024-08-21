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
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import nl.overheid.aerius.gml.base.FeatureCollection;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsArchiveMetadata;
import nl.overheid.aerius.gml.base.IsArchiveProject;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.MetaData;
import nl.overheid.aerius.gml.base.geo.GML2Geometry;
import nl.overheid.aerius.gml.base.source.IsGmlEmission;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.archive.ArchiveMetaData;
import nl.overheid.aerius.shared.domain.v2.archive.ArchiveProject;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioMetaData;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Reads metadata from the feature collection.
 */
public class GMLMetaDataReader {

  private final FeatureCollection featureCollection;
  private final GML2Geometry gml2geometry;

  public GMLMetaDataReader(final FeatureCollection featureCollection, final GMLConversionData conversionData) {
    this.featureCollection = featureCollection;
    gml2geometry = new GML2Geometry(conversionData.getSrid());
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
  public ArchiveMetaData readArchiveMetaData() throws AeriusException {
    if (!hasArchiveMetadata(featureCollection)) {
      return null;
    }
    final ArchiveMetaData archiveMetaData = new ArchiveMetaData();
    final IsArchiveMetadata gmlMetaData = featureCollection.getMetaData().getArchive();
    archiveMetaData.setRetrievalDateTime(gmlMetaData.getRetrievalDateTime());
    final List<ArchiveProject> archiveProjects = new ArrayList<>();
    for (final IsArchiveProject gmlProject : gmlMetaData.getArchiveProjects()) {
      archiveProjects.add(mapProject(gmlProject));
    }
    archiveMetaData.setArchiveProjects(archiveProjects);
    return archiveMetaData;
  }

  private ArchiveProject mapProject(final IsArchiveProject gmlProject) throws AeriusException {
    final ArchiveProject project = new ArchiveProject();
    project.setId(gmlProject.getId());
    project.setName(gmlProject.getName());
    project.setAeriusVersion(gmlProject.getAeriusVersion());
    project.setType(gmlProject.getProjectType());
    project.setPermitReference(gmlProject.getPermitReference());
    project.setPlanningReference(gmlProject.getPlanningReference());
    if (gmlProject.getNetEmissions() != null) {
      project.setNetEmissions(convertEmissions(gmlProject.getNetEmissions()));
    }
    if (gmlProject.getCentroid() != null) {
      project.setCentroid(gml2geometry.fromXMLPoint(gmlProject.getCentroid()));
    }
    return project;
  }

  private static Map<Substance, Double> convertEmissions(final List<? extends IsGmlProperty<IsGmlEmission>> emissions) {
    final Map<Substance, Double> converted = new EnumMap<>(Substance.class);
    for (final IsGmlProperty<IsGmlEmission> emissionProperty : emissions) {
      final IsGmlEmission emission = emissionProperty.getProperty();
      converted.put(emission.getSubstance(), emission.getValue());
    }
    return converted;
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
