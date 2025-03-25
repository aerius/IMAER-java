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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
public class GML2Farm<T extends IsGmlFarmLodgingEmissionSource> extends AbstractGML2Specific<T, EmissionSource> {

  private static final Logger LOG = LoggerFactory.getLogger(GML2Farm.class);

  private static final String UNKNOWN_ANIMAL_TYPE_CODE = "";

  public GML2Farm(final GMLConversionData conversionData) {
    super(conversionData);
  }

  @Override
  public EmissionSource convert(final T source) throws AeriusException {
    final FarmAnimalHousingEmissionSource animalHousingSource = new FarmAnimalHousingEmissionSource();
    for (final IsGmlProperty<IsGmlFarmLodging> lodging : source.getFarmLodgings()) {
      animalHousingSource.getSubSources().add(getAnimalHousing(lodging.getProperty(), source));
    }
    animalHousingSource.setEstablished(source.getEstablished());
    return animalHousingSource;
  }

  private FarmAnimalHousing getAnimalHousing(final IsGmlFarmLodging lodging, final T source) throws AeriusException {
    final FarmAnimalHousing returnAnimalHousing;
    if (lodging instanceof final IsGmlCustomFarmLodging customLodging) {
      returnAnimalHousing = convertCustom(customLodging, source);
    } else if (lodging instanceof final IsGmlStandardFarmLodging standardLodging) {
      returnAnimalHousing = convertStandard(standardLodging, source);
    } else {
      LOG.error("Don't know how to treat lodging type: {}", lodging.getClass());
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR);
    }
    returnAnimalHousing.setNumberOfAnimals(lodging.getNumberOfAnimals());
    returnAnimalHousing.setNumberOfDays(lodging.getNumberOfDays());
    return returnAnimalHousing;
  }

  private CustomFarmAnimalHousing convertCustom(final IsGmlCustomFarmLodging customLodging, final T source) throws AeriusException {
    final CustomFarmAnimalHousing customEmissions = new CustomFarmAnimalHousing();
    customEmissions.setAnimalTypeCode(customLodging.getAnimalCode() == null ? UNKNOWN_ANIMAL_TYPE_CODE : customLodging.getAnimalCode());
    customEmissions.setDescription(customLodging.getDescription());
    customEmissions.setFarmEmissionFactorType(determineEmissionFactorType(customLodging.getEmissionFactorType(), source.getId()));
    for (final IsGmlProperty<IsGmlEmission> emissionProperty : customLodging.getEmissionFactors()) {
      final IsGmlEmission emission = emissionProperty.getProperty();
      customEmissions.getEmissionFactors().put(emission.getSubstance(), emission.getValue());
    }
    return customEmissions;
  }

  private FarmAnimalHousing convertStandard(final IsGmlStandardFarmLodging standardLodging, final T source) {
    final FarmAnimalHousing converted;
    final String oldCode = standardLodging.getCode();

    final FarmLodgingConversion conversion = getConversionData().determineFarmLodgingConversion(oldCode);
    if (conversion == null) {
      converted = convertStandardWithoutConversion(standardLodging, source);
    } else {
      converted = convertStandardWithConversion(conversion, standardLodging, source);
    }
    converted.setNumberOfAnimals(standardLodging.getNumberOfAnimals());
    converted.setNumberOfDays(standardLodging.getNumberOfDays());

    return converted;
  }

  private CustomFarmAnimalHousing convertStandardWithoutConversion(
      final IsGmlStandardFarmLodging standardLodging, final T source) {
    final CustomFarmAnimalHousing customEmissions = new CustomFarmAnimalHousing();
    // Not sure if this'll stick: relies on custom animal housing using the old AnimalType codes.
    customEmissions.setAnimalTypeCode(standardLodging.getCode() == null || standardLodging.getCode().length() <= 1
        ? UNKNOWN_ANIMAL_TYPE_CODE
        : standardLodging.getCode().substring(0, 1));
    final List<String> descriptionParts = new ArrayList<>();
    Optional.ofNullable(standardLodging.getCode()).ifPresent(descriptionParts::add);
    Optional.ofNullable(standardLodging.getLodgingSystemDefinitionCode()).ifPresent(descriptionParts::add);
    customEmissions.setDescription(String.join(", ", descriptionParts));
    customEmissions.setFarmEmissionFactorType(FarmEmissionFactorType.PER_ANIMAL_PER_YEAR);
    customEmissions.getEmissionFactors().put(Substance.NH3, 0.0);
    // Warn the user that this source has been converted to custom animal housing.
    addWarningForSource(ImaerExceptionReason.GML_CONVERTED_LODGING_TO_CUSTOM, source, standardLodging);
    return customEmissions;
  }

  private StandardFarmAnimalHousing convertStandardWithConversion(final FarmLodgingConversion conversion,
      final IsGmlStandardFarmLodging standardLodging, final T source) {
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
      addWarningForSource(ImaerExceptionReason.GML_CONVERTED_LODGING_WITH_SYSTEMS, source, standardLodging);
    } else if (!conversion.getAnimalHousingCode().equals(standardLodging.getCode())) {
      // Warn the user that this source has been converted.
      addWarningForSource(ImaerExceptionReason.GML_CONVERTED_LODGING, source, standardLodging);
    }
    return standardEmissions;
  }

  private void addWarningForSource(final ImaerExceptionReason reason, final T source, final IsGmlStandardFarmLodging standardLodging) {
    getConversionData().getWarnings().add(new AeriusException(reason, source.getId(), safe(source.getLabel()),
        standardLodging.getCode(), safe(standardLodging.getLodgingSystemDefinitionCode())));
  }

  private String safe(final String value) {
    return value == null ? "" : value;
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

}
