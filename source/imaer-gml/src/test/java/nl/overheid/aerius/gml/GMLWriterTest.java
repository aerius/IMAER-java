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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import nl.overheid.aerius.gml.base.MetaDataInput;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.calculation.CalculationSetOptions;
import nl.overheid.aerius.shared.domain.calculation.CalculationType;
import nl.overheid.aerius.shared.domain.geo.ReceptorGridSettings;
import nl.overheid.aerius.shared.domain.result.EmissionResultKey;
import nl.overheid.aerius.shared.domain.result.EmissionResultType;
import nl.overheid.aerius.shared.domain.scenario.SituationType;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPoint;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.point.CustomCalculationPoint;
import nl.overheid.aerius.shared.domain.v2.point.ReceptorPoint;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioMetaData;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.domain.v2.source.GenericEmissionSource;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.test.GMLTestDomain;

/**
 * Test class for {@link GMLWriter}.
 */
public class GMLWriterTest {

  private static final String SOURCES_ONLY_FILE = "test_sources_only";
  // This is a copy of test_sources_only but unformatted.
  private static final String SOURCES_ONLY_FILE_UNFORMATTED = SOURCES_ONLY_FILE + "_unformatted";
  private static final String RECEPTORS_DEPOSITION_ONLY_FILE = "test_receptors_deposition_only";
  private static final String RECEPTORS_CONCENTRATION_ONLY_FILE = "test_receptors_concentration_only";
  private static final String RECEPTORS_EDGE_EFFECT_FILE = "test_receptors_edge_effect";
  private static final String RECEPTORS_ALL_FILE = "test_receptors";
  private static final String MIXED_FEATURES_FILE = "test_mixed_features";
  private static final String PATH_CURRENT_VERSION = GMLWriter.LATEST_WRITER_VERSION.name().toLowerCase() + "/";

  private static final int XCOORD_1 = GMLTestDomain.XCOORD_1;
  private static final int YCOORD_1 = GMLTestDomain.YCOORD_1;
  private static final int XCOORD_2 = GMLTestDomain.XCOORD_2;
  private static final int YCOORD_2 = GMLTestDomain.YCOORD_2;

  private static final int GML_YEAR = 2013;
  private static final String VERSION = "V1.1";
  private static final String DATABASE_VERSION = "SomeDBVersion";
  private static final String SITUATION_NAME = "Situatie 1";
  private static final SituationType SITUATION_TYPE = SituationType.PROPOSED;

  private static final ReceptorGridSettings RECEPTOR_GRID_SETTINGS = GMLTestDomain.getExampleGridSettings();

  @ParameterizedTest
  @ValueSource(strings = {SOURCES_ONLY_FILE, SOURCES_ONLY_FILE_UNFORMATTED})
  void testConvertSources(final String gmlFilename) throws IOException, AeriusException {
    final GMLWriter builder = new GMLWriter(RECEPTOR_GRID_SETTINGS, GMLTestDomain.TEST_REFERENCE_GENERATOR);
    builder.setFormattedOutput(SOURCES_ONLY_FILE.equals(gmlFilename));
    final List<EmissionSourceFeature> sources = getExampleEmissionSources();
    final String result = getConversionResult(builder, sources);
    assertFalse(result.isEmpty(), "Result shouldn't be empty");
    AssertGML.assertEqualsGML(AssertGML.getFileContent(PATH_CURRENT_VERSION, gmlFilename), result, gmlFilename);
  }

