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
package nl.overheid.aerius.shared.domain.calculation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import jsinterop.annotations.JsProperty;

import nl.overheid.aerius.shared.domain.scenario.SituationType;

/**
 * type of Calculation Job
 */
public enum CalculationJobType {

  /**
   * Exactly 1 Project scenario
   * Optional: 1 off site reduction
   * Optional: 1 Reference
   * NO other scenario types
   */
  PROCESS_CONTRIBUTION(new HashSet<>(Arrays.asList(SituationType.PROPOSED)),
      new HashSet<>(Arrays.asList(SituationType.REFERENCE, SituationType.NETTING)),
      new HashSet<>(),
      1, 3),

  /**
   * At least one temporary scenario (checkboxes)
   * Optional: 1 off site reduction
   * Optional: 1 Reference
   * NO other scenario types
   */
  MAX_TEMPORARY_EFFECT(new HashSet<>(Arrays.asList(SituationType.TEMPORARY)),
      new HashSet<>(Arrays.asList(SituationType.REFERENCE, SituationType.NETTING)),
      new HashSet<>(Arrays.asList(SituationType.TEMPORARY)),
      1, 6),

  /**
   * Exactly 1 Project scenario
   * Optional: 1 off site reduction
   * Optional: 1 Reference
   * Optional: one or more ‘In combination_project’ scenarios
   * Optional: one or more ‘in combination reference’ scenarios
   */
  IN_COMBINATION_PROCESS_CONTRIBUTION(new HashSet<>(Arrays.asList(SituationType.PROPOSED)),
      new HashSet<>(
          Arrays.asList(SituationType.REFERENCE, SituationType.NETTING, SituationType.COMBINATION_REFERENCE, SituationType.COMBINATION_PROPOSED)),
      new HashSet<>(Arrays.asList(SituationType.COMBINATION_PROPOSED, SituationType.COMBINATION_REFERENCE)),
      1, 6),

  /**
   * Only 1 Reference scenario
   *
   * NO other scenario types
   */
  DEPOSITION_SUM(new HashSet<>(Arrays.asList(SituationType.REFERENCE)),
      new HashSet<>(),
      new HashSet<>(),
      1, 1),

  /**
   * One scenario (all types allowed)
   */
  SINGLE_SCENARIO(new HashSet<>(),
      new HashSet<>(Arrays.asList(SituationType.values())),
      new HashSet<>(),
      1, 1);

  private final @JsProperty Set<SituationType> requiredSituationTypes;
  private final @JsProperty Set<SituationType> optionalSituationTypes;
  private final @JsProperty Set<SituationType> pluralSituationTypes;

  private final int minimumNumberOfSituations;
  private final int maximumNumberOfSituations;

  CalculationJobType(final Set<SituationType> requiredSituationTypes, final Set<SituationType> optionalSituationTypes,
      final Set<SituationType> multipleSituationsAllowed, final int minimumNumberOfSituations, final int maximumNumberOfSituations) {
    this.requiredSituationTypes = requiredSituationTypes;
    this.optionalSituationTypes = optionalSituationTypes;
    this.pluralSituationTypes = multipleSituationsAllowed;
    this.minimumNumberOfSituations = minimumNumberOfSituations;
    this.maximumNumberOfSituations = maximumNumberOfSituations;
  }

  public Set<SituationType> getRequiredSituationTypes() {
    return new HashSet<>(requiredSituationTypes);
  }

  public boolean isRequired(final SituationType type) {
    return requiredSituationTypes.contains(type);
  }

  public Set<SituationType> getOptionalSituationTypes() {
    return new HashSet<>(optionalSituationTypes);
  }

  public boolean isOptional(final SituationType type) {
    return optionalSituationTypes.contains(type);
  }

  public Set<SituationType> getPluralSituationTypes() {
    return new HashSet<>(pluralSituationTypes);
  }

  public boolean isPlural(final SituationType type) {
    return pluralSituationTypes.contains(type);
  }

  public boolean isIllegal(final SituationType type) {
    return getIllegalSituationTypes().contains(type);
  }

  public int getMinimumNumberOfSituations() {
    return minimumNumberOfSituations;
  }

  public int getMaximumNumberOfSituations() {
    return maximumNumberOfSituations;
  }

  public Set<SituationType> getIllegalSituationTypes() {
    return Arrays.stream(SituationType.values())
        .filter(st -> !requiredSituationTypes.contains(st) && !optionalSituationTypes.contains(st))
        .collect(Collectors.toSet());
  }

  public static CalculationJobType safeValueOf(final String value) {
    try {
      return value == null ? null : valueOf(value.toUpperCase(Locale.ROOT));
    } catch (final IllegalArgumentException e) {
      return null;
    }
  }

  /**
   * Returns the name in lowercase.
   *
   * @return name in lowercase
   */
  @Override
  public String toString() {
    return name().toLowerCase(Locale.ROOT);
  }
}
