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

import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.geo.GML2Geometry;
import nl.overheid.aerius.gml.base.geo.GmlLineString;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.source.InlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.InlandWaterway;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Conversion of GML inland mooring object to data object.
 *
 * This class will automagically set an inland waterway for inland routes if it wasn't available in the GML.
 * To be used in IMAER versions < 2.0
 */
public class GML2InlandMooringForceWaterway<T extends IsGmlMooringInlandShippingEmissionSource>
    extends GML2InlandMooring<T> {

  private static final int DEFAULT_ROUTE_SECTOR_ID = 7620;

  private final GML2Geometry gml2Geometry;

  /**
   * @param conversionData The conversion data to use.
   */
  public GML2InlandMooringForceWaterway(final GMLConversionData conversionData, final GML2Geometry gml2Geometry) {
    super(conversionData);
    this.gml2Geometry = gml2Geometry;
  }

  @Override
  protected void additionalRouteStuff(final IsGmlInlandShippingRoute inlandShippingRoute, final InlandShippingEmissionSource route, final T source)
      throws AeriusException {
    ensureWaterway(route, inlandShippingRoute.getRoute(), source.getId());
  }

  private void ensureWaterway(final InlandShippingEmissionSource route, final GmlLineString lineString, final String sourceId)
      throws AeriusException {
    if (route.getWaterway() == null) {
      final Geometry geometry = gml2Geometry.fromXMLLineString(lineString);
      final InlandWaterway waterway = getConversionData().determineInlandWaterway(sourceId, geometry);
      route.setWaterway(waterway);
    }
  }

  @Override
  protected InlandShippingEmissionSource createRoute(final IsGmlProperty<IsGmlInlandWaterway> waterwayProperty, final String sourceLabel,
      final AtomicInteger routeIndexTracker) {
    final InlandShippingEmissionSource mooringRoute = new InlandShippingEmissionSource();
    if (waterwayProperty != null) {
      final InlandWaterway waterway = GML2InlandUtil.toInlandWaterway(waterwayProperty);
      mooringRoute.setWaterway(waterway);
    }
    mooringRoute.setSectorId(DEFAULT_ROUTE_SECTOR_ID);
    mooringRoute.setLabel(constructLabel(sourceLabel, routeIndexTracker));
    return mooringRoute;
  }

}
