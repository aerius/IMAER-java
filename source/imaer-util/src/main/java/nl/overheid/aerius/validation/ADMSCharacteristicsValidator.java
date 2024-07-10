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

import nl.overheid.aerius.shared.domain.v2.characteristics.ADMSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.characteristics.adms.ADMSLimits;
import nl.overheid.aerius.shared.domain.v2.characteristics.adms.SourceType;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.geojson.LineString;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.geojson.Polygon;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.util.GeometryUtil;

/**
 * Class to validate ADMS source characteristics
 */
class ADMSCharacteristicsValidator extends CharacteristicsValidator<ADMSSourceCharacteristics> {

  ADMSCharacteristicsValidator(final List<AeriusException> errors, final List<AeriusException> warnings, final String sourceId) {
    super(errors, warnings, sourceId);
  }

  @Override
  boolean validate(final ADMSSourceCharacteristics characteristics, final Geometry sourceGeometry) {
    boolean valid = validateGeometry(characteristics, sourceGeometry);
    valid = validateADMSHeatContent(characteristics) && valid;
    validateADMSVolumeHeight(characteristics);
    return valid;
  }

  private boolean validateADMSHeatContent(final ADMSSourceCharacteristics characteristics) {
    boolean valid = true;
    if (expectValidHeatContent(characteristics.getSourceType()) &&
        (characteristics.getSpecificHeatCapacity() < ADMSLimits.SOURCE_SPECIFIC_HEAT_CAPACITY_MINIMUM
            || characteristics.getSpecificHeatCapacity() > ADMSLimits.SOURCE_SPECIFIC_HEAT_CAPACITY_MAXIMUM)) {
      errors.add(new AeriusException(ImaerExceptionReason.HEAT_CAPACITY_OUT_OF_RANGE, sourceId,
          String.valueOf(characteristics.getSpecificHeatCapacity()), String.valueOf(ADMSLimits.SOURCE_SPECIFIC_HEAT_CAPACITY_MINIMUM),
          String.valueOf(ADMSLimits.SOURCE_SPECIFIC_HEAT_CAPACITY_MAXIMUM)));
      valid = false;
    }
    return valid;
  }

  private boolean expectValidHeatContent(final SourceType sourceType) {
    return sourceType != SourceType.VOLUME && sourceType != SourceType.ROAD;
  }

  /**
   * Warn if the height is larger than half the vertical height (or 2 * height is larger than the vertical dimension).
   *
   * @param characteristics
   */
  private void validateADMSVolumeHeight(final ADMSSourceCharacteristics characteristics) {
    if (characteristics.getSourceType() == SourceType.VOLUME && ((characteristics.getHeight() * 2) - characteristics.getVerticalDimension()) > 0) {
      warnings.add(new AeriusException(ImaerExceptionReason.SOURCE_VOLUME_FLOATING, sourceId));
    }
  }

  private boolean validateGeometry(final ADMSSourceCharacteristics characteristics, final Geometry sourceGeometry) {
    return switch (characteristics.getSourceType()) {
    case POINT, JET -> validPointGeometry(sourceGeometry);
    case LINE, ROAD -> validLineGeometry(sourceGeometry);
    case AREA, VOLUME -> validPolygonGeometry(sourceGeometry);
    };
  }

  private boolean validPointGeometry(final Geometry sourceGeometry) {
    boolean valid = true;
    if (!(sourceGeometry instanceof Point)) {
      errors.add(new AeriusException(ImaerExceptionReason.GML_GEOMETRY_NOT_PERMITTED, sourceId));
      valid = false;
    }
    return valid;
  }

  private boolean validLineGeometry(final Geometry sourceGeometry) {
    boolean valid = true;
    if (!(sourceGeometry instanceof LineString)) {
      errors.add(new AeriusException(ImaerExceptionReason.GML_GEOMETRY_NOT_PERMITTED, sourceId));
      valid = false;
    }
    return valid;
  }

  private boolean validPolygonGeometry(final Geometry sourceGeometry) {
    boolean valid = true;
    if (sourceGeometry instanceof final Polygon polygon) {
      valid = validPolygon(polygon);
    } else {
      errors.add(new AeriusException(ImaerExceptionReason.GML_GEOMETRY_NOT_PERMITTED, sourceId));
      valid = false;
    }
    return valid;
  }

  private boolean validPolygon(final Polygon polygon) {
    boolean valid = true;
    // Quick check on nr of vertices. Convex hull always has same or less number of vertices as the original polygon.
    // So if original already has less than maximum, the convex hull will also be less.
    // + 1 as the begin and end point should be the same and only unique points should be considered anyway.
    if (polygon.getCoordinates()[0].length > ADMSLimits.MAX_POLYGON_CONVEX_VERTICES + 1) {
      try {
        // Convert to convex hull and check nr of vertices again. If this is above the maximum, then it's invalid.
        // SRID doesn't matter too much in this case.
        final Polygon convexHull = GeometryUtil.toConvexHull(polygon, 0);
        if (convexHull.getCoordinates()[0].length > ADMSLimits.MAX_POLYGON_CONVEX_VERTICES + 1) {
          errors.add(new AeriusException(ImaerExceptionReason.GEOMETRY_TOO_MANY_VERTICES, sourceId));
          valid = false;
        }
      } catch (final AeriusException e) {
        errors.add(e);
        valid = false;
      }
    }
    return valid;
  }

}
