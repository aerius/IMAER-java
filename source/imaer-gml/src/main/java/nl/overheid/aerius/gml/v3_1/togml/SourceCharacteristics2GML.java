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
package nl.overheid.aerius.gml.v3_1.togml;

import nl.overheid.aerius.gml.v3_1.source.characteristics.AbstractHeatContent;
import nl.overheid.aerius.gml.v3_1.source.characteristics.CalculatedHeatContent;
import nl.overheid.aerius.gml.v3_1.source.characteristics.EmissionSourceCharacteristics;
import nl.overheid.aerius.gml.v3_1.source.characteristics.SpecifiedHeatContent;
import nl.overheid.aerius.shared.domain.v2.characteristics.HeatContentType;
import nl.overheid.aerius.shared.domain.v2.characteristics.OPSSourceCharacteristics;

/**
 *
 */
final class SourceCharacteristics2GML {

  private SourceCharacteristics2GML() {
    //util class.
  }

  static EmissionSourceCharacteristics toGML(
      final OPSSourceCharacteristics characteristics, final boolean includeOptionals) {
    final EmissionSourceCharacteristics returnCharacteristics = new EmissionSourceCharacteristics();
    returnCharacteristics.setHeatContent(determineHeatContent(characteristics));
    returnCharacteristics.setEmissionHeight(characteristics.getEmissionHeight());
    returnCharacteristics.setSpread(characteristics.getSpread());
    if (includeOptionals) {
      returnCharacteristics.setDiurnalVariation(characteristics.getDiurnalVariation() == null
          ? null : characteristics.getDiurnalVariation().getCode());
    }
    // No longer possible to set building information due to changes in how buildings work (they are now their own FeatureObject)
    return returnCharacteristics;
  }

  private static AbstractHeatContent determineHeatContent(final OPSSourceCharacteristics characteristics) {
    if (characteristics.getHeatContentType() == HeatContentType.FORCED) {
      final CalculatedHeatContent heatContent = new CalculatedHeatContent();
      heatContent.setEmissionTemperature(characteristics.getEmissionTemperature());
      heatContent.setOutflowDiameter(characteristics.getOutflowDiameter());
      heatContent.setOutflowVelocity(characteristics.getOutflowVelocity());
      heatContent.setOutflowDirection(characteristics.getOutflowDirection());
      return heatContent;
    } else {
      final SpecifiedHeatContent heatContent = new SpecifiedHeatContent();
      heatContent.setValue(characteristics.getHeatContent());
      return heatContent;

    }
  }

}
