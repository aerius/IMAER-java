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
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToIntFunction;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.source.InlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MooringInlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.CustomInlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.CustomInlandShippingEmissionProperties;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.CustomMooringInlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.InlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.InlandWaterway;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.MooringInlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.StandardInlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.StandardMooringInlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.WaterwayDirection;
import nl.overheid.aerius.shared.emissions.shipping.InlandShippingRouteEmissionPoint;
import nl.overheid.aerius.shared.emissions.shipping.ShippingLaden;
import nl.overheid.aerius.shared.exception.AeriusException;

public class InlandShippingEmissionsCalculator {

  private static final int MAX_PERCENTAGE = 100;
  private static final BigDecimal PERCENT_TO_FRACTION = BigDecimal.valueOf(100);

  private final InlandShippingEmissionFactorSupplier emissionFactorSupplier;

  public InlandShippingEmissionsCalculator(final InlandShippingEmissionFactorSupplier emissionFactorSupplier) {
    this.emissionFactorSupplier = emissionFactorSupplier;
  }

  public Map<Substance, Double> calculateEmissions(final MooringInlandShippingEmissionSource shippingEmissionSource) throws AeriusException {
    final Map<Substance, BigDecimal> summed = new EnumMap<>(Substance.class);
    for (final MooringInlandShipping shipping : shippingEmissionSource.getSubSources()) {
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

  private Map<Substance, BigDecimal> determineEmissionsMooring(final MooringInlandShipping shipping) throws AeriusException {
    final Map<Substance, BigDecimal> summed = new EnumMap<>(Substance.class);
    for (final ShippingLaden laden : ShippingLaden.values()) {
      final Map<Substance, BigDecimal> emissionsForShipping = calculateDockedEmissionsWithFractions(shipping, laden);
      emissionsForShipping.forEach(
          (key, value) -> summed.merge(key, value, (v1, v2) -> v1.add(v2)));
    }
    return summed;
  }

  public Map<Substance, Double> calculateDockedEmissions(final MooringInlandShipping shipping, final ShippingLaden laden) throws AeriusException {
    final Map<Substance, Double> result = new EnumMap<>(Substance.class);
    calculateDockedEmissionsWithFractions(shipping, laden).forEach(
        (key, value) -> result.put(key, value.doubleValue()));
    return result;
  }

  public Map<Substance, BigDecimal> calculateDockedEmissionsWithFractions(final MooringInlandShipping shipping, final ShippingLaden laden)
      throws AeriusException {
    final Function<MooringInlandShipping, BigDecimal> fractionFunction = laden == ShippingLaden.LADEN
        ? ship -> BigDecimal.valueOf(ship.getPercentageLaden()).divide(PERCENT_TO_FRACTION)
        : ship -> BigDecimal.ONE.subtract(BigDecimal.valueOf(ship.getPercentageLaden()).divide(PERCENT_TO_FRACTION));
    return determineDockedEmissionsMooring(shipping, laden, fractionFunction);
  }

  private Map<Substance, BigDecimal> determineDockedEmissionsMooring(final MooringInlandShipping shipping, final ShippingLaden laden,
      final Function<MooringInlandShipping, BigDecimal> fractionFunction) throws AeriusException {
    final Map<Substance, BigDecimal> emissions = new EnumMap<>(Substance.class);
    final BigDecimal averageResidenceTime = BigDecimal.valueOf(shipping.getAverageResidenceTime());
    final BigDecimal fraction = fractionFunction.apply(shipping);
    final BigDecimal shipsMoored = BigDecimal.valueOf(shipping.getTimeUnit().getPerYear(shipping.getShipsPerTimeUnit())).multiply(fraction);
    final BigDecimal totalResidenceTime = averageResidenceTime.multiply(shipsMoored);
    final BigDecimal shorePowerReductionFactor = BigDecimal.ONE.subtract(BigDecimal.valueOf(shipping.getShorePowerFactor()));
    final Map<Substance, Double> emissionFactors = determineDockedEmissionFactors(shipping, laden);
    emissionFactors.forEach(
        (key, value) -> emissions.put(key, BigDecimal.valueOf(value).multiply(totalResidenceTime).multiply(shorePowerReductionFactor)));
    return emissions;
  }

  private Map<Substance, Double> determineDockedEmissionFactors(final MooringInlandShipping shipping, final ShippingLaden laden)
      throws AeriusException {
    Map<Substance, Double> emissions;
    if (shipping instanceof StandardMooringInlandShipping) {
      emissions = emissionFactorSupplier.getInlandShippingDockedEmissionFactors(((StandardMooringInlandShipping) shipping).getShipCode());
    } else if (shipping instanceof CustomMooringInlandShipping) {
      emissions = getCustomEmissionFactors(((CustomMooringInlandShipping) shipping).getEmissionProperties(), laden);
    } else {
      throw new IllegalArgumentException("Unexpected MooringInlandShipping type");
    }
    return emissions;
  }

  public Map<Substance, Double> calculateEmissions(final InlandShippingEmissionSource shippingEmissionSource, final Geometry geometry)
      throws AeriusException {
    final List<InlandShippingRouteEmissionPoint> routePoints = emissionFactorSupplier.determineInlandRoutePoints(geometry,
        Optional.ofNullable(shippingEmissionSource.getWaterway()));
    final Map<Substance, BigDecimal> summed = new EnumMap<>(Substance.class);
    for (final InlandShipping inlandShipping : shippingEmissionSource.getSubSources()) {
      final Map<Substance, BigDecimal> emissionsForShipping = determineEmissionsForPoints(routePoints, inlandShipping);
      emissionsForShipping.forEach(
          (key, value) -> inlandShipping.getEmissions().put(key, value.doubleValue()));
      emissionsForShipping.forEach(
          (key, value) -> summed.merge(key, value, (v1, v2) -> v1.add(v2)));
    }
    final Map<Substance, Double> result = new EnumMap<>(Substance.class);
    summed.forEach(
        (key, value) -> result.put(key, value.doubleValue()));
    return result;
  }

  private Map<Substance, BigDecimal> determineEmissionsForPoints(final List<InlandShippingRouteEmissionPoint> routePoints,
      final InlandShipping shipping) throws AeriusException {
    final Map<Substance, BigDecimal> emissions = new EnumMap<>(Substance.class);
    for (final InlandShippingRouteEmissionPoint routePoint : routePoints) {
      //add laden A to B emissionsources
      if (shipping.getPercentageLadenAtoB() != null && shipping.getPercentageLadenAtoB() > 0) {
        calculateInlandRoutePointEmissions(emissions, routePoint, shipping, false, ShippingLaden.LADEN);
      }
      //add laden B to A emissionsources
      if (shipping.getPercentageLadenBtoA() != null && shipping.getPercentageLadenBtoA() > 0) {
        calculateInlandRoutePointEmissions(emissions, routePoint, shipping, true, ShippingLaden.LADEN);
      }
      //add unladen A to B emissionsources
      if (shipping.getPercentageLadenAtoB() != null && shipping.getPercentageLadenAtoB() < MAX_PERCENTAGE) {
        calculateInlandRoutePointEmissions(emissions, routePoint, shipping, false, ShippingLaden.UNLADEN);
      }
      //add unladen B to A emissionsources
      if (shipping.getPercentageLadenBtoA() != null && shipping.getPercentageLadenBtoA() < MAX_PERCENTAGE) {
        calculateInlandRoutePointEmissions(emissions, routePoint, shipping, true, ShippingLaden.UNLADEN);
      }
    }
    return emissions;
  }

  public Map<Substance, Double> calculateRouteEmissions(final InlandShippingRouteEmissionPoint routePoint, final InlandShipping shipping,
      final boolean reverse, final ShippingLaden laden) throws AeriusException {
    final Map<Substance, Double> result = new EnumMap<>(Substance.class);
    final Map<Substance, BigDecimal> emissions = new EnumMap<>(Substance.class);
    calculateInlandRoutePointEmissions(emissions, routePoint, shipping, reverse, laden);
    emissions.forEach(
        (key, value) -> result.put(key, value.doubleValue()));
    return result;
  }

  private void calculateInlandRoutePointEmissions(final Map<Substance, BigDecimal> emissions,
      final InlandShippingRouteEmissionPoint routePoint, final InlandShipping shipping, final boolean reverse, final ShippingLaden laden)
      throws AeriusException {
    //determine the right # ships per year based on reverse & laden
    final BigDecimal shipsPerYear = reverse
        ? getShipsBtoAPerYear(shipping, laden)
        : getShipsAtoBPerYear(shipping, laden);

    if (shipsPerYear.doubleValue() > 0) {
      final Map<Substance, BigDecimal> emissionsForPoint = getInlandRouteEmissionValues(reverse, routePoint.getWaterway(), laden, routePoint,
          shipping, shipsPerYear);
      emissionsForPoint.forEach(
          (key, value) -> emissions.merge(key, value, (v1, v2) -> v1.add(v2)));
    }
  }

  private BigDecimal getShipsAtoBPerYear(final InlandShipping shipping, final ShippingLaden laden) {
    return getShipsPerYear(shipping, laden,
        InlandShipping::getTimeUnitAtoB,
        v -> toSafeInt(v.getMovementsAtoBPerTimeUnit()),
        v -> toSafeInt(v.getPercentageLadenAtoB()));
  }

  private BigDecimal getShipsBtoAPerYear(final InlandShipping shipping, final ShippingLaden laden) {
    return getShipsPerYear(shipping, laden,
        InlandShipping::getTimeUnitBtoA,
        v -> toSafeInt(v.getMovementsBtoAPerTimeUnit()),
        v -> toSafeInt(v.getPercentageLadenBtoA()));
  }

  private int toSafeInt(final Integer value) {
    return value == null ? 0 : value.intValue();
  }

  private BigDecimal getShipsPerYear(final InlandShipping shipping, final ShippingLaden laden,
      final Function<InlandShipping, TimeUnit> timeUnitFunction,
      final ToIntFunction<InlandShipping> numberOfShipsPerTimeUnitFunction,
      final ToIntFunction<InlandShipping> percentageLadenFunction) {
    final BigDecimal shipsAtoBPerYear = BigDecimal
        .valueOf(timeUnitFunction.apply(shipping).getPerYear(numberOfShipsPerTimeUnitFunction.applyAsInt(shipping)));
    final int percentageAtoB = percentageLadenFunction.applyAsInt(shipping);
    final BigDecimal fraction = BigDecimal.valueOf(laden == ShippingLaden.LADEN ? percentageAtoB : 100 - percentageAtoB).divide(PERCENT_TO_FRACTION);
    return shipsAtoBPerYear.multiply(fraction);
  }

  private Map<Substance, BigDecimal> getInlandRouteEmissionValues(final boolean reverse, final InlandWaterway waterway,
      final ShippingLaden laden, final InlandShippingRouteEmissionPoint routePoint, final InlandShipping shipping, final BigDecimal shipsPerYear)
      throws AeriusException {
    final Map<Substance, BigDecimal> emissions = new EnumMap<>(Substance.class);
    final Map<Substance, Double> emissionFactors = determineRouteEmissionFactors(reverse, waterway, laden, shipping);
    final BigDecimal segmentLength = BigDecimal.valueOf(routePoint.getSegmentLength());
    final BigDecimal lockFactor = BigDecimal.valueOf(routePoint.getLockFactor());
    emissionFactors.forEach(
        (key, value) -> emissions.put(key, BigDecimal.valueOf(value).multiply(segmentLength).multiply(lockFactor).multiply(shipsPerYear)));
    return emissions;
  }

  private Map<Substance, Double> determineRouteEmissionFactors(final boolean reverse, final InlandWaterway waterway, final ShippingLaden laden,
      final InlandShipping shipping) throws AeriusException {
    Map<Substance, Double> emissions;
    if (shipping instanceof StandardInlandShipping) {
      final String waterwayCode = waterway.getWaterwayCode();
      final WaterwayDirection waterwayDirection = waterway.getDirection();
      final WaterwayDirection actualDirection = waterwayDirection != null && reverse ? waterwayDirection.getOpposite() : waterwayDirection;
      emissions = emissionFactorSupplier.getInlandShippingRouteEmissionFactors(waterwayCode, actualDirection, laden,
          ((StandardInlandShipping) shipping).getShipCode());
    } else if (shipping instanceof CustomInlandShipping) {
      final CustomInlandShipping customShipping = (CustomInlandShipping) shipping;
      if (reverse) {
        emissions = getCustomEmissionFactors(customShipping.getEmissionPropertiesBtoA(), laden);
      } else {
        emissions = getCustomEmissionFactors(customShipping.getEmissionPropertiesAtoB(), laden);
      }
    } else {
      throw new IllegalArgumentException("Unexpected InlandShipping type");
    }
    return emissions;
  }

  private Map<Substance, Double> getCustomEmissionFactors(final CustomInlandShippingEmissionProperties emissionProperties,
      final ShippingLaden laden) {
    Map<Substance, Double> emissions;
    if (laden == ShippingLaden.LADEN) {
      emissions = emissionProperties.getEmissionFactorsLaden();
    } else {
      emissions = emissionProperties.getEmissionFactorsEmpty();
    }
    return emissions;
  }

}
