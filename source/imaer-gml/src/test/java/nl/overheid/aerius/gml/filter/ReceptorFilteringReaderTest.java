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
package nl.overheid.aerius.gml.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.CharBuffer;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Unit tests for {@link ReceptorFilteringReader}.
 */
class ReceptorFilteringReaderTest {

  @ParameterizedTest
  @MethodSource("provideFilteringTestCases")
  void testFiltering(final String input, final String expected, final String description) throws IOException {
    try (final ReceptorFilteringReader reader = new ReceptorFilteringReader(new StringReader(input))) {
      final String result = readAll(reader);
      assertEquals(expected, result, description);
    }
  }

  static Stream<Arguments> provideFilteringTestCases() {
    final String sourceBlock = "<imaer:featureMember><imaer:Source>sourceContent</imaer:Source></imaer:featureMember>";
    final String receptorBlock = "<imaer:featureMember>\n<imaer:ReceptorPoint>receptorContent</imaer:ReceptorPoint>\n</imaer:featureMember>";

    return Stream.of(
        Arguments.of(
            "<imaer:featureMember><imaer:Source>content</imaer:Source></imaer:featureMember>",
            "<imaer:featureMember><imaer:Source>content</imaer:Source></imaer:featureMember>",
            "Input without ReceptorPoint should not be filtered"),
        Arguments.of(
            "<imaer:featureMember>\n<imaer:ReceptorPoint>content</imaer:ReceptorPoint>\n</imaer:featureMember>",
            "",
            "Single receptor block should be completely filtered out"),
        Arguments.of(
            "<imaer:featureMember>\n<imaer:ReceptorPoint>content1</imaer:ReceptorPoint>\n</imaer:featureMember><imaer:featureMember>\n<imaer:ReceptorPoint>content2</imaer:ReceptorPoint>\n</imaer:featureMember>",
            "",
            "Multiple receptor blocks should all be filtered out"),
        Arguments.of(
            sourceBlock + receptorBlock + sourceBlock,
            sourceBlock + sourceBlock,
            "Only receptor blocks should be filtered, source blocks should remain"),
        Arguments.of(
            "<imaer:featureMember>\n<imaer:ReceptorPoint receptorPointId=\"1\" gml:id=\"CP.1\">content</imaer:ReceptorPoint>\n</imaer:featureMember>",
            "",
            "Receptor blocks with attributes should be filtered out"),
        Arguments.of(
            "<IMAER:featureMember>\n<IMAER:RECEPTORPOINT>content</IMAER:RECEPTORPOINT>\n</IMAER:featureMember>",
            "",
            "Case-insensitive matching should filter uppercase receptor blocks"),
        Arguments.of(
            "",
            "",
            "Empty input should produce empty output"),
        Arguments.of(
            "<imaer:featureMember>\n<imaer:ReceptorPoint>\n<imaer:identifier><imaer:NEN3610ID><imaer:namespace>NL.IMAER</imaer:namespace><imaer:localId>CP.1</imaer:localId></imaer:NEN3610ID></imaer:identifier><imaer:GM_Point><gml:Point><gml:pos>137558.0 456251.0</gml:pos></gml:Point></imaer:GM_Point><imaer:result><imaer:CalculationResult><imaer:value>95.8</imaer:value></imaer:CalculationResult></imaer:result></imaer:ReceptorPoint>\n</imaer:featureMember>",
            "",
            "Receptor blocks with nested content should be completely filtered out"));
  }

  @Test
  void testSingleCharRead() throws IOException {
    final String input = "<imaer:featureMember><imaer:Source>X</imaer:Source></imaer:featureMember>";

    try (final ReceptorFilteringReader reader = new ReceptorFilteringReader(new StringReader(input))) {
      final StringBuilder result = new StringBuilder();
      int c;
      while ((c = reader.read()) != -1) {
        result.append((char) c);
      }
      assertEquals(input, result.toString(), "Single character read should produce original input when no filtering occurs");
    }
  }

