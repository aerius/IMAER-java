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
package nl.overheid.aerius.gml.base.source.road.v40;

import java.util.ArrayList;
import java.util.List;

import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.shared.domain.v2.source.SRM1RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.SRM1LinearReference;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
public class GML2SRM1Road<T extends IsGmlSRM1Road> extends GML2SRMRoad<T, SRM1RoadEmissionSource> {

  /**
   * @param conversionData
   *          The conversion data to use.
   */
  public GML2SRM1Road(final GMLConversionData conversionData) {
    super(conversionData);
  }

  @Override
  protected SRM1RoadEmissionSource construct() {
    return new SRM1RoadEmissionSource();
  }

  @Override
  protected void setSpecificVariables(final IsGmlSRM1Road source, final SRM1RoadEmissionSource emissionSource) {
    // Overwrite the road type based on sector with the one based on speed profile
    emissionSource.setRoadTypeCode(source.getSpeedProfile().getRoadTypeCode());
  }

  @Override
  protected void setOptionalVariables(final IsGmlSRM1Road source, final SRM1RoadEmissionSource emissionSource) throws AeriusException {
    if (source.getTunnelFactor() != null) {
      emissionSource.setTunnelFactor(source.getTunnelFactor());
    }

    if (!source.getDynamicSegments().isEmpty()) {
      setDynamicSegments(source, emissionSource);
    }
  }

  private void setDynamicSegments(final IsGmlSRM1Road source, final SRM1RoadEmissionSource emissionSource) throws AeriusException {
    final ArrayList<SRM1LinearReference> dynamicSegments = new ArrayList<>();

    for (final IsGmlProperty<IsGmlSRM1RoadLinearReference> partialChangeProperty : source.getDynamicSegments()) {
      final IsGmlSRM1RoadLinearReference partialChange = partialChangeProperty.getProperty();
      handleSegment(partialChange, dynamicSegments);
    }

    emissionSource.setPartialChanges(dynamicSegments);
  }

  private void handleSegment(final IsGmlSRM1RoadLinearReference dynamicSegmentGML, final List<SRM1LinearReference> dynamicSegments)
      throws AeriusException {
    final double fromPosition = dynamicSegmentGML.getFromPosition();
    validatePositionFraction(fromPosition);

    final double toPosition = dynamicSegmentGML.getToPosition();
    validatePositionFraction(toPosition);

    final SRM1LinearReference dynamicSegment = new SRM1LinearReference();
    dynamicSegment.setFromPosition(fromPosition);
    dynamicSegment.setToPosition(toPosition);
    dynamicSegment.setTunnelFactor(dynamicSegmentGML.getTunnelFactor());

    dynamicSegments.add(dynamicSegment);
  }

  private void validatePositionFraction(final double position) throws AeriusException {
    if (position < 0 || position > 1) {
      throw new AeriusException(ImaerExceptionReason.GML_ROAD_SEGMENT_POSITION_NOT_FRACTION, String.valueOf(position));
    }
  }

}
