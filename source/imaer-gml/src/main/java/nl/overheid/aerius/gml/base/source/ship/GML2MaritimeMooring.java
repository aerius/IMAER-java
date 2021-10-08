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
import nl.overheid.aerius.shared.domain.v2.source.MooringMaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.CustomMooringMaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.MooringMaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.StandardMooringMaritimeShipping;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Conversion of GML maritime mooring object to data object.
 */
public class GML2MaritimeMooring<T extends IsGmlMooringMaritimeShippingEmissionSource>
    extends AbstractGML2Specific<T, MooringMaritimeShippingEmissionSource> {

  /**
   * @param conversionData The conversion data to use.
   */
  public GML2MaritimeMooring(final GMLConversionData conversionData) {
    super(conversionData);
  }

  @Override
  public MooringMaritimeShippingEmissionSource convert(final T source) throws AeriusException {
    final MooringMaritimeShippingEmissionSource emissionValues = new MooringMaritimeShippingEmissionSource();
    for (final IsGmlProperty<IsGmlMooringMaritimeShipping> mooringMaritimeShippingProperty : source.getShips()) {
      final IsGmlMooringMaritimeShipping mooringMaritimeShipping = mooringMaritimeShippingProperty.getProperty();
      final MooringMaritimeShipping vesselGroupEmissionValues = determineType(mooringMaritimeShipping);
      vesselGroupEmissionValues.setDescription(mooringMaritimeShipping.getDescription());
      vesselGroupEmissionValues.setAverageResidenceTime(mooringMaritimeShipping.getAverageResidenceTime());
      vesselGroupEmissionValues.setShorePowerFactor(mooringMaritimeShipping.getShorePowerFactor());
      vesselGroupEmissionValues.setShipsPerTimeUnit(mooringMaritimeShipping.getShipsPerTimeUnit());
      vesselGroupEmissionValues.setTimeUnit(TimeUnit.valueOf(mooringMaritimeShipping.getTimeUnit().name()));
      emissionValues.getSubSources().add(vesselGroupEmissionValues);
    }
    return emissionValues;
  }

  private MooringMaritimeShipping determineType(final IsGmlMooringMaritimeShipping gmlShipping) {
    final MooringMaritimeShipping vesselGroupEmissionValues;
    if (gmlShipping instanceof IsGmlStandardMooringMaritimeShipping) {
      vesselGroupEmissionValues = getEmissionValues((IsGmlStandardMooringMaritimeShipping) gmlShipping);
    } else if (gmlShipping instanceof IsGmlCustomMooringMaritimeShipping) {
      vesselGroupEmissionValues = getEmissionValues((IsGmlCustomMooringMaritimeShipping) gmlShipping);
    } else {
      throw new IllegalArgumentException("Instance not supported:" + gmlShipping.getClass().getCanonicalName());
    }
    return vesselGroupEmissionValues;
  }

  private MooringMaritimeShipping getEmissionValues(final IsGmlStandardMooringMaritimeShipping gmlShipping) {
    final StandardMooringMaritimeShipping vesselGroupEmissionValues = new StandardMooringMaritimeShipping();
    vesselGroupEmissionValues.setShipCode(gmlShipping.getCode());
    return vesselGroupEmissionValues;
  }

  private MooringMaritimeShipping getEmissionValues(final IsGmlCustomMooringMaritimeShipping gmlShipping) {
    final CustomMooringMaritimeShipping vesselGroupEmissionValues = new CustomMooringMaritimeShipping();
    vesselGroupEmissionValues.setEmissionProperties(GML2MaritimeUtil.getCustomProperties(gmlShipping.getEmissionProperties()));
    return vesselGroupEmissionValues;
  }

}
