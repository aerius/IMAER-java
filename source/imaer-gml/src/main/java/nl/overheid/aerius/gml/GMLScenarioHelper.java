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

import java.util.List;
import java.util.function.BooleanSupplier;

import nl.overheid.aerius.gml.base.MetaDataInput;
import nl.overheid.aerius.gml.base.OtherSituationMetaData;
import nl.overheid.aerius.shared.domain.scenario.IsScenario;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.scenario.Scenario;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioSituation;

/**
 *
 */
public final class GMLScenarioHelper {

  private GMLScenarioHelper() {
    // Util
  }

  /**
   * Construct the metadata object needed for GML based on input.
   */
  public static MetaDataInput constructMetaData(final Scenario scenario, final ScenarioSituation situation, final BooleanSupplier resultsIncluded,
      final String aeriusVersion, final String databaseVersion) {
    final MetaDataInput metaData = new MetaDataInput();
    metaData.setTheme(scenario.getTheme());
    metaData.setScenarioMetaData(scenario.getMetaData());
    metaData.setYear(situation.getYear());
    metaData.setVersion(aeriusVersion);
    metaData.setDatabaseVersion(databaseVersion);
    metaData.setOptions(scenario.getOptions());
    metaData.setResultsIncluded(resultsIncluded.getAsBoolean());
    scenario.getSituations().stream()
        .filter(x -> situation != x)
        .map(GMLScenarioHelper::otherSituation)
        .forEach(metaData::addOtherSituation);
    return metaData;
  }

  private static OtherSituationMetaData otherSituation(final ScenarioSituation situation) {
    return OtherSituationMetaData.Builder
        .create(situation.getName(), situation.getType())
        .build();
  }

  /**
   * Construct the scenario that can be used to generate GML.
   * Receptor points can be either with or without results. The custom calculation points in the situation will not be used.
   */
  public static IsScenario constructScenario(final ScenarioSituation situation, final List<CalculationPointFeature> receptorPoints) {
    return GMLScenario.Builder.create(situation.getName(), situation.getType())
        .nettingFactor(situation.getNettingFactor())
        .sources(situation.getSources().getFeatures())
        .buildings(situation.getBuildingsList())
        .calculationPoints(receptorPoints)
        .nslDispersionLines(situation.getNslDispersionLinesList())
        .nslCorrections(situation.getNslCorrections())
        .nslMeasures(situation.getNslMeasuresList())
        .definitions(situation.getDefinitions())
        .build();
  }

}
