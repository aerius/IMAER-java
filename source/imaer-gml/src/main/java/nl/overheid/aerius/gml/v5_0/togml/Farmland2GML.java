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

import nl.overheid.aerius.gml.v5_0.source.farmland.FarmlandActivity;
import nl.overheid.aerius.gml.v5_0.source.farmland.FarmlandActivityProperty;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.FarmlandEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.farmland.AbstractFarmlandActivity;

/**
 *
 */
class Farmland2GML extends SpecificSource2GML<FarmlandEmissionSource> {

  private static final Substance FALLBACK_SUBSTANCE = Substance.NH3;

  @Override
  public nl.overheid.aerius.gml.v5_0.source.EmissionSource convert(final FarmlandEmissionSource emissionSource) {
    final nl.overheid.aerius.gml.v5_0.source.farmland.FarmlandEmissionSource returnSource = new nl.overheid.aerius.gml.v5_0.source.farmland.FarmlandEmissionSource();
    final List<AbstractFarmlandActivity> landSources = emissionSource.getSubSources();
    final List<FarmlandActivityProperty> activities = new ArrayList<>(landSources.size());

    for (final AbstractFarmlandActivity landSource : landSources) {
      final FarmlandActivity gmlLandSource = new FarmlandActivity();
      gmlLandSource.setCode(landSource.getActivityCode());
      gmlLandSource.setEmissions(getEmissions(landSource.getEmissions(), FALLBACK_SUBSTANCE));
      activities.add(new FarmlandActivityProperty(gmlLandSource));
    }

    returnSource.setActivities(activities);
    return returnSource;
  }

}
