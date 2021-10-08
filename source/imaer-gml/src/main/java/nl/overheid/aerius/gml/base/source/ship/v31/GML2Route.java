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

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.overheid.aerius.gml.base.geo.GML2Geometry;
import nl.overheid.aerius.gml.base.geo.GmlLineString;
import nl.overheid.aerius.shared.domain.v2.geojson.LineString;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
public final class GML2Route {

  private static final Logger LOG = LoggerFactory.getLogger(GML2Route.class);

  private final GML2Geometry gml2geometry;

  public GML2Route(final int srid) {
    gml2geometry = new GML2Geometry(srid);
  }

  public <T extends EmissionSource> T findRoute(final GmlLineString gmlRoute, final Map<EmissionSourceFeature, T> addToMap,
      final String sourceId, final String prefix, final Supplier<T> routePropertiesCreator) throws AeriusException {
    try {
      final EmissionSourceFeature route = toRouteFeature(gmlRoute);
      for (final Entry<EmissionSourceFeature, T> entry : addToMap.entrySet()) {
        if (Arrays.deepEquals(
            ((LineString) entry.getKey().getGeometry()).getCoordinates(),
            ((LineString) route.getGeometry()).getCoordinates())) {
          return entry.getValue();
        }
      }
      final T properties = routePropertiesCreator.get();
      route.setProperties(properties);
      route.setId(toShippingRouteId(addToMap.size(), prefix));
      addToMap.put(route, properties);
      return properties;
    } catch (final AeriusException e) {
      LOG.error("Invalid route geometry", e);
      throw new AeriusException(ImaerExceptionReason.GML_GEOMETRY_INVALID, sourceId);
    }
  }

  public EmissionSourceFeature toRouteFeature(final GmlLineString gmlRoute) throws AeriusException {
    final LineString geometry = gml2geometry.fromXMLLineString(gmlRoute);
    final EmissionSourceFeature feature = new EmissionSourceFeature();
    feature.setGeometry(geometry);
    return feature;
  }

  private String toShippingRouteId(final int existingInList, final String prefix) {
    return prefix + "." + existingInList;
  }

}
