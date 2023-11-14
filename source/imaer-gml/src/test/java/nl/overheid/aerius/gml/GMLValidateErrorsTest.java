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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import nl.overheid.aerius.shared.domain.v2.importer.ImportParcel;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioMetaData;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.AeriusException.Reason;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Test class to check how the GML importer handles invalid input.
 */
class GMLValidateErrorsTest {

  private static final String LATEST_VALIDATE = "latest/validate/errors/";

  private static final double EPSILON = 0.0001;

  @Test
  void testGMLValidationFailed() throws IOException {
    assertResult("fout_5201_projectiesysteem", "GML Invalid projectiesystem", ImaerExceptionReason.GML_VALIDATION_FAILED,
        e -> {
          assertFalse(e.getArgs()[0].isEmpty(), "Should show list of possible options");
          assertTrue(e.getArgs()[0].contains("Invalid content was found starting with element '"),
              "Invalid content was found");
          assertTrue(e.getArgs()[0].contains("Pointy"),
              "Invalid content was found");
        });
  }

  @Test
  void testGMLGeometryInvalid() throws IOException {
    assertResult("fout_5202_projectiesysteem", "GML Invalid geometry", ImaerExceptionReason.GML_VALIDATION_FAILED,
        e -> {
          assertFalse(e.getArgs()[0].isEmpty(), "Should show list of possible options");
          assertTrue(e.getArgs()[0].contains("Invalid content was found starting with element '"),
              "Invalid content was found");
          assertTrue(e.getArgs()[0].contains(":GM_SQUARE"),
              "Invalid content was found");
        });
  }

  @Test
  void testGMLEncodingIncorrect() throws IOException {
    assertResult("fout_5203_unsupported_character", "GML Incorrect encoding", ImaerExceptionReason.GML_VALIDATION_FAILED,
        e -> {
          assertFalse(e.getArgs()[0].isEmpty(), "Should show list of possible options");
          assertTrue(e.getArgs()[0].contains("Invalid content was found starting with element '"),
              "Invalid content was found");
          assertTrue(e.getArgs()[0].contains("GM_SURFACE"),
              "Invalid content was found");
        });
  }

  @Test
  void testGMLGeometryIntersects() throws IOException {
    assertResult("fout_5204_intersecting_geometry", "GML Geometry intersects", ImaerExceptionReason.GML_GEOMETRY_INTERSECTS);
  }

  @Test
  void testGMLGeometryNotPermitted() throws IOException {
    assertResult("fout_5205_unallowed_source_geometry", "GML Geometry not permitted", ImaerExceptionReason.SHIPPING_ROUTE_GEOMETRY_NOT_ALLOWED);
  }

  @Test
  void testGMLGeometryUnknown() throws IOException {
    assertResult("fout_5206_unsupported_geometry", "GML Geometry unkown", ImaerExceptionReason.GML_VALIDATION_FAILED,
        e -> {
          assertFalse(e.getArgs()[0].isEmpty(), "Should show list of possible options");
          assertTrue(e.getArgs()[0].contains("The value of {abstract} in the element declaration for 'gml:AbstractRing' must be false"),
              "Invalid content was found");
        });
  }

  @Test
  void testGMLUnknownRav() throws IOException {
    assertResult("fout_5207_unknown_rav", "GML Unknown rav", ImaerExceptionReason.GML_UNKNOWN_RAV_CODE);
  }

  @Test
  void testGMLUnknownMobileSource() throws IOException {
    assertResult("fout_5208_unknown_mobile_source", "GML Unknown mobile source", ImaerExceptionReason.GML_UNKNOWN_MOBILE_SOURCE_CODE);
  }

  @Test
  void testGMLUnknownShipCode() throws IOException {
    assertResult("fout_5209_unknown_ship", "GML Unknown ship code", ImaerExceptionReason.GML_UNKNOWN_SHIP_CODE);
  }

  @Test
  void testGMLUnknownPlancode() throws IOException {
    assertResult("fout_5210_unknown_plan", "GML Unknown plan code", ImaerExceptionReason.GML_UNKNOWN_PLAN_CODE);
  }

  @Test
  void testGMLGenericParseError() throws IOException {
    assertResult("fout_5211_gml_content", "GML Generic parse error", ImaerExceptionReason.GML_GENERIC_PARSE_ERROR);
  }

  @Test
  void testGMLParseError() throws IOException {
    assertResult("fout_5212_gml_content", "GML parse error", ImaerExceptionReason.GML_PARSE_ERROR,
        e -> {
          assertEquals("30", e.getArgs()[0], "Row");
          assertEquals("27", e.getArgs()[1], "Column");
          assertEquals("gml:Pointy", e.getArgs()[2], "Tag in error");
          assertEquals("</gml:Pointy>", e.getArgs()[3], "Tag expected");
        });
  }

