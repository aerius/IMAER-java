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

public interface RoadEmissionFactorSupplier {

  /**
   * Obtain emission factors per kilometer driven (in gram/kilometer) for a specific vehicle based on code.
   */
  Map<Substance, Double> getRoadSpecificVehicleEmissionFactors(final String specificVehicleCode, final String roadTypeCode);

  /**
   * Obtain emission factors per kilometer driven (in gram/kilometer) for a standard vehicle type.
   */
  Map<Substance, Double> getRoadStandardVehicleEmissionFactors(final RoadStandardEmissionFactorsKey emissionFactorsKey);

  /**
   * Obtain emission factors per kilometer driven (in gram/kilometer) for stagnation for a standard vehicle type.
   */
  Map<Substance, Double> getRoadStandardVehicleStagnatedEmissionFactors(final RoadStandardEmissionFactorsKey emissionFactorsKey);

}
