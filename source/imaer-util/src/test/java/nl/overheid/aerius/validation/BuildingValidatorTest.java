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
package nl.overheid.aerius.validation;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import nl.overheid.aerius.shared.domain.v2.building.Building;
import nl.overheid.aerius.shared.domain.v2.building.BuildingFeature;
import nl.overheid.aerius.shared.domain.v2.building.BuildingLimits;
import nl.overheid.aerius.shared.domain.v2.geojson.LineString;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.geojson.Polygon;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

class BuildingValidatorTest {

  private static final String BUILDING_ID = "OurBuildingId";
  private static final String BUILDING_LABEL = "One More Building";

  @ParameterizedTest
  @MethodSource("casesForPolygonBuilding")
  void testValidBuilding(final double height, final ImaerExceptionReason errorReason, final ImaerExceptionReason warningReason) {
    final BuildingFeature building = createBuilding(height);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();

    BuildingValidator.validateBuildings(List.of(building), limits(), errors, warnings);

    if (errorReason == null) {
      assertEquals(List.of(), errors, "No errors");
    } else {
      assertEquals(1, errors.size(), "Number of errors");
      assertEquals(errorReason, errors.get(0).getReason(), "Error reason");
      assertArrayEquals(new Object[] {BUILDING_LABEL}, errors.get(0).getArgs(), "Arguments");
    }
    if (warningReason == null) {
      assertEquals(List.of(), warnings, "No warnings");
    } else {
      assertEquals(1, warnings.size(), "Number of warnings");
      assertEquals(warningReason, warnings.get(0).getReason(), "Error reason");
      assertArrayEquals(new Object[] {BUILDING_LABEL}, warnings.get(0).getArgs(), "Arguments");
    }
  }

  private static Stream<Arguments> casesForPolygonBuilding() {
    return Stream.of(
        Arguments.of(-0.00001, ImaerExceptionReason.BUILDING_HEIGHT_TOO_LOW, null),
        Arguments.of(0.0, null, ImaerExceptionReason.BUILDING_HEIGHT_ZERO),
        Arguments.of(0.00001, null, null),
        Arguments.of(2.0, null, null),
        Arguments.of(2.00001, ImaerExceptionReason.BUILDING_HEIGHT_TOO_HIGH, null));
  }

  @Test
  void testBuildingLineStringGeometry() {
    final BuildingFeature building = createBuilding(1);
    building.setGeometry(new LineString());

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();

    BuildingValidator.validateBuildings(List.of(building), limits(), errors, warnings);

    assertEquals(1, errors.size(), "Number of errors");
    assertEquals(ImaerExceptionReason.GML_GEOMETRY_NOT_PERMITTED, errors.get(0).getReason(), "Error reason");
    assertArrayEquals(new Object[] {BUILDING_LABEL}, errors.get(0).getArgs(), "Arguments");
    assertEquals(List.of(), warnings, "No warnings");
  }

  @ParameterizedTest
  @MethodSource("casesForCircularBuilding")
  void testValidCircularBuilding(final double diameter, final boolean valid) {
    final BuildingFeature building = createBuilding(1);
    building.setGeometry(new Point(0, 0));
    building.getProperties().setDiameter(diameter);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();

    BuildingValidator.validateBuildings(List.of(building), limits(), errors, warnings);

    if (valid) {
      assertEquals(List.of(), errors, "No errors");
      assertEquals(List.of(), warnings, "No warnings");
    } else {
      assertEquals(1, errors.size(), "Number of errors");
      assertEquals(ImaerExceptionReason.CIRCULAR_BUILDING_INCORRECT_DIAMETER, errors.get(0).getReason(), "Error reason");
      assertArrayEquals(new Object[] {BUILDING_LABEL}, errors.get(0).getArgs(), "Arguments");
      assertEquals(List.of(), warnings, "No warnings");
    }
  }

  private static Stream<Arguments> casesForCircularBuilding() {
    return Stream.of(
        Arguments.of(0.0, false),
        Arguments.of(0.00001, true),
        Arguments.of(3.0, true),
        Arguments.of(3.00001, false));
  }

  @Test
  void testCircularBuildingNotAllowed() {
    final BuildingFeature building = createBuilding(1);
    building.setGeometry(new Point(0, 0));
    building.getProperties().setDiameter(1);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();

    final BuildingLimits limits = mock(BuildingLimits.class);
    lenient().when(limits.isCircularBuildingSupported()).thenReturn(false);

    BuildingValidator.validateBuildings(List.of(building), limits, errors, warnings);

    assertEquals(1, errors.size(), "Number of errors");
    assertEquals(ImaerExceptionReason.GML_GEOMETRY_NOT_PERMITTED, errors.get(0).getReason(), "Error reason");
    assertArrayEquals(new Object[] {BUILDING_LABEL}, errors.get(0).getArgs(), "Arguments");
    assertEquals(List.of(), warnings, "No warnings");
  }

  private static BuildingLimits limits() {
    final BuildingLimits limits = mock(BuildingLimits.class);
    lenient().when(limits.isCircularBuildingSupported()).thenReturn(true);
    lenient().when(limits.buildingHeightMinimum()).thenReturn(0.0);
    lenient().when(limits.buildingHeightMaximum()).thenReturn(2.0);
    lenient().when(limits.buildingDiameterMinimum()).thenReturn(0.0);
    lenient().when(limits.buildingDiameterMaximum()).thenReturn(3.0);
    return limits;
  }

  private static BuildingFeature createBuilding(final double height) {
    final BuildingFeature feature = new BuildingFeature();
    feature.setGeometry(new Polygon());
    final Building building = new Building();
    building.setGmlId(BUILDING_ID);
    building.setLabel(BUILDING_LABEL);
    building.setHeight(height);
    feature.setProperties(building);
    return feature;
  }

}
