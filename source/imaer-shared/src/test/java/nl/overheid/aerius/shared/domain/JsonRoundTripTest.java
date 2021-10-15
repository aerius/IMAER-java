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
package nl.overheid.aerius.shared.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import nl.overheid.aerius.shared.domain.v2.geojson.FeatureCollection;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLDispersionLineFeature;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioSituation;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;

class JsonRoundTripTest {

  ObjectMapper mapper;

  @BeforeEach
  void beforeEach() {
    mapper = new ObjectMapper();
    // Use indented output for better comparison (otherwise all results are 1 line).
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    // Do not serialize null values
    mapper.setSerializationInclusion(Include.NON_EMPTY);
    // Ensure (java 8) dates and times are properly (de)serialized
    mapper.registerModule(new JavaTimeModule());
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "SourceWithPointGeometry.json",
      "SourceWithLineStringGeometry.json",
      "SourceWithPolygonGeometry.json",
  })
  void testConvertGeometries(final String fileName) throws IOException {
    final String originalJson = getFileContent(fileName);
    final EmissionSourceFeature source = mapper.readValue(originalJson, EmissionSourceFeature.class);
    final String convertedBack = mapper.writeValueAsString(source);
    assertEquals(originalJson, convertedBack + System.lineSeparator(), "Original should be same as converted");
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "GenericEmissionSource.json",
      "FarmLodgingEmissionSource.json",
      "FarmlandEmissionSource.json",
      "PlanEmissionSource.json",
      "OffRoadMobileEmissionSource.json",
      "OffRoadMobileEmissionSource_specifications.json",
      "SRM1RoadEmissionSource.json",
      "SRM1RoadEmissionSource_dynamicSegmentation.json",
      "SRM2RoadEmissionSource.json",
      "SRM2RoadEmissionSource_dynamicSegmentation.json",
      "InlandShippingEmissionSource.json",
      "MooringInlandShippingEmissionSource.json",
      "InlandMaritimeShippingEmissionSource.json",
      "MaritimeMaritimeShippingEmissionSource.json",
      "MooringMaritimeShippingEmissionSource.json"
  })
  void testConvertEmissionSourceTypes(final String fileName) throws IOException {
    final String originalJson = getFileContent(fileName);
    final EmissionSourceFeature source = mapper.readValue(originalJson, EmissionSourceFeature.class);
    final String convertedBack = mapper.writeValueAsString(source);
    assertEquals(originalJson, convertedBack + System.lineSeparator(), "Original should be same as converted");
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "SourcesCollection.json",
  })
  void testConvertCollection(final String fileName) throws IOException {
    final String originalJson = getFileContent(fileName);
    final FeatureCollection<EmissionSourceFeature> source = mapper.readValue(originalJson,
        new TypeReference<FeatureCollection<EmissionSourceFeature>>() {
        });
    final String convertedBack = mapper.writeValueAsString(source);
    assertEquals(originalJson, convertedBack + System.lineSeparator(), "Original should be same as converted");
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "ScenarioSituationSourcesOnly.json",
      "ScenarioSituationNSL.json",
      "ScenarioSituationMooringShipping.json"
  })
  void testConvertSituation(final String fileName) throws IOException {
    final String originalJson = getFileContent(fileName);
    final ScenarioSituation situation = mapper.readValue(originalJson, ScenarioSituation.class);
    final String convertedBack = mapper.writeValueAsString(situation);
    assertEquals(originalJson, convertedBack + System.lineSeparator(), "Original should be same as converted");
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "ReceptorPoint.json",
      "CustomCalculationPoint.json",
      "CustomCalculationPointWithoutResults.json",
  })
  void testConvertCalculationPointTypes(final String fileName) throws IOException {
    final String originalJson = getFileContent(fileName);
    final CalculationPointFeature calculationPoint = mapper.readValue(originalJson, CalculationPointFeature.class);
    final String convertedBack = mapper.writeValueAsString(calculationPoint);
    assertEquals(originalJson, convertedBack + System.lineSeparator(), "Original should be same as converted");
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "NSLDispersionLine.json",
  })
  void testConvertNSLDispersionLine(final String fileName) throws IOException {
    final String originalJson = getFileContent(fileName);
    final NSLDispersionLineFeature source = mapper.readValue(originalJson, NSLDispersionLineFeature.class);
    final String convertedBack = mapper.writeValueAsString(source);
    assertEquals(originalJson, convertedBack + System.lineSeparator(), "Original should be same as converted");
  }

  private String getFileContent(final String fileName) throws IOException {
    final String resourceLocation = "/json/" + fileName;
    try (InputStream inputStream = getClass().getResourceAsStream(resourceLocation)) {
      if (inputStream == null) {
        throw new FileNotFoundException("File in test resources: " + resourceLocation);
      }
      return new String(inputStream.readAllBytes());
    }
  }

}
