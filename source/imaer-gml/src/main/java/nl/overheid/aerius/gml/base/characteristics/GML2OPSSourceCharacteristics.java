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
import nl.overheid.aerius.shared.domain.ops.DiurnalVariation;
import nl.overheid.aerius.shared.domain.v2.characteristics.HeatContentType;
import nl.overheid.aerius.shared.domain.v2.characteristics.OPSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * GML source characteristics with OPS specific parameters conversion.
 */
public class GML2OPSSourceCharacteristics extends GML2SourceCharacteristics<OPSSourceCharacteristics> {

  public GML2OPSSourceCharacteristics(final GMLConversionData conversionData) {
    super(conversionData);
  }

  @Override
  protected OPSSourceCharacteristics fromGMLSpecific(final IsBaseGmlEmissionSourceCharacteristics characteristics,
      final OPSSourceCharacteristics sectorCharacteristics, final Geometry geometry) throws AeriusException {
    final OPSSourceCharacteristics defaultCharacteristics = sectorCharacteristics == null ? new OPSSourceCharacteristics() : sectorCharacteristics;
    final OPSSourceCharacteristics returnCharacteristics = new OPSSourceCharacteristics();
    returnCharacteristics.setEmissionHeight(characteristics.getEmissionHeight());
    returnCharacteristics.setSpread(getOrDefault(characteristics.getSpread(), defaultCharacteristics.getSpread()));
    returnCharacteristics.setParticleSizeDistribution(defaultCharacteristics.getParticleSizeDistribution());

    fromGMLToDiurnalVariation(characteristics, returnCharacteristics, defaultCharacteristics, geometry);
    fromGMLToHeatContent(characteristics, returnCharacteristics);

    return returnCharacteristics;
  }

  private void fromGMLToDiurnalVariation(final IsBaseGmlEmissionSourceCharacteristics characteristics,
      final OPSSourceCharacteristics returnCharacteristics, final OPSSourceCharacteristics defaultCharacteristics, final Geometry geometry)
          throws AeriusException {
    if (characteristics instanceof IsGmlEmissionSourceCharacteristicsV31) {
      fromGMLToDiurnalVariation((IsGmlEmissionSourceCharacteristicsV31) characteristics, returnCharacteristics, defaultCharacteristics);
    } else if (characteristics instanceof IsGmlEmissionSourceCharacteristics) {
      fromGMLToDiurnalVariation((IsGmlEmissionSourceCharacteristics) characteristics, returnCharacteristics, defaultCharacteristics);
    }
  }

  private void fromGMLToDiurnalVariation(final IsGmlEmissionSourceCharacteristicsV31 characteristics,
      final OPSSourceCharacteristics returnCharacteristics, final OPSSourceCharacteristics defaultCharacteristics) {
    if (characteristics.getDiurnalVariation() == null) {
      returnCharacteristics.setDiurnalVariation(defaultCharacteristics.getDiurnalVariation());
    } else {
      final String diurnalVariationCode = characteristics.getDiurnalVariation();
      returnCharacteristics.setDiurnalVariation(DiurnalVariation.safeValueOf(diurnalVariationCode));
    }
  }

  private void fromGMLToDiurnalVariation(final IsGmlEmissionSourceCharacteristics characteristics,
      final OPSSourceCharacteristics returnCharacteristics, final OPSSourceCharacteristics defaultCharacteristics) {
    if (characteristics.getDiurnalVariation() == null) {
      returnCharacteristics.setDiurnalVariation(defaultCharacteristics.getDiurnalVariation());
    } else if (characteristics.getDiurnalVariation() instanceof IsGmlStandardDiurnalVariation) {
      final String diurnalVariationCode = ((IsGmlStandardDiurnalVariation) characteristics.getDiurnalVariation()).getCode();
      returnCharacteristics.setDiurnalVariation(DiurnalVariation.safeValueOf(diurnalVariationCode));
    } else if (characteristics.getDiurnalVariation() instanceof IsGmlReferenceDiurnalVariation) {
      final IsGmlReferenceDiurnalVariation gmlReferenceDiurnalVariation = (IsGmlReferenceDiurnalVariation) characteristics.getDiurnalVariation();
      returnCharacteristics.setCustomDiurnalVariationId(gmlReferenceDiurnalVariation.getCustomDiurnalVariation().getReferredId());
    }
  }

  private void fromGMLToHeatContent(final IsBaseGmlEmissionSourceCharacteristics characteristics,
      final OPSSourceCharacteristics returnCharacteristics) {
    final IsGmlHeatContent gmlHeatContent = characteristics.getHeatContent();
    if (gmlHeatContent instanceof IsGmlSpecifiedHeatContent) {
      returnCharacteristics.setHeatContentType(HeatContentType.NOT_FORCED);
      returnCharacteristics.setHeatContent(((IsGmlSpecifiedHeatContent) gmlHeatContent).getValue());
    } else if (gmlHeatContent instanceof IsGmlCalculatedHeatContent) {
      returnCharacteristics.setHeatContentType(HeatContentType.FORCED);
      final IsGmlCalculatedHeatContent calculatedHeatContent = (IsGmlCalculatedHeatContent) gmlHeatContent;
      fromGmlToEmissionTemperature(returnCharacteristics, calculatedHeatContent);
      returnCharacteristics.setOutflowDiameter(calculatedHeatContent.getOutflowDiameter());
      returnCharacteristics.setOutflowVelocity(calculatedHeatContent.getOutflowVelocity());
      returnCharacteristics.setOutflowDirection(calculatedHeatContent.getOutflowDirection());
      returnCharacteristics.setOutflowVelocityType(calculatedHeatContent.getOutflowVelocityType());
    }
  }

  protected void fromGmlToEmissionTemperature(final OPSSourceCharacteristics returnCharacteristics, final IsGmlCalculatedHeatContent gmlHeatContent) {
    returnCharacteristics.setEmissionTemperature(gmlHeatContent.getEmissionTemperature());
  }

  private static <T extends Number> T getOrDefault(final T value, final T defaultValue) {
    return value == null ? defaultValue : value;
  }
}