  @Test
  void testGMLVersionNotSupported() throws IOException {
    assertResult("fout_5213_unsupported_version", "GML Version not supported", ImaerExceptionReason.GML_VERSION_NOT_SUPPORTED);
  }

  @Test
  void testGMLGeometryInvalid_5215() throws IOException {
    assertResult("fout_5215_invalid_geometry", "GML Geometry invalid", ImaerExceptionReason.GML_GEOMETRY_INVALID);
  }

  @Test
  void testGMLUnknownPasMeasure() throws IOException {
    assertResult("fout_5216_unknown_pas_measure", "GML Unknown PAS measure", ImaerExceptionReason.GML_UNKNOWN_PAS_MEASURE_CODE,
        e -> {
          assertEquals("ES.1", e.getArgs()[0], "Id");
          assertEquals("PAS2013.09-01", e.getArgs()[1], "code");
        });
  }

  @Test
  void testGMLUnsupportedLodingMeasure() throws IOException {
    assertResult("fout_5217_unsupported_lodging_measure", "GML Unsupported loding measure", ImaerExceptionReason.GML_INVALID_PAS_MEASURE_CATEGORY,
        e -> {
          assertEquals("ES.1", e.getArgs()[0], "Label");
          assertEquals("PAS2015.04-01", e.getArgs()[1], "Offending category code");
          assertEquals("A1.1", e.getArgs()[2], "Category");
        });
  }

  @Test
  void testGMLInvalidRoadCategoryMatch() throws IOException {
    assertResult("fout_5219_obsolete_roadsource", "GML Invalid road category match",
        ImaerExceptionReason.GML_INVALID_ROAD_CATEGORY_MATCH);
  }

  @Test
  void testGMLIdNotUnique() throws IOException {
    assertResult("fout_5220_conflicting_id", "GML Id not unique", ImaerExceptionReason.GML_VALIDATION_FAILED,
        e -> assertTrue(e.getArgs()[0].contains("There are multiple occurrences of ID value 'ES.1'"), "Contains error"));
  }

  @Test
  void testGMLUnknownRoadCategory() throws IOException {
    assertResult("fout_5221_unknown_road", "GML Unknown road category", ImaerExceptionReason.GML_UNKNOWN_ROAD_CATEGORY);
  }

  @Test
  void testGMLMetaDataEmpty() throws IOException {
    assertResult("fout_5222_missing_metadata", "GML Metadata empty", ImaerExceptionReason.GML_VALIDATION_FAILED,
        e -> {
          assertTrue(e.getArgs()[0].contains("Invalid content was found starting with element '"), "Contains error");
          assertTrue(e.getArgs()[0].contains(":version"), "Contains error");
        });
  }

  @Test
  void testGMLSourceNegativeVehicles() throws IOException {
    assertResult("fout_5230_gml_source_negative_vehicles", "GML vehicles less than zero", ImaerExceptionReason.SRM2_SOURCE_NEGATIVE_VEHICLES,
        e -> assertEquals("Bron 1", e.getArgs()[0], "Expected label to be filled"));
  }

  @Test
  void testGMLRoadSegmentNotAFraction() throws IOException {
    assertResult("fout_5231_gml_road_segment_position_not_fraction", "GML road segment not a fraction",
        ImaerExceptionReason.GML_ROAD_SEGMENT_POSITION_NOT_FRACTION,
        e -> assertEquals(50D, Double.parseDouble(e.getArgs()[0]), EPSILON, "Expected road segment position to be 50"));
  }

  @Test
  void testGMLMissingNettingFactor() throws IOException {
    assertResult("fout_5235_netting_without_factor", "GML missing netting factor", ImaerExceptionReason.GML_MISSING_NETTING_FACTOR);
  }

  @Test
  void testGMLUnknownError() throws IOException {
    assertResult("fout_666_unknown_error", "GML Unknown error", ImaerExceptionReason.INTERNAL_ERROR, IllegalArgumentException.class);
  }

  @Test
  void testGMLYear2100() throws IOException {
    assertResult("fout_year_over_2100", "GML year greater than", ImaerExceptionReason.GML_VALIDATION_FAILED,
        e -> assertEquals("year must be less than 2100", e.getArgs()[0], "Year invalid"));
  }

  @Test
  void testGMLYear1900() throws IOException {
    assertResult("fout_year_under_1900", "GML year under than", ImaerExceptionReason.GML_VALIDATION_FAILED,
        e -> assertEquals("year must be greater than 1900", e.getArgs()[0], "Year invalid"));
  }

