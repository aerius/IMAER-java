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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.overheid.aerius.gml.base.AeriusGMLVersion;
import nl.overheid.aerius.gml.base.MetaDataInput;
import nl.overheid.aerius.importer.ImaerImporter;
import nl.overheid.aerius.importer.ImportOption;
import nl.overheid.aerius.shared.domain.scenario.SituationType;
import nl.overheid.aerius.shared.domain.v2.importer.ImportParcel;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.AeriusException.Reason;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.test.GMLTestDomain;

/**
 * Test class that reads an gml, converts to the data structures and then converts
 * it back to a gml file. It should not throw an exception. Expected warnings - if any - must be specified.
 *
 * <p>There are 2 ways to work around older version compatibility test problems here:
 *
 * <p>a) Make a copy of the expected file as found in the latest version and modify
 * the content to match the output as expected given the older version and give the
 * filename: [test]_v[version].gml
 * <p>b) Add the test to {@link #SKIPPED_TESTS}. This should preferably only be done
 * for tests that contain features not available in older versions.
 */
public class GMLRoundtripTest {

  private static final Logger LOG = LoggerFactory.getLogger(GMLRoundtripTest.class);

  private static final Object[][] FILES = {
      {"farm",},
      {"farm_with_systems",},
      {"farm_with_systems_and_fodder_measures",},
      {"farm_with_established",},
      {"farm_with_custom_animal",},
      {"industry",},
      {"inlandshipping",},
      {"inlandshipping_with_waterway",},
      {"inlandshipping_custom"},
      {"maritimeship",},
      {"maritimeship_custom",},
      {"mooringinland",},
      {"mooringinland_custom",},
      {"mooringmaritime",},
      {"mooringmaritime_custom",},
      {"offroad", EnumSet.of(ImaerExceptionReason.GML_SOURCE_NO_EMISSION, ImaerExceptionReason.GML_OFF_ROAD_CATEGORY_CONVERTED)},
      {"offroad_with_specifications", EnumSet.of(ImaerExceptionReason.GML_OFF_ROAD_CATEGORY_CONVERTED)},
      {"offroad_idle_and_nh3", EnumSet.of(ImaerExceptionReason.GML_OFF_ROAD_CATEGORY_CONVERTED)},
      {"offroad_non_idle", EnumSet.of(ImaerExceptionReason.GML_OFF_ROAD_CATEGORY_CONVERTED)},
      {"offroad_adblue"},
      {"plan",},
      {"road",},
      {"road_non_urban",},
      {"road_dynamic_segmentation",},
      {"road_empty", EnumSet.of(ImaerExceptionReason.SRM2_SOURCE_NO_VEHICLES, ImaerExceptionReason.GML_SOURCE_NO_EMISSION)},
      {"road_direction",},
      {"road_specific_and_custom",},
      {"metadata",},
      {"industry_with_calculated_heat_content",},
      {"industry_with_default_emission_temperature",},
      {"industry_with_building",},
      {"industry_with_custom_diurnal_variation",},
      {"two_networks",},
      {"road_srm1",},
      {"nsl_full_example",},
      {"farmland",},
      {"situation_type_reference",},
      {"situation_type_proposed",},
      {"situation_type_temporary",},
      {"situation_type_netting",},
      {"situation_type_combination_reference",},
      {"situation_type_combination_proposed",},
  };

  private static final String LATEST_VERSION = "latest";
  private static final String TEST_FOLDER = "/roundtrip/";
  private static final AeriusGMLVersion CURRENT_GML_VERSION = GMLWriter.LATEST_WRITER_VERSION;
  private static final String CURRENT_VERSION = CURRENT_GML_VERSION.name().toLowerCase();

  public static List<Object[]> data() throws FileNotFoundException {
    final List<Object[]> files = new ArrayList<>();
    for (final AeriusGMLVersion version : AeriusGMLVersion.values()) {
      for (final Object[] object : FILES) {
        if (!skipThisTest(version, (String) object[0])) {
          final Object[] f = new Object[3];
          f[0] = version;
          f[1] = object[0];
          f[2] = getWarningReasons(object, version);
          files.add(f);
        } else {
          LOG.debug("Skipping test '{}' for '{}'", object[0], version);
        }
      }
    }
    return files;
  }

  private static boolean skipThisTest(final AeriusGMLVersion version, final String file) {
    boolean skipThisTest = false;
    final String relativePath = getRelativePath(version.name().toLowerCase(), TEST_FOLDER);
    // Assuming that if there is no file present for the version, it's not possible to do the roundtrip due to newly added functionality.
    if (AssertGML.getFileResource(relativePath, file) == null) {
      skipThisTest = true;
    }
    return skipThisTest;
  }

  private static Set<Reason> getWarningReasons(final Object[] object, final AeriusGMLVersion version) {
    final Set<Reason> warnings = object.length == 2 ? new HashSet<>((Set<Reason>) object[1]) : new HashSet<>();

    if (version != CURRENT_GML_VERSION) {
      warnings.add(ImaerExceptionReason.GML_VERSION_NOT_LATEST);
    }
    // GML_INVALID_OFF_ROAD_CATEGORY_MATCH is a warning that should only pop up for versions < 4.0
    if (warnings.contains(ImaerExceptionReason.GML_OFF_ROAD_CATEGORY_CONVERTED) && version.ordinal() <= AeriusGMLVersion.V4_0.ordinal()) {
      warnings.remove(ImaerExceptionReason.GML_OFF_ROAD_CATEGORY_CONVERTED);
    }
    return warnings;
  }

