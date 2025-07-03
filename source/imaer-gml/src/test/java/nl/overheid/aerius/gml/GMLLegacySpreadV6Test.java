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

import java.io.IOException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import nl.overheid.aerius.shared.domain.v2.characteristics.OPSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.importer.ImportParcel;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Test class to test if support for legacy spread is correctly handled for IMAER V6 with AERIUS version 2024 and newer.
 */
class GMLLegacySpreadV6Test {

  private static final String V6_0 = "v6_0";
  private static final String LEGACY_2024 = "test_legacy_spread_2024";
  private static final String LEGACY_2025 = "test_legacy_spread_2025";

  @ParameterizedTest
  @CsvSource({
      // Legacy created with AERIUS 2024 should return calculated spread (= half height)
      LEGACY_2024 + ",40",
      // GML created with AERIUS 2025 (or newer) should return the spread set in the IMAER file.
      LEGACY_2025 + ",33"})
  void testLegacySpread2024(final String filename, final double expectedSpread) throws IOException, AeriusException {
    final ImportParcel parcel = AssertGML.getImportResult(V6_0, filename);
    final OPSSourceCharacteristics characteristics = (OPSSourceCharacteristics) parcel.getSituation().getEmissionSourcesList().get(0).getProperties()
        .getCharacteristics();

    assertEquals(expectedSpread, characteristics.getSpread(), 0.0001, "Not the expected spread for " + filename);
  }

}
