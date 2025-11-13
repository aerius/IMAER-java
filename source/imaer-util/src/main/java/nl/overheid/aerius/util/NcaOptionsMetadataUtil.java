/*
 * Crown copyright
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

import static nl.overheid.aerius.shared.domain.v2.characteristics.adms.ADMSLimits.ADMS_COMPLEX_TERRAIN_DEFAULT;
import static nl.overheid.aerius.shared.domain.v2.characteristics.adms.ADMSLimits.ADMS_PLUME_DEPLETION_NH3_DEFAULT;
import static nl.overheid.aerius.shared.domain.v2.characteristics.adms.ADMSLimits.ADMS_PLUME_DEPLETION_NOX_DEFAULT;
import static nl.overheid.aerius.shared.domain.v2.characteristics.adms.ADMSLimits.MIN_MONIN_OBUKHOV_LENGTH_DEFAULT;
import static nl.overheid.aerius.shared.domain.v2.characteristics.adms.ADMSLimits.PRIESTLEY_TAYLOR_PARAMETER_DEFAULT;
import static nl.overheid.aerius.shared.domain.v2.characteristics.adms.ADMSLimits.ROUGHNESS_DEFAULT;
import static nl.overheid.aerius.shared.domain.v2.characteristics.adms.ADMSLimits.SPATIALLY_VARYING_ROUGHNESS_DEFAULT;
import static nl.overheid.aerius.shared.domain.v2.characteristics.adms.ADMSLimits.SURFACE_ALBEDO_DEFAULT;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nl.overheid.aerius.shared.domain.calculation.ADMSOptions;
import nl.overheid.aerius.shared.domain.calculation.CalculationSetOptions;
import nl.overheid.aerius.shared.domain.calculation.MetDatasetType;
import nl.overheid.aerius.shared.domain.calculation.MetSurfaceCharacteristics;
import nl.overheid.aerius.shared.domain.calculation.NCACalculationOptions;
import nl.overheid.aerius.shared.domain.calculation.RoadLocalFractionNO2Option;
import nl.overheid.aerius.util.OptionsMetadataUtil.Option;

/**
 * Util class to read/write NCA theme meta data options from/to a map into/from the CalculationSetOptions object.
 */
final class NcaOptionsMetadataUtil extends OptionsMetadataUtilBase {

  private NcaOptionsMetadataUtil() {
    // Util class
  }

  /**
   * Reads the NCA specific {@link CalculationSetOptions} from a map.
   *
   * @param map The map to read the options from
   * @param prefixedOptionsMap map of options that have a prefixed key
   * @param options The object to set the options in
   */
  static void ncaCalculationSetOptionsFromMap(final Map<Option, String> map, final Map<String, Map<Option, String>> prefixedOptionsMap,
      final CalculationSetOptions options) {
    final NCACalculationOptions ncaCalculationOptions = options.getNcaCalculationOptions();

    NcaOptionsMetadataUtil.ncaOptionsFromMap(ncaCalculationOptions, map, prefixedOptionsMap);
    options.getCalculatedSnapshotValues().setDevelopmentPressureClassification(map.get(Option.DEVELOPMENT_PRESSURE_CLASSIFICATION));
  }

