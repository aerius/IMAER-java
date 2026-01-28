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

import java.util.List;

import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.GMLLegacyCodeConverter.GMLLegacyCodeType;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.source.IsGmlEmission;
import nl.overheid.aerius.gml.base.source.IsGmlEmissionSource;
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.source.road.CustomVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.SpecificVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.Vehicles;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 *
 */
class GML2VehicleUtil {

  private GML2VehicleUtil() {
    // Util class
  }

  static void addEmissionValuesSpecific(final List<Vehicles> addToVehicles, final IsGmlEmissionSource source, final IsGmlSpecificVehicle sv,
      final GMLConversionData conversionData) throws AeriusException {
    final String vehicleCode = conversionData.getCode(GMLLegacyCodeType.ON_ROAD_MOBILE_SOURCE, sv.getCode(), source.getLabel());

    if (conversionData.warnIfRemovedCode(GMLLegacyCodeType.ON_ROAD_MOBILE_SOURCE, vehicleCode, source.getLabel())) {
      addToVehicles.add(RemovedVehicleUtil.toCustomVehicles(sv, vehicleCode));
      return;
    }

    final SpecificVehicles vse = new SpecificVehicles();
    vse.setVehicleCode(vehicleCode);
    vse.setTimeUnit(TimeUnit.valueOf(sv.getTimeUnit().name()));
    vse.setVehiclesPerTimeUnit(sv.getVehiclesPerTimeUnit());
    addToVehicles.add(vse);
  }

  static void addEmissionValuesCustom(final List<Vehicles> addToVehicles, final IsGmlCustomVehicle cv, final boolean includeVehicleType) {
    final CustomVehicles vce = new CustomVehicles();
    vce.setDescription(cv.getDescription());
    for (final IsGmlProperty<IsGmlEmission> e : cv.getEmissionFactors()) {
      final IsGmlEmission emissionFactor = e.getProperty();
      vce.getEmissionFactors().put(emissionFactor.getSubstance(), emissionFactor.getValue());
    }
    vce.setTimeUnit(TimeUnit.valueOf(cv.getTimeUnit().name()));
    vce.setVehiclesPerTimeUnit(cv.getVehiclesPerTimeUnit());
    if (includeVehicleType) {
      vce.setVehicleType(cv.getVehicleType());
    }
    addToVehicles.add(vce);
  }

}
