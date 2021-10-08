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

import org.junit.jupiter.api.Test;

/**
 * Test for {@link GMLIdUtil}.
 */
public class GMLIdUtilTest {

  private static final String TEST_PREFIX = "ABC";

  @Test
  public void testToValidGmlIdWithoutBackup() {
    assertEquals(TEST_PREFIX + ".1", GMLIdUtil.toValidGmlId(null, "ABC"), "ID null");
    assertEquals(TEST_PREFIX + ".1", GMLIdUtil.toValidGmlId("", "ABC"), "ID empty");
    assertEquals(TEST_PREFIX + ".324", GMLIdUtil.toValidGmlId("324", "ABC"), "ID only numbers");
    assertEquals("word", GMLIdUtil.toValidGmlId("word", "ABC"), "ID a word");
    assertEquals("Someweirdid.", GMLIdUtil.toValidGmlId("Some weird id.", "ABC"), "ID a sentence");
    assertEquals(TEST_PREFIX + ".1", GMLIdUtil.toValidGmlId(":, @, $, %, &, /, +, ,, ;", "ABC"), "ID only not allowed characters");
  }

  @Test
  public void testToValidGmlIdWithBackup() {
    assertEquals(TEST_PREFIX + ".9", GMLIdUtil.toValidGmlId(null, "ABC", "9"), "ID null");
    assertEquals(TEST_PREFIX + ".9", GMLIdUtil.toValidGmlId("", "ABC", "9"), "ID empty");
    assertEquals(TEST_PREFIX + ".324", GMLIdUtil.toValidGmlId("324", "ABC", "9"), "ID only numbers");
    assertEquals("word", GMLIdUtil.toValidGmlId("word", "ABC", "9"), "ID a word");
    assertEquals("Someweirdid.", GMLIdUtil.toValidGmlId("Some weird id.", "ABC", "9"), "ID a sentence");
    assertEquals(TEST_PREFIX + ".9", GMLIdUtil.toValidGmlId(":, @, $, %, &, /, +, ,, ;", "ABC", "9"), "ID only not allowed characters");
  }
}
