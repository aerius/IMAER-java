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
import nl.overheid.aerius.shared.domain.v2.source.SRM2RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.RoadSideBarrier;
import nl.overheid.aerius.shared.domain.v2.source.road.SRM2LinearReference;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
public class GML2SRM2Road<T extends IsGmlSRM2Road> extends GML2SRMRoad<T, SRM2RoadEmissionSource> {

  /**
   * @param conversionData
   *          The conversion data to use.
   */
  public GML2SRM2Road(final GMLConversionData conversionData) {
    super(conversionData);
  }

  @Override
  protected SRM2RoadEmissionSource construct() {
    return new SRM2RoadEmissionSource();
  }

  @Override
  protected void setSpecificVariables(final T source, final SRM2RoadEmissionSource emissionSource) {
    emissionSource.setFreeway(source.isFreeway());
  }

  @Override
  protected void setOptionalVariables(final T source, final SRM2RoadEmissionSource emissionSource) throws AeriusException {
    if (source.getTunnelFactor() != null) {
      emissionSource.setTunnelFactor(source.getTunnelFactor());
    }
    if (source.getElevation() != null) {
      emissionSource.setElevation(source.getElevation());
    }
    if (source.getElevationHeight() != null) {
      emissionSource.setElevationHeight(source.getElevationHeight());
    }

    if (source.getBarrierLeft() != null) {
      emissionSource.setBarrierLeft(getRoadSideBarrier(source.getBarrierLeft()));
    }
    if (source.getBarrierRight() != null) {
      emissionSource.setBarrierRight(getRoadSideBarrier(source.getBarrierRight()));
    }

    if (!source.getDynamicSegments().isEmpty()) {
      setDynamicSegments(source, emissionSource);
    }
  }

  private void setDynamicSegments(final T source, final SRM2RoadEmissionSource emissionSource) throws AeriusException {
    final ArrayList<SRM2LinearReference> dynamicSegments = new ArrayList<>();

    for (final IsGmlProperty<IsGmlSRM2RoadLinearReference> partialChangeProperty : source.getDynamicSegments()) {
      final IsGmlSRM2RoadLinearReference partialChange = partialChangeProperty.getProperty();
      handleSegment(partialChange, dynamicSegments);
    }

    emissionSource.setPartialChanges(dynamicSegments);
  }

  private void handleSegment(final IsGmlSRM2RoadLinearReference dynamicSegmentGML, final List<SRM2LinearReference> dynamicSegments)
      throws AeriusException {
    final double fromPosition = toDynamicSegmentationFraction(dynamicSegmentGML.getFromPosition());
    validatePositionFraction(fromPosition);

    final double toPosition = toDynamicSegmentationFraction(dynamicSegmentGML.getToPosition());
    validatePositionFraction(toPosition);

    final SRM2LinearReference dynamicSegment = new SRM2LinearReference();
    dynamicSegment.setFromPosition(fromPosition);
    dynamicSegment.setToPosition(toPosition);
    dynamicSegment.setFreeway(dynamicSegmentGML.isFreeway());
    dynamicSegment.setTunnelFactor(dynamicSegmentGML.getTunnelFactor());
    dynamicSegment.setElevation(dynamicSegmentGML.getElevation());
    dynamicSegment.setElevationHeight(dynamicSegmentGML.getElevationHeight());

    if (dynamicSegmentGML.getBarrierLeft() != null) {
      dynamicSegment.setBarrierLeft(getRoadSideBarrier(dynamicSegmentGML.getBarrierLeft()));
    }

    if (dynamicSegmentGML.getBarrierRight() != null) {
      dynamicSegment.setBarrierRight(getRoadSideBarrier(dynamicSegmentGML.getBarrierRight()));
    }

    dynamicSegments.add(dynamicSegment);
  }

  protected double toDynamicSegmentationFraction(final double originalValue) {
    return originalValue;
  }

  private void validatePositionFraction(final double position) throws AeriusException {
    if (position < 0 || position > 1) {
      throw new AeriusException(ImaerExceptionReason.GML_ROAD_SEGMENT_POSITION_NOT_FRACTION, String.valueOf(position));
    }
  }

  private RoadSideBarrier getRoadSideBarrier(final IsGmlProperty<IsGmlRoadSideBarrier> barrierProperty) {
    final IsGmlRoadSideBarrier gmlBarrier = barrierProperty.getProperty();
    final RoadSideBarrier barrier = new RoadSideBarrier();
    barrier.setBarrierType(gmlBarrier.getBarrierType());
    barrier.setHeight(gmlBarrier.getHeight());
    barrier.setDistance(gmlBarrier.getDistance());
    return barrier;
  }
}
