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
package nl.overheid.aerius.gml.base;

import java.util.Map;

import nl.overheid.aerius.gml.base.GMLLegacyCodeConverter.Conversion;
import nl.overheid.aerius.gml.base.GMLLegacyCodeConverter.GMLLegacyCodeType;
import nl.overheid.aerius.gml.base.conversion.FarmLodgingConversion;
import nl.overheid.aerius.gml.base.conversion.MobileSourceOffRoadConversion;
import nl.overheid.aerius.gml.base.conversion.PlanConversion;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Interface related to legacy codes.
 * Legacy codes are old sector specific category codes that have been replaced by a new code.
 * This supplier returns the list of all known mappings.
 */
public interface GMLLegacyCodesSupplier {

  /**
   * Returns a map with old codes and the new converted code for a specific GML version.
   *
   * @param version GML version to get the codes for.
   * @return mapping of the codes
   * @throws AeriusException
   */
  Map<GMLLegacyCodeType, Map<String, Conversion>> getLegacyCodes(AeriusGMLVersion version) throws AeriusException;
  
  /**
   * Returns a map with old codes and the values to use for conversion for old offroad mobile sources.
   *
   * @return mapping of the codes
   * @throws AeriusException
   */
  Map<String, MobileSourceOffRoadConversion> getLegacyMobileSourceOffRoadConversions() throws AeriusException;

  /**
   * Returns a map with old codes and the values to use for conversion for plan activities.
   *
   * @return mapping of the codes
   * @throws AeriusException
   */
  default Map<String, PlanConversion> getLegacyPlanConversions() throws AeriusException {
    return Map.of();
  }

  /**
   * Returns a map with old codes and the values to use for conversion for farm lodgings.
   *
   * @return mapping of the codes
   * @throws AeriusException
   */
  default Map<String, FarmLodgingConversion> getLegacyFarmLodgingConversions() throws AeriusException {
    return Map.of();
  }

}
