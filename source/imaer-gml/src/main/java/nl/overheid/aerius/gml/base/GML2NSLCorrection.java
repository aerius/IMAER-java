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
package nl.overheid.aerius.gml.base;

import java.util.List;
import java.util.stream.Collectors;

import nl.overheid.aerius.gml.base.result.IsGmlCalculationPoint;
import nl.overheid.aerius.gml.base.result.IsGmlCalculationPointCorrection;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLCorrection;

/**
 * Utility class to convert from GML objects (specific for NSL corrections).
 */
public class GML2NSLCorrection {

  /**
   * Convert GML corrections linked to points to NSLCorrection.
   * @param calculationPoint The GML-representation of a calculation point, which could have corrections.
   * @return The NSL corrections linked to this point.
   */
  public List<NSLCorrection> fromGML(final IsGmlCalculationPoint calculationPoint) {
    final String calculationPointId = calculationPoint.getId();
    return calculationPoint.getCorrections().stream()
        .map(IsGmlProperty::getProperty)
        .map(correction -> fromGML(correction, calculationPointId))
        .collect(Collectors.toList());
  }

  private NSLCorrection fromGML(final IsGmlCalculationPointCorrection gmlCorrection, final String calculationPointId) {
    final NSLCorrection correction = new NSLCorrection();
    correction.setDescription(gmlCorrection.getDescription());
    correction.setLabel(gmlCorrection.getLabel());
    correction.setJurisdictionId(gmlCorrection.getJurisdictionId());
    correction.setResultType(gmlCorrection.getResultType());
    correction.setSubstance(gmlCorrection.getSubstance());
    correction.setValue(gmlCorrection.getValue());
    correction.setCalculationPointGmlId(calculationPointId);
    return correction;
  }

}
