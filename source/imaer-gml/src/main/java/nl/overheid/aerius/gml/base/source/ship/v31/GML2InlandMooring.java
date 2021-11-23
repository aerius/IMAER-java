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
package nl.overheid.aerius.gml.base.source.ship.v31;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import nl.overheid.aerius.gml.base.AbstractGML2Specific;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.source.InlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MooringInlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.InlandWaterway;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.MooringRouteDirection;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.StandardInlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.StandardMooringInlandShipping;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Conversion of GML inland mooring object to data object.
 */
public class GML2InlandMooring<T extends IsGmlMooringInlandShippingEmissionSource>
    extends AbstractGML2Specific<T, MooringInlandShippingEmissionSource> {

  private static final int DEFAULT_ROUTE_SECTOR_ID = 7620;

  private final GML2Route gml2route;

  /**
   * @param conversionData The conversion data to use.
   */
  public GML2InlandMooring(final GMLConversionData conversionData) {
    super(conversionData);
    gml2route = new GML2Route(conversionData.getSrid());
  }

  @Override
  public MooringInlandShippingEmissionSource convert(final T source) throws AeriusException {
    final MooringInlandShippingEmissionSource emissionSource = new MooringInlandShippingEmissionSource();
    final AtomicInteger routeIndexTracker = new AtomicInteger(1);
    for (final IsGmlProperty<IsGmlMooringInlandShipping> mooringInlandShippingProperty : source.getShips()) {
      addVesselGroup(mooringInlandShippingProperty.getProperty(), emissionSource, source, routeIndexTracker);
    }
    return emissionSource;
  }

  private void addVesselGroup(final IsGmlMooringInlandShipping mooringInlandShipping, final MooringInlandShippingEmissionSource emissionSource,
      final T source, final AtomicInteger routeIndexTracker) throws AeriusException {
    final StandardMooringInlandShipping vesselGroupEmissionValues = new StandardMooringInlandShipping();
    vesselGroupEmissionValues.setDescription(mooringInlandShipping.getDescription());
    vesselGroupEmissionValues.setShipCode(mooringInlandShipping.getCode());
    vesselGroupEmissionValues.setAverageResidenceTime(mooringInlandShipping.getAverageResidenceTime());
    if (mooringInlandShipping.getRoutes() != null) {
      for (final IsGmlProperty<IsGmlInlandShippingRoute> isrp : mooringInlandShipping.getRoutes()) {
        addRoute(isrp.getProperty(), vesselGroupEmissionValues, source, routeIndexTracker);
      }
    }
    // Assume movements in arrival and departure match, and we can divide by 2 to get to the number of ships moored.
    vesselGroupEmissionValues.setShipsPerTimeUnit(vesselGroupEmissionValues.getShipsPerTimeUnit() / 2);
    emissionSource.getSubSources().add(vesselGroupEmissionValues);
  }

  private void addRoute(final IsGmlInlandShippingRoute inlandShippingRoute, final StandardMooringInlandShipping vesselGroupEmissionValues,
      final T source, final AtomicInteger routeIndexTracker) throws AeriusException {
    final String sourceId = source.getId();
    final InlandShippingEmissionSource route = gml2route.findRoute(
        inlandShippingRoute.getRoute(), getConversionData().getInlandRoutes(), sourceId, "InlandMooringRoute",
        () -> this.createRoute(inlandShippingRoute.getInlandWaterwayProperty(), source.getLabel(), routeIndexTracker));
    final StandardInlandShipping imr = new StandardInlandShipping();
    imr.setDescription(vesselGroupEmissionValues.getDescription());
    imr.setShipCode(vesselGroupEmissionValues.getShipCode());
    if (inlandShippingRoute.getDirection() == MooringRouteDirection.DEPART) {
      imr.setMovementsAtoBPerTimeUnit(inlandShippingRoute.getShippingMovementsPerTimeUnit());
      imr.setPercentageLadenAtoB(inlandShippingRoute.getPercentageLaden());
      imr.setMovementsBtoAPerTimeUnit(0);
      imr.setPercentageLadenBtoA(0);
    } else {
      imr.setMovementsAtoBPerTimeUnit(0);
      imr.setPercentageLadenAtoB(0);
      imr.setMovementsBtoAPerTimeUnit(inlandShippingRoute.getShippingMovementsPerTimeUnit());
      imr.setPercentageLadenBtoA(inlandShippingRoute.getPercentageLaden());
    }
    imr.setTimeUnitAtoB(TimeUnit.valueOf(inlandShippingRoute.getTimeUnit().name()));
    imr.setTimeUnitBtoA(TimeUnit.valueOf(inlandShippingRoute.getTimeUnit().name()));
    route.getSubSources().add(imr);
    route.setMooringAId(sourceId);
    addRouteShipsToMooringShips(inlandShippingRoute, vesselGroupEmissionValues);
    additionalRouteStuff(inlandShippingRoute, route, source);
  }

  protected void additionalRouteStuff(final IsGmlInlandShippingRoute inlandShippingRoute, final InlandShippingEmissionSource route, final T source)
      throws AeriusException {
    // NO-OP for this version
  }

  private void addRouteShipsToMooringShips(final IsGmlInlandShippingRoute inlandShippingRoute,
      final StandardMooringInlandShipping vesselGroupEmissionValues) {
    // At this point we're just adding each departure and arrival. This will have to be divided by 2 to get the moored ships later on.
    // (not doing it right away to avoid some integer division shenanigans).
    final TimeUnit gmlTimeUnit = TimeUnit.valueOf(inlandShippingRoute.getTimeUnit().name());
    if (vesselGroupEmissionValues.getTimeUnit() == null) {
      vesselGroupEmissionValues.setTimeUnit(gmlTimeUnit);
      vesselGroupEmissionValues.setShipsPerTimeUnit(inlandShippingRoute.getShippingMovementsPerTimeUnit());
      vesselGroupEmissionValues.setPercentageLaden(inlandShippingRoute.getPercentageLaden());
    } else if (vesselGroupEmissionValues.getTimeUnit() == gmlTimeUnit) {
      final int shipsExisting = vesselGroupEmissionValues.getShipsPerTimeUnit();
      final int shipsToAdd = inlandShippingRoute.getShippingMovementsPerTimeUnit();
      final int shipsTotal = shipsExisting + shipsToAdd;
      vesselGroupEmissionValues.setShipsPerTimeUnit(shipsTotal);
      final int avgPercentageLaden = determinePercentageLaden(shipsExisting, vesselGroupEmissionValues.getPercentageLaden(),
          shipsToAdd, inlandShippingRoute.getPercentageLaden());
      vesselGroupEmissionValues.setPercentageLaden(avgPercentageLaden);
    } else {
      final int shipsPerYearExisting = vesselGroupEmissionValues.getTimeUnit().getPerYear(vesselGroupEmissionValues.getShipsPerTimeUnit());
      final int shipsPerYearToAdd = gmlTimeUnit.getPerYear(inlandShippingRoute.getShippingMovementsPerTimeUnit());
      final int shipsTotal = shipsPerYearExisting + shipsPerYearToAdd;
      vesselGroupEmissionValues.setTimeUnit(TimeUnit.YEAR);
      vesselGroupEmissionValues.setShipsPerTimeUnit(shipsTotal);
      final int avgPercentageLaden = determinePercentageLaden(shipsPerYearExisting, vesselGroupEmissionValues.getPercentageLaden(),
          shipsPerYearToAdd, inlandShippingRoute.getPercentageLaden());
      vesselGroupEmissionValues.setPercentageLaden(avgPercentageLaden);
    }
  }

  private int determinePercentageLaden(final int shipsExisting, final int percentageLadenExisting, final int shipsToAdd,
      final int percentageLadenToAdd) {
    return (percentageLadenExisting * shipsExisting + percentageLadenToAdd * shipsToAdd) / (shipsExisting + shipsToAdd);
  }

  protected InlandShippingEmissionSource createRoute(final IsGmlProperty<IsGmlInlandWaterway> waterwayProperty, final String sourceLabel,
      final AtomicInteger routeIndexTracker) {
    final InlandShippingEmissionSource mooringRoute = new InlandShippingEmissionSource();
    final InlandWaterway waterway = GML2InlandUtil.toInlandWaterway(waterwayProperty);
    mooringRoute.setSectorId(DEFAULT_ROUTE_SECTOR_ID);
    mooringRoute.setWaterway(waterway);
    mooringRoute.setLabel(constructLabel(sourceLabel, routeIndexTracker));
    return mooringRoute;
  }

  protected String constructLabel(final String sourceLabel, final AtomicInteger routeIndexTracker) {
    // Routes did not have a name in old GML, so come up with our own label
    // Might not be completely i18n, but works for both NL and EN at least.
    final int currentIndex = routeIndexTracker.getAndIncrement();
    final String routeDescription = "Route " + currentIndex;
    return Stream.of(sourceLabel, routeDescription)
        .filter(x -> x != null && !x.isBlank())
        .collect(Collectors.joining("; "));
  }

}
