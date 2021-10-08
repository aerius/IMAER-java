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
package nl.overheid.aerius.gml.v4_0.togml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nl.overheid.aerius.gml.v4_0.source.Emission;
import nl.overheid.aerius.gml.v4_0.source.EmissionProperty;
import nl.overheid.aerius.gml.v4_0.source.mobile.AbstractOffRoadVehicleSpecification;
import nl.overheid.aerius.gml.v4_0.source.mobile.CustomOffRoadMobileSource;
import nl.overheid.aerius.gml.v4_0.source.mobile.OffRoadMobileSource;
import nl.overheid.aerius.gml.v4_0.source.mobile.OffRoadMobileSourceProperty;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.characteristics.OPSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.source.OffRoadMobileEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.offroad.ConsumptionOffRoadVehicleSpecification;
import nl.overheid.aerius.shared.domain.v2.source.offroad.OffRoadVehicleSpecification;
import nl.overheid.aerius.shared.domain.v2.source.offroad.OperatingHoursOffRoadVehicleSpecification;

/**
 *
 */
class OffRoad2GML extends SpecificSource2GML<OffRoadMobileEmissionSource> {

  @Override
  public nl.overheid.aerius.gml.v4_0.source.EmissionSource convert(final OffRoadMobileEmissionSource emissionSource) {
    final nl.overheid.aerius.gml.v4_0.source.mobile.OffRoadMobileEmissionSource returnSource = new nl.overheid.aerius.gml.v4_0.source.mobile.OffRoadMobileEmissionSource();
    final List<OffRoadMobileSourceProperty> mobileSources = new ArrayList<>();

    for (final nl.overheid.aerius.shared.domain.v2.source.offroad.OffRoadMobileSource vehicleEmissionValues : emissionSource
        .getSubSources()) {
      if (vehicleEmissionValues instanceof nl.overheid.aerius.shared.domain.v2.source.offroad.CustomOffRoadMobileSource) {
        mobileSources.add(toCustom((nl.overheid.aerius.shared.domain.v2.source.offroad.CustomOffRoadMobileSource) vehicleEmissionValues));
      } else if (vehicleEmissionValues instanceof nl.overheid.aerius.shared.domain.v2.source.offroad.StandardOffRoadMobileSource) {
        mobileSources.add(toStandard((nl.overheid.aerius.shared.domain.v2.source.offroad.StandardOffRoadMobileSource) vehicleEmissionValues));
      } else {

      }
    }

    returnSource.setOffRoadMobileSources(mobileSources);
    return returnSource;
  }

  private OffRoadMobileSourceProperty toCustom(
      final nl.overheid.aerius.shared.domain.v2.source.offroad.CustomOffRoadMobileSource vehicleEmissionValues) {
    final CustomOffRoadMobileSource gmlMobileSource = new CustomOffRoadMobileSource();
    gmlMobileSource.setCharacteristics(SourceCharacteristics2GML.toGML((OPSSourceCharacteristics) vehicleEmissionValues.getCharacteristics(), false));
    gmlMobileSource.setEmissions(getEmissions(vehicleEmissionValues.getEmissions(), Substance.NOX));
    gmlMobileSource.setDescription(vehicleEmissionValues.getDescription());
    setAdditionalSpecifications(vehicleEmissionValues.getVehicleSpecification(), gmlMobileSource);
    return new OffRoadMobileSourceProperty(gmlMobileSource);
  }

  private void setAdditionalSpecifications(final OffRoadVehicleSpecification vehicleSpecification, final CustomOffRoadMobileSource gmlMobileSource) {
    // if no fuelType don't store anything.
    if (vehicleSpecification != null && vehicleSpecification.getFuelCode() != null) {
      if (vehicleSpecification instanceof ConsumptionOffRoadVehicleSpecification) {
        gmlMobileSource.setOffRoadVehicleSpecification(toConsuming((ConsumptionOffRoadVehicleSpecification) vehicleSpecification));
      } else if (vehicleSpecification instanceof OperatingHoursOffRoadVehicleSpecification) {
        gmlMobileSource.setOffRoadVehicleSpecification(toOperatingHours((OperatingHoursOffRoadVehicleSpecification) vehicleSpecification));
      }
    }
  }

  private AbstractOffRoadVehicleSpecification toConsuming(final ConsumptionOffRoadVehicleSpecification specification) {
    final nl.overheid.aerius.gml.v4_0.source.mobile.ConsumptionOffRoadVehicleSpecification gmlSpecification = new nl.overheid.aerius.gml.v4_0.source.mobile.ConsumptionOffRoadVehicleSpecification();
    gmlSpecification.setEmissionFactors(toGmlEmissionFactors(specification.getEmissionFactors()));
    gmlSpecification.setEnergyEfficiency(specification.getEnergyEfficiency());
    gmlSpecification.setConsumption(specification.getConsumption());
    setCategories(specification, gmlSpecification);
    return gmlSpecification;
  }

  private AbstractOffRoadVehicleSpecification toOperatingHours(final OperatingHoursOffRoadVehicleSpecification specification) {
    final nl.overheid.aerius.gml.v4_0.source.mobile.OperatingHoursOffRoadVehicleSpecification gmlSpecification = new nl.overheid.aerius.gml.v4_0.source.mobile.OperatingHoursOffRoadVehicleSpecification();
    gmlSpecification.setEmissionFactors(toGmlEmissionFactors(specification.getEmissionFactors()));
    gmlSpecification.setLoad(specification.getLoad());
    gmlSpecification.setPower(specification.getPower());
    gmlSpecification.setOperatingHours(specification.getOperatingHours());
    setCategories(specification, gmlSpecification);
    return gmlSpecification;
  }

  private List<EmissionProperty> toGmlEmissionFactors(final Map<Substance, Double> emissionFactors) {
    final List<EmissionProperty> gmlEmissionFactors = new ArrayList<>();
    for (final Entry<Substance, Double> entry : emissionFactors.entrySet()) {
      final Emission emission = new Emission();
      emission.setSubstance(entry.getKey());
      emission.setValue(entry.getValue());
      gmlEmissionFactors.add(new EmissionProperty(emission));
    }
    return gmlEmissionFactors;
  }

  private void setCategories(final OffRoadVehicleSpecification specification, final AbstractOffRoadVehicleSpecification gmlSpecification) {
    gmlSpecification.setFuelCode(specification.getFuelCode());
    //No need to do anything with the machinery type.
  }

  private OffRoadMobileSourceProperty toStandard(
      final nl.overheid.aerius.shared.domain.v2.source.offroad.StandardOffRoadMobileSource vehicleEmissionValues) {
    final OffRoadMobileSource gmlMobileSource = new OffRoadMobileSource();
    gmlMobileSource.setCode(vehicleEmissionValues.getOffRoadMobileSourceCode());
    gmlMobileSource.setDescription(vehicleEmissionValues.getDescription());
    gmlMobileSource.setLiterFuelPerYear(vehicleEmissionValues.getLiterFuelPerYear());
    gmlMobileSource.setOperatingHoursPerYear(vehicleEmissionValues.getOperatingHoursPerYear());
    gmlMobileSource.setLiterAdBluePerYear(vehicleEmissionValues.getLiterAdBluePerYear());
    return new OffRoadMobileSourceProperty(gmlMobileSource);
  }

}
