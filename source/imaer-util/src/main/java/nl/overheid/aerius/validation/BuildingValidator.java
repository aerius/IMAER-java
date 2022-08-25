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
package nl.overheid.aerius.validation;

import java.util.List;

import nl.overheid.aerius.shared.domain.v2.building.BuildingFeature;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

public final class BuildingValidator {

  private BuildingValidator() {
    // Util class
  }

  public static void validateBuildings(List<BuildingFeature> buildings, List<AeriusException> exceptions, List<AeriusException> warnings) {
    checkBuildingHeight(buildings, warnings);
  }

  private static void checkBuildingHeight(List<BuildingFeature> buildings, List<AeriusException> warnings) {
    for (final BuildingFeature building : buildings) {
      if (building.getProperties().getHeight() <= 0) {
        warnings.add(new AeriusException(ImaerExceptionReason.BUILDING_HEIGHT_ZERO, building.getProperties().getLabel()));
      }
    }
  }

}
