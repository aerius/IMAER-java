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
package nl.overheid.aerius.gml.base.source.ship;

import nl.overheid.aerius.gml.base.AbstractGML2Specific;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.source.InlandMaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MaritimeMaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.CustomMaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.MaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.ShippingMovementType;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.StandardMaritimeShipping;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Conversion of GML maritime route object to data object.
 */
public class GML2MaritimeRoute<T extends IsGmlMaritimeShippingEmissionSource> extends AbstractGML2Specific<T, MaritimeShippingEmissionSource> {

  /**
   * @param conversionData The conversion data to use.
   */
  public GML2MaritimeRoute(final GMLConversionData conversionData) {
    super(conversionData);
  }

  @Override
  public MaritimeShippingEmissionSource convert(final T source) throws AeriusException {
    final ShippingMovementType movementType = source.getMovementType();
    final MaritimeShippingEmissionSource emissionValues = determineType(movementType);
    emissionValues.setMovementType(movementType);
    emissionValues.setMooringAId(source.getMooringA() == null ? null : source.getMooringA().getReferredId());
    emissionValues.setMooringBId(source.getMooringB() == null ? null : source.getMooringB().getReferredId());
    for (final IsGmlProperty<IsGmlMaritimeShipping> maritimeShippingProperty : source.getMaritimeShippings()) {
      final IsGmlMaritimeShipping maritimeShipping = maritimeShippingProperty.getProperty();
      final MaritimeShipping vesselGroupEmissionValues = determineType(maritimeShipping);
      vesselGroupEmissionValues.setDescription(maritimeShipping.getDescription());
      vesselGroupEmissionValues.setMovementsPerTimeUnit(maritimeShipping.getShipsPerTimeUnit());
      vesselGroupEmissionValues.setTimeUnit(TimeUnit.valueOf(maritimeShipping.getTimeUnit().name()));
      emissionValues.getSubSources().add(vesselGroupEmissionValues);
    }
    return emissionValues;
  }

  private MaritimeShippingEmissionSource determineType(final ShippingMovementType movementType) {
    final MaritimeShippingEmissionSource emissionSource;
    if (movementType == ShippingMovementType.MARITIME) {
      emissionSource = new MaritimeMaritimeShippingEmissionSource();
    } else {
      emissionSource = new InlandMaritimeShippingEmissionSource();
    }
    return emissionSource;
  }

  private MaritimeShipping determineType(final IsGmlMaritimeShipping gmlShipping) {
    final MaritimeShipping vesselGroupEmissionValues;
    if (gmlShipping instanceof IsGmlStandardMaritimeShipping) {
      vesselGroupEmissionValues = getEmissionValues((IsGmlStandardMaritimeShipping) gmlShipping);
    } else if (gmlShipping instanceof IsGmlCustomMaritimeShipping) {
      vesselGroupEmissionValues = getEmissionValues((IsGmlCustomMaritimeShipping) gmlShipping);
    } else {
      throw new IllegalArgumentException("Instance not supported:" + gmlShipping.getClass().getCanonicalName());
    }
    return vesselGroupEmissionValues;
  }

  private MaritimeShipping getEmissionValues(final IsGmlStandardMaritimeShipping gmlShipping) {
    final StandardMaritimeShipping vesselGroupEmissionValues = new StandardMaritimeShipping();
    vesselGroupEmissionValues.setShipCode(gmlShipping.getCode());
    return vesselGroupEmissionValues;
  }

  private MaritimeShipping getEmissionValues(final IsGmlCustomMaritimeShipping gmlShipping) {
    final CustomMaritimeShipping vesselGroupEmissionValues = new CustomMaritimeShipping();
    vesselGroupEmissionValues.setEmissionProperties(GML2MaritimeUtil.getCustomProperties(gmlShipping.getEmissionProperties()));
    vesselGroupEmissionValues.setGrossTonnage(gmlShipping.getGrossTonnage());
    return vesselGroupEmissionValues;
  }

}
