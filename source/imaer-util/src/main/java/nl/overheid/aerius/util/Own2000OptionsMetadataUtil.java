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

import java.util.Map;
import java.util.Optional;

import nl.overheid.aerius.shared.domain.calculation.CalculationRoadOPS;
import nl.overheid.aerius.shared.domain.calculation.CalculationSetOptions;
import nl.overheid.aerius.shared.domain.calculation.OPSOptions;
import nl.overheid.aerius.shared.domain.calculation.OwN2000CalculationOptions;
import nl.overheid.aerius.shared.domain.calculation.PermitLowerBoundType;
import nl.overheid.aerius.util.OptionsMetadataUtil.Option;

/**
 * Util class to read/write Own2000 theme meta data options from/to a map into/from the CalculationSetOptions object.
 */
final class Own2000OptionsMetadataUtil extends OptionsMetadataUtilBase {

  private Own2000OptionsMetadataUtil() {
    // Util class
  }

  /**
   * Reads the Own2000 theme {@link CalculationSetOptions} from a map.
   *
   * @param map The map to read the options from
   * @param prefixedOptionsMap map of options that have a prefixed key
   * @param options The object to set the options in
   */
  static void own2000CalculationSetOptionsFromMap(final Map<Option, String> map, final Map<String, Map<Option, String>> prefixedOptionsMap,
      final CalculationSetOptions options) {
    final OwN2000CalculationOptions owN2000CalculationOptions = options.getOwN2000CalculationOptions();

    owN2000CalculationOptions.setPermitLowerBoundType(PermitLowerBoundType.safeValueOf(map.get(Option.PERMIT_LOWER_BOUND)));
  }

  /**
   * Put OWN2000 theme options into a map to be stored in the GML.
   *
   * @param options Object to get the options from
   * @param mapToAddTo map to add the options to
   * @param addDefaults flag if set to true will add default values when no value is present in the options object
   */
  static void own2000CalculationSetOptionsToMap(final CalculationSetOptions options, final Map<String, String> mapToAddTo, final boolean addDefaults) {
    own2000OptionsToMap(mapToAddTo, options.getOwN2000CalculationOptions(), addDefaults);
    addIntValue(mapToAddTo, Option.MONITOR_SRM2_YEAR, options.getRblCalculationOptions().getMonitorSrm2Year(), addDefaults);
  }

  private static void own2000OptionsToMap(final Map<String, String> mapToAddTo, final OwN2000CalculationOptions options, final boolean addDefaults) {
    addValue(mapToAddTo, Option.METEO_YEAR, options.getMeteo(), addDefaults);
    if (addDefaults || options.getRoadOPS() != CalculationRoadOPS.DEFAULT) {
      mapToAddTo.put(Option.OPS_ROAD.toKey(), options.getRoadOPS().name());
    }
    addBooleanValue(mapToAddTo, Option.FORCED_AGGREGATION, options.isForceAggregation(), addDefaults);
    addBooleanValue(mapToAddTo, Option.USE_RECEPTOR_HEIGHT, options.isUseReceptorHeights(), addDefaults);
    addPermitLowerBound(mapToAddTo, options);
    addBooleanValue(mapToAddTo, Option.WITH_MAX_DISTANCE, options.isUseMaxDistance(), addDefaults);
    addValue(mapToAddTo, Option.SUB_RECEPTORS_MODE, options.getSubReceptorsMode(), addDefaults);
    addValue(mapToAddTo, Option.SUB_RECEPTOR_ZOOM_LEVEL, options.getSubReceptorZoomLevel(), addDefaults);
    addBooleanValue(mapToAddTo, Option.SPLIT_SUB_RECEPTOR_WORK, options.isSplitSubReceptorWork(), addDefaults);
    if (options.isSplitSubReceptorWork()) {
      addValue(mapToAddTo, Option.SPLIT_SUB_RECEPTOR_WORK_DISTANCE, options.getSplitSubReceptorWorkDistance(), addDefaults);
    }
    opsOptionsToMap(options.getOpsOptions(), mapToAddTo, addDefaults);
  }

  private static void addPermitLowerBound(final Map<String, String> mapToAddTo, final OwN2000CalculationOptions options) {
    if (options.getPermitLowerBoundType() != null) {
      addValue(mapToAddTo, Option.PERMIT_LOWER_BOUND, options.getPermitLowerBoundType().name(), false);
      addValue(mapToAddTo, Option.PERMIT_LOWER_BOUND_VALUE, options.getPermitLowerBoundValue(), false);
    }
  }

  private static void opsOptionsToMap(final OPSOptions options, final Map<String, String> mapToAddTo, final boolean addDefaults) {
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
      addValue(mapToAddTo, Option.OPS_ROADS_OPT, options.getRoadsOpt(), addDefaults);
    }
  }
}
