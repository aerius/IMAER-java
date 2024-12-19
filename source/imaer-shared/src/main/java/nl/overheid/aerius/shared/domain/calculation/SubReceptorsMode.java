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
import java.util.Set;

/**
 * Different modes we support for calculations with subreceptors.
 */
public enum SubReceptorsMode {

  /**
   * Never use subreceptors
   */
  DISABLED(),
  /**
   * Always use subreceptors, but only inside relevant habitats.
   */
  ENABLED(Option.ENABLED, Option.ONLY_INSIDE_HABITATS),
  /**
   * Always use subreceptors
   * Only include subreceptors when inside nature area
   */
  ENABLED_INSIDE_NATURE_AREAS(Option.ENABLED, Option.ONLY_INSIDE_NATURE_AREAS),
  /**
   * Use subreceptors only when calculating receptors
   * Do not use subreceptors when calculating custom points
   */
  ENABLED_RECEPTORS_ONLY(Option.ENABLED, Option.RECEPTORS_ONLY, Option.ONLY_INSIDE_HABITATS),
  /**
   * Always use subreceptors
   * Also include subreceptors outside relevant habitat areas
   */
  ENABLED_OUTSIDE_HABITATS(Option.ENABLED);

  private enum Option {
    ENABLED,
    ONLY_INSIDE_NATURE_AREAS,
    ONLY_INSIDE_HABITATS,
    RECEPTORS_ONLY,
  }

  private final boolean enabled;
  private final boolean onlyInsideHabitats;
  private final boolean onlyInsideNatureAreas;
  private final boolean receptorsOnly;

  SubReceptorsMode(final Option ...options) {
    final Set<Option> set = new HashSet<>(Arrays.asList(options));

    this.enabled = set.contains(Option.ENABLED);
    this.onlyInsideHabitats = set.contains(Option.ONLY_INSIDE_HABITATS);
    this.onlyInsideNatureAreas = set.contains(Option.ONLY_INSIDE_NATURE_AREAS);
    this.receptorsOnly = set.contains(Option.RECEPTORS_ONLY);
  }

  /**
   * Whether subreceptors should be used
   * @return true if subreceptors are to be used
   */
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * Whether subreceptors should be used
   * @return true if subreceptors should not be used
   */
  public boolean isDisabled() {
    return !isEnabled();
  }

  /**
   * Whether subreceptors should be used on points
   * @return true if subreceptors are to be used on points
   */
  public boolean isEnabledForPoints() {
    return enabled && !receptorsOnly;
  }

  /**
   * Whether subreceptors should only be placed inside nature areas
   * @return true if subreceptors should only be placed inside nature areas
   */
  public boolean isOnlyInsideNatureAreas() {
    return onlyInsideNatureAreas;
  }

  /**
   * Whether subreceptors should only be placed inside habitats
   * @return true if subreceptors should only be placed inside habitats
   */
  public boolean isOnlyInsideHabitats() {
    return onlyInsideHabitats;
  }
}
