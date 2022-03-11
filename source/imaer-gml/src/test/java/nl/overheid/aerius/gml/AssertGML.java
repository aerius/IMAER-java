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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import nl.overheid.aerius.gml.base.GMLHelper;
import nl.overheid.aerius.importer.ImaerImporter;
import nl.overheid.aerius.importer.ImportOption;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.geo.ReceptorGridSettings;
import nl.overheid.aerius.shared.domain.scenario.SituationType;
import nl.overheid.aerius.shared.domain.v2.characteristics.CharacteristicsType;
import nl.overheid.aerius.shared.domain.v2.characteristics.OPSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.geojson.GeometryType;
import nl.overheid.aerius.shared.domain.v2.importer.ImportParcel;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceWithSubSources;
import nl.overheid.aerius.shared.domain.v2.source.InlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MooringInlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MooringMaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.InlandWaterway;
import nl.overheid.aerius.shared.emissions.EmissionsUpdater;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.shared.geometry.GeometryCalculator;
import nl.overheid.aerius.test.GMLTestDomain;
import nl.overheid.aerius.test.TestValidationAndEmissionHelper;
import nl.overheid.aerius.util.GeometryCalculatorImpl;
import nl.overheid.aerius.validation.ValidationVisitor;

/**
 * Convenience class for tests in this package.
 */
public final class AssertGML {

  /**
   * Due to linebreak interpretation differences between linux/windows and a randomness in attribute order for the collection,
   * tests can fail on either.
   * Following property should fix that. Set it to false to get the unittests to work on both,
   * set it to true to obtain prettier GML, which in turn can be used to update test files.
   */
  static final boolean USE_ORIGINAL_GML = false;

  static final String PATH_LATEST_VERSION = GMLWriter.LATEST_WRITER_VERSION.name().toLowerCase(Locale.ENGLISH) + "/";

  private static final String USED_EXTENSION = ".gml";

  private static final String REGEX_FEATURE_COLLECTION_ELEMENT = "<imaer:FeatureCollectionCalculator[^>]*>";

  private static final ReceptorGridSettings RECEPTOR_GRID_SETTINGS = GMLTestDomain.getExampleGridSettings();

  private AssertGML() {
    //util convenience class.
  }

  static void assertEqualsGML(final String expected, final String actual, final String gmlName) {
    assertEquals(getCleanGML(expected), getCleanGML(actual), "GML (" + gmlName + ") content is not the same.");
  }

  private static String getCleanGML(final String gml) {
    //clean out the feature collection element, the order of the xmlns attributes isn't guaranteed.
    return USE_ORIGINAL_GML ? gml : replaceNewLines(gml.replaceAll(REGEX_FEATURE_COLLECTION_ELEMENT, ""));
  }

  static String getFileContent(final String relativePath, final String fileName) throws IOException {
    final StringBuilder result = new StringBuilder();

    final URL resource = getFileResource(relativePath, fileName);
    try {
      if (resource != null) {
        final Path path = Paths.get(resource.toURI());
        try (final BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
          String line;

          while ((line = br.readLine()) != null) {
            result.append(line);
            if (USE_ORIGINAL_GML) {
              result.append(System.getProperty("line.separator"));
            }
          }
        }
      }
      return result.toString();
    } catch (final URISyntaxException e) {
      throw new IOException(e);
    }
  }

  static File getFile(final String relativePath, final String fileName) throws FileNotFoundException {
    final URL url = AssertGML.getFileResource(relativePath, fileName);
    File file = null;
    if (url != null) {
      file = new File(url.getFile());
      if (!file.exists()) {
        file = null;
      }
    }
    return file;
  }

  static ImportParcel getImportResult(final String relativePath, final String fileName)
      throws IOException, AeriusException {
    final File file = getFile(relativePath, fileName);
    final GMLHelper mockGMLHelper = AssertGML.mockGMLHelper();
    final ImaerImporter importer = new ImaerImporter(mockGMLHelper);
    final ImportParcel result = new ImportParcel();
    try (final InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
      importer.importStream(inputStream, ImportOption.getDefaultOptions(), result);
    }
    if (result.getSituation().getType() == null) {
      result.getSituation().setType(SituationType.PROPOSED);
    }
    return result;
  }

  static URL getFileResource(final String relativePath, final String fileName) {
    final String file = getFullPath(relativePath, fileName);

    return AssertGML.class.getResource(file);
  }

