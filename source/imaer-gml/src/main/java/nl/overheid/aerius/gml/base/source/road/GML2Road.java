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
import java.util.Objects;
import java.util.Optional;

import nl.overheid.aerius.gml.base.AbstractGML2Specific;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.source.RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.ValuesPerVehicleType;
import nl.overheid.aerius.shared.domain.v2.source.road.Vehicles;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 *
 */
abstract class GML2Road<T extends IsGmlRoadEmissionSource, S extends RoadEmissionSource> extends AbstractGML2Specific<T, S> {

  /**
   * @param conversionData
   *          The conversion data to use.
   */
  protected GML2Road(final GMLConversionData conversionData) {
    super(conversionData);
  }

  @Override
  public S convert(final T source) throws AeriusException {
    final S emissionSource = construct();
    final List<StandardVehicles> mergingStandardVehicles = new ArrayList<>();
    for (final IsGmlProperty<IsGmlVehicle> vp : source.getVehicles()) {
      addVehicleEmissions(emissionSource.getSubSources(), source, vp, mergingStandardVehicles);
    }
    emissionSource.setTrafficDirection(source.getTrafficDirection());
    emissionSource.setRoadManager(source.getRoadManager());
    emissionSource.setRoadAreaCode(source.getRoadAreaCode());
    emissionSource.setRoadTypeCode(source.getRoadTypeCode());

    setSpecificVariables(source, emissionSource);

    setOptionalVariables(source, emissionSource);

    return emissionSource;
  }

  protected abstract S construct();

  protected abstract void setSpecificVariables(T source, S emissionSource);

  protected abstract void setOptionalVariables(T source, S emissionSource) throws AeriusException;

  protected void addVehicleEmissions(final List<Vehicles> addToVehicles, final T source, final IsGmlProperty<IsGmlVehicle> vp,
      final List<StandardVehicles> mergingStandardVehicles) {
    final IsGmlVehicle av = vp.getProperty();
    if (av instanceof final IsGmlStandardVehicle standardVehicle) {
      addEmissionValues(addToVehicles, source, standardVehicle, mergingStandardVehicles);
    } else if (av instanceof final IsGmlSpecificVehicle specificVehicle) {
      GML2VehicleUtil.addEmissionValuesSpecific(addToVehicles, source, specificVehicle, getConversionData());
    } else if (av instanceof final IsGmlCustomVehicle customVehicle) {
      GML2VehicleUtil.addEmissionValuesCustom(addToVehicles, customVehicle);
    } else {
      throw new IllegalArgumentException("Instance not supported:" + av.getClass().getCanonicalName());
    }
  }

  private void addEmissionValues(final List<Vehicles> addToVehicles, final T source, final IsGmlStandardVehicle sv,
      final List<StandardVehicles> mergingStandardVehicles) {
    final StandardVehicles standardVehicle = findExistingMatch(sv, mergingStandardVehicles).orElseGet(() -> {
      final StandardVehicles vse = new StandardVehicles();
      vse.setMaximumSpeed(sv.getMaximumSpeed());
      vse.setStrictEnforcement(sv.isStrictEnforcement());
      vse.setTimeUnit(TimeUnit.valueOf(sv.getTimeUnit().name()));
      mergingStandardVehicles.add(vse);
      addToVehicles.add(vse);
      return vse;
    });
    final ValuesPerVehicleType valuesPerVehicleType = new ValuesPerVehicleType();
    valuesPerVehicleType.setStagnationFraction(sv.getStagnationFactor());
    valuesPerVehicleType.setVehiclesPerTimeUnit(sv.getVehiclesPerTimeUnit());
    standardVehicle.getValuesPerVehicleTypes().put(sv.getVehicleType(), valuesPerVehicleType);
  }

  private Optional<StandardVehicles> findExistingMatch(final IsGmlStandardVehicle sv, final List<StandardVehicles> mergingStandardVehicles) {
    return mergingStandardVehicles.stream()
        .filter(x -> Objects.equals(x.getMaximumSpeed(), sv.getMaximumSpeed()))
        .filter(x -> Objects.equals(x.getStrictEnforcement(), sv.isStrictEnforcement()))
        .filter(x -> x.getTimeUnit() == TimeUnit.valueOf(sv.getTimeUnit().name()))
        .filter(x -> !x.getValuesPerVehicleTypes().containsKey(sv.getVehicleType()))
        .findFirst();
  }

}
