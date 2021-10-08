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
package nl.overheid.aerius.gml.v3_1.togml;

import java.util.ArrayList;
import java.util.List;

import nl.overheid.aerius.gml.v3_1.source.TimeUnit;
import nl.overheid.aerius.gml.v3_1.source.ship.InlandShippingProperty;
import nl.overheid.aerius.shared.domain.v2.source.InlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.InlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.StandardInlandShipping;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 *
 */
class InlandRoute2GML extends SpecificSource2GML<InlandShippingEmissionSource> {

  @Override
  public nl.overheid.aerius.gml.v3_1.source.EmissionSource convert(final InlandShippingEmissionSource emissionSource) throws AeriusException {
    final nl.overheid.aerius.gml.v3_1.source.ship.InlandShippingEmissionSource returnSource = new nl.overheid.aerius.gml.v3_1.source.ship.InlandShippingEmissionSource();
    final List<InlandShipping> vesselGroups = emissionSource.getSubSources();
    final List<InlandShippingProperty> ships = new ArrayList<>(vesselGroups.size());

    for (final InlandShipping vesselGroupEmissionValues : vesselGroups) {
      final nl.overheid.aerius.gml.v3_1.source.ship.InlandShipping gmlShip = new nl.overheid.aerius.gml.v3_1.source.ship.InlandShipping();
      if (vesselGroupEmissionValues instanceof StandardInlandShipping) {
        gmlShip.setCode(((StandardInlandShipping) vesselGroupEmissionValues).getShipCode());
      }
      gmlShip.setDescription(vesselGroupEmissionValues.getDescription());
      gmlShip.setNumberOfShipsAtoBperTimeUnit(vesselGroupEmissionValues.getMovementsAtoBPerTimeUnit());
      gmlShip.setNumberOfShipsBtoAperTimeUnit(vesselGroupEmissionValues.getMovementsBtoAPerTimeUnit());
      gmlShip.setTimeUnitShipsAtoB(TimeUnit.from(vesselGroupEmissionValues.getTimeUnitAtoB()));
      gmlShip.setTimeUnitShipsBtoA(TimeUnit.from(vesselGroupEmissionValues.getTimeUnitBtoA()));
      gmlShip.setPercentageLadenAtoB(vesselGroupEmissionValues.getPercentageLadenAtoB());
      gmlShip.setPercentageLadenBtoA(vesselGroupEmissionValues.getPercentageLadenBtoA());
      //we're not adding emissionfactor/description to avoid impression that it will be used on import.
      ships.add(new InlandShippingProperty(gmlShip));
    }

    returnSource.setInlandWaterwayProperty(Inland2GMLUtil.getWaterway(emissionSource.getWaterway(), emissionSource.getGmlId()));

    returnSource.setInlandShippings(ships);
    return returnSource;
  }

}
