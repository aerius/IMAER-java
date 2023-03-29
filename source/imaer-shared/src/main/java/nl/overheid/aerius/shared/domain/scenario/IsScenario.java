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
package nl.overheid.aerius.shared.domain.scenario;

import java.util.List;
import java.util.Optional;

import nl.overheid.aerius.shared.domain.v2.building.BuildingFeature;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKCorrection;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKDispersionLineFeature;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKMeasureFeature;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.scenario.Definitions;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioMetaData;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;

public interface IsScenario {

  String getName();

  SituationType getSituationType();

  Double getNettingFactor();

  Definitions getDefinitions();

  List<EmissionSourceFeature> getSources();

  List<BuildingFeature> getBuildings();

  List<CalculationPointFeature> getCalculationPoints();

  List<CIMLKDispersionLineFeature> getCimlkDispersionLines();

  List<CIMLKMeasureFeature> getCimlkMeasures();

  List<CIMLKCorrection> getCimlkCorrections();

  Optional<ScenarioMetaData> getProjectMetaData();

  Optional<SituationMetaData> getSituationMetaData();

}
