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
import java.util.Optional;

/**
 * Class to be used to convert from properties used for the older IMAER versions (<= 3.1)
 * to the properties used for the newer IMAER versions (>= 4.0).
 */
public class MobileSourceOffRoadConversion {

  private final BigDecimal fuelConsumptionUnderLoad;
  private final BigDecimal fuelConsumptionIdle;

  /**
   * @param fuelConsumptionUnderLoad The fuel consumption under load per hour (in l/h).
   * @param fuelConsumptionIdle The fuel consumption while idle per liter engine displacement per hour (in l/l/h).
   */
  public MobileSourceOffRoadConversion(final double fuelConsumptionUnderLoad, final Double fuelConsumptionIdle) {
    this.fuelConsumptionUnderLoad = BigDecimal.valueOf(fuelConsumptionUnderLoad);
    this.fuelConsumptionIdle = Optional.ofNullable(fuelConsumptionIdle).map(BigDecimal::valueOf).orElse(BigDecimal.ZERO);
  }

  /**
   * <pre>
   * Formulas used:
   * BS = DS * BSPU_CI * CI
   * BW = B - BS
   * DW = BW / BSPUW
   * D = DS + DW
   *
   * Where:
   * BS = Fuel used while idle (brandstofverbruik stationair)
   * DS = operating hours idle (draaiuren stationair)
   * BSPU_CI = fuel consumption idle (brandstofverbruik stationair per uur per liter cilinderinhoud)
   * CI = engine displacement (cilinderinhoud)
   * BW = Fuel used under load (brandstofverbruik werkend)
   * B = total fuel used (brandstofverbruik)
   * DW = operating hours under load (draaiuren werkend)
   * BSPUW = fuel consumption under load (brandstofverbruik werkend per uur)
   * D = total operating hours (draaiuren)
   * </pre>
   *
   * @param literFuelPerYear
   * @param hoursIdlePerYear If supplied, engineDisplacement is also expected. If not, this value is ignored.
   * @param engineDisplacement If supplied, hoursIdlePerYear is also expected. If not, this value is ignored.
   * @return Estimation of the operating hours.
   */
  public int estimageOffRoadOperatingHours(final int literFuelPerYear,
      final Integer hoursIdlePerYear, final Double engineDisplacement) {
    final BigDecimal literFuelPerYearBD = BigDecimal.valueOf(literFuelPerYear);
    final BigDecimal hoursIdlePerYearBD;
    final BigDecimal fuelUsedUnderLoad;
    if (hoursIdlePerYear != null && engineDisplacement != null) {
      hoursIdlePerYearBD = BigDecimal.valueOf(hoursIdlePerYear);
      final BigDecimal engineDisplacementBD = BigDecimal.valueOf(engineDisplacement);
      final BigDecimal fuelUsedWhileIdle = fuelConsumptionIdle.multiply(hoursIdlePerYearBD).multiply(engineDisplacementBD);
      fuelUsedUnderLoad = literFuelPerYearBD.subtract(fuelUsedWhileIdle);
    } else {
      hoursIdlePerYearBD = BigDecimal.ZERO;
      fuelUsedUnderLoad = literFuelPerYearBD;
    }
    final BigDecimal hoursUnderLoadPerYear = fuelUsedUnderLoad.divide(fuelConsumptionUnderLoad, RoundingMode.HALF_UP);
    final BigDecimal totalOperatingHours = hoursIdlePerYearBD.add(hoursUnderLoadPerYear);
    // ensure we don't end up with negative estimations (which technically would be possible due to the subtraction)
    // and round to the nearest integer.
    return totalOperatingHours.max(BigDecimal.ZERO).setScale(0, RoundingMode.HALF_UP).intValue();
  }

}
