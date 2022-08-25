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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import nl.overheid.aerius.shared.domain.v2.building.Building;
import nl.overheid.aerius.shared.domain.v2.building.BuildingFeature;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

class BuildingValidatorTest {

  private static final String SOURCE_ID = "OurSourceId";
  private static final String BUILDING_LABEL = "One More Building";
  private static final double BUILDING_HEIGHT_VALID = 1D;
  private static final double BUILDING_HEIGHT_ZERO = 0D;

  @Test
  void testValidBuilding() {
    final BuildingFeature building = new BuildingFeature();

    Building buildingProps = new Building();
    buildingProps.setGmlId(SOURCE_ID);
    buildingProps.setLabel(BUILDING_LABEL);
    buildingProps.setHeight(BUILDING_HEIGHT_VALID);
    building.setProperties(buildingProps);;

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();

    final List<BuildingFeature> buildings = new ArrayList<>();
    buildings.add(building);

    BuildingValidator.validateBuildings(buildings, errors, warnings);;

    assertTrue(errors.isEmpty(), "No errors");
    assertTrue(warnings.isEmpty(), "No warnings");
  }

  @Test
  void testZeroHeighBuilding() {
    final BuildingFeature building = new BuildingFeature();

    Building buildingProps = new Building();
    buildingProps.setGmlId(SOURCE_ID);
    buildingProps.setLabel(BUILDING_LABEL);
    buildingProps.setHeight(BUILDING_HEIGHT_ZERO);
    building.setProperties(buildingProps);;

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();

    final List<BuildingFeature> buildings = new ArrayList<>();
    buildings.add(building);

    BuildingValidator.validateBuildings(buildings, errors, warnings);;

    assertTrue(errors.isEmpty(), "No errors");
    assertEquals(1, warnings.size(), "Number of warnings");
    assertEquals(ImaerExceptionReason.BUILDING_HEIGHT_ZERO, warnings.get(0).getReason(), "Error reason");
    assertArrayEquals(new Object[] {BUILDING_LABEL}, warnings.get(0).getArgs(), "Arguments");

  }

}
