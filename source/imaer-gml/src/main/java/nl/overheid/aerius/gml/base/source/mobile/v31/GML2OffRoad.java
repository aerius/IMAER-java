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
package nl.overheid.aerius.gml.base.source.mobile.v31;

import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.GMLLegacyCodeConverter.GMLLegacyCodeType;
import nl.overheid.aerius.gml.base.characteristics.GML2SourceCharacteristics;
import nl.overheid.aerius.gml.base.source.mobile.AbstractGML2OffRoad;
import nl.overheid.aerius.gml.base.source.mobile.IsGmlOffRoadMobileEmissionSource;
import nl.overheid.aerius.gml.base.source.mobile.IsGmlStandardOffRoadMobileBaseSource;
import nl.overheid.aerius.shared.domain.v2.source.offroad.OffRoadMobileSource;
import nl.overheid.aerius.shared.domain.v2.source.offroad.StandardOffRoadMobileSource;

/**
 * Convert GML Off road to internal OffRoad data structure.
 */
public class GML2OffRoad<T extends IsGmlOffRoadMobileEmissionSource> extends AbstractGML2OffRoad<IsGmlStandardOffRoadMobileBaseSource, T> {

  /**
   * @param conversionData The conversionData to use.
   * @param gml2SourceCharacteristics util class
   */
  public GML2OffRoad(final GMLConversionData conversionData, final GML2SourceCharacteristics gml2SourceCharacteristics) {
    super(conversionData, gml2SourceCharacteristics);
  }

  @Override
  protected OffRoadMobileSource convertStandard(final IsGmlStandardOffRoadMobileBaseSource mobileSource) {
    final StandardOffRoadMobileSource vehicleEmissionValues = new StandardOffRoadMobileSource();
    vehicleEmissionValues.setDescription(mobileSource.getDescription());
    vehicleEmissionValues.setLiterFuelPerYear(mobileSource.getLiterFuelPerYear());
    final String oldCode = mobileSource.getCode();
    final String newCode = getConversionData().getCode(GMLLegacyCodeType.OFF_ROAD_MOBILE_SOURCE, oldCode, mobileSource.getDescription());
    vehicleEmissionValues.setOffRoadMobileSourceCode(newCode);
    if (!oldCode.equals(newCode)) {
      // If the code was converted, that means we can convert from the old properties to the new properties
      // In theory this should be for all codes, but there's a possibility that the code supplied was incorrect in the first place.
      final int estimatedOperatingHours = getConversionData().estimageOffRoadOperatingHours(oldCode, mobileSource.getLiterFuelPerYear());
      vehicleEmissionValues.setOperatingHoursPerYear(estimatedOperatingHours);
      vehicleEmissionValues.setLiterAdBluePerYear(0);
    }

    return vehicleEmissionValues;
  }
}
