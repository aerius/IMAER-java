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
import java.util.Optional;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.InlandWaterway;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.WaterwayDirection;
import nl.overheid.aerius.shared.emissions.shipping.InlandShippingRouteEmissionPoint;
import nl.overheid.aerius.shared.emissions.shipping.ShippingLaden;
import nl.overheid.aerius.shared.exception.AeriusException;

public interface InlandShippingEmissionFactorSupplier {

  /**
   * @param geometry The geometry that should be converted to points, taking locks into account
   * @param waterway The waterway to use for each point. If not supplied, the waterway should be determined per point by the supplier.
   * @return A list of points that make up the inland shipping route, including the segment length each point represents and the lockfactor associated to it.
   */
  List<InlandShippingRouteEmissionPoint> determineInlandRoutePoints(Geometry geometry, Optional<InlandWaterway> waterway) throws AeriusException;

  /**
   * Obtain emission factors per meter shipping route (in kg/meter) for a ship type.
   */
  Map<Substance, Double> getInlandShippingRouteEmissionFactors(String waterwayCode, WaterwayDirection direction, ShippingLaden laden, String shipCode)
      throws AeriusException;

  /**
   * Obtain emission factors per hour moored (in kg/hour) for a ship type.
   */
  Map<Substance, Double> getInlandShippingDockedEmissionFactors(String shipCode) throws AeriusException;
}
