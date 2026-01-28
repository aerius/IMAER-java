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
package nl.overheid.aerius.gml.base.source.road;

import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.source.road.CustomVehicles;

/**
 * Utility for converting removed vehicle codes to custom vehicles.
 */
public final class RemovedVehicleUtil {

  private RemovedVehicleUtil() {
    // Util class
  }

  /**
   * Converts a specific vehicle with a removed code to a custom vehicle with zero emissions.
   *
   * @param sv the specific vehicle to convert
   * @param vehicleCode the removed vehicle code
   * @return a custom vehicle preserving the vehicle count and time unit
   */
  public static CustomVehicles toCustomVehicles(final IsGmlSpecificVehicle sv, final String vehicleCode) {
    final CustomVehicles custom = new CustomVehicles();
    custom.setTimeUnit(TimeUnit.valueOf(sv.getTimeUnit().name()));
    custom.setVehiclesPerTimeUnit(sv.getVehiclesPerTimeUnit());
    custom.setDescription("Voormalig " + vehicleCode);
    return custom;
  }

}
