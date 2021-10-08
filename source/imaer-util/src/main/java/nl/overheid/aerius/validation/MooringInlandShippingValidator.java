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

import nl.overheid.aerius.shared.domain.v2.source.MooringInlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.MooringInlandShipping;
import nl.overheid.aerius.shared.exception.AeriusException;

public class MooringInlandShippingValidator extends InlandShippingValidator<MooringInlandShippingEmissionSource, MooringInlandShipping> {

  MooringInlandShippingValidator(final List<AeriusException> errors, final List<AeriusException> warnings,
      final InlandShippingValidationHelper validationHelper) {
    super(errors, warnings, validationHelper);
  }

  @Override
  boolean validate(final MooringInlandShippingEmissionSource source) {
    boolean valid = true;
    for (final MooringInlandShipping inlandShipping : source.getSubSources()) {
      valid = validateShipping(source, inlandShipping) && valid;
    }
    return valid;
  }

  @Override
  protected boolean validateSpecifics(final MooringInlandShippingEmissionSource source, final MooringInlandShipping subSource) {
    // Nothing to validate for now
    return true;
  }

}
