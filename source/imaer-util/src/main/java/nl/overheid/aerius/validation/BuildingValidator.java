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

import nl.overheid.aerius.shared.domain.ops.OPSLimits;
import nl.overheid.aerius.shared.domain.v2.building.Building;
import nl.overheid.aerius.shared.domain.v2.building.BuildingFeature;
import nl.overheid.aerius.shared.domain.v2.building.BuildingLimits;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.geojson.Polygon;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

public final class BuildingValidator {

  BuildingValidator() {
    // Util class
  }

  /**
   * @Deprecated Use the variant with a BuildingLimits parameter instead.
   * This version uses OPS buildings limits as default.
   */
  @Deprecated
  public static void validateBuildings(final List<BuildingFeature> buildings, final List<AeriusException> errors,
      final List<AeriusException> warnings) {
    validateBuildings(buildings, OPSLimits.INSTANCE, errors, warnings);
  }

  public static void validateBuildings(final List<BuildingFeature> buildings, final BuildingLimits buildingLimits,
      final List<AeriusException> errors, final List<AeriusException> warnings) {
    for (final BuildingFeature feature : buildings) {
      checkBuildingGeometry(feature, buildingLimits, errors);
      checkBuildingHeight(feature, buildingLimits, errors, warnings);
      checkBuildingDiameter(feature, buildingLimits, errors);
    }
  }

  private static void checkBuildingGeometry(final BuildingFeature feature, final BuildingLimits buildingLimits,
      final List<AeriusException> errors) {
    // Only polygon and point geometries are supported.
    if (!(feature.getGeometry() instanceof Polygon
        || (buildingLimits.isCircularBuildingSupported() && feature.getGeometry() instanceof Point))) {
      errors.add(new AeriusException(ImaerExceptionReason.GML_GEOMETRY_NOT_PERMITTED, feature.getProperties().getLabel()));
    }
  }

  private static void checkBuildingHeight(final BuildingFeature feature, final BuildingLimits buildingLimits,
      final List<AeriusException> errors, final List<AeriusException> warnings) {
    final Building building = feature.getProperties();
    if (building.getHeight() < buildingLimits.buildingHeightMinimum()) {
      errors.add(new AeriusException(ImaerExceptionReason.BUILDING_HEIGHT_TOO_LOW, building.getLabel()));
    } else if (building.getHeight() == 0) {
      // This warning only comes into play when the normal building height minimum is 0 or less.
      // Some models have a higher minimum height, and in that case we don't want this warning to pop up.
      // Other models do allow it, but modeling it that way would be a bit weird, hence the warning.
      warnings.add(new AeriusException(ImaerExceptionReason.BUILDING_HEIGHT_ZERO, building.getLabel()));
    } else if (building.getHeight() > buildingLimits.buildingHeightMaximum()) {
      errors.add(new AeriusException(ImaerExceptionReason.BUILDING_HEIGHT_TOO_HIGH, building.getLabel()));
    }
  }

  private static void checkBuildingDiameter(final BuildingFeature feature, final BuildingLimits buildingLimits,
      final List<AeriusException> errors) {
    final Building building = feature.getProperties();
    // When the geometry is a point, that indicates that a circular building is defined.
    // A circular building consists of a point and a positive diameter.
    final double diameter = building.getDiameter();
    if (feature.getGeometry() instanceof Point
        && (diameter <= buildingLimits.buildingDiameterMinimum() || diameter > buildingLimits.buildingDiameterMaximum())) {
      errors.add(new AeriusException(ImaerExceptionReason.CIRCULAR_BUILDING_INCORRECT_DIAMETER, building.getLabel()));
    }
  }

}
