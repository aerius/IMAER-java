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
package nl.overheid.aerius.gml.base.source.lodging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.overheid.aerius.gml.base.AbstractGML2Specific;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.conversion.FarmLodgingConversion;
import nl.overheid.aerius.gml.base.source.IsGmlEmission;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.FarmAnimalHousingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.FarmLodgingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.farm.AdditionalLodgingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.CustomFarmAnimalHousing;
import nl.overheid.aerius.shared.domain.v2.source.farm.CustomFarmLodging;
import nl.overheid.aerius.shared.domain.v2.source.farm.FarmAnimalHousing;
import nl.overheid.aerius.shared.domain.v2.source.farm.FarmLodging;
import nl.overheid.aerius.shared.domain.v2.source.farm.LodgingFodderMeasure;
import nl.overheid.aerius.shared.domain.v2.source.farm.ReductiveLodgingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.StandardAdditionalHousingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.StandardFarmAnimalHousing;
import nl.overheid.aerius.shared.domain.v2.source.farm.StandardFarmLodging;
import nl.overheid.aerius.shared.emissions.FarmEmissionFactorType;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
public class GML2Farm<T extends IsGmlFarmLodgingEmissionSource> extends AbstractGML2Specific<T, EmissionSource> {

  private static final Logger LOG = LoggerFactory.getLogger(GML2Farm.class);

  private static final String UNKNOWN_ANIMAL_TYPE_CODE = "";

  public GML2Farm(final GMLConversionData conversionData) {
    super(conversionData);
  }

  @Override
  public EmissionSource convert(final T source) throws AeriusException {
    final EmissionSource converted;
    if (getConversionData().hasFarmLodgingConversions()) {
      converted = convertToAnimalHousing(source);
    } else {
      // Temporary while we implement this in a feature branch. Once upstream main is set, we can remove this along with the deprecated class.
      converted = convertLegacy(source);
    }
    return converted;
  }

  private FarmAnimalHousingEmissionSource convertToAnimalHousing(final T source) throws AeriusException {
    final FarmAnimalHousingEmissionSource animalHousingSource = new FarmAnimalHousingEmissionSource();
    for (final IsGmlProperty<IsGmlFarmLodging> lodging : source.getFarmLodgings()) {
      animalHousingSource.getSubSources().add(getAnimalHousing(lodging.getProperty(), source.getId()));
    }
    animalHousingSource.setEstablished(source.getEstablished());
    return animalHousingSource;
  }

