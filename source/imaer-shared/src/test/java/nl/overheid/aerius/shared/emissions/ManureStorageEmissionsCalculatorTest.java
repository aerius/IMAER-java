/*
 * Crown copyright
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
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import nl.overheid.aerius.shared.domain.v2.source.ManureStorageEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.manure.AbstractManureStorage;
import nl.overheid.aerius.shared.domain.v2.source.manure.CustomManureStorage;
import nl.overheid.aerius.shared.domain.v2.source.manure.StandardManureStorage;
import nl.overheid.aerius.shared.exception.AeriusException;

@ExtendWith(MockitoExtension.class)
class ManureStorageEmissionsCalculatorTest {

  private static final String CATEGORY_CODE = "someCategoryCode";
  @Mock ManureStorageEmissionFactorSupplier emissionFactorSupplier;
  ManureStorageEmissionsCalculator emissionsCalculator;

  @BeforeEach
  void beforeEach() throws AeriusException {
    emissionsCalculator = new ManureStorageEmissionsCalculator(emissionFactorSupplier);
  }

  @ParameterizedTest
  @MethodSource("casesPerEmissionFactorType")
  void testCalculateCustomEmissions(final FarmEmissionFactorType emissionFactorType, final double expectedNOxResult) throws AeriusException {
    final ManureStorageEmissionSource emissionSource = new ManureStorageEmissionSource();
    final CustomManureStorage customManureStorage = new CustomManureStorage();
    customManureStorage.setFarmEmissionFactorType(emissionFactorType);
    customManureStorage.getEmissionFactors().put(Substance.NOX, 2.0);
    setTestProperties(customManureStorage);
    emissionSource.getSubSources().add(customManureStorage);
    final CustomManureStorage customManureStorage2 = new CustomManureStorage();
    customManureStorage2.setFarmEmissionFactorType(emissionFactorType);
    setTestProperties(customManureStorage2);
    customManureStorage2.getEmissionFactors().put(Substance.NOX, 1.0);
    customManureStorage2.getEmissionFactors().put(Substance.NH3, 4.0);
    emissionSource.getSubSources().add(customManureStorage2);

    final Map<Substance, Double> results = emissionsCalculator.updateEmissions(emissionSource);

    assertEquals(expectedNOxResult * 3, results.get(Substance.NOX), 1E-7, "Emissions should be summed over all activities for a farmland source.");
    assertEquals(expectedNOxResult * 4, results.get(Substance.NH3), 1E-7, "Emission of each substance is summed individually.");
    assertEquals(expectedNOxResult * 2, customManureStorage.getEmissions().get(Substance.NOX), 1E-7,
        "Emissions for subsource should be updated as well.");
    assertFalse(customManureStorage.getEmissions().containsKey(Substance.NH3),
        "If emision factor wasn't present, it shouldn't be added to the subsource.");
    assertEquals(expectedNOxResult * 1, customManureStorage2.getEmissions().get(Substance.NOX), 1E-7,
        "Emissions for subsource 2 should be updated as well.");
    assertEquals(expectedNOxResult * 4, customManureStorage2.getEmissions().get(Substance.NH3), 1E-7,
        "Emissions for subsource 2 should be updated as well.");
  }

  @ParameterizedTest
  @MethodSource("casesPerEmissionFactorType")
  void testCalculateStandardEmissions(final FarmEmissionFactorType emissionFactorType, final double expectedNOxResult)
      throws AeriusException {
    final ManureStorageEmissionSource emissionSource = getEmissionSourceWithStandard();
    doReturn(emissionFactorType).when(emissionFactorSupplier).getManureStorageEmissionFactorType(CATEGORY_CODE);
    doReturn(Map.of(Substance.NOX, 1.0)).when(emissionFactorSupplier).getManureStorageEmissionFactors(CATEGORY_CODE);

    final Map<Substance, Double> results = emissionsCalculator.updateEmissions(emissionSource);

    assertEquals(expectedNOxResult, results.get(Substance.NOX),
        "Emissions should be calculated based on factor, number of animals, and number of days.");
    assertEquals(expectedNOxResult, emissionSource.getSubSources().get(0).getEmissions().get(Substance.NOX),
        "Emissions should be set on subsource as well");
  }

  private static Stream<Arguments> casesPerEmissionFactorType() {
    return Stream.of(
        Arguments.of(FarmEmissionFactorType.PER_TONNES_PER_YEAR, 21.4),
        Arguments.of(FarmEmissionFactorType.PER_METERS_SQUARED_PER_YEAR, 5.3),
        Arguments.of(FarmEmissionFactorType.PER_METERS_SQUARED_PER_DAY, 1060));
  }

  @Test
  void testCalculateStandardEmissionsWithoutSuppliedFactor() throws AeriusException {
    final ManureStorageEmissionSource emissionSource = getEmissionSourceWithStandard();
    doReturn(FarmEmissionFactorType.PER_METERS_SQUARED_PER_DAY).when(emissionFactorSupplier).getManureStorageEmissionFactorType(CATEGORY_CODE);
    doReturn(Collections.emptyMap()).when(emissionFactorSupplier).getManureStorageEmissionFactors(CATEGORY_CODE);

    final Map<Substance, Double> results = emissionsCalculator.updateEmissions(emissionSource);

    assertEquals(0, results.size(), "When no emission factors are present, no emissions should be returned.");
  }

  private static ManureStorageEmissionSource getEmissionSourceWithStandard() {
    final ManureStorageEmissionSource emissionSource = new ManureStorageEmissionSource();
    final StandardManureStorage storage = new StandardManureStorage();
    storage.setManureStorageCode(CATEGORY_CODE);
    storage.getEmissions().put(Substance.NOX, 0.5);
    setTestProperties(storage);
    emissionSource.getSubSources().add(storage);
    return emissionSource;
  }

  private static void setTestProperties(final AbstractManureStorage storage) {
    storage.setTonnes(21.4);
    storage.setMetersSquared(5.3);
    storage.setNumberOfDays(200);
  }
}
