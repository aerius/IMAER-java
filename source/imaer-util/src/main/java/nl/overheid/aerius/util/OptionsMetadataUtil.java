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

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import nl.overheid.aerius.shared.domain.Theme;
import nl.overheid.aerius.shared.domain.calculation.ADMSOptions;
import nl.overheid.aerius.shared.domain.calculation.CalculationJobType;
import nl.overheid.aerius.shared.domain.calculation.CalculationRoadOPS;
import nl.overheid.aerius.shared.domain.calculation.CalculationSetOptions;
import nl.overheid.aerius.shared.domain.calculation.ConnectSuppliedOptions;
import nl.overheid.aerius.shared.domain.calculation.MetDatasetType;
import nl.overheid.aerius.shared.domain.calculation.NCACalculationOptions;
import nl.overheid.aerius.shared.domain.calculation.OPSOptions;
import nl.overheid.aerius.shared.domain.calculation.OwN2000CalculationOptions;
import nl.overheid.aerius.shared.domain.calculation.RoadLocalFractionNO2Option;
import nl.overheid.aerius.shared.domain.v2.characteristics.adms.ADMSLimits;

/**
 * Utility class to convert calculation options to metadata.
 */
public final class OptionsMetadataUtil {

  private static final String METEO_YEARS_SEPARATOR = ",";

  public enum Option {
    // @formatter:off
    METEO_YEAR,
    WITHOUT_SOURCE_STACKING,
    OPS_ROAD,
    FORCED_AGGREGATION,
    USE_RECEPTOR_HEIGHT,
    MONITOR_SRM2_YEAR,
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
    ADMS_PERMIT_AREA,
    ADMS_MIN_MONIN_OBUKHOV_LENGTH,
    ADMS_SURFACE_ALBEDO,
    ADMS_PRIESTLEY_TAYLOR_PARAMETER,
    ADMS_PLUME_DEPLETION_NH3,
    ADMS_PLUME_DEPLETION_NOX,
    ADMS_SPATIALLY_VARYING_ROUGHNESS,
    ADMS_COMPLEX_TERRAIN,
    ADMS_MET_SITE_ID,
    ADMS_MET_DATASET_TYPE,
    ADMS_MET_YEARS,
    ADMS_MET_SITE_ROUGHNESS,
    ADMS_MET_SITE_MIN_MONIN_OBUKHOV_LENGTH,
    ADMS_MET_SITE_SURFACE_ALBEDO,
    ADMS_MET_SITE_PRIESTLEY_TAYLOR_PARAMETER,

    /* Road NOX - NO2 calculation related */
    ROAD_LOCAL_FRACTION_NO2_RECEPTORS_OPTION,
    ROAD_LOCAL_FRACTION_NO2_POINTS_OPTION,
    ROAD_LOCAL_FRACTION_NO2_CUSTOM_VALUE,
    ;
    // @formatter:on

