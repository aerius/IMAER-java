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
package nl.overheid.aerius.gml.base.characteristics;

import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.shared.domain.v2.characteristics.ADMSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.characteristics.adms.ADMSLimits;
import nl.overheid.aerius.shared.domain.v2.characteristics.adms.BuoyancyType;
import nl.overheid.aerius.shared.domain.v2.characteristics.adms.EffluxType;
import nl.overheid.aerius.shared.domain.v2.characteristics.adms.ReleaseTemperatureAndPressure;
import nl.overheid.aerius.shared.domain.v2.characteristics.adms.SourceType;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * GML source characteristics with OPS specific parameters conversion.
 */
public class GML2ADMSSourceCharacteristics
    extends GML2SourceCharacteristics<nl.overheid.aerius.shared.domain.v2.characteristics.ADMSSourceCharacteristics> {

  public GML2ADMSSourceCharacteristics(final GMLConversionData conversionData) {
    super(conversionData);
  }

  @Override
  protected nl.overheid.aerius.shared.domain.v2.characteristics.ADMSSourceCharacteristics fromGMLSpecific(
      final IsGmlSourceCharacteristics characteristics,
      final nl.overheid.aerius.shared.domain.v2.characteristics.ADMSSourceCharacteristics sectorCharacteristics) throws AeriusException {
    final IsGmlADMSSourceCharacteristics gmlOPSCharacteristics = (IsGmlADMSSourceCharacteristics) characteristics;
    final ADMSSourceCharacteristics returnCharacteristics = getDefaultCharacteristics();
    returnCharacteristics.setHeight(gmlOPSCharacteristics.getHeight());
    returnCharacteristics.setSpecificHeatCapacity(gmlOPSCharacteristics.getSpecificHeatCapacity());

    setSourceTypeProperties(gmlOPSCharacteristics, returnCharacteristics);
    setBuoyancyTypeProperties(gmlOPSCharacteristics, returnCharacteristics);
    setEffluxTypeProperties(gmlOPSCharacteristics, returnCharacteristics);

    return returnCharacteristics;
  }

  private void setSourceTypeProperties(final IsGmlADMSSourceCharacteristics gmlOPSCharacteristics,
      final ADMSSourceCharacteristics returnCharacteristics) {
    returnCharacteristics.setSourceType(gmlOPSCharacteristics.getSourceType());
    switch (gmlOPSCharacteristics.getSourceType()) {
    case JET:
      returnCharacteristics.setElevationAngle(gmlOPSCharacteristics.getElevationAngle());
      returnCharacteristics.setHorizontalAngle(gmlOPSCharacteristics.getHorizontalAngle());
      // Intentional fallthrough
    case POINT:
      returnCharacteristics.setDiameter(gmlOPSCharacteristics.getDiameter());
      break;
    case AREA:
      break;
    case LINE:
    case ROAD:
      returnCharacteristics.setWidth(gmlOPSCharacteristics.getWidth());
      break;
    case VOLUME:
      returnCharacteristics.setVerticalDimension(gmlOPSCharacteristics.getVerticalDimension());
      break;
    }
  }

  private void setBuoyancyTypeProperties(final IsGmlADMSSourceCharacteristics gmlOPSCharacteristics,
      final ADMSSourceCharacteristics returnCharacteristics) {
    returnCharacteristics.setBuoyancyType(gmlOPSCharacteristics.getBuoyancyType());
    switch (gmlOPSCharacteristics.getBuoyancyType()) {
    case AMBIENT:
      break;
    case DENSITY:
      returnCharacteristics.setDensity(gmlOPSCharacteristics.getDensity());
      break;
    case TEMPERATURE:
      returnCharacteristics.setTemperature(gmlOPSCharacteristics.getTemperature());
      break;
    }
  }

  private void setEffluxTypeProperties(final IsGmlADMSSourceCharacteristics gmlOPSCharacteristics,
      final ADMSSourceCharacteristics returnCharacteristics) throws AeriusException {
    returnCharacteristics.setEffluxType(gmlOPSCharacteristics.getEffluxType());
    switch (gmlOPSCharacteristics.getEffluxType()) {
    case VELOCITY:
      returnCharacteristics.setVerticalVelocity(gmlOPSCharacteristics.getVerticalVelocity());
      break;
    case VOLUME:
      returnCharacteristics.setVolumetricFlowRate(gmlOPSCharacteristics.getVolumetricFlowRate());
      break;
    case MASS:
      returnCharacteristics.setMassFlux(gmlOPSCharacteristics.getMassFlux());
      break;
    case MOMENTUM:
      // Not (yet) supported
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR);
    }
  }

  private static ADMSSourceCharacteristics getDefaultCharacteristics() {
    final ADMSSourceCharacteristics characteristics = new ADMSSourceCharacteristics();
    characteristics.setSourceType(SourceType.POINT);
    characteristics.setBuoyancyType(BuoyancyType.TEMPERATURE);
    characteristics.setEffluxType(EffluxType.VELOCITY);
    characteristics.setReleaseTemperatureAndPressure(ReleaseTemperatureAndPressure.NTP);
    characteristics.setHeight(ADMSLimits.SOURCE_HEIGHT_MINIMUM);
    characteristics.setWidth(ADMSLimits.SOURCE_WIDTH_INITIAL);
    characteristics.setDiameter(ADMSLimits.SOURCE_HEIGHT_MINIMUM);
    characteristics.setTemperature(ADMSLimits.SOURCE_TEMPERATURE_DEFAULT);
    characteristics.setDensity(ADMSLimits.SOURCE_DENSITY_DEFAULT);
    characteristics.setVerticalVelocity(ADMSLimits.SOURCE_VERTICAL_VELOCITY_DEFAULT);
    characteristics.setVolumetricFlowRate(ADMSLimits.SOURCE_VOLUMETRIC_FLOW_RATE_DEFAULT);
    characteristics.setSpecificHeatCapacity(ADMSLimits.SOURCE_SPECIFIC_HEAT_CAPACITY_DEFAULT);
    characteristics.setPercentNOxAsNO2(0.0);
    characteristics.setMomentumFlux(ADMSLimits.SOURCE_MOMENTUM_FLUX_DEFAULT);
    characteristics.setBuoyancyFlux(ADMSLimits.SOURCE_BUOYANCY_FLUX_DEFAULT);
    characteristics.setMassFlux(ADMSLimits.SOURCE_MASS_FLUX_DEFAULT);
    characteristics.setVerticalDimension(ADMSLimits.SOURCE_L1_DEFAULT);
    characteristics.setElevationAngle(ADMSLimits.ELEVATION_ANGLE_DEFAULT);
    characteristics.setHorizontalAngle(ADMSLimits.HORIZONTAL_ANGLE_DEFAULT);
    return characteristics;
  }

}
