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
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.source.InlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MooringInlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.CustomInlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.CustomInlandShippingEmissionProperties;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.CustomMooringInlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.InlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.InlandWaterway;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.MooringInlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.StandardInlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.StandardMooringInlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.WaterwayDirection;
import nl.overheid.aerius.shared.emissions.shipping.InlandShippingRouteEmissionPoint;
import nl.overheid.aerius.shared.emissions.shipping.ShippingLaden;
import nl.overheid.aerius.shared.exception.AeriusException;

@ExtendWith(MockitoExtension.class)
class InlandShippingEmissionsCalculatorTest {

  private static final String WATERWAY_CODE = "CEMTWHAT";
  private static final WaterwayDirection WATERWAY_DIRECTION = WaterwayDirection.UPSTREAM;

  @Mock InlandShippingEmissionFactorSupplier emissionFactorSupplier;

  InlandShippingEmissionsCalculator emissionsCalculator;

  @BeforeEach
  void beforeEach() throws AeriusException {
    emissionsCalculator = new InlandShippingEmissionsCalculator(emissionFactorSupplier);
  }

  @Test
  void testCalculateEmissionsMooringInlandShipping() throws AeriusException {
    final MooringInlandShippingEmissionSource emissionSource = new MooringInlandShippingEmissionSource();

    emissionSource.getSubSources().add(createExampleMooringShip1());

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource);

