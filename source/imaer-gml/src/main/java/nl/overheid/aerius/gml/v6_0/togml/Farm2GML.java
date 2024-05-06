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

import nl.overheid.aerius.gml.v6_0.source.housing.AbstractAdditionalHousingSystem;
import nl.overheid.aerius.gml.v6_0.source.housing.AbstractFarmAnimalHousing;
import nl.overheid.aerius.gml.v6_0.source.housing.AdditionalHousingSystemProperty;
import nl.overheid.aerius.gml.v6_0.source.housing.CustomAdditionalHousingSystem;
import nl.overheid.aerius.gml.v6_0.source.housing.CustomFarmAnimalHousing;
import nl.overheid.aerius.gml.v6_0.source.housing.FarmAnimalHousingEmissionSource;
import nl.overheid.aerius.gml.v6_0.source.housing.FarmAnimalHousingProperty;
import nl.overheid.aerius.gml.v6_0.source.housing.StandardAdditionalHousingSystem;
import nl.overheid.aerius.gml.v6_0.source.housing.StandardFarmAnimalHousing;
import nl.overheid.aerius.shared.domain.Substance;

/**
 *
 */
class Farm2GML extends SpecificSource2GML<nl.overheid.aerius.shared.domain.v2.source.FarmAnimalHousingEmissionSource> {

  @Override
  public nl.overheid.aerius.gml.v6_0.source.EmissionSource convert(
      final nl.overheid.aerius.shared.domain.v2.source.FarmAnimalHousingEmissionSource emissionSource) {
    final FarmAnimalHousingEmissionSource returnSource = new FarmAnimalHousingEmissionSource();
    final List<FarmAnimalHousingProperty> housings = new ArrayList<>();

    for (final nl.overheid.aerius.shared.domain.v2.source.farm.FarmAnimalHousing animalHousing : emissionSource.getSubSources()) {
      if (animalHousing instanceof final nl.overheid.aerius.shared.domain.v2.source.farm.StandardFarmAnimalHousing standard) {
        housings.add(convert(standard));
      } else if (animalHousing instanceof final nl.overheid.aerius.shared.domain.v2.source.farm.CustomFarmAnimalHousing custom) {
        housings.add(convert(custom));
      }
    }
    returnSource.setFarmAnimalHousings(housings);

    returnSource.setEstablished(emissionSource.getEstablished());

    return returnSource;
  }

  private FarmAnimalHousingProperty convert(final nl.overheid.aerius.shared.domain.v2.source.farm.StandardFarmAnimalHousing housing) {
    final StandardFarmAnimalHousing gmlHousing = new StandardFarmAnimalHousing();
    gmlHousing.setCode(housing.getAnimalHousingCode());
    copyGenericProperties(housing, gmlHousing);

    //add the additional systems based on input.
    for (final nl.overheid.aerius.shared.domain.v2.source.farm.AdditionalHousingSystem additional : housing.getAdditionalSystems()) {
      if (additional instanceof final nl.overheid.aerius.shared.domain.v2.source.farm.StandardAdditionalHousingSystem standard) {
        gmlHousing.getAdditionalSystems().add(new AdditionalHousingSystemProperty(convert(standard)));
      } else if (additional instanceof final nl.overheid.aerius.shared.domain.v2.source.farm.CustomAdditionalHousingSystem custom) {
        gmlHousing.getAdditionalSystems().add(new AdditionalHousingSystemProperty(convert(custom)));
      }
    }

    return new FarmAnimalHousingProperty(gmlHousing);
  }

  private FarmAnimalHousingProperty convert(final nl.overheid.aerius.shared.domain.v2.source.farm.CustomFarmAnimalHousing housing) {
    final CustomFarmAnimalHousing gmlHousing = new CustomFarmAnimalHousing();
    copyGenericProperties(housing, gmlHousing);
    gmlHousing.setEmissionFactors(getEmissions(housing.getEmissionFactors(), Substance.NH3));
    gmlHousing.setDescription(housing.getDescription());
    gmlHousing.setEmissionFactorType(housing.getFarmEmissionFactorType().name());
    return new FarmAnimalHousingProperty(gmlHousing);
  }

  private void copyGenericProperties(final nl.overheid.aerius.shared.domain.v2.source.farm.FarmAnimalHousing housing,
      final AbstractFarmAnimalHousing gmlHousing) {
    gmlHousing.setAnimalCode(housing.getAnimalTypeCode());
    gmlHousing.setNumberOfAnimals(housing.getNumberOfAnimals());
    gmlHousing.setNumberOfDays(housing.getNumberOfDays());
  }

  private AbstractAdditionalHousingSystem convert(
      final nl.overheid.aerius.shared.domain.v2.source.farm.StandardAdditionalHousingSystem standardSystem) {
    final StandardAdditionalHousingSystem gmlAdditionalSystem = new StandardAdditionalHousingSystem();
    gmlAdditionalSystem.setCode(standardSystem.getAdditionalSystemCode());
    return gmlAdditionalSystem;
  }

  private AbstractAdditionalHousingSystem convert(
      final nl.overheid.aerius.shared.domain.v2.source.farm.CustomAdditionalHousingSystem customSystem) {
    final CustomAdditionalHousingSystem gmlAdditionalSystem = new CustomAdditionalHousingSystem();
    gmlAdditionalSystem.setDescription(customSystem.getDescription());
    gmlAdditionalSystem.setAirScrubber(customSystem.isAirScrubber());
    gmlAdditionalSystem.setEmissionReductionFactors(getEmissions(customSystem.getEmissionReductionFactors(), Substance.NH3));
    return gmlAdditionalSystem;
  }

}
