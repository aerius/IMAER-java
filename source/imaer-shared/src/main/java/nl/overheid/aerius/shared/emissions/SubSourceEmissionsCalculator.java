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
import java.util.stream.Collectors;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.OffRoadMobileEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardColdStartVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.Vehicles;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.InlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.MooringInlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.MaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.MooringMaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.ShippingMovementType;
import nl.overheid.aerius.shared.emissions.shipping.InlandShippingRouteEmissionPoint;
import nl.overheid.aerius.shared.emissions.shipping.MaritimeShippingRouteEmissionPoint;
import nl.overheid.aerius.shared.emissions.shipping.ShippingLaden;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Helper class with all methods for calculating emissions on sub source emission objects.
 */
public class SubSourceEmissionsCalculator {

  private final OffRoadMobileEmissionsCalculator offRoadMobileEmissionsCalculator;
  private final InlandShippingEmissionsCalculator inlandShippingEmissionsCalculator;
  private final MaritimeShippingEmissionsCalculator maritimeShippingEmissionsCalculator;
  private final ColdStartEmissionsCalculator coldStartEmissionsCalculator;

  public SubSourceEmissionsCalculator(final EmissionFactorSupplier emissionFactorSupplier) {
    this(
        new OffRoadMobileEmissionsCalculator(emissionFactorSupplier.offRoadMobile()),
        new InlandShippingEmissionsCalculator(emissionFactorSupplier.inlandShipping()),
        new MaritimeShippingEmissionsCalculator(emissionFactorSupplier.maritimeShipping()),
        new ColdStartEmissionsCalculator(emissionFactorSupplier.coldStart()));
  }

  SubSourceEmissionsCalculator(final OffRoadMobileEmissionsCalculator offRoadMobileEmissionsCalculator,
      final InlandShippingEmissionsCalculator inlandShippingEmissionsCalculator,
      final MaritimeShippingEmissionsCalculator maritimeShippingEmissionsCalculator,
      final ColdStartEmissionsCalculator coldStartEmissionsCalculator) {
    this.offRoadMobileEmissionsCalculator = offRoadMobileEmissionsCalculator;
    this.inlandShippingEmissionsCalculator = inlandShippingEmissionsCalculator;
    this.maritimeShippingEmissionsCalculator = maritimeShippingEmissionsCalculator;
    this.coldStartEmissionsCalculator = coldStartEmissionsCalculator;
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

  /**
   * Calculates the emission of a {@link Vehicles} object and returns the values in Kg/Year.
   *
   * @param vehicle vehicle to calculate the emission on
   * @return emissions calculated emissions in Kg/Year.
   */
  public Map<Substance, Double> calculateColdStartEmissions(final Vehicles vehicle) throws AeriusException {
    // null is passed as road emission source since it has no additional information with coldstart for emission calculations.
    return coldStartEmissionsCalculator.calculateEmissions(null, vehicle)
        .entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry ->
            coldStartEmissionsCalculator.toTotalEmission(null, entry.getValue(), null).doubleValue()));
  }

  /**
   * Calculates the emission of a vehicle type on {@link StandardColdStartVehicles} object and returns the values in Kg/Year.
   *
   * @param vehicles object to get the time unit from
   * @param vehicleType vehicle type of the standard vehicle
   * @return emissions calculated emissions in Kg/Year.
   */
  public Map<Substance, Double> calculateColdStartEmissions(final StandardColdStartVehicles vehicles, final String vehicleType) {
    return coldStartEmissionsCalculator.calculateColdStartEmissions(vehicles, vehicleType, vehicles.getValuesPerVehicleTypes().get(vehicleType))
        .entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry ->
            coldStartEmissionsCalculator.toTotalEmission(null, entry.getValue(), null).doubleValue()));
  }
}