  static InputStream getFileInputStream(final String relativePath, final String fileName) throws FileNotFoundException {
    final String file = getFullPath(relativePath, fileName);
    final InputStream is = AssertGML.class.getResourceAsStream(file);
    if (is == null) {
      throw new FileNotFoundException("Input file not found:" + file);
    }
    return new BufferedInputStream(is);
  }

  static String getFullPath(final String relativePath, final String fileName) {
    return getFullPath(relativePath, fileName, USED_EXTENSION);
  }

  static String getFullPath(final String relativePath, final String fileName, final String extension) {
    final String actualRelativePath = relativePath.endsWith("/") ? relativePath : (relativePath + "/");
    return "/gml/" + actualRelativePath + fileName + extension;
  }

  static GMLHelper mockGMLHelper() throws AeriusException {
    final GMLHelper gmlHelper = mock(GMLHelper.class);
    when(gmlHelper.getReceptorGridSettings()).thenReturn(RECEPTOR_GRID_SETTINGS);
    when(gmlHelper.getCharacteristicsType()).thenReturn(CharacteristicsType.OPS);
    final TestValidationAndEmissionHelper valiationAndEmissionHelper = new TestValidationAndEmissionHelper();
    doAnswer(invocation -> {
      final List<EmissionSourceFeature> arg1 = (List<EmissionSourceFeature>) invocation.getArgument(1);

      for (final EmissionSourceFeature source : arg1) {
        mockEnforce(valiationAndEmissionHelper, source);
      }

      return null;
    }).when(gmlHelper).enforceEmissions(anyInt(), any());
    when(gmlHelper.suggestInlandShippingWaterway(any())).thenAnswer(invocation -> suggest((Geometry) invocation.getArgument(0)));
    when(gmlHelper.getLegacyCodes(any())).thenReturn(TestValidationAndEmissionHelper.legacyCodes());
    when(gmlHelper.getLegacyMobileSourceOffRoadConversions()).thenReturn(TestValidationAndEmissionHelper.legacyMobileSourceOffRoadConversions());
    when(gmlHelper.getLegacyPlanConversions()).thenReturn(TestValidationAndEmissionHelper.legacyPlanConversions());
    when(gmlHelper.determineDefaultCharacteristicsBySectorId(anyInt())).thenReturn(mock(OPSSourceCharacteristics.class));
    when(gmlHelper.getValidationHelper()).thenReturn(valiationAndEmissionHelper);
    return gmlHelper;
  }

  private static List<InlandWaterway> suggest(final Geometry geometry) throws AeriusException {
    validateGeometryForShipping(geometry);
    final InlandWaterway waterwayType = mock(InlandWaterway.class);
    when(waterwayType.getWaterwayCode()).thenReturn("CEMT_VIb");
    return Arrays.asList(waterwayType);
  }

  private static void mockEnforce(final TestValidationAndEmissionHelper validationAndEmissionHelper, final EmissionSourceFeature object)
      throws AeriusException {
    final EmissionSource source = object.getProperties();
    if (source instanceof InlandShippingEmissionSource
        || source instanceof MooringInlandShippingEmissionSource
        || source instanceof MaritimeShippingEmissionSource
        || source instanceof MooringMaritimeShippingEmissionSource) {
      for (final Substance substance : Substance.values()) {
        object.getProperties().getEmissions().put(substance, ((EmissionSourceWithSubSources<?>) source).getSubSources().size() *
            substance.getId() * 1.234);
      }
    } else {
      final GeometryCalculator geometryCalculator = new GeometryCalculatorImpl();
      final ValidationVisitor validationVisitor = new ValidationVisitor(new ArrayList<>(), new ArrayList<>(), validationAndEmissionHelper);
      if (object.accept(validationVisitor)) {
        final EmissionsUpdater updater = new EmissionsUpdater(validationAndEmissionHelper, geometryCalculator);
        updater.updateEmissions(object);
      }
    }
  }

  private static void validateGeometryForShipping(final Geometry geometry) throws AeriusException {
    if (geometry.type() != GeometryType.LINESTRING) {
      throw new AeriusException(ImaerExceptionReason.SHIPPING_ROUTE_GEOMETRY_NOT_ALLOWED);
    }
  }

  private static String replaceNewLines(final String string) {
    return USE_ORIGINAL_GML ? string : string.replaceAll("[\n\r]", "");
  }
}
