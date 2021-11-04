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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.overheid.aerius.gml.base.GMLHelper;
import nl.overheid.aerius.gml.base.GMLLegacyCodeConverter.Conversion;
import nl.overheid.aerius.gml.base.GMLLegacyCodeConverter.GMLLegacyCodeType;
import nl.overheid.aerius.gml.base.GMLVersionReaderFactory;
import nl.overheid.aerius.gml.base.MetaData;
import nl.overheid.aerius.gml.base.MetaDataInput;
import nl.overheid.aerius.gml.v0_5.GMLReaderFactoryV05;
import nl.overheid.aerius.shared.domain.calculation.CalculationType;
import nl.overheid.aerius.shared.domain.geo.ReceptorGridSettings;
import nl.overheid.aerius.shared.domain.scenario.SituationType;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioMetaData;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.test.GMLTestDomain;
import nl.overheid.aerius.util.ImaerFileUtil;

/**
 * Tests the basic cases for GML which will (most likely) hardly change.
 */
public class GMLReaderTest {
  private static final Logger LOG = LoggerFactory.getLogger(GMLReaderTest.class);

  private static final String USED_EXTENSION = ".gml";
  private static final String SOURCES_ONLY_FILE = "test_sources_only";
  private static final String EMPTY_METADATA_FILE = "test_sources_empty_metadata";
  private static final String RECEPTORS_FILE = "test_receptors";
  private static final String MIXED_FEATURES_FILE = "test_mixed_features";
  private static final String OLD_CODES_FILE = "test_old_codes";
  private static final String UNROUNDED_CALCPOINT_FILE = "test_calcpoint_rounding";
  private static final String METADATA_FILE = "test_metadata";

  private static final String PATH_ALL = "src/test/resources/gml/";

  private static final int GML_YEAR = 2013;
  private static final String VERSION = "V1.1";
  private static final String DATABASE_VERSION = "SomeDBVersion";
  private static final String SITUATION_NAME = "Situatie 1";
  private static final SituationType SITUATION_TYPE = SituationType.PROPOSED;
  private static final Integer MONITOR_SRM2_YEAR = 2030;

  private static GMLTestDomain testDomain;
  private static ReceptorGridSettings gridSettings;

  @BeforeAll
  public static void setUpBeforeClass() throws IOException {
    testDomain = new GMLTestDomain();
    gridSettings = GMLTestDomain.getExampleGridSettings();
  }

  @Test
  public void testConvertToEmissionSources() throws IOException, AeriusException {
    final File file = getFile(AssertGML.PATH_LATEST_VERSION, SOURCES_ONLY_FILE);
    final List<EmissionSourceFeature> sources = getEmissionSourcesFromFile(file);
    assertNotNull(sources, "Emission sources");
    assertEquals(2, sources.size(), "Number of sources");
  }

  @Test
  public void testConvertToEmissionSourcesWithoutMetadata() throws IOException, AeriusException {
    final File file = getFile(AssertGML.PATH_LATEST_VERSION, EMPTY_METADATA_FILE);
    final List<EmissionSourceFeature> sources = getEmissionSourcesFromFile(file);
    assertNotNull(sources, "Emission sources without meta data");
    assertEquals(2, sources.size(), "Number of sources");
  }

  @Test
  public void testConvertToAeriusPoints() throws IOException, AeriusException {
    final File file = getFile(AssertGML.PATH_LATEST_VERSION, RECEPTORS_FILE);
    final List<CalculationPointFeature> points = getAeriusPointsFromFile(file, true);
    assertNotNull(points, "Receptor points");
    assertEquals(3, points.size(), "Number of receptor points");
  }

  @Test
  public void testCalcPointToUnroundedAeriusPoint() throws IOException, AeriusException {
    final List<File> files = getFiles(PATH_ALL, UNROUNDED_CALCPOINT_FILE);
    for (final File file : files) {
      final List<CalculationPointFeature> points = getAeriusPointsFromFile(file, false);
      assertNotNull(points, "Calculation points");
      assertEquals(1, points.size(), "Number of calculation points");
      final double theX = points.get(0).getGeometry().getX();
      assertEquals(71902.123456, theX, 0d, "The x coordinate shouldn't have been rounded");
      final double theY = points.get(0).getGeometry().getY();
      assertEquals(450141.123456, theY, 0d, "The y coordinate shouldn't have been rounded");
    }
  }

  @Test
  public void testConvertFromMultipleFeatures() throws IOException, AeriusException {
    final File file = getFile(AssertGML.PATH_LATEST_VERSION, MIXED_FEATURES_FILE);
    final List<CalculationPointFeature> points = getAeriusPointsFromFile(file, true);
    assertNotNull(points, "Receptor points");
    assertEquals(3, points.size(), "Number of receptor points");

    final List<EmissionSourceFeature> sources = getEmissionSourcesFromFile(file);
    assertNotNull(sources, "Emission sources");
    assertEquals(2, sources.size(), "Number of sources");
  }

