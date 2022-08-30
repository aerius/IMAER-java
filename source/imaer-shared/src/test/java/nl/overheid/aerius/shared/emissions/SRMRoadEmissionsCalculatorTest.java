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
import nl.aerius.shared.domain.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.source.RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.SRM1RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.SRM2RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.CustomVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.RoadStandardEmissionFactorsKey;
import nl.overheid.aerius.shared.domain.v2.source.road.SpecificVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardVehicleMeasure;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.ValuesPerVehicleType;
import nl.overheid.aerius.shared.domain.v2.source.road.Vehicles;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.geometry.GeometryCalculator;

@ExtendWith(MockitoExtension.class)
class SRMRoadEmissionsCalculatorTest {

  private static final String TEST_ROAD_AREA = "NL";
  private static final String TEST_ROAD_TYPE_SRM2 = "URBAN_ROAD";
  private static final String TEST_ROAD_TYPE_SRM1 = "NON_URBAN_ROAD_STAGNATING";

  @Mock RoadEmissionFactorSupplier emissionFactorSupplier;
  @Mock GeometryCalculator geometryCalculator;

  SRMRoadEmissionsCalculator emissionsCalculator;

  @BeforeEach
  void beforeEach() throws AeriusException {
    emissionsCalculator = new SRMRoadEmissionsCalculator(emissionFactorSupplier, geometryCalculator);
  }

  @Test
  void testCalculateEmissionsSRM1() throws AeriusException {
    final SRM1RoadEmissionSource emissionSource = new SRM1RoadEmissionSource();
    emissionSource.setRoadAreaCode(TEST_ROAD_AREA);
    emissionSource.setRoadTypeCode(TEST_ROAD_TYPE_SRM1);
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
    emissionSource.setRoadAreaCode(TEST_ROAD_AREA);
    emissionSource.setRoadTypeCode(TEST_ROAD_TYPE_SRM2);
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
    assertEquals(571.5790530975, results.get(Substance.NOX), "Total NOx emissions");
    // 321.5 * (0.0 + 20400.00 + 152400.000 ) / (1000 * 1000)
    assertEquals(55.5552, results.get(Substance.NH3), "Total NH3 emissions");
    // Check emissions per subsource (should be set during calculation)
    assertEquals(434.6200530975, emissionSource.getSubSources().get(0).getEmissions().get(Substance.NOX), "NOx emissions first subsource");
    assertEquals(13.503, emissionSource.getSubSources().get(1).getEmissions().get(Substance.NOX), "NOx emissions second subsource");
    assertEquals(123.456, emissionSource.getSubSources().get(2).getEmissions().get(Substance.NOX), "NOx emissions third subsource");
    assertNull(emissionSource.getSubSources().get(0).getEmissions().get(Substance.NH3), "NH3 emissions first subsource");
    assertEquals(6.5586, emissionSource.getSubSources().get(1).getEmissions().get(Substance.NH3), "NH3 emissions second subsource");
    assertEquals(48.9966, emissionSource.getSubSources().get(2).getEmissions().get(Substance.NH3), "NH3 emissions third subsource");
  }

  @Test
  void testCalculateEmissionsCustomVehicles() {
    final CustomVehicles vehicles = createCustom();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles);

