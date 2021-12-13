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

import java.util.List;

import nl.overheid.aerius.geo.shared.BBox;
import nl.overheid.aerius.shared.domain.geo.HexagonZoomLevel;
import nl.overheid.aerius.shared.domain.geo.ReceptorGridSettings;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.point.ReceptorPoint;

/**
 * Utility class for Receptors.
 */
public final class ReceptorUtil {

  private static final double DIVIDE_CORRECTION = 0.1;

  private static final int TRIPLE = 3;
  private static final double ONE_AND_HALF = 1.5;

  private static final int INDEX_X = 0;
  private static final int INDEX_Y = 1;

  // Bounds in which receptors exist and in which receptor id/point calculations are guaranteed to succeed.
  private final double xMinGuarantee;
  private final double xMaxGuarantee;
  private final double yMinGuarantee;
  private final double yMaxGuarantee;

  private final double doubleHexRow;
  private final double tripleRadius;
  private final double oneAndHalfRadius;
  private final double halfHeight;

  private final HexagonZoomLevel zoomLevel1;
  private final BBox boundingBox;
  private final int hexHor;

  private final double[][] zoomLevelXY;

  public ReceptorUtil(final ReceptorGridSettings rgs) {
    this.boundingBox = rgs.getBoundingBox();
    this.hexHor = rgs.getHexHor();
    this.zoomLevel1 = rgs.getZoomLevel1();
    tripleRadius = zoomLevel1.getHexagonRadius() * TRIPLE;
    xMinGuarantee = boundingBox.getMinX();
    xMaxGuarantee = boundingBox.getMaxX() + tripleRadius;
    yMinGuarantee = boundingBox.getMinY();
    yMaxGuarantee = boundingBox.getMaxY() + zoomLevel1.getHexagonHeight();

    doubleHexRow = hexHor * 2;
    oneAndHalfRadius = zoomLevel1.getHexagonRadius() * ONE_AND_HALF;
    halfHeight = zoomLevel1.getHexagonHeight() / 2;

    final List<HexagonZoomLevel> zoomLevels = rgs.getHexagonZoomLevels();
    final int size = zoomLevels.size();
    zoomLevelXY = new double[size][2];
    for (final HexagonZoomLevel hzl : zoomLevels) {
      final int level = hzl.getLevel() - 1;
      zoomLevelXY[level] = new double[2];
      zoomLevelXY[level][INDEX_X] = hzl.getHexagonRadius();
      zoomLevelXY[level][INDEX_Y] = hzl.getHexagonHeight();
    }
  }

  /**
   * Gets the point (X and Y coordinates) given the receptor id.
   *
   * @param receptorId The receptor ID to get coordinates for
   * @return Point with X and Y coordinates
   **/
  public Point getPointFromReceptorId(final int receptorId) {
    final int id = receptorId - 1;
    final int dx = id % hexHor;
    final int dy = id / hexHor;
    final double sndrow = id % doubleHexRow >= hexHor ? oneAndHalfRadius : 0;

    final double x = dx * tripleRadius + sndrow + boundingBox.getMinX();
    final double y = dy * halfHeight + boundingBox.getMinY();
    return new Point(x, y);
  }

  /**
   * Gets the receptor id based on the given X and Y coordinates. Only works for X and Y within NL bounding box.
   *
   * @param rec AeriusPoint to set id for
   * @return AeriusPoint with id
   */
  public int getReceptorIdFromPoint(final Point rec) {
    return getReceptorIdFromPoint(rec, zoomLevel1);
  }

  /**
   * Calculates the receptor id based on the x/y and zoomlevel. Only works for X and Y within NL bounding box.
   *
   * @param rp point to set id on
   * @param zl zoomlevel the id is on
   * @return reference to input point
   */
  private int getReceptorIdFromPoint(final Point rp, final HexagonZoomLevel zl) {
    return getReceptorIdFromCoordinate(rp.getX(), rp.getY(), zl);
  }

