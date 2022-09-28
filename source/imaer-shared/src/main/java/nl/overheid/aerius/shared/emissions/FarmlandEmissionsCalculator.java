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
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

public class FarmlandEmissionsCalculator implements FarmlandActivityVisitor<Map<Substance, BigDecimal>> {

  private final FarmlandEmissionFactorSupplier farmlandEmissionFactorSupplier;

  public FarmlandEmissionsCalculator(final FarmlandEmissionFactorSupplier farmlandEmissionFactorSupplier) {
    this.farmlandEmissionFactorSupplier = farmlandEmissionFactorSupplier;
  }

  public Map<Substance, Double> updateEmissions(final FarmlandEmissionSource farmlandSource) throws AeriusException {
    final Map<Substance, BigDecimal> summed = new EnumMap<>(Substance.class);
    for (final AbstractFarmlandActivity activity : farmlandSource.getSubSources()) {
      final Map<Substance, BigDecimal> activityEmissions = activity.accept(this);
      activityEmissions.forEach(
          (key, value) -> {
            activity.getEmissions().put(key, value.doubleValue());
            summed.merge(key, value, BigDecimal::add);
          });
    }

    final Map<Substance, Double> result = new EnumMap<>(Substance.class);
    summed.forEach((key, value) -> result.put(key, value.doubleValue()));
    return result;
  }

  @Override
  public Map<Substance, BigDecimal> visit(final CustomFarmlandActivity activity) {
    final Map<Substance, BigDecimal> activityEmissions = new EnumMap<>(Substance.class);
    activity.getEmissions().forEach(
        (key, value) -> activityEmissions.put(key, BigDecimal.valueOf(value)));
    return activityEmissions;
  }

  @Override
  public Map<Substance, BigDecimal> visit(final StandardFarmlandActivity activity) throws AeriusException {
    final Map<Substance, BigDecimal> activityEmissions = new EnumMap<>(Substance.class);
    final Map<Substance, Double> emissionFactors = farmlandEmissionFactorSupplier
        .getFarmSourceEmissionFactors(activity.getFarmSourceCategoryCode());

    final FarmEmissionFactorType emissionFactorType =
        farmlandEmissionFactorSupplier.getFarmSourceEmissionFactorType(activity.getFarmSourceCategoryCode());
    if (emissionFactorType == FarmEmissionFactorType.PER_ANIMAL_PER_DAY) {
      emissionFactors.forEach((key, value) -> activityEmissions.put(key,
          BigDecimal.valueOf(value)
              .multiply(BigDecimal.valueOf(activity.getNumberOfDays()))
              .multiply(BigDecimal.valueOf(activity.getNumberOfAnimals()))));
    } else if (emissionFactorType == FarmEmissionFactorType.PER_ANIMAL_PER_YEAR) {
      emissionFactors.forEach((key, value) -> activityEmissions.put(key,
          BigDecimal.valueOf(value)
              .multiply(BigDecimal.valueOf(activity.getNumberOfAnimals()))));
    } else {
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR, "Invalid farm emission factor type for StandardFarmlandActivity.");
    }
    return activityEmissions;
  }
}
