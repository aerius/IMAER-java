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
package nl.overheid.aerius.gml.base.source;

import nl.overheid.aerius.gml.base.AbstractGML2Specific;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.shared.domain.v2.source.GenericEmissionSource;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Class to convert GML generic source to data object generic emission source.
 */
public class GML2Generic<T extends IsGmlEmissionSource> extends AbstractGML2Specific<T, GenericEmissionSource> {

  /**
   * @param conversionData The conversion data to use to use.
   */
  public GML2Generic(final GMLConversionData conversionData) {
    super(conversionData);
  }

  @Override
  public GenericEmissionSource convert(final T source) throws AeriusException {
    final GenericEmissionSource emissionValues = new GenericEmissionSource();
    for (final IsGmlProperty<IsGmlEmission> emissionProperty : source.getEmissionValues()) {
      final IsGmlEmission emission = emissionProperty.getProperty();
      emissionValues.getEmissions().put(emission.getSubstance(), emission.getValue());
    }
    return emissionValues;
  }

}

