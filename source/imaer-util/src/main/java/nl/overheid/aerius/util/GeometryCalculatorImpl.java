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
package nl.overheid.aerius.util;

import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.geometry.GeometryCalculator;

public class GeometryCalculatorImpl implements GeometryCalculator {

  @Override
  public double determineMeasure(final Geometry geometry) {
    double measure = 0.0;
    if (geometry != null) {
      switch (geometry.type()) {
      case POINT:
        measure = 1.0;
        break;
      case LINESTRING:
        measure = getLength(geometry);
        break;
      case POLYGON:
        measure = getArea(geometry);
        break;
      default:
        break;
      }
    }
    return measure;
  }

  private double getLength(final Geometry geometry) {
    try {
      return toJtsGeometry(geometry).getLength();
    } catch (final AeriusException | IllegalArgumentException e) {
      // Incorrect geometry, this should have been detected before we get to this point. Just return 0.0 now.
      return 0.0;
    }
  }

  private double getArea(final Geometry geometry) {
    try {
      return toJtsGeometry(geometry).getArea();
    } catch (final AeriusException | IllegalArgumentException e) {
      // Incorrect geometry, this should have been detected before we get to this point. Just return 0.0 now.
      return 0.0;
    }
  }

  private org.locationtech.jts.geom.Geometry toJtsGeometry(final Geometry geometry) throws AeriusException {
    return GeometryUtil.getGeometry(geometry);
  }

}