  private FarmAnimalHousing getAnimalHousing(final IsGmlFarmLodging lodging, final String sourceId) throws AeriusException {
    final FarmAnimalHousing returnAnimalHousing;
    if (lodging instanceof final IsGmlCustomFarmLodging customLodging) {
      returnAnimalHousing = convertCustom(customLodging, sourceId);
    } else if (lodging instanceof final IsGmlStandardFarmLodging standardLodging) {
      returnAnimalHousing = convertStandard(standardLodging, sourceId);
    } else {
      LOG.error("Don't know how to treat lodging type: {}", lodging.getClass());
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR);
    }
    returnAnimalHousing.setNumberOfAnimals(lodging.getNumberOfAnimals());
    returnAnimalHousing.setNumberOfDays(lodging.getNumberOfDays());
    return returnAnimalHousing;
  }

  private CustomFarmAnimalHousing convertCustom(final IsGmlCustomFarmLodging customLodging, final String sourceId) throws AeriusException {
    final CustomFarmAnimalHousing customEmissions = new CustomFarmAnimalHousing();
    customEmissions.setAnimalTypeCode(customLodging.getAnimalCode() == null ? UNKNOWN_ANIMAL_TYPE_CODE : customLodging.getAnimalCode());
    customEmissions.setDescription(customLodging.getDescription());
    customEmissions.setFarmEmissionFactorType(determineEmissionFactorType(customLodging.getEmissionFactorType(), sourceId));
    for (final IsGmlProperty<IsGmlEmission> emissionProperty : customLodging.getEmissionFactors()) {
      final IsGmlEmission emission = emissionProperty.getProperty();
      customEmissions.getEmissionFactors().put(emission.getSubstance(), emission.getValue());
    }
    return customEmissions;
  }

  private FarmAnimalHousing convertStandard(final IsGmlStandardFarmLodging standardLodging, final String sourceId) {
    final FarmAnimalHousing converted;
    final String oldCode = standardLodging.getCode();

    final FarmLodgingConversion conversion = getConversionData().determineFarmLodgingConversion(oldCode);
    if (conversion == null) {
      converted = convertStandardWithoutConversion(standardLodging, sourceId);
    } else {
      converted = convertStandardWithConversion(conversion, standardLodging, sourceId);
    }
    converted.setNumberOfAnimals(standardLodging.getNumberOfAnimals());
    converted.setNumberOfDays(standardLodging.getNumberOfDays());

    return converted;
  }

  private CustomFarmAnimalHousing convertStandardWithoutConversion(
      final IsGmlStandardFarmLodging standardLodging, final String sourceId) {
    final CustomFarmAnimalHousing customEmissions = new CustomFarmAnimalHousing();
    // Not sure if this'll stick: relies on custom animal housing using the old AnimalType codes.
    customEmissions.setAnimalTypeCode(standardLodging.getCode() == null || standardLodging.getCode().length() <= 1
        ? UNKNOWN_ANIMAL_TYPE_CODE
        : standardLodging.getCode().substring(0, 1));
    customEmissions.setDescription("");
    customEmissions.setFarmEmissionFactorType(FarmEmissionFactorType.PER_ANIMAL_PER_YEAR);
    customEmissions.getEmissionFactors().put(Substance.NH3, 0.0);
    // Warn the user that this source has been converted to custom animal housing.
    addWarningForSource(ImaerExceptionReason.GML_CONVERTED_LODGING_TO_CUSTOM, sourceId, standardLodging);
    return customEmissions;
  }

  private StandardFarmAnimalHousing convertStandardWithConversion(final FarmLodgingConversion conversion,
      final IsGmlStandardFarmLodging standardLodging, final String sourceId) {
    final StandardFarmAnimalHousing standardEmissions = new StandardFarmAnimalHousing();
    standardEmissions.setAnimalTypeCode(conversion.getAnimalTypeCode());
    standardEmissions.setAnimalHousingCode(conversion.getAnimalHousingCode());
    if (conversion.getAdditionalSystemCode() != null) {
      final StandardAdditionalHousingSystem additionalSystem = new StandardAdditionalHousingSystem();
      additionalSystem.setAdditionalSystemCode(conversion.getAdditionalSystemCode());
      standardEmissions.getAdditionalSystems().add(additionalSystem);
    }
    if (!standardLodging.getLodgingSystems().isEmpty() || !standardLodging.getFodderMeasures().isEmpty()) {
      // We can't convert additional systems: no data available to do so.
      // Instead, add a specific warning that the user has to check additional systems for this source.
      addWarningForSource(ImaerExceptionReason.GML_CONVERTED_LODGING_WITH_SYSTEMS, sourceId, standardLodging);
    } else if (!conversion.getAnimalHousingCode().equals(standardLodging.getCode())) {
      // Warn the user that this source has been converted.
      addWarningForSource(ImaerExceptionReason.GML_CONVERTED_LODGING, sourceId, standardLodging);
    }
    return standardEmissions;
  }

  private void addWarningForSource(final ImaerExceptionReason reason, final String sourceId, final IsGmlStandardFarmLodging standardLodging) {
    getConversionData().getWarnings().add(new AeriusException(reason, sourceId, standardLodging.getCode()));
  }

  public FarmLodgingEmissionSource convertLegacy(final T source) throws AeriusException {
    final FarmLodgingEmissionSource emissionValues = new FarmLodgingEmissionSource();
    for (final IsGmlProperty<IsGmlFarmLodging> lodging : source.getFarmLodgings()) {
      emissionValues.getSubSources().add(getFarmLodging(lodging.getProperty(), source.getId()));
    }
    emissionValues.setEstablished(source.getEstablished());
    return emissionValues;
  }

  private FarmLodging getFarmLodging(final IsGmlFarmLodging lodging, final String sourceId) throws AeriusException {
    final FarmLodging returnLodging;
    if (lodging instanceof final IsGmlCustomFarmLodging customLodging) {
      returnLodging = convertCustomLegacy(customLodging, sourceId);
    } else if (lodging instanceof final IsGmlStandardFarmLodging standardLodging) {
      returnLodging = convertStandardLegacy(standardLodging);
    } else {
      LOG.error("Don't know how to treat lodging type: {}", lodging.getClass());
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR);
    }
    returnLodging.setNumberOfAnimals(lodging.getNumberOfAnimals());
    returnLodging.setNumberOfDays(lodging.getNumberOfDays());
    return returnLodging;
  }

  private CustomFarmLodging convertCustomLegacy(final IsGmlCustomFarmLodging customLodging, final String sourceId) throws AeriusException {
    final CustomFarmLodging customEmissions = new CustomFarmLodging();
    customEmissions.setAnimalCode(customLodging.getAnimalCode());
    customEmissions.setDescription(customLodging.getDescription());
    customEmissions.setFarmEmissionFactorType(determineEmissionFactorType(customLodging.getEmissionFactorType(), sourceId));
    for (final IsGmlProperty<IsGmlEmission> emissionProperty : customLodging.getEmissionFactors()) {
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

  private StandardFarmLodging convertStandardLegacy(final IsGmlStandardFarmLodging standardLodging) {
    final StandardFarmLodging standardEmissions = new StandardFarmLodging();
    final String categoryCode = standardLodging.getCode();

    standardEmissions.setFarmLodgingCode(categoryCode);
    handleLodgingSystems(standardLodging, standardEmissions);
    handleFodderMeasures(standardLodging, standardEmissions);

    //handle BWL code bit. Ït's OK if it's null.
    standardEmissions.setSystemDefinitionCode(standardLodging.getLodgingSystemDefinitionCode());

    return standardEmissions;
  }

  private void handleLodgingSystems(final IsGmlStandardFarmLodging lodging, final StandardFarmLodging standardEmissions) {
    for (final IsGmlProperty<IsGmlLodgingSystem> lodgingSystemProperty : lodging.getLodgingSystems()) {
      final IsGmlLodgingSystem lodgingSystem = lodgingSystemProperty.getProperty();
      if (lodgingSystem instanceof IsGmlAdditionalLodgingSystem) {
        final AdditionalLodgingSystem resultSystem = getAdditionalSystem((IsGmlAdditionalLodgingSystem) lodgingSystem);
        standardEmissions.getAdditionalLodgingSystems().add(resultSystem);
      } else if (lodgingSystem instanceof IsGmlReductiveLodgingSystem) {
        final ReductiveLodgingSystem resultSystem = getReductiveSystem((IsGmlReductiveLodgingSystem) lodgingSystem);
        standardEmissions.getReductiveLodgingSystems().add(resultSystem);
      }
    }
  }

  private void handleFodderMeasures(final IsGmlStandardFarmLodging lodging, final StandardFarmLodging standardEmissions) {
    for (final IsGmlProperty<IsGmlLodgingFodderMeasure> fodderMeasure : lodging.getFodderMeasures()) {
      final LodgingFodderMeasure resultSystem = getFodderMeasure(fodderMeasure.getProperty());
      standardEmissions.getFodderMeasures().add(resultSystem);
    }
  }

  private AdditionalLodgingSystem getAdditionalSystem(final IsGmlAdditionalLodgingSystem additionalSystem) {
    final AdditionalLodgingSystem resultSystem = new AdditionalLodgingSystem();
    resultSystem.setNumberOfAnimals(additionalSystem.getNumberOfAnimals());

    resultSystem.setLodgingSystemCode(additionalSystem.getCode());
    resultSystem.setSystemDefinitionCode(additionalSystem.getLodgingSystemDefinitionCode());
    return resultSystem;
  }

  private ReductiveLodgingSystem getReductiveSystem(final IsGmlReductiveLodgingSystem reductiveSystem) {
    final ReductiveLodgingSystem resultSystem = new ReductiveLodgingSystem();
    resultSystem.setLodgingSystemCode(reductiveSystem.getCode());
    resultSystem.setSystemDefinitionCode(reductiveSystem.getLodgingSystemDefinitionCode());
    return resultSystem;
  }

  private LodgingFodderMeasure getFodderMeasure(final IsGmlLodgingFodderMeasure fodderMeasure) {
    final LodgingFodderMeasure resultMeasure = new LodgingFodderMeasure();
    resultMeasure.setFodderMeasureCode(fodderMeasure.getCode());
    return resultMeasure;
  }

}
