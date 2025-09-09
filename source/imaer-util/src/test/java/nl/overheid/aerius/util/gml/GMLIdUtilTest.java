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
package nl.overheid.aerius.util.gml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.domain.v2.source.GenericEmissionSource;

/**
 * Test for {@link GMLIdUtil}.
 */
class GMLIdUtilTest {

  private static final String TEST_PREFIX = "ABC";

  @Test
  void testToValidGmlIds() {
    final EmissionSourceFeature source1 = createFeature("3", "ES.3");
    final EmissionSourceFeature source2 = createFeature("2", null);
    final List<EmissionSourceFeature> features = List.of(source1, source2);

    GMLIdUtil.toValidGmlIds(features, GMLIdUtil.SOURCE_PREFIX);
    assertEquals("ES.3", source1.getProperties().getGmlId(), "GML id should not be changed.");
    assertEquals("ES.2", source2.getProperties().getGmlId(), "GML id should be set.");
  }

  private static EmissionSourceFeature createFeature(final String id, final String gmlId) {
    final EmissionSourceFeature source = new EmissionSourceFeature();
    source.setId(id);
    source.setProperties(new GenericEmissionSource());
    source.getProperties().setGmlId(gmlId);
    return source;
  }

  @Test
  void testToValidGmlIdWithoutBackup() {
    assertEquals(TEST_PREFIX + ".1", GMLIdUtil.toValidGmlId(null, "ABC"), "ID null");
    assertEquals(TEST_PREFIX + ".1", GMLIdUtil.toValidGmlId("", "ABC"), "ID empty");
    assertEquals(TEST_PREFIX + ".324", GMLIdUtil.toValidGmlId("324", "ABC"), "ID only numbers");
    assertEquals("word", GMLIdUtil.toValidGmlId("word", "ABC"), "ID a word");
    assertEquals("Someweirdid.", GMLIdUtil.toValidGmlId("Some weird id.", "ABC"), "ID a sentence");
    assertEquals(TEST_PREFIX + ".1", GMLIdUtil.toValidGmlId(":, @, $, %, &, /, +, ,, ;", "ABC"), "ID only not allowed characters");
  }

  @Test
  void testToValidGmlIdWithBackup() {
    assertEquals(TEST_PREFIX + ".9", GMLIdUtil.toValidGmlId(null, "ABC", "9"), "ID null");
    assertEquals(TEST_PREFIX + ".9", GMLIdUtil.toValidGmlId("", "ABC", "9"), "ID empty");
    assertEquals(TEST_PREFIX + ".324", GMLIdUtil.toValidGmlId("324", "ABC", "9"), "ID only numbers");
    assertEquals("word", GMLIdUtil.toValidGmlId("word", "ABC", "9"), "ID a word");
    assertEquals("Someweirdid.", GMLIdUtil.toValidGmlId("Some weird id.", "ABC", "9"), "ID a sentence");
    assertEquals(TEST_PREFIX + ".9", GMLIdUtil.toValidGmlId(":, @, $, %, &, /, +, ,, ;", "ABC", "9"), "ID only not allowed characters");
  }
}
