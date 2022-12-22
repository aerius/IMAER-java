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

/**
 * Different modes we support for calculations with subreceptors.
 */
public enum SubReceptorsMode {

  /**
   * Never use subreceptors
   */
  DISABLED(false, false, true),
  /**
   * Always use subreceptors
   */
  ENABLED(true, false, true),
  /**
   * Use subreceptors only when calculating receptors
   * Do not use subreceptors when calculating custom points
   */
  ENABLED_RECEPTORS_ONLY(true, true, true),
  /**
   * Always use subreceptors
   * Also include subreceptors outside relevant habitat areas
   */
  ENABLED_OUTSIDE_HABITATS(true, false, false);

  private final boolean enabled;
  private final boolean receptorsOnly;
  private final boolean onlyInsideHabitats;

  SubReceptorsMode(final boolean enabled, final boolean receptorsOnly, final boolean onlyInsideHabitats) {
    this.enabled = enabled;
    this.receptorsOnly = receptorsOnly;
    this.onlyInsideHabitats = onlyInsideHabitats;
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
   * Whether subreceptors should only be placed inside habitats
   * @return true if subreceptors should only be placed inside habitats
   */
  public boolean isOnlyInsideHabitats() {
    return onlyInsideHabitats;
  }
}
