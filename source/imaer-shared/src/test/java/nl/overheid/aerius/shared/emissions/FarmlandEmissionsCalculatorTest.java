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
import static org.mockito.Mockito.doReturn;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.FarmlandEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.farmland.AbstractFarmlandActivity;
import nl.overheid.aerius.shared.domain.v2.source.farmland.FarmlandManureActivity;
import nl.overheid.aerius.shared.domain.v2.source.farmland.FarmlandOrganicProcessesActivity;
import nl.overheid.aerius.shared.domain.v2.source.farmland.FarmlandPastureActivity;
import nl.overheid.aerius.shared.exception.AeriusException;

@ExtendWith(MockitoExtension.class)
class FarmlandEmissionsCalculatorTest {

  private static final String PASTURE_CATEGORY_CODE = "pastureCategoryCode";
  @Mock FarmlandEmissionFactorSupplier emissionFactorSupplier;
  FarmlandEmissionsCalculator emissionsCalculator;

  @BeforeEach
  void beforeEach() throws AeriusException {
    emissionsCalculator = new FarmlandEmissionsCalculator(emissionFactorSupplier);
  }

  @Test
  void testCalculateEmissions() {
    final FarmlandEmissionSource emissionSource = new FarmlandEmissionSource();
    final AbstractFarmlandActivity activity1 = new FarmlandManureActivity();
    activity1.getEmissions().put(Substance.NOX, 993.23);
    emissionSource.getSubSources().add(activity1);
    final AbstractFarmlandActivity activity2 = new FarmlandOrganicProcessesActivity();
    activity2.getEmissions().put(Substance.NOX, 5.423321);
    activity2.getEmissions().put(Substance.NH3, 7.9);
    emissionSource.getSubSources().add(activity2);

    final Map<Substance, Double> results = emissionsCalculator.updateEmissions(emissionSource);

    assertEquals(998.653321, results.get(Substance.NOX));
    assertEquals(7.9, results.get(Substance.NH3));
  }

  @Test
  void testCalculateGrazingEmissions() {
    final FarmlandEmissionSource emissionSource = getGrazingEmissionSource(FarmEmissionFactorType.PER_ANIMAL_PER_DAY);

    final Map<Substance, Double> results = emissionsCalculator.updateEmissions(emissionSource);

    assertEquals(50, results.get(Substance.NOX));
  }

  @Test
  void testCalculateGrazingEmissionsPerYear() {
    final FarmlandEmissionSource emissionSource = getGrazingEmissionSource(FarmEmissionFactorType.PER_ANIMAL_PER_YEAR);

    final Map<Substance, Double> results = emissionsCalculator.updateEmissions(emissionSource);

    assertEquals(0.5, results.get(Substance.NOX));
  }

  @Test
  void testCalculateGrazingEmissionsWithSuppliedFactor() {
    final FarmlandEmissionSource emissionSource = getGrazingEmissionSource(FarmEmissionFactorType.PER_ANIMAL_PER_DAY);
    doReturn(Map.of(Substance.NOX, 1.0)).when(emissionFactorSupplier).getPastureActivityEmissionFactors(PASTURE_CATEGORY_CODE);

    final Map<Substance, Double> results = emissionsCalculator.updateEmissions(emissionSource);

    assertEquals(100, results.get(Substance.NOX));
  }

  private static FarmlandEmissionSource getGrazingEmissionSource(FarmEmissionFactorType farmEmissionFactorType) {
    final FarmlandEmissionSource emissionSource = new FarmlandEmissionSource();
    final FarmlandPastureActivity activity = new FarmlandPastureActivity();
    activity.setPastureCategoryCode(PASTURE_CATEGORY_CODE);
    activity.getEmissions().put(Substance.NOX, 0.5);
    activity.setFarmEmissionFactorType(farmEmissionFactorType);
    activity.setDays(100);
    emissionSource.getSubSources().add(activity);
    return emissionSource;
  }
}