  @ParameterizedTest
  @MethodSource("data")
  public void testRoundTripGML(final AeriusGMLVersion version, final String file, final Set<Reason> expectedWarnings) {
    final String versionString = version.name().toLowerCase();
    final String fileVersion = ": " + file + ':' + versionString;
    final ImportParcel result = importAndCompare(versionString, fileVersion, file);

    assertTrue(result.getExceptions().isEmpty(),
        "No Exceptions: " + result.getExceptions().stream().map(Object::toString).collect(Collectors.joining(", "))
            + fileVersion);
    assertExpectedWarnings(result.getWarnings(), expectedWarnings);
    assertUnExpectedWarnings(result.getWarnings(), expectedWarnings);
  }

  private ImportParcel importAndCompare(final String versionString, final String fileVersion, final String file) {
    try {
      final ImportParcel result = getImportResult(versionString, TEST_FOLDER, file);
      final GMLWriter gmlc = new GMLWriter(GMLTestDomain.getExampleGridSettings(), GMLTestDomain.TEST_REFERENCE_GENERATOR);
      if (result.getSituation().getType() == null) {
        result.getSituation().setType(SituationType.PROPOSED);
      }
      final GMLScenario scenario = GMLScenario.Builder
          .create(result, result.getSituation())
          .build();
      final String gml;
      try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
        gmlc.write(bos, scenario, getMetaData(result));
        gml = bos.toString(StandardCharsets.UTF_8.name());
        assertFalse(gml.isEmpty(), "Generated GML is empty");
      }
      assertGMLs(gml, versionString, file);
      return result;
    } catch (final IOException | AeriusException e) {
      throw new AssertionError(fileVersion, e);
    }
  }

  /**
   * Expected warnings check.
   * @param expectedWarnings
   */
  private void assertExpectedWarnings(final List<AeriusException> warnings, final Set<Reason> expectedWarnings) {
    for (final Reason warning : expectedWarnings) {
      boolean contains = false;
      for (final AeriusException ae : warnings) {
        if (ae.getReason() == warning) {
          contains = true;
        }
      }
      assertTrue(contains, "Expected warning " + warning + " not found");
    }
  }

  /**
   * Test unexpected warnings.
   */
  public void assertUnExpectedWarnings(final List<AeriusException> warnings, final Set<Reason> expectedWarnings) {
    for (final AeriusException warning : warnings) {
      if (!expectedWarnings.contains(warning.getReason())) {
        fail("Not expected warning, got " + warning.getReason() + " " + warning.getMessage());
      }
    }
  }

  private MetaDataInput getMetaData(final ImportParcel result) {
    final MetaDataInput input = new MetaDataInput();
    input.setScenarioMetaData(result.getImportedMetaData());
    input.setYear(result.getSituation().getYear());
    input.setVersion("DEV");
    input.setDatabaseVersion(result.getDatabaseVersion());
    return input;
  }

  private void assertGMLs(final String generatedGML, final String version, final String file) throws IOException {
    final String actual = replaceIncomparables(generatedGML);
    assertFalse(actual.isEmpty(), "Result shouldn't be empty");
    final String expected = replaceIncomparables(getFileContent(version, file));
    AssertGML.assertEqualsGML(expected, actual);
  }

  private String getFileContent(final String version, final String file) throws IOException {
    final String relativePathCompatibleOld = LATEST_VERSION + TEST_FOLDER;
    final String fileNameCompatibleOld = file + '_' + version;
    final String relativePathCurrent = CURRENT_VERSION + TEST_FOLDER;
    final String fileNameCurrent = file;

    final String compatibleOld = AssertGML.getFileContent(relativePathCompatibleOld, fileNameCompatibleOld);
    if (compatibleOld.isEmpty()) {
      LOG.debug("Getting current file content with path '{}' and filename '{}'. Not an old compatible version found.",
          relativePathCurrent, fileNameCurrent);
    } else {
      LOG.debug("Getting current file content with path '{}' and filename '{}'. Old compatible version found.",
          relativePathCompatibleOld, fileNameCompatibleOld);
    }
    return compatibleOld.isEmpty() ? AssertGML.getFileContent(relativePathCurrent, fileNameCurrent) : compatibleOld;
  }

  private ImportParcel getImportResult(final String versionString, final String testFolder, final String file)
      throws IOException, AeriusException {
    final String relativePath = getRelativePath(versionString, testFolder);
    final AtomicReference<String> readVersion = new AtomicReference<>();
    final ImaerImporter importer = new ImaerImporter(AssertGML.mockGMLHelper()) {
      @Override
      protected GMLReader createGMLReader(final InputStream inputStream, final Set<ImportOption> importOptions, final ImportParcel result)
          throws AeriusException {
        final GMLReader reader = super.createGMLReader(inputStream, importOptions, result);
        readVersion.set(reader.getVersion().name().toLowerCase());
        return reader;
      }
    };
    final ImportParcel result = new ImportParcel();
    try (final InputStream inputStream = AssertGML.getFileInputStream(relativePath, file)) {
      importer.importStream(inputStream, ImportOption.getDefaultOptions(), result);
    }
    assertEquals(versionString, readVersion.get(), "GML imported is not of expected version");
    assertTrue(result.getExceptions().isEmpty(), "No exceptions");
    return result;
  }

  private static String getRelativePath(final String versionString, final String testFolder) {
    return versionString + testFolder;
  }

  private String replaceIncomparables(final String string) {
    return AssertGML.USE_ORIGINAL_GML ? string
        : string.replaceAll("<imaer:version>[^<]+</imaer:version>", "").replaceAll("\\s*<imaer:reference>[^<]+</imaer:reference>", "");
  }
}
