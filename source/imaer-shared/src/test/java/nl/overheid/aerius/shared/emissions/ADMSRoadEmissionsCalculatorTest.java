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
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.source.ADMSRoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.CustomVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.RoadStandardEmissionFactorsKey;
import nl.overheid.aerius.shared.domain.v2.source.road.RoadStandardsInterpolationValues;
import nl.overheid.aerius.shared.domain.v2.source.road.SpecificVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.TrafficDirection;
import nl.overheid.aerius.shared.domain.v2.source.road.ValuesPerVehicleType;
import nl.overheid.aerius.shared.domain.v2.source.road.Vehicles;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.geometry.GeometryCalculator;

/**
 * Test class for {@link ADMSRoadEmissionsCalculator}.
 */
@ExtendWith(MockitoExtension.class)
class ADMSRoadEmissionsCalculatorTest {

  private static final String TEST_ROAD_AREA = "UK";
  private static final String TEST_ROAD_TYPE_ADMS = "Lon1";

  @Mock RoadEmissionFactorSupplier emissionFactorSupplier;
  @Mock GeometryCalculator geometryCalculator;

  ADMSRoadEmissionsCalculator emissionsCalculator;

  @BeforeEach
  void beforeEach() throws AeriusException {
    emissionsCalculator = new ADMSRoadEmissionsCalculator(emissionFactorSupplier, geometryCalculator);
  }

  @Test
  void testCalculateEmissionsADMS() throws AeriusException {
    final ADMSRoadEmissionSource emissionSource = createADMSRoadEmissionSource();
    final Geometry geometry = mock(Geometry.class);
    when(geometryCalculator.determineMeasure(geometry)).thenReturn(321.5);
    final double gradient = 3.4;
    emissionSource.setGradient(gradient);
    emissionSource.setTrafficDirection(TrafficDirection.A_TO_B);

    // add 1 vehicles object of every type
    emissionSource.getSubSources().add(createCustom());
    emissionSource.getSubSources().add(createSpecific());
    emissionSource.getSubSources().add(createStandard(gradient, true, true));

    ensureSubSourceEmissionsEmpty(emissionSource);

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource, geometry);

