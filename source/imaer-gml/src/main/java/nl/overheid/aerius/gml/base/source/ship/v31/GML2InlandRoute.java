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
package nl.overheid.aerius.gml.base.source.ship.v31;

import nl.overheid.aerius.gml.base.AbstractGML2Specific;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.source.InlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.InlandWaterway;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.StandardInlandShipping;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Conversion of GML inland route object to data object.
 */
public class GML2InlandRoute<T extends IsGmlInlandShippingEmissionSource> extends AbstractGML2Specific<T, InlandShippingEmissionSource> {

  /**
   * @param conversionData The conversion data to use.
   */
  public GML2InlandRoute(final GMLConversionData conversionData) {
    super(conversionData);
  }

  @Override
  public InlandShippingEmissionSource convert(final T source) throws AeriusException {
    final InlandShippingEmissionSource emissionValues = new InlandShippingEmissionSource();
    final InlandWaterway waterway = GML2InlandUtil.toInlandWaterway(source.getInlandWaterwayProperty());
    emissionValues.setWaterway(waterway);

    for (final IsGmlProperty<IsGmlInlandShipping> shippingProperty : source.getInlandShippings()) {
      addVesselGroup(emissionValues, shippingProperty.getProperty());
    }
    return emissionValues;
  }

  private void addVesselGroup(final nl.overheid.aerius.shared.domain.v2.source.InlandShippingEmissionSource emissionValues,
      final IsGmlInlandShipping shipping) {
    final StandardInlandShipping vesselGroupEmissionValues = new StandardInlandShipping();
    vesselGroupEmissionValues.setDescription(shipping.getDescription());
    vesselGroupEmissionValues.setShipCode(shipping.getCode());
    vesselGroupEmissionValues.setMovementsAtoBPerTimeUnit(shipping.getNumberOfShipsAtoBperTimeUnit());
    vesselGroupEmissionValues.setMovementsBtoAPerTimeUnit(shipping.getNumberOfShipsBtoAperTimeUnit());
    vesselGroupEmissionValues.setTimeUnitAtoB(TimeUnit.valueOf(shipping.getTimeUnitShipsAtoB().name()));
    vesselGroupEmissionValues.setTimeUnitBtoA(TimeUnit.valueOf(shipping.getTimeUnitShipsBtoA().name()));
    vesselGroupEmissionValues.setPercentageLadenAtoB(shipping.getPercentageLadenAtoB());
    vesselGroupEmissionValues.setPercentageLadenBtoA(shipping.getPercentageLadenBtoA());
    emissionValues.getSubSources().add(vesselGroupEmissionValues);
  }
}
