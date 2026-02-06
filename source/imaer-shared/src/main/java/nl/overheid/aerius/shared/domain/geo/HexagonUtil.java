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
package nl.overheid.aerius.shared.domain.geo;

import java.util.function.DoubleUnaryOperator;

import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.geojson.Polygon;

/**
 * Utility class for hexagons.
 */
public final class HexagonUtil {

  private HexagonUtil() {}

  /**
   * Returns a Geometry with a hexagon conforming to the given Point, and rounds the points of the hexagon
   *
   * @see #createHexagon(Point, HexagonZoomLevel, boolean)
   */
  public static Polygon createHexagon(final Point point, final HexagonZoomLevel config) {
    return createHexagon(point, config, Math::round);
  }

  /**
   * Returns a Geometry with a hexagon conforming to the given Point and
   * HexagonConfiguration. The points of the hexagon are given in the following
   * order:
   * <pre>
   *    6 - 1
   *   /     \
   *  5   x   2
   *   \     /
   *    4 - 3
   *
   *  Where x is the point given as argument.
   * </pre>
   *
   * @param point Center of the hexagon
   * @param config the hexagon zoom level for level 1
   * @param roundFunction function to round the coordinates of the hexagon with
   * @return Returns the hexagon as an AERIUS polygon
   */
  public static Polygon createHexagon(final Point point, final HexagonZoomLevel config, final DoubleUnaryOperator roundFunction) {
    // Store hexagon values
    final double[] horizontal = config.getHorizontal();
    final double[] vertical = config.getVertical();
    final double[][] coordinates = new double[HexagonZoomLevel.HEXAGON_CORNERS + 1][];
    // Format hexagon into a wkt string and wrap it in a Geometry
    // Iterate over the number of corners in a hexagon
    for (int i = 0; i < HexagonZoomLevel.HEXAGON_CORNERS; i++) {
      final double[] coordinate = new double[] {
          roundFunction.applyAsDouble(point.getX() + horizontal[i]),
          roundFunction.applyAsDouble(point.getY() + vertical[i])
      };
      coordinates[i] = coordinate;
    }

    // Polygon first and last point need to be the same
    final double[] lastCoordinate = new double[] {
        roundFunction.applyAsDouble(point.getX() + horizontal[0]),
        roundFunction.applyAsDouble(point.getY() + vertical[0])
    };
    coordinates[HexagonZoomLevel.HEXAGON_CORNERS] = lastCoordinate;
    final Polygon polygon = new Polygon();
    polygon.setCoordinates(new double[][][] {coordinates});
    return polygon;
  }

  /**
   * Returns the number of hexagons that fit in given a radius for a specific
   * hexagon zoom level. This number is called the level.
   * The resulting level is the radius in hexagons of the smallest composite hexagon
   * that encompasses a circle with the supplied radius (in m).
   *
   * @param radius radius
   * @param zoomLevel hexagon zoom level
   * @return number of hexagon levels for a hexagon that covers a circle with radius
   */
  public static int getDistanceLevelForCircle(final double radius, final HexagonZoomLevel zoomLevel) {
    // Level containing all the hexagons up to the radius for a circle :
    // Math.sqrt(4/3) * radius / hexagonHeight
    // sqrt(4/3) would be 1 ( 4 / 3 = 1) however, so use 2/sqrt(3)
    return (int) Math.ceil((2 / Math.sqrt(3)) * radius / zoomLevel.getHexagonHeight());
  }
}
