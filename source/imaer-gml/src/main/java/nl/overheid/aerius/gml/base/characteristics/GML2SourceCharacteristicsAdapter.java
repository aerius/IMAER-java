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
import nl.overheid.aerius.shared.domain.v2.characteristics.CharacteristicsType;
import nl.overheid.aerius.shared.domain.v2.characteristics.SourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Implementation of GML2SourceCharacteristics that can either handle OPS or ADMS source characteristics.
 *
 * @param <T> the characteristics data type
 */
public class GML2SourceCharacteristicsAdapter<T extends SourceCharacteristics> extends GML2SourceCharacteristics<T> {

  private final GML2SourceCharacteristics<T> gml2SourceCharacteristics;

  public GML2SourceCharacteristicsAdapter(final GMLConversionData conversionData, final boolean legacySpread) {
    super(conversionData);
    gml2SourceCharacteristics = gml2SourceCharacteristics(conversionData, legacySpread);
  }

  private GML2SourceCharacteristics<T> gml2SourceCharacteristics(final GMLConversionData conversionData, final boolean legacySpread) {
    final CharacteristicsType ct = conversionData.getCharacteristicsType();

    if (ct == CharacteristicsType.OPS) {
      return (GML2SourceCharacteristics<T>) new GML2OPSSourceCharacteristics(conversionData, legacySpread);
    } else if (ct == CharacteristicsType.ADMS) {
      return (GML2SourceCharacteristics<T>) new GML2ADMSSourceCharacteristics(conversionData);
    } else {
      throw new IllegalArgumentException("Can't read GML for characteristics of type " + ct + ". This is not implemented.");
    }
  }

  @Override
  protected T fromGMLSpecific(final IsGmlSourceCharacteristics characteristics, final T sectorCharacteristics, final Geometry geometry)
      throws AeriusException {
    return gml2SourceCharacteristics.fromGMLSpecific(characteristics, sectorCharacteristics, geometry);
  }

}