  @Test
  public void testConvertMetaData() throws AeriusException, IOException {
    final File file = getFile(AssertGML.PATH_LATEST_VERSION, MIXED_FEATURES_FILE);
    final GMLReader reader = getGMLReaderFromFile(file);
    final MetaData expectedMetaData = getMetaData();
    final GMLMetaDataReader metaDataReader = reader.metaDataReader();
    assertEquals(expectedMetaData.getYear().intValue(), metaDataReader.readYear(), "Metadata year");
    assertEquals(expectedMetaData.getVersion(), metaDataReader.readAeriusVersion(), "Metadata version");
    assertEquals(expectedMetaData.getDatabaseVersion(), metaDataReader.readDatabaseVersion(), "Metadata DB version");
    assertEquals(expectedMetaData.getTemporaryPeriod(), metaDataReader.readTemporaryProjectPeriodYear(), "Metadata temporary period");
    final ScenarioMetaData MetaData = metaDataReader.readMetaData();
    assertEquals(expectedMetaData.getProjectName(), MetaData.getProjectName(), "Metadata name");
    assertEquals(expectedMetaData.getReference(), MetaData.getReference(), "Metadata reference");
    assertEquals(expectedMetaData.getCorporation(), MetaData.getCorporation(), "Metadata corporation");
    assertEquals(expectedMetaData.getDescription(), MetaData.getDescription(), "Metadata description");
  }

  @Test
  public void testMetaData() throws IOException, AeriusException {
    final File file = getFile(AssertGML.PATH_LATEST_VERSION, METADATA_FILE);
    final GMLReader reader = getGMLReaderFromFile(file);
    final GMLMetaDataReader metaDataReader = reader.metaDataReader();
    assertNotNull(metaDataReader, "Should have meta data reader");
  }

  @Test
  public void testConvertFromOldCodes() throws IOException, AeriusException {
    final File file = getFile("v0_5/", OLD_CODES_FILE);
    final Map<GMLLegacyCodeType, Map<String, Conversion>> codeMaps = new HashMap<>();

    final Map<String, Conversion> offRoadMobileSourceMap = new HashMap<>();
    offRoadMobileSourceMap.put("101", new Conversion("BA-B-E3", true));
    codeMaps.put(GMLLegacyCodeType.ON_ROAD_MOBILE_SOURCE, offRoadMobileSourceMap);
    final GMLHelper gmlHelper = mockGmlHelper();
    final GMLReaderFactoryV05 gmlReaderFactoryV05 = new GMLReaderFactoryV05(gmlHelper);
    final GMLReaderFactory factory = new GMLReaderFactory(gmlHelper, new GMLReaderProxy(gmlHelper) {
      @Override
      public GMLVersionReaderFactory determineReaderFactory(final NamespaceContext nameSpaceContext) throws AeriusException {
        return gmlReaderFactoryV05;
      }
    }, gridSettings);
    final List<EmissionSourceFeature> sources;
    final ArrayList<AeriusException> warnings = new ArrayList<>();
    try (InputStream inputStream = new FileInputStream(file)) {
      final GMLReader reader = factory.createReader(inputStream, true, warnings, warnings);
      sources = reader.readEmissionSourceList();
      reader.enforceEmissions(sources, 2030);
    } catch (final AeriusException e) {
      LOG.error("File in error during convertToCollection: " + file);
      throw e;
    }

    assertNotNull(sources, "Emission sources");
    //4 conversions, but 2 shouldn't issue a warning.
    assertEquals(1, warnings.size(), "Expected warnings");
    for (final EmissionSourceFeature source : sources) {
      if ("IncorrectSector".equals(source.getProperties().getLabel())) {
        assertEquals(1400, source.getProperties().getSectorId(), "Sector ID");
      }
    }
  }

  private MetaData getMetaData() throws AeriusException {
    final ScenarioMetaData metaData = new ScenarioMetaData();
    metaData.setReference("SomeReference001");
    metaData.setCorporation("Big Corp");
    metaData.setProjectName("SomeProject");
    metaData.setDescription("SomeFunkyDescription");
    final MetaDataInput metaDataInput = new MetaDataInput();
    metaDataInput.setScenarioMetaData(metaData);
    metaDataInput.setYear(GML_YEAR);
    metaDataInput.setName(SITUATION_NAME);
    metaDataInput.setSituationType(SITUATION_TYPE);
    metaDataInput.setVersion(VERSION);
    metaDataInput.setDatabaseVersion(DATABASE_VERSION);
    metaDataInput.getOptions().setCalculationType(CalculationType.PERMIT);
    metaDataInput.getOptions().setMonitorSrm2Year(MONITOR_SRM2_YEAR);
    metaDataInput.setResultsIncluded(true);
    final InternalGMLWriter writer = new InternalGMLWriter(gridSettings, GMLTestDomain.TEST_REFERENCE_GENERATOR);
    return writer.getWriter().metaData2GML(metaDataInput);
  }

