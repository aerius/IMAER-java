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
package nl.overheid.aerius.gml.v4_0.togml;

import nl.overheid.aerius.gml.v4_0.base.ReferenceType;
import nl.overheid.aerius.gml.v4_0.source.characteristics.AbstractDiurnalVariation;
import nl.overheid.aerius.gml.v4_0.source.characteristics.AbstractHeatContent;
import nl.overheid.aerius.gml.v4_0.source.characteristics.CalculatedHeatContent;
import nl.overheid.aerius.gml.v4_0.source.characteristics.EmissionSourceCharacteristics;
import nl.overheid.aerius.gml.v4_0.source.characteristics.ReferenceDiurnalVariation;
import nl.overheid.aerius.gml.v4_0.source.characteristics.SpecifiedHeatContent;
import nl.overheid.aerius.gml.v4_0.source.characteristics.StandardDiurnalVariation;
import nl.overheid.aerius.shared.domain.ops.OutflowDirectionType;
import nl.overheid.aerius.shared.domain.ops.OutflowVelocityType;
import nl.overheid.aerius.shared.domain.v2.characteristics.HeatContentType;
import nl.overheid.aerius.shared.domain.v2.characteristics.OPSSourceCharacteristics;
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
    AbstractDiurnalVariation gmlVariation = null;
    if (characteristics.getCustomDiurnalVariationId() != null) {
      final ReferenceDiurnalVariation referenceVariation = new ReferenceDiurnalVariation();
      referenceVariation.setCustomDiurnalVariation(toReferenceType(characteristics.getCustomDiurnalVariationId()));
      gmlVariation = referenceVariation;
    } else if (characteristics.getDiurnalVariation() != null) {
      final StandardDiurnalVariation standardVariation = new StandardDiurnalVariation();
      standardVariation.setCode(characteristics.getDiurnalVariation().getCode());
      gmlVariation = standardVariation;
    }
    return gmlVariation;
  }

  private static ReferenceType determineBuilding(final OPSSourceCharacteristics characteristics) {
    final String validBuildingId = characteristics.getBuildingId() == null
        ? null
        : GMLIdUtil.toValidGmlId(characteristics.getBuildingId(), GMLIdUtil.BUILDING_PREFIX);
    return toReferenceType(validBuildingId);
  }

  private static ReferenceType toReferenceType(final String id) {
    final ReferenceType reference;
    if (id == null) {
      reference = null;
    } else {
      reference = new ReferenceType(null);
      reference.setHref("#" + id);
    }
    return reference;
  }

}
