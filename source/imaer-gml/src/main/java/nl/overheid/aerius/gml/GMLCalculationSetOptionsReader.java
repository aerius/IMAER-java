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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import nl.overheid.aerius.gml.base.FeatureCollection;
import nl.overheid.aerius.gml.base.IsCalculationMetaData;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.MetaData;
import nl.overheid.aerius.shared.domain.Theme;
import nl.overheid.aerius.shared.domain.calculation.CalculationJobType;
import nl.overheid.aerius.shared.domain.calculation.CalculationMethod;
import nl.overheid.aerius.shared.domain.calculation.CalculationSetOptions;
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
  public CalculationSetOptions readCalculationSetOptions(final Theme theme) {
    final Optional<IsCalculationMetaData> optionalCheck = Optional.ofNullable(featureCollection.getMetaData()).map(MetaData::getCalculation);
    if (optionalCheck.isEmpty()) {
      return null;
    }

    final IsCalculationMetaData calculationMetaData = optionalCheck.get();
    final CalculationSetOptions options = new CalculationSetOptions();

    if (calculationMetaData.getOptions() != null) {
      final Map<String, String> optionsMap = new HashMap<>();

      // Use forEach to remove duplicate entries.
      calculationMetaData.getOptions().stream()
          .map(IsGmlProperty::getProperty)
          .forEach(p -> optionsMap.put(p.getKey(), p.getValue()));
      OptionsMetadataUtil.addOptionsFromMap(theme, optionsMap, options);
    }
    setCalculationMethod(calculationMetaData, options);
    setCalculationJobType(calculationMetaData, options);
    options.setCalculateMaximumRange(calculationMetaData.getMaximumRange() == null ? 0.0 : calculationMetaData.getMaximumRange());
    return options;
  }

  /**
   * Set the calculation method. If calculation method is null try if calculation type was used.
   *
   * @param calculationMetaData
   * @param options
   */
  private static void setCalculationMethod(final IsCalculationMetaData calculationMetaData, final CalculationSetOptions options) {
    final String method = calculationMetaData.getCalculationMethod();

    options.setCalculationMethod(
        method == null ? CalculationType.toCalculationMethod(calculationMetaData.getCalculationType()) : CalculationMethod.safeValueOf(method));
  }

  /**
   * Set the calculation job type.
   */
  private static void setCalculationJobType(final IsCalculationMetaData calculationMetaData, final CalculationSetOptions options) {
    final String jobType = calculationMetaData.getCalculationJobType();

    options.setCalculationJobType(jobType == null ? null : CalculationJobType.safeValueOf(jobType));
  }
}
