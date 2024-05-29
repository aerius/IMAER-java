/*
 * Crown copyright
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
package nl.overheid.aerius.shared.domain.v2.point;

import java.util.Locale;

/**
 * Entity types of entity references
 */
public enum EntityType {
  CRITICAL_LEVEL_ENTITY,
  HABITAT_TYPE;

  /**
   * Safely returns a EntityType. It is case independent and returns null in case the input was null or the entity type could not be found.
   *
   * @param value value to convert
   * @return EntityType or null if no valid input
   */
  public static EntityType safeValueOf(final String value) {
    EntityType returnValue = null;
    if (value != null) {
      try {
        returnValue = valueOf(value.toUpperCase(Locale.ROOT));
      } catch (final IllegalArgumentException e) {
        // not a correct value apparently, return null.
      }
    }
    return returnValue;
  }
}
