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

import nl.overheid.aerius.gml.v6_0.source.mobile.CustomOffRoadMobileSource;
import nl.overheid.aerius.gml.v6_0.source.mobile.OffRoadMobileSource;
import nl.overheid.aerius.gml.v6_0.source.mobile.OffRoadMobileSourceProperty;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.characteristics.OPSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.source.OffRoadMobileEmissionSource;

/**
 *
 */
class OffRoad2GML extends SpecificSource2GML<OffRoadMobileEmissionSource> {

  @Override
  public nl.overheid.aerius.gml.v6_0.source.EmissionSource convert(final OffRoadMobileEmissionSource emissionSource) {
    final nl.overheid.aerius.gml.v6_0.source.mobile.OffRoadMobileEmissionSource returnSource = new nl.overheid.aerius.gml.v6_0.source.mobile.OffRoadMobileEmissionSource();
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
    return new OffRoadMobileSourceProperty(gmlMobileSource);
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
