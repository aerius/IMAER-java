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
package nl.overheid.aerius.gml.base.conversion;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Class to be used to convert from properties used for the older IMAER versions (<= 3.1)
 * to the properties used for the newer IMAER versions (>= 4.0).
 */
public class MobileSourceOffRoadConversion {

  private final BigDecimal fuelConsumption;

  /**
   * @param fuelConsumption The average fuel consumption per hour (in l/h).
   */
  public MobileSourceOffRoadConversion(final double fuelConsumption) {
    this.fuelConsumption = BigDecimal.valueOf(fuelConsumption);
  }

  /**
   * <pre>
   * Formula used:
   * D = B / BSPU
   *
   * Where:
   * B = total fuel used (brandstofverbruik)
   * BSPU = fuel consumption (brandstofverbruik per uur)
   * D = total operating hours (draaiuren)
   * </pre>
   *
   * @param literFuelPerYear
   * @return Estimation of the operating hours.
   */
  public int estimageOffRoadOperatingHours(final int literFuelPerYear) {
    final BigDecimal literFuelPerYearBD = BigDecimal.valueOf(literFuelPerYear);
    final BigDecimal totalOperatingHours = literFuelPerYearBD.divide(fuelConsumption, RoundingMode.HALF_UP);
    // Round to the nearest integer.
    return totalOperatingHours.setScale(0, RoundingMode.HALF_UP).intValue();
  }

}
