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
package nl.overheid.aerius.gml.base.conversion;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.characteristics.OPSSourceCharacteristics;

/**
 * Class to be used to convert from properties used for plan activities for the older IMAER versions (<= 4.0)
 * to a generic emission source.
 */
public class PlanConversion {

  private final Map<Substance, BigDecimal> emissionFactors = new EnumMap<>(Substance.class);
  private final OPSSourceCharacteristics characteristics;

  /**
   * @param emissionFactors The emission factors to use.
   * @param fuelConsumptionIdle The characteristics to use.
   */
  public PlanConversion(final Map<Substance, Double> emissionFactors, final OPSSourceCharacteristics characteristics) {
    emissionFactors.forEach(
        (key, value) -> this.emissionFactors.put(key, BigDecimal.valueOf(value)));
    this.characteristics = characteristics;
  }

  public Map<Substance, Double> calculateEmissionsForActivity(final int amount) {
    final Map<Substance, Double> result = new EnumMap<>(Substance.class);
    emissionFactors.forEach(
        (key, value) -> result.put(key, value.multiply(BigDecimal.valueOf(amount)).doubleValue()));
    return result;
  }

  public OPSSourceCharacteristics getCharacteristics() {
    return characteristics;
  }

}
