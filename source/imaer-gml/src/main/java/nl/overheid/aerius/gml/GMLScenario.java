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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import nl.overheid.aerius.shared.domain.scenario.IsScenario;
import nl.overheid.aerius.shared.domain.scenario.SituationMetaData;
import nl.overheid.aerius.shared.domain.scenario.SituationType;
import nl.overheid.aerius.shared.domain.v2.building.BuildingFeature;
import nl.overheid.aerius.shared.domain.v2.importer.ImportParcel;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLCorrection;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLDispersionLineFeature;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLMeasureFeature;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.scenario.Definitions;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioMetaData;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioSituation;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;

/**
 *
 */
public class GMLScenario implements IsScenario {

  public static final class Builder {

    private final String name;
    private final SituationType situationType;
    private Double nettingFactor;
    private Definitions definitions;
    private List<EmissionSourceFeature> sources;
    private List<BuildingFeature> buildings;
    private List<CalculationPointFeature> calculationPoints;
    private List<NSLDispersionLineFeature> nslDispersionLines;
    private List<NSLMeasureFeature> nslMeasures;
    private List<NSLCorrection> nslCorrections;
    private ScenarioMetaData projectMetaData;
    private SituationMetaData situationMetaData;

    private Builder(final String name, final SituationType situationType) {
      this.name = name;
      this.situationType = situationType;
    }

    public static Builder create(final String name, final SituationType situationType) {
      return new Builder(name, situationType);
    }

    public static Builder create(final ImportParcel importParcel, final ScenarioSituation situation) {
      return new Builder(situation.getName(), situation.getType())
          .nettingFactor(situation.getNettingFactor())
          .definitions(situation.getDefinitions())
          .sources(situation.getEmissionSourcesList())
          .buildings(situation.getBuildingsList())
          .calculationPoints(importParcel.getCalculationPointsList())
          .nslDispersionLines(situation.getNslDispersionLinesList())
          .nslMeasures(situation.getNslMeasuresList())
          .nslCorrections(situation.getNslCorrections());
    }

    public GMLScenario build() {
      return new GMLScenario(this);
    }

    public Builder nettingFactor(final Double nettingFactor) {
      this.nettingFactor = nettingFactor;
      return this;
    }

    public Builder definitions(final Definitions definitions) {
      this.definitions = definitions;
      return this;
    }

    public Builder sources(final List<EmissionSourceFeature> sources) {
      this.sources = sources;
      return this;
    }

    public Builder buildings(final List<BuildingFeature> buildings) {
      this.buildings = buildings;
      return this;
    }

    public Builder calculationPoints(final List<CalculationPointFeature> calculationPoints) {
      this.calculationPoints = calculationPoints;
      return this;
    }

    public Builder nslDispersionLines(final List<NSLDispersionLineFeature> nslDispersionLines) {
      this.nslDispersionLines = nslDispersionLines;
      return this;
    }

    public Builder nslMeasures(final List<NSLMeasureFeature> nslMeasures) {
      this.nslMeasures = nslMeasures;
      return this;
    }

    public Builder nslCorrections(final List<NSLCorrection> nslCorrections) {
      this.nslCorrections = nslCorrections;
      return this;
    }

    public Builder projectMetaData(final ScenarioMetaData projectMetaData) {
      this.projectMetaData = projectMetaData;
      return this;
    }

    public Builder situationMetaData(final SituationMetaData situationMetaData) {
      this.situationMetaData = situationMetaData;
      return this;
    }

  }

  private final String name;
  private final SituationType situationType;
  private final Double nettingFactor;
  private final Definitions definitions;
  private final List<EmissionSourceFeature> sources;
  private final List<BuildingFeature> buildings;
  private final List<CalculationPointFeature> calculationPoints;
  private final List<NSLDispersionLineFeature> nslDispersionLines;
  private final List<NSLMeasureFeature> nslMeasures;
  private final List<NSLCorrection> nslCorrections;
  private final Optional<ScenarioMetaData> projectMetaData;
  private final Optional<SituationMetaData> situationMetaData;

  GMLScenario(final Builder builder) {
    this.name = builder.name;
    this.situationType = builder.situationType;
    this.nettingFactor = builder.situationType == SituationType.NETTING ? builder.nettingFactor : null;
    this.definitions = builder.definitions;
    this.sources = builder.sources == null ? Collections.emptyList() : builder.sources;
    this.buildings = builder.buildings == null ? Collections.emptyList() : builder.buildings;
    this.calculationPoints = builder.calculationPoints == null ? Collections.emptyList() : builder.calculationPoints;
    this.nslDispersionLines = builder.nslDispersionLines == null ? Collections.emptyList() : builder.nslDispersionLines;
    this.nslMeasures = builder.nslMeasures == null ? Collections.emptyList() : builder.nslMeasures;
    this.nslCorrections = builder.nslCorrections == null ? Collections.emptyList() : builder.nslCorrections;
    this.projectMetaData = Optional.ofNullable(builder.projectMetaData);
    this.situationMetaData = Optional.ofNullable(builder.situationMetaData);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public SituationType getSituationType() {
    return situationType;
  }

  @Override
  public Double getNettingFactor() {
    return nettingFactor;
  }

  @Override
  public Definitions getDefinitions() {
    return definitions;
  }

  @Override
  public List<EmissionSourceFeature> getSources() {
    return sources;
  }

  @Override
  public List<BuildingFeature> getBuildings() {
    return buildings;
  }

  @Override
  public List<CalculationPointFeature> getCalculationPoints() {
    return calculationPoints;
  }

  @Override
  public List<NSLDispersionLineFeature> getNslDispersionLines() {
    return nslDispersionLines;
  }

  @Override
  public List<NSLMeasureFeature> getNslMeasures() {
    return nslMeasures;
  }

  @Override
  public List<NSLCorrection> getNslCorrections() {
    return nslCorrections;
  }

  @Override
  public Optional<ScenarioMetaData> getProjectMetaData() {
    return projectMetaData;
  }

  @Override
  public Optional<SituationMetaData> getSituationMetaData() {
    return situationMetaData;
  }

}
