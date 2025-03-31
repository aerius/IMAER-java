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
package nl.overheid.aerius.gml.base.characteristics;

import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.geojson.LineString;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;

/**
 * Utility to handle the legacy variant of handling the OPS spread value.
 *
 * In older versions of OPS the spread parameter in the input data for point sources was ignored and calculated within OPS as being half the
 * emission height.
 * With newer OPS versions (since 5.3.1) the spread value in the OPS input file is read and not calculated.
 * This means the application should pass the desired spread value.
 * For older IMAER versions (prior to 6) for point sources it means that value is derived from the emission height.
 * Even if the spread value of a point source is set in the IMAER file it will be ignored.
 * For non-point/line sources and if spread was not set in older IMAER versions (< 6.0), the sector default will be used.
 * This is compatible with how it worked prior to this change.
 * For newer IMAER versions (>= 6.0) if the spread is not present it will be half of the emission height.
 */
final class OPSSpreadUtil {

  private final boolean legacySpread;

  public OPSSpreadUtil(final boolean legacySpread) {
    this.legacySpread = legacySpread;
  }

  public Double getSpread(final IsGmlBaseOPSSourceCharacteristics characteristics, final Double defaultSpread, final Geometry geometry) {
    final Double spread = characteristics.getSpread();
    final double emissionHeight = characteristics.getEmissionHeight();

    if (legacySpread) {
      if (geometry instanceof Point || geometry instanceof LineString) {
        return getSourceSpread(null, emissionHeight, defaultSpread);
      } else {
        return getLegacySpread(characteristics, defaultSpread);
      }
    } else {
      return getSourceSpread(spread, emissionHeight, defaultSpread);
    }
  }

  private static Double getLegacySpread(final IsGmlBaseOPSSourceCharacteristics characteristics, final Double defaultSpread) {
    final Double spread = characteristics.getSpread();

    return spread == null ? defaultSpread : spread;
  }

  private static Double getSourceSpread(final Double spread, final double emissionHeight, final Double defaultSpread) {
    return spread == null ? (emissionHeight > 0 ? emissionHeight / 2.0 : defaultSpread) : spread;
  }
}
