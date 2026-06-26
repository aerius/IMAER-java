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
package nl.overheid.aerius.gml.base.source.road;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.overheid.aerius.gml.base.FeatureMember;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.source.road.v10.GML2SRM2RoadV10;
import nl.overheid.aerius.gml.base.source.road.v40.GML2SRM2RoadV40;
import nl.overheid.aerius.gml.v6_0.source.TimeUnit;
import nl.overheid.aerius.gml.v6_0.source.road.SRM2Road;
import nl.overheid.aerius.gml.v6_0.source.road.StandardVehicle;
import nl.overheid.aerius.gml.v6_0.source.road.VehiclesProperty;
import nl.overheid.aerius.shared.domain.v2.source.SRM2RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.VehicleType;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Unit test to test merging of standardVehicle sub sources.
 *
 * Test also include testing older GML versions.
 */
@ExtendWith(MockitoExtension.class)
class GML2RoadTest {

  private interface VehicleCreator<T extends IsGmlProperty<IsGmlVehicle>> {
    T create(final String vehicleType, final String timeUnit, final Boolean strictEnforcement, final Integer maxSpeed);
  }

  private static final List<String> VEHICLE_TYPES = List.of("LIGHT_TRAFFIC", "NORMAL_FREIGHT");

  private List<AeriusException> warnings;

  private @Mock GMLConversionData conversionData;

  @BeforeEach
  void beforeEach() {
    warnings = new ArrayList<>();
    lenient().doReturn(warnings).when(conversionData).getWarnings();
  }

  // Test for GML version 1.0 and below.

  @ParameterizedTest
  @CsvSource({"3112,80,1,0", "3112,0,1,1", "3112,,1,1", "3111,100,1,0"})
  void testStandardVehicleV10Merge(final int sectorId, final Integer maxSpeed, final int expectedSubSources, final int expectedNrOfWarnings)
      throws AeriusException {
    final nl.overheid.aerius.gml.v1_0.source.road.SRM2RoadEmissionSource gmlRoad = createV10Road(sectorId, maxSpeed,
        VEHICLE_TYPES.stream().map(GML2RoadTest::createStandardVehicleV10).toList());
    final SRM2RoadEmissionSource converted = new GML2SRM2RoadV10<>(conversionData).convert(gmlRoad);

    assertStandardVehicleMerge(converted, expectedSubSources, expectedNrOfWarnings);
  }

  @Test
  void testStandardVehicleV10SpeedSet() throws AeriusException {
    final nl.overheid.aerius.gml.v1_0.source.road.SRM2RoadEmissionSource gmlRoad = createV10Road(3112, 0,
        List.of(createStandardVehicleV10("LIGHT_TRAFFIC")));
    final SRM2RoadEmissionSource converted = new GML2SRM2RoadV10<>(conversionData).convert(gmlRoad);

    assertEquals(60, ((StandardVehicles) converted.getSubSources().get(0)).getMaximumSpeed(), "Expected the speed for version 1.0 to be set");
  }

  private static nl.overheid.aerius.gml.v1_0.source.road.SRM2RoadEmissionSource createV10Road(final int sectorId, final Integer maxSpeed,
      final List<nl.overheid.aerius.gml.v1_0.source.road.VehiclesProperty> vehicles) {
    final nl.overheid.aerius.gml.v1_0.source.road.SRM2RoadEmissionSource gmlRoad = new nl.overheid.aerius.gml.v1_0.source.road.SRM2RoadEmissionSource();

    setId(gmlRoad);
    gmlRoad.setSectorId(sectorId);
    gmlRoad.setMaximumSpeed(maxSpeed);
    gmlRoad.getVehicles().addAll(vehicles);
    return gmlRoad;
  }

  private static nl.overheid.aerius.gml.v1_0.source.road.VehiclesProperty createStandardVehicleV10(final String vehicleType) {
    final nl.overheid.aerius.gml.v1_0.source.road.StandardVehicle vehicle = new nl.overheid.aerius.gml.v1_0.source.road.StandardVehicle();
    vehicle.setVehicleType(VehicleType.safeValueOf(vehicleType));
    vehicle.setStagnationFactor(20);
    final nl.overheid.aerius.gml.v1_0.source.road.VehiclesProperty vp = new nl.overheid.aerius.gml.v1_0.source.road.VehiclesProperty();

    vp.setProperty(vehicle);
    return vp;
  }

  // Test for GML version 1.1 to 4.0.

  @ParameterizedTest
  @CsvSource({"3112,12", "3111,0"})
  void testStandardVehicleV40Merge(final int sectorId, final int expectedNrOfWarnings) throws AeriusException {
    final nl.overheid.aerius.gml.v4_0.source.road.SRM2Road gmlRoad = createV40Road(sectorId,
        generateAllCombinations(GML2RoadTest::createStandardVehicleV40));
    final SRM2RoadEmissionSource converted = new GML2SRM2RoadV40<>(conversionData).convert(gmlRoad);

    assertStandardVehicleMerge(converted, 24, expectedNrOfWarnings);
  }

  @ParameterizedTest
  @CsvSource({",", "0"})
  void testStandardVehicleV40SpeedSet(final Integer speed) throws AeriusException {
    final nl.overheid.aerius.gml.v4_0.source.road.SRM2Road gmlRoad = createV40Road(3112,
        List.of(createStandardVehicleV40("LIGHT_TRAFFIC", "DAY", true, speed)));
    final SRM2RoadEmissionSource converted = new GML2SRM2RoadV40<>(conversionData).convert(gmlRoad);

    assertEquals(60, ((StandardVehicles) converted.getSubSources().get(0)).getMaximumSpeed(), "Expected the speed for versrion 4.0 to be set");
  }

