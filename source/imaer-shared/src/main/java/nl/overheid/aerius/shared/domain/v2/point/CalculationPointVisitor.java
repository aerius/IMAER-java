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
package nl.overheid.aerius.shared.domain.v2.point;

import nl.aerius.shared.domain.geojson.IsFeature;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Classes operating on calculation points can implement this interface to create a visitor pattern implementation.
 * @param <T> visitor specific data type
 */
public interface CalculationPointVisitor<T> {

  T visit(ReceptorPoint calculationPoint, IsFeature feature) throws AeriusException;

  T visit(CustomCalculationPoint emissionSource, IsFeature feature) throws AeriusException;

  T visit(SubPoint emissionSource, IsFeature feature) throws AeriusException;

}
