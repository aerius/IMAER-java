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
package nl.overheid.aerius.gml.base.source.plan;

import nl.overheid.aerius.gml.base.AbstractGML2Specific;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.shared.domain.v2.source.PlanEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.plan.Plan;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 *
 */
public class GML2Plan<T extends IsGmlPlanEmissionSource> extends AbstractGML2Specific<T, PlanEmissionSource> {

  /**
   * @param conversionData The conversion data to use.
   */
  public GML2Plan(final GMLConversionData conversionData) {
    super(conversionData);
  }

  @Override
  public PlanEmissionSource convert(final T source) throws AeriusException {
    final PlanEmissionSource emissionSource = new PlanEmissionSource();
    for (final IsGmlProperty<IsGmlPlan> planProperty : source.getPlans()) {
      final IsGmlPlan plan = planProperty.getProperty();
      final Plan planEmission = new Plan();
      planEmission.setPlanCode(plan.getCode());
      planEmission.setAmount(plan.getAmount());
      planEmission.setDescription(plan.getDescription());
      emissionSource.getSubSources().add(planEmission);
    }
    return emissionSource;
  }

}
