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
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.overheid.aerius.shared.ImaerConstants;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.FarmLodgingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.farm.AdditionalLodgingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.CustomFarmLodging;
import nl.overheid.aerius.shared.domain.v2.source.farm.FarmLodging;
import nl.overheid.aerius.shared.domain.v2.source.farm.LodgingFodderMeasure;
import nl.overheid.aerius.shared.domain.v2.source.farm.ReductiveLodgingSystem;
import nl.overheid.aerius.shared.domain.v2.source.farm.StandardFarmLodging;
import nl.overheid.aerius.shared.exception.AeriusException;

@ExtendWith(MockitoExtension.class)
class FarmLodgingEmissionsCalculatorTest {

  @Mock FarmLodgingEmissionFactorSupplier emissionFactorSupplier;

  FarmLodgingEmissionsCalculator emissionsCalculator;

  @BeforeEach
  void beforeEach() throws AeriusException {
    emissionsCalculator = new FarmLodgingEmissionsCalculator(emissionFactorSupplier);
  }

  @Test
  void testCalculateEmissions() throws AeriusException {
    final FarmLodgingEmissionSource emissionSource = new FarmLodgingEmissionSource();
    final CustomFarmLodging lodging1 = new CustomFarmLodging();
    lodging1.getEmissionFactors().put(Substance.NOX, 44.3);
    lodging1.setNumberOfAnimals(2);
    emissionSource.getSubSources().add(lodging1);
    final CustomFarmLodging lodging2 = new CustomFarmLodging();
    lodging2.getEmissionFactors().put(Substance.NOX, 5.4);
    lodging2.getEmissionFactors().put(Substance.NH3, 7.9);
    lodging2.setNumberOfAnimals(3);
    emissionSource.getSubSources().add(lodging2);
    final StandardFarmLodging lodging3 = createTestObjectBase();
    addAdditionalSystem1(lodging3);
    addAdditionalSystem2(lodging3);
    addReductiveSystem1(lodging3);
    addReductiveSystem2(lodging3);
    addFodderMeasure1(lodging3);
    addFodderMeasure2(lodging3);
    emissionSource.getSubSources().add(lodging3);

    // Ensure emissions per subsource are unknown at start
    for (final FarmLodging lodging : emissionSource.getSubSources()) {
      assertTrue(lodging.getEmissions().isEmpty());
    }

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource);

    // Check total emissions
    assertEquals(104.8, results.get(Substance.NOX));
    assertEquals(23.7 + 58.4375, results.get(Substance.NH3));
    // Check emissions per subsource (should be set during calculation)
    assertEquals(88.6, lodging1.getEmissions().get(Substance.NOX));
    assertEquals(16.2, lodging2.getEmissions().get(Substance.NOX));
    assertNull(lodging3.getEmissions().get(Substance.NOX));
    assertNull(lodging1.getEmissions().get(Substance.NH3));
    assertEquals(23.7, lodging2.getEmissions().get(Substance.NH3));
    assertEquals(58.4375, lodging3.getEmissions().get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsWithNumberOfDays() throws AeriusException {
    final CustomFarmLodging lodging = new CustomFarmLodging();
    lodging.getEmissionFactors().put(Substance.NOX, 0.034567);
    lodging.setNumberOfAnimals(56);
    lodging.setFarmEmissionFactorType(FarmEmissionFactorType.PER_ANIMAL_PER_DAY);
    lodging.setNumberOfDays(100);

    final FarmLodgingEmissionSource emissionSource = new FarmLodgingEmissionSource();
    emissionSource.getSubSources().add(lodging);

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource);

    assertEquals(193.5752, results.get(Substance.NOX));
  }

  @Test
  void testCalculateEmissionsWithMinNumberOfDays() throws AeriusException {
    final CustomFarmLodging lodging = new CustomFarmLodging();
    lodging.getEmissionFactors().put(Substance.NOX, 12.34567);
    lodging.setNumberOfAnimals(56);
    lodging.setFarmEmissionFactorType(FarmEmissionFactorType.PER_ANIMAL_PER_DAY);
    lodging.setNumberOfDays(0);

    final FarmLodgingEmissionSource emissionSource = new FarmLodgingEmissionSource();
    emissionSource.getSubSources().add(lodging);

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource);

