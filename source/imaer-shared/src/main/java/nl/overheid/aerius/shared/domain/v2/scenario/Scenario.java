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

import nl.overheid.aerius.shared.domain.Theme;
import nl.overheid.aerius.shared.domain.calculation.CalculationSetOptions;
import nl.overheid.aerius.shared.domain.v2.geojson.FeatureCollection;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;

/**
 * Object to represent a full scenario that can be calculated.
 * A scenario can consist of multiple situations.
 */
public class Scenario implements Serializable {

  private static final long serialVersionUID = 2L;

  private Theme theme;
  private CalculationSetOptions options = new CalculationSetOptions();
  private ScenarioMetaData metaData = new ScenarioMetaData();
  private List<ScenarioSituation> situations = new ArrayList<>();
  private final FeatureCollection<CalculationPointFeature> customPoints = new FeatureCollection<>();

  public Scenario(final Theme theme) {
    this.theme = theme;
  }

  public Scenario() {
    // protected scenario for serialization.
  }

  public Theme getTheme() {
    return theme;
  }

  public void setTheme(final Theme theme) {
    this.theme = theme;
  }

  public CalculationSetOptions getOptions() {
    return options;
  }

  public void setOptions(final CalculationSetOptions calculationSetOptions) {
    this.options = calculationSetOptions;
  }

  public ScenarioMetaData getMetaData() {
    return metaData;
  }

  public void setMetaData(final ScenarioMetaData metaData) {
    this.metaData = metaData;
  }

  public List<ScenarioSituation> getSituations() {
    return situations;
  }

  public void setSituations(final List<ScenarioSituation> situations) {
    this.situations = situations;
  }

  public FeatureCollection<CalculationPointFeature> getCustomPoints() {
    return customPoints;
  }

  @JsonIgnore
  public List<CalculationPointFeature> getCustomPointsList() {
    return customPoints.getFeatures();
  }

  @JsonIgnore
  public String getReference() {
    return situations != null && !situations.isEmpty() ? situations.get(0).getReference() : null;
  }

}
