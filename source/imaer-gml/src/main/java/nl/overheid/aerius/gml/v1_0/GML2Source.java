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
package nl.overheid.aerius.gml.v1_0;

import nl.overheid.aerius.gml.base.AbstractGML2Source;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.characteristics.GML2OPSSourceCharacteristics;
import nl.overheid.aerius.gml.base.geo.GML2Geometry;
import nl.overheid.aerius.gml.v1_0.source.EmissionSource;
import nl.overheid.aerius.shared.domain.v2.characteristics.OPSSourceCharacteristics;

/**
 * Utility class to convert to and from GML objects (specific for emission sources).
 */
class GML2Source extends AbstractGML2Source<EmissionSource, OPSSourceCharacteristics> {

  /**
   * @param conversionData The data to use when converting. Should be filled.
   * @param gml2SourceCharacteristics Converter for GML to type specific source characteristics.
   */
  public GML2Source(final GMLConversionData conversionData, final GML2OPSSourceCharacteristics gml2SourceCharacteristics) {
    super(conversionData, new GML2SourceVisitor(conversionData, new GML2Geometry(conversionData.getSrid()), gml2SourceCharacteristics),
        gml2SourceCharacteristics);
  }
}
