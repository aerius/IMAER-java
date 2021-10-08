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

  boolean validate(final T source, final IsFeature feature) {
    boolean valid = validateGeometry(feature.getGeometry());
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

}
