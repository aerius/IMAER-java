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
package nl.overheid.aerius.gml.base.source.ship;

import java.util.List;
import java.util.Map;

import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.source.IsGmlEmission;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.CustomMaritimeShippingEmissionProperties;

final class GML2MaritimeUtil {

  private GML2MaritimeUtil() {
    // Util class
  }

  public static CustomMaritimeShippingEmissionProperties getCustomProperties(
      final IsGmlProperty<IsGmlCustomMaritimeShippingEmissionProperties> gmlPropertiesProperty) {
    final IsGmlCustomMaritimeShippingEmissionProperties gmlProperties = gmlPropertiesProperty.getProperty();
    final CustomMaritimeShippingEmissionProperties properties = new CustomMaritimeShippingEmissionProperties();
    addEmissionFactors(gmlProperties.getEmissionFactors(), properties.getEmissionFactors());
    properties.setHeatContent(gmlProperties.getHeatContent());
    properties.setEmissionHeight(gmlProperties.getEmissionHeight());
    return properties;
  }

  private static void addEmissionFactors(final List<? extends IsGmlProperty<IsGmlEmission>> gmlEmissionFactorProperties,
      final Map<Substance, Double> targetEmissionFactors) {
    for (final IsGmlProperty<IsGmlEmission> emissionProperty : gmlEmissionFactorProperties) {
      final IsGmlEmission emission = emissionProperty.getProperty();
      targetEmissionFactors.put(emission.getSubstance(), emission.getValue());
    }
  }

}
