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

import nl.aerius.shared.domain.geojson.Geometry;
import nl.aerius.shared.domain.geojson.GeometryType;
import nl.overheid.aerius.shared.domain.v2.source.InlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.base.IsStandardShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.InlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.InlandWaterway;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

public class RouteInlandShippingValidator extends InlandShippingValidator<InlandShippingEmissionSource, InlandShipping> {

  RouteInlandShippingValidator(final List<AeriusException> errors, final List<AeriusException> warnings,
      final InlandShippingValidationHelper validationHelper) {
    super(errors, warnings, validationHelper);
  }

  @Override
  boolean validate(final InlandShippingEmissionSource source) {
    boolean valid = true;
    for (final InlandShipping inlandShipping : source.getSubSources()) {
      valid = validateShipping(source, inlandShipping) && valid;
    }
    return valid;
  }

  @Override
  protected boolean validateSpecifics(final InlandShippingEmissionSource source, final InlandShipping subSource) {
    return validateWaterway(source, subSource, Optional.ofNullable(source.getWaterway()));
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

  private boolean validateWaterway(final InlandShippingEmissionSource source, final InlandShipping subSource,
      final Optional<InlandWaterway> waterway) {
    boolean valid = true;
    if (subSource instanceof IsStandardShipping) {
      valid = validateWaterwayStandard(source, (IsStandardShipping) subSource, waterway);
    }
    return valid;
  }

  private boolean validateWaterwayStandard(final InlandShippingEmissionSource source, final IsStandardShipping subSource,
      final Optional<InlandWaterway> waterway) {
    final String sourceId = source.getGmlId();
    final String shipCode = subSource.getShipCode();
    boolean valid = true;
    if (waterway.isPresent()) {
      final String waterwayCode = waterway.get().getWaterwayCode();
      if (!validationHelper.isValidInlandWaterwayCode(waterwayCode)) {
        getErrors().add(new AeriusException(ImaerExceptionReason.GML_UNKNOWN_WATERWAY_CODE, sourceId, waterwayCode));
        valid = false;
      } else if (!validationHelper.isValidInlandShipWaterwayCombination(shipCode, waterwayCode)) {
        getErrors().add(new AeriusException(ImaerExceptionReason.GML_INVALID_SHIP_FOR_WATERWAY, sourceId, shipCode, waterwayCode));
        valid = false;
      }
    } else {
      getWarnings().add(new AeriusException(ImaerExceptionReason.GML_INLAND_WATERWAY_NOT_SET, sourceId));
    }
    return valid;
  }

}
