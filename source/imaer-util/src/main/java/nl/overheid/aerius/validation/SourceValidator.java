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

import nl.overheid.aerius.shared.domain.v2.characteristics.ADMSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.characteristics.OPSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.characteristics.SourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.geojson.IsFeature;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSource;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Base class for specific emission source validators.
 */
abstract class SourceValidator<T extends EmissionSource> {

  private final List<AeriusException> errors;
  private final List<AeriusException> warnings;

  SourceValidator(final List<AeriusException> errors, final List<AeriusException> warnings) {
    this.errors = errors;
    this.warnings = warnings;
  }

  /**
   * Validates the source. Any validation errors or warnings should be added to the errors/warnings list in the class.
   * Should return true if there are no validation errors.
   *
   * @param source The source data class
   * @param feature The source feature class
   * @return true if no validation errors
   */
  final boolean validate(final T source, final IsFeature feature) {
    boolean valid = validateGeometry(feature.getGeometry());
    valid = validateCharacteristics(source.getCharacteristics(), feature.getGeometry(), source.getGmlId()) && valid;
    valid = validate(source) && valid;
    return valid;
  }

  abstract boolean validate(T source);

  protected List<AeriusException> getErrors() {
    return errors;
  }

  protected List<AeriusException> getWarnings() {
    return warnings;
  }

  protected boolean validateGeometry(final Geometry geometry) {
    // By default any geometry type is valid.
    return true;
  }

  protected boolean validateCharacteristics(final SourceCharacteristics characteristics, final Geometry sourceGeometry, final String sourceId) {
    if (characteristics instanceof final OPSSourceCharacteristics opsChars) {
      return new OPSCharacteristicsValidator(errors, warnings, sourceId).validate(opsChars, sourceGeometry);
    } else if (characteristics instanceof final ADMSSourceCharacteristics admsChars) {
      return new ADMSCharacteristicsValidator(errors, warnings, sourceId).validate(admsChars, sourceGeometry);
    } else {
      return true;
    }
  }

}
