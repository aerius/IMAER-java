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

/**
 * Enum indicating the type of a situation.
 */
public enum SituationType {
  /**
   * Represents the (initial) unknown situation type
   */
  UNKNOWN,
  /**
   * Represents a situation in it's current state (referentie).
   */
  REFERENCE,
  /**
   * Represents a situation in a temporary state (tijdelijk),
   * where temporary is meant as a situation between the current and the proposed state.
   * For instance, a construction phase.
   */
  TEMPORARY,
  /**
   * Represents a situation in it's future state (beoogd).
   */
  PROPOSED,
  /**
   * Represents a situation that can be used for off site reduction purposes (salderingssituatie).
   * Acts like an addition to the reference situation when it comes to permit calculations.
   */
  OFF_SITE_REDUCTION,
  /**
   * Represents a situation that is to be calculated in cumulation with the reference situation.
   */
  COMBINATION_REFERENCE,
  /**
   * Represents a situation that is to be calculated in cumulation with the proposed situation.
   */
  COMBINATION_PROPOSED;

  /**
   * Returns the {@link SituationType} from the given string or {@link #UNKNOWN} if null or invalid input.
   * @param type type to parse
   * @return situation type object
   */
  public static SituationType safeValueOf(final String type) {
    try {
      return type == null ? UNKNOWN : valueOf(type);
    } catch (final IllegalArgumentException e) {
      return UNKNOWN;
    }
  }
}
