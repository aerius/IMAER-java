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

import nl.overheid.aerius.gml.v5_0.base.ReferenceType;
import nl.overheid.aerius.gml.v5_0.source.characteristics.AbstractDiurnalVariation;
import nl.overheid.aerius.gml.v5_0.source.characteristics.AbstractHeatContent;
import nl.overheid.aerius.gml.v5_0.source.characteristics.CalculatedHeatContent;
import nl.overheid.aerius.gml.v5_0.source.characteristics.EmissionSourceCharacteristics;
import nl.overheid.aerius.gml.v5_0.source.characteristics.SpecifiedHeatContent;
import nl.overheid.aerius.shared.domain.ops.OutflowDirectionType;
import nl.overheid.aerius.shared.domain.ops.OutflowVelocityType;
import nl.overheid.aerius.shared.domain.v2.characteristics.ADMSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.characteristics.HeatContentType;
import nl.overheid.aerius.shared.domain.v2.characteristics.OPSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.characteristics.SourceCharacteristics;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.util.gml.GMLIdUtil;

/**
 *
 */
final class SourceCharacteristics2GML {

  private SourceCharacteristics2GML() {
    //util class.
  }

  static EmissionSourceCharacteristics toGML(
      final OPSSourceCharacteristics characteristics, final boolean includeOptionals) {
    final EmissionSourceCharacteristics returnCharacteristics = new EmissionSourceCharacteristics();
    returnCharacteristics.setHeatContent(determineHeatContent(characteristics));
    returnCharacteristics.setEmissionHeight(characteristics.getEmissionHeight());
    returnCharacteristics.setSpread(characteristics.getSpread());
    if (includeOptionals) {
      returnCharacteristics.setDiurnalVariation(determineDiurnalVariation(characteristics));
    }
    returnCharacteristics.setBuilding(determineBuilding(characteristics));
    return returnCharacteristics;
  }

  private static AbstractHeatContent determineHeatContent(final OPSSourceCharacteristics characteristics) {
    if (characteristics.getHeatContentType() == HeatContentType.FORCED) {
      final CalculatedHeatContent heatContent = new CalculatedHeatContent();
      heatContent.setEmissionTemperature(characteristics.getEmissionTemperature());
      heatContent.setOutflowDiameter(characteristics.getOutflowDiameter());
      heatContent.setOutflowVelocity(characteristics.getOutflowVelocity());
      heatContent.setOutflowDirection(characteristics.getOutflowDirection() == null
          ? OutflowDirectionType.VERTICAL
          : characteristics.getOutflowDirection());
      heatContent.setOutflowVelocityType(characteristics.getOutflowVelocityType() == null
          ? OutflowVelocityType.ACTUAL_FLOW
          : characteristics.getOutflowVelocityType());
      return heatContent;
    } else {
      final SpecifiedHeatContent heatContent = new SpecifiedHeatContent();
      heatContent.setValue(characteristics.getHeatContent() == null
          ? 0
          : characteristics.getHeatContent());
      return heatContent;

    }
  }

  private static AbstractDiurnalVariation determineDiurnalVariation(final OPSSourceCharacteristics characteristics) {
    return ToGMLUtil.determineDiurnalVariation(
        characteristics::getCustomDiurnalVariationId,
        () -> characteristics.getDiurnalVariation() == null ? null : characteristics.getDiurnalVariation().getCode());
  }

  static nl.overheid.aerius.gml.v5_0.source.characteristics.ADMSSourceCharacteristics toGML(
      final ADMSSourceCharacteristics characteristics) throws AeriusException {
    final nl.overheid.aerius.gml.v5_0.source.characteristics.ADMSSourceCharacteristics returnCharacteristics =
        new nl.overheid.aerius.gml.v5_0.source.characteristics.ADMSSourceCharacteristics();
    returnCharacteristics.setBuilding(determineBuilding(characteristics));
    returnCharacteristics.setHeight(characteristics.getHeight());
    returnCharacteristics.setSpecificHeatCapacity(characteristics.getSpecificHeatCapacity());

    determineSourceType(characteristics, returnCharacteristics);
    determineBuoyancyType(characteristics, returnCharacteristics);
    determineEffluxType(characteristics, returnCharacteristics);
    returnCharacteristics.setDiurnalVariation(determineDiurnalVariation(characteristics));

    return returnCharacteristics;
  }

  private static void determineSourceType(final ADMSSourceCharacteristics characteristics,
      final nl.overheid.aerius.gml.v5_0.source.characteristics.ADMSSourceCharacteristics returnCharacteristics) {
    returnCharacteristics.setSourceType(characteristics.getSourceType());
    switch (characteristics.getSourceType()) {
    case JET:
      returnCharacteristics.setElevationAngle(characteristics.getElevationAngle());
      returnCharacteristics.setHorizontalAngle(characteristics.getHorizontalAngle());
      // Intentional fallthrough
    case POINT:
      returnCharacteristics.setDiameter(characteristics.getDiameter());
      break;
    case AREA:
      break;
    case LINE:
    case ROAD:
      returnCharacteristics.setWidth(characteristics.getWidth());
      break;
    case VOLUME:
      returnCharacteristics.setVerticalDimension(characteristics.getVerticalDimension());
      break;
    }
  }

  private static void determineBuoyancyType(final ADMSSourceCharacteristics characteristics,
      final nl.overheid.aerius.gml.v5_0.source.characteristics.ADMSSourceCharacteristics returnCharacteristics) {
    returnCharacteristics.setBuoyancyType(characteristics.getBuoyancyType());
    switch (characteristics.getBuoyancyType()) {
    case AMBIENT:
      break;
    case DENSITY:
      returnCharacteristics.setDensity(characteristics.getDensity());
      break;
    case TEMPERATURE:
      returnCharacteristics.setTemperature(characteristics.getTemperature());
      break;
    }
  }

  private static void determineEffluxType(final ADMSSourceCharacteristics characteristics,
      final nl.overheid.aerius.gml.v5_0.source.characteristics.ADMSSourceCharacteristics returnCharacteristics) throws AeriusException {
    returnCharacteristics.setEffluxType(characteristics.getEffluxType());
    switch (characteristics.getEffluxType()) {
    case VELOCITY:
      returnCharacteristics.setVerticalVelocity(characteristics.getVerticalVelocity());
      break;
    case VOLUME:
      returnCharacteristics.setVolumetricFlowRate(characteristics.getVolumetricFlowRate());
      break;
    case MASS:
      returnCharacteristics.setMassFlux(characteristics.getMassFlux());
      break;
    case MOMENTUM:
      // Not (yet) supported
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR);
    }
  }

  private static AbstractDiurnalVariation determineDiurnalVariation(final ADMSSourceCharacteristics characteristics) {
    return ToGMLUtil.determineDiurnalVariation(
        characteristics::getCustomDiurnalVariationId,
        characteristics::getStandardDiurnalVariationCode);
  }

  private static ReferenceType determineBuilding(final SourceCharacteristics characteristics) {
    return ToGMLUtil.toReferenceType(characteristics.getBuildingId(), GMLIdUtil.BUILDING_PREFIX);
  }

}