    // Check total emissions
    // 321.5 * (3703.7010 + 115.0685 + 673.9726 ) * (365 * 24 * 60 * 60) / (1000 * 1000)
    assertEquals(4.555E7, results.get(Substance.NOX), 1E4, "Total NOx emissions");
    // 321.5 * (0.0 + 55.8892 + 172.60320000 ) * (365 * 24 * 60 * 60) / (1000 * 1000)
    assertEquals(2.317E6, results.get(Substance.NH3), 1E4, "Total NH3 emissions");
    // Check emissions per subsource (should be set during calculation)
    assertEquals(3.7551E7, emissionSource.getSubSources().get(0).getEmissions().get(Substance.NOX), 1E3, "NOx emissions first subsource");
    assertEquals(1.1667E6, emissionSource.getSubSources().get(1).getEmissions().get(Substance.NOX), 1E3, "NOx emissions second subsource");
    assertEquals(6.8333E6, emissionSource.getSubSources().get(2).getEmissions().get(Substance.NOX), 1E3, "NOx emissions third subsource");
    assertNull(emissionSource.getSubSources().get(0).getEmissions().get(Substance.NH3), "NH3 emissions first subsource");
    assertEquals(0.5667E6, emissionSource.getSubSources().get(1).getEmissions().get(Substance.NH3), 1E3, "NH3 emissions second subsource");
    assertEquals(1.7500E6, emissionSource.getSubSources().get(2).getEmissions().get(Substance.NH3), 1E3, "NH3 emissions third subsource");
  }

  private ADMSRoadEmissionSource createADMSRoadEmissionSource() {
    final ADMSRoadEmissionSource emissionSource = new ADMSRoadEmissionSource();
    emissionSource.setRoadAreaCode(TEST_ROAD_AREA);
    emissionSource.setRoadTypeCode(TEST_ROAD_TYPE_ADMS);
    return emissionSource;
  }

  @Test
  void testCalculateEmissionsCustomVehicles() {
    final CustomVehicles vehicles = createCustom();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles);

    // 365 * 300 * 12.34567 / 365
    assertEquals(3703.7010, results.get(Substance.NOX).doubleValue(), 1E-3, "NOx emissions custom vehicle");
  }

  @Test
  void testCalculateEmissionsSpecificVehicles() {
    final SpecificVehicles vehicles = createSpecific();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles, createADMSRoadEmissionSource());

    // 12 * 500 * 7.0 / 365
    assertEquals(115.0685, results.get(Substance.NOX).doubleValue(), 1E-3, "NOx emissions specific vehicle");
    // 12 * 500 * 3.4 / 365
    assertEquals(55.8904, results.get(Substance.NH3).doubleValue(), 1E-3, "NH3 emissions specific vehicle");
  }

  @ParameterizedTest
  @MethodSource("standardVehicleInterpolation")
  void testCalculateEmissionsStandardVehicles(final boolean interpolateSpeed, final boolean interpolateGradient, final TrafficDirection direction,
      final double expectedNOx) {
    final double gradient = 3.4;
    final StandardVehicles vehicles = createStandard(gradient, interpolateSpeed, interpolateGradient);
    final ADMSRoadEmissionSource roadEmissionSource = mock(ADMSRoadEmissionSource.class);
    when(roadEmissionSource.getRoadAreaCode()).thenReturn(TEST_ROAD_AREA);
    when(roadEmissionSource.getRoadTypeCode()).thenReturn(TEST_ROAD_TYPE_ADMS);
    when(roadEmissionSource.getGradient()).thenReturn(gradient);
    when(roadEmissionSource.getTrafficDirection()).thenReturn(direction);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles, roadEmissionSource);

    // See method source as to what the calculation should be
    assertEquals(expectedNOx, results.get(Substance.NOX).doubleValue(), 1E-3, "NOx interpolated emissions standard vehicle");
    // Kept the NH3 the same for each test, so should return about the same
    // 30000 * 2.1 / 365
    assertEquals(172.6027, results.get(Substance.NH3).doubleValue(), 1E-3, "NH3 interpolated emissions standard vehicle");
  }

  private static Stream<Arguments> standardVehicleInterpolation() {
    // When no interpolation is done for emission factor, the last emission factor mocked should be used.
    // 30000 * 9.0 / 365
    final Arguments noInterpolationAtoB = Arguments.of(false, false, TrafficDirection.A_TO_B, 739.7260);
    // 30000 * 19.0 / 365
    final Arguments noInterpolationBtoA = Arguments.of(false, false, TrafficDirection.B_TO_A, 1561.6438);
    // 15000 * 9.0 / 365 + 15000 * 19.0 / 365
    final Arguments noInterpolationBoth = Arguments.of(false, false, TrafficDirection.BOTH, 1150.6849);

    // When interpolation is done for speed
    // interpolated = 6 + ((40 - 25) / (50 - 25)) * (9 - 6) = 7.8
    // 30000 * 7.8 / 365
    final Arguments speedInterpolationAtoB = Arguments.of(true, false, TrafficDirection.A_TO_B, 641.0959);
    // interpolated = 16 + ((40 - 25) / (50 - 25)) * (19 - 16) = 17.8
    // 30000 * 17.8 / 365
    final Arguments speedInterpolationBtoA = Arguments.of(true, false, TrafficDirection.B_TO_A, 1463.0137);
    // 15000 * 7.8 / 365 + 15000 * 17.8 / 365
    final Arguments speedInterpolationBoth = Arguments.of(true, false, TrafficDirection.BOTH, 1052.0548);

    // When interpolation is done for gradient
    // interpolated = 6 + ((3.4 - 1) / (4 - 1)) * (9 - 6) = 8.4
    // 30000 * 8.4 / 365
    final Arguments gradientInterpolationAtoB = Arguments.of(false, true, TrafficDirection.A_TO_B, 690.4110);
    // interpolated = 16 + ((-3.4 - -4) / (-1 - -4)) * (19 - 16) = 16.6
    // 30000 * 16.6 / 365
    final Arguments gradientInterpolationBtoA = Arguments.of(false, true, TrafficDirection.B_TO_A, 1364.3836);
    // 15000 * 8.4 / 365 + 15000 * 16.6 / 365
    final Arguments gradientInterpolationBoth = Arguments.of(false, true, TrafficDirection.BOTH, 1027.3973);

    // When interpolation is done for both
    // gradientFloor = 6 + ((40 - 25) / (50 - 25)) * (7 - 6) = 6.6
    // gradientCeiling = 8 + ((40 - 25) / (50 - 25)) * (9 - 8) = 8.6
    // interpolatedTotal = 6.6 + ((3.4 - 1) / (4 - 1)) * (8.6 - 6.6) = 8.2
    // 30000 * 8.2 / 365
    final Arguments bothInterpolationAtoB = Arguments.of(true, true, TrafficDirection.A_TO_B, 673.9726);
    // gradientFloor = 16 + ((40 - 25) / (50 - 25)) * (17 - 16) = 16.6
    // gradientCeiling = 18 + ((40 - 25) / (50 - 25)) * (19 - 18) = 18.6
    // interpolatedTotal = 16.6 + ((-3.4 - -4) / (-1 - -4)) * (18.6 - 16.6) = 17
    // 30000 * 17.0 / 365
    final Arguments bothInterpolationBtoA = Arguments.of(true, true, TrafficDirection.B_TO_A, 1397.2603);
    // 15000 * 8.2 / 365 + 15000 * 17.0 / 365
    final Arguments bothInterpolationBoth = Arguments.of(true, true, TrafficDirection.BOTH, 1035.6164);

    return Stream.of(noInterpolationAtoB, speedInterpolationAtoB, gradientInterpolationAtoB, bothInterpolationAtoB,
        noInterpolationBtoA, speedInterpolationBtoA, gradientInterpolationBtoA, bothInterpolationBtoA,
        noInterpolationBoth, speedInterpolationBoth, gradientInterpolationBoth, bothInterpolationBoth);
  }

  @Test
  void testCalculateEmissionsMultipleStandardVehicles() {
    final double gradient = 3.7;
    final StandardVehicles vehicles = createMultipleStandard(gradient);
    final ADMSRoadEmissionSource roadEmissionSource = mock(ADMSRoadEmissionSource.class);
    when(roadEmissionSource.getRoadAreaCode()).thenReturn(TEST_ROAD_AREA);
    when(roadEmissionSource.getRoadTypeCode()).thenReturn(TEST_ROAD_TYPE_ADMS);
    when(roadEmissionSource.getGradient()).thenReturn(gradient);
    when(roadEmissionSource.getTrafficDirection()).thenReturn(TrafficDirection.A_TO_B);

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles, roadEmissionSource);

    // Interpolation is done for emission factor
    // gradientFloor = 6 + ((40 - 25) / (50 - 25)) * (7 - 6) = 6.6
    // gradientCeiling = 8 + ((40 - 25) / (50 - 25)) * (9 - 8) = 8.6
    // interpolatedTotal = 6.6 + ((3.7 - 1) / (4 - 1)) * (8.6 - 6.6) = 8.4
    // (30000 * 8.4 + 2000 * 10.0) / 365
    assertEquals(745.2055, results.get(Substance.NOX).doubleValue(), 1E-3, "NOx interpolated emissions standard vehicles");
    // (30000 * 2.1 + 2000 * 5.3) / 365
    assertEquals(201.6438, results.get(Substance.NH3).doubleValue(), 1E-3, "NH3 interpolated emissions standard vehicles");
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
    lenient().when(emissionFactorSupplier.getRoadSpecificVehicleEmissionFactors(specificCode, TEST_ROAD_TYPE_ADMS)).thenReturn(emissionFactors);

    return vehicles;
  }

  private StandardVehicles createStandard(final double gradient, final boolean speedInterpolation, final boolean gradientInterpolation) {
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

    final int lowerBoundSpeed = speedInterpolation ? maximumSpeed - 15 : maximumSpeed;
    final int upperBoundSpeed = speedInterpolation ? maximumSpeed + 10 : maximumSpeed;
    final int lowerBoundGradient = gradientInterpolation ? (int) gradient - 2 : (int) gradient;
    final int upperBoundGradient = gradientInterpolation ? (int) gradient + 1 : (int) gradient;

    lenient().when(emissionFactorSupplier
        .getRoadStandardVehicleInterpolationValues(
            new RoadStandardEmissionFactorsKey(TEST_ROAD_AREA, TEST_ROAD_TYPE_ADMS, vehicleType, maximumSpeed, strictEnforcement, gradient)))
        .thenReturn(new RoadStandardsInterpolationValues(lowerBoundSpeed, upperBoundSpeed, lowerBoundGradient, upperBoundGradient));
    lenient().when(emissionFactorSupplier
        .getRoadStandardVehicleInterpolationValues(
            new RoadStandardEmissionFactorsKey(TEST_ROAD_AREA, TEST_ROAD_TYPE_ADMS, vehicleType, maximumSpeed, strictEnforcement, -gradient)))
        .thenReturn(new RoadStandardsInterpolationValues(lowerBoundSpeed, upperBoundSpeed, -upperBoundGradient, -lowerBoundGradient));

    mockEmissionFactors(vehicleType, lowerBoundSpeed, strictEnforcement, lowerBoundGradient, Map.of(Substance.NOX, 6.0, Substance.NH3, 2.1));
    mockEmissionFactors(vehicleType, lowerBoundSpeed, strictEnforcement, -upperBoundGradient, Map.of(Substance.NOX, 16.0, Substance.NH3, 2.1));
    if (speedInterpolation && gradientInterpolation) {
      // Not directly necessary as we're using lenient, but helps with understanding which factors are used.
      mockEmissionFactors(vehicleType, upperBoundSpeed, strictEnforcement, lowerBoundGradient, Map.of(Substance.NOX, 7.0, Substance.NH3, 2.1));
      mockEmissionFactors(vehicleType, upperBoundSpeed, strictEnforcement, -upperBoundGradient, Map.of(Substance.NOX, 17.0, Substance.NH3, 2.1));
      mockEmissionFactors(vehicleType, lowerBoundSpeed, strictEnforcement, upperBoundGradient, Map.of(Substance.NOX, 8.0, Substance.NH3, 2.1));
      mockEmissionFactors(vehicleType, lowerBoundSpeed, strictEnforcement, -lowerBoundGradient, Map.of(Substance.NOX, 18.0, Substance.NH3, 2.1));
    }
    mockEmissionFactors(vehicleType, upperBoundSpeed, strictEnforcement, upperBoundGradient, Map.of(Substance.NOX, 9.0, Substance.NH3, 2.1));
    mockEmissionFactors(vehicleType, upperBoundSpeed, strictEnforcement, -lowerBoundGradient, Map.of(Substance.NOX, 19.0, Substance.NH3, 2.1));

    return vehicles;
  }

  private void mockEmissionFactors(final String vehicleType, final int speed, final boolean strictEnforcement, final int gradient,
      final Map<Substance, Double> result) {
    lenient().when(emissionFactorSupplier
        .getRoadStandardVehicleEmissionFactors(new RoadStandardEmissionFactorsKey(
            TEST_ROAD_AREA, TEST_ROAD_TYPE_ADMS, vehicleType, speed, strictEnforcement, (double) gradient)))
        .thenReturn(result);
  }

  private StandardVehicles createMultipleStandard(final double gradient) {
    final StandardVehicles vehicles = createStandard(gradient, true, true);
    final ValuesPerVehicleType valuesPerVehicleType = new ValuesPerVehicleType();
    valuesPerVehicleType.setVehiclesPerTimeUnit(2000);
    final String vehicleType = "HEAVY_FREIGHT";
    vehicles.getValuesPerVehicleTypes().put(vehicleType, valuesPerVehicleType);
    final boolean strictEnforcement = vehicles.getStrictEnforcement();
    final int maximumSpeed = vehicles.getMaximumSpeed();
    final int gradientMatch = (int) gradient;

    lenient().when(emissionFactorSupplier
        .getRoadStandardVehicleInterpolationValues(
            new RoadStandardEmissionFactorsKey(TEST_ROAD_AREA, TEST_ROAD_TYPE_ADMS, vehicleType, maximumSpeed, strictEnforcement, gradient)))
        .thenReturn(new RoadStandardsInterpolationValues(maximumSpeed, maximumSpeed, gradientMatch, gradientMatch));

    final Map<Substance, Double> emissionFactors = Map.of(Substance.NOX, 10.0, Substance.NH3, 5.3);
    mockEmissionFactors(vehicleType, maximumSpeed, strictEnforcement, gradientMatch, emissionFactors);

    return vehicles;
  }

  private void ensureSubSourceEmissionsEmpty(final RoadEmissionSource emissionSource) {
    // Ensure emissions per subsource are unknown at start
    for (final Vehicles vehicles : emissionSource.getSubSources()) {
      assertTrue(vehicles.getEmissions().isEmpty());
    }
  }

}
