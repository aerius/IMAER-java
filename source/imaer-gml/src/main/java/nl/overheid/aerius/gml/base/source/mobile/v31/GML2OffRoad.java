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
package nl.overheid.aerius.gml.base.source.mobile.v31;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.overheid.aerius.gml.base.AbstractGML2Specific;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.GMLLegacyCodeConverter.GMLLegacyCodeType;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.characteristics.GML2SourceCharacteristics;
import nl.overheid.aerius.gml.base.geo.GML2Geometry;
import nl.overheid.aerius.gml.base.source.IsGmlEmission;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.domain.v2.source.GenericEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.OffRoadMobileEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.offroad.OffRoadMobileSource;
import nl.overheid.aerius.shared.domain.v2.source.offroad.StandardOffRoadMobileSource;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
public class GML2OffRoad<T extends IsGmlOffRoadMobileEmissionSource> extends AbstractGML2Specific<T, OffRoadMobileEmissionSource> {

  private static final Logger LOG = LoggerFactory.getLogger(GML2OffRoad.class);

  private final GML2SourceCharacteristics gml2SourceCharacteristics;
  private final GML2Geometry gml2Geometry;

  /**
   * @param conversionData The conversionData to use.
   */
  public GML2OffRoad(final GMLConversionData conversionData, final GML2SourceCharacteristics gml2SourceCharacteristics) {
    super(conversionData);
    this.gml2SourceCharacteristics = gml2SourceCharacteristics;
    this.gml2Geometry = new GML2Geometry(conversionData.getSrid());
  }

  @Override
  public OffRoadMobileEmissionSource convert(final T source) throws AeriusException {
    final OffRoadMobileEmissionSource emissionSource = new OffRoadMobileEmissionSource();

    for (final IsGmlProperty<IsGmlOffRoadMobileSource> offRoadMobileSourceProperty : source.getOffRoadMobileSources()) {
      final IsGmlOffRoadMobileSource offRoadMobileSource = offRoadMobileSourceProperty.getProperty();
      if (offRoadMobileSource instanceof IsGmlStandardOffRoadMobileSource) {
        emissionSource.getSubSources().add(convert((IsGmlStandardOffRoadMobileSource) offRoadMobileSource));
      } else if (offRoadMobileSource instanceof IsGmlCustomOffRoadMobileSource) {
        convert(source, (IsGmlCustomOffRoadMobileSource) offRoadMobileSource,
            source.getOffRoadMobileSources().indexOf(offRoadMobileSourceProperty));
      } else {
        LOG.error("Don't know how to treat offroad mobile source type: {}", offRoadMobileSource.getClass());
        throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR);
      }
    }

    // If all subsources were custom, no subsources will be left and there is no point in returning the emission source.
    return emissionSource.getSubSources().isEmpty() ? null : emissionSource;
  }

  private OffRoadMobileSource convert(final IsGmlStandardOffRoadMobileSource mobileSource) {
    final StandardOffRoadMobileSource vehicleEmissionValues = new StandardOffRoadMobileSource();
    vehicleEmissionValues.setDescription(mobileSource.getDescription());
    vehicleEmissionValues.setLiterFuelPerYear(mobileSource.getLiterFuelPerYear());
    final String oldCode = mobileSource.getCode();
    final String newCode = getConversionData().getCode(GMLLegacyCodeType.OFF_ROAD_MOBILE_SOURCE, oldCode, mobileSource.getDescription());
    vehicleEmissionValues.setOffRoadMobileSourceCode(newCode);
    if (!oldCode.equals(newCode)) {
      // If the code was converted, that means we can convert from the old properties to the new properties
      // In theory this should be for all codes, but there's a possibility that the code supplied was incorrect in the first place.
      final int estimatedOperatingHours = getConversionData().estimageOffRoadOperatingHours(oldCode, mobileSource.getLiterFuelPerYear());
      vehicleEmissionValues.setOperatingHoursPerYear(estimatedOperatingHours);
      vehicleEmissionValues.setLiterAdBluePerYear(0);
    }

    return vehicleEmissionValues;
  }

  private void convert(final T source, final IsGmlCustomOffRoadMobileSource customMobileSource, final int index) throws AeriusException {
    final GenericEmissionSource newSource = new GenericEmissionSource();
    newSource.setGmlId(source.getId() + "_" + index);
    final int sectorId = getConversionData().getSectorId(source.getSectorId(), source.getLabel());
    newSource.setSectorId(sectorId);
    newSource.setLabel(constructLabel(source.getLabel(), customMobileSource.getDescription()));
    newSource.setCharacteristics(gml2SourceCharacteristics.fromGML(customMobileSource.getCharacteristics(),
        getConversionData().determineDefaultCharacteristicsBySectorId(sectorId), null));
    for (final IsGmlProperty<IsGmlEmission> emissionProperty : customMobileSource.getEmissions()) {
      final IsGmlEmission emission = emissionProperty.getProperty();
      newSource.getEmissions().put(emission.getSubstance(), emission.getValue());
    }
    final EmissionSourceFeature feature = new EmissionSourceFeature();
    feature.setProperties(newSource);
    feature.setGeometry(gml2Geometry.getGeometry(source));
    getConversionData().getExtraSources().add(feature);
  }

  private String constructLabel(final String sourceLabel, final String subSourceDescription) {
    return constructLabelOf(sourceLabel, subSourceDescription);
  }

}
