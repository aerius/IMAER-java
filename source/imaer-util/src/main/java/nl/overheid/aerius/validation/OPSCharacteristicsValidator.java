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

import nl.overheid.aerius.shared.domain.ops.OPSLimits;
import nl.overheid.aerius.shared.domain.v2.characteristics.HeatContentType;
import nl.overheid.aerius.shared.domain.v2.characteristics.OPSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Class to validate OPS source characteristics
 */
class OPSCharacteristicsValidator extends CharacteristicsValidator<OPSSourceCharacteristics> {

  OPSCharacteristicsValidator(final List<AeriusException> errors, final List<AeriusException> warnings, final String sourceId) {
    super(errors, warnings, sourceId);
  }

  @Override
  boolean validate(final OPSSourceCharacteristics characteristics, final Geometry sourceGeometry) {
    boolean valid = true;
    if (characteristics.getHeatContentType() == HeatContentType.NOT_FORCED) {
      valid = validateOPSForcedHeatContent(characteristics);
    } else if (characteristics.getHeatContentType() == HeatContentType.FORCED) {
      // No validations on forced properties (yet), consider it valid.
    } else {
      errors.add(new AeriusException(ImaerExceptionReason.MISSING_HEAT_CONTENT, sourceId));
      valid = false;
    }
    return valid;
  }

  private boolean validateOPSForcedHeatContent(final OPSSourceCharacteristics characteristics) {
    boolean valid = true;
    final Double heatContent = characteristics.getHeatContent();
    if (heatContent == null) {
      errors.add(new AeriusException(ImaerExceptionReason.MISSING_HEAT_CONTENT, sourceId));
      valid = false;
    } else if (heatContent < OPSLimits.SOURCE_HEAT_CONTENT_SENSIBLE_MINIMUM || heatContent > OPSLimits.SOURCE_HEAT_CONTENT_MAXIMUM) {
      errors.add(new AeriusException(ImaerExceptionReason.HEAT_CONTENT_OUT_OF_RANGE, sourceId,
          String.valueOf(characteristics.getHeatContent()), String.valueOf(OPSLimits.SOURCE_HEAT_CONTENT_SENSIBLE_MINIMUM),
          String.valueOf(OPSLimits.SOURCE_HEAT_CONTENT_MAXIMUM)));
      valid = false;
    }
    return valid;
  }

}
