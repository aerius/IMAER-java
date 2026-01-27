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
package nl.overheid.aerius.gml.base.conversion;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.source.ColdStartEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.domain.v2.source.SRM1RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.CustomVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.SpecificVehicles;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

class RemovedVehicleCodeConverterTest {

  private static final String REMOVED_CODE = "BABCEUR4";
  private static final String UNKNOWN_CODE = "UNKNOWN_CODE";
  private static final String SOURCE_LABEL = "Source label";

  @Test
  void testRoadSourceWithRemovedCodeIsConverted() {
    final Set<String> removedCodes = Set.of(REMOVED_CODE);
    final List<AeriusException> warnings = new ArrayList<>();
    final RemovedVehicleCodeConverter converter =
        new RemovedVehicleCodeConverter(removedCodes, warnings);

    final List<EmissionSourceFeature> sources =
        List.of(createRoadSourceWithSpecificVehicle(REMOVED_CODE));

    converter.convertRemovedVehicleCodes(sources);

    // Verify the SpecificVehicles was converted to CustomVehicles
    final SRM1RoadEmissionSource roadSource =
        (SRM1RoadEmissionSource) sources.get(0).getProperties();
    assertEquals(1, roadSource.getSubSources().size());
    assertInstanceOf(CustomVehicles.class, roadSource.getSubSources().get(0));

    final CustomVehicles customVehicles = (CustomVehicles) roadSource.getSubSources().get(0);
    assertEquals(TimeUnit.DAY, customVehicles.getTimeUnit());
    assertEquals(100.0, customVehicles.getVehiclesPerTimeUnit());
    assertEquals("Voormalig " + REMOVED_CODE, customVehicles.getDescription());

    // Verify warning was added
    assertEquals(1, warnings.size());
    assertEquals(ImaerExceptionReason.GML_UNKNOWN_MOBILE_SOURCE_CODE, warnings.get(0).getReason());
  }

  @Test
  void testRoadSourceWithUnknownCodeIsNotConverted() {
    final Set<String> removedCodes = Set.of(REMOVED_CODE);
    final List<AeriusException> warnings = new ArrayList<>();
    final RemovedVehicleCodeConverter converter =
        new RemovedVehicleCodeConverter(removedCodes, warnings);

    final List<EmissionSourceFeature> sources =
        List.of(createRoadSourceWithSpecificVehicle(UNKNOWN_CODE));

    converter.convertRemovedVehicleCodes(sources);

    // Verify the SpecificVehicles was NOT converted (unknown code not in removed list)
    final SRM1RoadEmissionSource roadSource =
        (SRM1RoadEmissionSource) sources.get(0).getProperties();
    assertEquals(1, roadSource.getSubSources().size());
    assertInstanceOf(SpecificVehicles.class, roadSource.getSubSources().get(0));

    // Verify no warning was added
    assertEquals(0, warnings.size());
  }

  @Test
  void testColdStartSourceWithRemovedCodeIsConverted() {
    final Set<String> removedCodes = Set.of(REMOVED_CODE);
    final List<AeriusException> warnings = new ArrayList<>();
    final RemovedVehicleCodeConverter converter =
        new RemovedVehicleCodeConverter(removedCodes, warnings);

    final List<EmissionSourceFeature> sources =
        List.of(createColdStartSourceWithSpecificVehicle(REMOVED_CODE));

    converter.convertRemovedVehicleCodes(sources);

    // Verify the SpecificVehicles was converted to CustomVehicles
    final ColdStartEmissionSource coldStartSource =
        (ColdStartEmissionSource) sources.get(0).getProperties();
    assertEquals(1, coldStartSource.getSubSources().size());
    assertInstanceOf(CustomVehicles.class, coldStartSource.getSubSources().get(0));

    // Verify warning was added
    assertEquals(1, warnings.size());
    assertEquals(ImaerExceptionReason.GML_UNKNOWN_MOBILE_SOURCE_CODE, warnings.get(0).getReason());
  }

  private EmissionSourceFeature createRoadSourceWithSpecificVehicle(final String vehicleCode) {
    final SRM1RoadEmissionSource source = new SRM1RoadEmissionSource();
    source.setLabel(SOURCE_LABEL);

    final SpecificVehicles specific = new SpecificVehicles();
    specific.setVehicleCode(vehicleCode);
    specific.setTimeUnit(TimeUnit.DAY);
    specific.setVehiclesPerTimeUnit(100.0);
    source.getSubSources().add(specific);

    final EmissionSourceFeature feature = new EmissionSourceFeature();
    feature.setProperties(source);
    return feature;
  }

  private EmissionSourceFeature createColdStartSourceWithSpecificVehicle(final String vehicleCode) {
    final ColdStartEmissionSource source = new ColdStartEmissionSource();
    source.setLabel(SOURCE_LABEL);

    final SpecificVehicles specific = new SpecificVehicles();
    specific.setVehicleCode(vehicleCode);
    specific.setTimeUnit(TimeUnit.DAY);
    specific.setVehiclesPerTimeUnit(100.0);
    source.getSubSources().add(specific);

    final EmissionSourceFeature feature = new EmissionSourceFeature();
    feature.setProperties(source);
    return feature;
  }
}
