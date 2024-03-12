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
import java.util.Optional;
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
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.Theme;
import nl.overheid.aerius.shared.domain.calculation.CalculationSetOptions;
import nl.overheid.aerius.shared.domain.result.EmissionResultKey;
import nl.overheid.aerius.shared.domain.result.EmissionResultType;
import nl.overheid.aerius.shared.domain.scenario.SituationType;
import nl.overheid.aerius.shared.domain.v2.characteristics.CharacteristicsType;
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
 *
 * <p>In some cases the GML might be enriched with additional data.
 * In that case the input file would be different from the output data.
 * In such case a version of the file with `-reference` appended to the name can be added next the the input file.
 * This file should contain the enriched data and will then be used as reference output.
 * The enriched reference file itself will also be tested and should result in the same content.
 */
class GMLRoundtripTest {

  private static final Logger LOG = LoggerFactory.getLogger(GMLRoundtripTest.class);

  private static final Object[][] FILES = {
      {"farm", CharacteristicsType.OPS},
      {"farm_with_systems", CharacteristicsType.OPS},
      {"farm_with_systems_and_fodder_measures", CharacteristicsType.OPS},
      {"farm_with_established", CharacteristicsType.OPS},
      {"farm_with_custom_animal", CharacteristicsType.OPS},
      {"farm_with_custom_emission_factor_type", CharacteristicsType.OPS},
      {"industry", CharacteristicsType.OPS},
      {"inlandshipping", CharacteristicsType.OPS},
      {"inlandshipping_with_waterway", CharacteristicsType.OPS},
      {"inlandshipping_custom", CharacteristicsType.OPS},
      {"maritimeship", CharacteristicsType.OPS},
      {"maritimeship_custom", CharacteristicsType.OPS},
      {"mooringinland", CharacteristicsType.OPS},
      {"mooringinland_custom", CharacteristicsType.OPS},
      {"mooringmaritime", CharacteristicsType.OPS},
      {"mooringmaritime_custom", CharacteristicsType.OPS},
      {"offroad", CharacteristicsType.OPS,
          EnumSet.of(ImaerExceptionReason.GML_SOURCE_NO_EMISSION, ImaerExceptionReason.GML_OFF_ROAD_CATEGORY_CONVERTED)},
      {"offroad_with_specifications", CharacteristicsType.OPS, EnumSet.of(ImaerExceptionReason.GML_OFF_ROAD_CATEGORY_CONVERTED)},
      {"offroad_idle_and_nh3", CharacteristicsType.OPS, EnumSet.of(ImaerExceptionReason.GML_OFF_ROAD_CATEGORY_CONVERTED)},
      {"offroad_non_idle", CharacteristicsType.OPS, EnumSet.of(ImaerExceptionReason.GML_OFF_ROAD_CATEGORY_CONVERTED)},
      {"offroad_adblue", CharacteristicsType.OPS},
      {"plan", CharacteristicsType.OPS,},
      {"road", CharacteristicsType.OPS,},
      {"road_non_urban", CharacteristicsType.OPS,},
      {"road_dynamic_segmentation", CharacteristicsType.OPS,},
      {"road_empty", CharacteristicsType.OPS, EnumSet.of(ImaerExceptionReason.SRM2_SOURCE_NO_VEHICLES, ImaerExceptionReason.GML_SOURCE_NO_EMISSION)},
      {"road_direction", CharacteristicsType.OPS},
      {"road_specific_and_custom", CharacteristicsType.OPS},
      {"metadata", CharacteristicsType.OPS},
      {"industry_with_calculated_heat_content", CharacteristicsType.OPS},
      {"industry_with_default_emission_temperature", CharacteristicsType.OPS},
      {"industry_with_building", CharacteristicsType.OPS},
      {"industry_with_circular_building", CharacteristicsType.OPS},
      {"two_networks", CharacteristicsType.OPS},
      {"road_srm1", CharacteristicsType.OPS},
      {"nsl_full_example", CharacteristicsType.OPS},
      {"farmland", CharacteristicsType.OPS},
      {"farmland_standard", CharacteristicsType.OPS},
      {"situation_type_reference", CharacteristicsType.OPS},
      {"situation_type_proposed", CharacteristicsType.OPS},
      {"situation_type_temporary", CharacteristicsType.OPS},
      {"situation_type_netting", CharacteristicsType.OPS},
      {"situation_type_combination_reference", CharacteristicsType.OPS},
      {"situation_type_combination_proposed", CharacteristicsType.OPS},
      {"adms_industry", CharacteristicsType.ADMS},
      {"adms_industry_with_custom_diurnal_variation", CharacteristicsType.ADMS},
      {"adms_industry_with_custom_time_varying_profiles", CharacteristicsType.ADMS},
      {"adms_road", CharacteristicsType.ADMS},
      {"adms_road_with_custom_diurnal_variation", CharacteristicsType.ADMS},
      {"adms_manure_storage", CharacteristicsType.ADMS},
      {"scenario_composting_proposed", CharacteristicsType.OPS},
      {"scenario_composting_reference", CharacteristicsType.OPS},
      {"scenario_greenhouse_reference", CharacteristicsType.OPS},
      {"scenario_greenhouse_proposed", CharacteristicsType.OPS},
      {"scenario_livestock_farming_proposed", CharacteristicsType.OPS},
      {"scenario_livestock_farming_reference", CharacteristicsType.OPS},
      {"scenario_livestock_farming_netting", CharacteristicsType.OPS},
      {"scenario_powerplant", CharacteristicsType.OPS, EnumSet.of(ImaerExceptionReason.GML_SOURCE_NO_EMISSION)},
      {"scenario_smokehouse_proposed", CharacteristicsType.OPS},
      {"scenario_smokehouse_reference", CharacteristicsType.OPS},
      {"scenario_farmland_proposed", CharacteristicsType.OPS, EnumSet.of(ImaerExceptionReason.GML_SOURCE_NO_EMISSION)},
      {"scenario_traffic_network_reference", CharacteristicsType.OPS},
      {"scenario_traffic_network_proposed", CharacteristicsType.OPS},
      {"nca_calculation_point", CharacteristicsType.ADMS},
      {"nca_calculation_options", CharacteristicsType.ADMS},
      {"nca_calculation_options_quick_run", CharacteristicsType.ADMS},
      {"archive_metadata", CharacteristicsType.ADMS}
  };

