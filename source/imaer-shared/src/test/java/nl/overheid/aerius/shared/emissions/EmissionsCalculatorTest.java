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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.geojson.IsFeature;
import nl.overheid.aerius.shared.domain.v2.source.FarmLodgingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.FarmlandEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.GenericEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.InlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MooringInlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MooringMaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.OffRoadMobileEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.PlanEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.SRM1RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.SRM2RoadEmissionSource;
import nl.overheid.aerius.shared.exception.AeriusException;

@ExtendWith(MockitoExtension.class)
class EmissionsCalculatorTest {

  @Mock FarmLodgingEmissionsCalculator farmLodgingCalculator;
  @Mock FarmlandEmissionsCalculator farmlandCalculator;
  @Mock PlanEmissionsCalculator planCalculator;
  @Mock OffRoadMobileEmissionsCalculator offRoadMobileCalculator;
  @Mock RoadEmissionsCalculator roadCalculator;
  @Mock InlandShippingEmissionsCalculator inlandShippingCalculator;
  @Mock MaritimeShippingEmissionsCalculator maritimeShippingCalculator;

  EmissionsCalculator emissionsCalculator;

  @BeforeEach
  void beforeEach() throws AeriusException {
    emissionsCalculator = new EmissionsCalculator(farmLodgingCalculator, farmlandCalculator, planCalculator, offRoadMobileCalculator,
        roadCalculator, inlandShippingCalculator, maritimeShippingCalculator);
  }

  @Test
  void testVisitFarmLodging() throws AeriusException {
    final Map<Substance, Double> calculatedEmissions = Map.of(Substance.NH3, 324.0);
    final FarmLodgingEmissionSource emissionSource = mock(FarmLodgingEmissionSource.class);
    when(farmLodgingCalculator.calculateEmissions(emissionSource)).thenReturn(calculatedEmissions);

    final Map<Substance, Double> result = emissionsCalculator.visit(emissionSource, null);

    assertEquals(calculatedEmissions, result,
        "Calculated emission values from FarmLodgingEmissionsCalculator should be returned for farm lodging source");
  }

  @Test
  void testVisitFarmland() throws AeriusException {
    final Map<Substance, Double> calculatedEmissions = Map.of(Substance.NH3, 324.0);
    final FarmlandEmissionSource emissionSource = mock(FarmlandEmissionSource.class);
    when(farmlandCalculator.calculateEmissions(emissionSource)).thenReturn(calculatedEmissions);

    final Map<Substance, Double> result = emissionsCalculator.visit(emissionSource, null);

    assertEquals(calculatedEmissions, result,
        "Calculated emission values from FarmlandEmissionsCalculator should be returned for farmland source");
  }

  @Test
  void testVisitPlan() throws AeriusException {
    final Map<Substance, Double> calculatedEmissions = Map.of(Substance.NH3, 324.0);
    final PlanEmissionSource emissionSource = mock(PlanEmissionSource.class);
    when(planCalculator.calculateEmissions(emissionSource)).thenReturn(calculatedEmissions);

    final Map<Substance, Double> result = emissionsCalculator.visit(emissionSource, null);

    assertEquals(calculatedEmissions, result,
        "Calculated emission values from PlanEmissionsCalculator should be returned for plan source");
  }

  @Test
  void testVisitOffRoadMobileSource() throws AeriusException {
    final Map<Substance, Double> calculatedEmissions = Map.of(Substance.NH3, 324.0);
    final OffRoadMobileEmissionSource emissionSource = mock(OffRoadMobileEmissionSource.class);
    when(offRoadMobileCalculator.calculateEmissions(emissionSource)).thenReturn(calculatedEmissions);

    final Map<Substance, Double> result = emissionsCalculator.visit(emissionSource, null);

    assertEquals(calculatedEmissions, result,
        "Calculated emission values from OffRoadMobileEmissionsCalculator should be returned for off road mobile source");
  }

  @Test
  void testVisitSRM1RoadSource() throws AeriusException {
    final Map<Substance, Double> calculatedEmissions = Map.of(Substance.NH3, 324.0);
    final SRM1RoadEmissionSource emissionSource = mock(SRM1RoadEmissionSource.class);
    final Geometry geometry = mock(Geometry.class);
    final IsFeature feature = mock(IsFeature.class);
    when(feature.getGeometry()).thenReturn(geometry);
    when(roadCalculator.calculateEmissions(emissionSource, geometry)).thenReturn(calculatedEmissions);

    final Map<Substance, Double> result = emissionsCalculator.visit(emissionSource, feature);

    assertEquals(calculatedEmissions, result,
        "Calculated emission values from RoadEmissionsCalculator should be returned for SRM1 road source");
  }

