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
package nl.overheid.aerius.gml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.EnumSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import nl.overheid.aerius.gml.base.GMLHelper;
import nl.overheid.aerius.importer.ImaerImporter;
import nl.overheid.aerius.importer.ImportOption;
import nl.overheid.aerius.shared.domain.v2.importer.ImportParcel;
import nl.overheid.aerius.shared.domain.v2.source.SRM2RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.CustomVehicles;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Integration test verifying that ImaerImporter calls convertRemovedVehicleCodes during import.
 */
class RemovedVehicleCodeImportTest {

  private static final String REMOVED_CODE = "BA-B-E3";

  @Test
  void testRemovedVehicleCodeIsConvertedDuringImport() throws IOException, AeriusException {
    final GMLHelper mockHelper = AssertGML.mockGMLHelper();
    when(mockHelper.getRemovedVehicleCodes()).thenReturn(Set.of(REMOVED_CODE));

    // Create fresh factory with our mock helper (not cached) so getRemovedVehicleCodes is used
    final GMLReaderFactory factory = new GMLReaderFactory(mockHelper);
    final ImaerImporter importer = new ImaerImporter(mockHelper, factory);
    final ImportParcel result = new ImportParcel();

    try (final InputStream inputStream = AssertGML.getFileInputStream(
        AssertGML.PATH_LATEST_VERSION + "roundtrip", "road_specific_and_custom")) {
      importer.importStream(inputStream, EnumSet.of(ImportOption.INCLUDE_SOURCES), result);
    }

    assertEquals(0, result.getExceptions().size(), "Expected no exceptions");
    assertTrue(result.getWarnings().stream()
        .anyMatch(w -> w.getReason() == ImaerExceptionReason.GML_UNKNOWN_MOBILE_SOURCE_CODE),
        "Expected warning for removed vehicle code");

    final SRM2RoadEmissionSource roadSource =
        (SRM2RoadEmissionSource) result.getSituation().getEmissionSourcesList().get(0).getProperties();
    final CustomVehicles converted = assertInstanceOf(CustomVehicles.class, roadSource.getSubSources().get(0));
    assertEquals("Voormalig " + REMOVED_CODE, converted.getDescription());
  }

}
