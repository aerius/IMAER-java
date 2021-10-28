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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.base.EmissionReduction;
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.source.RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.SRM1RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.SRM2RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.CustomVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.RoadSpeedType;
import nl.overheid.aerius.shared.domain.v2.source.road.RoadType;
import nl.overheid.aerius.shared.domain.v2.source.road.SpecificVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardVehicleMeasure;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.ValuesPerVehicleType;
import nl.overheid.aerius.shared.domain.v2.source.road.VehicleType;
import nl.overheid.aerius.shared.domain.v2.source.road.Vehicles;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.geometry.GeometryCalculator;

@ExtendWith(MockitoExtension.class)
class RoadEmissionsCalculatorTest {

  private static final RoadType TEST_ROAD_TYPE = RoadType.URBAN_ROAD;
  private static final RoadSpeedType TEST_ROAD_SPEED_TYPE = RoadSpeedType.URBAN_TRAFFIC_NORMAL;

  @Mock RoadEmissionFactorSupplier emissionFactorSupplier;
  @Mock GeometryCalculator geometryCalculator;

  RoadEmissionsCalculator emissionsCalculator;

  @BeforeEach
  void beforeEach() throws AeriusException {
    emissionsCalculator = new RoadEmissionsCalculator(emissionFactorSupplier, geometryCalculator);
  }

  @Test
  void testCalculateEmissionsSRM1() throws AeriusException {
    final SRM1RoadEmissionSource emissionSource = new SRM1RoadEmissionSource();
    emissionSource.setSectorId(TEST_ROAD_TYPE.getSectorId());
    emissionSource.setRoadSpeedType(TEST_ROAD_SPEED_TYPE);
    final Geometry geometry = mock(Geometry.class);
    when(geometryCalculator.determineMeasure(geometry)).thenReturn(321.5);

    // add 1 vehicles object of every type
    emissionSource.getSubSources().add(createCustom());
    emissionSource.getSubSources().add(createSpecific());
    emissionSource.getSubSources().add(createStandard());

    ensureSubSourceEmissionsEmpty(emissionSource);

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource, geometry);

