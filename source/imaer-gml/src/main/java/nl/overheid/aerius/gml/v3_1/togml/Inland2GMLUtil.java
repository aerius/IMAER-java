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

import nl.overheid.aerius.gml.v3_1.source.ship.InlandWaterway;
import nl.overheid.aerius.gml.v3_1.source.ship.InlandWaterwayProperty;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.WaterwayDirection;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
final class Inland2GMLUtil {

  private Inland2GMLUtil() {
    //util class.
  }

  static InlandWaterwayProperty getWaterway(final nl.overheid.aerius.shared.domain.v2.source.shipping.inland.InlandWaterway waterwayType,
      final String sourceId) throws AeriusException {
    if (waterwayType == null || waterwayType.getWaterwayCode() == null) {
      throw new AeriusException(ImaerExceptionReason.GML_INLAND_WATERWAY_NOT_SET, sourceId);
    } else {
      final InlandWaterway waterway = new InlandWaterway();
      waterway.setType(waterwayType.getWaterwayCode());
      waterway.setDirection(waterwayType.getDirection() == WaterwayDirection.IRRELEVANT ? null : waterwayType.getDirection());
      return new InlandWaterwayProperty(waterway);
    }
  }
}
