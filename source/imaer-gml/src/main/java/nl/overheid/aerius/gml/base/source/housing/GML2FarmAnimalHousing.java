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
package nl.overheid.aerius.gml.base.source.housing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.overheid.aerius.gml.base.AbstractGML2Specific;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.source.IsGmlEmission;
import nl.overheid.aerius.shared.domain.v2.source.FarmAnimalHousingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.farm.AdditionalHousingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.CustomAdditionalHousingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.CustomFarmAnimalHousing;
import nl.overheid.aerius.shared.domain.v2.source.farm.FarmAnimalHousing;
import nl.overheid.aerius.shared.domain.v2.source.farm.StandardAdditionalHousingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.StandardFarmAnimalHousing;
import nl.overheid.aerius.shared.emissions.FarmEmissionFactorType;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
public class GML2FarmAnimalHousing<T extends IsGmlFarmAnimalHousingSource> extends AbstractGML2Specific<T, FarmAnimalHousingEmissionSource> {

  private static final Logger LOG = LoggerFactory.getLogger(GML2FarmAnimalHousing.class);

  public GML2FarmAnimalHousing(final GMLConversionData conversionData) {
    super(conversionData);
  }

  @Override
  public FarmAnimalHousingEmissionSource convert(final T source) throws AeriusException {
    final FarmAnimalHousingEmissionSource emissionValues = new FarmAnimalHousingEmissionSource();
    for (final IsGmlProperty<IsGmlFarmAnimalHousing> housing : source.getFarmAnimalHousings()) {
      emissionValues.getSubSources().add(getFarmLodging(housing.getProperty(), source.getId()));
    }
    emissionValues.setEstablished(source.getEstablished());
    return emissionValues;
  }

  private FarmAnimalHousing getFarmLodging(final IsGmlFarmAnimalHousing housing, final String sourceId) throws AeriusException {
    final FarmAnimalHousing returnHousing;
    if (housing instanceof final IsGmlCustomFarmAnimalHousing custom) {
      returnHousing = convertCustom(custom, sourceId);
    } else if (housing instanceof final IsGmlStandardFarmAnimalHousing standard) {
      returnHousing = convertStandard(standard);
    } else {
      LOG.error("Don't know how to treat lodging type: {}", housing.getClass());
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR);
    }
    returnHousing.setAnimalTypeCode(housing.getAnimalCode());
    returnHousing.setNumberOfAnimals(housing.getNumberOfAnimals());
    returnHousing.setNumberOfDays(housing.getNumberOfDays());
    return returnHousing;
  }

  private CustomFarmAnimalHousing convertCustom(final IsGmlCustomFarmAnimalHousing customHousing, final String sourceId) throws AeriusException {
    final CustomFarmAnimalHousing customEmissions = new CustomFarmAnimalHousing();
    customEmissions.setDescription(customHousing.getDescription());
    customEmissions.setFarmEmissionFactorType(determineEmissionFactorType(customHousing.getEmissionFactorType(), sourceId));
    for (final IsGmlProperty<IsGmlEmission> emissionProperty : customHousing.getEmissionFactors()) {
      final IsGmlEmission emission = emissionProperty.getProperty();
      customEmissions.getEmissionFactors().put(emission.getSubstance(), emission.getValue());
    }
    return customEmissions;
  }

  private FarmEmissionFactorType determineEmissionFactorType(final String gmlEmissionFactorType, final String sourceId) throws AeriusException {
    if (gmlEmissionFactorType == null) {
      return FarmEmissionFactorType.PER_ANIMAL_PER_YEAR;
    }
    final FarmEmissionFactorType type = FarmEmissionFactorType.safeValueOf(gmlEmissionFactorType);
    if (type == null) {
      throw new AeriusException(ImaerExceptionReason.GML_UNKNOWN_FARM_EMISSION_FACTOR_TYPE, sourceId, gmlEmissionFactorType);
    }

    return type;
  }

  private StandardFarmAnimalHousing convertStandard(final IsGmlStandardFarmAnimalHousing standardLodging) throws AeriusException {
    final StandardFarmAnimalHousing standardEmissions = new StandardFarmAnimalHousing();
    final String categoryCode = standardLodging.getCode();

    standardEmissions.setAnimalHousingCode(categoryCode);
    handleAdditionalSystems(standardLodging, standardEmissions);

    return standardEmissions;
  }

  private void handleAdditionalSystems(final IsGmlStandardFarmAnimalHousing housing, final StandardFarmAnimalHousing standardEmissions)
      throws AeriusException {
    for (final IsGmlProperty<IsGmlAdditionalHousingSystem> additionalSystemProperty : housing.getAdditionalSystems()) {
      final IsGmlAdditionalHousingSystem lodgingSystem = additionalSystemProperty.getProperty();
      final AdditionalHousingSystem resultSystem;
      if (lodgingSystem instanceof final IsGmlStandardAdditionalHousingSystem standardSystem) {
        resultSystem = getStandardAdditionalSystem(standardSystem);
      } else if (lodgingSystem instanceof final IsGmlCustomAdditionalHousingSystem customSystem) {
        resultSystem = getCustomAdditionalSystem(customSystem);
      } else {
        LOG.error("Don't know how to treat additional system type: {}", lodgingSystem.getClass());
        throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR);
      }
      standardEmissions.getAdditionalSystems().add(resultSystem);
    }
  }

  private StandardAdditionalHousingSystem getStandardAdditionalSystem(final IsGmlStandardAdditionalHousingSystem additionalSystem) {
    final StandardAdditionalHousingSystem resultSystem = new StandardAdditionalHousingSystem();
    resultSystem.setAdditionalSystemCode(additionalSystem.getCode());
    return resultSystem;
  }

  private CustomAdditionalHousingSystem getCustomAdditionalSystem(final IsGmlCustomAdditionalHousingSystem additionalSystem) {
    final CustomAdditionalHousingSystem resultSystem = new CustomAdditionalHousingSystem();
    resultSystem.setDescription(additionalSystem.getDescription());
    resultSystem.setAirScrubber(additionalSystem.getAirScrubber() == null || additionalSystem.getAirScrubber());
    for (final IsGmlProperty<IsGmlEmission> emissionProperty : additionalSystem.getEmissionReductionFactors()) {
      final IsGmlEmission emission = emissionProperty.getProperty();
      resultSystem.getEmissionReductionFactors().put(emission.getSubstance(), emission.getValue());
    }
    return resultSystem;
  }

}