  @Test
  void testGMLCustomDiurnalVariationUnknownType() throws IOException {
    assertResult("fout_1023_custom_diurnal_variation", "GML custom diurnal variation type",
        ImaerExceptionReason.CUSTOM_DIURNAL_VARIATION_TYPE_UNKNOWN,
        e -> {
          assertEquals(1, e.getArgs().length, "Number of arguments");
          assertEquals("DOUBLE_HOURS", e.getArgs()[0], "The type that wasn't recognized");
        });
  }

  @Test
  void testGMLCustomDiurnalVariationCount() throws IOException {
    assertResult("fout_1024_custom_diurnal_variation", "GML custom diurnal variation count",
        ImaerExceptionReason.CUSTOM_DIURNAL_VARIATION_INVALID_COUNT,
        e -> {
          assertEquals(2, e.getArgs().length, "Number of arguments");
          assertEquals("24", e.getArgs()[0], "Expected count");
          assertEquals("2", e.getArgs()[1], "Found count");
        });
  }

  @Test
  void testGMLCustomDiurnalVariationSum() throws IOException {
    assertResult("fout_1025_custom_diurnal_variation", "GML custom diurnal variation sum",
        ImaerExceptionReason.CUSTOM_DIURNAL_VARIATION_INVALID_SUM,
        e -> {
          assertEquals(2, e.getArgs().length, "Number of arguments");
          assertEquals("24", e.getArgs()[0], "Expected sum");
          assertEquals("240", e.getArgs()[1], "Found sum");
        });
  }

  @Test
  void testGMLBuildingHeightZero() throws IOException {
    assertResult("fout_5522_building_height_zero", "GML with building height zero",
        ImaerExceptionReason.BUILDING_HEIGHT_TOO_LOW,
        e -> {
          assertEquals(1, e.getArgs().length, "Number of arguments");
        });
  }

  @Test
  void testValidateNullMetaData() {
    final List<AeriusException> exeptions = new ArrayList<>();

    GMLValidator.validateMetaData(null, exeptions, true);
    assertEquals(6, exeptions.size(), "If ScenarioMetaData is null and required it should return 6 errors.");
  }

  @Test
  void testValidateEmptyMetaData() {
    final List<AeriusException> exeptions = new ArrayList<>();

    GMLValidator.validateMetaData(new ScenarioMetaData(), exeptions, true);
    assertEquals(6, exeptions.size(), "If ScenarioMetaData is empty and required it should return 6 errors.");
  }

  @Test
  void testValidateMetaData() {
    final List<AeriusException> exeptions = new ArrayList<>();
    final ScenarioMetaData metaData = new ScenarioMetaData();
    metaData.setProjectName("ProjectName");

    GMLValidator.validateMetaData(metaData, exeptions, true);
    assertEquals(5, exeptions.size(), "ScenarioMetaData only has project name, but all required, therefor it should return 5 errors.");
  }


  private static void assertResult(final String fileName, final String expectedReasonTxt, final Reason expectedReason,
      final Consumer<AeriusException> check) throws IOException {
    check.accept(assertResult(fileName, expectedReasonTxt, expectedReason));
  }

  private static AeriusException assertResult(final String fileName, final String expectedReasonTxt, final Reason expectedReason) throws IOException {
    return assertResult(fileName, expectedReasonTxt, expectedReason, AeriusException.class);
  }

  private static <T extends Throwable> T assertResult(final String fileName, final String expectedReasonTxt, final Reason expectedReason,
      final Class<T> clazz) throws IOException {
    return assertThrows(clazz,
        () -> {
          final ImportParcel result = getImportResult(LATEST_VALIDATE, fileName);

          if (!result.getExceptions().isEmpty()) {
            final AeriusException aeriusException = result.getExceptions().get(0);

            assertSame(expectedReason, aeriusException.getReason(), expectedReasonTxt);
            throw aeriusException;
          }
          if (!result.getWarnings().isEmpty()) {
            for (final AeriusException e : result.getWarnings()) {
              if (e.getReason() != ImaerExceptionReason.GML_VERSION_NOT_LATEST
                  && e.getReason() != ImaerExceptionReason.GML_SOURCE_NO_EMISSION) {
                assertSame(expectedReason, e.getReason(), expectedReasonTxt);
                throw e;
              }
            }
          }
        },
        "Expected an exception " + clazz.getSimpleName());
  }

  private static ImportParcel getImportResult(final String relativePath, final String fileName)
      throws IOException, AeriusException {
    return AssertGML.getImportResult(relativePath, fileName);
  }
}
