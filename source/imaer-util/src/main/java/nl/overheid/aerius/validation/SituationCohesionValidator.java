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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import nl.overheid.aerius.shared.domain.v2.building.Building;
import nl.overheid.aerius.shared.domain.v2.building.BuildingFeature;
import nl.overheid.aerius.shared.domain.v2.characteristics.ADMSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.characteristics.CustomTimeVaryingProfile;
import nl.overheid.aerius.shared.domain.v2.scenario.Definitions;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioSituation;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Validator to check cohesion of a ScenarioSituation, most notably duplicate IDs and correct references.
 */
public final class SituationCohesionValidator {

  private static final class CohesionTracker {
    private final Map<String, EmissionSourceFeature> sources = new HashMap<>();

    private final Map<String, Integer> sourceIds = new HashMap<>();
    private final Map<String, Integer> buildingIds = new HashMap<>();
    private final Map<String, Integer> timeVaryingProfileIds = new HashMap<>();
    private final List<AeriusException> errors;

    private CohesionTracker(final List<AeriusException> errors) {
      this.errors = errors;
    }

    public void add(final ScenarioSituation situation) {
      addSources(situation.getEmissionSourcesList());
      addBuildings(situation.getBuildingsList());
      addDefinitions(situation.getDefinitions());
    }

    public void addError(final AeriusException error) {
      errors.add(error);
    }

    private void addSources(final List<EmissionSourceFeature> sourcesToAdd) {
      sources.putAll(sourcesToAdd.stream()
          .collect(Collectors.toMap(feature -> feature.getProperties().getGmlId(), Function.identity(), (oldValue, value) -> oldValue)));
      sourcesToAdd.stream()
          .map(EmissionSourceFeature::getProperties)
          .map(EmissionSource::getGmlId)
          .forEach(sourceId -> sourceIds.merge(sourceId, 1, (oldValue, value) -> oldValue + value));
    }

    private void addBuildings(final List<BuildingFeature> buildingsToAdd) {
      buildingsToAdd.stream()
          .map(BuildingFeature::getProperties)
          .map(Building::getGmlId)
          .forEach(sourceId -> buildingIds.merge(sourceId, 1, (oldValue, value) -> oldValue + value));
    }

    private void addDefinitions(final Definitions definitions) {
      if (definitions == null || definitions.getCustomTimeVaryingProfiles() == null) {
        return;
      }
      final List<CustomTimeVaryingProfile> profilesInSituation = definitions.getCustomTimeVaryingProfiles();
      profilesInSituation.stream()
          .map(CustomTimeVaryingProfile::getGmlId)
          .forEach(sourceId -> timeVaryingProfileIds.merge(sourceId, 1, (oldValue, value) -> oldValue + value));
    }
  }

  private SituationCohesionValidator() {
    // Util class
  }

  /**
   * Checks cohesion of a situation.
   *
   * @param scenario Scenario to check cohesion
   * @param errors cohesion errors will be added to this list
   * @param warnings cohesion warnings will be added to this list
   */
  public static void checkCohesion(final ScenarioSituation situation, final List<AeriusException> errors, final List<AeriusException> warnings) {
    final CohesionTracker tracker = new CohesionTracker(errors);
    tracker.add(situation);

    checkCohesion(tracker);
  }

  private static void checkCohesion(final CohesionTracker tracker) {
    checkDuplicateIds(tracker);
    checkReferencesForSources(tracker);
  }

  private static void checkDuplicateIds(final CohesionTracker tracker) {
    final List<String> duplicateSourceIds = determineDuplicates(tracker.sourceIds);
    for (final String duplicateSourceId : duplicateSourceIds) {
      tracker.addError(new AeriusException(ImaerExceptionReason.COHESION_DUPLICATE_SOURCE_IDS, duplicateSourceId));
    }
    final List<String> duplicateBuildingIds = determineDuplicates(tracker.buildingIds);
    for (final String duplicateBuildingId : duplicateBuildingIds) {
      tracker.addError(new AeriusException(ImaerExceptionReason.COHESION_DUPLICATE_BUILDING_IDS, duplicateBuildingId));
    }
    final List<String> duplicateTimeVaryingProfileIds = determineDuplicates(tracker.timeVaryingProfileIds);
    for (final String duplicateTimeVaryingProfileId : duplicateTimeVaryingProfileIds) {
      tracker.addError(new AeriusException(ImaerExceptionReason.COHESION_DUPLICATE_TIME_VARYING_PROFILE_IDS, duplicateTimeVaryingProfileId));
    }
  }

  private static <T> List<T> determineDuplicates(final Map<T, Integer> mapToTest) {
    return mapToTest.entrySet().stream()
        .filter(entry -> entry.getValue() > 1)
        .map(Entry::getKey)
        .sorted()
        .toList();
  }

  private static void checkReferencesForSources(final CohesionTracker tracker) {
    for (final EmissionSourceFeature source : tracker.sources.values()) {
      if (source.getProperties() == null || source.getProperties().getCharacteristics() == null) {
        continue;
      }
      checkBuildingReference(source, tracker);
      checkTimeVaryingProfileReferences(source, tracker);
    }
  }

  private static void checkBuildingReference(final EmissionSourceFeature source, final CohesionTracker tracker) {
    final String buildingId = obtainBuildingReference(source);
    if (buildingId != null && !tracker.buildingIds.containsKey(buildingId)) {
      tracker.addError(new AeriusException(ImaerExceptionReason.COHESION_REFERENCE_MISSING_BUILDING, buildingId));
    }
  }

  private static String obtainBuildingReference(final EmissionSourceFeature source) {
    return source.getProperties().getCharacteristics().getBuildingId();
  }

  private static void checkTimeVaryingProfileReferences(final EmissionSourceFeature source, final CohesionTracker tracker) {
    final List<String> timeVaryingProfileIds = obtainTimeVaryingProfileReferences(source);
    for (final String timeVaryingProfileId : timeVaryingProfileIds) {
      if (timeVaryingProfileId != null && !tracker.timeVaryingProfileIds.containsKey(timeVaryingProfileId)) {
        tracker.addError(new AeriusException(ImaerExceptionReason.COHESION_REFERENCE_MISSING_TIME_VARYING_PROFILE, timeVaryingProfileId));
      }
    }
  }

  private static List<String> obtainTimeVaryingProfileReferences(final EmissionSourceFeature source) {
    if (source.getProperties().getCharacteristics() instanceof final ADMSSourceCharacteristics admsCharacteristics) {
      final List<String> tvpReferences = new ArrayList<>();
      if (admsCharacteristics.getCustomHourlyTimeVaryingProfileId() != null) {
        tvpReferences.add(admsCharacteristics.getCustomHourlyTimeVaryingProfileId());
      }
      if (admsCharacteristics.getCustomMonthlyTimeVaryingProfileId() != null) {
        tvpReferences.add(admsCharacteristics.getCustomMonthlyTimeVaryingProfileId());
      }
      return tvpReferences;
    } else {
      // OPS does not use TVP
      return List.of();
    }
  }

}
