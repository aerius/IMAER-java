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
 * Emission factor supplier for farm animal housing.
 */
public interface FarmAnimalHousingEmissionFactorSupplier {

  /**
   * Obtain emission factors per animal for farm animal housing.
   * This should be the flat emission factor.
   */
  Map<Substance, Double> getAnimalHousingEmissionFactors(String animalHousingCode);

  /**
   * Determine the emission factor type for the animal housing.
   */
  FarmEmissionFactorType getAnimalHousingEmissionFactorType(String animalHousingCode);

  /**
   * Determine if the additional housing system is in fact a scrubber.
   */
  boolean isAdditionalHousingSystemAirScrubber(String additionalSystemCode);

  /**
   * Get the code of the basic housing system for a housing system.
   * Can return null, or the same housing code, to indicate that the housing system itself is a basic one.
   */
  String getAnimalBasicHousingCode(String animalHousingCode);

  /**
   * Obtain the remaining fraction of emission for additonal system for animal housing.
   */
  Map<Substance, Double> getAdditionalHousingSystemReductionFractions(String additionalSystemCode, String animalHousingCode);

}
