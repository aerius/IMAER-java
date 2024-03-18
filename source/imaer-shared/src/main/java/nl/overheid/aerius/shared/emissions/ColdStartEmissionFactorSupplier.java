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

/**
 * Interface for getting the emission factors for cold start.
 */
public interface ColdStartEmissionFactorSupplier {

  /**
   * Obtain emission factors per cold start for a specific vehicle based on the code.
   *
   * @param vehicleCode code for the vehicle
   * @return emission factors
   */
  Map<Substance, Double> getColdStartSpecificVehicleEmissionFactors(String vehicleCode);

  /**
   * Obtain emission factors per cold start for a standard vehicle based on the code.
   *
   * @param vehicleCode code for the vehicle
   * @return emission factors
   */
  Map<Substance, Double> getColdStartStandardVehicleEmissionFactors(String vehicleCode);

}
