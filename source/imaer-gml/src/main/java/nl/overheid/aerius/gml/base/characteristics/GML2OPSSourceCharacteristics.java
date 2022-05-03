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
import nl.overheid.aerius.gml.base.util.DiurnalVariationUtil;
import nl.overheid.aerius.shared.domain.ops.DiurnalVariation;
import nl.overheid.aerius.shared.domain.v2.characteristics.HeatContentType;
import nl.overheid.aerius.shared.domain.v2.characteristics.OPSSourceCharacteristics;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * GML source characteristics with OPS specific parameters conversion.
 */
public class GML2OPSSourceCharacteristics extends GML2SourceCharacteristics<OPSSourceCharacteristics> {

  public GML2OPSSourceCharacteristics(final GMLConversionData conversionData) {
    super(conversionData);
  }

  @Override
  protected OPSSourceCharacteristics fromGMLSpecific(final IsGmlSourceCharacteristics characteristics,
      final OPSSourceCharacteristics sectorCharacteristics) throws AeriusException {
    final IsGmlBaseOPSSourceCharacteristics gmlOPSCharacteristics = (IsGmlBaseOPSSourceCharacteristics) characteristics;
    final OPSSourceCharacteristics defaultCharacteristics = sectorCharacteristics == null ? new OPSSourceCharacteristics() : sectorCharacteristics;
    final OPSSourceCharacteristics returnCharacteristics = new OPSSourceCharacteristics();
    returnCharacteristics.setEmissionHeight(gmlOPSCharacteristics.getEmissionHeight());
    returnCharacteristics.setSpread(getOrDefault(gmlOPSCharacteristics.getSpread(), defaultCharacteristics.getSpread()));
    returnCharacteristics.setParticleSizeDistribution(defaultCharacteristics.getParticleSizeDistribution());

    fromGMLToDiurnalVariation(gmlOPSCharacteristics, returnCharacteristics, defaultCharacteristics);
    fromGMLToHeatContent(gmlOPSCharacteristics, returnCharacteristics);

    return returnCharacteristics;
  }

  private void fromGMLToDiurnalVariation(final IsGmlBaseOPSSourceCharacteristics characteristics,
      final OPSSourceCharacteristics returnCharacteristics, final OPSSourceCharacteristics defaultCharacteristics) {
    if (characteristics instanceof IsGmlOPSSourceCharacteristicsV31) {
      fromGMLToDiurnalVariation((IsGmlOPSSourceCharacteristicsV31) characteristics, returnCharacteristics, defaultCharacteristics);
    } else if (characteristics instanceof IsGmlOPSSourceCharacteristics) {
      fromGMLToDiurnalVariation((IsGmlOPSSourceCharacteristics) characteristics, returnCharacteristics, defaultCharacteristics);
    }
  }

  private void fromGMLToDiurnalVariation(final IsGmlOPSSourceCharacteristicsV31 characteristics,
      final OPSSourceCharacteristics returnCharacteristics, final OPSSourceCharacteristics defaultCharacteristics) {
    if (characteristics.getDiurnalVariation() == null) {
      returnCharacteristics.setDiurnalVariation(defaultCharacteristics.getDiurnalVariation());
    } else {
      final String diurnalVariationCode = characteristics.getDiurnalVariation();
      returnCharacteristics.setDiurnalVariation(DiurnalVariation.safeValueOf(diurnalVariationCode));
    }
  }

  private void fromGMLToDiurnalVariation(final IsGmlOPSSourceCharacteristics characteristics,
      final OPSSourceCharacteristics returnCharacteristics, final OPSSourceCharacteristics defaultCharacteristics) {
    DiurnalVariationUtil.setDiurnalVariation(characteristics.getDiurnalVariation(),
        code -> setStandardDiurnalVariation(returnCharacteristics, defaultCharacteristics, code),
        returnCharacteristics::setCustomDiurnalVariationId);
  }

  private void setStandardDiurnalVariation(final OPSSourceCharacteristics returnCharacteristics,
      final OPSSourceCharacteristics defaultCharacteristics, final String code) {
    returnCharacteristics.setDiurnalVariation(code == null
        ? defaultCharacteristics.getDiurnalVariation()
        : DiurnalVariation.safeValueOf(code));
  }

  private void fromGMLToHeatContent(final IsGmlBaseOPSSourceCharacteristics characteristics,
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
