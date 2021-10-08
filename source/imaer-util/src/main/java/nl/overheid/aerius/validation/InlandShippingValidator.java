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

import nl.overheid.aerius.shared.domain.v2.source.EmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.base.AbstractShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.base.IsStandardShipping;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

public abstract class InlandShippingValidator<T extends EmissionSource, S extends AbstractShipping> extends SourceValidator<T> {

  protected final InlandShippingValidationHelper validationHelper;

  InlandShippingValidator(final List<AeriusException> errors, final List<AeriusException> warnings,
      final InlandShippingValidationHelper validationHelper) {
    super(errors, warnings);
    this.validationHelper = validationHelper;
  }

  protected boolean validateShipping(final T source, final S subSource) {
    boolean valid = true;
    if (subSource instanceof IsStandardShipping) {
      final String shipCode = ((IsStandardShipping) subSource).getShipCode();
      if (!validationHelper.isValidInlandShipCode(shipCode)) {
        getErrors().add(new AeriusException(ImaerExceptionReason.GML_UNKNOWN_SHIP_CODE, source.getGmlId(), shipCode));
        valid = false;
      } else {
        valid = validateSpecifics(source, subSource);
      }
    }
    return valid;
  }

  protected abstract boolean validateSpecifics(T source, S subSource);

}