  private static void ncaOptionsFromMap(final NCACalculationOptions options, final Map<Option, String> map,
      final Map<String, Map<Option, String>> prefixedOptionsMap) {
    options.setProjectCategory(map.get(Option.PROJECT_CATEGORY));
    options.setPermitArea(map.get(Option.PERMIT_AREA));
    parseListOption(Option.DEVELOPMENT_PRESSURE_SOURCE_IDS, map, options::setDevelopmentPressureSourceIds);
    options.setRoadLocalFractionNO2ReceptorsOption(RoadLocalFractionNO2Option.safeValueOf(map.get(Option.ROAD_LOCAL_FRACTION_NO2_RECEPTORS_OPTION)));
    options.setRoadLocalFractionNO2PointsOption(RoadLocalFractionNO2Option.safeValueOf(map.get(Option.ROAD_LOCAL_FRACTION_NO2_POINTS_OPTION)));
    if (options.getRoadLocalFractionNO2ReceptorsOption() == RoadLocalFractionNO2Option.ONE_CUSTOM_VALUE
        || options.getRoadLocalFractionNO2PointsOption() == RoadLocalFractionNO2Option.ONE_CUSTOM_VALUE) {
      options.setRoadLocalFractionNO2(
          Optional.ofNullable(map.get(Option.ROAD_LOCAL_FRACTION_NO2_CUSTOM_VALUE)).map(Double::parseDouble).orElse(null));
    }

    final ADMSOptions admsOptions = options.getAdmsOptions();
    admsOptions.setMinMoninObukhovLength(getOrDefault(map, Option.ADMS_MIN_MONIN_OBUKHOV_LENGTH, MIN_MONIN_OBUKHOV_LENGTH_DEFAULT));
    admsOptions.setSurfaceAlbedo(getOrDefault(map, Option.ADMS_SURFACE_ALBEDO, SURFACE_ALBEDO_DEFAULT));
    admsOptions.setPriestleyTaylorParameter(getOrDefault(map, Option.ADMS_PRIESTLEY_TAYLOR_PARAMETER, PRIESTLEY_TAYLOR_PARAMETER_DEFAULT));

    if (map.get(Option.ADMS_MET_SITE_ID) != null) {
      admsOptions.setMetSiteId(Integer.parseInt(map.get(Option.ADMS_MET_SITE_ID)));
      admsOptions.setMetSiteLatitude(Optional.ofNullable(map.get(Option.ADMS_MET_SITE_LATITUDE)).map(Double::parseDouble).orElse(0.0));
      admsOptions.setMetDatasetType(MetDatasetType.safeValueOf(map.get(Option.ADMS_MET_DATASET_TYPE)));
      parseListOption(Option.ADMS_MET_YEARS, map, admsOptions::setMetYears);
      ncaMetSiteOptions(prefixedOptionsMap, admsOptions, map);
    }
    admsOptions.setPlumeDepletionNH3(isOrDefault(map, Option.ADMS_PLUME_DEPLETION_NH3, ADMS_PLUME_DEPLETION_NH3_DEFAULT));
    admsOptions.setPlumeDepletionNOX(isOrDefault(map, Option.ADMS_PLUME_DEPLETION_NOX, ADMS_PLUME_DEPLETION_NOX_DEFAULT));
    admsOptions.setSpatiallyVaryingRoughness(isOrDefault(map, Option.ADMS_SPATIALLY_VARYING_ROUGHNESS, SPATIALLY_VARYING_ROUGHNESS_DEFAULT));
    admsOptions.setComplexTerrain(isOrDefault(map, Option.ADMS_COMPLEX_TERRAIN, ADMS_COMPLEX_TERRAIN_DEFAULT));
  }

  /**
   * Read Met Site data. If multiple met years are present the met site options key is expected to be prefixed with the met year.
   * If only 1 year or no met year is present the options are not prefixed.
   */
  private static void ncaMetSiteOptions(final Map<String, Map<Option, String>> prefixedOptionsMap, final ADMSOptions admsOptions,
      final Map<Option, String> map) {
    if (admsOptions.getMetYears().size() > 1) {
      for (final String metYear : admsOptions.getMetYears()) {
        final Map<Option, String> prefixedMap = prefixedOptionsMap.get(metYear);
        ncaPutMetSiteOptions(admsOptions, metYear, prefixedMap);
      }
    } else {
      ncaPutMetSiteOptions(admsOptions, "", map);
    }
  }

  private static void ncaPutMetSiteOptions(final ADMSOptions admsOptions, final String metYear, final Map<Option, String> prefixedMap) {
    final MetSurfaceCharacteristics msc = MetSurfaceCharacteristics.builder()
        .roughness(getOrDefault(prefixedMap, Option.ADMS_MET_SITE_ROUGHNESS, ROUGHNESS_DEFAULT))
        .minMoninObukhovLength(getOrDefault(prefixedMap, Option.ADMS_MET_SITE_MIN_MONIN_OBUKHOV_LENGTH,
            MIN_MONIN_OBUKHOV_LENGTH_DEFAULT))
        .surfaceAlbedo(getOrDefault(prefixedMap, Option.ADMS_MET_SITE_SURFACE_ALBEDO, SURFACE_ALBEDO_DEFAULT))
        .priestleyTaylorParameter(getOrDefault(prefixedMap, Option.ADMS_MET_SITE_PRIESTLEY_TAYLOR_PARAMETER,
            PRIESTLEY_TAYLOR_PARAMETER_DEFAULT))
        .windInSectors(isOrDefault(prefixedMap, Option.ADMS_MET_SITE_WIND_IN_SECTORS, false))
        .build();
    admsOptions.putMetSiteCharacteristics(metYear, msc);
  }