    String toKey() {
      return name().toLowerCase();
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

  public static void addOptionsFromMap(final Theme theme, final Map<Option, String> map, final CalculationSetOptions options) {
    if (theme == Theme.NCA) {
      final NCACalculationOptions ncaCalculationOptions = options.getNcaCalculationOptions();
      ncaOptionsFromMap(ncaCalculationOptions, map);
    }
  }

  public static Map<String, String> optionsToMap(final Theme theme, final CalculationSetOptions options, final boolean addDefaults) {
    final Map<Option, String> mapToAddTo = new LinkedHashMap<>();
    addBooleanValue(mapToAddTo, Option.WITHOUT_SOURCE_STACKING, !options.isStacking(), addDefaults);
    if (options.getCalculationJobType() == CalculationJobType.IN_COMBINATION_PROCESS_CONTRIBUTION) {
      addBooleanValue(mapToAddTo, Option.USE_IN_COMBINATION_ARCHIVE, options.isUseInCombinationArchive(), addDefaults);
    }
    if (theme == null || theme == Theme.OWN2000 || theme == Theme.RBL) {
      own2000OptionsToMap(mapToAddTo, options.getOwN2000CalculationOptions(), addDefaults);
      addIntValue(mapToAddTo, Option.MONITOR_SRM2_YEAR, options.getRblCalculationOptions().getMonitorSrm2Year(), addDefaults);
    } else if (theme == Theme.NCA) {
      ncaOptionsToMap(mapToAddTo, options.getNcaCalculationOptions(), addDefaults);
    }
    addConnectSuppliedOptions(options.getConnectSuppliedOptions(), mapToAddTo, addDefaults);

    final Map<String, String> returnMap = new LinkedHashMap<>();
    mapToAddTo.forEach((key, value) -> returnMap.put(key.toKey(), value));
    return returnMap;
  }

  private static void own2000OptionsToMap(final Map<Option, String> mapToAddTo, final OwN2000CalculationOptions options, final boolean addDefaults) {
    addValue(mapToAddTo, Option.METEO_YEAR, options.getMeteo(), addDefaults);
    if (addDefaults || options.getRoadOPS() != CalculationRoadOPS.DEFAULT) {
      mapToAddTo.put(Option.OPS_ROAD, options.getRoadOPS().name());
    }
    addBooleanValue(mapToAddTo, Option.FORCED_AGGREGATION, options.isForceAggregation(), addDefaults);
    addBooleanValue(mapToAddTo, Option.USE_RECEPTOR_HEIGHT, options.isUseReceptorHeights(), addDefaults);
    addBooleanValue(mapToAddTo, Option.WITH_MAX_DISTANCE, options.isUseMaxDistance(), addDefaults);
    addValue(mapToAddTo, Option.SUB_RECEPTORS_MODE, options.getSubReceptorsMode(), addDefaults);
    addValue(mapToAddTo, Option.SUB_RECEPTOR_ZOOM_LEVEL, options.getSubReceptorZoomLevel(), addDefaults);
    addBooleanValue(mapToAddTo, Option.SPLIT_SUB_RECEPTOR_WORK, options.isSplitSubReceptorWork(), addDefaults);
    if (options.isSplitSubReceptorWork()) {
      addValue(mapToAddTo, Option.SPLIT_SUB_RECEPTOR_WORK_DISTANCE, options.getSplitSubReceptorWorkDistance(), addDefaults);
    }
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
      addValue(mapToAddTo, Option.OPS_CHEMISTRY, Optional.ofNullable(options.getChemistry()).map(OPSOptions.Chemistry::name).orElse(null),
          addDefaults);
      addValue(mapToAddTo, Option.OPS_COMP_CODE, options.getCompCode(), addDefaults);
      addValue(mapToAddTo, Option.OPS_MOL_WEIGHT, options.getMolWeight(), addDefaults);
      addValue(mapToAddTo, Option.OPS_PHASE, options.getPhase(), addDefaults);
      addValue(mapToAddTo, Option.OPS_LOSS, options.getLoss(), addDefaults);
      addValue(mapToAddTo, Option.OPS_DIFF_COEFF, options.getDiffCoeff(), addDefaults);
      addValue(mapToAddTo, Option.OPS_WASHOUT, options.getWashout(), addDefaults);
      addValue(mapToAddTo, Option.OPS_CONV_RATE, options.getConvRate(), addDefaults);
      addValue(mapToAddTo, Option.OPS_ROUGHNESS, options.getRoughness(), addDefaults);
      addValue(mapToAddTo, Option.OPS_ROADS, options.getRoads(), addDefaults);
    }
  }

