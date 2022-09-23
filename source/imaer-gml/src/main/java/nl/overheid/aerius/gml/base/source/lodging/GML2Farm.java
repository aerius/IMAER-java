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
import nl.overheid.aerius.gml.base.source.IsGmlEmission;
import nl.overheid.aerius.shared.domain.v2.source.FarmLodgingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.farm.AdditionalLodgingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.CustomFarmLodging;
import nl.overheid.aerius.shared.domain.v2.source.farm.FarmLodging;
import nl.overheid.aerius.shared.domain.v2.source.farm.LodgingFodderMeasure;
import nl.overheid.aerius.shared.domain.v2.source.farm.ReductiveLodgingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.StandardFarmLodging;
import nl.overheid.aerius.shared.emissions.FarmEmissionFactorType;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
public class GML2Farm<T extends IsGmlFarmLodgingEmissionSource> extends AbstractGML2Specific<T, FarmLodgingEmissionSource> {

  private static final Logger LOG = LoggerFactory.getLogger(GML2Farm.class);

  public GML2Farm(final GMLConversionData conversionData) {
    super(conversionData);
  }

  @Override
  public FarmLodgingEmissionSource convert(final T source) throws AeriusException {
    final FarmLodgingEmissionSource emissionValues = new FarmLodgingEmissionSource();
    for (final IsGmlProperty<IsGmlFarmLodging> lodging : source.getFarmLodgings()) {
      emissionValues.getSubSources().add(getFarmLodging(lodging.getProperty(), source.getId()));
    }
    emissionValues.setEstablished(source.getEstablished());
    return emissionValues;
  }

  private FarmLodging getFarmLodging(final IsGmlFarmLodging lodging, final String sourceId) throws AeriusException {
    final FarmLodging returnLodging;
    if (lodging instanceof IsGmlCustomFarmLodging) {
      returnLodging = convertCustom((IsGmlCustomFarmLodging) lodging, sourceId);
    } else if (lodging instanceof IsGmlStandardFarmLodging) {
      returnLodging = convertStandard((IsGmlStandardFarmLodging) lodging);
    } else {
      LOG.error("Don't know how to treat lodging type: {}", lodging.getClass());
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR);
    }
    returnLodging.setNumberOfAnimals(lodging.getNumberOfAnimals());
    returnLodging.setNumberOfDays(lodging.getNumberOfDays());
    return returnLodging;
  }

  private CustomFarmLodging convertCustom(final IsGmlCustomFarmLodging customLodging, final String sourceId) throws AeriusException {
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
      throw new AeriusException(ImaerExceptionReason.GML_UNKNOWN_FARMLAND_ACTIVITY_CODE, sourceId, gmlEmissionFactorType);
    }

    return type;
  }

  private StandardFarmLodging convertStandard(final IsGmlStandardFarmLodging standardLodging) {
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
