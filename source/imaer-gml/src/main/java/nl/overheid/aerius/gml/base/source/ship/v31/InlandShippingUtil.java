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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.aerius.shared.domain.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.InlandWaterway;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.InlandWaterwayUtil;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

public class InlandShippingUtil {
  private static final Logger LOG = LoggerFactory.getLogger(InlandShippingUtil.class);

  private final GMLInlandShippingSupplier inlandShippingSupplier;
  private final List<AeriusException> warnings;
  private final Map<Geometry, InlandWaterway> geometryWaterwayCache = new HashMap<>();

  public InlandShippingUtil(final GMLInlandShippingSupplier inlandShippingSupplier, final List<AeriusException> warnings) {
    this.inlandShippingSupplier = inlandShippingSupplier;
    this.warnings = warnings;
  }

  /**
   * Determine a waterway type based on the location of the given route. This will always return a waterway type even if the geometry is nowhere
   * near a water type.
   * <p>This method is only relevant for IMAER < 2.0 because since that version waterway type is not determined automatically but required to be set
   * in the GML.
   *
   * @param sourceId
   * @param routeGeometry
   * @throws AeriusException
   */
  public InlandWaterway determineInlandWaterWayType(final String sourceId, final Geometry routeGeometry) throws AeriusException {
    final InlandWaterway way;
    if (geometryWaterwayCache.containsKey(routeGeometry)) {
      way = geometryWaterwayCache.get(routeGeometry);
    } else {
      final List<InlandWaterway> ways = inlandShippingSupplier.suggestInlandShippingWaterway(routeGeometry);
      final AeriusException ae = InlandWaterwayUtil.detectInconclusiveRoute(sourceId, ways);
      if (ae != null) {
        warnings.add(ae);
      }
      // Should never happen.
      if (ways.isEmpty()) {
        LOG.error("No type of waterway could be established for: {}.", routeGeometry.toString());
        throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR);
      } else {
        way = ways.get(0);
        geometryWaterwayCache.put(routeGeometry, way);
        LOG.debug("Keys in cache: {}", geometryWaterwayCache.keySet().size());
      }
    }
    return way;
  }

}
