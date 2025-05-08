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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.overheid.aerius.shared.domain.v2.building.Building;
import nl.overheid.aerius.shared.domain.v2.building.BuildingFeature;
import nl.overheid.aerius.shared.domain.v2.characteristics.ADMSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.characteristics.CustomTimeVaryingProfile;
import nl.overheid.aerius.shared.domain.v2.characteristics.OPSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.characteristics.SourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioSituation;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Tests for {@link SituationCohesionValidator}
 */
class SituationCohesionValidatorTest {

  private static final String HOURLY_PROFILE_ID = "hourly-id";
  private static final String MONTHLY_PROFILE_ID = "monthly-id";

  private List<AeriusException> errors;
  private List<AeriusException> warnings;

  @BeforeEach
  void before() {
    errors = new ArrayList<>();
    warnings = new ArrayList<>();
  }

  @Test
  void testCheckCohesionBare() {
    final ScenarioSituation situation = new ScenarioSituation();
    situation.getEmissionSourcesList().add(mockFeature(mockSource("gml-id-1")));
    situation.getEmissionSourcesList().add(mockFeature(mockSource("gml-id-2")));
    situation.setDefinitions(null);

    check(situation);

    validateWarnings();
    validateErrors();
  }

  @Test
  void testCheckCohesionValid() {
    final EmissionSource source1 = mockSource("gml-id-1");
    final SourceCharacteristics characteristics1 = mockCharacteristics("building-id-1", HOURLY_PROFILE_ID, MONTHLY_PROFILE_ID);
    when(source1.getCharacteristics()).thenReturn(characteristics1);

    final EmissionSource source2 = mockSource("gml-id-2");
    final SourceCharacteristics characteristics2 = mockCharacteristics("building-id-2", HOURLY_PROFILE_ID, MONTHLY_PROFILE_ID);
    when(source2.getCharacteristics()).thenReturn(characteristics2);

    final ScenarioSituation situation = new ScenarioSituation();
    situation.getEmissionSourcesList().add(mockFeature(source1));
    situation.getEmissionSourcesList().add(mockFeature(source2));
    situation.getBuildingsList().add(mockBuilding("building-id-1"));
    situation.getBuildingsList().add(mockBuilding("building-id-2"));
    situation.getBuildingsList().add(mockBuilding("unused-building-id"));
    situation.getDefinitions().getCustomTimeVaryingProfiles().add(mockTimeVaryingProfile(HOURLY_PROFILE_ID));
    situation.getDefinitions().getCustomTimeVaryingProfiles().add(mockTimeVaryingProfile(MONTHLY_PROFILE_ID));
    situation.getDefinitions().getCustomTimeVaryingProfiles().add(mockTimeVaryingProfile("unused-profile-id"));

    check(situation);

    validateWarnings();
    validateErrors();
  }

  @Test
  void testCheckCohesionOpsCharacteristics() {
    final EmissionSource source = mockSource("gml-id-1");
    final OPSSourceCharacteristics characteristics = mock(OPSSourceCharacteristics.class);
    when(characteristics.getBuildingId()).thenReturn("building-id-1");
    when(source.getCharacteristics()).thenReturn(characteristics);

    final ScenarioSituation situation = new ScenarioSituation();
    situation.getEmissionSourcesList().add(mockFeature(source));
    situation.getBuildingsList().add(mockBuilding("building-id-1"));

    check(situation);

    validateWarnings();
    validateErrors();
  }

