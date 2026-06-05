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

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.v6_0.source.TimeUnit;
import nl.overheid.aerius.gml.v6_0.source.road.SRM2Road;
import nl.overheid.aerius.gml.v6_0.source.road.StandardVehicle;
import nl.overheid.aerius.gml.v6_0.source.road.VehiclesProperty;
import nl.overheid.aerius.shared.domain.v2.source.SRM2RoadEmissionSource;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Unit test to test merging of standardVehicle sub sources.
 */
@ExtendWith(MockitoExtension.class)
class GML2RoadTest {

  private @Mock GMLConversionData conversionData;

  /**
   * Test merging sub sources. vehicle types with the same configuration can be joined into a single sub source.
   */
  @ParameterizedTest
  @CsvSource({"NON_URBAN_ROAD_NATIONAL", "Other"})
  void testStandardVehicleMerge(final String roadTypeCode) throws AeriusException {
    final SRM2Road gmlRoad = new SRM2Road();
    gmlRoad.setRoadTypeCode(roadTypeCode);
    gmlRoad.getVehicles().addAll(generateAllCombinations());

    final SRM2RoadEmissionSource converted = new GML2SRM2Road<>(conversionData).convert(gmlRoad);
    assertEquals(24, converted.getSubSources().size(), "Merging sub sources did not give the expected number of sub sources ");
  }

  private static List<VehiclesProperty> generateAllCombinations() {
    final TimeUnit[] timeUnits = {TimeUnit.DAY, TimeUnit.YEAR};
    final Boolean[] strictEnforcements = {null, false, true};
    final Integer[] maxSpeeds = {null, 0, 80, 100};
    final String[] vehicleTypes = {"A", "B"};
    final List<VehiclesProperty> subSources = new ArrayList<>();

    for (final TimeUnit second : timeUnits) {
      for (final Boolean third : strictEnforcements) {
        for (final Integer fourth : maxSpeeds) {
          for (final String first : vehicleTypes) {
            subSources.add(createStandardVehicle(first, second, third, fourth));
          }
        }
      }
    }
    return subSources;
  }

  private static VehiclesProperty createStandardVehicle(final String vehicleType, final TimeUnit timeUnit, final Boolean strictEnforcement,
      final Integer maxSpeed) {
    final StandardVehicle vehicle = new StandardVehicle();

    vehicle.setVehicleType(vehicleType);
    vehicle.setTimeUnit(timeUnit);
    vehicle.setStrictEnforcement(strictEnforcement);
    vehicle.setMaximumSpeed(maxSpeed);
    vehicle.setVehiclesPerTimeUnit(10);
    vehicle.setStagnationFactor(20);
    return new VehiclesProperty(vehicle);
  }
}
