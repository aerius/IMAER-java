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
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.source.ADMSRoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.CustomVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.SpecificVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.ValuesPerVehicleType;
import nl.overheid.aerius.shared.domain.v2.source.road.Vehicles;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.geometry.GeometryCalculator;

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
    final ADMSRoadEmissionSource emissionSource = new ADMSRoadEmissionSource();
    emissionSource.setRoadAreaCode(TEST_ROAD_AREA);
    emissionSource.setRoadTypeCode(TEST_ROAD_TYPE_ADMS);
    final Geometry geometry = mock(Geometry.class);
    when(geometryCalculator.determineMeasure(geometry)).thenReturn(321.5);

    // add 1 vehicles object of every type
    emissionSource.getSubSources().add(createCustom());
    emissionSource.getSubSources().add(createSpecific());
    emissionSource.getSubSources().add(createStandard());

    ensureSubSourceEmissionsEmpty(emissionSource);

    final Map<Substance, Double> results = emissionsCalculator.calculateEmissions(emissionSource, geometry);

    // Check total emissions
    // 321.5 * (3703.70100000 + 115.0660 + 493.1520 ) / (1000 * 1000)
    assertEquals(1.3862819585, results.get(Substance.NOX));
    // 321.5 * (0.0 + 55.8892 + 172.6032 ) / (1000 * 1000)
    assertEquals(0.0734603066, results.get(Substance.NH3));
    // Check emissions per subsource (should be set during calculation)
    assertEquals(1.1907398715, emissionSource.getSubSources().get(0).getEmissions().get(Substance.NOX));
    assertEquals(0.036993719, emissionSource.getSubSources().get(1).getEmissions().get(Substance.NOX));
    assertEquals(0.158548368, emissionSource.getSubSources().get(2).getEmissions().get(Substance.NOX));
    assertNull(emissionSource.getSubSources().get(0).getEmissions().get(Substance.NH3));
    assertEquals(0.0179683778, emissionSource.getSubSources().get(1).getEmissions().get(Substance.NH3));
    assertEquals(0.0554919288, emissionSource.getSubSources().get(2).getEmissions().get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsCustomVehicles() {
    final CustomVehicles vehicles = createCustom();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles);

    // 365 * 300 * 12.34567 / 365
    assertEquals(new BigDecimal("3703.70100000"), results.get(Substance.NOX));
  }

  @Test
  void testCalculateEmissionsSpecificVehicles() {
    final SpecificVehicles vehicles = createSpecific();

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles, TEST_ROAD_TYPE_ADMS);

    // 12 * 500 * 7.0 / 365
    assertEquals(new BigDecimal("115.0660"), results.get(Substance.NOX));
    // 12 * 500 * 3.4 / 365
    assertEquals(new BigDecimal("55.8892"), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsStandardVehicles() {
    final StandardVehicles vehicles = createStandard();
    final double gradient = 3.4;

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles, TEST_ROAD_AREA, TEST_ROAD_TYPE_ADMS,
        gradient);

    // 30000 * 6.0 / 365
    assertEquals(new BigDecimal("493.1520"), results.get(Substance.NOX));
    // 30000 * 2.1 / 365
    assertEquals(new BigDecimal("172.6032"), results.get(Substance.NH3));
  }

  @Test
  void testCalculateEmissionsMultipleStandardVehicles() {
    final StandardVehicles vehicles = createMultipleStandard();
    final double gradient = 2.7;

    final Map<Substance, BigDecimal> results = emissionsCalculator.calculateEmissions(vehicles, TEST_ROAD_AREA, TEST_ROAD_TYPE_ADMS,
        gradient);

    // (30000 * 6.0 + 2000 * 10.0) / 365
    assertEquals(new BigDecimal("547.9420"), results.get(Substance.NOX));
    // (30000 * 2.1 + 2000 * 5.3) / 365
    assertEquals(new BigDecimal("201.6419"), results.get(Substance.NH3));
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
        .getRoadStandardVehicleEmissionFactors(TEST_ROAD_AREA, vehicleType, TEST_ROAD_TYPE_ADMS, maximumSpeed, strictEnforcement))
        .thenReturn(emissionFactorsSrm1);

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
        .getRoadStandardVehicleEmissionFactors(TEST_ROAD_AREA, vehicleType, TEST_ROAD_TYPE_ADMS, maximumSpeed, strictEnforcement))
        .thenReturn(emissionFactorsSrm2);

    return vehicles;
  }

  private void ensureSubSourceEmissionsEmpty(final RoadEmissionSource emissionSource) {
    // Ensure emissions per subsource are unknown at start
    for (final Vehicles vehicles : emissionSource.getSubSources()) {
      assertTrue(vehicles.getEmissions().isEmpty());
    }
  }

}
