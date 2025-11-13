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

import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import nl.overheid.aerius.shared.domain.Theme;
import nl.overheid.aerius.shared.domain.calculation.CalculationJobType;
import nl.overheid.aerius.shared.domain.calculation.CalculationSetOptions;
import nl.overheid.aerius.shared.domain.calculation.ConnectSuppliedOptions;

/**
 * Utility class to convert calculation options to metadata.
 */
public final class OptionsMetadataUtil extends OptionsMetadataUtilBase {

  public enum Option {
    // @formatter:off
    METEO_YEAR,
    WITHOUT_SOURCE_STACKING,
    OPS_ROAD,
    FORCED_AGGREGATION,
    USE_RECEPTOR_HEIGHT,
    MONITOR_SRM2_YEAR,
    PERMIT_LOWER_BOUND,
    PERMIT_LOWER_BOUND_VALUE,
    WITH_MAX_DISTANCE,
    SUB_RECEPTORS_MODE,
    SUB_RECEPTOR_ZOOM_LEVEL,
    SPLIT_SUB_RECEPTOR_WORK,
    SPLIT_SUB_RECEPTOR_WORK_DISTANCE,
    USE_IN_COMBINATION_ARCHIVE,

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
    OPS_CHEMISTRY,
    OPS_ROADS,

    /* ADMS options related */
    ADMS_VERSION,
    ADMS_MIN_MONIN_OBUKHOV_LENGTH,
    ADMS_SURFACE_ALBEDO,
    ADMS_PRIESTLEY_TAYLOR_PARAMETER,
    ADMS_PLUME_DEPLETION_NH3,
    ADMS_PLUME_DEPLETION_NOX,
    ADMS_SPATIALLY_VARYING_ROUGHNESS,
    ADMS_COMPLEX_TERRAIN,
    ADMS_MET_SITE_ID,
    ADMS_MET_SITE_LATITUDE,
    ADMS_MET_DATASET_TYPE,
    ADMS_MET_YEARS,
    ADMS_MET_SITE_ROUGHNESS,
    ADMS_MET_SITE_MIN_MONIN_OBUKHOV_LENGTH,
    ADMS_MET_SITE_SURFACE_ALBEDO,
    ADMS_MET_SITE_PRIESTLEY_TAYLOR_PARAMETER,
    ADMS_MET_SITE_WIND_IN_SECTORS,

    /* NCA options related */
    PROJECT_CATEGORY,
    PERMIT_AREA,
    DEVELOPMENT_PRESSURE_SOURCE_IDS,

    /* Calculated snapshot values related */
    DEVELOPMENT_PRESSURE_CLASSIFICATION,

    /* Road NOX - NO2 calculation related */
    ROAD_LOCAL_FRACTION_NO2_RECEPTORS_OPTION,
    ROAD_LOCAL_FRACTION_NO2_POINTS_OPTION,
    ROAD_LOCAL_FRACTION_NO2_CUSTOM_VALUE,
    ;
    // @formatter:on

    String toKey() {
      return name().toLowerCase(Locale.ROOT);
    }

    public static Option safeValueOf(final String value) {
      try {
        return value == null ? null : valueOf(value.toUpperCase(Locale.ROOT));
      } catch (final IllegalArgumentException e) {
        return null;
      }
    }

  }

  private OptionsMetadataUtil() {
    //util class
  }

  public static void addOptionsFromMap(final Theme theme, final Map<String, String> map, final CalculationSetOptions options) {
    final Map<Option, String> optionsMap = new EnumMap<>(Option.class);
    final Map<String, Map<Option, String>> prefixedOptionsMap = new HashMap<>();

    map.forEach((k, v) -> mapOptions(optionsMap, prefixedOptionsMap, k.toUpperCase(Locale.ROOT), v));
    addOptionsFromMap(theme, optionsMap, prefixedOptionsMap, options);
  }

  /**
   * Store the given option (key and value) into the options maps. If the key is prefixed (i.e. it contains a -) the option is stored in the
   * prefixedOptionsMap otherwise the option is stored in the optionsMap.
   *
   * @param optionsMap
   * @param prefixedOptionsMap
   * @param key
   * @param value
   */
  private static void mapOptions(final Map<Option, String> optionsMap, final Map<String, Map<Option, String>> prefixedOptionsMap,
      final String key, final String value) {
    final Option parsed = Option.safeValueOf(key);

    if (parsed == null) {
      final String[] values = key.split(OPTION_KEY_SPLIT);

      if (values.length == 2) {
        final Option parsedValue = Option.safeValueOf(values[1]);

        if (parsedValue != null) {
          prefixedOptionsMap.computeIfAbsent(values[0], k -> new EnumMap<>(Option.class)).put(parsedValue, value);
        } // else the option could not be referenced and will be ignored.
      }
    } else {
      optionsMap.put(parsed, value);
    }
  }

  private static void addOptionsFromMap(final Theme theme, final Map<Option, String> map,
      final Map<String, Map<Option, String>> prefixedOptionsMap, final CalculationSetOptions options) {
    if (theme == Theme.NCA) {
      NcaOptionsMetadataUtil.ncaCalculationSetOptionsFromMap(map, prefixedOptionsMap, options);
    } else {
      Own2000OptionsMetadataUtil.own2000CalculationSetOptionsFromMap(map, prefixedOptionsMap, options);
    }
  }

  public static Map<String, String> optionsToMap(final Theme theme, final CalculationSetOptions options, final boolean addDefaults) {
    final Map<String, String> mapToAddTo = new LinkedHashMap<>();
    addBooleanValue(mapToAddTo, Option.WITHOUT_SOURCE_STACKING, !options.isStacking(), addDefaults);
    if (options.getCalculationJobType() == CalculationJobType.IN_COMBINATION_PROCESS_CONTRIBUTION) {
      addBooleanValue(mapToAddTo, Option.USE_IN_COMBINATION_ARCHIVE, options.isUseInCombinationArchive(), addDefaults);
    }
    if (theme == null || theme == Theme.OWN2000 || theme == Theme.CIMLK) {
      Own2000OptionsMetadataUtil.own2000CalculationSetOptionsToMap(options, mapToAddTo, addDefaults);
    } else if (theme == Theme.NCA) {
      NcaOptionsMetadataUtil.ncaCalculationSetOptionsToMap(options, mapToAddTo, addDefaults);
    }
    addConnectSuppliedOptions(options.getConnectSuppliedOptions(), mapToAddTo, addDefaults);
    return mapToAddTo;
  }

  private static void addConnectSuppliedOptions(final ConnectSuppliedOptions connectSuppliedOptions, final Map<String, String> mapToAddTo,
      final boolean addDefaults) {
    if (connectSuppliedOptions != null) {
      addValue(mapToAddTo, Option.CALCULATION_YEAR, connectSuppliedOptions.getCalculationYear(), addDefaults);
      addValue(mapToAddTo, Option.RECEPTOR_SET, connectSuppliedOptions.getReceptorSetName(), addDefaults);
    }
  }
}
