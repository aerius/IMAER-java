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
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Map;
import java.util.OptionalDouble;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.OffRoadMobileEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.offroad.CustomOffRoadMobileSource;
import nl.overheid.aerius.shared.domain.v2.source.offroad.StandardOffRoadMobileSource;
import nl.overheid.aerius.shared.exception.AeriusException;

@ExtendWith(MockitoExtension.class)
class OffRoadMobileEmissionsCalculatorTest {

  @Mock OffRoadMobileEmissionFactorSupplier emissionFactorSupplier;

  OffRoadMobileEmissionsCalculator emissionsCalculator;

  @BeforeEach
  void beforeEach() throws AeriusException {
    emissionsCalculator = new OffRoadMobileEmissionsCalculator(emissionFactorSupplier);
  }

  @Test
  void testCalculateEmissions() throws AeriusException {
    final OffRoadMobileEmissionSource emissionSource = new OffRoadMobileEmissionSource();
    final CustomOffRoadMobileSource mobileSource1 = new CustomOffRoadMobileSource();
    mobileSource1.getEmissions().put(Substance.NOX, 88.6);
    emissionSource.getSubSources().add(mobileSource1);
    final CustomOffRoadMobileSource mobileSource2 = new CustomOffRoadMobileSource();
    mobileSource2.getEmissions().put(Substance.NOX, 16.2);
    mobileSource2.getEmissions().put(Substance.NH3, 23.7);
    emissionSource.getSubSources().add(mobileSource2);
    final StandardOffRoadMobileSource mobileSource3 = createOnlyFuel();
    emissionSource.getSubSources().add(mobileSource3);

    assertTrue(mobileSource3.getEmissions().isEmpty(), "Custom vehicles directly use the emissions map (for now), so don't expect those to be empty");

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource);

