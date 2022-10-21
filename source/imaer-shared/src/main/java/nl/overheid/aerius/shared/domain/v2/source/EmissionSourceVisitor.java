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
package nl.overheid.aerius.shared.domain.v2.source;

import nl.overheid.aerius.shared.domain.v2.geojson.IsFeature;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Classes operating on the emission source types can implement this interface to create a visitor pattern implementation.
 * @param <T> visitor specific data type
 */
public interface EmissionSourceVisitor<T> {

  T visit(FarmLodgingEmissionSource emissionSource, IsFeature feature) throws AeriusException;

  T visit(FarmlandEmissionSource emissionSource, IsFeature feature) throws AeriusException;

  default T visit(final ManureStorageEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    // Temporary default while projects implement this method.
    // TODO: remove after a week or so.
    return null;
  }

  T visit(PlanEmissionSource emissionSource, IsFeature feature) throws AeriusException;

  T visit(OffRoadMobileEmissionSource emissionSource, IsFeature feature) throws AeriusException;

  T visit(SRM1RoadEmissionSource emissionSource, IsFeature feature) throws AeriusException;

  T visit(SRM2RoadEmissionSource emissionSource, IsFeature feature) throws AeriusException;

  T visit(ADMSRoadEmissionSource emissionSource, IsFeature feature) throws AeriusException;

  T visit(InlandShippingEmissionSource emissionSource, IsFeature feature) throws AeriusException;

  T visit(MooringInlandShippingEmissionSource emissionSource, IsFeature feature) throws AeriusException;

  T visit(MaritimeShippingEmissionSource emissionSource, IsFeature feature) throws AeriusException;

  T visit(MooringMaritimeShippingEmissionSource emissionSource, IsFeature feature) throws AeriusException;

  T visit(GenericEmissionSource emissionSource, IsFeature feature) throws AeriusException;

}
