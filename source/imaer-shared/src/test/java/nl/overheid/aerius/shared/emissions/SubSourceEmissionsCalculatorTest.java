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
package nl.overheid.aerius.shared.emissions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.source.road.CustomVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardColdStartVehicles;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Test class for {@link SubSourceEmissionsCalculator}.
 */
@ExtendWith(MockitoExtension.class)
class SubSourceEmissionsCalculatorTest {

  private @Mock ColdStartEmissionFactorSupplier emissionFactorSupplier;

  private SubSourceEmissionsCalculator subSourceEmissionsCalculator;

  @BeforeEach
  void beforeEach() {
    final ColdStartEmissionsCalculator emissionsCalculator = new ColdStartEmissionsCalculator(emissionFactorSupplier);
    subSourceEmissionsCalculator = new SubSourceEmissionsCalculator(null, null, null, emissionsCalculator);
  }

  @Test
  void testCalculateColdStartEmissionsPerVehicle() throws AeriusException {
    final CustomVehicles vehicle = new CustomVehicles();
    vehicle.setTimeUnit(TimeUnit.DAY);
    vehicle.setVehiclesPerTimeUnit(2002);
    vehicle.getEmissionFactors().put(Substance.NOX, 0.02);
    final Map<Substance, Double> emissions = subSourceEmissionsCalculator.calculateColdStartEmissions(vehicle);

    assertEquals(emissionInKgPerYear(2002, 0.02), emissions.get(Substance.NOX), "Should return correct emission per Kg per Year");
  }

  @Test
  void testCalculateColdStartEmissionsStandardColdStartVehicles() {
    final StandardColdStartVehicles vehicle = new StandardColdStartVehicles();
    vehicle.setTimeUnit(TimeUnit.DAY);
    vehicle.setValuesPerVehicleTypes(Map.of("BUS", 2002.0));
    doReturn(Map.of(Substance.NOX, 0.02)).when(emissionFactorSupplier).getColdStartStandardVehicleEmissionFactors(eq("BUS"));
    final Map<Substance, Double> emissions = subSourceEmissionsCalculator.calculateColdStartEmissions(vehicle, "BUS");

    assertEquals(emissionInKgPerYear(2002, 0.02), emissions.get(Substance.NOX), "Should return correct emission per Kg per Year");
  }

  private static double emissionInKgPerYear(final int numberOfVehicles, final double emissionGramPerColdstart) {
    return BigDecimal.valueOf(numberOfVehicles * 365 * emissionGramPerColdstart).divide(BigDecimal.valueOf(1000.0)).doubleValue();
  }
}
