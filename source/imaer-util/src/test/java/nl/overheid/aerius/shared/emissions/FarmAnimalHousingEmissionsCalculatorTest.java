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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.FarmAnimalHousingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.farm.AdditionalHousingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.CustomAdditionalHousingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.CustomFarmAnimalHousing;
import nl.overheid.aerius.shared.domain.v2.source.farm.FarmAnimalHousing;
import nl.overheid.aerius.shared.domain.v2.source.farm.StandardAdditionalHousingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.StandardFarmAnimalHousing;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

@ExtendWith(MockitoExtension.class)
class FarmAnimalHousingEmissionsCalculatorTest {

  @Mock FarmAnimalHousingEmissionFactorSupplier emissionFactorSupplier;

  FarmAnimalHousingEmissionsCalculator emissionsCalculator;

  @BeforeEach
  void beforeEach() throws AeriusException {
    emissionsCalculator = new FarmAnimalHousingEmissionsCalculator(emissionFactorSupplier);
  }

  @Test
  void testCalculateEmissions() throws AeriusException {
    final FarmAnimalHousingEmissionSource emissionSource = new FarmAnimalHousingEmissionSource();
    final CustomFarmAnimalHousing housing1 = new CustomFarmAnimalHousing();
    housing1.getEmissionFactors().put(Substance.NOX, 44.3);
    housing1.setNumberOfAnimals(2);
    emissionSource.getSubSources().add(housing1);
    final CustomFarmAnimalHousing housing2 = new CustomFarmAnimalHousing();
    housing2.getEmissionFactors().put(Substance.NOX, 5.4);
    housing2.getEmissionFactors().put(Substance.NH3, 7.9);
    housing2.setNumberOfAnimals(3);
    emissionSource.getSubSources().add(housing2);
    final StandardFarmAnimalHousing housing3 = createTestObjectBase();
    addAdditionalSystem1(housing3);
    addAdditionalSystem2(housing3);
    emissionSource.getSubSources().add(housing3);

    // Ensure emissions per subsource are unknown at start
    for (final FarmAnimalHousing housing : emissionSource.getSubSources()) {
      assertTrue(housing.getEmissions().isEmpty());
    }

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource);

    // Check total emissions
    assertEquals(104.8, results.get(Substance.NOX));
    assertEquals(23.7 + 1710.0, results.get(Substance.NH3));
    // Check emissions per subsource (should be set during calculation)
    assertEquals(88.6, housing1.getEmissions().get(Substance.NOX));
    assertEquals(16.2, housing2.getEmissions().get(Substance.NOX));
    assertNull(housing3.getEmissions().get(Substance.NOX));
    assertNull(housing1.getEmissions().get(Substance.NH3));
    assertEquals(23.7, housing2.getEmissions().get(Substance.NH3));
    assertEquals(1710.0, housing3.getEmissions().get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsWithNumberOfDays() throws AeriusException {
    final FarmAnimalHousingEmissionSource emissionSource =
        getCustomFarmAnimalHousingTestCase(0.034567, FarmEmissionFactorType.PER_ANIMAL_PER_DAY, 100);
    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource);

    assertEquals(193.5752, results.get(Substance.NOX));
  }

  @Test
  void testCalculateEmissionsWithMinNumberOfDays() throws AeriusException {
    final FarmAnimalHousingEmissionSource emissionSource = getCustomFarmAnimalHousingTestCase(12.34567, FarmEmissionFactorType.PER_ANIMAL_PER_DAY, 0);
    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource);

