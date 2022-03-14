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
package nl.overheid.aerius.shared.domain.v2.source.road;

import java.math.BigDecimal;

/**
 * When determining emission factors for roads, there are not always exact matches.
 * In that case, interpolation can be used to estimate an emission factor.
 *
 * This class can be used as an assistant when doing this interpolation.
 * It is used to define what the floor and the ceiling are for a supplied combination of values.
 *
 * In the case of an exact match, use that match for both floor and ceiling values.
 *
 * Interpolation is only done between values, if a value is outside the range of available values, use the closest for both floor and ceiling.
 */
public class RoadStandardsInterpolationValues {

  private final BigDecimal speedFloor;
  private final BigDecimal speedCeiling;
  private final BigDecimal gradientFloor;
  private final BigDecimal gradientCeiling;

  public RoadStandardsInterpolationValues(final int speedFloor, final int speedCeiling, final int gradientFloor, final int gradientCeiling) {
    this.speedFloor = BigDecimal.valueOf(speedFloor);
    this.speedCeiling = BigDecimal.valueOf(speedCeiling);
    this.gradientFloor = BigDecimal.valueOf(gradientFloor);
    this.gradientCeiling = BigDecimal.valueOf(gradientCeiling);
  }

  public int getSpeedFloor() {
    return speedFloor.intValue();
  }

  public BigDecimal getSpeedFloorBD() {
    return speedFloor;
  }

  public int getSpeedCeiling() {
    return speedCeiling.intValue();
  }

  public BigDecimal getSpeedCeilingBD() {
    return speedCeiling;
  }

  public double getGradientFloor() {
    return gradientFloor.doubleValue();
  }

  public BigDecimal getGradientFloorBD() {
    return gradientFloor;
  }

  public double getGradientCeiling() {
    return gradientCeiling.doubleValue();
  }

  public BigDecimal getGradientCeilingDB() {
    return gradientCeiling;
  }

  public boolean speedMatches() {
    return speedFloor.equals(speedCeiling);
  }

  public boolean gradientMatches() {
    return gradientFloor.equals(gradientCeiling);
  }

}