    // Check total emissions
    // 321.5 * (1351850.865000 + 42000.00 + 192000.000 ) / (1000 * 1000)
    assertEquals(509.8510530975, results.get(Substance.NOX));
    // 321.5 * (0.0 + 20400.00 + 76200.000 ) / (1000 * 1000)
    assertEquals(31.0569, results.get(Substance.NH3));
    // Check emissions per subsource (should be set during calculation)
    assertEquals(434.6200530975, emissionSource.getSubSources().get(0).getEmissions().get(Substance.NOX));
    assertEquals(13.503, emissionSource.getSubSources().get(1).getEmissions().get(Substance.NOX));
    assertEquals(61.728, emissionSource.getSubSources().get(2).getEmissions().get(Substance.NOX));
    assertNull(emissionSource.getSubSources().get(0).getEmissions().get(Substance.NH3));
    assertEquals(6.5586, emissionSource.getSubSources().get(1).getEmissions().get(Substance.NH3));
    assertEquals(24.4983, emissionSource.getSubSources().get(2).getEmissions().get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsSRM2() throws AeriusException {
    final SRM2RoadEmissionSource emissionSource = new SRM2RoadEmissionSource();
    emissionSource.setSectorId(TEST_ROAD_TYPE.getSectorId());
    final Geometry geometry = mock(Geometry.class);
    when(geometryCalculator.determineMeasure(geometry)).thenReturn(321.5);

    // add 1 vehicles object of every type
    emissionSource.getSubSources().add(createCustom());
    emissionSource.getSubSources().add(createSpecific());
    emissionSource.getSubSources().add(createStandard());

    ensureSubSourceEmissionsEmpty(emissionSource);

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource, geometry);

    // Check total emissions
    // 321.5 * (1351850.865000 + 42000.00 + 384000.000 ) / (1000 * 1000)
    assertEquals(571.5790530975, results.get(Substance.NOX));
    // 321.5 * (0.0 + 20400.00 + 152400.000 ) / (1000 * 1000)
    assertEquals(55.5552, results.get(Substance.NH3));
    // Check emissions per subsource (should be set during calculation)
    assertEquals(434.6200530975, emissionSource.getSubSources().get(0).getEmissions().get(Substance.NOX));
    assertEquals(13.503, emissionSource.getSubSources().get(1).getEmissions().get(Substance.NOX));
    assertEquals(123.456, emissionSource.getSubSources().get(2).getEmissions().get(Substance.NOX));
    assertNull(emissionSource.getSubSources().get(0).getEmissions().get(Substance.NH3));
    assertEquals(6.5586, emissionSource.getSubSources().get(1).getEmissions().get(Substance.NH3));
    assertEquals(48.9966, emissionSource.getSubSources().get(2).getEmissions().get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsCustomVehicles() {
    final CustomVehicles vehicles = createCustom();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles);

    // 365 * 300 * 12.34567
    assertEquals(new BigDecimal("1351850.865000"), results.get(Substance.NOX));
  }

  @Test
  void testCalculateEmissionsSpecificVehicles() {
    final SpecificVehicles vehicles = createSpecific();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles, TEST_ROAD_TYPE);

    // 12 * 500 * 7.0
    assertEquals(new BigDecimal("42000.00"), results.get(Substance.NOX));
    // 12 * 500 * 3.4
    assertEquals(new BigDecimal("20400.00"), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsStandardVehiclesSrm1() {
    final StandardVehicles vehicles = createStandard();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles, TEST_ROAD_TYPE, TEST_ROAD_SPEED_TYPE);

    // 30000 * 6.0 * 0.8 + 30000 * 8.0 * 0.2
    assertEquals(new BigDecimal("192000.000"), results.get(Substance.NOX));
    // 30000 * 2.1 * 0.8 + 30000 * 4.3 * 0.2
    assertEquals(new BigDecimal("76200.000"), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsStandardVehiclesSrm1WithMeasures() {
    final StandardVehicles vehicles = createStandard();

    final StandardVehicleMeasure measure = new StandardVehicleMeasure();
    measure.setVehicleType(VehicleType.NORMAL_FREIGHT);
    measure.setRoadSpeedType(TEST_ROAD_SPEED_TYPE);

    final EmissionReduction emissionReduction = new EmissionReduction();
    emissionReduction.setSubstance(Substance.NOX);
    emissionReduction.setFactor(0.7);
    measure.addEmissionReduction(emissionReduction);

    vehicles.getMeasures().add(measure);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles, TEST_ROAD_TYPE, TEST_ROAD_SPEED_TYPE);

    // (30000 * 6.0 * 0.8 + 30000 * 8.0 * 0.2) * 0.7
    assertEquals(new BigDecimal("134400.0000"), results.get(Substance.NOX));
    // 30000 * 2.1 * 0.8 + 30000 * 4.3 * 0.2
    assertEquals(new BigDecimal("76200.0000"), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsStandardVehiclesSrm2() {
    final StandardVehicles vehicles = createStandard();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles, TEST_ROAD_TYPE, null);

    // 30000 * 12.0 * 0.8 + 30000 * 16.0 * 0.2
    assertEquals(new BigDecimal("384000.000"), results.get(Substance.NOX));
    // 30000 * 4.2 * 0.8 + 30000 * 8.6 * 0.2
    assertEquals(new BigDecimal("152400.000"), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsMultipleStandardVehiclesSrm2() {
    final StandardVehicles vehicles = createMultipleStandard();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles, TEST_ROAD_TYPE, null);

    // 30000 * 12.0 * 0.8 + 30000 * 16.0 * 0.2 + 2000 * 10.0 * 0.8 + 2000 * 14.0 * 0.2
    assertEquals(new BigDecimal("405600.000"), results.get(Substance.NOX));
    // 30000 * 4.2 * 0.8 + 30000 * 8.6 * 0.2 + 2000 * 5.3 * 0.8 + 2000 * 9.4 * 0.2
    assertEquals(new BigDecimal("164640.000"), results.get(Substance.NH3));
  }

  private CustomVehicles createCustom() {
    final CustomVehicles vehicles = new CustomVehicles();
    vehicles.getEmissionFactors().put(Substance.NOX, 12.34567);
    vehicles.setVehiclesPerTimeUnit(300);
    vehicles.setTimeUnit(TimeUnit.DAY);
    return vehicles;
  }

  private SpecificVehicles createSpecific() {
    final SpecificVehicles vehicles = new SpecificVehicles();
    vehicles.setVehiclesPerTimeUnit(500);
    vehicles.setTimeUnit(TimeUnit.MONTH);
    final String specificCode = "DEF";
    vehicles.setVehicleCode(specificCode);

    final Map<Substance, Double> emissionFactors = Map.of(Substance.NOX, 7.0, Substance.NH3, 3.4);
    when(emissionFactorSupplier.getRoadSpecificVehicleEmissionFactors(specificCode, TEST_ROAD_TYPE)).thenReturn(emissionFactors);

    return vehicles;
  }

  private StandardVehicles createStandard() {
    final StandardVehicles vehicles = new StandardVehicles();
    vehicles.setTimeUnit(TimeUnit.YEAR);
    final ValuesPerVehicleType valuesPerVehicleType = new ValuesPerVehicleType();
    valuesPerVehicleType.setStagnationFraction(0.2);
    valuesPerVehicleType.setVehiclesPerTimeUnit(30000);
    final VehicleType vehicleType = VehicleType.NORMAL_FREIGHT;
    vehicles.getValuesPerVehicleTypes().put(vehicleType, valuesPerVehicleType);
    final boolean strictEnforcement = false;
    vehicles.setStrictEnforcement(strictEnforcement);
    final int maximumSpeed = 40;
    vehicles.setMaximumSpeed(maximumSpeed);

    final Map<Substance, Double> emissionFactorsSrm1 = Map.of(Substance.NOX, 6.0, Substance.NH3, 2.1);
    lenient().when(emissionFactorSupplier
        .getRoadStandardVehicleEmissionFactors(vehicleType, TEST_ROAD_TYPE, TEST_ROAD_SPEED_TYPE, maximumSpeed, strictEnforcement))
        .thenReturn(emissionFactorsSrm1);
    final Map<Substance, Double> emissionFactorsSrm2 = Map.of(Substance.NOX, 12.0, Substance.NH3, 4.2);
    lenient().when(emissionFactorSupplier
        .getRoadStandardVehicleEmissionFactors(vehicleType, TEST_ROAD_TYPE, null, maximumSpeed, strictEnforcement))
        .thenReturn(emissionFactorsSrm2);

    final Map<Substance, Double> stagnatedEmissionFactorsSrm1 = Map.of(Substance.NOX, 8.0, Substance.NH3, 4.3);
    lenient().when(emissionFactorSupplier
        .getRoadStandardVehicleStagnatedEmissionFactors(vehicleType, TEST_ROAD_TYPE, TEST_ROAD_SPEED_TYPE, maximumSpeed, strictEnforcement))
        .thenReturn(stagnatedEmissionFactorsSrm1);
    final Map<Substance, Double> stagnatedEmissionFactorsSrm2 = Map.of(Substance.NOX, 16.0, Substance.NH3, 8.6);
    lenient().when(emissionFactorSupplier
        .getRoadStandardVehicleStagnatedEmissionFactors(vehicleType, TEST_ROAD_TYPE, null, maximumSpeed, strictEnforcement))
        .thenReturn(stagnatedEmissionFactorsSrm2);

    return vehicles;
  }

  private StandardVehicles createMultipleStandard() {
    final StandardVehicles vehicles = createStandard();
    final ValuesPerVehicleType valuesPerVehicleType = new ValuesPerVehicleType();
    valuesPerVehicleType.setStagnationFraction(0.2);
    valuesPerVehicleType.setVehiclesPerTimeUnit(2000);
    final VehicleType vehicleType = VehicleType.HEAVY_FREIGHT;
    vehicles.getValuesPerVehicleTypes().put(vehicleType, valuesPerVehicleType);
    final boolean strictEnforcement = vehicles.getStrictEnforcement();
    final int maximumSpeed = vehicles.getMaximumSpeed();

    final Map<Substance, Double> emissionFactorsSrm2 = Map.of(Substance.NOX, 10.0, Substance.NH3, 5.3);
    lenient().when(emissionFactorSupplier
        .getRoadStandardVehicleEmissionFactors(vehicleType, TEST_ROAD_TYPE, null, maximumSpeed, strictEnforcement))
        .thenReturn(emissionFactorsSrm2);

    final Map<Substance, Double> stagnatedEmissionFactorsSrm2 = Map.of(Substance.NOX, 14.0, Substance.NH3, 9.4);
    lenient().when(emissionFactorSupplier
        .getRoadStandardVehicleStagnatedEmissionFactors(vehicleType, TEST_ROAD_TYPE, null, maximumSpeed, strictEnforcement))
        .thenReturn(stagnatedEmissionFactorsSrm2);

    return vehicles;
  }

  private void ensureSubSourceEmissionsEmpty(final RoadEmissionSource emissionSource) {
    // Ensure emissions per subsource are unknown at start
    for (final Vehicles vehicles : emissionSource.getSubSources()) {
      assertTrue(vehicles.getEmissions().isEmpty());
    }
  }

}
