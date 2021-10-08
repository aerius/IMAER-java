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

import nl.overheid.aerius.shared.domain.v2.source.EmissionSource;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Abstract class to convert GML sources to AERIUS domain sources.
 * @param <T> The type of the GML object to convert.
 * @param <E> The type of the domain object to convert to.
 */
public abstract class AbstractGML2Specific<T, E extends EmissionSource> {

  private final GMLConversionData conversionData;

  /**
   * @param conversionData The conversion data to use when converting.
   */
  protected AbstractGML2Specific(final GMLConversionData conversionData) {
    this.conversionData = conversionData;
  }

  /**
   * @param source The GML source to convert.
   * @return The proper AERIUS source.
   * @throws AeriusException In case of exceptions.
   */
  public abstract E convert(final T source) throws AeriusException;

  protected GMLConversionData getConversionData() {
    return conversionData;
  }

}
