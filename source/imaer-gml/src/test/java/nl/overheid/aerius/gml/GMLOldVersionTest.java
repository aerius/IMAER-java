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

import static nl.overheid.aerius.shared.exception.ImaerExceptionReason.GML_INLAND_WATERWAY_NOT_SET;
import static nl.overheid.aerius.shared.exception.ImaerExceptionReason.GML_OFF_ROAD_CATEGORY_CONVERTED;
import static nl.overheid.aerius.shared.exception.ImaerExceptionReason.GML_SOURCE_NO_EMISSION;
import static nl.overheid.aerius.shared.exception.ImaerExceptionReason.GML_VERSION_NOT_LATEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.overheid.aerius.gml.base.AeriusGMLVersion;
import nl.overheid.aerius.gml.base.MetaDataInput;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.scenario.SituationType;
import nl.overheid.aerius.shared.domain.v2.importer.ImportParcel;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.test.GMLTestDomain;

/**
 * Test class that reads an old version GML and compares it to the current version of that same GML.
 * Tests if old versions can be imported and if emissions are different based on different versions.
 */
class GMLOldVersionTest {

  private static final Logger LOG = LoggerFactory.getLogger(GMLOldVersionTest.class);

  private enum TestFile {
    FARM,
    FARM_WITH_SYSTEMS,
    FARM_WITH_SYSTEMS_AND_FODDER_MEASURES,
    ROAD(Arrays.asList(AeriusGMLVersion.V0_5, AeriusGMLVersion.V1_0)),
    ON_ROAD,
    OFF_ROAD(true, Arrays.asList(AeriusGMLVersion.V0_5, AeriusGMLVersion.V1_0,
        AeriusGMLVersion.V1_1, AeriusGMLVersion.V2_0, AeriusGMLVersion.V2_1, AeriusGMLVersion.V2_2,
        AeriusGMLVersion.V3_0, AeriusGMLVersion.V3_1)),
    NETWORK_ROAD,
    PLAN,
    ROUTE_INLAND_SHIPPING,
    ROUTE_MARITIME_SHIPPING,
    MOORING_INLAND_SHIPPING(true, Arrays.asList(AeriusGMLVersion.V0_5, AeriusGMLVersion.V1_0,
        AeriusGMLVersion.V1_1, AeriusGMLVersion.V2_0, AeriusGMLVersion.V2_1, AeriusGMLVersion.V2_2,
        AeriusGMLVersion.V3_0, AeriusGMLVersion.V3_1)),
    MOORING_MARITIME_SHIPPING(true, Arrays.asList(AeriusGMLVersion.V0_5, AeriusGMLVersion.V1_0,
        AeriusGMLVersion.V1_1, AeriusGMLVersion.V2_0, AeriusGMLVersion.V2_1, AeriusGMLVersion.V2_2,
        AeriusGMLVersion.V3_0, AeriusGMLVersion.V3_1)),
    METADATA,
    FARMLAND;

    private final List<AeriusGMLVersion> warningsIn;
    private final List<AeriusGMLVersion> errorsIn;
    private final List<AeriusGMLVersion> skipEmissionCheckIn;
    private final boolean skipNumberOfSourcesCheck;

    private TestFile(final AeriusGMLVersion... warningsIn) {
      this(false, Collections.emptyList(), Collections.emptyList(), warningsIn);
    }

    private TestFile(final List<AeriusGMLVersion> skipEmissionCheckIn, final AeriusGMLVersion... warningsIn) {
      this(false, skipEmissionCheckIn, Collections.emptyList(), warningsIn);
    }

    private TestFile(final boolean skipNumberOfSourcesCheck, final List<AeriusGMLVersion> skipEmissionCheckIn, final AeriusGMLVersion... warningsIn) {
      this(skipNumberOfSourcesCheck, skipEmissionCheckIn, Collections.emptyList(), warningsIn);
    }

