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
import nl.overheid.aerius.shared.domain.v2.geojson.GeometryType;
import nl.overheid.aerius.shared.domain.v2.source.MaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.MaritimeShipping;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

public class RouteMaritimeShippingValidator extends MaritimeShippingValidator<MaritimeShipping, MaritimeShippingEmissionSource> {

  RouteMaritimeShippingValidator(final List<AeriusException> errors, final List<AeriusException> warnings,
      final MaritimeShippingValidationHelper validationHelper) {
    super(errors, warnings, validationHelper);
  }

  @Override
  protected boolean validateGeometry(final Geometry geometry) {
    boolean valid = true;
    if (geometry.type() != GeometryType.LINESTRING) {
      getErrors().add(new AeriusException(ImaerExceptionReason.SHIPPING_ROUTE_GEOMETRY_NOT_ALLOWED));
      valid = false;
    }
    return valid;
  }

}
