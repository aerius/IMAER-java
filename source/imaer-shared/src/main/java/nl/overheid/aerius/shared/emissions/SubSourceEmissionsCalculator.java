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

import java.util.Map;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.OffRoadMobileEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.InlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.MooringInlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.MaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.MooringMaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.ShippingMovementType;
import nl.overheid.aerius.shared.emissions.shipping.InlandShippingRouteEmissionPoint;
import nl.overheid.aerius.shared.emissions.shipping.MaritimeShippingRouteEmissionPoint;
import nl.overheid.aerius.shared.emissions.shipping.ShippingLaden;
import nl.overheid.aerius.shared.exception.AeriusException;

public class SubSourceEmissionsCalculator {

  private final OffRoadMobileEmissionsCalculator offRoadMobileEmissionsCalculator;
  private final InlandShippingEmissionsCalculator inlandShippingEmissionsCalculator;
  private final MaritimeShippingEmissionsCalculator maritimeShippingEmissionsCalculator;

  public SubSourceEmissionsCalculator(final EmissionFactorSupplier emissionFactorSupplier) {
    this(
        new OffRoadMobileEmissionsCalculator(emissionFactorSupplier.offRoadMobile()),
        new InlandShippingEmissionsCalculator(emissionFactorSupplier.inlandShipping()),
        new MaritimeShippingEmissionsCalculator(emissionFactorSupplier.maritimeShipping()));
  }

  SubSourceEmissionsCalculator(final OffRoadMobileEmissionsCalculator offRoadMobileEmissionsCalculator,
      final InlandShippingEmissionsCalculator inlandShippingEmissionsCalculator,
      final MaritimeShippingEmissionsCalculator maritimeShippingEmissionsCalculator) {
    this.offRoadMobileEmissionsCalculator = offRoadMobileEmissionsCalculator;
    this.inlandShippingEmissionsCalculator = inlandShippingEmissionsCalculator;
    this.maritimeShippingEmissionsCalculator = maritimeShippingEmissionsCalculator;
  }

  public Map<Substance, Double> calculateOffRoadEmissions(final OffRoadMobileEmissionSource emissionSource) throws AeriusException {
    return offRoadMobileEmissionsCalculator.calculateEmissions(emissionSource);
  }

  public Map<Substance, Double> calculateInlandDockedEmissions(final MooringInlandShipping mooringInlandShipping, final ShippingLaden laden)
      throws AeriusException {
    return inlandShippingEmissionsCalculator.calculateDockedEmissions(mooringInlandShipping, laden);
  }

  public Map<Substance, Double> calculateInlandRouteEmissions(final InlandShippingRouteEmissionPoint routePoint, final InlandShipping shipping,
      final boolean reverse, final ShippingLaden laden) throws AeriusException {
    return inlandShippingEmissionsCalculator.calculateRouteEmissions(routePoint, shipping, reverse, laden);
  }

  public Map<Substance, Double> calculateMaritimeDockedEmissions(final MooringMaritimeShipping mooringMaritimeShipping)
      throws AeriusException {
    return maritimeShippingEmissionsCalculator.calculateDockedEmissions(mooringMaritimeShipping);
  }

  public Map<Substance, Double> calculateMaritimeRouteEmissions(final MaritimeShippingRouteEmissionPoint routePoint,
      final ShippingMovementType movementType, final int numberOfShipsPerYear, final MaritimeShipping maritimeShipping)
      throws AeriusException {
    return maritimeShippingEmissionsCalculator.determineEmissionsForPoint(routePoint, movementType, numberOfShipsPerYear,
        maritimeShipping);
  }

}
