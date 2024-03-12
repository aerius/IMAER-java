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
package nl.overheid.aerius.gml.v6_0;

import nl.overheid.aerius.gml.base.AbstractGML2Source;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.characteristics.GML2SourceCharacteristics;
import nl.overheid.aerius.gml.v6_0.source.EmissionSource;
import nl.overheid.aerius.shared.domain.v2.characteristics.SourceCharacteristics;

/**
 * Utility class to convert to and from GML objects (specific for emission sources).
 */
class GML2Source<S extends SourceCharacteristics> extends AbstractGML2Source<EmissionSource, S> {

  /**
   * @param conversionData The data to use when converting. Should be filled.
   * @param gml2SourceCharacteristics Converter for GML to type specific source characteristics.
   */
  public GML2Source(final GMLConversionData conversionData, final GML2SourceCharacteristics<S> gml2SourceCharacteristics) {
    super(conversionData, new GML2SourceVisitor<>(conversionData, gml2SourceCharacteristics), gml2SourceCharacteristics);
  }

}
