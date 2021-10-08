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

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.FarmlandEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.farmland.FarmlandActivity;
import nl.overheid.aerius.shared.exception.AeriusException;

class FarmlandEmissionsCalculatorTest {

  FarmlandEmissionsCalculator emissionsCalculator;

  @BeforeEach
  void beforeEach() throws AeriusException {
    emissionsCalculator = new FarmlandEmissionsCalculator();
  }

  @Test
  void testCalculateEmissions() {
    final FarmlandEmissionSource emissionSource = new FarmlandEmissionSource();
    final FarmlandActivity activity1 = new FarmlandActivity();
    activity1.getEmissions().put(Substance.NOX, 993.23);
    emissionSource.getSubSources().add(activity1);
    final FarmlandActivity activity2 = new FarmlandActivity();
    activity2.getEmissions().put(Substance.NOX, 5.423321);
    activity2.getEmissions().put(Substance.NH3, 7.9);
    emissionSource.getSubSources().add(activity2);

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource);

    assertEquals(998.653321, results.get(Substance.NOX));
    assertEquals(7.9, results.get(Substance.NH3));
  }

}
