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

import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.FarmlandEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.farmland.AbstractFarmlandActivity;
import nl.overheid.aerius.shared.domain.v2.source.farmland.CustomFarmlandActivity;
import nl.overheid.aerius.shared.domain.v2.source.farmland.StandardFarmlandActivity;
import nl.overheid.aerius.shared.exception.AeriusException;

@ExtendWith(MockitoExtension.class)
class FarmlandEmissionsCalculatorTest {

  private static final String CATEGORY_CODE = "someCategoryCode";
  @Mock FarmlandEmissionFactorSupplier emissionFactorSupplier;
  FarmlandEmissionsCalculator emissionsCalculator;

  @BeforeEach
  void beforeEach() throws AeriusException {
    emissionsCalculator = new FarmlandEmissionsCalculator(emissionFactorSupplier);
  }

  @Test
  void testCalculateCustomEmissions() throws AeriusException {
    final FarmlandEmissionSource emissionSource = new FarmlandEmissionSource();
    final AbstractFarmlandActivity activity1 = new CustomFarmlandActivity();
    activity1.getEmissions().put(Substance.NOX, 993.23);
    emissionSource.getSubSources().add(activity1);
    final AbstractFarmlandActivity activity2 = new CustomFarmlandActivity();
    activity2.getEmissions().put(Substance.NOX, 5.423321);
    activity2.getEmissions().put(Substance.NH3, 7.9);
    emissionSource.getSubSources().add(activity2);

    final Map<Substance, Double> results = emissionsCalculator.updateEmissions(emissionSource);

    assertEquals(998.653321, results.get(Substance.NOX), "Emissions should be summed over all activities for a farmland source.");
    assertEquals(7.9, results.get(Substance.NH3), "Emission of each substance is summed individually.");
  }

  @ParameterizedTest
  @MethodSource("casesPerEmissionFactorType")
  void testCalculateStandardEmissionsPerAnimalPerDay(final FarmEmissionFactorType emissionFactorType, final double expectedNOxResult)
      throws AeriusException {
    final FarmlandEmissionSource emissionSource = getEmissionSourceWitStandardFarmlandActivity();
    doReturn(emissionFactorType).when(emissionFactorSupplier).getFarmSourceEmissionFactorType(CATEGORY_CODE);
    doReturn(Map.of(Substance.NOX, 1.0)).when(emissionFactorSupplier).getFarmSourceEmissionFactors(CATEGORY_CODE);

    final Map<Substance, Double> results = emissionsCalculator.updateEmissions(emissionSource);

    assertEquals(expectedNOxResult, results.get(Substance.NOX),
        "Emissions should be calculated based on factor, number of animals, and number of days.");
    assertEquals(expectedNOxResult, emissionSource.getSubSources().get(0).getEmissions().get(Substance.NOX),
        "Emissions should be set on subsource as well");
  }

  private static Stream<Arguments> casesPerEmissionFactorType() {
    return Stream.of(
        Arguments.of(FarmEmissionFactorType.PER_YEAR, 1),
        Arguments.of(FarmEmissionFactorType.PER_ANIMAL_PER_DAY, 200),
        Arguments.of(FarmEmissionFactorType.PER_ANIMAL_PER_YEAR, 2),
        Arguments.of(FarmEmissionFactorType.PER_METERS_CUBED_PER_APPLICATION, 90.6),
        Arguments.of(FarmEmissionFactorType.PER_TONNES_PER_APPLICATION, 120.9),
        Arguments.of(FarmEmissionFactorType.PER_TONNES_PER_YEAR, 40.3));
  }

  @Test
  void testCalculateStandardEmissionsWithoutSuppliedFactor() throws AeriusException {
    final FarmlandEmissionSource emissionSource = getEmissionSourceWitStandardFarmlandActivity();
    doReturn(FarmEmissionFactorType.PER_ANIMAL_PER_DAY).when(emissionFactorSupplier).getFarmSourceEmissionFactorType(CATEGORY_CODE);
    doReturn(Collections.emptyMap()).when(emissionFactorSupplier).getFarmSourceEmissionFactors(CATEGORY_CODE);

    final Map<Substance, Double> results = emissionsCalculator.updateEmissions(emissionSource);

    assertEquals(0, results.size(), "When no emission factors are present, no emissions should be returned.");
  }

  private static FarmlandEmissionSource getEmissionSourceWitStandardFarmlandActivity() {
    final FarmlandEmissionSource emissionSource = new FarmlandEmissionSource();
    final StandardFarmlandActivity activity = new StandardFarmlandActivity();
    activity.setFarmSourceCategoryCode(CATEGORY_CODE);
    activity.getEmissions().put(Substance.NOX, 0.5);
    activity.setNumberOfDays(100);
    activity.setNumberOfAnimals(2);
    activity.setMetersCubed(30.2);
    activity.setTonnes(40.3);
    activity.setNumberOfApplications(3);
    emissionSource.getSubSources().add(activity);
    return emissionSource;
  }
}