    private TestFile(final boolean skipNumberOfSourcesCheck, final List<AeriusGMLVersion> skipEmissionCheckIn, final List<AeriusGMLVersion> errorsIn,
        final AeriusGMLVersion... warningsIn) {
      this.skipNumberOfSourcesCheck = skipNumberOfSourcesCheck;
      this.skipEmissionCheckIn = skipEmissionCheckIn;
      this.errorsIn = errorsIn;
      this.warningsIn = Arrays.asList(warningsIn);
    }

    String getFileName() {
      return name().toLowerCase();
    }

    boolean expectWarning(final AeriusGMLVersion version) {
      return warningsIn.contains(version);
    }

    boolean expectError(final AeriusGMLVersion version) {
      return errorsIn.contains(version);
    }

    boolean shouldCheckEmissions(final AeriusGMLVersion version) {
      return !skipEmissionCheckIn.contains(version);
    }

    boolean shouldCheckNumberOfSources() {
      return !skipNumberOfSourcesCheck;
    }

  }

  private static final String TEST_FOLDER = "/complex/";
  private static final String CURRENT_VERSION = GMLWriter.LATEST_WRITER_VERSION.name().toLowerCase(Locale.ENGLISH);

  private static final EnumSet<ImaerExceptionReason> VALID_WARNINGS = EnumSet.of(GML_VERSION_NOT_LATEST, GML_INLAND_WATERWAY_NOT_SET,
      GML_SOURCE_NO_EMISSION, GML_OFF_ROAD_CATEGORY_CONVERTED);
  private static final EnumSet<ImaerExceptionReason> VALID_ERRORS = EnumSet.noneOf(ImaerExceptionReason.class);

  static List<Object[]> data() throws FileNotFoundException {
    final List<Object[]> files = new ArrayList<>();
    for (final AeriusGMLVersion version : AeriusGMLVersion.values()) {
      for (final TestFile file : TestFile.values()) {
        if (AssertGML.getFileResource(version.name().toLowerCase() + TEST_FOLDER, file.getFileName()) == null) {
          LOG.info("Ignoring GMLOldVersionTest for file '{}' for version {}. No old version of this file", file.getFileName(), version);
        } else {
          final Object[] f = new Object[2];
          f[0] = version;
          f[1] = file;
          files.add(f);
        }
      }
    }
    return files;
  }

