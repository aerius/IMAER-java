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
package nl.overheid.aerius.gml.base;

import java.util.ArrayList;
import java.util.List;

import nl.overheid.aerius.shared.domain.Theme;
import nl.overheid.aerius.shared.domain.calculation.CalculationSetOptions;
import nl.overheid.aerius.shared.domain.scenario.SituationType;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioMetaData;

/**
 *
 */
public class MetaDataInput {

  private ScenarioMetaData scenarioMetaData;
  private Theme theme;
  private int year;
  private String name;
  private SituationType situationType;
  private Double nettingFactor;
  private String version;
  private String databaseVersion;
  private CalculationSetOptions options = new CalculationSetOptions();
  private boolean resultsIncluded;
  private final List<OtherSituationMetaData> otherSituations = new ArrayList<>();

  public ScenarioMetaData getScenarioMetaData() {
    return scenarioMetaData;
  }

  public void setScenarioMetaData(final ScenarioMetaData scenarioMetaData) {
    this.scenarioMetaData = scenarioMetaData;
  }

  public Theme getTheme() {
    return theme;
  }

  public void setTheme(final Theme theme) {
    this.theme = theme;
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

  public SituationType getSituationType() {
    return situationType;
  }

  public void setSituationType(final SituationType situationType) {
    this.situationType = situationType;
  }

  public Double getNettingFactor() {
    return nettingFactor;
  }

  public void setNettingFactor(final Double nettingFactor) {
    this.nettingFactor = nettingFactor;
  }

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

  public CalculationSetOptions getOptions() {
    return options;
  }

  public void setOptions(final CalculationSetOptions options) {
    this.options = options;
  }

  public boolean isResultsIncluded() {
    return resultsIncluded;
  }

  public void setResultsIncluded(final boolean resultsIncluded) {
    this.resultsIncluded = resultsIncluded;
  }

  public List<OtherSituationMetaData> getOtherSituations() {
    return otherSituations;
  }

  public void addOtherSituation(final OtherSituationMetaData otherSituation) {
    this.otherSituations.add(otherSituation);
  }
}
