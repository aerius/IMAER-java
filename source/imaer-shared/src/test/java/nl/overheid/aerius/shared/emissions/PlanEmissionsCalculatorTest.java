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
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.PlanEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.plan.Plan;
import nl.overheid.aerius.shared.exception.AeriusException;

@ExtendWith(MockitoExtension.class)
class PlanEmissionsCalculatorTest {

  @Mock PlanEmissionFactorSupplier emissionFactorSupplier;

  PlanEmissionsCalculator emissionsCalculator;

  @BeforeEach
  void beforeEach() throws AeriusException {
    emissionsCalculator = new PlanEmissionsCalculator(emissionFactorSupplier);
  }

  @Test
  void testCalculateEmissionsOneSubstance() {
    final PlanEmissionSource planSource = new PlanEmissionSource();
    planSource.getSubSources().add(getExamplePlanOneSubstance());

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(planSource);

    assertEquals(1, results.size(), "1 emission only");
    assertEquals(44, results.get(Substance.NOX), "Emissions for NOx");
  }

  @Test
  void testCalculateEmissionsTwoSubstances() {
    final PlanEmissionSource planSource = new PlanEmissionSource();
    planSource.getSubSources().add(getExamplePlanTwoSubstances());

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(planSource);

    assertEquals(2, results.size(), "2 emissions");
    assertEquals(220, results.get(Substance.NOX), "Emissions for NOx");
    assertEquals(88, results.get(Substance.NH3), "Emissions for NH3");
  }

  @Test
  void testCalculateEmissionsNotPerUnit() {
    final PlanEmissionSource planSource = new PlanEmissionSource();
    planSource.getSubSources().add(getExamplePlanNotPerUnit());

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(planSource);

    assertEquals(1, results.size(), "1 emission only");
    assertEquals(203, results.get(Substance.NOX), "Emissions for NOx");
  }

  @Test
  void testCalculateEmissionsMixed() {
    final PlanEmissionSource planSource = new PlanEmissionSource();
    planSource.getSubSources().add(getExamplePlanOneSubstance());
    planSource.getSubSources().add(getExamplePlanTwoSubstances());
    planSource.getSubSources().add(getExamplePlanNotPerUnit());

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(planSource);

    assertEquals(2, results.size(), "2 emissions");
    assertEquals(467, results.get(Substance.NOX), "Emissions for NOx");
    assertEquals(88, results.get(Substance.NH3), "Emissions for NH3");
  }

  private Plan getExamplePlanOneSubstance() {
    final Plan plan = new Plan();
    plan.setAmount(22);
    final String planCode = "AAA";
    plan.setPlanCode(planCode);
    when(emissionFactorSupplier.isEmissionFactorPerUnits(planCode)).thenReturn(true);
    when(emissionFactorSupplier.getPlanEmissionFactors(planCode)).thenReturn(Map.of(Substance.NOX, 2.0));
    return plan;
  }

  private Plan getExamplePlanTwoSubstances() {
    final Plan plan = new Plan();
    plan.setAmount(22);
    final String planCode = "BBB";
    plan.setPlanCode(planCode);
    when(emissionFactorSupplier.isEmissionFactorPerUnits(planCode)).thenReturn(true);
    when(emissionFactorSupplier.getPlanEmissionFactors(planCode)).thenReturn(Map.of(Substance.NOX, 10.0, Substance.NH3, 4.0));

    return plan;
  }

  private Plan getExamplePlanNotPerUnit() {
    final Plan plan = new Plan();
    plan.setAmount(22);
    final String planCode = "CCC";
    plan.setPlanCode(planCode);
    when(emissionFactorSupplier.isEmissionFactorPerUnits(planCode)).thenReturn(false);
    when(emissionFactorSupplier.getPlanEmissionFactors(planCode)).thenReturn(Map.of(Substance.NOX, 203.0));
    return plan;
  }

}