    // 365 * 300 * 12.34567
    assertEquals(new BigDecimal("1351850.865000"), results.get(Substance.NOX), "NOx emissions custom vehicle");
  }

  @Test
  void testCalculateEmissionsSpecificVehicles() {
    final SpecificVehicles vehicles = createSpecific();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles, TEST_ROAD_TYPE_SRM2);

    // 12 * 500 * 7.0
    assertEquals(new BigDecimal("42000.00"), results.get(Substance.NOX), "NOx emissions specific vehicle");
    // 12 * 500 * 3.4
    assertEquals(new BigDecimal("20400.00"), results.get(Substance.NH3), "NH3 emissions specific vehicle");
  }

  @Test
  void testCalculateEmissionsStandardVehiclesSrm1() {
    final StandardVehicles vehicles = createStandard();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles, TEST_ROAD_AREA, TEST_ROAD_TYPE_SRM1);

    // 30000 * 6.0 * 0.8 + 30000 * 8.0 * 0.2
    assertEquals(new BigDecimal("192000.000"), results.get(Substance.NOX), "NOx emissions standard vehicle");
    // 30000 * 2.1 * 0.8 + 30000 * 4.3 * 0.2
    assertEquals(new BigDecimal("76200.000"), results.get(Substance.NH3), "NH3 emissions standard vehicle");
  }

  @Test
  void testCalculateEmissionsStandardVehiclesSrm1WithMeasures() {
    final StandardVehicles vehicles = createStandard();

    final StandardVehicleMeasure measure = new StandardVehicleMeasure();
    measure.setVehicleTypeCode("NORMAL_FREIGHT");
    measure.setRoadTypeCode(TEST_ROAD_TYPE_SRM1);

    final EmissionReduction emissionReduction = new EmissionReduction();
    emissionReduction.setSubstance(Substance.NOX);
    emissionReduction.setFactor(0.7);
    measure.addEmissionReduction(emissionReduction);

    vehicles.getMeasures().add(measure);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles, TEST_ROAD_AREA, TEST_ROAD_TYPE_SRM1);

    // (30000 * 6.0 * 0.8 + 30000 * 8.0 * 0.2) * 0.7
    assertEquals(new BigDecimal("134400.0000"), results.get(Substance.NOX), "NOx emissions standard vehicle with measure");
    // 30000 * 2.1 * 0.8 + 30000 * 4.3 * 0.2
    assertEquals(new BigDecimal("76200.0000"), results.get(Substance.NH3), "NH3 emissions standard vehicle with measure");
  }

  @Test
  void testCalculateEmissionsStandardVehiclesSrm2() {
    final StandardVehicles vehicles = createStandard();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles, TEST_ROAD_AREA, TEST_ROAD_TYPE_SRM2);

    // 30000 * 12.0 * 0.8 + 30000 * 16.0 * 0.2
    assertEquals(new BigDecimal("384000.000"), results.get(Substance.NOX), "NOx emissions standard vehicle");
    // 30000 * 4.2 * 0.8 + 30000 * 8.6 * 0.2
    assertEquals(new BigDecimal("152400.000"), results.get(Substance.NH3), "NH3 emissions standard vehicle");
  }

  @Test
  void testCalculateEmissionsMultipleStandardVehiclesSrm2() {
    final StandardVehicles vehicles = createMultipleStandard();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles, TEST_ROAD_AREA, TEST_ROAD_TYPE_SRM2);

    // 30000 * 12.0 * 0.8 + 30000 * 16.0 * 0.2 + 2000 * 10.0 * 0.8 + 2000 * 14.0 * 0.2
    assertEquals(new BigDecimal("405600.000"), results.get(Substance.NOX), "NOx emissions standard vehicles");
    // 30000 * 4.2 * 0.8 + 30000 * 8.6 * 0.2 + 2000 * 5.3 * 0.8 + 2000 * 9.4 * 0.2
    assertEquals(new BigDecimal("164640.000"), results.get(Substance.NH3), "NH3 emissions standard vehicles");
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
    lenient().when(emissionFactorSupplier.getRoadSpecificVehicleEmissionFactors(specificCode, TEST_ROAD_TYPE_SRM2)).thenReturn(emissionFactors);
    lenient().when(emissionFactorSupplier.getRoadSpecificVehicleEmissionFactors(specificCode, TEST_ROAD_TYPE_SRM1)).thenReturn(emissionFactors);

    return vehicles;
  }

  private StandardVehicles createStandard() {
    final StandardVehicles vehicles = new StandardVehicles();
    vehicles.setTimeUnit(TimeUnit.YEAR);
    final ValuesPerVehicleType valuesPerVehicleType = new ValuesPerVehicleType();
    valuesPerVehicleType.setStagnationFraction(0.2);
    valuesPerVehicleType.setVehiclesPerTimeUnit(30000);
    final String vehicleType = "NORMAL_FREIGHT";
    vehicles.getValuesPerVehicleTypes().put(vehicleType, valuesPerVehicleType);
    final boolean strictEnforcement = false;
    vehicles.setStrictEnforcement(strictEnforcement);
    final int maximumSpeed = 40;
    vehicles.setMaximumSpeed(maximumSpeed);

    final Map<Substance, Double> emissionFactorsSrm1 = Map.of(Substance.NOX, 6.0, Substance.NH3, 2.1);
    lenient().when(emissionFactorSupplier
        .getRoadStandardVehicleEmissionFactors(
            new RoadStandardEmissionFactorsKey(TEST_ROAD_AREA, TEST_ROAD_TYPE_SRM1, vehicleType, maximumSpeed, strictEnforcement, null)))
        .thenReturn(emissionFactorsSrm1);
    final Map<Substance, Double> emissionFactorsSrm2 = Map.of(Substance.NOX, 12.0, Substance.NH3, 4.2);
    lenient().when(emissionFactorSupplier
        .getRoadStandardVehicleEmissionFactors(
            new RoadStandardEmissionFactorsKey(TEST_ROAD_AREA, TEST_ROAD_TYPE_SRM2, vehicleType, maximumSpeed, strictEnforcement, null)))
        .thenReturn(emissionFactorsSrm2);

    final Map<Substance, Double> stagnatedEmissionFactorsSrm1 = Map.of(Substance.NOX, 8.0, Substance.NH3, 4.3);
    lenient().when(emissionFactorSupplier
        .getRoadStandardVehicleStagnatedEmissionFactors(
            new RoadStandardEmissionFactorsKey(TEST_ROAD_AREA, TEST_ROAD_TYPE_SRM1, vehicleType, maximumSpeed, strictEnforcement, null)))
        .thenReturn(stagnatedEmissionFactorsSrm1);
    final Map<Substance, Double> stagnatedEmissionFactorsSrm2 = Map.of(Substance.NOX, 16.0, Substance.NH3, 8.6);
    lenient().when(emissionFactorSupplier
        .getRoadStandardVehicleStagnatedEmissionFactors(
            new RoadStandardEmissionFactorsKey(TEST_ROAD_AREA, TEST_ROAD_TYPE_SRM2, vehicleType, maximumSpeed, strictEnforcement, null)))
        .thenReturn(stagnatedEmissionFactorsSrm2);

    return vehicles;
  }

  private StandardVehicles createMultipleStandard() {
    final StandardVehicles vehicles = createStandard();
    final ValuesPerVehicleType valuesPerVehicleType = new ValuesPerVehicleType();
    valuesPerVehicleType.setStagnationFraction(0.2);
    valuesPerVehicleType.setVehiclesPerTimeUnit(2000);
    final String vehicleType = "HEAVY_FREIGHT";
    vehicles.getValuesPerVehicleTypes().put(vehicleType, valuesPerVehicleType);
    final boolean strictEnforcement = vehicles.getStrictEnforcement();
    final int maximumSpeed = vehicles.getMaximumSpeed();

    final Map<Substance, Double> emissionFactorsSrm2 = Map.of(Substance.NOX, 10.0, Substance.NH3, 5.3);
    lenient().when(emissionFactorSupplier
        .getRoadStandardVehicleEmissionFactors(
            new RoadStandardEmissionFactorsKey(TEST_ROAD_AREA, TEST_ROAD_TYPE_SRM2, vehicleType, maximumSpeed, strictEnforcement, null)))
        .thenReturn(emissionFactorsSrm2);

    final Map<Substance, Double> stagnatedEmissionFactorsSrm2 = Map.of(Substance.NOX, 14.0, Substance.NH3, 9.4);
    lenient().when(emissionFactorSupplier
        .getRoadStandardVehicleStagnatedEmissionFactors(
            new RoadStandardEmissionFactorsKey(TEST_ROAD_AREA, TEST_ROAD_TYPE_SRM2, vehicleType, maximumSpeed, strictEnforcement, null)))
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
