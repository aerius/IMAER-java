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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 *
 */
class MobileSourceOffRoadConversionTest {

  @Test
  void testWithIdle() {
    final double fuelConsumptionUnderLoad = 2.4;
    final double fuelConsumptionIdle = 0.42;
    final MobileSourceOffRoadConversion conversion = new MobileSourceOffRoadConversion(fuelConsumptionUnderLoad, fuelConsumptionIdle);

    // No idle supplied: 1000 / 2.4 = 416.6667
    assertEquals(417, conversion.estimageOffRoadOperatingHours(1000, null, null));
    // Only hours idle but no engine displacement has same effect
    assertEquals(417, conversion.estimageOffRoadOperatingHours(1000, 30, null));
    // Only engine displacement but no hours idle has same effect
    assertEquals(417, conversion.estimageOffRoadOperatingHours(1000, null, 4.0));
    // zero engine displacement and non-zero idle hours does have effect
    assertEquals(447, conversion.estimageOffRoadOperatingHours(1000, 30, 0.0));
    // non-zero engine displacement and zero idle hours does not have effect
    assertEquals(417, conversion.estimageOffRoadOperatingHours(1000, 0, 4.0));
    // all three supplied:
    // 30 * (1 - (0.42 * 4.0) / 2.4) + 1000 / 2.4 = 425.6667
    assertEquals(426, conversion.estimageOffRoadOperatingHours(1000, 30, 4.0));
  }

  @Test
  void testWithIdleExcessive() {
    final double fuelConsumptionUnderLoad = 2.4;
    final double fuelConsumptionIdle = 0.42;
    final MobileSourceOffRoadConversion conversion = new MobileSourceOffRoadConversion(fuelConsumptionUnderLoad, fuelConsumptionIdle);

    // It's possible that a user supplies unrealistic value for engine displacement (which we no longer validate)
    // 30 * (1 - (0.42 * 400.0) / 2.4) + 1000 / 2.4 = -1653.3333
    // we're avoiding negative values however, so should end up with an estimation of 0
    assertEquals(0, conversion.estimageOffRoadOperatingHours(1000, 30, 400.0));
  }

  @Test
  void testWithoutIdle() {
    final double fuelConsumptionUnderLoad = 2.4;
    final MobileSourceOffRoadConversion conversion = new MobileSourceOffRoadConversion(fuelConsumptionUnderLoad, null);

    // No idle supplied: 1000 / 2.4 = 416.6667
    assertEquals(417, conversion.estimageOffRoadOperatingHours(1000, null, null));
    // Only hours idle but no engine displacement has same effect
    assertEquals(417, conversion.estimageOffRoadOperatingHours(1000, 30, null));
    // Only engine displacement but no hours idle has same effect
    assertEquals(417, conversion.estimageOffRoadOperatingHours(1000, null, 4.0));
    // zero engine displacement and non-zero idle hours does have effect
    assertEquals(447, conversion.estimageOffRoadOperatingHours(1000, 30, 0.0));
    // non-zero engine displacement and zero idle hours does not have effect
    assertEquals(417, conversion.estimageOffRoadOperatingHours(1000, 0, 4.0));
    // all three supplied:
    // 30 * (1 - (0 * 4.0) / 2.4) + 1000 / 2.4 = 446.6667
    assertEquals(447, conversion.estimageOffRoadOperatingHours(1000, 30, 4.0));
  }

}
