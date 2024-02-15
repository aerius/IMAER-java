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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import org.junit.jupiter.api.Test;

import nl.overheid.aerius.shared.domain.v2.characteristics.CustomTimeVaryingProfile;
import nl.overheid.aerius.shared.domain.v2.characteristics.CustomTimeVaryingProfileType;
import nl.overheid.aerius.shared.domain.v2.scenario.Definitions;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
class DefinitionsValidatorTest {

  private static final String TIME_VARYING_PROFILE_ID = "OurTVPId";
  private static final String TIME_VARYING_PROFILE_LABEL = "Some sort of time thing";

  @Test
  void testEmptyDefinitions() {
    final Definitions definitions = new Definitions();

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();

    DefinitionsValidator.validateDefinitions(definitions, errors, warnings);

    assertEquals(List.of(), errors, "No errors");
    assertEquals(List.of(), warnings, "No warnings");
  }

  @Test
  void testValidTimeVaryingProfile() {
    final Definitions definitions = new Definitions();
    final List<Double> values = DoubleStream.generate(() -> 1.0)
        .limit(24)
        .mapToObj(Double::valueOf)
        .collect(Collectors.toList());
    final CustomTimeVaryingProfile timeVaryingProfile = createTimeVaryingProfile(CustomTimeVaryingProfileType.DAY, values);
    definitions.getCustomTimeVaryingProfiles().add(timeVaryingProfile);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();

    DefinitionsValidator.validateDefinitions(definitions, errors, warnings);

    assertEquals(List.of(), errors, "No errors");
    assertEquals(List.of(), warnings, "No warnings");
  }

  @Test
  void testTimeVaryingProfileMissingType() {
    final Definitions definitions = new Definitions();
    final List<Double> values = DoubleStream.generate(() -> 1.0)
        .limit(24)
        .mapToObj(Double::valueOf)
        .collect(Collectors.toList());
    final CustomTimeVaryingProfile timeVaryingProfile = createTimeVaryingProfile(null, values);
    definitions.getCustomTimeVaryingProfiles().add(timeVaryingProfile);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();

    DefinitionsValidator.validateDefinitions(definitions, errors, warnings);

    assertEquals(1, errors.size(), "Number of errors");
    assertEquals(ImaerExceptionReason.CUSTOM_TIME_VARYING_PROFILE_TYPE_UNKNOWN, errors.get(0).getReason(), "Error reason");
    assertArrayEquals(new Object[] {"null"}, errors.get(0).getArgs(), "Arguments");
    assertEquals(List.of(), warnings, "No warnings");
  }

  @Test
  void testTimeVaryingProfileInvalidNumberOfValues() {
    final Definitions definitions = new Definitions();
    final List<Double> values = DoubleStream.generate(() -> 1.0)
        .limit(23)
        .mapToObj(Double::valueOf)
        .collect(Collectors.toList());
    final CustomTimeVaryingProfile timeVaryingProfile = createTimeVaryingProfile(CustomTimeVaryingProfileType.DAY, values);
    definitions.getCustomTimeVaryingProfiles().add(timeVaryingProfile);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();

    DefinitionsValidator.validateDefinitions(definitions, errors, warnings);

    assertEquals(1, errors.size(), "Number of errors");
    assertEquals(ImaerExceptionReason.CUSTOM_TIME_VARYING_PROFILE_INVALID_COUNT, errors.get(0).getReason(), "Error reason");
    assertArrayEquals(new Object[] {"24", "23"}, errors.get(0).getArgs(), "Arguments");
    assertEquals(List.of(), warnings, "No warnings");
  }

  @Test
  void testTimeVaryingProfileInvalidSumValues() {
    final Definitions definitions = new Definitions();
    final List<Double> values = DoubleStream.generate(() -> 1.1)
        .limit(24)
        .mapToObj(Double::valueOf)
        .collect(Collectors.toList());
    final CustomTimeVaryingProfile timeVaryingProfile = createTimeVaryingProfile(CustomTimeVaryingProfileType.DAY, values);
    definitions.getCustomTimeVaryingProfiles().add(timeVaryingProfile);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();

    DefinitionsValidator.validateDefinitions(definitions, errors, warnings);

    assertEquals(1, errors.size(), "Number of errors");
    assertEquals(ImaerExceptionReason.CUSTOM_TIME_VARYING_PROFILE_INVALID_SUM, errors.get(0).getReason(), "Error reason");
    assertArrayEquals(new Object[] {"24", "26.4"}, errors.get(0).getArgs(), "Arguments");
    assertEquals(List.of(), warnings, "No warnings");
  }

  private static CustomTimeVaryingProfile createTimeVaryingProfile(final CustomTimeVaryingProfileType type, final List<Double> values) {
    final CustomTimeVaryingProfile timeVaryingProfile = new CustomTimeVaryingProfile();
    timeVaryingProfile.setGmlId(TIME_VARYING_PROFILE_ID);
    timeVaryingProfile.setLabel(TIME_VARYING_PROFILE_LABEL);
    timeVaryingProfile.setType(type);
    timeVaryingProfile.setValues(values);
    return timeVaryingProfile;
  }
}
