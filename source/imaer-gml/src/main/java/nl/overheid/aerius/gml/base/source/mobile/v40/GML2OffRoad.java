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
package nl.overheid.aerius.gml.base.source.mobile.v40;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.overheid.aerius.gml.base.AbstractGML2Specific;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.characteristics.GML2SourceCharacteristics;
import nl.overheid.aerius.gml.base.geo.GML2Geometry;
import nl.overheid.aerius.gml.base.source.IsGmlEmission;
import nl.overheid.aerius.shared.domain.v2.source.OffRoadMobileEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.offroad.CustomOffRoadMobileSource;
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
        emissionSource.getSubSources().add(convert((IsGmlCustomOffRoadMobileSource) offRoadMobileSource));
      } else {
        LOG.error("Don't know how to treat offroad mobile source type: {}", offRoadMobileSource.getClass());
        throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR);
      }
    }

    return emissionSource.getSubSources().isEmpty() ? null : emissionSource;
  }

  private OffRoadMobileSource convert(final IsGmlStandardOffRoadMobileSource mobileSource) {
    final StandardOffRoadMobileSource vehicleEmissionValues = new StandardOffRoadMobileSource();
    vehicleEmissionValues.setDescription(mobileSource.getDescription());
    vehicleEmissionValues.setLiterFuelPerYear(mobileSource.getLiterFuelPerYear());
    vehicleEmissionValues.setOffRoadMobileSourceCode(mobileSource.getCode());
    vehicleEmissionValues.setOperatingHoursPerYear(mobileSource.getOperatingHoursPerYear());
    vehicleEmissionValues.setLiterAdBluePerYear(mobileSource.getLiterAdBluePerYear());

    return vehicleEmissionValues;
  }

  private OffRoadMobileSource convert(final IsGmlCustomOffRoadMobileSource customMobileSource) throws AeriusException {
    final CustomOffRoadMobileSource customVehicleEmissionValues = new CustomOffRoadMobileSource();
    customVehicleEmissionValues.setDescription(customMobileSource.getDescription());
    customVehicleEmissionValues.setCharacteristics(gml2SourceCharacteristics.fromGML(customMobileSource.getCharacteristics(),
        null, null));

    for (final IsGmlProperty<IsGmlEmission> emissionProperty : customMobileSource.getEmissions()) {
      final IsGmlEmission emission = emissionProperty.getProperty();
      customVehicleEmissionValues.getEmissions().put(emission.getSubstance(), emission.getValue());
    }

    return customVehicleEmissionValues;
  }

}