  private static void ncaOptionsFromMap(final NCACalculationOptions options, final Map<Option, String> map) {
    options.setPermitArea(map.get(Option.ADMS_PERMIT_AREA));
    options.setRoadLocalFractionNO2ReceptorsOption(RoadLocalFractionNO2Option.safeValueOf(map.get(Option.ROAD_LOCAL_FRACTION_NO2_RECEPTORS_OPTION)));
    options.setRoadLocalFractionNO2PointsOption(RoadLocalFractionNO2Option.safeValueOf(map.get(Option.ROAD_LOCAL_FRACTION_NO2_POINTS_OPTION)));
    if (options.getRoadLocalFractionNO2ReceptorsOption() == RoadLocalFractionNO2Option.ONE_CUSTOM_VALUE
        || options.getRoadLocalFractionNO2PointsOption() == RoadLocalFractionNO2Option.ONE_CUSTOM_VALUE) {
      options.setRoadLocalFractionNO2(
          Optional.ofNullable(map.get(Option.ROAD_LOCAL_FRACTION_NO2_CUSTOM_VALUE)).map(Double::parseDouble).orElse(null));
    }

    final ADMSOptions admsOptions = options.getAdmsOptions();
    admsOptions.setMinMoninObukhovLength(getOrDefault(map, Option.ADMS_MIN_MONIN_OBUKHOV_LENGTH, ADMSLimits.MIN_MONIN_OBUKHOV_LENGTH_DEFAULT));
    admsOptions.setSurfaceAlbedo(getOrDefault(map, Option.ADMS_SURFACE_ALBEDO, ADMSLimits.SURFACE_ALBEDO_DEFAULT));
    admsOptions.setPriestleyTaylorParameter(getOrDefault(map, Option.ADMS_PRIESTLEY_TAYLOR_PARAMETER, ADMSLimits.PRIESTLEY_TAYLOR_PARAMETER_DEFAULT));

    if (map.get(Option.ADMS_MET_SITE_ID) != null) {
      admsOptions.setMetSiteId(Integer.parseInt(map.get(Option.ADMS_MET_SITE_ID)));
      admsOptions.setMetDatasetType(MetDatasetType.safeValueOf(map.get(Option.ADMS_MET_DATASET_TYPE)));
      parseMetYears(admsOptions, map);
      admsOptions.setMsRoughness(getOrDefault(map, Option.ADMS_MET_SITE_ROUGHNESS, ADMSLimits.ROUGHNESS_DEFAULT));
      admsOptions
          .setMsMinMoninObukhovLength(getOrDefault(map, Option.ADMS_MET_SITE_MIN_MONIN_OBUKHOV_LENGTH, ADMSLimits.MIN_MONIN_OBUKHOV_LENGTH_DEFAULT));
      admsOptions.setMsSurfaceAlbedo(getOrDefault(map, Option.ADMS_MET_SITE_SURFACE_ALBEDO, ADMSLimits.SURFACE_ALBEDO_DEFAULT));
      admsOptions.setMsPriestleyTaylorParameter(
          getOrDefault(map, Option.ADMS_MET_SITE_PRIESTLEY_TAYLOR_PARAMETER, ADMSLimits.PRIESTLEY_TAYLOR_PARAMETER_DEFAULT));
    }

    admsOptions.setPlumeDepletionNH3(getOrDefault(map, Option.ADMS_PLUME_DEPLETION_NH3, ADMSLimits.ADMS_PLUME_DEPLETION_NH3_DEFAULT));
    admsOptions.setPlumeDepletionNOX(getOrDefault(map, Option.ADMS_PLUME_DEPLETION_NOX, ADMSLimits.ADMS_PLUME_DEPLETION_NOX_DEFAULT));
    admsOptions
        .setSpatiallyVaryingRoughness(getOrDefault(map, Option.ADMS_SPATIALLY_VARYING_ROUGHNESS, ADMSLimits.SPATIALLY_VARYING_ROUGHNESS_DEFAULT));
    admsOptions.setComplexTerrain(getOrDefault(map, Option.ADMS_COMPLEX_TERRAIN, ADMSLimits.ADMS_COMPLEX_TERRAIN_DEFAULT));
  }

  private static double getOrDefault(final Map<Option, String> map, final Option option, final double defaultValue) {
    return Optional.ofNullable(map.get(option)).map(Double::parseDouble).orElse(defaultValue);
  }

  private static boolean getOrDefault(final Map<Option, String> map, final Option option, final boolean defaultValue) {
    return Optional.ofNullable(map.get(option)).map(Boolean::parseBoolean).orElse(defaultValue);
  }

