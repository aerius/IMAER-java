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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import nl.overheid.aerius.shared.domain.v2.geojson.Feature;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLCorrection;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLDispersionLine;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLMeasure;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPoint;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.scenario.Scenario;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioSituation;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.domain.v2.source.SRM1RoadEmissionSource;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.util.GeometryUtil;

/**
 * Validator to check cohesion of input related to RBL input data.
 */
public class RblCohesionValidator {

  private static class CohesionTracker {
    private final Map<String, EmissionSourceFeature> sources = new HashMap<>();
    private final Map<String, CalculationPointFeature> points = new HashMap<>();
    private final List<NSLMeasure> measures = new ArrayList<>();
    private final List<NSLCorrection> corrections = new ArrayList<>();
    private final List<NSLDispersionLine> dispersionLines = new ArrayList<>();

    private final Map<String, Integer> sourceIds = new HashMap<>();
    private final Map<String, Integer> calculationPointIds = new HashMap<>();
    private final Map<String, Integer> measureIds = new HashMap<>();
    private final Map<String, Integer> dispersionLineIds = new HashMap<>();
    private final Map<String, Integer> dispersionLinesPerRoadId = new HashMap<>();
    private final Set<String> srm1SourceIds = new HashSet<>();
    private final List<AeriusException> errors = new ArrayList<>();
    private final List<AeriusException> warnings = new ArrayList<>();

    public void add(final ScenarioSituation situation) {
      addSources(situation);
      addMeasures(situation);
      addDispersionLineIds(situation);
      this.corrections.addAll(situation.getNslCorrections());
    }

    public void addPoints(final List<CalculationPointFeature> calculationPoints) {
      calculationPoints.stream()
          .map(CalculationPointFeature::getProperties)
          .map(CalculationPoint::getGmlId)
          .forEach(calculationPointId -> calculationPointIds.merge(calculationPointId, 1, (oldValue, value) -> oldValue + value));
      points.putAll(calculationPoints.stream()
          .collect(Collectors.toMap(feature -> feature.getProperties().getGmlId(), Function.identity(), (oldValue, value) -> oldValue)));
    }

    public void addError(final AeriusException error) {
      errors.add(error);
    }

    public List<AeriusException> getErrors() {
      return errors;
    }

    public void addWarning(final AeriusException warning) {
      warnings.add(warning);
    }

    public List<AeriusException> getWarnings() {
      return warnings;
    }

    private void addSources(final ScenarioSituation situation) {
      final List<EmissionSourceFeature> importedSources = situation.getEmissionSourcesList();
      sources.putAll(importedSources.stream()
          .collect(Collectors.toMap(feature -> feature.getProperties().getGmlId(), Function.identity(), (oldValue, value) -> oldValue)));
      importedSources.stream()
          .map(EmissionSourceFeature::getProperties)
          .map(EmissionSource::getGmlId)
          .forEach(sourceId -> sourceIds.merge(sourceId, 1, (oldValue, value) -> oldValue + value));
      srm1SourceIds.addAll(importedSources.stream()
          .map(EmissionSourceFeature::getProperties)
          .filter(SRM1RoadEmissionSource.class::isInstance)
          .map(SRM1RoadEmissionSource.class::cast)
          .map(SRM1RoadEmissionSource::getGmlId)
          .collect(Collectors.toList()));
    }

    private void addMeasures(final ScenarioSituation situation) {
      final List<NSLMeasure> importedMeasures = situation.getNslMeasuresList().stream()
          .map(Feature::getProperties)
          .collect(Collectors.toList());
      importedMeasures.stream()
          .map(NSLMeasure::getGmlId)
          .forEach(measureId -> measureIds.merge(measureId, 1, (oldValue, value) -> oldValue + value));
      measures.addAll(importedMeasures);
    }

    private void addDispersionLineIds(final ScenarioSituation situation) {
      final List<NSLDispersionLine> importedDispersionLines = situation.getNslDispersionLinesList().stream()
          .map(Feature::getProperties)
          .collect(Collectors.toList());
      // Use a fake ID in this case to simulate the composite ID.
      importedDispersionLines.stream()
          .map(dispersionLine -> dispersionLine.getCalculationPointGmlId() + ID_SEPARATOR + dispersionLine.getRoadGmlId())
          .forEach(dispersionId -> dispersionLineIds.merge(dispersionId, 1, (oldValue, value) -> oldValue + value));
      importedDispersionLines.stream()
          .map(NSLDispersionLine::getRoadGmlId)
          .forEach(roadId -> dispersionLinesPerRoadId.merge(roadId, 1, (oldValue, value) -> oldValue + value));
      this.dispersionLines.addAll(importedDispersionLines);
    }
  }

  private static final String ID_SEPARATOR = "| |";

  /**
   * Checks cohesion of a list of {@link Scenario}.
   *
   * @param scenario Scenario to check cohesion
   * @param errors cohesion errors will be added to this list
   * @param warnings cohesion warnings will be added to this list
   */
  public void checkCohesion(final Scenario scenario, final List<AeriusException> errors, final List<AeriusException> warnings) {
    final CohesionTracker tracker = new CohesionTracker();
    scenario.getSituations().forEach(tracker::add);
    tracker.addPoints(scenario.getCustomPointsList());

    checkCohesion(tracker, errors, warnings);
  }

  private static void checkCohesion(final CohesionTracker tracker, final List<AeriusException> errors, final List<AeriusException> warnings) {
    checkDuplicateIds(tracker);
    checkDispersionLines(tracker);
    checkSrm1Roads(tracker);
    checkCorrections(tracker);
    errors.addAll(tracker.getErrors());
    warnings.addAll(tracker.getWarnings());
  }