  @Test
  void testReadIntoCharBuffer() throws IOException {
    final String input = "<imaer:featureMember><imaer:Source>content</imaer:Source></imaer:featureMember>";

    try (final ReceptorFilteringReader reader = new ReceptorFilteringReader(new StringReader(input))) {
      final CharBuffer buffer = CharBuffer.allocate(input.length());
      final int read = reader.read(buffer);
      buffer.flip();
      assertEquals(input.length(), read, "Should read all characters when no filtering occurs");
      assertEquals(input, buffer.toString(), "CharBuffer content should match original input");
    }
  }

  @Test
  void testSkip() throws IOException {
    final String input = "<imaer:featureMember><imaer:Source>content</imaer:Source></imaer:featureMember>";

    try (final ReceptorFilteringReader reader = new ReceptorFilteringReader(new StringReader(input))) {
      final long skipped = reader.skip(10);
      assertEquals(10, skipped, "Should skip exactly 10 characters");

      final String remaining = readAll(reader);
      assertEquals(input.substring(10), remaining, "Remaining content after skip should match input substring");
    }
  }

  @Test
  void testReady() throws IOException {
    final String input = "test";

    try (final ReceptorFilteringReader reader = new ReceptorFilteringReader(new StringReader(input))) {
      assertTrue(reader.ready(), "Reader should be ready when there is data available");
    }
  }

  @Test
  void testMarkNotSupported() throws IOException {
    try (final ReceptorFilteringReader reader = new ReceptorFilteringReader(new StringReader("test"))) {
      assertThrows(IOException.class, () -> reader.mark(10), "mark() should throw IOException as it's not supported");
      assertThrows(IOException.class, reader::reset, "reset() should throw IOException as it's not supported");
    }
  }

  @Test
  void testClose() throws IOException {
    final StringReader source = new StringReader("test");
    final ReceptorFilteringReader reader = new ReceptorFilteringReader(source);

    reader.close();

    // Should throw IOException when trying to read after close
    assertThrows(IOException.class, reader::read, "Reading from closed reader should throw IOException");
  }

  @Test
  void testComplexGMLStructure() throws IOException {
    final String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<imaer:FeatureCollection>\n";
    final String metadata = "<imaer:metadata><imaer:ProjectMetadata><imaer:year>2024</imaer:year></imaer:ProjectMetadata></imaer:metadata>\n";
    final String sourceBlock = "<imaer:featureMember><imaer:EmissionSource><imaer:label>Source1</imaer:label></imaer:EmissionSource></imaer:featureMember>\n";
    final String receptorBlock = "<imaer:featureMember>\n<imaer:ReceptorPoint receptorPointId=\"1\">\n<imaer:result>value</imaer:result>\n</imaer:ReceptorPoint>\n</imaer:featureMember>\n";
    final String footer = "</imaer:FeatureCollection>";

    final String input = header + metadata + sourceBlock + receptorBlock + sourceBlock + footer;

    try (final ReceptorFilteringReader reader = new ReceptorFilteringReader(new StringReader(input))) {
      final String result = readAll(reader);
      // Verify receptor block is removed and source blocks are preserved
      assertFalse(result.contains("ReceptorPoint"), "ReceptorPoint should be filtered out");
      assertTrue(result.contains("Source1"), "Source1 should be preserved");
      assertTrue(result.contains("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"), "Header should be preserved");
      assertTrue(result.contains("</imaer:FeatureCollection>"), "Footer should be preserved");
      // Count source blocks - should have 2
      final int sourceCount = result.split("EmissionSource").length - 1;
      assertEquals(2, sourceCount / 2, "Should have 2 source blocks (opening and closing tags)");
    }
  }

  private String readAll(final Reader reader) throws IOException {
    final StringBuilder result = new StringBuilder();
    final char[] buffer = new char[1024];
    int read;
    while ((read = reader.read(buffer)) != -1) {
      result.append(buffer, 0, read);
    }
    return result.toString();
  }
}
