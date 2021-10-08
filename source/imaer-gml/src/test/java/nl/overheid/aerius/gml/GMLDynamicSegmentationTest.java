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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.overheid.aerius.shared.domain.v2.source.EmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.domain.v2.source.SRM2RoadEmissionSource;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.test.GMLTestDomain;

public class GMLDynamicSegmentationTest {
  private static final Logger LOG = LoggerFactory.getLogger(GMLDynamicSegmentationTest.class);
  private static final String DYNAMIC_SEGMENTATION_FILE = "test_dynamic_segmentation_road";

  private static GMLTestDomain testDomain;

  @BeforeAll
  public static void setUpBeforeClass() throws IOException {
    testDomain = new GMLTestDomain();
  }

  @Test
  public void testConvertToEmissionSources() throws IOException, AeriusException {
    final GMLReaderFactory factory = GMLReaderFactory.getFactory(AssertGML.mockGMLHelper());
    List<EmissionSourceFeature> sources = null;
    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();

    try (InputStream inputStream = AssertGML.getFileInputStream(AssertGML.PATH_LATEST_VERSION, DYNAMIC_SEGMENTATION_FILE)) {
      final GMLReader reader = factory.createReader(inputStream, true, errors, warnings);
      sources = reader.readEmissionSourceList();
      reader.enforceEmissions(sources, 2030);

    } catch (final AeriusException e) {
      LOG.error("File in error during convertToEmissionSources: " + DYNAMIC_SEGMENTATION_FILE);
      throw e;
    }

    assertTrue(errors.isEmpty(), "Expected no exceptions/warnings: " + errors);
    assertTrue(warnings.isEmpty(), "Expected no exceptions/warnings: " + warnings);
    assertEquals(1, sources.size(), "Unexpected number of sources.");
    final EmissionSource source = sources.get(0).getProperties();
    assertEquals(SRM2RoadEmissionSource.class, source.getClass(), "Invalid source type.");

    final SRM2RoadEmissionSource srm2Source = (SRM2RoadEmissionSource) source;

    assertEquals(0.5d, srm2Source.getPartialChanges().get(0).getFromPosition(), 0.00001d, "Segment start position should be 0.5");
    assertEquals(2, srm2Source.getPartialChanges().size(), "Invalid number of dynamic segments.");
  }
}
