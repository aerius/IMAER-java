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
package nl.overheid.aerius.shared.geometry;

import java.io.Serializable;

/**
 * Limits imposed on the emission sources.
 */
public class EmissionSourceLimits implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Maximum length of a line in meters.
   */
  private int maxLineLength;

  /**
   * Maximum surface of a polygon in hectare.
   */
  private int maxPolygonSurface;

  /**
   * Maximum number of sources in a single list.
   */
  private int maxSources;

  public int getMaxLineLength() {
    return maxLineLength;
  }

  public int getMaxPolygonSurface() {
    return maxPolygonSurface;
  }

  public int getMaxSources() {
    return maxSources;
  }

  /**
   * Returns true if the given length is smaller or equals to the max length.
   *
   * @param lineLength to check
   * @return true if length within limit
   */
  public boolean isWithinLineLengthLimit(final double lineLength) {
    return lineLength <= maxLineLength;
  }

  /**
   * Returns true if the given surface is smaller or equals to the max surface.
   *
   * @param polygonSurface to check
   * @return true if surface within limit
   */
  public boolean isWithinPolygonSurfaceLimit(final int polygonSurface) {
    return polygonSurface <= maxPolygonSurface;
  }

  /**
   * Returns true if the given count of sources is smaller or equal to the max
   * number of sources allowed. To use this method in context where the return
   * value is used to enable/disable functionality to add more sources use as
   * argument: the count to check.
   *
   * @param sources count of sources
   * @return true if count within limit
   */
  public boolean isWithinMaxSourcesLimit(final int sources) {
    return sources <= maxSources;
  }

  public void setMaxLineLength(final int maxLineLength) {
    this.maxLineLength = maxLineLength;
  }

  public void setMaxPolygonSurface(final int maxPolygonSurface) {
    this.maxPolygonSurface = maxPolygonSurface;
  }

  public void setMaxSources(final int maxSources) {
    this.maxSources = maxSources;
  }
}
