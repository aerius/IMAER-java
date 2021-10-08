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

import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceWithSubSources;
import nl.overheid.aerius.shared.domain.v2.source.shipping.base.AbstractShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.base.IsStandardShipping;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

public class MaritimeShippingValidator<T extends AbstractShipping, S extends EmissionSourceWithSubSources<T>> extends SourceValidator<S> {

  private final MaritimeShippingValidationHelper validationHelper;

  MaritimeShippingValidator(final List<AeriusException> errors, final List<AeriusException> warnings,
      final MaritimeShippingValidationHelper validationHelper) {
    super(errors, warnings);
    this.validationHelper = validationHelper;
  }

  @Override
  boolean validate(final S source) {
    boolean valid = true;
    for (final AbstractShipping maritimeShipping : source.getSubSources()) {
      valid = validateShipping(maritimeShipping, source.getGmlId()) && valid;
    }
    return valid;
  }

  protected boolean validateShipping(final AbstractShipping subSource, final String sourceId) {
    boolean valid = true;
    if (subSource instanceof IsStandardShipping) {
      final String shipCode = ((IsStandardShipping) subSource).getShipCode();
      if (!validationHelper.isValidMaritimeShipCode(shipCode)) {
        getErrors().add(new AeriusException(ImaerExceptionReason.GML_UNKNOWN_SHIP_CODE, sourceId, shipCode));
        valid = false;
      }
    }
    return valid;
  }

}
