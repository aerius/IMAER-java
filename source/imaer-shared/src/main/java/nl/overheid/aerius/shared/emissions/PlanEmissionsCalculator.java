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
import nl.overheid.aerius.shared.domain.v2.source.PlanEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.plan.Plan;

public class PlanEmissionsCalculator {

  private final PlanEmissionFactorSupplier emissionFactorSupplier;

  PlanEmissionsCalculator(final PlanEmissionFactorSupplier emissionFactorSupplier) {
    this.emissionFactorSupplier = emissionFactorSupplier;
  }

  public Map<Substance, Double> calculateEmissions(final PlanEmissionSource planSource) {
    final Map<Substance, BigDecimal> summed = new EnumMap<>(Substance.class);
    for (final Plan plan : planSource.getSubSources()) {
      final Map<Substance, BigDecimal> emissionsForActivity = calculateEmissions(plan);
      emissionsForActivity.forEach(
          (key, value) -> plan.getEmissions().put(key, value.doubleValue()));
      emissionsForActivity.forEach(
          (key, value) -> summed.merge(key, value, (v1, v2) -> v1.add(v2)));
    }
    final Map<Substance, Double> result = new EnumMap<>(Substance.class);
    summed.forEach(
        (key, value) -> result.put(key, value.doubleValue()));
    return result;
  }

  public Map<Substance, Double> calculatePlanEmissions(final Plan plan) {
    final Map<Substance, Double> result = new EnumMap<>(Substance.class);
    calculateEmissions(plan).forEach(
        (key, value) -> result.put(key, value.doubleValue()));
    return result;
  }

  private Map<Substance, BigDecimal> calculateEmissions(final Plan plan) {
    final Map<Substance, BigDecimal> results = new EnumMap<>(Substance.class);
    final String planCode = plan.getPlanCode();
    final Map<Substance, Double> emissionFactors = emissionFactorSupplier.getPlanEmissionFactors(planCode);
    final BigDecimal amount = emissionFactorSupplier.isEmissionFactorPerUnits(planCode) ? BigDecimal.valueOf(plan.getAmount()) : BigDecimal.ONE;
    emissionFactors.forEach(
        (key, value) -> results.put(key, BigDecimal.valueOf(value).multiply(amount)));
    return results;
  }

}
