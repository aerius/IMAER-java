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
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.overheid.aerius.shared.domain.Theme;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKCorrection;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKDispersionLine;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKDispersionLineFeature;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKMeasure;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKMeasureFeature;
import nl.overheid.aerius.shared.domain.v2.geojson.LineString;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPoint;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.point.CustomCalculationPoint;
import nl.overheid.aerius.shared.domain.v2.scenario.Scenario;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioSituation;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.domain.v2.source.SRM1RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.SRM2RoadEmissionSource;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Test class for {@link CimlkCohesionValidator}.
 */
class CimlkCohesionValidatorTest {

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
    final ScenarioSituation resultRoadSituation = new ScenarioSituation();
    final EmissionSourceFeature road1 = exampleSrm2Source(DEFAULT_ROAD_ID);
    final EmissionSourceFeature road2 = exampleSrm2Source(DEFAULT_ROAD_ID + 1);
    resultRoadSituation.getEmissionSourcesList().addAll(List.of(road1, road2));

    check(resultRoadSituation);

    validateWarnings();
    validateErrors();
  }

  @Test
  void testDuplicateRoadIdsInSameImport() {
    final ScenarioSituation resultRoadSituation = new ScenarioSituation();
    final EmissionSourceFeature road1 = exampleSrm2Source(DEFAULT_ROAD_ID);
    final EmissionSourceFeature road2 = exampleSrm2Source(DEFAULT_ROAD_ID);
    final List<EmissionSourceFeature> sourceList = List.of(road1, road2);
    resultRoadSituation.getEmissionSourcesList().addAll(sourceList);

    check(resultRoadSituation);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_DUPLICATE_SOURCE_IDS);
  }

  @Test
  void testDuplicateRoadIdsInDifferentImport() {
    final ScenarioSituation resultRoad1Situation = new ScenarioSituation();
    final EmissionSourceFeature road1 = exampleSrm2Source(DEFAULT_ROAD_ID);
    final List<EmissionSourceFeature> sourceList1 = List.of(road1);
    resultRoad1Situation.getEmissionSourcesList().addAll(sourceList1);

    final ScenarioSituation resultRoad2Situation = new ScenarioSituation();
    final EmissionSourceFeature road2 = exampleSrm2Source(DEFAULT_ROAD_ID);
    final List<EmissionSourceFeature> sourceList2 = List.of(road2);
    resultRoad2Situation.getEmissionSourcesList().addAll(sourceList2);

    check(resultRoad1Situation, resultRoad2Situation);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_DUPLICATE_SOURCE_IDS);
  }

  @Test
  void testNonDuplicatePointIds() {
    final Scenario scenario = new Scenario(Theme.CIMLK);
    final CalculationPointFeature point1 = examplePoint(DEFAULT_POINT_ID);
    final CalculationPointFeature point2 = examplePoint(DEFAULT_POINT_ID + 1);
    scenario.getCustomPointsList().addAll(Arrays.asList(point1, point2));

    check(scenario);

    validateWarnings();
    validateErrors();
  }

  @Test
  void testDuplicatePointIdsInSameImport() {
    final Scenario scenario = new Scenario(Theme.CIMLK);
    final CalculationPointFeature point1 = examplePoint(DEFAULT_POINT_ID);
    final CalculationPointFeature point2 = examplePoint(DEFAULT_POINT_ID);
    scenario.getCustomPointsList().addAll(Arrays.asList(point1, point2));

    check(scenario);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_DUPLICATE_POINT_IDS);
  }

  @Test
  void testNonDuplicateMeasureIds() {
    final ScenarioSituation resultMeasureSituation = new ScenarioSituation();
    final CIMLKMeasureFeature feature1 = new CIMLKMeasureFeature();
    final CIMLKMeasure measure1 = new CIMLKMeasure();
    measure1.setGmlId(DEFAULT_MEASURE_ID);
    feature1.setProperties(measure1);
    final CIMLKMeasureFeature feature2 = new CIMLKMeasureFeature();
    final CIMLKMeasure measure2 = new CIMLKMeasure();
    measure2.setGmlId(DEFAULT_MEASURE_ID + "_other");
    feature2.setProperties(measure2);
    resultMeasureSituation.getCimlkMeasuresList().addAll(Arrays.asList(feature1, feature2));

    check(resultMeasureSituation);

    validateWarnings();
    validateErrors();
  }

  @Test
  void testDuplicateMeasureIdsInSameImport() {
    final ScenarioSituation resultMeasureSituation = new ScenarioSituation();
    final CIMLKMeasureFeature feature1 = new CIMLKMeasureFeature();
    final CIMLKMeasure measure1 = new CIMLKMeasure();
    measure1.setGmlId(DEFAULT_MEASURE_ID);
    feature1.setProperties(measure1);
    final CIMLKMeasureFeature feature2 = new CIMLKMeasureFeature();
    final CIMLKMeasure measure2 = new CIMLKMeasure();
    measure2.setGmlId(DEFAULT_MEASURE_ID);
    feature2.setProperties(measure2);
    resultMeasureSituation.getCimlkMeasuresList().addAll(Arrays.asList(feature1, feature2));

    check(resultMeasureSituation);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_DUPLICATE_MEASURE_IDS);
  }

  @Test
  void testDuplicateMeasureIdsInDifferentImport() {
    final ScenarioSituation resultMeasure1Situation = new ScenarioSituation();
    final CIMLKMeasureFeature feature1 = new CIMLKMeasureFeature();
    final CIMLKMeasure measure1 = new CIMLKMeasure();
    measure1.setGmlId(DEFAULT_MEASURE_ID);
    feature1.setProperties(measure1);
    resultMeasure1Situation.getCimlkMeasuresList().addAll(Arrays.asList(feature1));

    final ScenarioSituation resultMeasure2Situation = new ScenarioSituation();
    final CIMLKMeasureFeature feature2 = new CIMLKMeasureFeature();
    final CIMLKMeasure measure2 = new CIMLKMeasure();
    measure2.setGmlId(DEFAULT_MEASURE_ID);
    feature2.setProperties(measure2);
    resultMeasure2Situation.getCimlkMeasuresList().addAll(Arrays.asList(feature2));

    check(resultMeasure1Situation, resultMeasure2Situation);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_DUPLICATE_MEASURE_IDS);
  }

  @Test
  void testDispersionLineCorrect() {
    final Scenario scenario = new Scenario(Theme.RBL);
    final ScenarioSituation situation = new ScenarioSituation();
    final CIMLKDispersionLine dispersionLine = new CIMLKDispersionLine();
    dispersionLine.setCalculationPointGmlId(DEFAULT_POINT_ID);
    dispersionLine.setRoadGmlId(DEFAULT_ROAD_ID);
    final CIMLKDispersionLineFeature dispersionLineFeature = new CIMLKDispersionLineFeature();
    dispersionLineFeature.setProperties(dispersionLine);
    situation.getCimlkDispersionLinesList().add(dispersionLineFeature);

    final EmissionSourceFeature road = exampleSrm1Source(DEFAULT_ROAD_ID);
    final List<EmissionSourceFeature> sourceList = List.of(road);
    situation.getEmissionSourcesList().addAll(sourceList);
    scenario.getSituations().add(situation);

    final CalculationPointFeature point = examplePoint(DEFAULT_POINT_ID);
    scenario.getCustomPointsList().addAll(Arrays.asList(point));

    check(scenario);

    validateWarnings();
    validateErrors();
  }

  @Test
  void testDispersionLineWithSrm2Road() {
    final Scenario scenario = new Scenario(Theme.RBL);
    final ScenarioSituation situation = new ScenarioSituation();
    final CIMLKDispersionLine dispersionLine = new CIMLKDispersionLine();
    dispersionLine.setCalculationPointGmlId(DEFAULT_POINT_ID);
    dispersionLine.setRoadGmlId(DEFAULT_ROAD_ID);
    final CIMLKDispersionLineFeature dispersionLineFeature = new CIMLKDispersionLineFeature();
    dispersionLineFeature.setProperties(dispersionLine);
    situation.getCimlkDispersionLinesList().add(dispersionLineFeature);

    final EmissionSourceFeature road = exampleSrm2Source(DEFAULT_ROAD_ID);
    final List<EmissionSourceFeature> sourceList = List.of(road);
    situation.getEmissionSourcesList().addAll(sourceList);
    scenario.getSituations().add(situation);

    final CalculationPointFeature point = examplePoint(DEFAULT_POINT_ID);
    scenario.getCustomPointsList().addAll(Arrays.asList(point));

    check(scenario);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_REFERENCE_DISPERSION_LINE_MISSING_ROAD);
  }

  @Test
  void testDispersionLineWithWrongRoad() {
    final Scenario scenario = new Scenario(Theme.RBL);
    final ScenarioSituation situation = new ScenarioSituation();
    final CIMLKDispersionLine dispersionLine = new CIMLKDispersionLine();
    dispersionLine.setCalculationPointGmlId(DEFAULT_POINT_ID);
    dispersionLine.setRoadGmlId(DEFAULT_ROAD_ID);
    final CIMLKDispersionLineFeature dispersionLineFeature = new CIMLKDispersionLineFeature();
    dispersionLineFeature.setProperties(dispersionLine);
    situation.getCimlkDispersionLinesList().add(dispersionLineFeature);

    final EmissionSourceFeature road = exampleSrm1Source("9");
    final List<EmissionSourceFeature> sourceList = List.of(road);
    situation.getEmissionSourcesList().addAll(sourceList);
    scenario.getSituations().add(situation);

    final CalculationPointFeature point = examplePoint(DEFAULT_POINT_ID);
    scenario.getCustomPointsList().addAll(Arrays.asList(point));

    check(scenario);

    validateWarnings(ImaerExceptionReason.COHESION_ROAD_MISSING_DISPERSION_LINE);
    validateErrors(ImaerExceptionReason.COHESION_REFERENCE_DISPERSION_LINE_MISSING_ROAD);
  }

  @Test
  void testDispersionLineWithWrongPoint() {
    final Scenario scenario = new Scenario(Theme.RBL);
    final ScenarioSituation situation = new ScenarioSituation();
    final CIMLKDispersionLine dispersionLine = new CIMLKDispersionLine();
    dispersionLine.setCalculationPointGmlId(DEFAULT_POINT_ID);
    dispersionLine.setRoadGmlId(DEFAULT_ROAD_ID);
    final CIMLKDispersionLineFeature dispersionLineFeature = new CIMLKDispersionLineFeature();
    dispersionLineFeature.setProperties(dispersionLine);
    situation.getCimlkDispersionLinesList().add(dispersionLineFeature);

    final EmissionSourceFeature road = exampleSrm1Source(DEFAULT_ROAD_ID);
    final List<EmissionSourceFeature> sourceList = List.of(road);
    situation.getEmissionSourcesList().addAll(sourceList);
    scenario.getSituations().add(situation);

    final CalculationPointFeature point = examplePoint("8");
    scenario.getCustomPointsList().addAll(Arrays.asList(point));

    check(scenario);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_REFERENCE_DISPERSION_LINE_MISSING_POINT);
  }

  @Test
  void testDispersionLineNotPerpendicular() {
    final Scenario scenario = new Scenario(Theme.RBL);
    final ScenarioSituation situation = new ScenarioSituation();
    final CIMLKDispersionLine dispersionLine = new CIMLKDispersionLine();
    dispersionLine.setCalculationPointGmlId(DEFAULT_POINT_ID);
    dispersionLine.setRoadGmlId(DEFAULT_ROAD_ID);
    final CIMLKDispersionLineFeature dispersionLineFeature = new CIMLKDispersionLineFeature();
    dispersionLineFeature.setProperties(dispersionLine);
    situation.getCimlkDispersionLinesList().add(dispersionLineFeature);

    final EmissionSourceFeature road = exampleSrm1Source(DEFAULT_ROAD_ID);
    final List<EmissionSourceFeature> sourceList = List.of(road);
    situation.getEmissionSourcesList().addAll(sourceList);
    scenario.getSituations().add(situation);

    final CalculationPointFeature point = examplePoint(DEFAULT_POINT_ID);
    point.setGeometry(new Point(-200, -100));
    scenario.getCustomPointsList().addAll(Arrays.asList(point));

    check(scenario);

    validateWarnings(ImaerExceptionReason.COHESION_DISPERSION_LINE_NOT_PERPENDICULAR);
    validateErrors();
  }

  @Test
  void testSrm1RoadWithoutDispersionLine() {
    final Scenario scenario = new Scenario(Theme.RBL);
    final ScenarioSituation situation = new ScenarioSituation();
    final EmissionSourceFeature road = exampleSrm1Source(DEFAULT_ROAD_ID);
    final List<EmissionSourceFeature> sourceList = List.of(road);
    situation.getEmissionSourcesList().addAll(sourceList);
    scenario.getSituations().add(situation);

    final CalculationPointFeature point = examplePoint(DEFAULT_POINT_ID);
    point.setGeometry(new Point(-200, -100));
    scenario.getCustomPointsList().addAll(Arrays.asList(point));

    check(scenario);

    validateWarnings(ImaerExceptionReason.COHESION_ROAD_MISSING_DISPERSION_LINE);
    validateErrors();
  }

  @Test
  void testDuplicateDispersionLinesInSameImport() {
    final Scenario scenario = new Scenario(Theme.RBL);
    final ScenarioSituation situation = new ScenarioSituation();
    final CIMLKDispersionLine dispersionLine1 = new CIMLKDispersionLine();
    dispersionLine1.setCalculationPointGmlId(DEFAULT_POINT_ID);
    dispersionLine1.setRoadGmlId(DEFAULT_ROAD_ID);
    final CIMLKDispersionLineFeature dispersionLineFeature1 = new CIMLKDispersionLineFeature();
    dispersionLineFeature1.setProperties(dispersionLine1);
    situation.getCimlkDispersionLinesList().add(dispersionLineFeature1);
    final CIMLKDispersionLine dispersionLine2 = new CIMLKDispersionLine();
    dispersionLine2.setCalculationPointGmlId(DEFAULT_POINT_ID);
    dispersionLine2.setRoadGmlId(DEFAULT_ROAD_ID);
    final CIMLKDispersionLineFeature dispersionLineFeature2 = new CIMLKDispersionLineFeature();
    dispersionLineFeature2.setProperties(dispersionLine2);
    situation.getCimlkDispersionLinesList().add(dispersionLineFeature2);

    final EmissionSourceFeature road = exampleSrm1Source(DEFAULT_ROAD_ID);
    final List<EmissionSourceFeature> sourceList = List.of(road);
    situation.getEmissionSourcesList().addAll(sourceList);
    scenario.getSituations().add(situation);

    final CalculationPointFeature point = examplePoint(DEFAULT_POINT_ID);
    scenario.getCustomPointsList().addAll(Arrays.asList(point));

    check(scenario);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_DUPLICATE_DISPERSION_LINES);
  }

  @Test
  void testDuplicateDispersionLinesDifferentImport() {
    final Scenario scenario = new Scenario(Theme.RBL);
    final ScenarioSituation situation1 = new ScenarioSituation();
    final CIMLKDispersionLine dispersionLine1 = new CIMLKDispersionLine();
    dispersionLine1.setCalculationPointGmlId(DEFAULT_POINT_ID);
    dispersionLine1.setRoadGmlId(DEFAULT_ROAD_ID);
    final CIMLKDispersionLineFeature dispersionLineFeature1 = new CIMLKDispersionLineFeature();
    dispersionLineFeature1.setProperties(dispersionLine1);
    situation1.getCimlkDispersionLinesList().add(dispersionLineFeature1);
    scenario.getSituations().add(situation1);

    final ScenarioSituation situation2 = new ScenarioSituation();
    final CIMLKDispersionLine dispersionLine2 = new CIMLKDispersionLine();
    dispersionLine2.setCalculationPointGmlId(DEFAULT_POINT_ID);
    dispersionLine2.setRoadGmlId(DEFAULT_ROAD_ID);
    final CIMLKDispersionLineFeature dispersionLineFeature2 = new CIMLKDispersionLineFeature();
    dispersionLineFeature2.setProperties(dispersionLine2);
    situation2.getCimlkDispersionLinesList().add(dispersionLineFeature2);

    final EmissionSourceFeature road = exampleSrm1Source(DEFAULT_ROAD_ID);
    final List<EmissionSourceFeature> sourceList = List.of(road);
    situation2.getEmissionSourcesList().addAll(sourceList);
    scenario.getSituations().add(situation2);

    final CalculationPointFeature point = examplePoint(DEFAULT_POINT_ID);
    scenario.getCustomPointsList().addAll(Arrays.asList(point));

    check(scenario);

    validateWarnings();
    validateErrors(ImaerExceptionReason.COHESION_DUPLICATE_DISPERSION_LINES);
  }

  @Test
  void testCorrectionCorrect() {
    final Scenario scenario = new Scenario(Theme.RBL);
    final ScenarioSituation situation = new ScenarioSituation();
    final CIMLKCorrection correction = new CIMLKCorrection();
    correction.setCalculationPointGmlId(DEFAULT_POINT_ID);
    situation.getCimlkCorrections().add(correction);
    scenario.getSituations().add(situation);

    final CalculationPointFeature point = examplePoint(DEFAULT_POINT_ID);
    scenario.getCustomPointsList().addAll(Arrays.asList(point));

    check(scenario);

    validateWarnings();
    validateErrors();
  }

  @Test
  void testCorrectionWithWrongPoint() {
    final Scenario scenario = new Scenario(Theme.RBL);
    final ScenarioSituation situation = new ScenarioSituation();
    final CIMLKCorrection correction = new CIMLKCorrection();
    correction.setCalculationPointGmlId(DEFAULT_POINT_ID);
    situation.getCimlkCorrections().add(correction);
    scenario.getSituations().add(situation);

    final CalculationPointFeature point = examplePoint("8");
    scenario.getCustomPointsList().addAll(Arrays.asList(point));

    check(scenario);

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

  private void check(final ScenarioSituation... situations) {
    final Scenario scenario = new Scenario(Theme.RBL);
    Stream.of(situations).forEach(s -> scenario.getSituations().add(s));
    check(scenario);
  }

  private void check(final Scenario scenario) {
    final CimlkCohesionValidator checker = new CimlkCohesionValidator();
    checker.checkCohesion(scenario, errors, warnings);
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
