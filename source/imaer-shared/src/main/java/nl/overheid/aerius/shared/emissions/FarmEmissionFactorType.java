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
package nl.overheid.aerius.shared.emissions;

import java.util.Locale;

public enum FarmEmissionFactorType {
  PER_YEAR,
  PER_ANIMAL_PER_YEAR,
  PER_ANIMAL_PER_DAY,
  PER_METERS_CUBED_PER_APPLICATION,
  PER_TONNES_PER_APPLICATION,
  PER_TONNES_PER_YEAR,
  PER_METERS_SQUARED_PER_DAY,
  PER_METERS_SQUARED_PER_YEAR;

  public static boolean expectsNumberOfAnimals(final FarmEmissionFactorType emissionFactorType) {
    return emissionFactorType == PER_ANIMAL_PER_DAY || emissionFactorType == PER_ANIMAL_PER_YEAR;
  }

  public static boolean expectsNumberOfDays(final FarmEmissionFactorType emissionFactorType) {
    return emissionFactorType == PER_ANIMAL_PER_DAY || emissionFactorType == PER_METERS_SQUARED_PER_DAY;
  }

  public static boolean expectsNumberOfApplications(final FarmEmissionFactorType emissionFactorType) {
    return emissionFactorType == PER_METERS_CUBED_PER_APPLICATION || emissionFactorType == PER_TONNES_PER_APPLICATION;
  }

  public static boolean expectsMetersCubed(final FarmEmissionFactorType emissionFactorType) {
    return emissionFactorType == PER_METERS_CUBED_PER_APPLICATION;
  }

  public static boolean expectsTonnes(final FarmEmissionFactorType emissionFactorType) {
    return emissionFactorType == PER_TONNES_PER_APPLICATION || emissionFactorType == PER_TONNES_PER_YEAR;
  }

  public static boolean expectsMetersSquared(final FarmEmissionFactorType emissionFactorType) {
    return emissionFactorType == PER_METERS_SQUARED_PER_DAY || emissionFactorType == PER_METERS_SQUARED_PER_YEAR;
  }

  public static FarmEmissionFactorType safeValueOf(final String value) {
    try {
      return value == null ? null : valueOf(value.toUpperCase(Locale.ROOT));
    } catch (final IllegalArgumentException e) {
      return null;
    }
  }
}
