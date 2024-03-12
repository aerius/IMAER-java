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
package nl.overheid.aerius.gml.v6_0.togml;

import java.util.ArrayList;
import java.util.List;

import nl.overheid.aerius.gml.v6_0.source.farmland.FarmlandActivityProperty;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.FarmlandEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.farmland.AbstractFarmlandActivity;
import nl.overheid.aerius.shared.domain.v2.source.farmland.CustomFarmlandActivity;
import nl.overheid.aerius.shared.domain.v2.source.farmland.StandardFarmlandActivity;

/**
 *
 */
class Farmland2GML extends SpecificSource2GML<FarmlandEmissionSource> {

  private static final Substance FALLBACK_SUBSTANCE = Substance.NH3;

  @Override
  public nl.overheid.aerius.gml.v6_0.source.EmissionSource convert(final FarmlandEmissionSource emissionSource) {
    final nl.overheid.aerius.gml.v6_0.source.farmland.FarmlandEmissionSource returnSource =
        new nl.overheid.aerius.gml.v6_0.source.farmland.FarmlandEmissionSource();
    final List<AbstractFarmlandActivity> landSources = emissionSource.getSubSources();
    final List<FarmlandActivityProperty> activities = new ArrayList<>(landSources.size());

    for (final AbstractFarmlandActivity landSource : landSources) {
      if (landSource instanceof CustomFarmlandActivity) {
        activities.add(new FarmlandActivityProperty(convert((CustomFarmlandActivity) landSource)));
      } else if (landSource instanceof StandardFarmlandActivity) {
        activities.add(new FarmlandActivityProperty(convert((StandardFarmlandActivity) landSource)));
      }
    }

    returnSource.setActivities(activities);
    return returnSource;
  }

  private nl.overheid.aerius.gml.v6_0.source.farmland.CustomFarmlandActivity convert(final CustomFarmlandActivity landSource) {
    final nl.overheid.aerius.gml.v6_0.source.farmland.CustomFarmlandActivity gmlLandSource =
        new nl.overheid.aerius.gml.v6_0.source.farmland.CustomFarmlandActivity();
    gmlLandSource.setCode(landSource.getActivityCode());
    gmlLandSource.setEmissions(getEmissions(landSource.getEmissions(), FALLBACK_SUBSTANCE));
    return gmlLandSource;
  }

  private nl.overheid.aerius.gml.v6_0.source.farmland.StandardFarmlandActivity convert(final StandardFarmlandActivity landSource) {
    final nl.overheid.aerius.gml.v6_0.source.farmland.StandardFarmlandActivity gmlLandSource =
        new nl.overheid.aerius.gml.v6_0.source.farmland.StandardFarmlandActivity();
    gmlLandSource.setCode(landSource.getActivityCode());
    gmlLandSource.setStandardActivityCode(landSource.getFarmSourceCategoryCode());
    gmlLandSource.setNumberOfAnimals(landSource.getNumberOfAnimals());
    gmlLandSource.setNumberOfDays(landSource.getNumberOfDays());
    gmlLandSource.setTonnes(landSource.getTonnes());
    gmlLandSource.setMetersCubed(landSource.getMetersCubed());
    gmlLandSource.setNumberOfApplications(landSource.getNumberOfApplications());
    return gmlLandSource;
  }

}
