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

import java.util.ArrayList;
import java.util.List;

import nl.overheid.aerius.shared.ImaerConstants;
import nl.overheid.aerius.shared.MathUtil;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.shared.geometry.EmissionSourceGeometryLimits;
import nl.overheid.aerius.shared.geometry.GeometryCalculator;
import nl.overheid.aerius.util.GeometryCalculatorImpl;

/**
 * Util class to check limits.
 */
public final class EmissionSourceCheckLimits {

  private static final GeometryCalculator GEOMETRY_CALCULATOR = new GeometryCalculatorImpl();

  private EmissionSourceCheckLimits() {
    // Util class.
  }

  /**
   * Checks if the given number of sources conforms to the limits. If a limit is exceeded an AeriusException is thrown.
   *
   * @param numberOfSources the number of sources to check.
   * @param limits the limits to check against.
   * @throws AeriusException throws exception if a limit is exceeded
   */
  public static void check(final int numberOfSources, final EmissionSourceGeometryLimits limits) throws AeriusException {
    if (!limits.isWithinMaxSourcesLimit(numberOfSources)) {
      throw new AeriusException(ImaerExceptionReason.LIMIT_SOURCES_EXCEEDED, String.valueOf(limits.getMaxSources()),
          String.valueOf(numberOfSources));
    }
  }

  /**
   * Checks if the given emission source list conforms to the geometric limits of max line length and polygon size.
   * Returns a list of exceptions for sources that exceed the limitations.
   *
   * @param emissionSourceList source list to check
   * @param limits the limits to check against.
   * @return list of geometry exceptions.
   */
  public static List<AeriusException> checkGeometries(final List<EmissionSourceFeature> emissionSourceList, final EmissionSourceGeometryLimits limits) {
    final List<AeriusException> exceptions = new ArrayList<>();
    for (final EmissionSourceFeature emissionSourceFeature : emissionSourceList) {
      final Geometry geo = emissionSourceFeature.getGeometry();

      if (geo != null) {
        switch (geo.type()) {
        case LINESTRING:
          final double length = GEOMETRY_CALCULATOR.determineMeasure(geo);
          if (!limits.isWithinLineLengthLimit(length)) {
            exceptions.add(new AeriusException(ImaerExceptionReason.LIMIT_LINE_LENGTH_EXCEEDED, emissionSourceFeature.getProperties().getLabel(),
                String.valueOf(limits.getMaxLineLength()), String.valueOf(length)));
          }
          break;
        case POLYGON:
          final double area = GEOMETRY_CALCULATOR.determineMeasure(geo);
          final int surfaceHa = MathUtil.round(area / ImaerConstants.M2_TO_HA);
          if (!limits.isWithinPolygonSurfaceLimit(surfaceHa)) {
            exceptions.add(new AeriusException(ImaerExceptionReason.LIMIT_POLYGON_SURFACE_EXCEEDED, emissionSourceFeature.getProperties().getLabel(),
                String.valueOf(limits.getMaxPolygonSurface()), String.valueOf(surfaceHa)));
          }
          break;
        default:
          // no checks for other geometries.
        }
      }
    }
    return exceptions;
  }

}
