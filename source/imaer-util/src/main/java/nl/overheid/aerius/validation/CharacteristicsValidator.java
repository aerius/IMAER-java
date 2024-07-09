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

import nl.overheid.aerius.shared.domain.v2.characteristics.SourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Base class to validate source characteristics
 */
abstract class CharacteristicsValidator<T extends SourceCharacteristics> {

  protected final List<AeriusException> errors;
  protected final List<AeriusException> warnings;
  protected final String sourceId;

  CharacteristicsValidator(final List<AeriusException> errors, final List<AeriusException> warnings, final String sourceId) {
    this.errors = errors;
    this.warnings = warnings;
    this.sourceId = sourceId;
  }

  /**
   * Validates the source characteristics. Any validation errors or warnings should be added to the errors/warnings list in the class.
   * If no validation errors should return true.
   *
   * @param characteristics characteristics to validate
   * @param sourceGeometry the geometry of the associated source
   * @return true if no validation errors
   */
  abstract boolean validate(final T characteristics, final Geometry sourceGeometry);

}
