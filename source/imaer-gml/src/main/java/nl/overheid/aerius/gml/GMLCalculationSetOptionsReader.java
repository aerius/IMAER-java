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
package nl.overheid.aerius.gml;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import nl.overheid.aerius.gml.base.FeatureCollection;
import nl.overheid.aerius.gml.base.IsCalculationMetaData;
import nl.overheid.aerius.shared.domain.calculation.CalculationSetOptions;
import nl.overheid.aerius.shared.domain.calculation.CalculationType;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioMetaData;
import nl.overheid.aerius.util.OptionsMetadataUtil;

/**
 * Reads metadata from the feature collection.
 */
public class GMLCalculationSetOptionsReader {

  private final FeatureCollection featureCollection;

  public GMLCalculationSetOptionsReader(final FeatureCollection featureCollection) {
    this.featureCollection = featureCollection;
  }

  /**
   * Returns the {@link ScenarioMetaData} from the GML data.
   * @return ScenarioMetaData object
   */
  public CalculationSetOptions readCalculationSetOptions() {
    final IsCalculationMetaData calculationMetaData = featureCollection.getMetaData().getCalculation();
    final CalculationSetOptions options = new CalculationSetOptions();
    final Map<OptionsMetadataUtil.Option, String> optionsMap = calculationMetaData.getOptions().stream()
        .collect(Collectors.toMap(prop -> OptionsMetadataUtil.Option.valueOf(prop.getProperty().getKey().toUpperCase(Locale.ROOT)),
            prop -> prop.getProperty().getValue()));
    OptionsMetadataUtil.addOptionsFromMap(optionsMap, options);

    options.setCalculationType(CalculationType.valueOf(calculationMetaData.getCalculationType()));
    options.setCalculateMaximumRange(calculationMetaData.getMaximumRange());
    return options;
  }
}
