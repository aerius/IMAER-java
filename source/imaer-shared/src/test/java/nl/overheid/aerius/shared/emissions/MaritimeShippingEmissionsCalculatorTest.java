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

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.source.InlandMaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MooringMaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.CustomMaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.CustomMaritimeShippingEmissionProperties;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.CustomMooringMaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.MaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.MooringMaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.ShippingMovementType;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.StandardMaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.StandardMooringMaritimeShipping;
import nl.overheid.aerius.shared.emissions.shipping.MaritimeShippingRouteEmissionPoint;
import nl.overheid.aerius.shared.exception.AeriusException;

@ExtendWith(MockitoExtension.class)
class MaritimeShippingEmissionsCalculatorTest {

  @Mock MaritimeShippingEmissionFactorSupplier emissionFactorSupplier;

  MaritimeShippingEmissionsCalculator emissionsCalculator;

  @BeforeEach
  void beforeEach() throws AeriusException {
    emissionsCalculator = new MaritimeShippingEmissionsCalculator(emissionFactorSupplier);
  }

  @Test
  void testCalculateEmissionsMooringMaritimeShipping() throws AeriusException {
    final MooringMaritimeShippingEmissionSource emissionSource = new MooringMaritimeShippingEmissionSource();

    emissionSource.getSubSources().add(createExampleMooringShip());

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource);

