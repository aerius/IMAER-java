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

import nl.overheid.aerius.gml.v5_0.source.lodging.AdditionalLodgingSystem;
import nl.overheid.aerius.gml.v5_0.source.lodging.CustomFarmLodging;
import nl.overheid.aerius.gml.v5_0.source.lodging.FarmLodging;
import nl.overheid.aerius.gml.v5_0.source.lodging.FarmLodgingEmissionSource;
import nl.overheid.aerius.gml.v5_0.source.lodging.FarmLodgingProperty;
import nl.overheid.aerius.gml.v5_0.source.lodging.LodgingFodderMeasure;
import nl.overheid.aerius.gml.v5_0.source.lodging.LodgingFodderMeasureProperty;
import nl.overheid.aerius.gml.v5_0.source.lodging.LodgingSystemProperty;
import nl.overheid.aerius.gml.v5_0.source.lodging.ReductiveLodgingSystem;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.farm.StandardFarmLodging;

/**
 *
 */
class Farm2GML extends SpecificSource2GML<nl.overheid.aerius.shared.domain.v2.source.FarmLodgingEmissionSource> {

  @Override
  public nl.overheid.aerius.gml.v5_0.source.EmissionSource convert(
      final nl.overheid.aerius.shared.domain.v2.source.FarmLodgingEmissionSource emissionSource) {
    final FarmLodgingEmissionSource returnSource = new FarmLodgingEmissionSource();
    final List<FarmLodgingProperty> lodgings = new ArrayList<>();

    for (final nl.overheid.aerius.shared.domain.v2.source.farm.FarmLodging lodging : emissionSource.getSubSources()) {
      if (lodging instanceof StandardFarmLodging) {
        lodgings.add(convert((StandardFarmLodging) lodging));
      } else if (lodging instanceof nl.overheid.aerius.shared.domain.v2.source.farm.CustomFarmLodging) {
        lodgings.add(convert((nl.overheid.aerius.shared.domain.v2.source.farm.CustomFarmLodging) lodging));
      }
    }
    returnSource.setFarmLodgings(lodgings);

    returnSource.setEstablished(emissionSource.getEstablished());

    return returnSource;
  }

  private FarmLodgingProperty convert(final StandardFarmLodging lodging) {
    final FarmLodging gmlLodging = new FarmLodging();
    gmlLodging.setCode(lodging.getFarmLodgingCode());
    gmlLodging.setNumberOfAnimals(lodging.getNumberOfAnimals());

    gmlLodging.setLodgingSystemDefinitionCode(lodging.getSystemDefinitionCode());

    //add the lodging system code and additional/reductive ones based on input.
    for (final nl.overheid.aerius.shared.domain.v2.source.farm.AdditionalLodgingSystem additional : lodging.getAdditionalLodgingSystems()) {
      gmlLodging.getLodgingSystems().add(new LodgingSystemProperty(convert(additional)));
    }
    for (final nl.overheid.aerius.shared.domain.v2.source.farm.ReductiveLodgingSystem reductive : lodging.getReductiveLodgingSystems()) {
      gmlLodging.getLodgingSystems().add(new LodgingSystemProperty(convert(reductive)));
    }
    for (final nl.overheid.aerius.shared.domain.v2.source.farm.LodgingFodderMeasure fodderMeasure : lodging.getFodderMeasures()) {
      gmlLodging.getFodderMeasures().add(new LodgingFodderMeasureProperty(convert(fodderMeasure)));
    }

    //we're not adding emissionfactor/description to avoid impression that it will be used on import.
    return new FarmLodgingProperty(gmlLodging);
  }

  private FarmLodgingProperty convert(final nl.overheid.aerius.shared.domain.v2.source.farm.CustomFarmLodging lodging) {
    final CustomFarmLodging gmlLodging = new CustomFarmLodging();
    gmlLodging.setNumberOfAnimals(lodging.getNumberOfAnimals());
    gmlLodging.setAnimalCode(lodging.getAnimalCode());
    gmlLodging.setEmissionFactors(getEmissions(lodging.getEmissionFactors(), Substance.NH3));
    gmlLodging.setDescription(lodging.getDescription());
    //custom farm does NOT have a code other than the optional animal code.
    return new FarmLodgingProperty(gmlLodging);
  }

  private AdditionalLodgingSystem convert(final nl.overheid.aerius.shared.domain.v2.source.farm.AdditionalLodgingSystem farmLodgingAdditional) {
    final AdditionalLodgingSystem additionalLodgingSystem = new AdditionalLodgingSystem();
    additionalLodgingSystem.setCode(farmLodgingAdditional.getLodgingSystemCode());
    additionalLodgingSystem.setNumberOfAnimals(farmLodgingAdditional.getNumberOfAnimals());
    additionalLodgingSystem.setLodgingSystemDefinitionCode(farmLodgingAdditional.getSystemDefinitionCode());
    return additionalLodgingSystem;
  }

  private LodgingFodderMeasure convert(final nl.overheid.aerius.shared.domain.v2.source.farm.LodgingFodderMeasure fodderMeasure) {
    final LodgingFodderMeasure lodgingFodderMeasure = new LodgingFodderMeasure();
    lodgingFodderMeasure.setCode(fodderMeasure.getFodderMeasureCode());
    return lodgingFodderMeasure;
  }

  private ReductiveLodgingSystem convert(final nl.overheid.aerius.shared.domain.v2.source.farm.ReductiveLodgingSystem farmLodgingReductive) {
    final ReductiveLodgingSystem reductiveLodgingSystem = new ReductiveLodgingSystem();
    reductiveLodgingSystem.setCode(farmLodgingReductive.getLodgingSystemCode());
    reductiveLodgingSystem.setLodgingSystemDefinitionCode(farmLodgingReductive.getSystemDefinitionCode());
    return reductiveLodgingSystem;
  }

}
