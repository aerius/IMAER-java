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
package nl.overheid.aerius.gml.base.characteristics;

import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.shared.domain.v2.characteristics.OPSSourceCharacteristics;

/**
 * Class to convert to and from GML objects (specific for source characteristics).
 *
 * Will ensure that the emission temperature will be set to the correct value for default,
 * if the value in the GML was the default at that time (11.85).
 */
public class GML2SourceCharacteristicsV31 extends GML2OPSSourceCharacteristics {

  private static final double EMISSION_TEMPERATURE_DEFAULT = 11.85;

  public GML2SourceCharacteristicsV31(final GMLConversionData conversionData) {
    super(conversionData);
  }

  @Override
  protected void fromGmlToEmissionTemperature(final OPSSourceCharacteristics returnCharacteristics, final IsGmlCalculatedHeatContent gmlHeatContent) {
    if (Double.compare(EMISSION_TEMPERATURE_DEFAULT, gmlHeatContent.getEmissionTemperature()) == 0) {
      returnCharacteristics.setEmissionTemperature(null);
    } else {
      returnCharacteristics.setEmissionTemperature(gmlHeatContent.getEmissionTemperature());
    }
  }
}
