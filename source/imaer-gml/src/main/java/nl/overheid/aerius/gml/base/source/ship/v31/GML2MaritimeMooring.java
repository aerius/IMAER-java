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
import java.util.function.IntSupplier;

import nl.overheid.aerius.gml.base.AbstractGML2Specific;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.source.InlandMaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MaritimeMaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MooringMaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.StandardMaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.StandardMooringMaritimeShipping;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Conversion of GML maritime mooring object to data object.
 */
public class GML2MaritimeMooring<T extends IsGmlMooringMaritimeShippingEmissionSource>
    extends AbstractGML2Specific<T, MooringMaritimeShippingEmissionSource> {

  private static final int DEFAULT_SECTOR_INLAND_ROUTE = 7520;
  private static final int DEFAULT_SECTOR_MARITIME_ROUTE = 7530;
  private static final int IN_AND_OUTGOING_FACTOR = 2;

  private final GML2Route gml2route;

  /**
   * @param conversionData The conversion data to use.
   */
  public GML2MaritimeMooring(final GMLConversionData conversionData) {
    super(conversionData);
    gml2route = new GML2Route(conversionData.getSrid());
  }

  @Override
  public MooringMaritimeShippingEmissionSource convert(final T source) throws AeriusException {
    final MooringMaritimeShippingEmissionSource emissionValues = new MooringMaritimeShippingEmissionSource();
    final AtomicInteger inlandRouteIndexTracker = new AtomicInteger(1);
    for (final IsGmlProperty<IsGmlMooringMaritimeShipping> mooringMaritimeShippingProperty : source.getShips()) {
      final IsGmlMooringMaritimeShipping mooringMaritimeShipping = mooringMaritimeShippingProperty.getProperty();
      final StandardMooringMaritimeShipping vesselGroupEmissionValues = new StandardMooringMaritimeShipping();
      vesselGroupEmissionValues.setDescription(mooringMaritimeShipping.getDescription());
      vesselGroupEmissionValues.setShipCode(mooringMaritimeShipping.getCode());
      vesselGroupEmissionValues.setAverageResidenceTime(mooringMaritimeShipping.getAverageResidenceTime());
      vesselGroupEmissionValues.setShipsPerTimeUnit(mooringMaritimeShipping.getShipsPerTimeUnit());
      vesselGroupEmissionValues.setTimeUnit(TimeUnit.valueOf(mooringMaritimeShipping.getTimeUnit().name()));
      if (mooringMaritimeShipping.getInlandRoute() != null) {
        handleInlandRoute(mooringMaritimeShipping, source, inlandRouteIndexTracker);
      }
      handleMaritimeRoutes(mooringMaritimeShipping, source.getId());
      emissionValues.getSubSources().add(vesselGroupEmissionValues);
    }
    return emissionValues;
  }

  private void handleInlandRoute(final IsGmlMooringMaritimeShipping mooringMaritimeShipping,
      final T source, final AtomicInteger inlandRouteIndexTracker) throws AeriusException {
    final String sourceId = source.getId();
    final InlandMaritimeShippingEmissionSource route = gml2route.findRoute(mooringMaritimeShipping.getInlandRoute(),
        getConversionData().getMaritimeInlandRoutes(), sourceId, "MaritimeInlandMooringRoute",
        () -> createInlandRoute(source.getLabel(), inlandRouteIndexTracker));
    final StandardMaritimeShipping mr = new StandardMaritimeShipping();
    mr.setDescription(mooringMaritimeShipping.getDescription());
    mr.setShipCode(mooringMaritimeShipping.getCode());
    // Times 2 as in older versions of AERIUS the inland route part had to be used for both in- and outgoing ships.
    mr.setMovementsPerTimeUnit(mooringMaritimeShipping.getShipsPerTimeUnit() * IN_AND_OUTGOING_FACTOR);
    mr.setTimeUnit(TimeUnit.valueOf(mooringMaritimeShipping.getTimeUnit().name()));
    route.getSubSources().add(mr);
    route.setMooringAId(sourceId);
  }

  private InlandMaritimeShippingEmissionSource createInlandRoute(final String sourceLabel, final AtomicInteger inlandRouteIndexTracker) {
    final InlandMaritimeShippingEmissionSource source = new InlandMaritimeShippingEmissionSource();
    source.setSectorId(DEFAULT_SECTOR_INLAND_ROUTE);
    source.setLabel(constructLabel(sourceLabel, inlandRouteIndexTracker::getAndIncrement));
    return source;
  }

  private void handleMaritimeRoutes(final IsGmlMooringMaritimeShipping mooringMaritimeShipping,
      final String sourceId) throws AeriusException {
    if (mooringMaritimeShipping.getMaritimeRoutes() != null) {
      for (final IsGmlProperty<IsGmlMaritimeShippingRoute> msrp : mooringMaritimeShipping.getMaritimeRoutes()) {
        final IsGmlMaritimeShippingRoute gmlRoute = msrp.getProperty();
        final MaritimeMaritimeShippingEmissionSource route = gml2route.findRoute(gmlRoute.getRoute(),
            getConversionData().getMaritimeMaritimeRoutes(), sourceId, "MaritimeMaritimeMooringRoute", this::createMaritimeRoute);
        final StandardMaritimeShipping mr = new StandardMaritimeShipping();
        mr.setDescription(mooringMaritimeShipping.getDescription());
        mr.setShipCode(mooringMaritimeShipping.getCode());
        mr.setMovementsPerTimeUnit(gmlRoute.getShippingMovementsPerTimeUnit());
        mr.setTimeUnit(TimeUnit.valueOf(gmlRoute.getTimeUnit().name()));
        route.getSubSources().add(mr);
      }
    }
  }

  private MaritimeMaritimeShippingEmissionSource createMaritimeRoute() {
    final MaritimeMaritimeShippingEmissionSource source = new MaritimeMaritimeShippingEmissionSource();
    source.setSectorId(DEFAULT_SECTOR_MARITIME_ROUTE);
    // Maritime routes used to be global, so can't really tie them to 1 source.
    // Hence, use the global available routes to determine index, and don't use anything source specific.
    source.setLabel(constructLabel(null, () -> getConversionData().getMaritimeMaritimeRoutes().size() + 1));
    return source;
  }

  private String constructLabel(final String sourceLabel, final IntSupplier routeIndexTracker) {
    // Routes did not have a name in old GML, so come up with our own label
    // Might not be completely i18n, but works for both NL and EN at least.
    final int currentIndex = routeIndexTracker.getAsInt();
    final String routeDescription = "Route " + currentIndex;
    return constructLabelOf(sourceLabel, routeDescription);
  }

}