  private static void parseMetYears(final ADMSOptions admsOptions, final Map<Option, String> map) {
    final String metYearString = map.get(Option.ADMS_MET_YEARS);
    if (metYearString != null && !metYearString.isBlank()) {
      final String[] meteoYears = metYearString.split(METEO_YEARS_SEPARATOR);
      if (meteoYears.length > 0) {
        admsOptions.setMetYears(Arrays.asList(meteoYears));
      }
    }
  }

  private static void ncaOptionsToMap(final Map<Option, String> mapToAddTo, final NCACalculationOptions options, final boolean addDefaults) {
    if (options != null) {
      addValue(mapToAddTo, Option.ADMS_VERSION, options.getAdmsVersion(), addDefaults);
      addValue(mapToAddTo, Option.ADMS_PERMIT_AREA, options.getPermitArea(), addDefaults);
      addValue(mapToAddTo, Option.ROAD_LOCAL_FRACTION_NO2_RECEPTORS_OPTION, options.getRoadLocalFractionNO2ReceptorsOption(), addDefaults);
      addValue(mapToAddTo, Option.ROAD_LOCAL_FRACTION_NO2_POINTS_OPTION, options.getRoadLocalFractionNO2PointsOption(), addDefaults);
      if (options.getRoadLocalFractionNO2ReceptorsOption() == RoadLocalFractionNO2Option.ONE_CUSTOM_VALUE
          || options.getRoadLocalFractionNO2PointsOption() == RoadLocalFractionNO2Option.ONE_CUSTOM_VALUE) {
        addValue(mapToAddTo, Option.ROAD_LOCAL_FRACTION_NO2_CUSTOM_VALUE, options.getRoadLocalFractionNO2(), addDefaults);
      }
      final ADMSOptions adms = options.getAdmsOptions();

      if (adms != null) {
        addValue(mapToAddTo, Option.ADMS_MIN_MONIN_OBUKHOV_LENGTH, adms.getMinMoninObukhovLength(), addDefaults);
        addValue(mapToAddTo, Option.ADMS_SURFACE_ALBEDO, adms.getSurfaceAlbedo(), addDefaults);
        addValue(mapToAddTo, Option.ADMS_PRIESTLEY_TAYLOR_PARAMETER, adms.getPriestleyTaylorParameter(), addDefaults);
        addIntValue(mapToAddTo, Option.ADMS_MET_SITE_ID, adms.getMetSiteId(), addDefaults);
        addValue(mapToAddTo, Option.ADMS_MET_DATASET_TYPE, adms.getMetDatasetType(), addDefaults);
        addValue(mapToAddTo, Option.ADMS_MET_YEARS, adms.getMetYears().isEmpty() ? null : String.join(METEO_YEARS_SEPARATOR, adms.getMetYears()),
            addDefaults);
        addValue(mapToAddTo, Option.ADMS_MET_SITE_ROUGHNESS, adms.getMsRoughness(), addDefaults);
        addValue(mapToAddTo, Option.ADMS_MET_SITE_MIN_MONIN_OBUKHOV_LENGTH, adms.getMsMinMoninObukhovLength(), addDefaults);
        addValue(mapToAddTo, Option.ADMS_MET_SITE_SURFACE_ALBEDO, adms.getMsSurfaceAlbedo(), addDefaults);
        addValue(mapToAddTo, Option.ADMS_MET_SITE_PRIESTLEY_TAYLOR_PARAMETER, adms.getMsPriestleyTaylorParameter(), addDefaults);
        // Always add the following fields to the GML as it also gives an indication if run in demo mode.
        addBooleanValue(mapToAddTo, Option.ADMS_PLUME_DEPLETION_NH3, adms.isPlumeDepletionNH3(), true);
        addBooleanValue(mapToAddTo, Option.ADMS_PLUME_DEPLETION_NOX, adms.isPlumeDepletionNOX(), true);
        addBooleanValue(mapToAddTo, Option.ADMS_SPATIALLY_VARYING_ROUGHNESS, adms.isSpatiallyVaryingRoughness(), true);
        addBooleanValue(mapToAddTo, Option.ADMS_COMPLEX_TERRAIN, adms.isComplexTerrain(), true);
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
