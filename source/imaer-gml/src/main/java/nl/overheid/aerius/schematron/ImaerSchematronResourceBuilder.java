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
package nl.overheid.aerius.schematron;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import nl.overheid.aerius.gml.GMLWriter;
import nl.overheid.aerius.gml.base.GMLSchema;

/**
 * Builds a complete schematron validation schema given a list of schema patterns to validate.
 */
final class ImaerSchematronResourceBuilder {

  private static final String SCHEMATRON_TEMPLATE_FILES_DIRECTORY = "schemas/schematron/";
  private static final String HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
      + "<sch:schema xmlns:sch=\"http://purl.oclc.org/dsdl/schematron\" queryBinding=\"xslt2\" >"
      + "<sch:title>IMAER Schematron Validation</sch:title>"
      + "<sch:ns prefix=\"imaer\" uri=\"" + GMLWriter.LATEST_WRITER_NAMESPACE + "\"/>"
      + "<sch:ns prefix=\"gml\" uri=\"" + GMLSchema.NAMESPACE + "\"/>";
  private static final String FOOTER = "</sch:schema>";

  private static final String SCHEMATRON_TEMPLATE = "aerius-schematron-%s.sch-template";

  private ImaerSchematronResourceBuilder() {
    // Util class
  }

  /**
   * Returns a complete Schematron XML file.
   *
   * @param validations List of patterns to create a schematron validator for
   * @return Schematron XML file
   * @throws IOException
   */
  public static String buildSchematron(final List<ImaerSchematronValidation> validations) throws IOException {
    final StringBuilder builder = new StringBuilder();

    builder.append(HEADER);
    for (final ImaerSchematronValidation validation : validations) {
      final String filename = String.format(SCHEMATRON_TEMPLATE, validation.name().toLowerCase(Locale.ENGLISH));
      final String schematronContent = getResourceFileAsString(SCHEMATRON_TEMPLATE_FILES_DIRECTORY + filename);

      builder.append(schematronContent);
    }
    builder.append(FOOTER);
    return builder.toString();
  }

  /**
   * Reads given resource file as a string.
   *
   * @param fileName path to the resource file
   * @return the file's contents
   * @throws IOException if read fails for any reason
   */
  static String getResourceFileAsString(final String fileName) throws IOException {
    final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    try (InputStream is = classLoader.getResourceAsStream(fileName);
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(isr)) {
      return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }
  }
}
