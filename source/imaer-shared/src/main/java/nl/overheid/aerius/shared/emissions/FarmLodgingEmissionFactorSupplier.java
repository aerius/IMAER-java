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

public interface FarmLodgingEmissionFactorSupplier {

  /**
   * Obtain emission factors per animal for farm lodging.
   * This should be the flat emission factor
   */
  Map<Substance, Double> getLodgingEmissionFactors(final String lodgingCode);

  /**
   * For some lodgings, the emission factor can be constrained if scrubbers are stacked on it.
   *
   * If that's a possibility, this method should return true.
   */
  boolean canLodgingEmissionFactorsBeConstrained(final String lodgingCode);

  /**
   * Determine if the additional system is in fact a scrubber.
   */
  boolean isAdditionalSystemScrubber(final String additionalSystemCode);

  /**
   * Determine if the reducvie system is in fact a scrubber.
   */
  boolean isReductiveSystemScrubber(final String reductiveSystemCode);

  /**
   * Obtain constrained emission factors per animal for farm lodging.
   *
   */
  Map<Substance, Double> getLodgingConstrainedEmissionFactors(final String lodgingCode);

  /**
   * Obtain emission factors per animal for additional system for lodging.
   */
  Map<Substance, Double> getAdditionalSystemEmissionFactors(final String additionalSystemCode);

  /**
   * Obtain the remaining fraction of emission for reductive system for lodging.
   */
  Map<Substance, Double> getReductiveSystemRemainingFractions(final String reductiveSystemCode);

  /**
   * Obtain the total remaining fraction of emission for a fodder measure.
   *
   * Should only be used when there is one fodder measure being applied.
   */
  Map<Substance, Double> getFodderRemainingFractionTotal(final String fodderMeasureCode);

  /**
   * Determine if a fodder measure can be applied to a specific lodging type.
   *
   * This should be done based on animal category of the lodging type.
   */
  boolean canFodderApplyToLodging(final String fodderMeasureCode, final String lodgingCode);

  /**
   * Obtain the proportion part for the floor for a fodder measure and lodging type combination.
   *
   * This is used when multiple fodder measures are applied to calculate a combined emission reduction.
   */
  Map<Substance, Double> getFodderProportionFloor(final String fodderMeasureCode, final String lodgingCode);

  /**
   * Obtain the proportion part for the cellar for a fodder measure and lodging type combination.
   *
   * This is used when multiple fodder measures are applied to calculate a combined emission reduction.
   */
  Map<Substance, Double> getFodderProportionCellar(final String fodderMeasureCode, final String lodgingCode);

  /**
   * Obtain the remaining emission fraction for the floor for a fodder measure.
   *
   * This is used when multiple fodder measures are applied to calculate a combined emission reduction.
   */
  Map<Substance, Double> getFodderRemainingFractionFloor(final String fodderMeasureCode);

  /**
   * Obtain the remaining emission fraction for the cellar for a fodder measure.
   *
   * This is used when multiple fodder measures are applied to calculate a combined emission reduction.
   */
  Map<Substance, Double> getFodderRemainingFractionCellar(final String fodderMeasureCode);

}
