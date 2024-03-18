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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.source.ColdStartEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.CustomVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.SpecificVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardColdStartVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.Vehicles;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Test class for {@link ColdStartEmissionsCalculator}.
 */
@ExtendWith(MockitoExtension.class)
class ColdStartEmissionsCalculatorTest {

  @Mock
  ColdStartEmissionFactorSupplier emissionFactorSupplier;

  ColdStartEmissionsCalculator emissionsCalculator;

  @BeforeEach
  void beforeEach() throws AeriusException {
    emissionsCalculator = new ColdStartEmissionsCalculator(emissionFactorSupplier);
  }

  @Test
  void testCalculateEmissionsColdStart() throws AeriusException {
    final ColdStartEmissionSource emissionSource = new ColdStartEmissionSource();

    // add 1 vehicles object of every type
    emissionSource.getSubSources().add(createCustom());
    emissionSource.getSubSources().add(createSpecific());
    emissionSource.getSubSources().add(createStandard());

    ensureSubSourceEmissionsEmpty(emissionSource);

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource);

    // Check total emissions
    // (1351850.865000 + 42000.00 + 360000.00) / 1000
    assertEquals(1753.850865, results.get(Substance.NOX), 0.00001, "Total NOx emissions");
    // (0.0 + 20400.00 + 126000.00) / 1000
    assertEquals(146.4, results.get(Substance.NH3), 0.00001, "Total NH3 emissions");
    // Check emissions per subsource (should be set during calculation)
    assertEquals(1351.850865, emissionSource.getSubSources().get(0).getEmissions().get(Substance.NOX), "NOx emissions first subsource");
    assertEquals(42.0, emissionSource.getSubSources().get(1).getEmissions().get(Substance.NOX), "NOx emissions second subsource");
    assertEquals(360.0, emissionSource.getSubSources().get(2).getEmissions().get(Substance.NOX), "NOx emissions third subsource");
    assertNull(emissionSource.getSubSources().get(0).getEmissions().get(Substance.NH3), "NH3 emissions first subsource");
    assertEquals(20.4, emissionSource.getSubSources().get(1).getEmissions().get(Substance.NH3), "NH3 emissions second subsource");
    assertEquals(126.0, emissionSource.getSubSources().get(2).getEmissions().get(Substance.NH3), "NH3 emissions third subsource");
  }

  @Test
  void testCalculateEmissionsCustomVehicles() {
    final CustomVehicles vehicles = createCustom();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles);

    // 365 * 300 * 12.34567
    assertEquals(new BigDecimal("1351850.865000"), results.get(Substance.NOX), "NOx emissions custom vehicle");
  }

  @Test
  void testCalculateEmissionsSpecificVehicles() {
    final SpecificVehicles vehicles = createSpecific();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles, new ColdStartEmissionSource());

    // 12 * 500 * 7.0
    assertEquals(new BigDecimal("42000.00"), results.get(Substance.NOX), "NOx emissions specific vehicle");
    // 12 * 500 * 3.4
    assertEquals(new BigDecimal("20400.00"), results.get(Substance.NH3), "NH3 emissions specific vehicle");
  }

  @Test
  void testCalculateEmissionsStandardVehicles() {
    final StandardColdStartVehicles vehicles = createStandard();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles);

    // 30000 * 12.0
    assertEquals(new BigDecimal("360000.00"), results.get(Substance.NOX), "NOx emissions standard vehicle");
    // 30000 * 4.2
    assertEquals(new BigDecimal("126000.00"), results.get(Substance.NH3), "NH3 emissions standard vehicle");
  }

  @Test
  void testCalculateEmissionsMultipleStandardVehicles() {
    final StandardColdStartVehicles vehicles = createMultipleStandard();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles);

    // 30000 * 12.0 + 2000 * 10.0
    assertEquals(new BigDecimal("380000.00"), results.get(Substance.NOX), "NOx emissions standard vehicles");
    // 30000 * 4.2 *+ 2000 * 5.3
    assertEquals(new BigDecimal("136600.00"), results.get(Substance.NH3), "NH3 emissions standard vehicles");
  }

  private CustomVehicles createCustom() {
    final CustomVehicles vehicles = new CustomVehicles();
    vehicles.getEmissionFactors().put(Substance.NOX, 12.34567);
    vehicles.setVehiclesPerTimeUnit(300);
    vehicles.setTimeUnit(TimeUnit.DAY);
    return vehicles;
  }

  private SpecificVehicles createSpecific() {
    final SpecificVehicles vehicles = new SpecificVehicles();
    vehicles.setVehiclesPerTimeUnit(500);
    vehicles.setTimeUnit(TimeUnit.MONTH);
    final String specificCode = "DEF";
    vehicles.setVehicleCode(specificCode);

    final Map<Substance, Double> emissionFactors = Map.of(Substance.NOX, 7.0, Substance.NH3, 3.4);
    lenient().when(emissionFactorSupplier.getColdStartSpecificVehicleEmissionFactors(specificCode)).thenReturn(emissionFactors);

    return vehicles;
  }

  private StandardColdStartVehicles createStandard() {
    final StandardColdStartVehicles vehicles = new StandardColdStartVehicles();
    vehicles.setTimeUnit(TimeUnit.YEAR);
    final String vehicleType = "NORMAL_FREIGHT";
    vehicles.getValuesPerVehicleTypes().put(vehicleType, 30000D);

    lenient().when(emissionFactorSupplier.getColdStartStandardVehicleEmissionFactors(vehicleType))
        .thenReturn(Map.of(Substance.NOX, 12.0, Substance.NH3, 4.2));

    return vehicles;
  }

  private StandardColdStartVehicles createMultipleStandard() {
    final StandardColdStartVehicles vehicles = createStandard();
    final String vehicleType = "HEAVY_FREIGHT";
    vehicles.getValuesPerVehicleTypes().put(vehicleType, 2000D);

    lenient().when(emissionFactorSupplier.getColdStartStandardVehicleEmissionFactors(vehicleType))
        .thenReturn(Map.of(Substance.NOX, 10.0, Substance.NH3, 5.3));

    return vehicles;
  }

  private void ensureSubSourceEmissionsEmpty(final ColdStartEmissionSource emissionSource) {
    // Ensure emissions per subsource are unknown at start
    for (final Vehicles vehicles : emissionSource.getSubSources()) {
      assertTrue(vehicles.getEmissions().isEmpty(), "Subsources should have no emissions");
    }
  }

}