  private String getConversionResult(final GMLWriter builder, final List<EmissionSourceFeature> sources) throws IOException, AeriusException {
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
      builder.writeEmissionSources(bos, sources, getMetaDataInput(getScenarioMetaData()));
      return bos.toString(StandardCharsets.UTF_8.name());
    }
  }

  @Test
  void testConvertInvalidSources() throws IOException, AeriusException {
    final GMLWriter converter = new GMLWriter(RECEPTOR_GRID_SETTINGS, GMLTestDomain.TEST_REFERENCE_GENERATOR);
    final List<EmissionSourceFeature> sources1 = getExampleEmissionSources();
    sources1.get(0).setGeometry(null);

    final IllegalArgumentException e = assertThrows(
        IllegalArgumentException.class,
        () -> getConversionResult(converter, sources1),
        "Emissionsource not allowed to have no geometry.");

    final List<EmissionSourceFeature> sources2 = getExampleEmissionSources();
    ((GenericEmissionSource) sources2.get(0).getProperties()).getEmissions().clear();
    //source has no emission, it'll just be exported with 0.0 emissions for all substances.
    //this used to be an error situation, now it's treated as a work-in-progress export.
    assertNotNull(getConversionResult(converter, sources2), "Expect source to be written to file while having no emissions");
  }

  @Test
  void testConvertMetaData() throws IOException, AeriusException {
    final GMLWriter writer = new GMLWriter(RECEPTOR_GRID_SETTINGS, r -> Optional.of("test"));
    final ScenarioMetaData metaData = getScenarioMetaData();
    final String originalReference = metaData.getReference();
    final List<EmissionSourceFeature> sourceList = new ArrayList<>();
    final GMLScenario scenario = GMLScenario.Builder.create(SITUATION_NAME, SituationType.PROPOSED)
        .sources(sourceList)
        .build();
    String result;
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
      final MetaDataInput metaDataInput = getMetaDataInput(metaData);

      writer.write(bos, scenario, metaDataInput);
      result = bos.toString(StandardCharsets.UTF_8.name());
    }
    validateDefaultMetaDataFields(result, metaData);
    assertFalse(result.contains("<imaer:temporaryPeriod>"), "Shouldn't contain temporaryPeriod");
    assertFalse(result.contains("<imaer:permitCalculationRadiusType>"), "Shouldn't contain PermitCalculationRadiusType");
    assertNotEquals(originalReference, metaData.getReference(), "Reference should be overwritten");
    assertTrue(result.contains(getExpectedElement("reference", metaData.getReference())), "Should contain new generated reference");
  }

  private void validateDefaultMetaDataFields(final String result, final ScenarioMetaData metaData) {
    assertTrue(result.contains(getExpectedElement("aeriusVersion", VERSION)), "Should contain version");
    assertTrue(result.contains(getExpectedElement("databaseVersion", DATABASE_VERSION)), "Should contain databaseVersion");
    assertTrue(result.contains(getExpectedElement("year", String.valueOf(GML_YEAR))), "Should contain year");
    assertTrue(result.contains(getExpectedElement("name", SITUATION_NAME)), "Should contain situationName");
    assertTrue(result.contains(getExpectedElement("corporation", metaData.getCorporation())), "Should contain corporation");
    assertTrue(result.contains(getExpectedElement("name", metaData.getProjectName())), "Should contain projectName");
    assertTrue(result.contains(getExpectedElement("description", metaData.getDescription())), "Should contain description");
    assertTrue(result.contains(getExpectedElement("streetAddress", metaData.getStreetAddress())), "Should contain streetAddress");
    assertTrue(result.contains(getExpectedElement("postcode", metaData.getPostcode())), "Should contain postcode");
    assertTrue(result.contains(getExpectedElement("city", metaData.getCity())), "Should contain city");
    assertTrue(result.contains("<imaer:reference>"), "Should contain reference tag");
  }

  private String getExpectedElement(final String element, final String value) {
    return "<imaer:" + element + ">" + value + "</imaer:" + element + ">";
  }

  private MetaDataInput getMetaDataInput(final ScenarioMetaData scenarioMetaData) {
    final MetaDataInput metaDataInput = new MetaDataInput();
    metaDataInput.setScenarioMetaData(scenarioMetaData);
    metaDataInput.setYear(GML_YEAR);
    metaDataInput.setName(SITUATION_NAME);
    metaDataInput.setSituationType(SITUATION_TYPE);
    metaDataInput.setVersion(VERSION);
    metaDataInput.setDatabaseVersion(DATABASE_VERSION);
    metaDataInput.setOptions(getCalculationOptions());
    return metaDataInput;
  }

  private CalculationSetOptions getCalculationOptions() {
    final CalculationSetOptions options = new CalculationSetOptions();
    options.setCalculationType(CalculationType.NATURE_AREA);
    options.setCalculateMaximumRange(3);
    options.getRblCalculationOptions().setMonitorSrm2Year(2030);
    options.getSubstances().add(Substance.NOX);
    options.getSubstances().add(Substance.NH3);
    options.getSubstances().add(Substance.NO2);
    for (final Substance substance : options.getSubstances()) {
      options.getEmissionResultKeys().add(EmissionResultKey.safeValueOf(substance, EmissionResultType.CONCENTRATION));
      options.getEmissionResultKeys().add(EmissionResultKey.safeValueOf(substance, EmissionResultType.DEPOSITION));
    }
    return options;
  }

  private List<EmissionSourceFeature> getExampleEmissionSources() {
    final List<EmissionSourceFeature> sources = new ArrayList<>();

    final Point point1 = new Point(XCOORD_1, YCOORD_1);
    final GenericEmissionSource emissionSource1 = new GenericEmissionSource();
    final EmissionSourceFeature sourceFeature1 = GMLTestDomain.getSource(1, point1, "SomeSource", emissionSource1);
    emissionSource1.getEmissions().put(Substance.NH3, 40020.300);
    emissionSource1.getEmissions().put(Substance.NOX, 10098.3242);
    emissionSource1.setCharacteristics(GMLTestDomain.getNonDefaultCharacteristics());
    sources.add(sourceFeature1);

    final Point point2 = new Point(XCOORD_2, YCOORD_2);
    final GenericEmissionSource emissionSource2 = new GenericEmissionSource();
    final EmissionSourceFeature sourceFeature2 = GMLTestDomain.getSource(2, point2, "ëa?e:(é", emissionSource2);
    emissionSource2.getEmissions().put(Substance.NH3, 7123.11);

    sources.add(sourceFeature2);

    return sources;
  }

  private static Stream<Arguments> convertReceptorsData() {
    return Stream.of(
        Arguments.of(RECEPTORS_ALL_FILE, true, true, false),
        Arguments.of(RECEPTORS_DEPOSITION_ONLY_FILE, true, false, false),
        Arguments.of(RECEPTORS_CONCENTRATION_ONLY_FILE, false, true, false),
        Arguments.of(RECEPTORS_EDGE_EFFECT_FILE, true, true, true));
  }

  @ParameterizedTest(name = "Testfile: {0}")
  @MethodSource("convertReceptorsData")
  void testConvertReceptors(final String receptorFile, final boolean includeDeposition, final boolean includeConcentration,
      final boolean includeOverlapping) throws IOException, AeriusException {
    final ArrayList<CalculationPointFeature> receptors = getExampleAeriusPoints(includeDeposition, includeConcentration, includeOverlapping);
    final MetaDataInput metaDataInput = getMetaDataInput(new ScenarioMetaData());
    metaDataInput.setName(null);
    metaDataInput.setSituationType(null);
    metaDataInput.setResultsIncluded(true);
    final GMLWriter writer = new GMLWriter(RECEPTOR_GRID_SETTINGS, GMLTestDomain.TEST_REFERENCE_GENERATOR);
    String result;
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
      writer.writeAeriusPoints(bos, receptors, metaDataInput);
      result = bos.toString(StandardCharsets.UTF_8.name());
    }
    assertFalse(result.isEmpty(), "Result shouldn't be empty for " + receptorFile);
    AssertGML.assertEqualsGML(AssertGML.getFileContent(PATH_CURRENT_VERSION, receptorFile), result, receptorFile);
  }

  private ArrayList<CalculationPointFeature> getExampleAeriusPoints(final boolean includeDeposition, final boolean includeConcentration,
      final boolean includeOverlapping) {
    final ArrayList<CalculationPointFeature> receptors = new ArrayList<>();

    final int xCoord1 = XCOORD_1 + 1000;
    final int yCoord1 = YCOORD_1 + 1000;
    final CalculationPointFeature feature1 = new CalculationPointFeature();
    final Point point1 = new Point(xCoord1, yCoord1);
    feature1.setGeometry(point1);
    final ReceptorPoint calculationPoint1 = new ReceptorPoint();
    calculationPoint1.setReceptorId(1);
    if (includeDeposition) {
      calculationPoint1.getResults().put(EmissionResultKey.NH3_DEPOSITION, 8546.77);
      calculationPoint1.getResults().put(EmissionResultKey.NOX_DEPOSITION, 968.3);
    }
    if (includeConcentration) {
      calculationPoint1.getResults().put(EmissionResultKey.NH3_CONCENTRATION, 95.8);
      calculationPoint1.getResults().put(EmissionResultKey.NOX_CONCENTRATION, 3.001);
    }
    if (includeOverlapping) {
      calculationPoint1.setEdgeEffect(false);
    }
    feature1.setProperties(calculationPoint1);
    receptors.add(feature1);

    final int xCoord2 = XCOORD_2 - 1000;
    final int yCoord2 = YCOORD_2 + 1000;
    final CalculationPointFeature feature2 = new CalculationPointFeature();
    final Point point2 = new Point(xCoord2, yCoord2);
    feature2.setGeometry(point2);
    final CalculationPoint calculationPoint2;
    if (includeOverlapping) {
      final ReceptorPoint actualPoint2 = new ReceptorPoint();
      actualPoint2.setReceptorId(2);
      actualPoint2.setEdgeEffect(true);
      calculationPoint2 = actualPoint2;
    } else {
      final CustomCalculationPoint actualPoint2 = new CustomCalculationPoint();
      actualPoint2.setCustomPointId(2);
      actualPoint2.setLabel("DB-team 1e depositie");
      calculationPoint2 = actualPoint2;
    }
    if (includeConcentration && includeDeposition) {
      calculationPoint2.getResults().put(EmissionResultKey.NOX_CONCENTRATION, 98.4);
      calculationPoint2.getResults().put(EmissionResultKey.NO2_CONCENTRATION, 12595.2);
      calculationPoint2.getResults().put(EmissionResultKey.NOX_DEPOSITION, 49.2);
      calculationPoint2.getResults().put(EmissionResultKey.NH3_CONCENTRATION, 1574.4);
      calculationPoint2.getResults().put(EmissionResultKey.NH3_DEPOSITION, 787.2);
      calculationPoint2.getResults().put(EmissionResultKey.PM10_CONCENTRATION, 50380.8);
      calculationPoint2.getResults().put(EmissionResultKey.PM25_CONCENTRATION, 201523.2);
    } else if (includeDeposition) {
      calculationPoint2.getResults().put(EmissionResultKey.NH3_DEPOSITION, 110.66);
      calculationPoint2.getResults().put(EmissionResultKey.NOX_DEPOSITION, 89.11);
    } else if (includeConcentration) {
      calculationPoint2.getResults().put(EmissionResultKey.NH3_CONCENTRATION, 80.0);
      calculationPoint2.getResults().put(EmissionResultKey.NOX_CONCENTRATION, 0.001);
    }
    feature2.setProperties(calculationPoint2);
    receptors.add(feature2);

    final int xCoord3 = xCoord2 - 10;
    final int yCoord3 = yCoord2 - 60;
    final CalculationPointFeature feature3 = new CalculationPointFeature();
    final Point point3 = new Point(xCoord3, yCoord3);
    feature3.setGeometry(point3);
    final CustomCalculationPoint calculationPoint3 = new CustomCalculationPoint();
    calculationPoint3.setCustomPointId(3);
    calculationPoint3.setLabel("DB-team 2e depositie");
    feature3.setProperties(calculationPoint3);
    receptors.add(feature3);

    return receptors;
  }

  @Test
  void testConvertMixedFeatures() throws IOException, AeriusException {
    final GMLWriter builder = new GMLWriter(RECEPTOR_GRID_SETTINGS, GMLTestDomain.TEST_REFERENCE_GENERATOR);
    final List<EmissionSourceFeature> emissionSources = getExampleEmissionSources();
    final ArrayList<CalculationPointFeature> receptors = getExampleAeriusPoints(true, false, false);
    String result;
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
      final MetaDataInput metaDataInput = getMetaDataInput(getScenarioMetaData());
      metaDataInput.setResultsIncluded(true);
      final GMLScenario scenario = GMLScenario.Builder.create(SITUATION_NAME, SituationType.PROPOSED)
          .sources(emissionSources)
          .calculationPoints(receptors)
          .build();
      builder.write(bos, scenario, metaDataInput);
      result = bos.toString(StandardCharsets.UTF_8.name());
    }
    assertFalse(result.isEmpty(), "Result shouldn't be empty");
    AssertGML.assertEqualsGML(AssertGML.getFileContent(PATH_CURRENT_VERSION, MIXED_FEATURES_FILE), result, MIXED_FEATURES_FILE);
  }

  private ScenarioMetaData getScenarioMetaData() {
    final ScenarioMetaData metaData = new ScenarioMetaData();
    metaData.setReference("SomeReference001");
    metaData.setCorporation("Big Corp");
    metaData.setProjectName("SomeProject");
    metaData.setDescription("SomeFunkyDescription");
    metaData.setStreetAddress("SomeStreet");
    metaData.setCity("SomeCity");
    metaData.setPostcode("somePostalCode");
    return metaData;
  }

}
