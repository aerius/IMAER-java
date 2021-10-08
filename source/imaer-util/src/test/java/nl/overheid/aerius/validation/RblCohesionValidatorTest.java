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

package nl.overheid.aerius.validation;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.overheid.aerius.shared.domain.v2.geojson.LineString;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.importer.ImportParcel;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLCorrection;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLDispersionLine;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLDispersionLineFeature;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLMeasure;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLMeasureFeature;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPoint;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.point.CustomCalculationPoint;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.domain.v2.source.SRM1RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.SRM2RoadEmissionSource;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Test class for {@link RblCohesionValidator}.
 */
class RblCohesionValidatorTest {

  private static final String DEFAULT_ROAD_ID = "342";
  private static final String DEFAULT_POINT_ID = "6584";
  private static final String DEFAULT_MEASURE_ID = "SOME_3525";

  private List<AeriusException> errors;
  private List<AeriusException> warnings;

  @BeforeEach
  void before() {
    errors = new ArrayList<>();
    warnings = new ArrayList<>();
  }

  @Test
  void testNonDuplicateRoadIds() {
    final ImportParcel resultRoad = new ImportParcel();
    final EmissionSourceFeature road1 = exampleSrm2Source(DEFAULT_ROAD_ID);
    final EmissionSourceFeature road2 = exampleSrm2Source(DEFAULT_ROAD_ID + 1);
    final List<EmissionSourceFeature> sourceList = List.of(road1, road2);
    resultRoad.getSituation().getEmissionSourcesList().addAll(sourceList);

    check(resultRoad);

    validateWarnings();
    validateErrors();
  }

