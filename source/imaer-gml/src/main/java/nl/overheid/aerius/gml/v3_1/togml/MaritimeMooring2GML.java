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
import nl.overheid.aerius.gml.v3_1.source.TimeUnit;
import nl.overheid.aerius.gml.v3_1.source.ship.MooringMaritimeShipping;
import nl.overheid.aerius.gml.v3_1.source.ship.MooringMaritimeShippingEmissionSource;
import nl.overheid.aerius.gml.v3_1.source.ship.MooringMaritimeShippingProperty;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.StandardMooringMaritimeShipping;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 *
 */
class MaritimeMooring2GML extends SpecificSource2GML<nl.overheid.aerius.shared.domain.v2.source.MooringMaritimeShippingEmissionSource> {

  private final Geometry2GML geometry2gml;

  public MaritimeMooring2GML(final Geometry2GML geometry2gmL) {
    this.geometry2gml = geometry2gmL;
  }

  @Override
  public nl.overheid.aerius.gml.v3_1.source.EmissionSource convert(
      final nl.overheid.aerius.shared.domain.v2.source.MooringMaritimeShippingEmissionSource emissionSource) throws AeriusException {
    final MooringMaritimeShippingEmissionSource returnSource = new MooringMaritimeShippingEmissionSource();
    final List<nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.MooringMaritimeShipping> vesselGroups = emissionSource
        .getSubSources();
    final List<MooringMaritimeShippingProperty> mooringMaritimeShippings = new ArrayList<>(vesselGroups.size());

    for (final nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.MooringMaritimeShipping vesselGroup : vesselGroups) {
      final MooringMaritimeShipping gmlShip = new MooringMaritimeShipping();
      if (vesselGroup instanceof StandardMooringMaritimeShipping) {
        gmlShip.setCode(((StandardMooringMaritimeShipping) vesselGroup).getShipCode());
      }
      gmlShip.setShipsPerTimeUnit(vesselGroup.getShipsPerTimeUnit());
      gmlShip.setTimeUnit(TimeUnit.from(vesselGroup.getTimeUnit()));
      gmlShip.setDescription(vesselGroup.getDescription());
      gmlShip.setAverageResidenceTime(vesselGroup.getAverageResidenceTime());
      final int vesselGroupId = vesselGroups.indexOf(vesselGroup);
      // With refactoring of shipping this is no longer possible. v3_1.to_gml has to be removed at some point anyway...
//      gmlShip.setInlandRoute(
//          toInlandRoute(vesselGroup.getShipsOnInlandRoute().getShippingRoute(), emissionSource.getGmlId(), vesselGroupId));
//      gmlShip.setMaritimeRoutes(
//          toShippingRouteProperties(vesselGroup.getShipsOnMaritimeRoutes(), emissionSource.getGmlId(), vesselGroupId));
      //we're not adding emissionfactor/description to avoid impression that it will be used on import.
      mooringMaritimeShippings.add(new MooringMaritimeShippingProperty(gmlShip));
    }
    returnSource.setShips(mooringMaritimeShippings);
    return returnSource;
  }

//  private LineString toInlandRoute(final ShippingRouteFeature<MaritimeInlandMooringRoute> shippingRoute, final String sourceId,
//      final int vesselGroupId) throws AeriusException {
//    LineString lineString = null;
//    if (shippingRoute != null && shippingRoute.getGeometry() != null) {
//      lineString = geometry2gml.toXMLLineString(shippingRoute.getGeometry(), new LineString());
//      lineString.getGMLLineString().setId(sourceId + ".InlandRoute."
//          + vesselGroupId + ID_SEPARATOR + 0);
//    }
//    return lineString;
//  }
//
//  private List<MaritimeShippingRouteProperty> toShippingRouteProperties(final List<ShipsOnRoute<MaritimeMaritimeMooringRoute>> originalRoutes,
//      final String sourceId, final int vesselGroupId) throws AeriusException {
//    final List<MaritimeShippingRouteProperty> maritimeRoutes = new ArrayList<>(originalRoutes.size());
//    //keep track of an ID so we won't violate unique ID constraints.
//    //still depends on the id of vesselGroupEmissionValues being set right in UI however.
//    int currentId = 0;
//    for (final ShipsOnRoute<MaritimeMaritimeMooringRoute> mr : originalRoutes) {
//      final MaritimeShippingRoute maritimeRoute = new MaritimeShippingRoute();
//      final LineString route = geometry2gml.toXMLLineString(mr.getShippingRoute().getGeometry(), new LineString());
//      route.getGMLLineString().setId(sourceId + ".MaritimeRoute." + vesselGroupId + ID_SEPARATOR + currentId);
//      maritimeRoute.setRoute(route);
//      maritimeRoute.setShippingMovementsPerTimeUnit(mr.getMovementsPerTimeUnit());
//      maritimeRoute.setTimeUnit(TimeUnit.from(mr.getTimeUnit()));
//      maritimeRoutes.add(new MaritimeShippingRouteProperty(maritimeRoute));
//      currentId++;
//    }
//    return maritimeRoutes;
//  }

}
