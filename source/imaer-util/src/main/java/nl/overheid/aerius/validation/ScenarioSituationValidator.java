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
package nl.overheid.aerius.validation;

import java.util.ArrayList;
import java.util.List;

import nl.overheid.aerius.shared.domain.v2.building.BuildingFeature;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLMeasureFeature;
import nl.overheid.aerius.shared.domain.v2.scenario.Definitions;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioSituation;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Validator to help with validating everything in a situation.
 * Methods in this validator usually throw an exception on the first error they encounter.
 */
public final class ScenarioSituationValidator {

  private ScenarioSituationValidator() {
    // Util class
  }

  /**
   * Validate the supplied situation. If a validation fails, an AeriusException will be thrown with the cause.
   * @param situation The situation to validate
   * @param validationHelper The helper to use with validating objects
   * @throws AeriusException In case of a invalid object.
   */
  public static void validateSituation(final ScenarioSituation situation, final ValidationHelper validationHelper) throws AeriusException {
    validateSources(situation.getEmissionSourcesList(), validationHelper);
    validateBuildings(situation.getBuildingsList());
    validateNslMeasures(situation.getNslMeasuresList());
    validateDefinitions(situation.getDefinitions());
  }

  public static void validateSources(final List<EmissionSourceFeature> sources, final ValidationHelper validationHelper) throws AeriusException {
    final List<AeriusException> errors = new ArrayList<>();
    EmissionSourceValidator.validateSources(sources, errors, new ArrayList<>(), validationHelper);
    if (!errors.isEmpty()) {
      throw errors.get(0);
    }
  }

  public static void validateBuildings(final List<BuildingFeature> buildings) throws AeriusException {
    final List<AeriusException> errors = new ArrayList<>();
    BuildingValidator.validateBuildings(buildings, errors, new ArrayList<>());
    if (!errors.isEmpty()) {
      throw errors.get(0);
    }
  }

  public static void validateNslMeasures(final List<NSLMeasureFeature> measures) throws AeriusException {
    final List<AeriusException> errors = new ArrayList<>();
    NSLMeasureValidator.validateMeasures(measures, errors, new ArrayList<>());
    if (!errors.isEmpty()) {
      throw errors.get(0);
    }
  }

  public static void validateDefinitions(final Definitions definitions) throws AeriusException {
    final List<AeriusException> errors = new ArrayList<>();
    DefinitionsValidator.validateDefinitions(definitions, errors, new ArrayList<>());
    if (!errors.isEmpty()) {
      throw errors.get(0);
    }
  }

}
