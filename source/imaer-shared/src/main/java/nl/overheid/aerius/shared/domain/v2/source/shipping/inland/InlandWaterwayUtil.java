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
package nl.overheid.aerius.shared.domain.v2.source.shipping.inland;

import java.util.List;

import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Util class for {@link InlandWaterway} related operations.
 */
public final class InlandWaterwayUtil {

  private InlandWaterwayUtil() {
    // util class.
  }

  /**
   * If the list contains more then 1 result it means multiple water way types were found. In that case an {@link AeriusException} with
   * {@link ImaerExceptionReason#INLAND_SHIPPING_WATERWAY_INCONCLUSIVE} is returned.
   * @param sourceId the id of the emission source
   * @param result list of water way types.
   * @return {@link AeriusException} in case multiple water ways were in the list otherwise returns null.
   */
  public static AeriusException detectInconclusiveRoute(final String sourceId, final List<InlandWaterway> result) {
    final int size = result.size();
    if (size > 1) {
      final StringBuilder names = new StringBuilder();
      for (int i = 1; i < size; i++) {
        if (i > 1) {
          names.append(", ");
        }
        names.append(result.get(i).getWaterwayCode());
      }
      return new AeriusException(
          ImaerExceptionReason.INLAND_SHIPPING_WATERWAY_INCONCLUSIVE, sourceId, result.get(0).getWaterwayCode(), names.toString());
    }
    return null;
  }
}
