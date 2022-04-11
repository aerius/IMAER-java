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
package nl.overheid.aerius.shared.domain.v2.importer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import nl.overheid.aerius.shared.domain.v2.geojson.FeatureCollection;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioMetaData;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioSituation;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioSituationResults;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Parcel object containing all data of an IMAER import.
 */
public class ImportParcel implements Serializable {

  private static final long serialVersionUID = 1L;

  private String version;
  private String databaseVersion;
  private ScenarioMetaData importedMetaData;
  private final ScenarioSituation situation = new ScenarioSituation();
  private ScenarioSituationResults situationResults;
  private final FeatureCollection<CalculationPointFeature> calculationPoints = new FeatureCollection<>();
  private final List<AeriusException> exceptions = new ArrayList<>();
  private final List<AeriusException> warnings = new ArrayList<>();

  public String getVersion() {
    return version;
  }

  public void setVersion(final String version) {
    this.version = version;
  }

  public String getDatabaseVersion() {
    return databaseVersion;
  }

  public void setDatabaseVersion(final String databaseVersion) {
    this.databaseVersion = databaseVersion;
  }

  public ScenarioMetaData getImportedMetaData() {
    return importedMetaData;
  }

  public void setImportedMetaData(final ScenarioMetaData importedMetaData) {
    this.importedMetaData = importedMetaData;
  }

  public ScenarioSituationResults getSituationResults() {
    return situationResults;
  }

  public void setSituationResults(final ScenarioSituationResults situationResults) {
    this.situationResults = situationResults;
  }

  public ScenarioSituation getSituation() {
    return situation;
  }

  public FeatureCollection<CalculationPointFeature> getCalculationPoints() {
    return calculationPoints;
  }

  @JsonIgnore
  public List<CalculationPointFeature> getCalculationPointsList() {
    return calculationPoints.getFeatures();
  }

  public List<AeriusException> getExceptions() {
    return exceptions;
  }

  public List<AeriusException> getWarnings() {
    return warnings;
  }

}