  @Test
  void testCheckCohesionSourceIdsDuplicates() {
    final ScenarioSituation situation = new ScenarioSituation();
    situation.getEmissionSourcesList().add(mockFeature(mockSource("gml-id-1")));
    situation.getEmissionSourcesList().add(mockFeature(mockSource("gml-id-1")));

    check(situation);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_DUPLICATE_SOURCE_IDS);
  }

  @Test
  void testCheckCohesionBuildingIdsDuplicates() {
    final ScenarioSituation situation = new ScenarioSituation();
    situation.getBuildingsList().add(mockBuilding("building-id-1"));
    situation.getBuildingsList().add(mockBuilding("building-id-1"));

    check(situation);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_DUPLICATE_BUILDING_IDS);
  }

  @Test
  void testCheckCohesionBuildingIdsMissing() {
    final EmissionSource source = mockSource("gml-id-1");
    final SourceCharacteristics characteristics1 = mockCharacteristics("building-id-1", null, null);
    when(source.getCharacteristics()).thenReturn(characteristics1);

    final ScenarioSituation situation = new ScenarioSituation();
    situation.getEmissionSourcesList().add(mockFeature(source));

    check(situation);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_REFERENCE_MISSING_BUILDING);
  }

  @Test
  void testCheckCohesionBuildingIdsIncorrect() {
    final EmissionSource source = mockSource("gml-id-1");
    final SourceCharacteristics characteristics1 = mockCharacteristics("building-id-1", null, null);
    when(source.getCharacteristics()).thenReturn(characteristics1);

    final ScenarioSituation situation = new ScenarioSituation();
    situation.getEmissionSourcesList().add(mockFeature(source));
    situation.getBuildingsList().add(mockBuilding("incorrect-building-id"));

    check(situation);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_REFERENCE_MISSING_BUILDING);
  }

  @Test
  void testCheckCohesionProfileIdsDuplicates() {
    final ScenarioSituation situation = new ScenarioSituation();
    situation.getDefinitions().getCustomTimeVaryingProfiles().add(mockTimeVaryingProfile(HOURLY_PROFILE_ID));
    situation.getDefinitions().getCustomTimeVaryingProfiles().add(mockTimeVaryingProfile(HOURLY_PROFILE_ID));

    check(situation);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_DUPLICATE_TIME_VARYING_PROFILE_IDS);
  }

  @Test
  void testCheckCohesionProfileIdsMissingHourly() {
    final EmissionSource source = mockSource("gml-id-1");
    final SourceCharacteristics characteristics1 = mockCharacteristics(null, HOURLY_PROFILE_ID, MONTHLY_PROFILE_ID);
    when(source.getCharacteristics()).thenReturn(characteristics1);

    final ScenarioSituation situation = new ScenarioSituation();
    situation.getEmissionSourcesList().add(mockFeature(source));
    situation.getDefinitions().getCustomTimeVaryingProfiles().add(mockTimeVaryingProfile(MONTHLY_PROFILE_ID));

    check(situation);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_REFERENCE_MISSING_TIME_VARYING_PROFILE);
  }

  @Test
  void testCheckCohesionProfileIdsIncorrectHourly() {
    final EmissionSource source = mockSource("gml-id-1");
    final SourceCharacteristics characteristics1 = mockCharacteristics(null, HOURLY_PROFILE_ID, MONTHLY_PROFILE_ID);
    when(source.getCharacteristics()).thenReturn(characteristics1);

    final ScenarioSituation situation = new ScenarioSituation();
    situation.getEmissionSourcesList().add(mockFeature(source));
    situation.getDefinitions().getCustomTimeVaryingProfiles().add(mockTimeVaryingProfile("incorrect-hourly-id"));
    situation.getDefinitions().getCustomTimeVaryingProfiles().add(mockTimeVaryingProfile(MONTHLY_PROFILE_ID));

    check(situation);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_REFERENCE_MISSING_TIME_VARYING_PROFILE);
  }

  @Test
  void testCheckCohesionProfileIdsMissingMonthly() {
    final EmissionSource source = mockSource("gml-id-1");
    final SourceCharacteristics characteristics1 = mockCharacteristics(null, HOURLY_PROFILE_ID, MONTHLY_PROFILE_ID);
    when(source.getCharacteristics()).thenReturn(characteristics1);

    final ScenarioSituation situation = new ScenarioSituation();
    situation.getEmissionSourcesList().add(mockFeature(source));
    situation.getDefinitions().getCustomTimeVaryingProfiles().add(mockTimeVaryingProfile(HOURLY_PROFILE_ID));

    check(situation);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_REFERENCE_MISSING_TIME_VARYING_PROFILE);
  }

  @Test
  void testCheckCohesionProfileIdsIncorrectMonthly() {
    final EmissionSource source = mockSource("gml-id-1");
    final SourceCharacteristics characteristics1 = mockCharacteristics(null, HOURLY_PROFILE_ID, MONTHLY_PROFILE_ID);
    when(source.getCharacteristics()).thenReturn(characteristics1);

    final ScenarioSituation situation = new ScenarioSituation();
    situation.getEmissionSourcesList().add(mockFeature(source));
    situation.getDefinitions().getCustomTimeVaryingProfiles().add(mockTimeVaryingProfile(HOURLY_PROFILE_ID));
    situation.getDefinitions().getCustomTimeVaryingProfiles().add(mockTimeVaryingProfile("incorrect-monthly-profile"));

    check(situation);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_REFERENCE_MISSING_TIME_VARYING_PROFILE);
  }

  private EmissionSource mockSource(final String gmlId) {
    final EmissionSource source = mock(EmissionSource.class);
    when(source.getGmlId()).thenReturn(gmlId);
    return source;
  }

  private EmissionSourceFeature mockFeature(final EmissionSource source) {
    final EmissionSourceFeature sourceFeature = mock(EmissionSourceFeature.class);
    when(sourceFeature.getProperties()).thenReturn(source);
    return sourceFeature;
  }

  private SourceCharacteristics mockCharacteristics(final String buildingId, final String hourlyProfileId, final String monthlyProfileId) {
    final ADMSSourceCharacteristics characteristics = mock(ADMSSourceCharacteristics.class);
    when(characteristics.getBuildingId()).thenReturn(buildingId);
    when(characteristics.getCustomHourlyTimeVaryingProfileId()).thenReturn(hourlyProfileId);
    when(characteristics.getCustomMonthlyTimeVaryingProfileId()).thenReturn(monthlyProfileId);
    return characteristics;
  }

  private BuildingFeature mockBuilding(final String gmlId) {
    final BuildingFeature buildingFeature = mock(BuildingFeature.class);
    final Building building = mock(Building.class);
    when(building.getGmlId()).thenReturn(gmlId);
    when(buildingFeature.getProperties()).thenReturn(building);
    return buildingFeature;
  }

  private CustomTimeVaryingProfile mockTimeVaryingProfile(final String gmlId) {
    final CustomTimeVaryingProfile profile = mock(CustomTimeVaryingProfile.class);
    when(profile.getGmlId()).thenReturn(gmlId);
    return profile;
  }

  private void validateWarnings(final ImaerExceptionReason... expectedWarningReasons) {
    assertEquals(expectedWarningReasons.length, warnings.size(), "Number of warnings");
    assertArrayEquals(expectedWarningReasons, warnings.stream().map(AeriusException::getReason).toArray(), "Reasons of warnings");
  }

  private void validateErrors(final ImaerExceptionReason... expectedErrorReasons) {
    assertEquals(expectedErrorReasons.length, errors.size(), "Number of errors");
    assertArrayEquals(expectedErrorReasons, errors.stream().map(AeriusException::getReason).toArray(), "Reasons for errors");
  }

  private void check(final ScenarioSituation situation) {
    SituationCohesionValidator.checkCohesion(situation, errors, warnings);
  }

}
