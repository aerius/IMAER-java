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
import nl.overheid.aerius.gml.v3_1.source.ship.MaritimeShippingProperty;
import nl.overheid.aerius.shared.domain.v2.source.MaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.MaritimeShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.StandardMaritimeShipping;

/**
 *
 */
class MaritimeRoute2GML extends SpecificSource2GML<MaritimeShippingEmissionSource> {

  @Override
  public nl.overheid.aerius.gml.v3_1.source.EmissionSource convert(final MaritimeShippingEmissionSource emissionSource) {
    final nl.overheid.aerius.gml.v3_1.source.ship.MaritimeShippingEmissionSource returnSource = new nl.overheid.aerius.gml.v3_1.source.ship.MaritimeShippingEmissionSource();
    final List<MaritimeShipping> vesselGroups = emissionSource.getSubSources();
    final List<MaritimeShippingProperty> maritimeShippings = new ArrayList<>(vesselGroups.size());

    for (final MaritimeShipping vesselGroup : vesselGroups) {
      final nl.overheid.aerius.gml.v3_1.source.ship.MaritimeShipping gmlShip = new nl.overheid.aerius.gml.v3_1.source.ship.MaritimeShipping();
      if (vesselGroup instanceof StandardMaritimeShipping) {
        gmlShip.setCode(((StandardMaritimeShipping) vesselGroup).getShipCode());
      }
      gmlShip.setShipsPerTimeUnit(vesselGroup.getMovementsPerTimeUnit());
      gmlShip.setTimeUnit(TimeUnit.from(vesselGroup.getTimeUnit()));
      gmlShip.setDescription(vesselGroup.getDescription());
      maritimeShippings.add(new MaritimeShippingProperty(gmlShip));
    }
    returnSource.setMovementType(emissionSource.getMovementType());
    returnSource.setMaritimeShippings(maritimeShippings);
    return returnSource;
  }

}
