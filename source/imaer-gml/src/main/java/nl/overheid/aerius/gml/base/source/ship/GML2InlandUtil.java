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
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.CustomInlandShippingEmissionProperties;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.InlandWaterway;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.WaterwayDirection;

final class GML2InlandUtil {

  private GML2InlandUtil() {
    // Util class
  }

  public static InlandWaterway toInlandWaterway(final IsGmlProperty<IsGmlInlandWaterway> gmlWaterwayProperty) {
    final InlandWaterway waterway = new InlandWaterway();
    final IsGmlInlandWaterway gmlWaterway = gmlWaterwayProperty.getProperty();
    waterway.setDirection(gmlWaterway.getDirection() == null ? WaterwayDirection.IRRELEVANT : gmlWaterway.getDirection());
    waterway.setWaterwayCode(gmlWaterwayProperty.getProperty().getType());
    return waterway;
  }

  public static CustomInlandShippingEmissionProperties getCustomProperties(
      final IsGmlProperty<IsGmlCustomInlandShippingEmissionProperties> gmlPropertiesProperty) {
    final IsGmlCustomInlandShippingEmissionProperties gmlProperties = gmlPropertiesProperty.getProperty();
    final CustomInlandShippingEmissionProperties properties = new CustomInlandShippingEmissionProperties();
    addEmissionFactors(gmlProperties.getEmissionFactorsEmpty(), properties.getEmissionFactorsEmpty());
    addEmissionFactors(gmlProperties.getEmissionFactorsLaden(), properties.getEmissionFactorsLaden());
    properties.setHeatContentEmpty(gmlProperties.getHeatContentEmpty());
    properties.setHeatContentLaden(gmlProperties.getHeatContentLaden());
    properties.setEmissionHeightEmpty(gmlProperties.getEmissionHeightEmpty());
    properties.setEmissionHeightLaden(gmlProperties.getEmissionHeightLaden());
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
