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
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.GMLLegacyCodeConverter.GMLLegacyCodeType;
import nl.overheid.aerius.gml.base.source.IsGmlEmissionSource;
import nl.overheid.aerius.gml.base.source.IsGmlTimeUnit;
import nl.overheid.aerius.shared.domain.v2.source.road.CustomVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.SpecificVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.Vehicles;
import nl.overheid.aerius.shared.exception.AeriusException;

class GML2VehicleUtilTest {

  private static final String REMOVED_CODE = "BABCEUR4";
  private static final String VALID_CODE = "VALID_CODE";
  private static final String SOURCE_LABEL = "Test source";

  @Test
  void testRemovedCodeRoutesToCustomVehicles() throws AeriusException {
    final GMLConversionData conversionData = mock(GMLConversionData.class);
    when(conversionData.getCode(GMLLegacyCodeType.ON_ROAD_MOBILE_SOURCE, REMOVED_CODE, SOURCE_LABEL))
        .thenReturn(REMOVED_CODE);
    when(conversionData.warnIfRemovedCode(GMLLegacyCodeType.ON_ROAD_MOBILE_SOURCE, REMOVED_CODE, SOURCE_LABEL))
        .thenReturn(true);

    final List<Vehicles> vehicles = new ArrayList<>();
    GML2VehicleUtil.addEmissionValuesSpecific(vehicles, mockSource(), mockSpecificVehicle(REMOVED_CODE), conversionData);

    assertEquals(1, vehicles.size());
    assertInstanceOf(CustomVehicles.class, vehicles.get(0));
  }

  @Test
  void testValidCodeRoutesToSpecificVehicles() throws AeriusException {
    final GMLConversionData conversionData = mock(GMLConversionData.class);
    when(conversionData.getCode(GMLLegacyCodeType.ON_ROAD_MOBILE_SOURCE, VALID_CODE, SOURCE_LABEL))
        .thenReturn(VALID_CODE);
    when(conversionData.warnIfRemovedCode(GMLLegacyCodeType.ON_ROAD_MOBILE_SOURCE, VALID_CODE, SOURCE_LABEL))
        .thenReturn(false);

    final List<Vehicles> vehicles = new ArrayList<>();
    GML2VehicleUtil.addEmissionValuesSpecific(vehicles, mockSource(), mockSpecificVehicle(VALID_CODE), conversionData);

    assertEquals(1, vehicles.size());
    assertInstanceOf(SpecificVehicles.class, vehicles.get(0));
  }

  private IsGmlEmissionSource mockSource() {
    final IsGmlEmissionSource source = mock(IsGmlEmissionSource.class);
    when(source.getLabel()).thenReturn(SOURCE_LABEL);
    return source;
  }

  private IsGmlSpecificVehicle mockSpecificVehicle(final String code) {
    final IsGmlSpecificVehicle vehicle = mock(IsGmlSpecificVehicle.class);
    when(vehicle.getCode()).thenReturn(code);
    when(vehicle.getVehiclesPerTimeUnit()).thenReturn(100.0);
    final IsGmlTimeUnit timeUnit = mock(IsGmlTimeUnit.class);
    when(timeUnit.name()).thenReturn("DAY");
    when(vehicle.getTimeUnit()).thenReturn(timeUnit);
    return vehicle;
  }

}
