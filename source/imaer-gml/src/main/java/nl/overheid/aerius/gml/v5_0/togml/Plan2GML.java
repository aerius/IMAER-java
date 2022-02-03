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
package nl.overheid.aerius.gml.v5_0.togml;

import java.util.ArrayList;
import java.util.List;

import nl.overheid.aerius.gml.v5_0.source.plan.PlanProperty;
import nl.overheid.aerius.shared.domain.v2.source.PlanEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.plan.Plan;

/**
 *
 */
class Plan2GML extends SpecificSource2GML<PlanEmissionSource> {

  @Override
  public nl.overheid.aerius.gml.v5_0.source.EmissionSource convert(final PlanEmissionSource emissionSource) {
    final nl.overheid.aerius.gml.v5_0.source.plan.PlanEmissionSource returnSource = new nl.overheid.aerius.gml.v5_0.source.plan.PlanEmissionSource();
    final List<Plan> planSources = emissionSource.getSubSources();
    final List<PlanProperty> plans = new ArrayList<>(planSources.size());

    for (final Plan plan : planSources) {
      final nl.overheid.aerius.gml.v5_0.source.plan.Plan gmlPlanSource = new nl.overheid.aerius.gml.v5_0.source.plan.Plan();
      gmlPlanSource.setDescription(plan.getDescription());
      gmlPlanSource.setCode(plan.getPlanCode());
      gmlPlanSource.setAmount(plan.getAmount());
      //we're not adding emissionfactor/description to avoid impression that it will be used on import.
      plans.add(new PlanProperty(gmlPlanSource));
    }

    returnSource.setPlans(plans);
    return returnSource;
  }

}
