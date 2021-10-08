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

import nl.overheid.aerius.gml.base.AbstractGML2Specific;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.GMLLegacyCodeConverter.GMLLegacyCodeType;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.source.IsGmlEmission;
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.source.RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.CustomVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.SpecificVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.Vehicles;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 *
 */
abstract class GML2SRMRoad<T extends IsGmlRoadEmissionSource, S extends RoadEmissionSource> extends AbstractGML2Specific<T, S> {

  /**
   * @param conversionData
   *          The conversion data to use.
   */
  protected GML2SRMRoad(final GMLConversionData conversionData) {
    super(conversionData);
  }

  @Override
  public S convert(final T source) throws AeriusException {
    final S emissionSource = construct();
    for (final IsGmlProperty<IsGmlVehicle> vp : source.getVehicles()) {
      emissionSource.getSubSources().add(getVehicleEmissions(source, vp));
    }
    emissionSource.setTrafficDirection(source.getTrafficDirection());
    emissionSource.setRoadManager(source.getRoadManager());

    setSpecificVariables(source, emissionSource);

    setOptionalVariables(source, emissionSource);

    return emissionSource;
  }

  protected abstract S construct();

  protected abstract void setSpecificVariables(T source, S emissionSource);

  protected abstract void setOptionalVariables(T source, S emissionSource) throws AeriusException;

  protected Vehicles getVehicleEmissions(final T source, final IsGmlProperty<IsGmlVehicle> vp) throws AeriusException {
    final IsGmlVehicle av = vp.getProperty();
    final Vehicles vehicleEmissions;
    if (av instanceof IsGmlStandardVehicle) {
      vehicleEmissions = getEmissionValues(source, (IsGmlStandardVehicle) av);
    } else if (av instanceof IsGmlSpecificVehicle) {
      vehicleEmissions = getEmissionValues(source, (IsGmlSpecificVehicle) av);
    } else if (av instanceof IsGmlCustomVehicle) {
      vehicleEmissions = getEmissionValues((IsGmlCustomVehicle) av);
    } else {
      throw new IllegalArgumentException("Instance not supported:" + av.getClass().getCanonicalName());
    }
    vehicleEmissions.setVehiclesPerTimeUnit(av.getVehiclesPerTimeUnit());
    vehicleEmissions.setTimeUnit(TimeUnit.valueOf(av.getTimeUnit().name()));
    return vehicleEmissions;
  }

  protected StandardVehicles getEmissionValues(final T source, final IsGmlStandardVehicle sv) {
    final StandardVehicles vse = new StandardVehicles();
    vse.setStagnationFraction(sv.getStagnationFactor());
    vse.setStandardVehicleType(sv.getVehicleType());
    vse.setMaximumSpeed(sv.getMaximumSpeed());
    vse.setStrictEnforcement(sv.isStrictEnforcement());
    return vse;
  }

  protected SpecificVehicles getEmissionValues(final T source, final IsGmlSpecificVehicle sv) {
    final SpecificVehicles vse = new SpecificVehicles();
    final String vehicleCode = getConversionData().getCode(GMLLegacyCodeType.ON_ROAD_MOBILE_SOURCE, sv.getCode(), source.getLabel());
    vse.setVehicleCode(vehicleCode);
    return vse;
  }

  protected CustomVehicles getEmissionValues(final IsGmlCustomVehicle cv) {
    final CustomVehicles vce = new CustomVehicles();
    vce.setDescription(cv.getDescription());
    for (final IsGmlProperty<IsGmlEmission> e : cv.getEmissions()) {
      final IsGmlEmission emission = e.getProperty();
      vce.getEmissionFactors().put(emission.getSubstance(), emission.getValue());
    }
    return vce;
  }

}
