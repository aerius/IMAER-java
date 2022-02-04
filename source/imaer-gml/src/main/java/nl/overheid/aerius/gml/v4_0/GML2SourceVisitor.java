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
package nl.overheid.aerius.gml.v4_0;

import java.util.HashMap;

import nl.overheid.aerius.gml.base.AbstractGML2Specific;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGML2SourceVisitor;
import nl.overheid.aerius.gml.base.characteristics.GML2SourceCharacteristics;
import nl.overheid.aerius.gml.base.source.GML2Generic;
import nl.overheid.aerius.gml.base.source.farmland.GML2Farmland;
import nl.overheid.aerius.gml.base.source.lodging.GML2Farm;
import nl.overheid.aerius.gml.base.source.mobile.v40.GML2OffRoad;
import nl.overheid.aerius.gml.base.source.plan.GML2Plan;
import nl.overheid.aerius.gml.base.source.road.v40.GML2SRM1Road;
import nl.overheid.aerius.gml.base.source.road.v40.GML2SRM2Road;
import nl.overheid.aerius.gml.base.source.ship.GML2InlandMooring;
import nl.overheid.aerius.gml.base.source.ship.GML2InlandRoute;
import nl.overheid.aerius.gml.base.source.ship.GML2MaritimeMooring;
import nl.overheid.aerius.gml.base.source.ship.GML2MaritimeRoute;
import nl.overheid.aerius.gml.v4_0.source.EmissionSource;
import nl.overheid.aerius.gml.v4_0.source.farmland.FarmlandEmissionSource;
import nl.overheid.aerius.gml.v4_0.source.lodging.FarmLodgingEmissionSource;
import nl.overheid.aerius.gml.v4_0.source.mobile.OffRoadMobileEmissionSource;
import nl.overheid.aerius.gml.v4_0.source.plan.PlanEmissionSource;
import nl.overheid.aerius.gml.v4_0.source.road.SRM1Road;
import nl.overheid.aerius.gml.v4_0.source.road.SRM2Road;
import nl.overheid.aerius.gml.v4_0.source.ship.InlandShippingEmissionSource;
import nl.overheid.aerius.gml.v4_0.source.ship.MaritimeShippingEmissionSource;
import nl.overheid.aerius.gml.v4_0.source.ship.MooringInlandShippingEmissionSource;
import nl.overheid.aerius.gml.v4_0.source.ship.MooringMaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.characteristics.SourceCharacteristics;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
class GML2SourceVisitor<S extends SourceCharacteristics> implements IsGML2SourceVisitor<EmissionSource> {

  @SuppressWarnings("rawtypes") private final HashMap<Class<? extends EmissionSource>, AbstractGML2Specific> handlers = new HashMap<>();

  GML2SourceVisitor(final GMLConversionData conversionData, final GML2SourceCharacteristics<S> gml2SourceCharacteristics) {
    handlers.put(EmissionSource.class, new GML2Generic<EmissionSource>(conversionData));
    handlers.put(FarmLodgingEmissionSource.class, new GML2Farm<FarmLodgingEmissionSource>(conversionData));
    handlers.put(FarmlandEmissionSource.class, new GML2Farmland<FarmlandEmissionSource>(conversionData));
    handlers.put(MooringInlandShippingEmissionSource.class, new GML2InlandMooring<MooringInlandShippingEmissionSource>(conversionData));
    handlers.put(InlandShippingEmissionSource.class, new GML2InlandRoute<InlandShippingEmissionSource>(conversionData));
    handlers.put(MooringMaritimeShippingEmissionSource.class, new GML2MaritimeMooring<MooringMaritimeShippingEmissionSource>(conversionData));
    handlers.put(MaritimeShippingEmissionSource.class, new GML2MaritimeRoute<MaritimeShippingEmissionSource>(conversionData));
    handlers.put(OffRoadMobileEmissionSource.class, new GML2OffRoad<OffRoadMobileEmissionSource>(conversionData, gml2SourceCharacteristics));
    handlers.put(PlanEmissionSource.class, new GML2Plan<PlanEmissionSource>(conversionData));
    handlers.put(SRM2Road.class, new GML2SRM2Road<SRM2Road>(conversionData));
    handlers.put(SRM1Road.class, new GML2SRM1Road<SRM1Road>(conversionData));
  }

  @Override
  @SuppressWarnings("unchecked")
  public nl.overheid.aerius.shared.domain.v2.source.EmissionSource visit(final EmissionSource source) throws AeriusException {
    if (!handlers.containsKey(source.getClass())) {
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR, "Unknown how to treat this type/emissionvalue: "
          + source.getClass());
    }
    return handlers.get(source.getClass()).convert(source);
  }
}
