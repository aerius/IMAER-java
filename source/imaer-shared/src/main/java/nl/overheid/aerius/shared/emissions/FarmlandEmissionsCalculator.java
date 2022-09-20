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
import nl.overheid.aerius.shared.domain.v2.source.farmland.AbstractFarmlandActivity;
import nl.overheid.aerius.shared.domain.v2.source.farmland.CustomFarmlandActivity;
import nl.overheid.aerius.shared.domain.v2.source.farmland.FarmlandActivityVisitor;
import nl.overheid.aerius.shared.domain.v2.source.farmland.StandardFarmlandActivity;

public class FarmlandEmissionsCalculator implements FarmlandActivityVisitor<Map<Substance, BigDecimal>> {

  private final FarmlandEmissionFactorSupplier farmlandEmissionFactorSupplier;

  public FarmlandEmissionsCalculator(final FarmlandEmissionFactorSupplier farmlandEmissionFactorSupplier) {
    this.farmlandEmissionFactorSupplier = farmlandEmissionFactorSupplier;
  }

  public Map<Substance, Double> updateEmissions(final FarmlandEmissionSource farmlandSource) {
    final Map<Substance, BigDecimal> summed = new EnumMap<>(Substance.class);
    for (final AbstractFarmlandActivity activity : farmlandSource.getSubSources()) {
      activity.accept(this, summed);
    }

    final Map<Substance, Double> result = new EnumMap<>(Substance.class);
    summed.forEach((key, value) -> result.put(key, value.doubleValue()));
    return result;
  }

  @Override
  public void visit(final CustomFarmlandActivity activity, final Map<Substance, BigDecimal> summedEmissions) {
    activity.getEmissions().forEach(
        (key, value) -> summedEmissions.merge(key, BigDecimal.valueOf(value), (v1, v2) -> v1.add(v2)));
  }

  @Override
  public void visit(final StandardFarmlandActivity activity, final Map<Substance, BigDecimal> summedEmissions) {
    final Map<Substance, Double> emissionFactors = farmlandEmissionFactorSupplier
        .getFarmSourceEmissionFactors(activity.getFarmSourceCategoryCode());

    if (activity.getFarmEmissionFactorType() == FarmEmissionFactorType.PER_ANIMAL_PER_DAY) {
      emissionFactors.forEach((key, value) ->
          summedEmissions.merge(key, BigDecimal.valueOf(value).multiply(BigDecimal.valueOf(activity.getNumberOfDays()))
              .multiply(BigDecimal.valueOf(activity.getNumberOfAnimals())), (v1, v2) -> v1.add(v2))
      );
    }

    if (activity.getFarmEmissionFactorType() == FarmEmissionFactorType.PER_ANIMAL_PER_YEAR) {
      emissionFactors.forEach((key, value) ->
          summedEmissions.merge(key, BigDecimal.valueOf(value).multiply(BigDecimal.valueOf(activity.getNumberOfAnimals())), (v1, v2) -> v1.add(v2))
      );
    }
  }
}