    // Check total emissions
    assertEquals(104.8 + 12.50, results.get(Substance.NOX), "Not the expected total emission");
    assertEquals(23.7, results.get(Substance.NH3), "Not the expected NH3 emission");
    // Check emissions per subsource (should be set during calculation)
    assertEquals(88.6, mobileSource1.getEmissions().get(Substance.NOX), "Not the expected NOx emission for source 1");
    assertEquals(16.2, mobileSource2.getEmissions().get(Substance.NOX), "Not the expected NOx emission for source 2");
    assertEquals(12.5, mobileSource3.getEmissions().get(Substance.NOX), "Not the expected NOx emission for source 3");
    assertNull(mobileSource1.getEmissions().get(Substance.NH3), "There should not be NH3 emission for source 1");
    assertEquals(23.7, mobileSource2.getEmissions().get(Substance.NH3), "Not the expected NH3 emission for source 2");
    assertNull(mobileSource3.getEmissions().get(Substance.NH3), "There should not be NH3 emission for source 3");
  }

  @Test
  void testCalculateEmissionsCustom() {
    final CustomOffRoadMobileSource mobileSource = new CustomOffRoadMobileSource();
    mobileSource.getEmissions().put(Substance.NOX, 12.34567);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(mobileSource);

    assertEquals(BigDecimal.valueOf(12.34567), results.get(Substance.NOX), "Not the expected NOx emission");
  }

  @Test
  void testCalculateEmissionsPower() {
    final StandardOffRoadMobileSource mobileSource = createPower();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(mobileSource);
    // Power = 0.033 * 365 hours * 500 kW / 1000 = 6.0225;
    assertEquals(new BigDecimal("6.0225"), results.get(Substance.NOX).stripTrailingZeros(),
        "Not the expected calculated emission based on power input");
  }

  @Test
  void testCalculateEmissionsOnlyFuel() {
    final StandardOffRoadMobileSource mobileSource = createOnlyFuel();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(mobileSource);

    assertEquals(new BigDecimal("12.500"), results.get(Substance.NOX), "Not the expected calculated emission based on fuel input");
  }

  @Test
  void testCalculateEmissionsOnlyOperatingHour() {
    final StandardOffRoadMobileSource mobileSource = createOnlyOperatingHour();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(mobileSource);

    assertEquals(new BigDecimal("0.600"), results.get(Substance.NOX), "Not the expected calculated emission based on operating hours input");
  }

  @Test
  void testCalculateEmissionsWithAdBlue() {
    final StandardOffRoadMobileSource mobileSource = createFuelAndAdBlue();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(mobileSource);

    assertEquals(new BigDecimal("10.660"), results.get(Substance.NOX), "Not the expected calculated emission with AdBlue input");
  }

  @Test
  void testCalculateEmissionsWithTooMuchAdBlue() {
    final StandardOffRoadMobileSource mobileSource = createFuelAndAdBlue();
    // Adjust value ad blue so emissions by fuel are lower than the emission reductions by AdBlue.
    mobileSource.setLiterAdBluePerYear(100);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(mobileSource);

    // Fuel emissions: 500 * 0.025 = 12.5
    // Max adBlue (due to ratio): 500 * 0.04 = 20
    // AdBlue emissions: 20 * -0.46 = -9.2
    // Total (all values summed) = 3.3
    assertEquals(new BigDecimal("3.3000"), results.get(Substance.NOX), "Not the expected calculated emission too much AdBlue input");
  }

  @Test
  void testCalculateEmissionsAll() {
    final StandardOffRoadMobileSource mobileSource = createAll();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(mobileSource);

    // Fuel emissions: 500 * 0.025 = 12.5
    // Operating hour emissions: 120 * 0.005 = 0.6
    // AdBlue emissions: 4 * -0.46 = -1.84
    // Total (all values summed) = 11.26
    assertEquals(new BigDecimal("11.260"), results.get(Substance.NOX), "Not the expected calculated emission with both fuel and AdBlue");
  }

  private StandardOffRoadMobileSource createPower() {
    final StandardOffRoadMobileSource mobileSource = new StandardOffRoadMobileSource();
    mobileSource.setPower(500);
    mobileSource.setOperatingHoursPerYear(365);
    final String mobileSourceCode = "ABC";
    mobileSource.setOffRoadMobileSourceCode(mobileSourceCode);

    mockPower(mobileSourceCode);

    return mobileSource;
  }

  private StandardOffRoadMobileSource createOnlyFuel() {
    final StandardOffRoadMobileSource mobileSource = new StandardOffRoadMobileSource();
    mobileSource.setLiterFuelPerYear(500);
    final String mobileSourceCode = "ABC";
    mobileSource.setOffRoadMobileSourceCode(mobileSourceCode);

    mockFuel(mobileSourceCode);

    return mobileSource;
  }

  private StandardOffRoadMobileSource createOnlyOperatingHour() {
    final StandardOffRoadMobileSource mobileSource = new StandardOffRoadMobileSource();
    mobileSource.setOperatingHoursPerYear(120);
    final String mobileSourceCode = "DEF";
    mobileSource.setOffRoadMobileSourceCode(mobileSourceCode);

    mockOperatingHour(mobileSourceCode);

    return mobileSource;
  }

  private StandardOffRoadMobileSource createFuelAndAdBlue() {
    final StandardOffRoadMobileSource mobileSource = new StandardOffRoadMobileSource();
    mobileSource.setLiterFuelPerYear(500);
    mobileSource.setLiterAdBluePerYear(4);
    final String mobileSourceCode = "DEF";
    mobileSource.setOffRoadMobileSourceCode(mobileSourceCode);

    mockFuel(mobileSourceCode);
    mockAdBlue(mobileSourceCode);

    return mobileSource;
  }

  private StandardOffRoadMobileSource createAll() {
    final StandardOffRoadMobileSource mobileSource = new StandardOffRoadMobileSource();
    mobileSource.setLiterFuelPerYear(500);
    mobileSource.setOperatingHoursPerYear(120);
    mobileSource.setLiterAdBluePerYear(4);
    final String mobileSourceCode = "XYZ";
    mobileSource.setOffRoadMobileSourceCode(mobileSourceCode);

    mockFuel(mobileSourceCode);
    mockOperatingHour(mobileSourceCode);
    mockAdBlue(mobileSourceCode);

    return mobileSource;
  }

  private void mockPower(final String mobileSourceCode) {
    final Map<Substance, Double> emissionFactorsPerKWHour = Map.of(Substance.NOX, 0.033);
    when(emissionFactorSupplier.getOffRoadMobileEmissionFactorsPerKWHour(mobileSourceCode)).thenReturn(emissionFactorsPerKWHour);
  }

  private void mockFuel(final String mobileSourceCode) {
    final Map<Substance, Double> emissionFactorsPerLiterFuel = Map.of(Substance.NOX, 0.025);
    when(emissionFactorSupplier.getOffRoadMobileEmissionFactorsPerLiterFuel(mobileSourceCode)).thenReturn(emissionFactorsPerLiterFuel);
  }

  private void mockOperatingHour(final String mobileSourceCode) {
    final Map<Substance, Double> emissionFactorsPerOperatingHour = Map.of(Substance.NOX, 0.005);
    when(emissionFactorSupplier.getOffRoadMobileEmissionFactorsPerOperatingHour(mobileSourceCode)).thenReturn(emissionFactorsPerOperatingHour);
  }

  private void mockAdBlue(final String mobileSourceCode) {
    final Map<Substance, Double> emissionFactorsPerLiterAdBlue = Map.of(Substance.NOX, -0.46);
    when(emissionFactorSupplier.getOffRoadMobileEmissionFactorsPerLiterAdBlue(mobileSourceCode)).thenReturn(emissionFactorsPerLiterAdBlue);
    when(emissionFactorSupplier.getMaxAdBlueFuelRatio(mobileSourceCode)).thenReturn(OptionalDouble.of(0.04));
  }

}
