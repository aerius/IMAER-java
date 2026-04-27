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
package nl.overheid.aerius.gml.base.source.road;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import nl.overheid.aerius.gml.base.source.IsGmlTimeUnit;
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.source.road.CustomVehicles;

class RemovedVehicleUtilTest {

  @Test
  void testToCustomVehiclesPreservesTimeUnit() {
    final IsGmlSpecificVehicle vehicle = createMockVehicle("TEST_CODE", 100.0, "DAY");

    final CustomVehicles result = RemovedVehicleUtil.toCustomVehicles(vehicle, "TEST_CODE");

    assertEquals(TimeUnit.DAY, result.getTimeUnit());
  }

  @Test
  void testToCustomVehiclesPreservesVehicleCount() {
    final IsGmlSpecificVehicle vehicle = createMockVehicle("TEST_CODE", 42.5, "YEAR");

    final CustomVehicles result = RemovedVehicleUtil.toCustomVehicles(vehicle, "TEST_CODE");

    assertEquals(42.5, result.getVehiclesPerTimeUnit());
  }

  @Test
  void testToCustomVehiclesSetsDescriptionWithVehicleCode() {
    final IsGmlSpecificVehicle vehicle = createMockVehicle("BABCEUR4", 100.0, "DAY");

    final CustomVehicles result = RemovedVehicleUtil.toCustomVehicles(vehicle, "BABCEUR4");

    assertEquals("BABCEUR4", result.getDescription());
  }

  @Test
  void testToCustomVehiclesHasZeroEmissions() {
    final IsGmlSpecificVehicle vehicle = createMockVehicle("TEST_CODE", 100.0, "DAY");

    final CustomVehicles result = RemovedVehicleUtil.toCustomVehicles(vehicle, "TEST_CODE");

    assertTrue(result.getEmissionFactors().isEmpty());
  }

  private IsGmlSpecificVehicle createMockVehicle(final String code, final double vehiclesPerTimeUnit, final String timeUnitName) {
    final IsGmlSpecificVehicle vehicle = mock(IsGmlSpecificVehicle.class);
    when(vehicle.getCode()).thenReturn(code);
    when(vehicle.getVehiclesPerTimeUnit()).thenReturn(vehiclesPerTimeUnit);
    final IsGmlTimeUnit timeUnit = mock(IsGmlTimeUnit.class);
    when(timeUnit.name()).thenReturn(timeUnitName);
    when(vehicle.getTimeUnit()).thenReturn(timeUnit);
    return vehicle;
  }

}
