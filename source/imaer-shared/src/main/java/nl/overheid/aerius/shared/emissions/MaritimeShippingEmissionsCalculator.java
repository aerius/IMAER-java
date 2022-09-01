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

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.source.MaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MooringMaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.CustomMaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.CustomMooringMaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.MaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.MooringMaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.ShippingMovementType;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.StandardMaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.StandardMooringMaritimeShipping;
import nl.overheid.aerius.shared.emissions.shipping.MaritimeShippingRouteEmissionPoint;
import nl.overheid.aerius.shared.exception.AeriusException;

public class MaritimeShippingEmissionsCalculator {

  private final MaritimeShippingEmissionFactorSupplier emissionFactorSupplier;

  public MaritimeShippingEmissionsCalculator(final MaritimeShippingEmissionFactorSupplier emissionFactorSupplier) {
    this.emissionFactorSupplier = emissionFactorSupplier;
  }

  public Map<Substance, Double> calculateEmissions(final MooringMaritimeShippingEmissionSource shippingEmissionSource) throws AeriusException {
    final Map<Substance, BigDecimal> summed = new EnumMap<>(Substance.class);
    for (final MooringMaritimeShipping shipping : shippingEmissionSource.getSubSources()) {
      final Map<Substance, BigDecimal> emissionsForShipping = determineEmissionsMooring(shipping);
      emissionsForShipping.forEach(
          (key, value) -> shipping.getEmissions().put(key, value.doubleValue()));
      emissionsForShipping.forEach(
          (key, value) -> summed.merge(key, value, (v1, v2) -> v1.add(v2)));
    }
    final Map<Substance, Double> result = new EnumMap<>(Substance.class);
    summed.forEach(
        (key, value) -> result.put(key, value.doubleValue()));
    return result;
  }

  private Map<Substance, BigDecimal> determineEmissionsMooring(final MooringMaritimeShipping shipping) throws AeriusException {
    return determineDockedEmissionsMooring(shipping);
  }

  public Map<Substance, Double> calculateDockedEmissions(final MooringMaritimeShipping shipping) throws AeriusException {
    final Map<Substance, Double> result = new EnumMap<>(Substance.class);
    determineDockedEmissionsMooring(shipping).forEach(
        (key, value) -> result.put(key, value.doubleValue()));
    return result;
  }

  private Map<Substance, BigDecimal> determineDockedEmissionsMooring(final MooringMaritimeShipping shipping) throws AeriusException {
    final Map<Substance, BigDecimal> emissions = new EnumMap<>(Substance.class);
    final BigDecimal averageResidenceTime = BigDecimal.valueOf(shipping.getAverageResidenceTime());
    final BigDecimal shipsMoored = BigDecimal.valueOf(shipping.getTimeUnit().getPerYear(shipping.getShipsPerTimeUnit()));
    final BigDecimal totalResidenceTime = averageResidenceTime.multiply(shipsMoored);
    final Map<Substance, Double> emissionFactors = determineDockedEmissionFactors(shipping);
    final BigDecimal shorePowerReductionFactor = BigDecimal.ONE.subtract(BigDecimal.valueOf(shipping.getShorePowerFactor()));
    emissionFactors.forEach(
        (key, value) -> emissions.put(key, BigDecimal.valueOf(value).multiply(totalResidenceTime).multiply(shorePowerReductionFactor)));
    return emissions;
  }

  private Map<Substance, Double> determineDockedEmissionFactors(final MooringMaritimeShipping shipping) throws AeriusException {
    Map<Substance, Double> emissionFactors;
    if (shipping instanceof StandardMooringMaritimeShipping) {
      emissionFactors = emissionFactorSupplier.getMaritimeShippingDockedEmissionFactors(((StandardMooringMaritimeShipping) shipping).getShipCode());
    } else if (shipping instanceof CustomMooringMaritimeShipping) {
      emissionFactors = ((CustomMooringMaritimeShipping) shipping).getEmissionProperties().getEmissionFactors();
    } else {
      throw new IllegalArgumentException("Unexpected MooringMaritimeShipping type");
    }
    return emissionFactors;
  }

  public Map<Substance, Double> calculateEmissions(final MaritimeShippingEmissionSource shippingEmissionSource, final Geometry geometry)
      throws AeriusException {
    final Map<Substance, BigDecimal> summed;
    if (shippingEmissionSource.getMooringAId() == null && shippingEmissionSource.getMooringBId() == null) {
      summed = calculateStandaloneRouteEmissions(shippingEmissionSource, geometry);
    } else {
      summed = calculateMooringRouteEmissions(shippingEmissionSource, geometry);
    }
    final Map<Substance, Double> result = new EnumMap<>(Substance.class);
    summed.forEach(
        (key, value) -> result.put(key, value.doubleValue()));
    return result;
  }

  private Map<Substance, BigDecimal> calculateStandaloneRouteEmissions(final MaritimeShippingEmissionSource shippingEmissionSource,
      final Geometry geometry) throws AeriusException {
    final List<MaritimeShippingRouteEmissionPoint> routePoints = emissionFactorSupplier.determineMaritimeRoutePoints(geometry);
    final Map<Substance, BigDecimal> summed = new EnumMap<>(Substance.class);
    for (final MaritimeShipping shipping : shippingEmissionSource.getSubSources()) {
      final BigDecimal numberOfShipsPerYear = BigDecimal.valueOf(shipping.getTimeUnit().getPerYear(shipping.getMovementsPerTimeUnit()));
      final Map<Substance, BigDecimal> emissionsForShipping = determineEmissionsForPoints(routePoints,
          shippingEmissionSource.getMovementType(), numberOfShipsPerYear, shipping);
      emissionsForShipping.forEach(
          (key, value) -> shipping.getEmissions().put(key, value.doubleValue()));
      emissionsForShipping.forEach(
          (key, value) -> summed.merge(key, value, (v1, v2) -> v1.add(v2)));
    }
    return summed;
  }

