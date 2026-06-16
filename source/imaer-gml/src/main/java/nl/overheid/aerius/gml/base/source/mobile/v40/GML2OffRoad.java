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
package nl.overheid.aerius.gml.base.source.mobile.v40;

import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.characteristics.GML2SourceCharacteristics;
import nl.overheid.aerius.gml.base.source.mobile.AbstractGML2OffRoad;
import nl.overheid.aerius.gml.base.source.mobile.IsGmlOffRoadMobileEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.offroad.OffRoadMobileSource;
import nl.overheid.aerius.shared.domain.v2.source.offroad.StandardOffRoadMobileSource;

/**
 * Convert GML Off road to internal OffRoad data structure.
 */
public class GML2OffRoad<T extends IsGmlOffRoadMobileEmissionSource> extends AbstractGML2OffRoad<T, IsGmlStandardOffRoadMobileSource> {

  /**
   * @param conversionData The conversionData to use.
   */
  public GML2OffRoad(final GMLConversionData conversionData, final GML2SourceCharacteristics gml2SourceCharacteristics) {
    super(conversionData, gml2SourceCharacteristics);
  }

  @Override
  public OffRoadMobileSource convertStandard(final IsGmlStandardOffRoadMobileSource mobileSource) {
    final StandardOffRoadMobileSource vehicleEmissionValues = new StandardOffRoadMobileSource();
    vehicleEmissionValues.setDescription(mobileSource.getDescription());
    vehicleEmissionValues.setLiterFuelPerYear(mobileSource.getLiterFuelPerYear());
    vehicleEmissionValues.setOffRoadMobileSourceCode(mobileSource.getCode());
    vehicleEmissionValues.setOperatingHoursPerYear(mobileSource.getOperatingHoursPerYear());
    vehicleEmissionValues.setLiterAdBluePerYear(mobileSource.getLiterAdBluePerYear());

    return vehicleEmissionValues;
  }


}
