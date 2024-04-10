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
package nl.overheid.aerius.gml.v6_0.togml;

import java.util.ArrayList;
import java.util.List;

import nl.overheid.aerius.gml.v6_0.source.TimeUnit;
import nl.overheid.aerius.gml.v6_0.source.road.ColdStartStandardVehicle;
import nl.overheid.aerius.gml.v6_0.source.road.CustomVehicle;
import nl.overheid.aerius.gml.v6_0.source.road.SpecificVehicle;
import nl.overheid.aerius.gml.v6_0.source.road.VehiclesProperty;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.road.CustomVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.SpecificVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardColdStartVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.Vehicles;

/**
 * Converts {@link nl.overheid.aerius.shared.domain.v2.source.ColdStartEmissionSource} to GML data object.
 */
class ColdStart2GML extends SpecificSource2GML<nl.overheid.aerius.shared.domain.v2.source.ColdStartEmissionSource> {

  @Override
  public nl.overheid.aerius.gml.v6_0.source.EmissionSource convert(
      final nl.overheid.aerius.shared.domain.v2.source.ColdStartEmissionSource emissionSource) {
    final nl.overheid.aerius.gml.v6_0.source.road.ColdStartEmissionSource returnSource =
        new nl.overheid.aerius.gml.v6_0.source.road.ColdStartEmissionSource();
    returnSource.setVehicleBasedCharacteristics(emissionSource.isVehicleBasedCharacteristics());
    returnSource.setVehicles(toVehicleProperties(emissionSource.getSubSources(), emissionSource.isVehicleBasedCharacteristics()));
    return returnSource;
  }

  private List<VehiclesProperty> toVehicleProperties(final List<Vehicles> vehicleGroups, final boolean vehicleBasedCharacteristics) {
    final List<VehiclesProperty> vehiclesList = new ArrayList<>(vehicleGroups.size());

    for (final Vehicles vehicleGroup : vehicleGroups) {
      if (vehicleGroup instanceof final StandardColdStartVehicles standardVehicles) {
        addVehicleEmissionSource(vehiclesList, standardVehicles);
      } else if (vehicleGroup instanceof final SpecificVehicles specificVehicles) {
        addVehicleEmissionSource(vehiclesList, specificVehicles);
      } else if (vehicleGroup instanceof final CustomVehicles customVehicles) {
        addVehicleEmissionSource(vehiclesList, customVehicles, vehicleBasedCharacteristics);
      } else {
        throw new IllegalArgumentException("Class for cold start not permitted: " + vehicleGroup);
      }
    }
    return vehiclesList;
  }

  private void addVehicleEmissionSource(final List<VehiclesProperty> vehiclesList, final StandardColdStartVehicles vse) {
    // Loop over all vehicle types in the valuesPerVehicleTypes map, but sort them first to get predictable order.
    final List<String> vehicleTypes = vse.getValuesPerVehicleTypes().keySet().stream().sorted().toList();
    for (final String vehicleType : vehicleTypes) {
      if (vse.getValuesPerVehicleTypes().containsKey(vehicleType)) {
        final ColdStartStandardVehicle sv = new ColdStartStandardVehicle();
        sv.setVehiclesPerTimeUnit(vse.getValuesPerVehicleTypes().get(vehicleType));
        sv.setVehicleType(vehicleType);
        sv.setTimeUnit(TimeUnit.from(vse.getTimeUnit()));
        vehiclesList.add(new VehiclesProperty(sv));
      }
    }
  }

  private void addVehicleEmissionSource(final List<VehiclesProperty> vehiclesList, final SpecificVehicles vse) {
    final SpecificVehicle sv = new SpecificVehicle();

    sv.setCode(vse.getVehicleCode());
    sv.setVehiclesPerTimeUnit(vse.getVehiclesPerTimeUnit());
    sv.setTimeUnit(TimeUnit.from(vse.getTimeUnit()));
    vehiclesList.add(new VehiclesProperty(sv));
  }

  private void addVehicleEmissionSource(final List<VehiclesProperty> vehiclesList, final CustomVehicles vce, final boolean vehicleBasedCharacteristics) {
    final CustomVehicle cv = new CustomVehicle();
    cv.setDescription(vce.getDescription());
    cv.setEmissionFactors(getEmissions(vce.getEmissionFactors(), Substance.NOX));
    cv.setVehiclesPerTimeUnit(vce.getVehiclesPerTimeUnit());
    cv.setTimeUnit(TimeUnit.from(vce.getTimeUnit()));
    if (vehicleBasedCharacteristics) {
      cv.setVehicleType(vce.getVehicleType());
    }
    vehiclesList.add(new VehiclesProperty(cv));
  }

}