    assertEquals(0, results.get(Substance.NOX));
  }

  @Test
  void testCalculateEmissionsWithNumberOfDaysAndEmissionFactorPerYear() throws AeriusException {
    final FarmAnimalHousingEmissionSource emissionSource =
        getCustomFarmAnimalHousingTestCase(12.34567, FarmEmissionFactorType.PER_ANIMAL_PER_YEAR, 100);
    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource);

    assertEquals(691.35752, results.get(Substance.NOX));
  }

  @Test
  void testCalculateEmissionsUnknownImplementation() throws AeriusException {
    final FarmAnimalHousingEmissionSource emissionSource = new FarmAnimalHousingEmissionSource();
    emissionSource.getSubSources().add(new FarmAnimalHousing() {
      private static final long serialVersionUID = 1L;
    });

    final AeriusException thrown = assertThrows(AeriusException.class, () -> emissionsCalculator.calculateEmissions(emissionSource));

    assertEquals(ImaerExceptionReason.INTERNAL_ERROR, thrown.getReason());
  }

  private FarmAnimalHousingEmissionSource getCustomFarmAnimalHousingTestCase(final double noxEmissionFactor,
      final FarmEmissionFactorType farmEmissionFactorType,
      final int numberOfDays) {
    final CustomFarmAnimalHousing housing = new CustomFarmAnimalHousing();
    housing.getEmissionFactors().put(Substance.NOX, noxEmissionFactor);
    housing.setNumberOfAnimals(56);
    housing.setFarmEmissionFactorType(farmEmissionFactorType);
    housing.setNumberOfDays(numberOfDays);

    final FarmAnimalHousingEmissionSource emissionSource = new FarmAnimalHousingEmissionSource();
    emissionSource.getSubSources().add(housing);
    return emissionSource;
  }

  @Test
  void testCalculateEmissionsCustomFarmAnimalHousing() {
    final CustomFarmAnimalHousing housing = new CustomFarmAnimalHousing();
    housing.getEmissionFactors().put(Substance.NOX, 12.34567);
    housing.setNumberOfAnimals(56);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(housing);

    assertEquals(BigDecimal.valueOf(691.35752), results.get(Substance.NOX));
  }

  @Test
  void testCalculateEmissionsStandardFarmAnimalHousing() throws AeriusException {
    final StandardFarmAnimalHousing housing = createTestObjectBase();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(housing);

    // 1000 * 3
    assertEquals(BigDecimal.valueOf(3000.0), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsStandardFarmAnimalHousingPerDay() throws AeriusException {
    final StandardFarmAnimalHousing housing = createStandardHousingPerDay();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(housing);

    // 1000 * 0.02 * 175
    assertEquals(new BigDecimal("3500.00"), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsStandardFarmAnimalHousingOneAdditional() throws AeriusException {
    final StandardFarmAnimalHousing housing = createTestObjectBase();
    addAdditionalSystem1(housing);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(housing);

    // 1000 * 3 * (1 - 0.4)
    assertEquals(new BigDecimal("1800.00"), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsStandardFarmAnimalHousingOneAdditionalScrubber() throws AeriusException {
    final StandardFarmAnimalHousing housing = createTestObjectBase();
    addAdditionalSystem1(housing, true);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(housing);

    // Animal housing is not constrained, so same value.
    assertEquals(new BigDecimal("1800.00"), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsStandardFarmAnimalHousingTwoAdditionals() throws AeriusException {
    final StandardFarmAnimalHousing housing = createTestObjectBase();
    addAdditionalSystem1(housing);
    addAdditionalSystem2(housing);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(housing);

    // 1000 * 3 * (1 - 0.4) * (1 - 0.05)
    assertEquals(new BigDecimal("1710.0000"), results.get(Substance.NH3));
    assertEquals(1710.0, results.get(Substance.NH3).doubleValue());
  }

  @Test
  void testCalculateEmissionsStandardFarmAnimalHousingAdditionalCustom() throws AeriusException {
    final StandardFarmAnimalHousing housing = createTestObjectBase();
    addAdditionalSystemCustom(housing, false);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(housing);

    // 1000 * 3 * (1 - 0.77)
    assertEquals(new BigDecimal("690.000"), results.get(Substance.NH3));
    assertEquals(690.0, results.get(Substance.NH3).doubleValue());
  }

  @Test
  void testCalculateEmissionsStandardFarmAnimalHousingMixed() throws AeriusException {
    final StandardFarmAnimalHousing housing = createTestObjectBase();
    addAdditionalSystem1(housing);
    addAdditionalSystem2(housing);
    addAdditionalSystemCustom(housing, false);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(housing);

    // 1000 * 3 * (1 - 0.4) * (1 - 0.05) * (1 - 0.77)
    assertEquals(new BigDecimal("393.300000"), results.get(Substance.NH3));
    assertEquals(393.3, results.get(Substance.NH3).doubleValue());
  }

  @Test
  void testCalculateEmissionsAdditionalUnknownImplementation() throws AeriusException {
    final StandardFarmAnimalHousing housing = createTestObjectBase();
    housing.getAdditionalSystems().add(new AdditionalHousingSystem() {
      private static final long serialVersionUID = 1L;
    });

    final AeriusException thrown = assertThrows(AeriusException.class, () -> emissionsCalculator.calculateEmissions(housing));

    assertEquals(ImaerExceptionReason.INTERNAL_ERROR, thrown.getReason());
  }

  @Test
  void testCalculateEmissionsStandardFarmAnimalHousingConstrained() throws AeriusException {
    final StandardFarmAnimalHousing housing = createTestObjectBaseConstrained();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(housing);

    assertEquals(BigDecimal.valueOf(1000.0), results.get(Substance.NH3));
    assertEquals(BigDecimal.valueOf(10000.0), results.get(Substance.PM10));
  }

  @Test
  void testCalculateEmissionsStandardFarmAnimalHousingConstrainedOneAdditionalNonScrubber() throws AeriusException {
    final StandardFarmAnimalHousing housing = createTestObjectBaseConstrained();
    addAdditionalSystem1(housing);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(housing);

    // Unconstrained = 1000 * 1.0 * (1 - 0.4)
    assertEquals(new BigDecimal("600.00"), results.get(Substance.NH3));
    // PM10: 1000 * 10.0 * (1 - 0.6)
    assertEquals(new BigDecimal("4000.00"), results.get(Substance.PM10));
  }

  @Test
  void testCalculateEmissionsStandardFarmAnimalHousingConstrainedOneAdditionalScrubber() throws AeriusException {
    final StandardFarmAnimalHousing housing = createTestObjectBaseConstrained();
    addAdditionalSystem1(housing, true);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(housing);

    // Constrained = 1000 * 8.0 * 0.3 * (1 - 0.4)
    assertEquals(new BigDecimal("1440.00"), results.get(Substance.NH3));
    // Constrained only works for NH3
    assertEquals(new BigDecimal("4000.00"), results.get(Substance.PM10));
  }

  @Test
  void testCalculateEmissionsStandardFarmAnimalHousingConstrainedCustomNonScrubber() throws AeriusException {
    final StandardFarmAnimalHousing housing = createTestObjectBaseConstrained();
    addAdditionalSystemCustom(housing, false);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(housing);

    // Unconstrained = 1000 * 1.0 * (1 - 0.77)
    assertEquals(new BigDecimal("230.000"), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsStandardFarmAnimalHousingConstrainedCustomScrubber() throws AeriusException {
    final StandardFarmAnimalHousing housing = createTestObjectBaseConstrained();
    addAdditionalSystemCustom(housing, true);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(housing);

    // Constrained = 1000 * 8.0 * 0.3 * (1 - 0.77)
    assertEquals(new BigDecimal("552.000"), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsStandardFarmAnimalHousingConstrainedMixed() throws AeriusException {
    final StandardFarmAnimalHousing housing = createTestObjectBaseConstrained();
    addAdditionalSystem1(housing, true);
    addAdditionalSystem2(housing);
    addAdditionalSystem1(housing, true);
    addAdditionalSystemCustom(housing, false);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(housing);

    // Constrained = 1000 * 8.0 * 0.3 * (1 - 0.4) * (1 - 0.05) * (1 - 0.4) * (1 - 0.77)
    assertEquals(new BigDecimal("188.7840000"), results.get(Substance.NH3));
    assertEquals(188.784, results.get(Substance.NH3).doubleValue());
  }

  private StandardFarmAnimalHousing createTestObjectBase() {
    final StandardFarmAnimalHousing housing = new StandardFarmAnimalHousing();
    housing.setNumberOfAnimals(1000);
    final String housingCode = "ABC";
    housing.setAnimalHousingCode(housingCode);

    final Map<Substance, Double> emissionFactors = Map.of(Substance.NH3, 3.0);
    when(emissionFactorSupplier.getAnimalHousingEmissionFactors(housingCode)).thenReturn(emissionFactors);

    return housing;
  }

  private StandardFarmAnimalHousing createTestObjectBaseConstrained() {
    final StandardFarmAnimalHousing housing = new StandardFarmAnimalHousing();
    housing.setNumberOfAnimals(1000);
    final String housingCode = "ABC";
    final String basicCode = "DEF";
    housing.setAnimalHousingCode(housingCode);

    final Map<Substance, Double> emissionFactors = Map.of(Substance.NH3, 1.0, Substance.PM10, 10.0);
    final Map<Substance, Double> emissionFactorsConstrained = Map.of(Substance.NH3, 8.0, Substance.PM10, 80.0);
    lenient().when(emissionFactorSupplier.getAnimalHousingEmissionFactors(housingCode)).thenReturn(emissionFactors);
    lenient().when(emissionFactorSupplier.getAnimalBasicHousingCode(housingCode)).thenReturn(basicCode);
    lenient().when(emissionFactorSupplier.getAnimalHousingEmissionFactors(basicCode)).thenReturn(emissionFactorsConstrained);

    return housing;
  }

  private void addAdditionalSystem1(final StandardFarmAnimalHousing housing) {
    addAdditionalSystem1(housing, false);
  }

  private void addAdditionalSystem1(final StandardFarmAnimalHousing housing, final boolean airScrubber) {
    final StandardAdditionalHousingSystem additionalSystem = new StandardAdditionalHousingSystem();
    final String systemCode = "ADD1";
    additionalSystem.setAdditionalSystemCode(systemCode);

    final Map<Substance, Double> emissionFactors = Map.of(Substance.NH3, 0.4, Substance.PM10, 0.6);
    when(emissionFactorSupplier.getAdditionalHousingSystemReductionFractions(systemCode, housing.getAnimalHousingCode())).thenReturn(emissionFactors);
    lenient().when(emissionFactorSupplier.isAdditionalHousingSystemAirScrubber(systemCode)).thenReturn(airScrubber);

    housing.getAdditionalSystems().add(additionalSystem);
  }

  private void addAdditionalSystem2(final StandardFarmAnimalHousing housing) {
    final StandardAdditionalHousingSystem additionalSystem = new StandardAdditionalHousingSystem();
    final String systemCode = "ADD2";
    additionalSystem.setAdditionalSystemCode(systemCode);

    final Map<Substance, Double> emissionFactors = Map.of(Substance.NH3, 0.05);
    when(emissionFactorSupplier.getAdditionalHousingSystemReductionFractions(systemCode, housing.getAnimalHousingCode())).thenReturn(emissionFactors);

    housing.getAdditionalSystems().add(additionalSystem);
  }

  private void addAdditionalSystemCustom(final StandardFarmAnimalHousing housing, final boolean airScrubber) {
    final CustomAdditionalHousingSystem additionalSystem = new CustomAdditionalHousingSystem();
    additionalSystem.setAirScrubber(airScrubber);
    additionalSystem.getEmissionReductionFactors().put(Substance.NH3, 0.77);

    housing.getAdditionalSystems().add(additionalSystem);
  }

  private StandardFarmAnimalHousing createStandardHousingPerDay() {
    final StandardFarmAnimalHousing housing = new StandardFarmAnimalHousing();
    housing.setNumberOfAnimals(1000);
    housing.setNumberOfDays(175);
    final String housingCode = "ABC";
    housing.setAnimalHousingCode(housingCode);

    final Map<Substance, Double> emissionFactors = Map.of(Substance.NH3, 0.02);
    when(emissionFactorSupplier.getAnimalHousingEmissionFactors(housingCode)).thenReturn(emissionFactors);
    when(emissionFactorSupplier.getAnimalHousingEmissionFactorType(housingCode)).thenReturn(FarmEmissionFactorType.PER_ANIMAL_PER_DAY);

    return housing;
  }
}
