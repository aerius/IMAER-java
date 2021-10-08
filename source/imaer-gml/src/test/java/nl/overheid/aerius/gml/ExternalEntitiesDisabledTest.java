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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verfies that an XMLInputFactory has external entity references disabled.
 */
public class ExternalEntitiesDisabledTest {
  private String xmlContent;
  private String referencedText;

  @BeforeEach
  public void setUp() throws IOException, URISyntaxException {
    final String xmlFile = AssertGML.getFullPath("", "referencing", ".xml");
    try (final BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(xmlFile)))) {
      final StringBuilder builder = new StringBuilder();
      final String currentPath = File.separator
          + Paths.get(getClass().getResource(".").toURI())
              .resolve("../../../../gml/")
              .toAbsolutePath()
              .toString()
          + File.separator;

      for (String line = reader.readLine(); line != null; line = reader.readLine()) {
        builder.append(line.replace("${path}", currentPath)).append(System.lineSeparator());
      }

      xmlContent = builder.toString();
    }

    final String referencedFile = AssertGML.getFullPath("", "referenced", ".txt");
    try (final BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(referencedFile)))) {
      final StringBuilder builder = new StringBuilder();

      for (String line = reader.readLine(); line != null; line = reader.readLine()) {
        builder.append(line);
      }

      referencedText = builder.toString();
    }
  }

  /**
   * Verifies that an XMLInputFactory allows external references by default.
   *
   * @throws XMLStreamException
   *           Thrown if something went wrong while reading the file.
   */
  @Test
  public void testExternalEntitiesDefault() throws XMLStreamException {
    final XMLInputFactory factory = XMLInputFactory.newFactory();

    final String content = readXml(factory, xmlContent);

    assertNotNull(content, "No content read from xml.");
    assertEquals(referencedText, content, "Invalid content.");
  }

  /**
   * Verifies that an XMLInputFactory as returned by {@link SafeXMLInputFactory#newFactory()} disables external
   * entities.
   *
   * @throws XMLStreamException
   *           Should be thrown, as the reference to the external entity can not be resolved.
   */
  @Test
  public void testExternalEntitiesDisabled() throws XMLStreamException {
    final XMLInputFactory factory = SafeXMLInputFactory.newFactory();

    assertThrows(
        XMLStreamException.class,
        () -> readXml(factory, xmlContent),
        "Expected XMLStreamException to throw, but it didn't");
  }

  private static String readXml(final XMLInputFactory factory, final String xmlContent) throws XMLStreamException {
    XMLStreamReader reader = null;
    String content = null;

    try {
      reader = factory.createXMLStreamReader(new StringReader(xmlContent));

      boolean readCharacters = false;

      while (reader.hasNext()) {
        final int eventType = reader.next();

        if (eventType == XMLStreamReader.START_ELEMENT && "foo".equals(reader.getName().getLocalPart())) {
          readCharacters = true;
        } else if (eventType == XMLStreamReader.END_ELEMENT && "foo".equals(reader.getName().getLocalPart())) {
          readCharacters = false;
        } else if (eventType == XMLStreamReader.CHARACTERS && readCharacters) {
          content = reader.getText();
        }
      }

      return content;
    } finally {
      if (reader != null) {
        reader.close();
      }
    }
  }
}