  private static nl.overheid.aerius.gml.v4_0.source.road.SRM2Road createV40Road(final int sectorId,
      final List<nl.overheid.aerius.gml.v4_0.source.road.VehiclesProperty> vehicles) {
    final nl.overheid.aerius.gml.v4_0.source.road.SRM2Road gmlRoad = new nl.overheid.aerius.gml.v4_0.source.road.SRM2Road();

    setId(gmlRoad);
    gmlRoad.setSectorId(sectorId);
    gmlRoad.getVehicles().addAll(vehicles);
    return gmlRoad;
  }

  private static nl.overheid.aerius.gml.v4_0.source.road.VehiclesProperty createStandardVehicleV40(final String vehicleType, final String timeUnit,
      final Boolean strictEnforcement, final Integer maxSpeed) {
    final nl.overheid.aerius.gml.v4_0.source.road.StandardVehicle vehicle = new nl.overheid.aerius.gml.v4_0.source.road.StandardVehicle();

    vehicle.setVehicleType(VehicleType.safeValueOf(vehicleType));
    vehicle.setTimeUnit(nl.overheid.aerius.gml.v4_0.source.TimeUnit.valueOf(timeUnit));
    vehicle.setStrictEnforcement(strictEnforcement);
    vehicle.setMaximumSpeed(maxSpeed);
    vehicle.setVehiclesPerTimeUnit(10);
    vehicle.setStagnationFactor(20);
    return new nl.overheid.aerius.gml.v4_0.source.road.VehiclesProperty(vehicle);
  }

  // Tests for GML version beyond version 4.0.

  @ParameterizedTest
  @CsvSource({"NON_URBAN_ROAD_NATIONAL,12", "Other,0"})
  void testStandardVehicleMerge(final String roadTypeCode, final int expectedNrOfWarnings) throws AeriusException {
    final SRM2Road gmlRoad = createRoad(roadTypeCode, generateAllCombinations(GML2RoadTest::createStandardVehicle));
    final SRM2RoadEmissionSource converted = new GML2SRM2Road<>(conversionData).convert(gmlRoad);

    assertStandardVehicleMerge(converted, 24, expectedNrOfWarnings);
  }

  @ParameterizedTest
  @CsvSource({",", "0"})
  void testStandardVehicleSpeedSet(final Integer speed) throws AeriusException {
    final SRM2Road gmlRoad = createRoad("NON_URBAN_ROAD_NATIONAL", List.of(createStandardVehicle("LIGHT_TRAFFIC", "DAY", true, speed)));
    final SRM2RoadEmissionSource converted = new GML2SRM2Road<>(conversionData).convert(gmlRoad);

    assertEquals(60, ((StandardVehicles) converted.getSubSources().get(0)).getMaximumSpeed(), "Expected the speed to be set");
  }

  private static SRM2Road createRoad(final String roadTypeCode, final List<VehiclesProperty> vehicles) {
    final SRM2Road gmlRoad = new SRM2Road();
    setId(gmlRoad);
    gmlRoad.setRoadTypeCode(roadTypeCode);
    gmlRoad.getVehicles().addAll(vehicles);
    return gmlRoad;
  }

  private static VehiclesProperty createStandardVehicle(final String vehicleType, final String timeUnit, final Boolean strictEnforcement,
      final Integer maxSpeed) {
    final StandardVehicle vehicle = new StandardVehicle();

    vehicle.setVehicleType(vehicleType);
    vehicle.setTimeUnit(TimeUnit.valueOf(timeUnit));
    vehicle.setStrictEnforcement(strictEnforcement);
    vehicle.setMaximumSpeed(maxSpeed);
    vehicle.setVehiclesPerTimeUnit(10);
    vehicle.setStagnationFactor(20);
    return new VehiclesProperty(vehicle);
  }

  // Generic methods

  private static void setId(final FeatureMember gmlRoad) {
    gmlRoad.setId("123");
  }

  private void assertStandardVehicleMerge(final SRM2RoadEmissionSource source, final int expectedSubSources, final int expectedNrOfWarnings)
      throws AeriusException {
    assertEquals(expectedSubSources, source.getSubSources().size(), "Merging sub sources did not give the expected number of sub sources ");
    assertEquals(expectedNrOfWarnings, conversionData.getWarnings().size(), "Not the expected number of warnings");
    conversionData.getWarnings().stream().forEach(e -> {
      assertEquals(e.getReason(), ImaerExceptionReason.GML_NON_URBAN_ROAD_DEFAULT_SPEED,
          "Not the expected warning reason code GML_NON_URBAN_ROAD_DEFAULT_SPEED");
      assertEquals("123", e.getArgs()[0], "Source is doesn't match");
      assertEquals("60", e.getArgs()[1], "Not the expected default speed");
    });
  }

  private static <T extends IsGmlProperty<IsGmlVehicle>> List<T> generateAllCombinations(final VehicleCreator<T> creator) {
    final String[] timeUnits = {TimeUnit.DAY.name(), TimeUnit.YEAR.name()};
    final Boolean[] strictEnforcements = {null, false, true};
    final Integer[] maxSpeeds = {null, 0, 60, 100};
    final List<T> subSources = new ArrayList<>();

    for (final String timeUnit : timeUnits) {
      for (final Boolean strictEnforcement : strictEnforcements) {
        for (final Integer maxSpeed : maxSpeeds) {
          for (final String vehicleType : VEHICLE_TYPES) {
            subSources.add(creator.create(vehicleType, timeUnit, strictEnforcement, maxSpeed));
          }
        }
      }
    }
    return subSources;
  }
}