  @Test
  public void testConvertAllVersions() throws IOException, AeriusException {
    //Test mainly checks if a file can be imported, and some small checks on contents of the import.
    //To be flexible with adding new (complex) cases, this is quite minimal.
    List<File> files = getFiles(PATH_ALL, SOURCES_ONLY_FILE);
    for (final File file : files) {
      final List<EmissionSourceFeature> sources = getEmissionSourcesFromFile(file);
      assertNotNull(sources, "Emission sources for " + file);
      assertEquals(2, sources.size(), "Number of sources for " + file);
    }
    files = getFiles(PATH_ALL, EMPTY_METADATA_FILE);
    for (final File file : files) {
      final List<EmissionSourceFeature> sources = getEmissionSourcesFromFile(file);
      assertNotNull(sources, "Emission sources for " + file);
      assertEquals(2, sources.size(), "Number of sources for " + file);
    }
    files = getFiles(PATH_ALL, RECEPTORS_FILE);
    for (final File file : files) {
      final List<CalculationPointFeature> points = getAeriusPointsFromFile(file, true);
      assertNotNull(points, "Receptor points");
      assertEquals(3, points.size(), "Number of receptor points");

    }
    files = getFiles(PATH_ALL, MIXED_FEATURES_FILE);
    for (final File file : files) {
      final List<CalculationPointFeature> points = getAeriusPointsFromFile(file, true);
      assertNotNull(points, "Receptor points");
      assertEquals(3, points.size(), "Number of receptor points");
      assertFalse(points.get(0).getProperties().getResults().isEmpty(), "Results included");
      final List<EmissionSourceFeature> sources = getEmissionSourcesFromFile(file);
      assertNotNull(sources, "Emission sources for " + file);
      assertEquals(2, sources.size(), "Number of sources for " + file);
    }
  }

  private File getFile(final String relativePath, final String fileName) throws FileNotFoundException {
    final URL url = AssertGML.getFileResource(relativePath, fileName);
    if (url == null) {
      throw new FileNotFoundException(relativePath + fileName);
    }
    final File file = new File(url.getFile());
    if (!file.exists()) {
      throw new FileNotFoundException(file.toString());
    }
    return file;
  }

  /**
   * Finds all files with fileName in the directory, recursively.
   */
  private List<File> getFiles(final String directory, final String fileName) throws FileNotFoundException {
    final String actualFileName = fileName + USED_EXTENSION;
    return ImaerFileUtil.getFiles(directory, actualFileName);
  }

  private List<EmissionSourceFeature> getEmissionSourcesFromFile(final File file) throws IOException, AeriusException {
    final GMLReader reader = getGMLReaderFromFile(file);
    List<EmissionSourceFeature> sources = null;
    try (InputStream inputStream = new FileInputStream(file)) {
      sources = reader.readEmissionSourceList();
      reader.enforceEmissions(sources, 2030);

    } catch (final AeriusException e) {
      LOG.error("File in error during convertToEmissionSources: " + file);
      throw e;
    }
    return sources;
  }

  private List<CalculationPointFeature> getAeriusPointsFromFile(final File file, final boolean includeReceptors)
      throws IOException, AeriusException {
    final GMLReader reader = getGMLReaderFromFile(file);
    return reader.getAeriusPoints(includeReceptors);
  }

  private GMLReader getGMLReaderFromFile(final File file) throws IOException, AeriusException {
    final GMLReaderFactory factory = GMLReaderFactory.getFactory(mockGmlHelper());
    try (InputStream inputStream = new FileInputStream(file)) {
      return factory.createReader(inputStream, true, new ArrayList<AeriusException>(), new ArrayList<AeriusException>());
    } catch (final AeriusException e) {
      LOG.error("File in error during convertToCollection: " + file);
      throw e;
    }
  }

  private GMLHelper mockGmlHelper() throws AeriusException {
    final GMLHelper mockHelper = AssertGML.mockGMLHelper();

    final Map<GMLLegacyCodeType, Map<String, Conversion>> codeMaps = new HashMap<>();
    final Map<String, Conversion> offRoadMobileSourceMap = new HashMap<>();
    offRoadMobileSourceMap.put("101", new Conversion("BA-B-E3", true));
    codeMaps.put(GMLLegacyCodeType.ON_ROAD_MOBILE_SOURCE, offRoadMobileSourceMap);
    when(mockHelper.getLegacyCodes(any())).thenReturn(codeMaps);

    return mockHelper;
  }

}