    // Mooring: 40 * 5 * 200 * (1 - 0.3) = 28000
    assertEquals(28000.0, results.get(Substance.NOX));
  }

  @Test
  void testCalculateEmissionsMooringMaritimeShippingCustom() throws AeriusException {
    final MooringMaritimeShippingEmissionSource emissionSource = new MooringMaritimeShippingEmissionSource();

    emissionSource.getSubSources().add(createExampleMooringShipCustom());

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource);

    // Mooring: 40 * 5 * 300 = 60000
    assertEquals(60000.0, results.get(Substance.NOX));
  }

  @Test
  void testCalculateEmissionsMaritimeShipping() throws AeriusException {
    final MaritimeShippingEmissionSource emissionSource = new InlandMaritimeShippingEmissionSource();
    final Geometry geometry = mock(Geometry.class);
    // Maneuver factor can apply to standalone routes as well
    final MaritimeShippingRouteEmissionPoint point1 = createRoutePoint(1000.0, 1.6);
    final MaritimeShippingRouteEmissionPoint point2 = createRoutePoint(3000.0, 1.0);
    final List<MaritimeShippingRouteEmissionPoint> points = List.of(point1, point2);
    when(emissionFactorSupplier.determineMaritimeRoutePoints(geometry)).thenReturn(points);

    emissionSource.getSubSources().add(createExampleShip1());

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource, geometry);

    // 1st point: 1000 * 1.6 * 40 * 50 = 3200000
    // 2nd point: 3000 * 1.0 * 40 * 50 = 6000000
    assertEquals(9200000.0, results.get(Substance.NOX));
  }

  @Test
  void testCalculateEmissionsInlandShippingTwoShips() throws AeriusException {
    final MaritimeShippingEmissionSource emissionSource = new InlandMaritimeShippingEmissionSource();
    final Geometry geometry = mock(Geometry.class);
    final MaritimeShippingRouteEmissionPoint point1 = createRoutePoint(1000.0, 1.6);
    final MaritimeShippingRouteEmissionPoint point2 = createRoutePoint(3000.0, 1.0);
    final List<MaritimeShippingRouteEmissionPoint> points = List.of(point1, point2);
    when(emissionFactorSupplier.determineMaritimeRoutePoints(geometry)).thenReturn(points);

    emissionSource.getSubSources().add(createExampleShip1());
    emissionSource.getSubSources().add(createExampleShip2());

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource, geometry);

    // 1st point 2nd ship: 1000 * 1.6 * 25 * 40 = 1600000
    // 2nd point 2nd ship: 3000 * 1.0 * 25 * 40 = 3000000
    // Add emissions of first test: 9200000
    assertEquals(13800000.0, results.get(Substance.NOX));
    // 1st point 2nd ship: 1000 * 1.6 * 10 * 40 = 640000
    // 2nd point 2nd ship: 3000 * 1.0 * 10 * 40 = 1200000
    assertEquals(1840000, results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsMaritimeShippingCustom() throws AeriusException {
    final MaritimeShippingEmissionSource emissionSource = new InlandMaritimeShippingEmissionSource();
    final Geometry geometry = mock(Geometry.class);
    final MaritimeShippingRouteEmissionPoint point1 = createRoutePoint(1000.0, 1.6);
    final MaritimeShippingRouteEmissionPoint point2 = createRoutePoint(3000.0, 1.0);
    final List<MaritimeShippingRouteEmissionPoint> points = List.of(point1, point2);
    when(emissionFactorSupplier.determineMaritimeRoutePoints(geometry)).thenReturn(points);

    emissionSource.getSubSources().add(createExampleShipCustom());

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource, geometry);

    // 1st point: 1000 * 1.6 * 35 * 50 = 2800000
    // 2nd point: 3000 * 1.0 * 35 * 50 = 5250000
    assertEquals(8050000.0, results.get(Substance.NOX));
  }

  @Test
  void testCalculateEmissionsMaritimeShippingMooringRoute() throws AeriusException {
    final MaritimeShippingEmissionSource emissionSource = new InlandMaritimeShippingEmissionSource();
    final Geometry geometry = mock(Geometry.class);
    final MaritimeShippingRouteEmissionPoint point1 = createRoutePoint(1000.0, 1.6);
    final MaritimeShippingRouteEmissionPoint point2 = createRoutePoint(3000.0, 1.0);
    final List<MaritimeShippingRouteEmissionPoint> points = List.of(point1, point2);
    final StandardMaritimeShipping ship = createExampleShip1();
    // Should use the ship code based maritime mooring inland route points method in this case
    when(emissionFactorSupplier.determineMaritimeMooringInlandRoutePoints(geometry, ship.getShipCode(), true, false)).thenReturn(points);

    emissionSource.getSubSources().add(ship);
    emissionSource.setMooringAId("SomeId");

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource, geometry);

    // 1st point: 1000 * 1.6 * 40 * 50 = 3200000
    // 2nd point: 3000 * 1.0 * 40 * 50 = 6000000
    assertEquals(9200000.0, results.get(Substance.NOX));
  }

  @Test
  void testCalculateEmissionsMaritimeShippingCustomMooringRoute() throws AeriusException {
    final MaritimeShippingEmissionSource emissionSource = new InlandMaritimeShippingEmissionSource();
    final Geometry geometry = mock(Geometry.class);
    final MaritimeShippingRouteEmissionPoint point1 = createRoutePoint(1000.0, 1.6);
    final MaritimeShippingRouteEmissionPoint point2 = createRoutePoint(3000.0, 1.0);
    final List<MaritimeShippingRouteEmissionPoint> points = List.of(point1, point2);
    final CustomMaritimeShipping customShip = createExampleShipCustom();
    // Should use the gross tonnage based maritime mooring inland route points method in this case
    when(emissionFactorSupplier.determineMaritimeMooringInlandRoutePointsForGrossTonnage(geometry, customShip.getGrossTonnage(), true, false))
        .thenReturn(points);

    emissionSource.getSubSources().add(customShip);
    emissionSource.setMooringAId("SomeId");

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource, geometry);

    // 1st point: 1000 * 1.6 * 35 * 50 = 2800000
    // 2nd point: 3000 * 1.0 * 35 * 50 = 5250000
    assertEquals(8050000.0, results.get(Substance.NOX));
  }

  private MaritimeShippingRouteEmissionPoint createRoutePoint(final double segmentLength, final double maneuverFactor) {
    final MaritimeShippingRouteEmissionPoint point = mock(MaritimeShippingRouteEmissionPoint.class);
    when(point.getSegmentLength()).thenReturn(segmentLength);
    when(point.getManeuverFactor()).thenReturn(maneuverFactor);
    return point;
  }

  private MooringMaritimeShipping createExampleMooringShip() throws AeriusException {
    final StandardMooringMaritimeShipping maritimeShipping = new StandardMooringMaritimeShipping();
    final String shipCode = "LMN";
    maritimeShipping.setShipCode(shipCode);
    maritimeShipping.setAverageResidenceTime(5);
    maritimeShipping.setTimeUnit(TimeUnit.YEAR);
    maritimeShipping.setShipsPerTimeUnit(40);
    maritimeShipping.setShorePowerFactor(0.3);

    final Map<Substance, Double> emissionFactorsDocked = Map.of(Substance.NOX, 200.0);
    when(emissionFactorSupplier.getMaritimeShippingDockedEmissionFactors(shipCode)).thenReturn(emissionFactorsDocked);

    return maritimeShipping;
  }

  private MooringMaritimeShipping createExampleMooringShipCustom() throws AeriusException {
    final CustomMooringMaritimeShipping maritimeShipping = new CustomMooringMaritimeShipping();
    maritimeShipping.setAverageResidenceTime(5);
    maritimeShipping.setTimeUnit(TimeUnit.YEAR);
    maritimeShipping.setShipsPerTimeUnit(40);
    final CustomMaritimeShippingEmissionProperties emissionProperties = new CustomMaritimeShippingEmissionProperties();
    emissionProperties.getEmissionFactors().put(Substance.NOX, 300.0);
    maritimeShipping.setEmissionProperties(emissionProperties);

    return maritimeShipping;
  }

  private StandardMaritimeShipping createExampleShip1() throws AeriusException {
    final StandardMaritimeShipping maritimeShipping = new StandardMaritimeShipping();
    final String shipCode = "IJK";
    maritimeShipping.setShipCode(shipCode);
    maritimeShipping.setTimeUnit(TimeUnit.YEAR);
    maritimeShipping.setMovementsPerTimeUnit(50);

    final Map<Substance, Double> emissionFactors = Map.of(Substance.NOX, 40.0);
    when(emissionFactorSupplier.getMaritimeShippingRouteEmissionFactors(ShippingMovementType.INLAND, shipCode))
        .thenReturn(emissionFactors);

    return maritimeShipping;
  }

  private MaritimeShipping createExampleShip2() throws AeriusException {
    final StandardMaritimeShipping maritimeShipping = new StandardMaritimeShipping();
    final String shipCode = "LMP";
    maritimeShipping.setShipCode(shipCode);
    maritimeShipping.setTimeUnit(TimeUnit.YEAR);
    maritimeShipping.setMovementsPerTimeUnit(40);

    final Map<Substance, Double> emissionFactors = Map.of(Substance.NOX, 25.0, Substance.NH3, 10.0);
    when(emissionFactorSupplier.getMaritimeShippingRouteEmissionFactors(ShippingMovementType.INLAND, shipCode))
        .thenReturn(emissionFactors);

    return maritimeShipping;
  }

  private CustomMaritimeShipping createExampleShipCustom() throws AeriusException {
    final CustomMaritimeShipping maritimeShipping = new CustomMaritimeShipping();
    maritimeShipping.setTimeUnit(TimeUnit.YEAR);
    maritimeShipping.setMovementsPerTimeUnit(50);

    final CustomMaritimeShippingEmissionProperties emissionProperties = new CustomMaritimeShippingEmissionProperties();
    emissionProperties.getEmissionFactors().put(Substance.NOX, 35.0);
    maritimeShipping.setEmissionProperties(emissionProperties);
    maritimeShipping.setGrossTonnage(30000);

    return maritimeShipping;
  }

}