    assertEquals(0, results.get(Substance.NOX));
  }

  @Test
  void testCalculateEmissionsCustomFarmLodging() {
    final CustomFarmLodging lodging = new CustomFarmLodging();
    lodging.getEmissionFactors().put(Substance.NOX, 12.34567);
    lodging.setNumberOfAnimals(56);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(lodging);

    assertEquals(BigDecimal.valueOf(691.35752), results.get(Substance.NOX));
  }

  @Test
  void testCalculateEmissionsStandardFarmLodging() {
    final StandardFarmLodging lodging = createTestObjectBase();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(lodging);

    assertEquals(BigDecimal.valueOf(3000.0), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsStandardFarmLodgingOneAdditional() {
    final StandardFarmLodging lodging = createTestObjectBase();
    addAdditionalSystem1(lodging);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(lodging);

    assertEquals(BigDecimal.valueOf(4000.0), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsStandardFarmLodgingTwoAdditionals() {
    final StandardFarmLodging lodging = createTestObjectBase();
    addAdditionalSystem1(lodging);
    addAdditionalSystem2(lodging);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(lodging);

    assertEquals(BigDecimal.valueOf(4250.0), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsStandardFarmLodgingOneReductive() {
    final StandardFarmLodging lodging = createTestObjectBase();
    addReductiveSystem1(lodging);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(lodging);

    assertEquals(new BigDecimal("1500.00"), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsStandardFarmLodgingTwoReductives() {
    final StandardFarmLodging lodging = createTestObjectBase();
    addReductiveSystem1(lodging);
    addReductiveSystem2(lodging);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(lodging);

    assertEquals(new BigDecimal("75.0000"), results.get(Substance.NH3));
    assertEquals(75.0, results.get(Substance.NH3).doubleValue());
  }

  @Test
  void testCalculateEmissionsStandardFarmLodgingOneFodderMeasure() {
    final StandardFarmLodging lodging = createTestObjectBase();
    addFodderMeasure1(lodging);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(lodging);

    assertEquals(new BigDecimal("1950.000"), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsStandardFarmLodgingTwoFodderMeasures() {
    final StandardFarmLodging lodging = createTestObjectBase();
    addFodderMeasure1(lodging);
    addFodderMeasure2(lodging);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(lodging);

    assertEquals(new BigDecimal("1650.000"), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsStandardFarmLodgingMixed() {
    final StandardFarmLodging lodging = createTestObjectBase();
    addAdditionalSystem1(lodging);
    addAdditionalSystem2(lodging);
    addReductiveSystem1(lodging);
    addReductiveSystem2(lodging);
    addFodderMeasure1(lodging);
    addFodderMeasure2(lodging);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(lodging);

    assertEquals(new BigDecimal("58.437500"), results.get(Substance.NH3));
    assertEquals(58.4375, results.get(Substance.NH3).doubleValue());
  }

  @Test
  void testCalculateEmissionsStandardFarmLodgingConstrained() {
    final StandardFarmLodging lodging = createTestObjectBaseConstrained();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(lodging);

    assertEquals(BigDecimal.valueOf(1000.0), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsStandardFarmLodgingConstrainedOneAdditionalNonScrubber() {
    final StandardFarmLodging lodging = createTestObjectBaseConstrained();
    addAdditionalSystem1(lodging);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(lodging);

    assertEquals(BigDecimal.valueOf(2000.0), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsStandardFarmLodgingConstrainedOneAdditionalScrubber() {
    final StandardFarmLodging lodging = createTestObjectBaseConstrained();
    addAdditionalSystem1(lodging, true);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(lodging);

    assertEquals(BigDecimal.valueOf(2500.0), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsStandardFarmLodgingConstrainedOneReductiveNonScrubber() {
    final StandardFarmLodging lodging = createTestObjectBaseConstrained();
    addReductiveSystem1(lodging);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(lodging);

    assertEquals(new BigDecimal("500.00"), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsStandardFarmLodgingConstrainedOneReductiveScrubber() {
    final StandardFarmLodging lodging = createTestObjectBaseConstrained();
    addReductiveSystem1(lodging, true);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(lodging);

    assertEquals(new BigDecimal("750.00"), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsStandardFarmLodgingConstrainedMixed() {
    final StandardFarmLodging lodging = createTestObjectBaseConstrained();
    addAdditionalSystem1(lodging, true);
    addAdditionalSystem2(lodging);
    addReductiveSystem1(lodging, true);
    addReductiveSystem2(lodging);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(lodging);

    assertEquals(new BigDecimal("68.7500"), results.get(Substance.NH3));
    assertEquals(68.75, results.get(Substance.NH3).doubleValue());
  }

  private StandardFarmLodging createTestObjectBase() {
    final StandardFarmLodging lodging = new StandardFarmLodging();
    lodging.setNumberOfAnimals(1000);
    final String lodgingCode = "ABC";
    lodging.setFarmLodgingCode(lodgingCode);

    final Map<Substance, Double> emissionFactors = Map.of(Substance.NH3, 3.0);
    when(emissionFactorSupplier.getLodgingEmissionFactors(lodgingCode)).thenReturn(emissionFactors);

    return lodging;
  }

  private StandardFarmLodging createTestObjectBaseConstrained() {
    final StandardFarmLodging lodging = new StandardFarmLodging();
    lodging.setNumberOfAnimals(1000);
    final String lodgingCode = "ABC";
    lodging.setFarmLodgingCode(lodgingCode);

    final Map<Substance, Double> emissionFactors = Map.of(Substance.NH3, 1.0);
    final Map<Substance, Double> emissionFactorsConstrained = Map.of(Substance.NH3, 1.5);
    lenient().when(emissionFactorSupplier.getLodgingEmissionFactors(lodgingCode)).thenReturn(emissionFactors);
    lenient().when(emissionFactorSupplier.getLodgingConstrainedEmissionFactors(lodgingCode)).thenReturn(emissionFactorsConstrained);
    when(emissionFactorSupplier.canLodgingEmissionFactorsBeConstrained(lodgingCode)).thenReturn(true);

    return lodging;
  }

  private void addAdditionalSystem1(final StandardFarmLodging lodging) {
    addAdditionalSystem1(lodging, false);
  }

  private void addAdditionalSystem1(final StandardFarmLodging lodging, final boolean scrubber) {
    final AdditionalLodgingSystem additionalSystem = new AdditionalLodgingSystem();
    final String systemCode = "ADD1";
    additionalSystem.setLodgingSystemCode(systemCode);
    additionalSystem.setNumberOfAnimals(500);

    final Map<Substance, Double> emissionFactors = Map.of(Substance.NH3, 2.0);
    when(emissionFactorSupplier.getAdditionalSystemEmissionFactors(systemCode)).thenReturn(emissionFactors);
    lenient().when(emissionFactorSupplier.isAdditionalSystemScrubber(systemCode)).thenReturn(scrubber);

    lodging.getAdditionalLodgingSystems().add(additionalSystem);
  }

  private void addAdditionalSystem2(final StandardFarmLodging lodging) {
    final AdditionalLodgingSystem additionalSystem = new AdditionalLodgingSystem();
    final String systemCode = "ADD2";
    additionalSystem.setLodgingSystemCode(systemCode);
    additionalSystem.setNumberOfAnimals(100);

    final Map<Substance, Double> emissionFactors = Map.of(Substance.NH3, 2.5);
    when(emissionFactorSupplier.getAdditionalSystemEmissionFactors(systemCode)).thenReturn(emissionFactors);

    lodging.getAdditionalLodgingSystems().add(additionalSystem);
  }

  private void addReductiveSystem1(final StandardFarmLodging lodging) {
    addReductiveSystem1(lodging, false);
  }

  private void addReductiveSystem1(final StandardFarmLodging lodging, final boolean scrubber) {
    final ReductiveLodgingSystem reductiveSystem = new ReductiveLodgingSystem();
    final String systemCode = "RED1";
    reductiveSystem.setLodgingSystemCode(systemCode);

    final Map<Substance, Double> emissionFactors = Map.of(Substance.NH3, 0.5);
    when(emissionFactorSupplier.getReductiveSystemRemainingFractions(systemCode)).thenReturn(emissionFactors);
    lenient().when(emissionFactorSupplier.isReductiveSystemScrubber(systemCode)).thenReturn(scrubber);

    lodging.getReductiveLodgingSystems().add(reductiveSystem);
  }

  private void addReductiveSystem2(final StandardFarmLodging lodging) {
    final ReductiveLodgingSystem reductiveSystem = new ReductiveLodgingSystem();
    final String systemCode = "RED2";
    reductiveSystem.setLodgingSystemCode(systemCode);

    final Map<Substance, Double> emissionFactors = Map.of(Substance.NH3, 0.05);
    when(emissionFactorSupplier.getReductiveSystemRemainingFractions(systemCode)).thenReturn(emissionFactors);

    lodging.getReductiveLodgingSystems().add(reductiveSystem);
  }

  private void addFodderMeasure1(final StandardFarmLodging lodging) {
    final LodgingFodderMeasure fodderMeasure = new LodgingFodderMeasure();
    final String fodderMeasureCode = "FOD1";
    fodderMeasure.setFodderMeasureCode(fodderMeasureCode);

    final Map<Substance, Double> remainingFractionTotal = Map.of(Substance.NH3, 0.65);
    final Map<Substance, Double> remainingFractionFloor = Map.of(Substance.NH3, 0.84);
    final Map<Substance, Double> remainingFractionCellar = Map.of(Substance.NH3, 0.6);
    final Map<Substance, Double> proportionFloor = Map.of(Substance.NH3, 0.3);
    final Map<Substance, Double> proportionCellar = Map.of(Substance.NH3, 0.7);
    lenient().when(emissionFactorSupplier.canFodderApplyToLodging(fodderMeasureCode, lodging.getFarmLodgingCode())).thenReturn(true);
    lenient().when(emissionFactorSupplier.getFodderRemainingFractionTotal(fodderMeasureCode)).thenReturn(remainingFractionTotal);
    lenient().when(emissionFactorSupplier.getFodderRemainingFractionFloor(fodderMeasureCode)).thenReturn(remainingFractionFloor);
    lenient().when(emissionFactorSupplier.getFodderRemainingFractionCellar(fodderMeasureCode)).thenReturn(remainingFractionCellar);
    lenient().when(emissionFactorSupplier.getFodderProportionFloor(fodderMeasureCode, lodging.getFarmLodgingCode())).thenReturn(proportionFloor);
    lenient().when(emissionFactorSupplier.getFodderProportionCellar(fodderMeasureCode, lodging.getFarmLodgingCode())).thenReturn(proportionCellar);

    lodging.getFodderMeasures().add(fodderMeasure);
  }

  private void addFodderMeasure2(final StandardFarmLodging lodging) {
    final LodgingFodderMeasure fodderMeasure = new LodgingFodderMeasure();
    final String fodderMeasureCode = "FOD2";
    fodderMeasure.setFodderMeasureCode(fodderMeasureCode);

    final Map<Substance, Double> remainingFractionTotal = Map.of(Substance.NH3, 0.8);
    final Map<Substance, Double> remainingFractionFloor = Map.of(Substance.NH3, 0.8);
    final Map<Substance, Double> remainingFractionCellar = Map.of(Substance.NH3, 0.8);
    final Map<Substance, Double> proportionFloor = Map.of(Substance.NH3, 0.3);
    final Map<Substance, Double> proportionCellar = Map.of(Substance.NH3, 0.7);
    lenient().when(emissionFactorSupplier.canFodderApplyToLodging(fodderMeasureCode, lodging.getFarmLodgingCode())).thenReturn(true);
    lenient().when(emissionFactorSupplier.getFodderRemainingFractionTotal(fodderMeasureCode)).thenReturn(remainingFractionTotal);
    lenient().when(emissionFactorSupplier.getFodderRemainingFractionFloor(fodderMeasureCode)).thenReturn(remainingFractionFloor);
    lenient().when(emissionFactorSupplier.getFodderRemainingFractionCellar(fodderMeasureCode)).thenReturn(remainingFractionCellar);
    lenient().when(emissionFactorSupplier.getFodderProportionFloor(fodderMeasureCode, lodging.getFarmLodgingCode())).thenReturn(proportionFloor);
    lenient().when(emissionFactorSupplier.getFodderProportionCellar(fodderMeasureCode, lodging.getFarmLodgingCode())).thenReturn(proportionCellar);

    lodging.getFodderMeasures().add(fodderMeasure);
  }

}
