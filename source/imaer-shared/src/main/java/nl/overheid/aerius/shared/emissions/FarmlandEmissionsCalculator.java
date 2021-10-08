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

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.FarmlandEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.farmland.FarmlandActivity;

public class FarmlandEmissionsCalculator {

  public Map<Substance, Double> calculateEmissions(final FarmlandEmissionSource farmlandSource) {
    final Map<Substance, BigDecimal> summed = new EnumMap<>(Substance.class);
    for (final FarmlandActivity activity : farmlandSource.getSubSources()) {
      final Map<Substance, Double> emissionsForActivity = activity.getEmissions();
      emissionsForActivity.forEach(
          (key, value) -> summed.merge(key, BigDecimal.valueOf(value), (v1, v2) -> v1.add(v2)));
    }
    final Map<Substance, Double> result = new EnumMap<>(Substance.class);
    summed.forEach(
        (key, value) -> result.put(key, value.doubleValue()));
    return result;
  }

}
