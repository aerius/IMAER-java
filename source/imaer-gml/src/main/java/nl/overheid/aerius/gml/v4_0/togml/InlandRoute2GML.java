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
package nl.overheid.aerius.gml.v4_0.togml;

import java.util.ArrayList;
import java.util.List;

import nl.overheid.aerius.gml.v4_0.base.ReferenceType;
import nl.overheid.aerius.gml.v4_0.source.TimeUnit;
import nl.overheid.aerius.gml.v4_0.source.ship.InlandShippingProperty;
import nl.overheid.aerius.shared.domain.v2.source.InlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.CustomInlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.InlandShipping;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.StandardInlandShipping;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.util.gml.GMLIdUtil;

/**
 *
 */
class InlandRoute2GML extends SpecificSource2GML<InlandShippingEmissionSource> {

  @Override
  public nl.overheid.aerius.gml.v4_0.source.EmissionSource convert(final InlandShippingEmissionSource emissionSource) throws AeriusException {
    final nl.overheid.aerius.gml.v4_0.source.ship.InlandShippingEmissionSource returnSource = new nl.overheid.aerius.gml.v4_0.source.ship.InlandShippingEmissionSource();
    final List<InlandShipping> vesselGroups = emissionSource.getSubSources();
    final List<InlandShippingProperty> ships = new ArrayList<>(vesselGroups.size());

    for (final InlandShipping vesselGroupEmissionValues : vesselGroups) {
      final nl.overheid.aerius.gml.v4_0.source.ship.InlandShipping gmlShip = determineType(vesselGroupEmissionValues);
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

    returnSource.setMooringA(toReferenceType(emissionSource.getMooringAId()));
    returnSource.setMooringB(toReferenceType(emissionSource.getMooringBId()));

    returnSource.setInlandShippings(ships);
    return returnSource;
  }

  private nl.overheid.aerius.gml.v4_0.source.ship.InlandShipping determineType(final InlandShipping inlandShipping) {
    nl.overheid.aerius.gml.v4_0.source.ship.InlandShipping gmlInlandShipping;
    if (inlandShipping instanceof StandardInlandShipping) {
      gmlInlandShipping = toStandard((StandardInlandShipping) inlandShipping);
    } else if (inlandShipping instanceof CustomInlandShipping) {
      gmlInlandShipping = toCustom((CustomInlandShipping) inlandShipping);
    } else {
      throw new IllegalArgumentException("Unknown inland shipping type: " + inlandShipping);
    }
    return gmlInlandShipping;
  }

  private nl.overheid.aerius.gml.v4_0.source.ship.InlandShipping toStandard(final StandardInlandShipping inlandShipping) {
    final nl.overheid.aerius.gml.v4_0.source.ship.StandardInlandShipping gmlShip = new nl.overheid.aerius.gml.v4_0.source.ship.StandardInlandShipping();
    gmlShip.setCode(inlandShipping.getShipCode());
    return gmlShip;
  }

  private nl.overheid.aerius.gml.v4_0.source.ship.InlandShipping toCustom(final CustomInlandShipping inlandShipping) {
    final nl.overheid.aerius.gml.v4_0.source.ship.CustomInlandShipping gmlShip = new nl.overheid.aerius.gml.v4_0.source.ship.CustomInlandShipping();
    gmlShip.setEmissionPropertiesAtoB(Inland2GMLUtil.toCustomEmissionProperties(inlandShipping.getEmissionPropertiesAtoB()));
    gmlShip.setEmissionPropertiesBtoA(Inland2GMLUtil.toCustomEmissionProperties(inlandShipping.getEmissionPropertiesBtoA()));
    return gmlShip;
  }

  private ReferenceType toReferenceType(final String id) {
    final ReferenceType reference;
    if (id == null) {
      reference = null;
    } else {
      reference = new ReferenceType(null);
      final String gmlId = GMLIdUtil.toValidGmlId(id, GMLIdUtil.SOURCE_PREFIX);
      reference.setHref("#" + gmlId);
    }
    return reference;
  }

}