  private static final String LATEST_VERSION = "latest";
  private static final String REFERENCE = "-reference";
  private static final String TEST_FOLDER = "/roundtrip/";
  private static final AeriusGMLVersion CURRENT_GML_VERSION = GMLWriter.LATEST_WRITER_VERSION;

  private static final List<Substance> SUBSTANCES = List.of(Substance.NOX, Substance.NO2, Substance.NH3);

  private static final Set<AeriusGMLVersion> SUPPORTED_GML_WRITER_VERSIONS = EnumSet.of(AeriusGMLVersion.V6_0, AeriusGMLVersion.V5_1);

  static List<Object[]> data() throws FileNotFoundException {
    final List<Object[]> files = new ArrayList<>();
    for (final AeriusGMLVersion version : AeriusGMLVersion.values()) {
      if (SUPPORTED_GML_WRITER_VERSIONS.contains(version) && version != CURRENT_GML_VERSION) {
        addArguments(files, version, version);
      }
      addArguments(files, version, CURRENT_GML_VERSION);
    }
    return files;
  }

  private static void addArguments(final List<Object[]> files, final AeriusGMLVersion version, final AeriusGMLVersion targetVersion) {
    for (final Object[] object : FILES) {
      if (!skipThisTest(version, (String) object[0])) {
        final Object[] f = new Object[5];
        f[0] = version;
        f[1] = object[0];
        f[2] = object[1];
        f[3] = getWarningReasons(object, version);
        f[4] = targetVersion;
        files.add(f);
      } else {
        LOG.debug("Skipping test '{}' for '{}'", object[0], version);
      }
    }
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
    final Set<Reason> warnings = object.length == 3 ? new HashSet<>((Set<Reason>) object[2]) : new HashSet<>();

    if (version != CURRENT_GML_VERSION) {
      warnings.add(ImaerExceptionReason.GML_VERSION_NOT_LATEST);
    }
    // GML_INVALID_OFF_ROAD_CATEGORY_MATCH is a warning that should only pop up for versions < 4.0
    if (warnings.contains(ImaerExceptionReason.GML_OFF_ROAD_CATEGORY_CONVERTED) && version.ordinal() <= AeriusGMLVersion.V4_0.ordinal()) {
      warnings.remove(ImaerExceptionReason.GML_OFF_ROAD_CATEGORY_CONVERTED);
    }
    return warnings;
  }

