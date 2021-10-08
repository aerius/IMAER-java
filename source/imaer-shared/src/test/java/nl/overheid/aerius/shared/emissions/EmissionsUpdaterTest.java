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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.exception.AeriusException;

@ExtendWith(MockitoExtension.class)
class EmissionsUpdaterTest {

  @Mock EmissionsCalculator calculator;
  @Mock EmissionSourceFeature sourceFeatureOne;
  @Mock EmissionSourceFeature sourceFeatureTwo;

  EmissionsUpdater emissionsUpdater;

  @BeforeEach
  void beforeEach() throws AeriusException {
    emissionsUpdater = new EmissionsUpdater(calculator);
  }

  @Test
  void testUpdateEmissionsCollection() throws AeriusException {
    final Map<Substance, Double> emissionsSourceOne = Map.of(Substance.NH3, 324.0);
    when(sourceFeatureOne.accept(calculator)).thenReturn(emissionsSourceOne);
    final EmissionSource sourceOne = mock(EmissionSource.class);
    when(sourceFeatureOne.getProperties()).thenReturn(sourceOne);

    final Map<Substance, Double> emissionsSourceTwo = Map.of(Substance.NOX, 4.4, Substance.NH3, 5.2);
    when(sourceFeatureTwo.accept(calculator)).thenReturn(emissionsSourceTwo);
    final EmissionSource sourceTwo = mock(EmissionSource.class);
    when(sourceFeatureTwo.getProperties()).thenReturn(sourceTwo);

    emissionsUpdater.updateEmissions(List.of(sourceFeatureOne, sourceFeatureTwo));

    verify(sourceOne).setEmissions(emissionsSourceOne);
    verify(sourceTwo).setEmissions(emissionsSourceTwo);
  }

  @Test
  void testUpdateEmissionsSingle() throws AeriusException {
    final Map<Substance, Double> emissionsSource = Map.of(Substance.NH3, 324.0);
    when(sourceFeatureOne.accept(calculator)).thenReturn(emissionsSource);
    final EmissionSource source = mock(EmissionSource.class);
    when(sourceFeatureOne.getProperties()).thenReturn(source);

    emissionsUpdater.updateEmissions(sourceFeatureOne);

    verify(source).setEmissions(emissionsSource);
  }

}
