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

import java.util.ArrayList;
import java.util.List;

import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;

/**
 * Object to keep track of results related to a scenario situation.
 */
public class ScenarioSituationResults {

  private int calculationId;
  private List<CalculationPointFeature> results = new ArrayList<>();

  public int getCalculationId() {
    return calculationId;
  }

  public void setCalculationId(final int calculationId) {
    this.calculationId = calculationId;
  }

  public List<CalculationPointFeature> getResults() {
    return results;
  }

  public void setResults(final List<CalculationPointFeature> results) {
    this.results = results;
  }

}
