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
package nl.overheid.aerius.gml.v5_1.togml;

import java.util.ArrayList;
import java.util.List;

import nl.overheid.aerius.gml.v5_1.source.TimeUnit;
import nl.overheid.aerius.gml.v5_1.source.ship.MooringInlandShippingProperty;
import nl.overheid.aerius.shared.domain.v2.source.MooringInlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.CustomMooringInlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.MooringInlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.StandardMooringInlandShipping;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 *
 */
class InlandMooring2GML extends SpecificSource2GML<MooringInlandShippingEmissionSource> {

  @Override
  public nl.overheid.aerius.gml.v5_1.source.EmissionSource convert(final MooringInlandShippingEmissionSource emissionSource)
      throws AeriusException {
    final nl.overheid.aerius.gml.v5_1.source.ship.MooringInlandShippingEmissionSource returnSource = new nl.overheid.aerius.gml.v5_1.source.ship.MooringInlandShippingEmissionSource();
    final List<MooringInlandShipping> vesselGroups = emissionSource.getSubSources();
    final List<MooringInlandShippingProperty> mooringMaritimeShippings = new ArrayList<>(vesselGroups.size());

    for (final MooringInlandShipping vesselGroup : vesselGroups) {
      final nl.overheid.aerius.gml.v5_1.source.ship.MooringInlandShipping gmlShip = determineType(vesselGroup);
      gmlShip.setDescription(vesselGroup.getDescription());
      gmlShip.setAverageResidenceTime(vesselGroup.getAverageResidenceTime());
      gmlShip.setShorePowerFactor(vesselGroup.getShorePowerFactor());
      gmlShip.setShipsPerTimeUnit(vesselGroup.getShipsPerTimeUnit());
      gmlShip.setTimeUnit(TimeUnit.from(vesselGroup.getTimeUnit()));
      gmlShip.setPercentageLaden(vesselGroup.getPercentageLaden());
      //we're not adding emissionfactor/description to avoid impression that it will be used on import.
      mooringMaritimeShippings.add(new MooringInlandShippingProperty(gmlShip));
    }
    returnSource.setShips(mooringMaritimeShippings);
    return returnSource;
  }

  private nl.overheid.aerius.gml.v5_1.source.ship.MooringInlandShipping determineType(final MooringInlandShipping inlandShipping) {
    nl.overheid.aerius.gml.v5_1.source.ship.MooringInlandShipping gmlInlandShipping;
    if (inlandShipping instanceof StandardMooringInlandShipping) {
      gmlInlandShipping = toStandard((StandardMooringInlandShipping) inlandShipping);
    } else if (inlandShipping instanceof CustomMooringInlandShipping) {
      gmlInlandShipping = toCustom((CustomMooringInlandShipping) inlandShipping);
    } else {
      throw new IllegalArgumentException("Unknown inland shipping type: " + inlandShipping);
    }
    return gmlInlandShipping;
  }

  private nl.overheid.aerius.gml.v5_1.source.ship.MooringInlandShipping toStandard(final StandardMooringInlandShipping vesselGroup) {
    final nl.overheid.aerius.gml.v5_1.source.ship.StandardMooringInlandShipping gmlShip = new nl.overheid.aerius.gml.v5_1.source.ship.StandardMooringInlandShipping();
    gmlShip.setCode(vesselGroup.getShipCode());
    return gmlShip;
  }

  private nl.overheid.aerius.gml.v5_1.source.ship.MooringInlandShipping toCustom(final CustomMooringInlandShipping vesselGroup) {
    final nl.overheid.aerius.gml.v5_1.source.ship.CustomMooringInlandShipping gmlShip = new nl.overheid.aerius.gml.v5_1.source.ship.CustomMooringInlandShipping();
    gmlShip.setEmissionProperties(Inland2GMLUtil.toCustomEmissionProperties(vesselGroup.getEmissionProperties()));
    return gmlShip;
  }

}
