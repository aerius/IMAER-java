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
package nl.overheid.aerius.gml.v3_1.togml;

import java.util.ArrayList;
import java.util.List;

import nl.overheid.aerius.gml.base.geo.Geometry2GML;
import nl.overheid.aerius.gml.v3_1.source.ship.MooringInlandShippingProperty;
import nl.overheid.aerius.shared.domain.v2.source.MooringInlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.MooringInlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.StandardMooringInlandShipping;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 *
 */
class InlandMooring2GML extends SpecificSource2GML<MooringInlandShippingEmissionSource> {

  private final Geometry2GML geometry2gmL;

  public InlandMooring2GML(final Geometry2GML geometry2gmL) {
    this.geometry2gmL = geometry2gmL;
  }

  @Override
  public nl.overheid.aerius.gml.v3_1.source.EmissionSource convert(final MooringInlandShippingEmissionSource emissionSource)
      throws AeriusException {
    final nl.overheid.aerius.gml.v3_1.source.ship.MooringInlandShippingEmissionSource returnSource = new nl.overheid.aerius.gml.v3_1.source.ship.MooringInlandShippingEmissionSource();
    final List<MooringInlandShipping> vesselGroups = emissionSource.getSubSources();
    final List<MooringInlandShippingProperty> mooringMaritimeShippings = new ArrayList<>(vesselGroups.size());

    for (final MooringInlandShipping vesselGroup : vesselGroups) {
      final nl.overheid.aerius.gml.v3_1.source.ship.MooringInlandShipping gmlShip = new nl.overheid.aerius.gml.v3_1.source.ship.MooringInlandShipping();
      if (vesselGroup instanceof StandardMooringInlandShipping) {
        gmlShip.setCode(((StandardMooringInlandShipping) vesselGroup).getShipCode());
      }
      gmlShip.setDescription(vesselGroup.getDescription());
      gmlShip.setAverageResidenceTime(vesselGroup.getAverageResidenceTime());
      final int vesselGroupId = vesselGroups.indexOf(vesselGroup);
      // With refactoring of shipping this is no longer possible. v3_1.to_gml has to be removed at some point anyway...
//      gmlShip.setRoutes(
//          toShippingRouteProperties(vesselGroup.getShipsOnRoute(), emissionSource.getGmlId(), vesselGroupId));
      //we're not adding emissionfactor/description to avoid impression that it will be used on import.
      mooringMaritimeShippings.add(new MooringInlandShippingProperty(gmlShip));
    }
    returnSource.setShips(mooringMaritimeShippings);
    return returnSource;
  }

//  private List<InlandShippingRouteProperty> toShippingRouteProperties(final List<? extends InlandShipsOnRoute> originalRoutes, final String sourceId,
//      final int vesselGroupId) throws AeriusException {
//    final List<InlandShippingRouteProperty> routes = new ArrayList<>(originalRoutes.size());
//    //keep track of an ID so we won't violate unique ID constraints.
//    //still depends on the id of vesselGroupEmissionValues being set right in UI however.
//    int currentId = 0;
//    for (final InlandShipsOnRoute imr : originalRoutes) {
//      final InlandShippingRoute route = new InlandShippingRoute();
//      final LineString routeGeometry = geometry2gmL.toXMLLineString(imr.getShippingRoute().getGeometry(), new LineString());
//      routeGeometry.getGMLLineString().setId(sourceId + ".InlandMooringRoute." + vesselGroupId + ID_SEPARATOR + currentId);
//      route.setRoute(routeGeometry);
//      route.setShippingMovementsPerTimeUnit(imr.getMovementsPerTimeUnit());
//      route.setTimeUnit(TimeUnit.from(imr.getTimeUnit()));
//      route.setPercentageLaden(imr.getPercentageLaden());
//      route.setDirection(imr.getDirection());
//      route.setInlandWaterwayProperty(Inland2GMLUtil.getWaterway(imr.getShippingRoute().getProperties().getWaterway(), sourceId));
//      routes.add(new InlandShippingRouteProperty(route));
//      currentId++;
//    }
//    return routes;
//  }

}