  private Map<Substance, BigDecimal> calculateMooringRouteEmissions(final MaritimeShippingEmissionSource shippingEmissionSource,
      final Geometry geometry) throws AeriusException {
    final Map<Substance, BigDecimal> summed = new EnumMap<>(Substance.class);
    for (final MaritimeShipping shipping : shippingEmissionSource.getSubSources()) {
      final List<MaritimeShippingRouteEmissionPoint> routePoints = determineRoutePoints(geometry, shipping,
          shippingEmissionSource.getMooringAId() != null, shippingEmissionSource.getMooringBId() != null);
      final BigDecimal numberOfShipsPerYear = BigDecimal.valueOf(shipping.getTimeUnit().getPerYear(shipping.getMovementsPerTimeUnit()));
      final Map<Substance, BigDecimal> emissionsForShipping = determineEmissionsForPoints(routePoints,
          shippingEmissionSource.getMovementType(), numberOfShipsPerYear, shipping);
      emissionsForShipping.forEach(
          (key, value) -> shipping.getEmissions().put(key, value.doubleValue()));
      emissionsForShipping.forEach(
          (key, value) -> summed.merge(key, value, (v1, v2) -> v1.add(v2)));
    }
    return summed;
  }

  private List<MaritimeShippingRouteEmissionPoint> determineRoutePoints(final Geometry geometry, final MaritimeShipping shipping,
      final boolean mooringOnA, final boolean mooringOnB) throws AeriusException {
    List<MaritimeShippingRouteEmissionPoint> emissions;

    if (shipping instanceof StandardMaritimeShipping) {
      emissions = emissionFactorSupplier.determineMaritimeMooringInlandRoutePoints(geometry,
          ((StandardMaritimeShipping) shipping).getShipCode(), mooringOnA, mooringOnB);
    } else if (shipping instanceof CustomMaritimeShipping) {
      emissions = emissionFactorSupplier.determineMaritimeMooringInlandRoutePointsForGrossTonnage(geometry,
          ((CustomMaritimeShipping) shipping).getGrossTonnage(), mooringOnA, mooringOnB);
    } else {
      throw new IllegalArgumentException("Unexpected MaritimeShipping type");
    }
    return emissions;
  }

  private Map<Substance, BigDecimal> determineEmissionsForPoints(final List<MaritimeShippingRouteEmissionPoint> routePoints,
      final ShippingMovementType movementType, final BigDecimal numberOfShipsPerYear, final MaritimeShipping shipping) throws AeriusException {
    final Map<Substance, BigDecimal> emissions = new EnumMap<>(Substance.class);
    final Map<Substance, BigDecimal> emissionFactors = determineRouteEmissionFactors(movementType, shipping);
    for (final MaritimeShippingRouteEmissionPoint routePoint : routePoints) {
      determineEmissionsForPoint(routePoint, numberOfShipsPerYear, emissionFactors).forEach(
          (key, value) -> emissions.merge(key, value, (v1, v2) -> v1.add(v2)));
    }
    return emissions;
  }

  private Map<Substance, BigDecimal> determineRouteEmissionFactors(final ShippingMovementType movementType, final MaritimeShipping shipping)
      throws AeriusException {
    final Map<Substance, BigDecimal> emissionFactors;
    if (shipping instanceof StandardMaritimeShipping) {
      emissionFactors = toBigDecimalMap(emissionFactorSupplier
          .getMaritimeShippingRouteEmissionFactors(movementType, ((StandardMaritimeShipping) shipping).getShipCode()));
    } else if (shipping instanceof CustomMaritimeShipping) {
      emissionFactors = toBigDecimalMap(((CustomMaritimeShipping) shipping).getEmissionProperties().getEmissionFactors());
    } else {
      throw new IllegalArgumentException("Unexpected MaritimeShipping type");
    }
    return emissionFactors;
  }

  public Map<Substance, Double> determineEmissionsForPoint(final MaritimeShippingRouteEmissionPoint routePoint,
      final ShippingMovementType movementType, final int numberOfShipsPerYear, final MaritimeShipping shipping) throws AeriusException {
    final Map<Substance, Double> result = new EnumMap<>(Substance.class);
    final Map<Substance, BigDecimal> emissionFactors = determineRouteEmissionFactors(movementType, shipping);
    determineEmissionsForPoint(routePoint, BigDecimal.valueOf(numberOfShipsPerYear), emissionFactors).forEach(
        (key, value) -> result.put(key, value.doubleValue()));
    return result;
  }

  private Map<Substance, BigDecimal> determineEmissionsForPoint(final MaritimeShippingRouteEmissionPoint routePoint,
      final BigDecimal numberOfShipsPerYear, final Map<Substance, BigDecimal> emissionFactors) {
    final Map<Substance, BigDecimal> emissions = new EnumMap<>(Substance.class);
    final BigDecimal segmentLength = BigDecimal.valueOf(routePoint.getSegmentLength());
    final BigDecimal maneuverFactor = BigDecimal.valueOf(routePoint.getManeuverFactor());
    emissionFactors.forEach(
        (key, value) -> emissions
            .merge(key, value.multiply(numberOfShipsPerYear).multiply(segmentLength).multiply(maneuverFactor),
                (v1, v2) -> v1.add(v2)));
    return emissions;
  }

  private Map<Substance, BigDecimal> toBigDecimalMap(final Map<Substance, Double> original) {
    final Map<Substance, BigDecimal> result = new EnumMap<>(Substance.class);
    original.forEach(
        (key, value) -> result.put(key, BigDecimal.valueOf(value)));
    return result;
  }

}
