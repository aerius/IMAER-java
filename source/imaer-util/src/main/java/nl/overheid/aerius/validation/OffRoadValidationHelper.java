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

import java.util.Optional;
import java.util.OptionalDouble;

import nl.overheid.aerius.shared.domain.IntRange;

/**
 * Interface of Helper class used in off road validation. Helper class should implement checks and data of actual off road data.
 */
public interface OffRoadValidationHelper {

  /**
   * Returns true if the give code is a valid off road source code.
   *
   * @param offRoadMobileSourceCode code to check
   * @return true if valid
   */
  boolean isValidOffRoadMobileSourceCode(String offRoadMobileSourceCode);

  /**
   * Returns true if the source emission can be calculated using power.
   *
   * @param offRoadMobileSourceCode code of the off road source
   * @return true if can be calculated using power
   */
  default boolean expectsPower(final String offRoadMobileSourceCode) {
    return false;
  }

  /**
   * Returns the power range for the off road category if there is a power range present, otherwise returns empty Optional.
   *
   * @param offRoadMobileSourceCode code of the off road source
   * @return the range or empty optional
   */
  default Optional<IntRange> getPowerRange(final String offRoadMobileSourceCode) {
    return Optional.empty();
  }

  /**
   * Returns true if the source emission can be calculated using liters fuel per year.
   *
   * @param offRoadMobileSourceCode code of the off road source
   * @return true if can be calculated using liters fuel per year
   */
  boolean expectsLiterFuelPerYear(String offRoadMobileSourceCode);

  /**
   * Returns true if the source emission can be calculated using operating hours per year.
   *
   * @param offRoadMobileSourceCode code of the off road source
   * @return true if can be calculated using operating hours per year
   */
  boolean expectsOperatingHoursPerYear(String offRoadMobileSourceCode);

  /**
   * Returns true if the source emission can be calculated using AdBlue per year.
   *
   * @param offRoadMobileSourceCode code of the off road source
   * @return true if can be calculated using AdBlue per year
   */
  boolean expectsLiterAdBluePerYear(String offRoadMobileSourceCode);

  /**
   * Returns the maximum ratio that is possible between fuel usage and AdBlue usage.
   *
   * @param offRoadMobileSourceCode code of the off road source
   * @return Optional ratio number or an empty object if no ratio possible for the give off road code
   */
  OptionalDouble getMaxAdBlueFuelRatio(final String offRoadMobileSourceCode);

}