    // Mooring: 50 * 5 * 200 * (1 - 0.4) = 30000
    assertEquals(30000.0, results.get(Substance.NOX));
  }

  @Test
  void testCalculateEmissionsMooringInlandShippingCustom() throws AeriusException {
    final MooringInlandShippingEmissionSource emissionSource = new MooringInlandShippingEmissionSource();

    emissionSource.getSubSources().add(createExampleMooringShipCustom());

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource);

    // Mooring NH3: 50 * 5 * 100 * .34 = 8500
    assertEquals(8500.0, results.get(Substance.NH3));
    // Mooring NOX: 50 * 5 * 200 * .66 + 50 * 5 * 100 * .34 = 50000
    assertEquals(41500.0, results.get(Substance.NOX));
  }

  @Test
  void testCalculateEmissionsInlandShipping() throws AeriusException {
    final InlandShippingEmissionSource emissionSource = new InlandShippingEmissionSource();
    final Geometry geometry = mock(Geometry.class);
    final InlandWaterway waterway = mock(InlandWaterway.class);
    when(waterway.getWaterwayCode()).thenReturn(WATERWAY_CODE);
    when(waterway.getDirection()).thenReturn(WATERWAY_DIRECTION);
    emissionSource.setWaterway(waterway);
    final InlandShippingRouteEmissionPoint point1 = createRoutePoint(waterway, 1000.0, 1.6);
    final InlandShippingRouteEmissionPoint point2 = createRoutePoint(waterway, 3000.0, 1.0);
    final List<InlandShippingRouteEmissionPoint> points = List.of(point1, point2);
    when(emissionFactorSupplier.determineInlandRoutePoints(geometry, Optional.of(waterway))).thenReturn(points);

    emissionSource.getSubSources().add(createExampleShip1());

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource, geometry);

    // 1st point: 1000 * 1.6 * (40 * (50 * 0.3) + 30 * (50 * 0.7)) = 2640000
    // 2nd point: 3000 * 1.0 * (40 * (50 * 0.3) + 30 * (50 * 0.7)) = 4950000
    assertEquals(7590000.0, results.get(Substance.NOX));
  }

  @Test
  void testCalculateEmissionsInlandShippingTwoShips() throws AeriusException {
    final InlandShippingEmissionSource emissionSource = new InlandShippingEmissionSource();
    final Geometry geometry = mock(Geometry.class);
    final InlandWaterway waterway = mock(InlandWaterway.class);
    when(waterway.getWaterwayCode()).thenReturn(WATERWAY_CODE);
    when(waterway.getDirection()).thenReturn(WATERWAY_DIRECTION);
    emissionSource.setWaterway(waterway);
    final InlandShippingRouteEmissionPoint point1 = createRoutePoint(waterway, 1000.0, 1.6);
    final InlandShippingRouteEmissionPoint point2 = createRoutePoint(waterway, 3000.0, 1.0);
    final List<InlandShippingRouteEmissionPoint> points = List.of(point1, point2);
    when(emissionFactorSupplier.determineInlandRoutePoints(geometry, Optional.of(waterway))).thenReturn(points);

    emissionSource.getSubSources().add(createExampleShip1());
    emissionSource.getSubSources().add(createExampleShip2());

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource, geometry);

    // 1st point 2nd ship: 1000 * 1.6 * (25 * (40 * 0.4) + 20 * (40 * 0.6) + 20 * (90 * 0.8) + 15 * (90 * 0.2)) = 4144000
    // 2nd point 2nd ship: 3000 * 1.0 * (25 * (40 * 0.4) + 20 * (40 * 0.6) + 20 * (90 * 0.8) + 15 * (90 * 0.2)) = 7770000
    // Add emissions of first test: 7590000
    assertEquals(19504000.0, results.get(Substance.NOX));
    // 1st point 2nd ship: 1000 * 1.6 * (10 * (40 * 0.4) + 7 * (40 * 0.6) + 9 * (90 * 0.8) + 6 * (90 * 0.2)) = 1734400
    // 2nd point 2nd ship: 3000 * 1.0 * (10 * (40 * 0.4) + 7 * (40 * 0.6) + 9 * (90 * 0.8) + 6 * (90 * 0.2)) = 3252000
    assertEquals(4986400.0, results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsInlandShippingCustom() throws AeriusException {
    final InlandShippingEmissionSource emissionSource = new InlandShippingEmissionSource();
    final Geometry geometry = mock(Geometry.class);
    final InlandWaterway waterway = mock(InlandWaterway.class);
    emissionSource.setWaterway(waterway);
    final InlandShippingRouteEmissionPoint point1 = createRoutePoint(waterway, 1000.0, 1.6);
    final InlandShippingRouteEmissionPoint point2 = createRoutePoint(waterway, 3000.0, 1.0);
    final List<InlandShippingRouteEmissionPoint> points = List.of(point1, point2);
    when(emissionFactorSupplier.determineInlandRoutePoints(geometry, Optional.of(waterway))).thenReturn(points);

    emissionSource.getSubSources().add(createExampleShipCustom());

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource, geometry);

    // 1st point NH3: 1000 * 1.6 * (100 * (50 * 0.3) = 2400000
    // 2nd point NH3: 3000 * 1.0 * (100 * (50 * 0.3) = 4500000
    assertEquals(6900000.0, results.get(Substance.NH3));
    // 1st point NOx: 1000 * 1.6 * (100 * (50 * 0.3) + 200 * (50 * 0.7)) = 13600000
    // 2nd point NOx: 3000 * 1.0 * (100 * (50 * 0.3) + 200 * (50 * 0.7)) = 25500000
    assertEquals(39100000.0, results.get(Substance.NOX));
  }

  private InlandShippingRouteEmissionPoint createRoutePoint(final InlandWaterway waterway, final double segmentLength, final double lockFactor) {
    final InlandShippingRouteEmissionPoint point = mock(InlandShippingRouteEmissionPoint.class);
    when(point.getWaterway()).thenReturn(waterway);
    when(point.getSegmentLength()).thenReturn(segmentLength);
    when(point.getLockFactor()).thenReturn(lockFactor);
    return point;
  }

  private MooringInlandShipping createExampleMooringShip1() throws AeriusException {
    final StandardMooringInlandShipping inlandShipping = new StandardMooringInlandShipping();
    final String shipCode = "LMN";
    inlandShipping.setShipCode(shipCode);
    inlandShipping.setAverageResidenceTime(5);
    inlandShipping.setTimeUnit(TimeUnit.YEAR);
    inlandShipping.setShipsPerTimeUnit(50);
    inlandShipping.setPercentageLaden(34);
    inlandShipping.setShorePowerFactor(0.4);

    final Map<Substance, Double> emissionFactorsDocked = Map.of(Substance.NOX, 200.0);
    when(emissionFactorSupplier.getInlandShippingDockedEmissionFactors(shipCode)).thenReturn(emissionFactorsDocked);

    return inlandShipping;
  }

  private MooringInlandShipping createExampleMooringShipCustom() throws AeriusException {
    final CustomMooringInlandShipping inlandShipping = new CustomMooringInlandShipping();
    inlandShipping.setAverageResidenceTime(5);
    inlandShipping.setTimeUnit(TimeUnit.YEAR);
    inlandShipping.setShipsPerTimeUnit(50);
    inlandShipping.setPercentageLaden(34);
    final CustomInlandShippingEmissionProperties emissionProperties = new CustomInlandShippingEmissionProperties();
    emissionProperties.getEmissionFactorsEmpty().put(Substance.NOX, 200.0);
    emissionProperties.getEmissionFactorsLaden().put(Substance.NOX, 100.0);
    emissionProperties.getEmissionFactorsLaden().put(Substance.NH3, 100.0);
    inlandShipping.setEmissionProperties(emissionProperties);

    return inlandShipping;
  }

  private InlandShipping createExampleShip1() throws AeriusException {
    final StandardInlandShipping inlandShipping = new StandardInlandShipping();
    final String shipCode = "IJK";
    inlandShipping.setShipCode(shipCode);
    inlandShipping.setTimeUnitAtoB(TimeUnit.YEAR);
    inlandShipping.setMovementsAtoBPerTimeUnit(50);
    inlandShipping.setPercentageLadenAtoB(30);

    final Map<Substance, Double> emissionFactorsLaden = Map.of(Substance.NOX, 40.0);
    when(emissionFactorSupplier.getInlandShippingRouteEmissionFactors(WATERWAY_CODE, WATERWAY_DIRECTION, ShippingLaden.LADEN, shipCode))
        .thenReturn(emissionFactorsLaden);
    final Map<Substance, Double> emissionFactorsUnladen = Map.of(Substance.NOX, 30.0);
    when(emissionFactorSupplier.getInlandShippingRouteEmissionFactors(WATERWAY_CODE, WATERWAY_DIRECTION, ShippingLaden.UNLADEN, shipCode))
        .thenReturn(emissionFactorsUnladen);

    return inlandShipping;
  }

  private InlandShipping createExampleShip2() throws AeriusException {
    final StandardInlandShipping inlandShipping = new StandardInlandShipping();
    final String shipCode = "LMP";
    inlandShipping.setShipCode(shipCode);
    inlandShipping.setTimeUnitAtoB(TimeUnit.YEAR);
    inlandShipping.setMovementsAtoBPerTimeUnit(40);
    inlandShipping.setPercentageLadenAtoB(40);
    inlandShipping.setTimeUnitBtoA(TimeUnit.YEAR);
    inlandShipping.setMovementsBtoAPerTimeUnit(90);
    inlandShipping.setPercentageLadenBtoA(80);

    final Map<Substance, Double> emissionFactorsLaden = Map.of(Substance.NOX, 25.0, Substance.NH3, 10.0);
    when(emissionFactorSupplier.getInlandShippingRouteEmissionFactors(WATERWAY_CODE, WATERWAY_DIRECTION, ShippingLaden.LADEN, shipCode))
        .thenReturn(emissionFactorsLaden);
    final Map<Substance, Double> emissionFactorsUnladen = Map.of(Substance.NOX, 20.0, Substance.NH3, 7.0);
    when(emissionFactorSupplier.getInlandShippingRouteEmissionFactors(WATERWAY_CODE, WATERWAY_DIRECTION, ShippingLaden.UNLADEN, shipCode))
        .thenReturn(emissionFactorsUnladen);

    final Map<Substance, Double> emissionFactorsLadenReverse = Map.of(Substance.NOX, 20.0, Substance.NH3, 9.0);
    when(emissionFactorSupplier.getInlandShippingRouteEmissionFactors(WATERWAY_CODE, WATERWAY_DIRECTION.getOpposite(), ShippingLaden.LADEN, shipCode))
        .thenReturn(emissionFactorsLadenReverse);
    final Map<Substance, Double> emissionFactorsUnladenReverse = Map.of(Substance.NOX, 15.0, Substance.NH3, 6.0);
    when(emissionFactorSupplier.getInlandShippingRouteEmissionFactors(WATERWAY_CODE, WATERWAY_DIRECTION.getOpposite(), ShippingLaden.UNLADEN,
        shipCode)).thenReturn(emissionFactorsUnladenReverse);

    return inlandShipping;
  }

  private InlandShipping createExampleShipCustom() throws AeriusException {
    final CustomInlandShipping inlandShipping = new CustomInlandShipping();
    inlandShipping.setTimeUnitAtoB(TimeUnit.YEAR);
    inlandShipping.setMovementsAtoBPerTimeUnit(50);
    inlandShipping.setPercentageLadenAtoB(30);
    final CustomInlandShippingEmissionProperties emissionProperties = new CustomInlandShippingEmissionProperties();
    emissionProperties.getEmissionFactorsEmpty().put(Substance.NOX, 200.0);
    emissionProperties.getEmissionFactorsLaden().put(Substance.NOX, 100.0);
    emissionProperties.getEmissionFactorsLaden().put(Substance.NH3, 100.0);
    inlandShipping.setEmissionPropertiesAtoB(emissionProperties);

    return inlandShipping;
  }

}