  /**
   * Calculates the receptor id based on the x/y and zoomlevel. Only works for X and Y within NL bounding box.
   *
   * @param x The x coordinate for the point.
   * @param y The y coordinate for the point.
   * @param zl zoomlevel the id is on
   * @return The receptor id for the coordinate.
   */
  public int getReceptorIdFromCoordinate(final double x, final double y, final HexagonZoomLevel zl) {
    // Get x/y values
    final double _x = correctPositionX(x);
    final double _y = correctPositionY(y);

    // Hex config
    // Note: 3 * hex_radius == the (horizontal) distance between 2 hexagons on the same horizontal axis
    // 1 1/2 * hex_radius == distance to the left/right-most point.
    // hex_height == distance between even rows or odd rows
    // half height == distance between even and odd row.
    final double tripleHexRadius = zl.getHexagonRadius() * 3;
    final double oneAndHalfRadius = tripleHexRadius / 2;
    final double hexHeight = zl.getHexagonHeight();
    final double halfHeight = hexHeight / 2;

    // Horizontal offset
    final double xEven = _x - boundingBox.getMinX();
    final double xOdd = xEven + oneAndHalfRadius;

    // Vertical offset
    final double yEven = _y - boundingBox.getMinY();
    final double yOdd = yEven + halfHeight;

    final double xEvenMinusOneAndHalfRadius = xEven - oneAndHalfRadius;
    final double xEvenDividedByTripleRadius = xEven / tripleHexRadius;

    final double yEvenMinusHalfHeight = yEven - halfHeight;
    final double yEvenDividedByHexagonHeight = yEven / hexHeight;

    // Horizontal distance
    final double horDistToEven = xEvenMinusOneAndHalfRadius - (long) xEvenDividedByTripleRadius * tripleHexRadius;
    final double horDistToOdd = Math.abs(
        xEvenMinusOneAndHalfRadius - (long) (xEvenMinusOneAndHalfRadius / tripleHexRadius) * tripleHexRadius)
        - oneAndHalfRadius;

    // Vertical distance
    final double vertDistToEven = yEvenMinusHalfHeight - (long) yEvenDividedByHexagonHeight * hexHeight;
    final double vertDistToOdd = Math.abs(yEvenMinusHalfHeight - (long) (yEvenMinusHalfHeight / hexHeight) * hexHeight)
        - halfHeight;

    // Pyth distance
    final double distToEvenGrid = horDistToEven * horDistToEven + vertDistToEven * vertDistToEven;
    final double distToOddGrid = horDistToOdd * horDistToOdd + vertDistToOdd * vertDistToOdd;

    // Declare receptor id var
    final int recId;

    // Do the test
    if (distToEvenGrid >= distToOddGrid) {
      recId = hexHor * 2 * (int) (yOdd / hexHeight) + (int) (xOdd / tripleHexRadius) + 1;
    } else {
      recId = hexHor * (2 * (int) yEvenDividedByHexagonHeight + 1) + (int) xEvenDividedByTripleRadius + 1;
    }

    return recId;
  }

  private double correctPositionX(final double x) {
    return Math.min(Math.max(x, xMinGuarantee), xMaxGuarantee);
  }

  private double correctPositionY(final double y) {
    return Math.min(Math.max(y, yMinGuarantee), yMaxGuarantee);
  }

  /**
   * Returns true if the receptor point is a valid point at the given zoom level.
   *
   * @param bbox the bounding box of the receptor grid.
   * @param point receptor point to check
   * @param zoomLevel zoom level to check against.
   * @return Returns true if if valid at given zoom level.
   */
  public boolean isReceptorAtZoomLevel(final Point point, final HexagonZoomLevel zoomLevel) {
    final double baseX = point.getX() + DIVIDE_CORRECTION - boundingBox.getMinX();
    final double baseY = point.getY() + DIVIDE_CORRECTION - boundingBox.getMinY();

    final double hexWidth = zoomLevel.getHexagonRadius() * 3d;
    final double hexHeight = zoomLevel.getHexagonHeight();

    final double xm1 = baseX % hexWidth;
    final double ym1 = baseY % hexHeight;

    final double xOffset = zoomLevel.getHexagonRadius() * 1.5;
    final double yOffset = zoomLevel.getHexagonHeight() * 0.5;

    final double xm2 = (baseX + xOffset) % hexWidth;
    final double ym2 = (baseY + yOffset) % hexHeight;

    return Math.abs(xm1) < 10 && Math.abs(ym1) < 10 || Math.abs(xm2) < 10 && Math.abs(ym2) < 10;
  }

  /**
   * Snaps an approximate receptor to the hexagon grid. If no id is set it will first set an id.
   * Then the x and y coordinates are set from that id.
   * @param ap point to attach to grid.
   */
  public void attachReceptorToGrid(final CalculationPointFeature point) {
    if (point.getProperties() instanceof ReceptorPoint) {
      final ReceptorPoint receptor = (ReceptorPoint) point.getProperties();
      // First, retrieve the ID (if not set) from the approximate location
      if (receptor.getReceptorId() == 0) {
        receptor.setReceptorId(getReceptorIdFromPoint(point.getGeometry()));
      }

      // Then, fix the location by extracting it from the ID
      point.setGeometry(getPointFromReceptorId(receptor.getReceptorId()));
    }
  }

  public int getZoomLevelForReceptor(final int receptorId) {
    final int rowNumber = (receptorId - 1) / hexHor;
    final int numberInRow = receptorId - rowNumber * hexHor;

    for (int zoomLevel = 1; zoomLevel < zoomLevelXY.length; zoomLevel++) {
      final int nextZoomLevel = zoomLevel + 1;
      final int zoomlevelFactor = (int) Math.pow(2, nextZoomLevel);
      final int zoomlevelFactorMinusOne = (int) Math.pow(2, zoomLevel);

      if (rowNumber % zoomlevelFactor == 0) {
        // One of the rows where the numbering starts at the beginning
        if ((numberInRow - 1) % zoomlevelFactorMinusOne == 0) {
          continue;
        }
      } else if ((rowNumber) % zoomlevelFactor == zoomlevelFactorMinusOne) {
        // One of the rows where the numbering starts one step to the right
        if ((numberInRow + zoomlevelFactorMinusOne / 2 - 1) % zoomlevelFactorMinusOne == 0) {
          continue;
        }
      }
      return zoomLevel;
    }
    return zoomLevelXY.length;
  }
}
