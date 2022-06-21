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
import java.util.Optional;

import nl.overheid.aerius.shared.domain.Theme;
import nl.overheid.aerius.shared.domain.calculation.ADMSOptions;
import nl.overheid.aerius.shared.domain.calculation.CalculationRoadOPS;
import nl.overheid.aerius.shared.domain.calculation.CalculationSetOptions;
import nl.overheid.aerius.shared.domain.calculation.CalculationType;
import nl.overheid.aerius.shared.domain.calculation.ConnectSuppliedOptions;
import nl.overheid.aerius.shared.domain.calculation.NCACalculationOptions;
import nl.overheid.aerius.shared.domain.calculation.OPSOptions;
import nl.overheid.aerius.shared.domain.calculation.WNBCalculationOptions;

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
    WITH_WNB_MAX_DISTANCE,
    DISABLE_SUB_RECEPTORS,

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

    /* ADMS options related */
    ADMS_PERMIT_AREA,
    ADMS_METEO_SITE_LOCATION,
    ADMS_METEO_YEARS,
    ADMS_MIN_MONIN_OBUKHOV_LENGTH,
    ADMS_SURFACE_ALBEDO,
    ADMS_PRIESTLEY_TAYLOR_PARAMETER,
    ADMS_PLUME_DEPLETION_NH3,
    ADMS_PLUME_DEPLETION_NOX,
    ADMS_COMPLEX_TERRAIN,
    ;
    // @formatter:on

    String toKey() {
      return name().toLowerCase();
    }

  }

  private OptionsMetadataUtil() {
    //util class
  }

  public static Map<String, String> optionsToMap(final Theme theme, final CalculationSetOptions options, final boolean addDefaults) {
    final Map<Option, String> mapToAddTo = new LinkedHashMap<>();
    addBooleanValue(mapToAddTo, Option.WITHOUT_SOURCE_STACKING, !options.isStacking(), addDefaults);
    if (theme == null || theme == Theme.WNB || theme == Theme.RBL) {
      wnbOptionsToMap(mapToAddTo, options.getWnbCalculationOptions(), addDefaults, options.getCalculationType());
      addIntValue(mapToAddTo, Option.MONITOR_SRM2_YEAR, options.getRblCalculationOptions().getMonitorSrm2Year(), addDefaults);
    } else if (theme == Theme.NCA) {
      ncaOptionsToMap(mapToAddTo, options.getNcaCalculationOptions(), addDefaults);
    }
    addConnectSuppliedOptions(options.getConnectSuppliedOptions(), mapToAddTo, addDefaults);

    final Map<String, String> returnMap = new LinkedHashMap<>();
    mapToAddTo.forEach((key, value) -> returnMap.put(key.toKey(), value));
    return returnMap;
  }

  private static void wnbOptionsToMap(final Map<Option, String> mapToAddTo, final WNBCalculationOptions options, final boolean addDefaults,
      final CalculationType calculationType) {
    addValue(mapToAddTo, Option.METEO_YEAR, options.getMeteo(), addDefaults);
    if (addDefaults || options.getRoadOPS() != CalculationRoadOPS.DEFAULT) {
      mapToAddTo.put(Option.OPS_ROAD, options.getRoadOPS().name());
    }
    addBooleanValue(mapToAddTo, Option.FORCED_AGGREGATION, options.isForceAggregation(), addDefaults);
    addBooleanValue(mapToAddTo, Option.USE_RECEPTOR_HEIGHT, options.isUseReceptorHeights(), addDefaults);
    addBooleanValue(mapToAddTo, Option.WITH_WNB_MAX_DISTANCE, options.isUseWNBMaxDistance(), addDefaults);
    addBooleanValue(mapToAddTo, Option.DISABLE_SUB_RECEPTORS, options.isDisableSubReceptors(), addDefaults);
    opsOptionsToMap(options.getOpsOptions(), mapToAddTo, addDefaults);
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
      addValue(mapToAddTo, Option.OPS_CHEMISTRY, Optional.ofNullable(options.getChemistry()).map(OPSOptions.Chemistry::name).orElse(null), addDefaults);
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

  private static void ncaOptionsToMap(final Map<Option, String> mapToAddTo, final NCACalculationOptions options, final boolean addDefaults) {
    if (options != null) {
      addValue(mapToAddTo, Option.ADMS_PERMIT_AREA, options.getPermitArea(), addDefaults);
      addValue(mapToAddTo, Option.ADMS_METEO_SITE_LOCATION, options.getMeteoSiteLocation(), addDefaults);
      addValue(mapToAddTo, Option.ADMS_METEO_YEARS, String.join(",", options.getMeteoYears()), addDefaults);
      final ADMSOptions adms = options.getAdmsOptions();

      if (adms != null) {
        addValue(mapToAddTo, Option.ADMS_MIN_MONIN_OBUKHOV_LENGTH, adms.getMinMoninObukhovLength(), addDefaults);
        addValue(mapToAddTo, Option.ADMS_SURFACE_ALBEDO, adms.getSurfaceAlbedo(), addDefaults);
        addValue(mapToAddTo, Option.ADMS_PRIESTLEY_TAYLOR_PARAMETER, adms.getPriestleyTaylorParameter(), addDefaults);
        addBooleanValue(mapToAddTo, Option.ADMS_PLUME_DEPLETION_NH3, adms.isPlumeDepletionNH3(), addDefaults);
        addBooleanValue(mapToAddTo, Option.ADMS_PLUME_DEPLETION_NOX, adms.isPlumeDepletionNOX(), addDefaults);
        addBooleanValue(mapToAddTo, Option.ADMS_COMPLEX_TERRAIN, adms.isComplexTerrain(), addDefaults);
      }
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
    addIntValue(mapToAddTo, key, value, addDefaults, 0);
  }

  private static void addIntValue(final Map<Option, String> mapToAddTo, final Option key, final int value, final boolean addDefaults,
      final int defaultValue) {
    if (addDefaults || value != defaultValue) {
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
