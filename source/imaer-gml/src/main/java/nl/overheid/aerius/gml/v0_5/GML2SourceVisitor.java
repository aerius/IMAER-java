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
package nl.overheid.aerius.gml.v0_5;

import java.util.HashMap;

import nl.overheid.aerius.gml.base.AbstractGML2Specific;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGML2SourceVisitor;
import nl.overheid.aerius.gml.base.characteristics.GML2SourceCharacteristicsV31;
import nl.overheid.aerius.gml.base.geo.GML2Geometry;
import nl.overheid.aerius.gml.base.source.GML2Generic;
import nl.overheid.aerius.gml.base.source.lodging.GML2Farm;
import nl.overheid.aerius.gml.base.source.mobile.v31.GML2OffRoad;
import nl.overheid.aerius.gml.base.source.plan.GML2Plan;
import nl.overheid.aerius.gml.base.source.road.GML2SRM2RoadV10;
import nl.overheid.aerius.gml.base.source.ship.v31.GML2InlandMooringForceWaterway;
import nl.overheid.aerius.gml.base.source.ship.v31.GML2InlandRouteForceWaterway;
import nl.overheid.aerius.gml.base.source.ship.v31.GML2MaritimeMooring;
import nl.overheid.aerius.gml.base.source.ship.v31.GML2MaritimeRoute;
import nl.overheid.aerius.gml.v0_5.source.EmissionSource;
import nl.overheid.aerius.gml.v0_5.source.lodging.FarmLodgingEmissionSource;
import nl.overheid.aerius.gml.v0_5.source.mobile.OffRoadMobileEmissionSource;
import nl.overheid.aerius.gml.v0_5.source.plan.PlanEmissionSource;
import nl.overheid.aerius.gml.v0_5.source.road.SRM2RoadEmissionSource;
import nl.overheid.aerius.gml.v0_5.source.ship.InlandShippingEmissionSource;
import nl.overheid.aerius.gml.v0_5.source.ship.MaritimeShippingEmissionSource;
import nl.overheid.aerius.gml.v0_5.source.ship.MooringInlandShippingEmissionSource;
import nl.overheid.aerius.gml.v0_5.source.ship.MooringMaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
class GML2SourceVisitor implements IsGML2SourceVisitor<EmissionSource> {

  @SuppressWarnings("rawtypes") private final HashMap<Class<? extends EmissionSource>, AbstractGML2Specific> handlers = new HashMap<>();

  GML2SourceVisitor(final GMLConversionData conversionData, final GML2Geometry gml2Geometry) {
    handlers.put(EmissionSource.class, new GML2Generic<EmissionSource>(conversionData));
    handlers.put(FarmLodgingEmissionSource.class, new GML2Farm<FarmLodgingEmissionSource>(conversionData));
    handlers.put(MooringInlandShippingEmissionSource.class,
        new GML2InlandMooringForceWaterway<MooringInlandShippingEmissionSource>(conversionData, gml2Geometry));
    handlers.put(InlandShippingEmissionSource.class, new GML2InlandRouteForceWaterway<InlandShippingEmissionSource>(conversionData, gml2Geometry));
    handlers.put(MooringMaritimeShippingEmissionSource.class, new GML2MaritimeMooring<MooringMaritimeShippingEmissionSource>(conversionData));
    handlers.put(MaritimeShippingEmissionSource.class, new GML2MaritimeRoute<MaritimeShippingEmissionSource>(conversionData));
    handlers.put(OffRoadMobileEmissionSource.class, new GML2OffRoad<OffRoadMobileEmissionSource>(conversionData,
        new GML2SourceCharacteristicsV31(conversionData)));
    handlers.put(PlanEmissionSource.class, new GML2Plan<PlanEmissionSource>(conversionData));
    handlers.put(SRM2RoadEmissionSource.class, new GML2SRM2RoadV10<SRM2RoadEmissionSource>(conversionData));
  }

  @SuppressWarnings("unchecked")
  @Override
  public nl.overheid.aerius.shared.domain.v2.source.EmissionSource visit(final EmissionSource source) throws AeriusException {
    if (!handlers.containsKey(source.getClass())) {
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR, "Unknown how to treat this type/emissionvalue: "
          + source.getClass());
    }
    return handlers.get(source.getClass()).convert(source);
  }

}
