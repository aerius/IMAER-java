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
package nl.overheid.aerius.gml.v6_0.togml;

import java.util.ArrayList;
import java.util.List;

import nl.overheid.aerius.gml.v6_0.source.TimeUnit;
import nl.overheid.aerius.gml.v6_0.source.ship.MooringMaritimeShippingProperty;
import nl.overheid.aerius.shared.domain.v2.source.MooringMaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.CustomMooringMaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.MooringMaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.StandardMooringMaritimeShipping;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 *
 */
class MaritimeMooring2GML extends SpecificSource2GML<MooringMaritimeShippingEmissionSource> {

  @Override
  public nl.overheid.aerius.gml.v6_0.source.EmissionSource convert(final MooringMaritimeShippingEmissionSource emissionSource)
      throws AeriusException {
    final nl.overheid.aerius.gml.v6_0.source.ship.MooringMaritimeShippingEmissionSource returnSource = new nl.overheid.aerius.gml.v6_0.source.ship.MooringMaritimeShippingEmissionSource();
    final List<MooringMaritimeShipping> vesselGroups = emissionSource.getSubSources();
    final List<MooringMaritimeShippingProperty> mooringMaritimeShippings = new ArrayList<>(vesselGroups.size());

    for (final MooringMaritimeShipping vesselGroup : vesselGroups) {
      final nl.overheid.aerius.gml.v6_0.source.ship.MooringMaritimeShipping gmlShip = determineType(vesselGroup);
      gmlShip.setDescription(vesselGroup.getDescription());
      gmlShip.setAverageResidenceTime(vesselGroup.getAverageResidenceTime());
      gmlShip.setShorePowerFactor(vesselGroup.getShorePowerFactor());
      gmlShip.setShipsPerTimeUnit(vesselGroup.getShipsPerTimeUnit());
      gmlShip.setTimeUnit(TimeUnit.from(vesselGroup.getTimeUnit()));
      //we're not adding emissionfactor/description to avoid impression that it will be used on import.
      mooringMaritimeShippings.add(new MooringMaritimeShippingProperty(gmlShip));
    }
    returnSource.setShips(mooringMaritimeShippings);
    return returnSource;
  }

  private nl.overheid.aerius.gml.v6_0.source.ship.MooringMaritimeShipping determineType(final MooringMaritimeShipping maritimeShipping) {
    nl.overheid.aerius.gml.v6_0.source.ship.MooringMaritimeShipping gmlMaritimeShipping;
    if (maritimeShipping instanceof StandardMooringMaritimeShipping) {
      gmlMaritimeShipping = toStandard((StandardMooringMaritimeShipping) maritimeShipping);
    } else if (maritimeShipping instanceof CustomMooringMaritimeShipping) {
      gmlMaritimeShipping = toCustom((CustomMooringMaritimeShipping) maritimeShipping);
    } else {
      throw new IllegalArgumentException("Unknown inland shipping type: " + maritimeShipping);
    }
    return gmlMaritimeShipping;
  }

  private nl.overheid.aerius.gml.v6_0.source.ship.MooringMaritimeShipping toStandard(final StandardMooringMaritimeShipping vesselGroup) {
    final nl.overheid.aerius.gml.v6_0.source.ship.StandardMooringMaritimeShipping gmlShip = new nl.overheid.aerius.gml.v6_0.source.ship.StandardMooringMaritimeShipping();
    gmlShip.setCode(vesselGroup.getShipCode());
    return gmlShip;
  }

  private nl.overheid.aerius.gml.v6_0.source.ship.MooringMaritimeShipping toCustom(final CustomMooringMaritimeShipping maritimeShipping) {
    final nl.overheid.aerius.gml.v6_0.source.ship.CustomMooringMaritimeShipping gmlShip = new nl.overheid.aerius.gml.v6_0.source.ship.CustomMooringMaritimeShipping();
    gmlShip.setEmissionProperties(Maritime2GMLUtil.toCustomEmissionProperties(maritimeShipping.getEmissionProperties()));
    return gmlShip;
  }

}