  @Test
  void testDuplicateRoadIdsInSameImport() {
    final ImportParcel resultRoad = new ImportParcel();
    final EmissionSourceFeature road1 = exampleSrm2Source(DEFAULT_ROAD_ID);
    final EmissionSourceFeature road2 = exampleSrm2Source(DEFAULT_ROAD_ID);
    final List<EmissionSourceFeature> sourceList = List.of(road1, road2);
    resultRoad.getSituation().getEmissionSourcesList().addAll(sourceList);

    check(resultRoad);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_DUPLICATE_SOURCE_IDS);
  }

  @Test
  void testDuplicateRoadIdsInDifferentImport() {
    final ImportParcel resultRoad1 = new ImportParcel();
    final EmissionSourceFeature road1 = exampleSrm2Source(DEFAULT_ROAD_ID);
    final List<EmissionSourceFeature> sourceList1 = List.of(road1);
    resultRoad1.getSituation().getEmissionSourcesList().addAll(sourceList1);

    final ImportParcel resultRoad2 = new ImportParcel();
    final EmissionSourceFeature road2 = exampleSrm2Source(DEFAULT_ROAD_ID);
    final List<EmissionSourceFeature> sourceList2 = List.of(road2);
    resultRoad2.getSituation().getEmissionSourcesList().addAll(sourceList2);

    check(resultRoad1, resultRoad2);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_DUPLICATE_SOURCE_IDS);
  }

  @Test
  void testNonDuplicatePointIds() {
    final ImportParcel resultPoint = new ImportParcel();
    final CalculationPointFeature point1 = examplePoint(DEFAULT_POINT_ID);
    final CalculationPointFeature point2 = examplePoint(DEFAULT_POINT_ID + 1);
    resultPoint.getCalculationPointsList().addAll(Arrays.asList(point1, point2));

    check(resultPoint);

    validateWarnings();
    validateErrors();
  }

  @Test
  void testDuplicatePointIdsInSameImport() {
    final ImportParcel resultPoint = new ImportParcel();
    final CalculationPointFeature point1 = examplePoint(DEFAULT_POINT_ID);
    final CalculationPointFeature point2 = examplePoint(DEFAULT_POINT_ID);
    resultPoint.getCalculationPointsList().addAll(Arrays.asList(point1, point2));

    check(resultPoint);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_DUPLICATE_POINT_IDS);
  }

  @Test
  void testDuplicatePointIdsInDifferentImport() {
    final ImportParcel resultPoint1 = new ImportParcel();
    final CalculationPointFeature point1 = examplePoint(DEFAULT_POINT_ID);
    resultPoint1.getCalculationPointsList().addAll(Arrays.asList(point1));

    final ImportParcel resultPoint2 = new ImportParcel();
    final CalculationPointFeature point2 = examplePoint(DEFAULT_POINT_ID);
    resultPoint2.getCalculationPointsList().addAll(Arrays.asList(point2));

    check(resultPoint1, resultPoint2);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_DUPLICATE_POINT_IDS);
  }

  @Test
  void testNonDuplicateMeasureIds() {
    final ImportParcel resultMeasure = new ImportParcel();
    final NSLMeasureFeature feature1 = new NSLMeasureFeature();
    final NSLMeasure measure1 = new NSLMeasure();
    measure1.setGmlId(DEFAULT_MEASURE_ID);
    feature1.setProperties(measure1);
    final NSLMeasureFeature feature2 = new NSLMeasureFeature();
    final NSLMeasure measure2 = new NSLMeasure();
    measure2.setGmlId(DEFAULT_MEASURE_ID + "_other");
    feature2.setProperties(measure2);
    resultMeasure.getSituation().getNslMeasuresList().addAll(Arrays.asList(feature1, feature2));

    check(resultMeasure);

    validateWarnings();
    validateErrors();
  }

  @Test
  void testDuplicateMeasureIdsInSameImport() {
    final ImportParcel resultMeasure = new ImportParcel();
    final NSLMeasureFeature feature1 = new NSLMeasureFeature();
    final NSLMeasure measure1 = new NSLMeasure();
    measure1.setGmlId(DEFAULT_MEASURE_ID);
    feature1.setProperties(measure1);
    final NSLMeasureFeature feature2 = new NSLMeasureFeature();
    final NSLMeasure measure2 = new NSLMeasure();
    measure2.setGmlId(DEFAULT_MEASURE_ID);
    feature2.setProperties(measure2);
    resultMeasure.getSituation().getNslMeasuresList().addAll(Arrays.asList(feature1, feature2));

    check(resultMeasure);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_DUPLICATE_MEASURE_IDS);
  }

  @Test
  void testDuplicateMeasureIdsInDifferentImport() {
    final ImportParcel resultMeasure1 = new ImportParcel();
    final NSLMeasureFeature feature1 = new NSLMeasureFeature();
    final NSLMeasure measure1 = new NSLMeasure();
    measure1.setGmlId(DEFAULT_MEASURE_ID);
    feature1.setProperties(measure1);
    resultMeasure1.getSituation().getNslMeasuresList().addAll(Arrays.asList(feature1));

    final ImportParcel resultMeasure2 = new ImportParcel();
    final NSLMeasureFeature feature2 = new NSLMeasureFeature();
    final NSLMeasure measure2 = new NSLMeasure();
    measure2.setGmlId(DEFAULT_MEASURE_ID);
    feature2.setProperties(measure2);
    resultMeasure2.getSituation().getNslMeasuresList().addAll(Arrays.asList(feature2));

    check(resultMeasure1, resultMeasure2);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_DUPLICATE_MEASURE_IDS);
  }

  @Test
  void testDispersionLineCorrect() {
    final ImportParcel resultDispersionLine = new ImportParcel();
    final NSLDispersionLine dispersionLine = new NSLDispersionLine();
    dispersionLine.setCalculationPointGmlId(DEFAULT_POINT_ID);
    dispersionLine.setRoadGmlId(DEFAULT_ROAD_ID);
    final NSLDispersionLineFeature dispersionLineFeature = new NSLDispersionLineFeature();
    dispersionLineFeature.setProperties(dispersionLine);
    resultDispersionLine.getSituation().getNslDispersionLinesList().add(dispersionLineFeature);

    final ImportParcel resultRoad = new ImportParcel();
    final EmissionSourceFeature road = exampleSrm1Source(DEFAULT_ROAD_ID);
    final List<EmissionSourceFeature> sourceList = List.of(road);
    resultRoad.getSituation().getEmissionSourcesList().addAll(sourceList);

    final ImportParcel resultPoint = new ImportParcel();
    final CalculationPointFeature point = examplePoint(DEFAULT_POINT_ID);
    resultPoint.getCalculationPointsList().addAll(Arrays.asList(point));

    check(resultDispersionLine, resultRoad, resultPoint);

    validateWarnings();
    validateErrors();
  }

  @Test
  void testDispersionLineWithSrm2Road() {
    final ImportParcel resultDispersionLine = new ImportParcel();
    final NSLDispersionLine dispersionLine = new NSLDispersionLine();
    dispersionLine.setCalculationPointGmlId(DEFAULT_POINT_ID);
    dispersionLine.setRoadGmlId(DEFAULT_ROAD_ID);
    final NSLDispersionLineFeature dispersionLineFeature = new NSLDispersionLineFeature();
    dispersionLineFeature.setProperties(dispersionLine);
    resultDispersionLine.getSituation().getNslDispersionLinesList().add(dispersionLineFeature);

    final ImportParcel resultRoad = new ImportParcel();
    final EmissionSourceFeature road = exampleSrm2Source(DEFAULT_ROAD_ID);
    final List<EmissionSourceFeature> sourceList = List.of(road);
    resultRoad.getSituation().getEmissionSourcesList().addAll(sourceList);

    final ImportParcel resultPoint = new ImportParcel();
    final CalculationPointFeature point = examplePoint(DEFAULT_POINT_ID);
    resultPoint.getCalculationPointsList().addAll(Arrays.asList(point));

    check(resultDispersionLine, resultRoad, resultPoint);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_REFERENCE_DISPERSION_LINE_MISSING_ROAD);
  }

  @Test
  void testDispersionLineWithWrongRoad() {
    final ImportParcel resultDispersionLine = new ImportParcel();
    final NSLDispersionLine dispersionLine = new NSLDispersionLine();
    dispersionLine.setCalculationPointGmlId(DEFAULT_POINT_ID);
    dispersionLine.setRoadGmlId(DEFAULT_ROAD_ID);
    final NSLDispersionLineFeature dispersionLineFeature = new NSLDispersionLineFeature();
    dispersionLineFeature.setProperties(dispersionLine);
    resultDispersionLine.getSituation().getNslDispersionLinesList().add(dispersionLineFeature);

    final ImportParcel resultRoad = new ImportParcel();
    final EmissionSourceFeature road = exampleSrm1Source("9");
    final List<EmissionSourceFeature> sourceList = List.of(road);
    resultRoad.getSituation().getEmissionSourcesList().addAll(sourceList);

    final ImportParcel resultPoint = new ImportParcel();
    final CalculationPointFeature point = examplePoint(DEFAULT_POINT_ID);
    resultPoint.getCalculationPointsList().addAll(Arrays.asList(point));

    check(resultDispersionLine, resultRoad, resultPoint);

    validateWarnings(ImaerExceptionReason.COHESION_ROAD_MISSING_DISPERSION_LINE);
    validateErrors(ImaerExceptionReason.COHESION_REFERENCE_DISPERSION_LINE_MISSING_ROAD);
  }

  @Test
  void testDispersionLineWithWrongPoint() {
    final ImportParcel resultDispersionLine = new ImportParcel();
    final NSLDispersionLine dispersionLine = new NSLDispersionLine();
    dispersionLine.setCalculationPointGmlId(DEFAULT_POINT_ID);
    dispersionLine.setRoadGmlId(DEFAULT_ROAD_ID);
    final NSLDispersionLineFeature dispersionLineFeature = new NSLDispersionLineFeature();
    dispersionLineFeature.setProperties(dispersionLine);
    resultDispersionLine.getSituation().getNslDispersionLinesList().add(dispersionLineFeature);

    final ImportParcel resultRoad = new ImportParcel();
    final EmissionSourceFeature road = exampleSrm1Source(DEFAULT_ROAD_ID);
    final List<EmissionSourceFeature> sourceList = List.of(road);
    resultRoad.getSituation().getEmissionSourcesList().addAll(sourceList);

    final ImportParcel resultPoint = new ImportParcel();
    final CalculationPointFeature point = examplePoint("8");
    resultPoint.getCalculationPointsList().addAll(Arrays.asList(point));

    check(resultDispersionLine, resultRoad, resultPoint);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_REFERENCE_DISPERSION_LINE_MISSING_POINT);
  }

  @Test
  void testDispersionLineNotPerpendicular() {
    final ImportParcel resultDispersionLine = new ImportParcel();
    final NSLDispersionLine dispersionLine = new NSLDispersionLine();
    dispersionLine.setCalculationPointGmlId(DEFAULT_POINT_ID);
    dispersionLine.setRoadGmlId(DEFAULT_ROAD_ID);
    final NSLDispersionLineFeature dispersionLineFeature = new NSLDispersionLineFeature();
    dispersionLineFeature.setProperties(dispersionLine);
    resultDispersionLine.getSituation().getNslDispersionLinesList().add(dispersionLineFeature);

    final ImportParcel resultRoad = new ImportParcel();
    final EmissionSourceFeature road = exampleSrm1Source(DEFAULT_ROAD_ID);
    final List<EmissionSourceFeature> sourceList = List.of(road);
    resultRoad.getSituation().getEmissionSourcesList().addAll(sourceList);

    final ImportParcel resultPoint = new ImportParcel();
    final CalculationPointFeature point = examplePoint(DEFAULT_POINT_ID);
    point.setGeometry(new Point(-200, -100));
    resultPoint.getCalculationPointsList().addAll(Arrays.asList(point));

    check(resultDispersionLine, resultRoad, resultPoint);

    validateWarnings(ImaerExceptionReason.COHESION_DISPERSION_LINE_NOT_PERPENDICULAR);
    validateErrors();
  }

  @Test
  void testSrm1RoadWithoutDispersionLine() {
    final ImportParcel resultRoad = new ImportParcel();
    final EmissionSourceFeature road = exampleSrm1Source(DEFAULT_ROAD_ID);
    final List<EmissionSourceFeature> sourceList = List.of(road);
    resultRoad.getSituation().getEmissionSourcesList().addAll(sourceList);

    final ImportParcel resultPoint = new ImportParcel();
    final CalculationPointFeature point = examplePoint(DEFAULT_POINT_ID);
    point.setGeometry(new Point(-200, -100));
    resultPoint.getCalculationPointsList().addAll(Arrays.asList(point));

    check(resultRoad, resultPoint);

    validateWarnings(ImaerExceptionReason.COHESION_ROAD_MISSING_DISPERSION_LINE);
    validateErrors();
  }

  @Test
  void testDuplicateDispersionLinesInSameImport() {
    final ImportParcel resultDispersionLine = new ImportParcel();
    final NSLDispersionLine dispersionLine1 = new NSLDispersionLine();
    dispersionLine1.setCalculationPointGmlId(DEFAULT_POINT_ID);
    dispersionLine1.setRoadGmlId(DEFAULT_ROAD_ID);
    final NSLDispersionLineFeature dispersionLineFeature1 = new NSLDispersionLineFeature();
    dispersionLineFeature1.setProperties(dispersionLine1);
    resultDispersionLine.getSituation().getNslDispersionLinesList().add(dispersionLineFeature1);
    final NSLDispersionLine dispersionLine2 = new NSLDispersionLine();
    dispersionLine2.setCalculationPointGmlId(DEFAULT_POINT_ID);
    dispersionLine2.setRoadGmlId(DEFAULT_ROAD_ID);
    final NSLDispersionLineFeature dispersionLineFeature2 = new NSLDispersionLineFeature();
    dispersionLineFeature2.setProperties(dispersionLine2);
    resultDispersionLine.getSituation().getNslDispersionLinesList().add(dispersionLineFeature2);

    final ImportParcel resultRoad = new ImportParcel();
    final EmissionSourceFeature road = exampleSrm1Source(DEFAULT_ROAD_ID);
    final List<EmissionSourceFeature> sourceList = List.of(road);
    resultRoad.getSituation().getEmissionSourcesList().addAll(sourceList);

    final ImportParcel resultPoint = new ImportParcel();
    final CalculationPointFeature point = examplePoint(DEFAULT_POINT_ID);
    resultPoint.getCalculationPointsList().addAll(Arrays.asList(point));

    check(resultDispersionLine, resultRoad, resultPoint);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_DUPLICATE_DISPERSION_LINES);
  }

  @Test
  void testDuplicateDispersionLinesDifferentImport() {
    final ImportParcel resultDispersionLine1 = new ImportParcel();
    final NSLDispersionLine dispersionLine1 = new NSLDispersionLine();
    dispersionLine1.setCalculationPointGmlId(DEFAULT_POINT_ID);
    dispersionLine1.setRoadGmlId(DEFAULT_ROAD_ID);
    final NSLDispersionLineFeature dispersionLineFeature1 = new NSLDispersionLineFeature();
    dispersionLineFeature1.setProperties(dispersionLine1);
    resultDispersionLine1.getSituation().getNslDispersionLinesList().add(dispersionLineFeature1);
    final ImportParcel resultDispersionLine2 = new ImportParcel();
    final NSLDispersionLine dispersionLine2 = new NSLDispersionLine();
    dispersionLine2.setCalculationPointGmlId(DEFAULT_POINT_ID);
    dispersionLine2.setRoadGmlId(DEFAULT_ROAD_ID);
    final NSLDispersionLineFeature dispersionLineFeature2 = new NSLDispersionLineFeature();
    dispersionLineFeature2.setProperties(dispersionLine2);
    resultDispersionLine2.getSituation().getNslDispersionLinesList().add(dispersionLineFeature2);

    final ImportParcel resultRoad = new ImportParcel();
    final EmissionSourceFeature road = exampleSrm1Source(DEFAULT_ROAD_ID);
    final List<EmissionSourceFeature> sourceList = List.of(road);
    resultRoad.getSituation().getEmissionSourcesList().addAll(sourceList);

    final ImportParcel resultPoint = new ImportParcel();
    final CalculationPointFeature point = examplePoint(DEFAULT_POINT_ID);
    resultPoint.getCalculationPointsList().addAll(Arrays.asList(point));

    check(resultDispersionLine1, resultDispersionLine2, resultRoad, resultPoint);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_DUPLICATE_DISPERSION_LINES);
  }

  @Test
  void testCorrectionCorrect() {
    final ImportParcel resultCorrection = new ImportParcel();
    final NSLCorrection correction = new NSLCorrection();
    correction.setCalculationPointGmlId(DEFAULT_POINT_ID);
    resultCorrection.getSituation().getNslCorrections().add(correction);

    final ImportParcel resultPoint = new ImportParcel();
    final CalculationPointFeature point = examplePoint(DEFAULT_POINT_ID);
    resultPoint.getCalculationPointsList().addAll(Arrays.asList(point));

    check(resultCorrection, resultPoint);

    validateWarnings();
    validateErrors();
  }

  @Test
  void testCorrectionWithWrongPoint() {
    final ImportParcel resultCorrection = new ImportParcel();
    final NSLCorrection correction = new NSLCorrection();
    correction.setCalculationPointGmlId(DEFAULT_POINT_ID);
    resultCorrection.getSituation().getNslCorrections().add(correction);

    final ImportParcel resultPoint = new ImportParcel();
    final CalculationPointFeature point = examplePoint("8");
    resultPoint.getCalculationPointsList().addAll(Arrays.asList(point));

    check(resultCorrection, resultPoint);

    validateWarnings(ImaerExceptionReason.COHESION_REFERENCE_CORRECTION_MISSING_POINT);
    validateErrors();
  }

  private void validateWarnings(final ImaerExceptionReason... expectedWarningReasons) {
    assertEquals(expectedWarningReasons.length, warnings.size(), "Number of warnings");
    assertArrayEquals(expectedWarningReasons, warnings.stream().map(AeriusException::getReason).toArray(), "Reasons of warnings");
  }

  private void validateErrors(final ImaerExceptionReason... expectedErrorReasons) {
    assertEquals(expectedErrorReasons.length, errors.size(), "Number of errors");
    assertArrayEquals(expectedErrorReasons, errors.stream().map(AeriusException::getReason).toArray(), "Reasons for errors");
  }

  private void check(final ImportParcel... results) {
    final RblCohesionValidator checker = new RblCohesionValidator();
    checker.checkCohesion(Arrays.asList(results), errors, warnings);
  }

  private EmissionSourceFeature exampleSrm2Source(final String gmlId) {
    final EmissionSourceFeature feature = new EmissionSourceFeature();
    final LineString geometry = new LineString();
    geometry.setCoordinates(new double[][] {
    {5, 9},
    {10, 3},
    });
    feature.setGeometry(geometry);
    final SRM2RoadEmissionSource road = new SRM2RoadEmissionSource();
    road.setGmlId(gmlId);
    feature.setProperties(road);
    return feature;
  }

  private EmissionSourceFeature exampleSrm1Source(final String gmlId) {
    final EmissionSourceFeature feature = new EmissionSourceFeature();
    final LineString geometry = new LineString();
    geometry.setCoordinates(new double[][] {
    {101, 202},
    {101, 303},
    });
    feature.setGeometry(geometry);
    final SRM1RoadEmissionSource road = new SRM1RoadEmissionSource();
    road.setGmlId(gmlId);
    feature.setProperties(road);
    return feature;
  }

  private CalculationPointFeature examplePoint(final String gmlId) {
    final CalculationPointFeature feature = new CalculationPointFeature();
    feature.setGeometry(new Point(150, 250));
    final CalculationPoint point = new CustomCalculationPoint();
    point.setGmlId(gmlId);
    feature.setProperties(point);
    return feature;
  }

}