  @Test
  void testVisitSRM2RoadSource() throws AeriusException {
    final Map<Substance, Double> calculatedEmissions = Map.of(Substance.NH3, 324.0);
    final SRM2RoadEmissionSource emissionSource = mock(SRM2RoadEmissionSource.class);
    final Geometry geometry = mock(Geometry.class);
    final IsFeature feature = mock(IsFeature.class);
    when(feature.getGeometry()).thenReturn(geometry);
    when(roadCalculator.calculateEmissions(emissionSource, geometry)).thenReturn(calculatedEmissions);

    final Map<Substance, Double> result = emissionsCalculator.visit(emissionSource, feature);

    assertEquals(calculatedEmissions, result,
        "Calculated emission values from RoadEmissionsCalculator should be returned for SRM2 road source");
  }

  @Test
  void testVisitInlandShippingSource() throws AeriusException {
    final Map<Substance, Double> calculatedEmissions = Map.of(Substance.NH3, 324.0);
    final InlandShippingEmissionSource emissionSource = mock(InlandShippingEmissionSource.class);
    final Geometry geometry = mock(Geometry.class);
    final IsFeature feature = mock(IsFeature.class);
    when(feature.getGeometry()).thenReturn(geometry);
    when(inlandShippingCalculator.calculateEmissions(emissionSource, geometry)).thenReturn(calculatedEmissions);

    final Map<Substance, Double> result = emissionsCalculator.visit(emissionSource, feature);

    assertEquals(calculatedEmissions, result,
        "Calculated emission values from InlandShippingEmissionsCalculator should be returned for inland shipping source");
  }

  @Test
  void testVisitMooringInlandShippingSource() throws AeriusException {
    final Map<Substance, Double> calculatedEmissions = Map.of(Substance.NH3, 324.0);
    final MooringInlandShippingEmissionSource emissionSource = mock(MooringInlandShippingEmissionSource.class);
    when(inlandShippingCalculator.calculateEmissions(emissionSource)).thenReturn(calculatedEmissions);

    final Map<Substance, Double> result = emissionsCalculator.visit(emissionSource, null);

    assertEquals(calculatedEmissions, result,
        "Calculated emission values from InlandShippingEmissionsCalculator should be returned for mooring inland shipping source");
  }

  @Test
  void testVisitMaritimeShippingSource() throws AeriusException {
    final Map<Substance, Double> calculatedEmissions = Map.of(Substance.NH3, 324.0);
    final MaritimeShippingEmissionSource emissionSource = mock(MaritimeShippingEmissionSource.class);
    final Geometry geometry = mock(Geometry.class);
    final IsFeature feature = mock(IsFeature.class);
    when(feature.getGeometry()).thenReturn(geometry);
    when(maritimeShippingCalculator.calculateEmissions(emissionSource, geometry)).thenReturn(calculatedEmissions);

    final Map<Substance, Double> result = emissionsCalculator.visit(emissionSource, feature);

    assertEquals(calculatedEmissions, result,
        "Calculated emission values from MaritimeShippingEmissionsCalculator should be returned for maritime shipping source");
  }

  @Test
  void testVisitMooringMaritimeShippingSource() throws AeriusException {
    final Map<Substance, Double> calculatedEmissions = Map.of(Substance.NH3, 324.0);
    final MooringMaritimeShippingEmissionSource emissionSource = mock(MooringMaritimeShippingEmissionSource.class);
    when(maritimeShippingCalculator.calculateEmissions(emissionSource)).thenReturn(calculatedEmissions);

    final Map<Substance, Double> result = emissionsCalculator.visit(emissionSource, null);

    assertEquals(calculatedEmissions, result,
        "Calculated emission values from MaritimeShippingEmissionsCalculator should be returned for mooring maritime shipping source");
  }

  @Test
  void testVisitGeneric() throws AeriusException {
    final Map<Substance, Double> originalEmissions = Map.of(Substance.NH3, 324.0);
    final GenericEmissionSource emissionSource = mock(GenericEmissionSource.class);
    when(emissionSource.getEmissions()).thenReturn(originalEmissions);

    final Map<Substance, Double> result = emissionsCalculator.visit(emissionSource, null);

    assertEquals(originalEmissions, result, "Original emission values should be returned for generic source");
  }

}