  @ParameterizedTest(name = "{0}-{1}")
  @MethodSource("data")
  void testVersionGML(final AeriusGMLVersion version, final TestFile testFile) throws IOException, AeriusException {
    final ImportParcel oldResult = getImportResult(version.name().toLowerCase() + TEST_FOLDER, testFile);
    final ImportParcel currentResult = getImportResult(CURRENT_VERSION + TEST_FOLDER, testFile);
    //if there is, there should be a current counter part.
    assertNotNull(currentResult, "Expected same file for current result " + testFile);
    validateExceptions(oldResult, version, testFile);
    validateWarnings(oldResult, version, testFile);
    final List<EmissionSourceFeature> oldSources = oldResult.getSituation().getEmissionSourcesList();
    final List<EmissionSourceFeature> currentSources = currentResult.getSituation().getEmissionSourcesList();
    if (testFile.shouldCheckNumberOfSources()) {
      assertEquals(currentResult.getSituation().getEmissionSourcesList().size(),
          oldResult.getSituation().getEmissionSourcesList().size(), "Number of sources");
    }
    if (testFile.shouldCheckEmissions(version)) {
      for (final EmissionSourceFeature source : oldSources) {
        testSource(source, currentSources.get(oldSources.indexOf(source)));
      }
    }
    //for good measure, try to export to a current-gen version
    final GMLWriter writer = new GMLWriter(GMLTestDomain.getExampleGridSettings(), r -> Optional.empty());
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
      writer.writeEmissionSources(bos, oldSources, getMetaDataInput(oldResult));
    }
  }

  private void validateExceptions(final ImportParcel oldResult, final AeriusGMLVersion version, final TestFile testFile) {
    if (testFile.expectError(version)) {
      assertFalse(oldResult.getExceptions().isEmpty(), "Expected  exceptions, got none");
    } else {
      // errors test on allowed
      for (final AeriusException error : oldResult.getExceptions()) {
        if (!VALID_ERRORS.contains(error.getReason())) {
          fail("Not expected exception, got " + error.getReason() + " " + error.getMessage());
        }
      }
      assertTrue(oldResult.getExceptions().isEmpty(), "Expected no exceptions, got " + oldResult.getExceptions());
    }
  }

  private void validateWarnings(final ImportParcel oldResult, final AeriusGMLVersion version, final TestFile testFile) {
    if (testFile.expectWarning(version)) {
      assertFalse(oldResult.getWarnings().isEmpty(), "Expected warnings, but got none");
    } else {
      // warnings test on allowed
      for (final AeriusException warning : oldResult.getWarnings()) {
        if (!VALID_WARNINGS.contains(warning.getReason())) {
          fail("Not expected warning, got " + warning.getReason() + " " + warning.getMessage());
        }
      }
    }
  }

  private MetaDataInput getMetaDataInput(final ImportParcel oldResult) {
    final MetaDataInput metaDataInput = new MetaDataInput();
    metaDataInput.setScenarioMetaData(oldResult.getImportedMetaData());
    metaDataInput.setYear(2020);
    metaDataInput.setName("name");
    metaDataInput.setSituationType(oldResult.getSituation().getType() == null ? SituationType.PROPOSED : oldResult.getSituation().getType());
    metaDataInput.setNettingFactor(oldResult.getSituation().getNettingFactor());
    metaDataInput.setVersion(CURRENT_VERSION);
    metaDataInput.setDatabaseVersion("version");
    return metaDataInput;
  }

  private void testSource(final EmissionSourceFeature oldSourceFeature, final EmissionSourceFeature currentSourceFeature) {
    final EmissionSource oldSource = oldSourceFeature.getProperties();
    final EmissionSource currentSource = currentSourceFeature.getProperties();
    assertEquals(currentSource.getLabel(), oldSource.getLabel(), getTestString("Label", currentSource.getLabel()));
    assertEquals(currentSource.getSectorId(), oldSource.getSectorId(), getTestString("Sector", currentSource.getLabel()));
    assertEquals(currentSourceFeature.getGeometry(), oldSourceFeature.getGeometry(), getTestString("Geometry", currentSource.getLabel()));
    testEmissionValues(currentSource.getLabel(), oldSource, currentSource);
  }

  private void testEmissionValues(final String label, final EmissionSource oldValues, final EmissionSource currentValues) {
    assertEquals(oldValues.getClass(), currentValues.getClass(), getTestString("Class of EmissionValues", label));
    assertEquals(currentValues.getEmissions().containsKey(Substance.NH3), oldValues.getEmissions().containsKey(Substance.NH3));
    if (currentValues.getEmissions().containsKey(Substance.NH3)) {
      assertEquals(currentValues.getEmissions().get(Substance.NH3),
          oldValues.getEmissions().get(Substance.NH3),
          1E-3, getTestString("NH3 emission", label));
    }
    assertEquals(currentValues.getEmissions().containsKey(Substance.NOX), oldValues.getEmissions().containsKey(Substance.NOX));
    if (currentValues.getEmissions().containsKey(Substance.NOX)) {
      assertEquals(currentValues.getEmissions().get(Substance.NOX),
          oldValues.getEmissions().get(Substance.NOX),
          1E-3, getTestString("NOx emission", label));
    }
  }

  private String getTestString(final String testPart, final String sourceLabel) {
    final StringBuilder builder = new StringBuilder(31);
    builder.append(testPart);
    builder.append(" for source ");
    builder.append(sourceLabel);
    return builder.toString();
  }

  private ImportParcel getImportResult(final String relativePath, final TestFile testFile)
      throws IOException, AeriusException {
    return AssertGML.getImportResult(relativePath, testFile.getFileName());
  }
}
