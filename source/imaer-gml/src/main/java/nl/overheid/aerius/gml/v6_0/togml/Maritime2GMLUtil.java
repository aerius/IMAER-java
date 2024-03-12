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

import nl.overheid.aerius.gml.v6_0.source.ship.CustomMaritimeShippingEmissionPropertiesProperty;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.CustomMaritimeShippingEmissionProperties;

/**
 *
 */
final class Maritime2GMLUtil {

  private Maritime2GMLUtil() {
    //util class.
  }

  static CustomMaritimeShippingEmissionPropertiesProperty toCustomEmissionProperties(
      final CustomMaritimeShippingEmissionProperties customProperties) {
    final nl.overheid.aerius.gml.v6_0.source.ship.CustomMaritimeShippingEmissionProperties gmlProperties = new nl.overheid.aerius.gml.v6_0.source.ship.CustomMaritimeShippingEmissionProperties();
    gmlProperties.setEmissionFactors(SpecificSource2GML.getEmissions(customProperties.getEmissionFactors(), Substance.NOX));
    gmlProperties.setHeatContent(customProperties.getHeatContent());
    gmlProperties.setEmissionHeight(customProperties.getEmissionHeight());
    return new CustomMaritimeShippingEmissionPropertiesProperty(gmlProperties);
  }
}