  private static void checkDuplicateIds(final CohesionTracker tracker) {
    final List<String> duplicateSourceIds = determineDuplicates(tracker.sourceIds);
    for (final String duplicateSourceId : duplicateSourceIds) {
      tracker.addError(new AeriusException(ImaerExceptionReason.COHESION_DUPLICATE_SOURCE_IDS, duplicateSourceId));
    }
    final List<String> duplicateCalculationPointIds = determineDuplicates(tracker.calculationPointIds);
    for (final String duplicateCalculationPointId : duplicateCalculationPointIds) {
      tracker.addError(new AeriusException(ImaerExceptionReason.COHESION_DUPLICATE_POINT_IDS, duplicateCalculationPointId));
    }
    final List<String> duplicateMeasureIds = determineDuplicates(tracker.measureIds);
    for (final String duplicateMeasureId : duplicateMeasureIds) {
      tracker.addError(new AeriusException(ImaerExceptionReason.COHESION_DUPLICATE_MEASURE_IDS, duplicateMeasureId));
    }
    final List<String> duplicateDispersionLineIds = determineDuplicates(tracker.dispersionLineIds);
    for (final String duplicateDispersionLineId : duplicateDispersionLineIds) {
      final String[] idParts = duplicateDispersionLineId.split(ID_SEPARATOR);
      final String calculationPointId = idParts[0];
      final String segmentId = idParts[1];
      tracker.addError(new AeriusException(ImaerExceptionReason.COHESION_DUPLICATE_DISPERSION_LINES,
          calculationPointId, segmentId));
    }
  }

  private static <T> List<T> determineDuplicates(final Map<T, Integer> mapToTest) {
    return mapToTest.entrySet().stream()
        .filter(entry -> entry.getValue() > 1)
        .map(Entry::getKey)
        .sorted()
        .collect(Collectors.toList());
  }

  private static void checkDispersionLines(final CohesionTracker tracker) {
    final List<NSLDispersionLine> dispersionLineWithoutPoints = tracker.dispersionLines.stream()
        .filter(dispersionLine -> !tracker.calculationPointIds.containsKey(dispersionLine.getCalculationPointGmlId()))
        .collect(Collectors.toList());
    for (final NSLDispersionLine dispersionLineWithoutPoint : dispersionLineWithoutPoints) {
      tracker.addError(new AeriusException(ImaerExceptionReason.COHESION_REFERENCE_DISPERSION_LINE_MISSING_POINT,
          dispersionLineWithoutPoint.getCalculationPointGmlId(),
          dispersionLineWithoutPoint.getRoadGmlId()));
    }
    final List<NSLDispersionLine> dispersionLineWithoutSegments = tracker.dispersionLines.stream()
        .filter(dispersionLine -> !tracker.srm1SourceIds.contains(dispersionLine.getRoadGmlId()))
        .collect(Collectors.toList());
    for (final NSLDispersionLine dispersionLineWithoutSegment : dispersionLineWithoutSegments) {
      tracker.addError(new AeriusException(ImaerExceptionReason.COHESION_REFERENCE_DISPERSION_LINE_MISSING_ROAD,
          dispersionLineWithoutSegment.getCalculationPointGmlId(),
          dispersionLineWithoutSegment.getRoadGmlId()));
    }
    tracker.dispersionLines.forEach(dispersionLine -> checkDispersionlinePerpendicular(dispersionLine, tracker));
  }

  private static void checkDispersionlinePerpendicular(final NSLDispersionLine dispersionLine,
      final CohesionTracker tracker) {
    final EmissionSourceFeature sourceFeature = tracker.sources.get(dispersionLine.getRoadGmlId());
    final CalculationPointFeature pointFeature = tracker.points.get(dispersionLine.getCalculationPointGmlId());
    try {
      if (sourceFeature != null && pointFeature != null && isSrm1Source(sourceFeature.getProperties())
          && !GeometryUtil.isPerpendicularAlongLine(GeometryUtil.getGeometry(sourceFeature.getGeometry()), pointFeature.getGeometry())) {
        tracker.addWarning(new AeriusException(ImaerExceptionReason.COHESION_DISPERSION_LINE_NOT_PERPENDICULAR,
            dispersionLine.getCalculationPointGmlId(),
            dispersionLine.getRoadGmlId()));
      }
    } catch (final AeriusException e) {
      tracker.addError(e);
    }
  }

  private static boolean isSrm1Source(final EmissionSource source) {
    return source instanceof SRM1RoadEmissionSource;
  }

  private static void checkCorrections(final CohesionTracker tracker) {
    final List<String> missingCalculationPoints = tracker.corrections.stream()
        .map(NSLCorrection::getCalculationPointGmlId)
        .filter(calculationPointId -> !tracker.calculationPointIds.containsKey(calculationPointId))
        .collect(Collectors.toList());
    for (final String missingCalculationPoint : missingCalculationPoints) {
      tracker.addWarning(new AeriusException(ImaerExceptionReason.COHESION_REFERENCE_CORRECTION_MISSING_POINT,
          missingCalculationPoint));
    }
  }

  private static void checkSrm1Roads(final CohesionTracker tracker) {
    final List<String> srm1RoadsWithoutDispersionLines = tracker.srm1SourceIds.stream()
        .filter(srm1SourceId -> !tracker.dispersionLinesPerRoadId.containsKey(srm1SourceId))
        .collect(Collectors.toList());
    for (final String roadIdWithoutDispersionLine : srm1RoadsWithoutDispersionLines) {
      tracker.addWarning(new AeriusException(ImaerExceptionReason.COHESION_ROAD_MISSING_DISPERSION_LINE,
          roadIdWithoutDispersionLine));
    }
  }
}
