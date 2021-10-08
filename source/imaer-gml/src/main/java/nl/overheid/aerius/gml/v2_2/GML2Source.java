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
package nl.overheid.aerius.gml.v2_2;

import nl.overheid.aerius.gml.base.AbstractGML2Source;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.characteristics.GML2SourceCharacteristicsV31;
import nl.overheid.aerius.gml.v2_2.source.EmissionSource;

/**
 * Utility class to convert to and from GML objects (specific for emission sources).
 */
class GML2Source extends AbstractGML2Source<EmissionSource> {

  /**
   * @param conversionData The data to use when converting. Should be filled.
   */
  public GML2Source(final GMLConversionData conversionData) {
    super(conversionData, new GML2SourceVisitor(conversionData), new GML2SourceCharacteristicsV31(conversionData));
  }

}