  @ParameterizedTest(name = "{0}-{4}-{1}")
  @MethodSource("data")
  void testRoundTripGML(final AeriusGMLVersion originalGMLVersion, final String file, final CharacteristicsType ct,
      final Set<Reason> expectedWarnings, final AeriusGMLVersion targetGMLVersion) {
    final String versionString = originalGMLVersion.name().toLowerCase();
    final String fileVersion = ": " + file + ':' + versionString;
    final ImportParcel result = importAndCompare(versionString, fileVersion, file, ct, targetGMLVersion);

    assertNoExceptions(result.getExceptions(), fileVersion);
    assertExpectedWarnings(result.getWarnings(), expectedWarnings);
    assertUnExpectedWarnings(result.getWarnings(), expectedWarnings);
  }

  private ImportParcel importAndCompare(final String versionString, final String fileVersion, final String file, final CharacteristicsType ct,
      final AeriusGMLVersion targetGMLVersion) {
    try {
      final ImportParcel result = getImportResult(versionString, TEST_FOLDER, file, ct, file.contains("archive"));
      final GMLWriter gmlc = new GMLWriter(GMLTestDomain.getExampleGridSettings(), GMLTestDomain.TEST_REFERENCE_GENERATOR, targetGMLVersion);
      revertAutoCorrections(result);
      final GMLScenario scenario = GMLScenario.Builder
          .create(result, result.getSituation())
          .build();
      final String gml;
      try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
        final MetaDataInput metaDataInput = getMetaData(result, file.startsWith("nca") ? Theme.NCA : Theme.OWN2000,
            file.contains("calculation_options"));
        gmlc.write(bos, scenario, metaDataInput);
        gml = bos.toString(StandardCharsets.UTF_8.name());
        assertFalse(gml.isEmpty(), "Generated GML is empty");
      }
      assertGMLs(gml, versionString, file, targetGMLVersion);
      return result;
    } catch (final IOException | AeriusException e) {
      throw new AssertionError(fileVersion, e);
    }
  }

  private static void revertAutoCorrections(final ImportParcel result) {
    if (result.getSituation().getType() == null && result.getArchiveMetaData() == null) {
      result.getSituation().setType(SituationType.PROPOSED);
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
  void assertUnExpectedWarnings(final List<AeriusException> warnings, final Set<Reason> expectedWarnings) {
    for (final AeriusException warning : warnings) {
      if (!expectedWarnings.contains(warning.getReason())) {
        fail("Not expected warning, got " + warning.getReason() + " " + warning.getMessage());
      }
    }
  }

  private MetaDataInput getMetaData(final ImportParcel result, final Theme theme, final boolean resultsIncluded) {
    final MetaDataInput input = new MetaDataInput();
    input.setScenarioMetaData(result.getImportedMetaData());
    input.setArchiveMetaData(result.getArchiveMetaData());
    input.setYear(result.getSituation().getYear());
    input.setVersion("DEV");
    input.setDatabaseVersion(result.getDatabaseVersion());
    input.setOptions(result.getCalculationSetOptions() == null ? new CalculationSetOptions() : result.getCalculationSetOptions());
    input.getOptions().getSubstances().addAll(SUBSTANCES);
    input.getOptions().getEmissionResultKeys()
        .addAll(EmissionResultKey.getEmissionResultKeys(SUBSTANCES, EmissionResultType.CONCENTRATION));
    input.getOptions().getEmissionResultKeys()
        .addAll(EmissionResultKey.getEmissionResultKeys(SUBSTANCES, EmissionResultType.DEPOSITION));
    input.setTheme(theme);
    input.setResultsIncluded(resultsIncluded);
    return input;
  }

  private void assertGMLs(final String generatedGML, final String version, final String file, final AeriusGMLVersion targetGMLVersion)
      throws IOException {
    final String actual = replaceIncomparables(generatedGML);
    assertFalse(actual.isEmpty(), "Result shouldn't be empty for " + file);
    final String expected = replaceIncomparables(getReferenceFileContent(version, file, targetGMLVersion));
    AssertGML.assertEqualsGML(expected, actual, file);
  }

  private String getReferenceFileContent(final String version, final String file, final AeriusGMLVersion targetGMLVersion) throws IOException {
    final String relativePathCompatibleOld = LATEST_VERSION + TEST_FOLDER;
    final String fileNameCompatibleOld = file + '_' + version;
    final String fileNameAlternativeReference = file + REFERENCE;
    final String relativePathCurrent = targetGMLVersion.name().toLowerCase() + TEST_FOLDER;

    String reference = getReferenceFileContent(relativePathCompatibleOld, fileNameCompatibleOld, "Old compatible version found.");
    if (reference.isEmpty()) {
      reference = getReferenceFileContent(relativePathCurrent, fileNameAlternativeReference, "Alternative reference file found.");
    }
    return reference.isEmpty() ? getReferenceFileContent(relativePathCurrent, file, "Use input as reference file.") : reference;
  }

  private static String getReferenceFileContent(final String directory, final String filename, final String message) throws IOException {
    final String fileContent = AssertGML.getFileContent(directory, filename);

    if (fileContent.isEmpty()) {
      LOG.debug("Getting current file content with path '{}' and filename '{}'.{}", directory, filename, message);
    }
    return fileContent;
  }

  private ImportParcel getImportResult(final String versionString, final String testFolder, final String file, final CharacteristicsType ct,
      final boolean includeResults) throws IOException, AeriusException {
    final String relativePath = getRelativePath(versionString, testFolder);
    final AtomicReference<String> readVersion = new AtomicReference<>();
    final ImaerImporter importer = new ImaerImporter(AssertGML.mockGMLHelper(ct)) {
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
      final Set<ImportOption> importOptions = ImportOption.getDefaultOptions();
      if (includeResults) {
        importOptions.add(ImportOption.INCLUDE_RESULTS);
      }
      importer.importStream(inputStream, importOptions, result, Optional.empty(), file.startsWith("nca") ? Theme.NCA : Theme.OWN2000);
    }
    assertNoExceptions(result.getExceptions(), " for version " + versionString + " with file " + file);
    assertEquals(versionString, readVersion.get(), "GML imported is not of expected version");
    if (includeResults) {
      result.getCalculationPoints().getFeatures().addAll(result.getSituationResults().getResults());
    }
    return result;
  }

  private void assertNoExceptions(final List<AeriusException> exceptions, final String message) {
    assertTrue(exceptions.isEmpty(), "No Exceptions: " + exceptions.stream().map(Object::toString).collect(Collectors.joining(", ")) + message);
  }

  private static String getRelativePath(final String versionString, final String testFolder) {
    return versionString + testFolder;
  }

  private String replaceIncomparables(final String string) {
    return AssertGML.USE_ORIGINAL_GML ? string
        : string.replaceAll("<imaer:version>[^<]+</imaer:version>", "").replaceAll("\\s*<imaer:reference>[^<]+</imaer:reference>", "");
  }
}
