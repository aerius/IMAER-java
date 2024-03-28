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
package nl.overheid.aerius.gml.base.source.road;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.overheid.aerius.gml.base.AbstractGML2Specific;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.source.ColdStartEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardColdStartVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.Vehicles;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
public class GML2ColdStart<T extends IsGmlColdStartSource> extends AbstractGML2Specific<T, ColdStartEmissionSource> {

  private static final Logger LOG = LoggerFactory.getLogger(GML2ColdStart.class);

  /**
   * @param conversionData The conversion data to use.
   */
  public GML2ColdStart(final GMLConversionData conversionData) {
    super(conversionData);
  }

  @Override
  public ColdStartEmissionSource convert(final T source) throws AeriusException {
    final ColdStartEmissionSource emissionSource = new ColdStartEmissionSource();
    emissionSource.setVehicleBasedCharacteristics(source.isVehicleBasedCharacteristics());
    final List<StandardColdStartVehicles> mergingStandardVehicles = new ArrayList<>();
    for (final IsGmlProperty<IsGmlVehicle> vp : source.getVehicles()) {
      addVehicleEmissions(emissionSource.getSubSources(), source, vp, mergingStandardVehicles);
    }
    return emissionSource;
  }

  protected void addVehicleEmissions(final List<Vehicles> addToVehicles, final T source, final IsGmlProperty<IsGmlVehicle> vp,
      final List<StandardColdStartVehicles> mergingStandardVehicles) throws AeriusException {
    final IsGmlVehicle av = vp.getProperty();
    if (av instanceof final IsGmlColdStartStandardVehicle standardVehicle) {
      addEmissionValues(addToVehicles, source, standardVehicle, mergingStandardVehicles);
    } else if (av instanceof final IsGmlSpecificVehicle specificVehicle) {
      GML2VehicleUtil.addEmissionValuesSpecific(addToVehicles, source, specificVehicle, getConversionData());
    } else if (av instanceof final IsGmlCustomVehicle customVehicle) {
      GML2VehicleUtil.addEmissionValuesCustom(addToVehicles, customVehicle);
    } else {
      LOG.error("Don't know how to treat cold start vehicle type: {}", av.getClass());
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR);
    }
  }

  private void addEmissionValues(final List<Vehicles> addToVehicles, final T source, final IsGmlColdStartStandardVehicle sv,
      final List<StandardColdStartVehicles> mergingStandardVehicles) {
    final StandardColdStartVehicles standardVehicle = findExistingMatch(sv, mergingStandardVehicles).orElseGet(() -> {
      final StandardColdStartVehicles vse = new StandardColdStartVehicles();
      vse.setTimeUnit(TimeUnit.valueOf(sv.getTimeUnit().name()));
      mergingStandardVehicles.add(vse);
      addToVehicles.add(vse);
      return vse;
    });
    standardVehicle.getValuesPerVehicleTypes().put(sv.getVehicleType(), sv.getVehiclesPerTimeUnit());
  }

  private Optional<StandardColdStartVehicles> findExistingMatch(final IsGmlColdStartStandardVehicle sv,
      final List<StandardColdStartVehicles> mergingStandardVehicles) {
    return mergingStandardVehicles.stream()
        .filter(x -> x.getTimeUnit() == TimeUnit.valueOf(sv.getTimeUnit().name()))
        .filter(x -> !x.getValuesPerVehicleTypes().containsKey(sv.getVehicleType()))
        .findFirst();
  }
}
