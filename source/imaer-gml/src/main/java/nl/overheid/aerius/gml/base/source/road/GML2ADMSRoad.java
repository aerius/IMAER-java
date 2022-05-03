/*
 * Crown copyright
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
package nl.overheid.aerius.gml.base.source.road;

import java.util.function.Consumer;
import java.util.function.Supplier;

import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.characteristics.IsGmlReferenceDiurnalVariation;
import nl.overheid.aerius.gml.base.characteristics.IsGmlStandardDiurnalVariation;
import nl.overheid.aerius.shared.domain.v2.source.ADMSRoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.ADMSRoadSideBarrier;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 *
 */
public class GML2ADMSRoad<T extends IsGmlADMSRoad> extends GML2Road<T, ADMSRoadEmissionSource> {

  /**
   * @param conversionData
   *          The conversion data to use.
   */
  public GML2ADMSRoad(final GMLConversionData conversionData) {
    super(conversionData);
  }

  @Override
  protected ADMSRoadEmissionSource construct() {
    return new ADMSRoadEmissionSource();
  }

  @Override
  protected void setSpecificVariables(final T source, final ADMSRoadEmissionSource emissionSource) {
    emissionSource.setWidth(source.getWidth());
    emissionSource.setElevation(source.getElevation());
    emissionSource.setGradient(source.getGradient());
    emissionSource.setCoverage(source.getCoverage());
  }

  @Override
  protected void setOptionalVariables(final T source, final ADMSRoadEmissionSource emissionSource) throws AeriusException {
    setRoadSideBarrier(source::getBarrierLeft, emissionSource::setBarrierLeft);
    setRoadSideBarrier(source::getBarrierRight, emissionSource::setBarrierRight);
    setDiurnalVariation(source, emissionSource);
  }

  private void setRoadSideBarrier(final Supplier<IsGmlProperty<IsGmlADMSRoadSideBarrier>> getter, final Consumer<ADMSRoadSideBarrier> setter) {
    final IsGmlProperty<IsGmlADMSRoadSideBarrier> barrierProperty = getter.get();
    setter.accept(barrierProperty == null ? null : getRoadSideBarrier(barrierProperty));
  }

  private ADMSRoadSideBarrier getRoadSideBarrier(final IsGmlProperty<IsGmlADMSRoadSideBarrier> barrierProperty) {
    final IsGmlADMSRoadSideBarrier gmlBarrier = barrierProperty.getProperty();
    final ADMSRoadSideBarrier barrier = new ADMSRoadSideBarrier();
    barrier.setBarrierType(gmlBarrier.getBarrierType());
    barrier.setWidth(gmlBarrier.getDistance());
    barrier.setAverageHeight(gmlBarrier.getAverageHeight());
    barrier.setMaximumHeight(gmlBarrier.getMaximumHeight());
    barrier.setMinimumHeight(gmlBarrier.getMinimumHeight());
    barrier.setPorosity(gmlBarrier.getPorosity());
    return barrier;
  }

  private void setDiurnalVariation(final T source, final ADMSRoadEmissionSource emissionSource) {
    if (source.getDiurnalVariation() == null) {
      emissionSource.setStandardDiurnalVariationCode(null);
      emissionSource.setCustomDiurnalVariationId(null);
    } else if (source.getDiurnalVariation() instanceof IsGmlStandardDiurnalVariation) {
      final String diurnalVariationCode = ((IsGmlStandardDiurnalVariation) source.getDiurnalVariation()).getCode();
      emissionSource.setStandardDiurnalVariationCode(diurnalVariationCode);
      emissionSource.setCustomDiurnalVariationId(null);
    } else if (source.getDiurnalVariation() instanceof IsGmlReferenceDiurnalVariation) {
      emissionSource.setStandardDiurnalVariationCode(null);
      final IsGmlReferenceDiurnalVariation gmlReferenceDiurnalVariation = (IsGmlReferenceDiurnalVariation) source.getDiurnalVariation();
      emissionSource.setCustomDiurnalVariationId(gmlReferenceDiurnalVariation.getCustomDiurnalVariation().getReferredId());
    }
  }

}
