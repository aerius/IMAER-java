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
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import nl.overheid.aerius.shared.domain.scenario.SituationType;
import nl.overheid.aerius.shared.domain.v2.building.BuildingFeature;
import nl.overheid.aerius.shared.domain.v2.geojson.FeatureCollection;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLCorrection;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLDispersionLineFeature;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLMeasureFeature;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;

/**
 * Object containing situation specific information.
 */
public class ScenarioSituation implements Serializable {

  private static final long serialVersionUID = 3L;

  // Initialize with random number, and strip first '0.' to make a dot-less number.
  private String id = String.valueOf(Math.random()).substring(2);
  private int year;
  private String name;
  private SituationType type;
  private Double nettingFactor;
  private Definitions definitions = new Definitions();
  private final FeatureCollection<EmissionSourceFeature> sources = new FeatureCollection<>();
  private final FeatureCollection<NSLDispersionLineFeature> nslDispersionLines = new FeatureCollection<>();
  private final FeatureCollection<NSLMeasureFeature> nslMeasures = new FeatureCollection<>();
  private final List<NSLCorrection> nslCorrections = new ArrayList<>();
  private final FeatureCollection<BuildingFeature> buildings = new FeatureCollection<>();

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public int getYear() {
    return year;
  }

  public void setYear(final int year) {
    this.year = year;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public SituationType getType() {
    return type;
  }

  public void setType(final SituationType type) {
    this.type = type;
  }

  public Double getNettingFactor() {
    return nettingFactor;
  }

  public void setNettingFactor(final Double nettingFactor) {
    this.nettingFactor = nettingFactor;
  }

  public Definitions getDefinitions() {
    return definitions;
  }

  public void setDefinitions(final Definitions definitions) {
    this.definitions = definitions;
  }

  @JsonProperty("emissionSources")
  public FeatureCollection<EmissionSourceFeature> getSources() {
    return sources;
  }

  @JsonIgnore
  public List<EmissionSourceFeature> getEmissionSourcesList() {
    return sources.getFeatures();
  }

  @JsonProperty("nslDispersionLines")
  public FeatureCollection<NSLDispersionLineFeature> getNslDispersionLines() {
    return nslDispersionLines;
  }

  @JsonIgnore
  public List<NSLDispersionLineFeature> getNslDispersionLinesList() {
    return nslDispersionLines.getFeatures();
  }

  public FeatureCollection<NSLMeasureFeature> getNslMeasures() {
    return nslMeasures;
  }

  @JsonIgnore
  public List<NSLMeasureFeature> getNslMeasuresList() {
    return nslMeasures.getFeatures();
  }

  public List<NSLCorrection> getNslCorrections() {
    return nslCorrections;
  }

  public FeatureCollection<BuildingFeature> getBuildings() {
    return buildings;
  }

  @JsonIgnore
  public List<BuildingFeature> getBuildingsList() {
    return buildings.getFeatures();
  }

}
