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
package nl.overheid.aerius.gml.v0_5.source;

/**
 * The time-dependent emission behaviour specified as a daily variation.
 */
public enum DiurnalVariationEnum {
  /**
   * Continuous in time.
   */
  CONTINUOUS(0),
  /**
   * According to the (average) industrial activity over a working day.
   */
  INDUSTRIAL_ACTIVITY(1),
  /**
   * According to the (average) heating activity for space heating.
   */
  SPACE_HEATING(2),
  /**
   * Dependent on traffic intensity.
   */
  TRAFFIC(3),
  /**
   * Emissions from animal housing.
   */
  ANIMAL_HOUSING(4),
  /**
   * Application, fertiliser and other.
   */
  FERTILISER(5);

  private final int option;

  private DiurnalVariationEnum(final int option) {
    this.option = option;
  }

  public int getOption() {
    return option;
  }

  /**
   * @param option The option that OPS uses for diurnal variation.
   * @return The corresponding enum value (or null if not found).
   */
  public static DiurnalVariationEnum fromOption(final Integer option) {
    DiurnalVariationEnum result = null;
    if (option != null) {
      for (final DiurnalVariationEnum enumValue : values()) {
        if (enumValue.option == option) {
          result = enumValue;
          break;
        }
      }
    }
    return result;
  }
}
