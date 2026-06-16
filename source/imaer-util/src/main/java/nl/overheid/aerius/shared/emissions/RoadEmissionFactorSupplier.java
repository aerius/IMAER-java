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

import java.util.Map;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.road.RoadStandardEmissionFactorsKey;
import nl.overheid.aerius.shared.domain.v2.source.road.RoadStandardsInterpolationValues;

public interface RoadEmissionFactorSupplier {

  /**
   * Obtain emission factors per kilometer driven for a specific vehicle based on code.
   * For NL, this should be in gram/kilometer.
   * For UK, this should be in gram/kilometer/second for vehicles per 24 hours.
   */
  Map<Substance, Double> getRoadSpecificVehicleEmissionFactors(final String specificVehicleCode, final String roadTypeCode);

  /**
   * Obtain emission factors per kilometer driven for a standard vehicle type.
   * For NL, this should be in gram/kilometer.
   * For UK, this should be in gram/kilometer/second for vehicles per 24 hours.
   */
  Map<Substance, Double> getRoadStandardVehicleEmissionFactors(final RoadStandardEmissionFactorsKey emissionFactorsKey);

  /**
   * Obtain emission factors per kilometer driven for stagnation for a standard vehicle type.
   * For NL, this should be in gram/kilometer.
   * For UK, this should be in gram/kilometer/second for vehicles per 24 hours.
   */
  Map<Substance, Double> getRoadStandardVehicleStagnatedEmissionFactors(final RoadStandardEmissionFactorsKey emissionFactorsKey);

  /**
   * Obtain interpolation values for a standard vehicle type.
   */
  RoadStandardsInterpolationValues getRoadStandardVehicleInterpolationValues(final RoadStandardEmissionFactorsKey emissionFactorsKey);

}
