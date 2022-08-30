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
package nl.overheid.aerius.shared.emissions;

import java.util.List;
import java.util.Map;

import nl.overheid.aerius.shared.domain.Substance;
import nl.aerius.shared.domain.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.ShippingMovementType;
import nl.overheid.aerius.shared.emissions.shipping.MaritimeShippingRouteEmissionPoint;
import nl.overheid.aerius.shared.exception.AeriusException;

public interface MaritimeShippingEmissionFactorSupplier {

  /**
   * @param geometry The geometry that should be converted to points
   * @return A list of points that make up the maritime shipping route, including the segment length each point represents
   * and the maneuver factor associated to it (should be 1.0 for most cases).
   */
  List<MaritimeShippingRouteEmissionPoint> determineMaritimeRoutePoints(Geometry geometry) throws AeriusException;

  /**
   * @param geometry The geometry that should be converted to points
   * @param shipCode The ship code to use when determining maneuver factors
   * @param mooringOnA Indication if the ship is mooring at the start of the route
   * @param mooringOnB Indication if the ship is mooring at the end of the route
   * @return A list of points that make up the maritime shipping inland route when mooring,
   * including the segment length each point represents and the maneuver factor associated to it.
   */
  List<MaritimeShippingRouteEmissionPoint> determineMaritimeMooringInlandRoutePoints(Geometry geometry, String shipCode,
      boolean mooringOnA, boolean mooringOnB) throws AeriusException;

  /**
   * @param geometry The geometry that should be converted to points
   * @param grossTonnage The gross tonnage to use when determining maneuver factors
   * @param mooringOnA Indication if the ship is mooring at the start of the route
   * @param mooringOnB Indication if the ship is mooring at the end of the route
   * @return A list of points that make up the maritime shipping inland route when mooring,
   * including the segment length each point represents and the maneuver factor associated to it.
   */
  List<MaritimeShippingRouteEmissionPoint> determineMaritimeMooringInlandRoutePointsForGrossTonnage(Geometry geometry, int grossTonnage,
      boolean mooringOnA, boolean mooringOnB) throws AeriusException;

  /**
   * Obtain emission factors per meter shipping route (in kg/meter) for a ship type.
   */
  Map<Substance, Double> getMaritimeShippingRouteEmissionFactors(ShippingMovementType movementType, String shipCode) throws AeriusException;

  /**
   * Obtain emission factors per hour moored (in kg/hour) for a ship type.
   */
  Map<Substance, Double> getMaritimeShippingDockedEmissionFactors(String shipCode) throws AeriusException;

}
