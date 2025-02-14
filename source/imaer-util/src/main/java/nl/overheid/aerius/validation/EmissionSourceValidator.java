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

import java.util.List;
import java.util.Optional;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.locationtech.jts.geom.Geometry;

import nl.overheid.aerius.shared.MathUtil;
import nl.overheid.aerius.shared.domain.v2.geojson.GeometryType;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioSituation;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceWithSubSources;
import nl.overheid.aerius.shared.domain.v2.source.base.AbstractSubSource;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.util.GeometryUtil;

public final class EmissionSourceValidator {

  private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
  private static final SubSourceExpectsEmissionsVisitor SUB_SOURCE_EMISSION_VISITOR = new SubSourceExpectsEmissionsVisitor();

  private EmissionSourceValidator() {
    // Util class
  }

  public static void validateSources(final List<EmissionSourceFeature> sources, final List<AeriusException> exceptions,
      final List<AeriusException> warnings, final ValidationHelper validationHelper) {
    final ValidationVisitor validationVisitor = new ValidationVisitor(exceptions, warnings, validationHelper);

    checkEmissionSourceListIntersections(sources, exceptions);
    validateContraints(exceptions, warnings, sources);
    validationVisitor.visitSources(sources);
  }

  public static void validateSourcesWithEmissions(final List<EmissionSourceFeature> sources, final List<AeriusException> exceptions,
      final List<AeriusException> warnings) {
    for (final EmissionSourceFeature feature : sources) {
      final EmissionSource source = feature.getProperties();
      if (source.getEmissions().isEmpty() || source.getEmissions().values().stream()
          .noneMatch(value -> value > 0)) {
        warnings.add(new AeriusException(ImaerExceptionReason.GML_SOURCE_NO_EMISSION, source.getLabel()));
      } else if (source instanceof final EmissionSourceWithSubSources<?> sourceWithSubSources
          && expectsSubSourceEmissions(feature)
          && sourceWithSubSources.getSubSources().stream()
              .anyMatch(EmissionSourceValidator::hasNoEmissions)) {
        warnings.add(new AeriusException(ImaerExceptionReason.SUB_SOURCE_NO_EMISSION, source.getLabel()));
      }
    }
  }

  private static boolean expectsSubSourceEmissions(final EmissionSourceFeature feature) {
    try {
      return feature.accept(SUB_SOURCE_EMISSION_VISITOR);
    } catch (final AeriusException e) {
      // Silently ignore, visitor implementation shouldn't throw this.
      return false;
    }
  }

  private static boolean hasNoEmissions(final AbstractSubSource subSource) {
    return subSource.getEmissions().values().stream().noneMatch(value -> value > 0);
  }

  private static void validateContraints(final List<AeriusException> exceptions, final List<AeriusException> warnings,
      final List<EmissionSourceFeature> esl) {
    for (final EmissionSourceFeature emissionSource : esl) {
      final String sourceLabel = emissionSource.getProperties().getLabel();
      final Set<ConstraintViolation<EmissionSourceFeature>> constraintViolations = VALIDATOR.validate(emissionSource);

      if (!constraintViolations.isEmpty()) {
        for (final ConstraintViolation<EmissionSourceFeature> cv : constraintViolations) {
          exceptions
              .add(new AeriusException(ImaerExceptionReason.SOURCE_VALIDATION_FAILED, sourceLabel, cv.getMessage(),
                  cv.getInvalidValue() == null ? "" : String.valueOf(cv.getInvalidValue())));
        }
      }
    }
  }

  /**
   * Checks if all geometries are not self intersecting.
   *
   * @param arrayList scenario situations to check
   * @throws AeriusException throws exception if a geometry intersects
   */
  public static void checkIntersections(final ScenarioSituation situation, final List<AeriusException> errors) {
    checkEmissionSourceListIntersections(situation.getEmissionSourcesList(), errors);
  }

  public static void checkEmissionSourceListIntersections(final List<EmissionSourceFeature> esl, final List<AeriusException> errors) {
    for (final EmissionSourceFeature emissionSource : esl) {
      try {
        final String sourceLabel = emissionSource.getProperties().getLabel();
        final Geometry geometry = toValidGeometry(emissionSource, sourceLabel);
        final AeriusException firstException = checkEmissionSourceIntersections(geometry, sourceLabel);

        if (firstException == null) {
          validateGeoZero(emissionSource.getGeometry().type(), geometry, sourceLabel).ifPresent(errors::add);
        } else {
          errors.add(firstException);
        }
      } catch (final AeriusException e) {
        errors.add(e);
      }
    }
  }

  private static Geometry toValidGeometry(final EmissionSourceFeature emissionSource, final String sourceLabel) throws AeriusException {
    try {
      return GeometryUtil.getGeometry(emissionSource.getGeometry());
    } catch (final AeriusException e) {
      throw new AeriusException(ImaerExceptionReason.GML_GEOMETRY_INVALID, sourceLabel);
    }
  }

  /**
   * Checks if all geometries are not self intersecting.
   *
   * @param arrayList Emission Source List to check
   * @returns AeriusException throws exception if a geometry intersects
   */
  private static AeriusException checkEmissionSourceIntersections(final Geometry geometry, final String sourceLabel) {
    AeriusException exception = null;
    if (GeometryUtil.hasIntersections(geometry)) {
      exception = new AeriusException(ImaerExceptionReason.GML_GEOMETRY_INTERSECTS, sourceLabel,
          geometry.toString());
    }
    return exception;
  }

  /**
   * Checks if all geometries length or surface > 0
   *
   * @param arrayList Emission Source List to check
   * @returns AeriusException throws exception if a geometry intersects
   */
  private static Optional<AeriusException> validateGeoZero(final GeometryType geometryType, final Geometry geometry, final String sourceLabel) {
    AeriusException exception = null;
    if (geometryType != null) {
      switch (geometryType) {
      case LINESTRING:
        if (geometry.getLength() <= 0) {
          exception = new AeriusException(ImaerExceptionReason.LIMIT_LINE_LENGTH_ZERO, sourceLabel, String.valueOf(geometry.getLength()));
        }
        break;
      case POLYGON:
        if (MathUtil.round(geometry.getArea()) <= 0) {
          exception = new AeriusException(ImaerExceptionReason.LIMIT_POLYGON_SURFACE_ZERO, sourceLabel, String.valueOf(geometry.getArea()));
        }
        break;
      default:
        // no checks for other geometries.
      }
    }
    return Optional.ofNullable(exception);
  }
}
