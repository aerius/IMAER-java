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
package nl.overheid.aerius.util;

import java.util.LinkedHashMap;
import java.util.Map;

import nl.overheid.aerius.shared.domain.calculation.CalculationRoadOPS;
import nl.overheid.aerius.shared.domain.calculation.CalculationSetOptions;
import nl.overheid.aerius.shared.domain.calculation.ConnectSuppliedOptions;
import nl.overheid.aerius.shared.domain.calculation.OPSOptions;

/**
 * Utility class to convert calculation options to metadata.
 */
public final class OptionsMetadataUtil {

  private enum Option {
    // @formatter:off
    METEO_YEAR,
    WITHOUT_SOURCE_STACKING,
    OPS_ROAD,
    FORCED_AGGREGATION,
    USE_RECEPTOR_HEIGHT,
    MONITOR_SRM2_YEAR,

    /* ConnectSuppliedOptions related */
    CALCULATION_YEAR,
    RECEPTOR_SET,

    /* OPSOptions related */
    OPS_RAW_INPUT,
    OPS_YEAR,
    OPS_COMP_CODE,
    OPS_MOL_WEIGHT,
    OPS_PHASE,
    OPS_LOSS,
    OPS_DIFF_COEFF,
    OPS_WASHOUT,
    OPS_CONV_RATE,
    OPS_ROUGHNESS,
    ;
    // @formatter:on

    String toKey() {
      return name().toLowerCase();
    }

  }

  private OptionsMetadataUtil() {
    //util class
  }

  public static Map<String, String> optionsToMap(final CalculationSetOptions options, final boolean addDefaults) {
    final Map<Option, String> mapToAddTo = new LinkedHashMap<>();
    addValue(mapToAddTo, Option.METEO_YEAR, options.getMeteo(), addDefaults);
    addBooleanValue(mapToAddTo, Option.WITHOUT_SOURCE_STACKING, !options.isStacking(), addDefaults);
    if (addDefaults || options.getRoadOPS() != CalculationRoadOPS.DEFAULT) {
      mapToAddTo.put(Option.OPS_ROAD, options.getRoadOPS().name());
    }
    addBooleanValue(mapToAddTo, Option.FORCED_AGGREGATION, options.isForceAggregation(), addDefaults);
    addBooleanValue(mapToAddTo, Option.USE_RECEPTOR_HEIGHT, options.isUseReceptorHeights(), addDefaults);
    addConnectSuppliedOptions(options.getConnectSuppliedOptions(), mapToAddTo, addDefaults);
    addIntValue(mapToAddTo, Option.MONITOR_SRM2_YEAR, options.getMonitorSrm2Year(), addDefaults);
    opsOptionsToMap(options.getOpsOptions(), mapToAddTo, addDefaults);

    final Map<String, String> returnMap = new LinkedHashMap<>();
    mapToAddTo.forEach((key, value) -> returnMap.put(key.toKey(), value));
    return returnMap;
  }

  private static void addConnectSuppliedOptions(final ConnectSuppliedOptions connectSuppliedOptions, final Map<Option, String> mapToAddTo,
      final boolean addDefaults) {
    if (connectSuppliedOptions != null) {
      addValue(mapToAddTo, Option.CALCULATION_YEAR, connectSuppliedOptions.getCalculationYear(), addDefaults);
      addValue(mapToAddTo, Option.RECEPTOR_SET, connectSuppliedOptions.getReceptorSetName(), addDefaults);
    }
  }

  private static void opsOptionsToMap(final OPSOptions options, final Map<Option, String> mapToAddTo, final boolean addDefaults) {
    if (options != null) {
      addBooleanValue(mapToAddTo, Option.OPS_RAW_INPUT, options.isRawInput(), addDefaults);
      addValue(mapToAddTo, Option.OPS_YEAR, options.getYear(), addDefaults);
      addValue(mapToAddTo, Option.OPS_COMP_CODE, options.getCompCode(), addDefaults);
      addValue(mapToAddTo, Option.OPS_MOL_WEIGHT, options.getMolWeight(), addDefaults);
      addValue(mapToAddTo, Option.OPS_PHASE, options.getPhase(), addDefaults);
      addValue(mapToAddTo, Option.OPS_LOSS, options.getLoss(), addDefaults);
      addValue(mapToAddTo, Option.OPS_DIFF_COEFF, options.getDiffCoeff(), addDefaults);
      addValue(mapToAddTo, Option.OPS_WASHOUT, options.getWashout(), addDefaults);
      addValue(mapToAddTo, Option.OPS_CONV_RATE, options.getConvRate(), addDefaults);
      addValue(mapToAddTo, Option.OPS_ROUGHNESS, options.getRoughness(), addDefaults);
    }
  }

  private static void addValue(final Map<Option, String> mapToAddTo, final Option key, final Object value, final boolean addDefaults) {
    // false should be the default value, so only add if value is true, or if defaults should be added anyway.
    if (value != null) {
      mapToAddTo.put(key, value.toString());
    } else if (addDefaults) {
      mapToAddTo.put(key, "");
    }
  }

  private static void addIntValue(final Map<Option, String> mapToAddTo, final Option key, final int value, final boolean addDefaults) {
    if (addDefaults || value != 0) {
      mapToAddTo.put(key, String.valueOf(value));
    }
  }

  private static void addBooleanValue(final Map<Option, String> mapToAddTo, final Option key, final boolean value, final boolean addDefaults) {
    // false should be the default value, so only add if value is true, or if defaults should be added anyway.
    if (addDefaults || value) {
      mapToAddTo.put(key, String.valueOf(value));
    }
  }

}
