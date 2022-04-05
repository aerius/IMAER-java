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
import nl.overheid.aerius.shared.exception.AeriusException;

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
    final ADMSSourceCharacteristics returnCharacteristics = new ADMSSourceCharacteristics();
    returnCharacteristics.setHeight(gmlOPSCharacteristics.getHeight());

    return returnCharacteristics;
  }

}