  /**
   * Put NCA theme options into a map to be stored in the GML.
   *
   * @param options Object to get the options from
   * @param mapToAddTo map to add the options to
   * @param addDefaults flag if set to true will add default values when no value is present in the options object
   */
  static void ncaCalculationSetOptionsToMap(final CalculationSetOptions options, final Map<String, String> mapToAddTo, final boolean addDefaults) {
    ncaOptionsToMap(mapToAddTo, options.getNcaCalculationOptions(), addDefaults);
    addValue(mapToAddTo, Option.DEVELOPMENT_PRESSURE_CLASSIFICATION, options.getCalculatedSnapshotValues().getDevelopmentPressureClassification(),
        addDefaults);
  }

  private static void ncaOptionsToMap(final Map<String, String> mapToAddTo, final NCACalculationOptions options, final boolean addDefaults) {
    if (options != null) {
      addValue(mapToAddTo, Option.ADMS_VERSION, options.getAdmsVersion(), addDefaults);
      addValue(mapToAddTo, Option.PROJECT_CATEGORY, options.getProjectCategory(), addDefaults);
      addValue(mapToAddTo, Option.PERMIT_AREA, options.getPermitArea(), addDefaults);
      if (!options.getDevelopmentPressureSourceIds().isEmpty()) {
        addValue(mapToAddTo, Option.DEVELOPMENT_PRESSURE_SOURCE_IDS,
            String.join(LIST_OPTION_SEPARATOR, options.getDevelopmentPressureSourceIds()), addDefaults);
      }
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
        if (adms.getMetSiteLatitude() != 0.0) {
          addValue(mapToAddTo, Option.ADMS_MET_SITE_LATITUDE, adms.getMetSiteLatitude(), addDefaults);
        }
        addValue(mapToAddTo, Option.ADMS_MET_DATASET_TYPE, adms.getMetDatasetType(), addDefaults);
        ncaAddMetSite(mapToAddTo, addDefaults, adms);
        // Always add the following fields to the GML as it also gives an indication if run in demo mode.
        addBooleanValue(mapToAddTo, Option.ADMS_PLUME_DEPLETION_NH3, adms.isPlumeDepletionNH3(), true);
        addBooleanValue(mapToAddTo, Option.ADMS_PLUME_DEPLETION_NOX, adms.isPlumeDepletionNOX(), true);
        addBooleanValue(mapToAddTo, Option.ADMS_SPATIALLY_VARYING_ROUGHNESS, adms.isSpatiallyVaryingRoughness(), true);
        addBooleanValue(mapToAddTo, Option.ADMS_COMPLEX_TERRAIN, adms.isComplexTerrain(), true);
      }
    }
  }

  /**
   * Add Met Site data. If multiple met years are present the met site options key is prefixed with the met year.
   * If only 1 year or no met year is present the options are not prefixed.
   */
  private static void ncaAddMetSite(final Map<String, String> mapToAddTo, final boolean addDefaults, final ADMSOptions adms) {
    final List<String> metYears = adms.getMetYears();
    if (!metYears.isEmpty()) {
      addValue(mapToAddTo, Option.ADMS_MET_YEARS, String.join(LIST_OPTION_SEPARATOR, metYears), addDefaults);
    }
    if (metYears.size() > 1) {
      for (final String metYear : metYears) {
        final String prefix = metYear + OPTION_KEY_SPLIT;
        addADMSMetSiteOptions(mapToAddTo, addDefaults, adms.getMetSiteCharacteristics(metYear), prefix);
      }
    } else {
      final String metYear = metYears.isEmpty() ? "" : metYears.get(0);
      addADMSMetSiteOptions(mapToAddTo, addDefaults, adms.getMetSiteCharacteristics(metYear), "");
    }
  }

  private static void addADMSMetSiteOptions(final Map<String, String> mapToAddTo, final boolean addDefaults, final MetSurfaceCharacteristics msc,
      final String prefix) {

    addValue(mapToAddTo, prefix + Option.ADMS_MET_SITE_ROUGHNESS.toKey(), msc.getRoughness(), addDefaults);
    addValue(mapToAddTo, prefix + Option.ADMS_MET_SITE_MIN_MONIN_OBUKHOV_LENGTH.toKey(), msc.getMinMoninObukhovLength(), addDefaults);
    addValue(mapToAddTo, prefix + Option.ADMS_MET_SITE_SURFACE_ALBEDO.toKey(), msc.getSurfaceAlbedo(), addDefaults);
    addValue(mapToAddTo, prefix + Option.ADMS_MET_SITE_PRIESTLEY_TAYLOR_PARAMETER.toKey(), msc.getPriestleyTaylorParameter(), addDefaults);
    if (msc.isWindInSectors()) {
      addValue(mapToAddTo, prefix + Option.ADMS_MET_SITE_WIND_IN_SECTORS.toKey(), String.valueOf(msc.isWindInSectors()), addDefaults);
    }
  }
}
