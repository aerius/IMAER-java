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
package nl.overheid.aerius.shared.domain.ops;

import nl.overheid.aerius.shared.domain.v2.building.BuildingLimits;

/**
 * Contains any limits for input for OPS (Source and Emission files).
 */
public final class OPSLimits implements BuildingLimits {

  private static final long serialVersionUID = 1L;

  /**
   * receptor ID: fortran notation: I8 -> Over max value of int.
   */
  public static final int RECEPTOR_ID_MINIMUM = Integer.MIN_VALUE;

  /**
   * receptor ID: fortran notation: I8 -> Over max value of int.
   */
  public static final int RECEPTOR_ID_MAXIMUM = Integer.MAX_VALUE;

  /**
   * Minimum RDM X-coordinate according to wiki (NL).
   * X_COORDINATE_MINIMUM = -7;
   * RIVM: due to I8 format: min is -9999999
   * (this limit exceeds dutch territory by a fair bit)
   */
  public static final int X_COORDINATE_MINIMUM = -9999999;

  /**
   * Maximum RDM X-coordinate according to wiki (NL)
   * X_COORDINATE_MAXIMUM = 300000;
   * RIVM: due to I8 format: max is 99999999
   * testing: When coordinates are  > 9999999 (one 9 less) OPS starts to be unresponsive.
   * To avoid problems with workers becoming unresponsive, we use this as limit.
   * (this limit exceeds dutch territory by a fair bit)
   */
  public static final int X_COORDINATE_MAXIMUM = 9999999;

  /**
   * Minimum RDM Y-coordinate according to wiki (NL).
   * Y_COORDINATE_MINIMUM = 289000;
   * RIVM: due to I8 format: min is -9999999
   * (this limit exceeds dutch territory by a fair bit)
   */
  public static final int Y_COORDINATE_MINIMUM = -9999999;

  /**
   * Maximum RDM Y-coordinate according to wiki (NL)
   * Y_COORDINATE_MAXIMUM = 629000;
   * RIVM: due to I8 format: max is 99999999
   * testing: When coordinates are  > 9999999 (one 9 less) OPS starts to be unresponsive.
   * To avoid problems with workers becoming unresponsive, we use this as limit.
   * (this limit exceeds dutch territory by a fair bit)
   */
  public static final int Y_COORDINATE_MAXIMUM = 9999999;

  /**
   * source ID (ssn): fortran notation: I4 -> -999 min.
   * NOTE: not used, source ID for OPS is defined by the worker.
   */
  public static final int SOURCE_ID_MINIMUM = -999;

  /**
   * source ID (ssn): fortran notation: I4 -> 9999 max.
   * NOTE: not used, source ID for OPS is defined by the worker.
   */
  public static final int SOURCE_ID_MAXIMUM = 9999;

  /**
   * source emission (q): fortran notation: E10.3 -> no min/max (will be converted to something like 0.9876E543).
   * negative emission makes no sense.
   */
  public static final int SOURCE_EMISSION_MINIMUM = 0;

  /**
   *  source heat content (hc): fortran notation: F7.3 -> -99.999 min.
   *  RIVM: negative heat content would make no sense
   *  However, -999 is used when heat content is derived from other properties like emission temperature.
   */
  public static final int SOURCE_HEAT_CONTENT_MINIMUM = -999;

  /**
   *  RIVM: negative heat content would make no sense
   */
  public static final int SOURCE_HEAT_CONTENT_SENSIBLE_MINIMUM = 0;

  /**
   *  source heat content (hc): fortran notation: F7.3 -> 999.999 max.
   */
  public static final int SOURCE_HEAT_CONTENT_MAXIMUM = 999;

  /**
   * The number of digits after the decimal point for heat content.
   */
  public static final int SOURCE_HEAT_CONTENT_DIGITS_PRECISION = 3;

  /**
   * The decimal point precision for heat content.
   */
  public static final double SOURCE_HEAT_CONTENT_PRECISION = 0.001;

  /* EMISSION/OUTFLOW HEIGHT CONSTANTS */

  /**
   * source height(h): fortran notation: F6.1 -> -999.9 min.
   * RIVM: negative height would make no sense
   */
  public static final int SOURCE_EMISSION_HEIGHT_MINIMUM = 0;

  /**
   * source height(h): fortran notation: F6.1 -> 9999.9 max.
   */
  public static final int SOURCE_EMISSION_HEIGHT_MAXIMUM = 4999;

  /**
   * Minimum value for scope of ops for outflow height.
   */
  public static final double SCOPE_BUILDING_OUTFLOW_HEIGHT_MINIMUM = 0;

  /**
   * Maximum value for scope of ops for outflow height.
   */
  public static final double SCOPE_BUILDING_OUTFLOW_HEIGHT_MAXIMUM = 20;

  /**
   * The number of digits after the decimal point for height.
   */
  public static final int SOURCE_HEIGHT_DIGITS_PRECISION = 1;

  /**
   * The decimal point precision for height.
   */
  public static final double SOURCE_HEIGHT_PRECISION = 0.1;

  /* DIAMETER CONSTANTS */

  /**
   * source diameter(d): fortran notation: I7 -> -999999 min.
   * negative diameter is used for circular sources.
   * 0 is used for point-sources.
   * positive diameter is used for square sources.
   */
  public static final int SOURCE_DIAMETER_MINIMUM = -999999;

  /**
   * source diameter(d): fortran notation: I7 -> 999999 max.
   * negative diameter is used for circular sources.
   * 0 is used for point-sources.
   * positive diameter is used for square sources.
   */
  public static final int SOURCE_DIAMETER_MAXIMUM = 999999;

  /* SPREAD CONSTANTS */

  /**
   * source spread or source height distribution(s): fortran notation: F6.1 -> -999.9 min.
   */
  public static final int SOURCE_SPREAD_MINIMUM = 0;

  /**
   * source spread or source height distribution(s): fortran notation: F6.1 -> 9999.9 max.
   * TODO: maximum is actually the height of the source. How to validate that?
   */
  public static final int SOURCE_SPREAD_MAXIMUM = 9999;

  /**
   * The number of digits after the decimal point for spread.
   */
  public static final int SOURCE_SPREAD_DIGITS_PRECISION = 1;

  /**
   * The decimal point precision for spread.
   */
  public static final double SOURCE_SPREAD_PRECISION = 0.1;

  /* OUTFLOW DIAMETER CONSTANTS */

  /**
   * inner diameter stack (m): fortran notation: XXX -> -999.9 min.
   * negative is used to signal it isn't specified, heat content should be set in such a case.
   * if positive heat content should not be set.
   */
  public static final double SOURCE_OUTFLOW_DIAMETER_MINIMUM = -999;

  /**
   * Minimum value for scope with NO building influence of ops for outflow diameter.
   */
  public static final double SCOPE_NO_BUILDING_OUTFLOW_DIAMETER_MINIMUM = 0.1;

  /**
   * Maximum value for scope with NO building influence of ops for outflow diameter.
   */
  public static final double SCOPE_NO_BUILDING_OUTFLOW_DIAMETER_MAXIMUM = 30;

  /**
   * Minimum value for scope with building influence of ops for outflow diameter.
   */
  public static final double SCOPE_BUILDING_OUTFLOW_DIAMETER_MINIMUM = 0.01;

  /**
   * Maximum value for scope with building influence of ops for outflow diameter.
   */
  public static final double SCOPE_BUILDING_OUTFLOW_DIAMETER_MAXIMUM = 5.0;

  /**
   * The number of digits after the decimal point for outflow diameter.
   */
  public static final int SOURCE_OUTFLOW_DIAMETER_DIGITS_PRECISION = 1;

  /**
   * The decimal point precision for outflow diameter.
   */
  public static final double SOURCE_OUTFLOW_DIAMETER_PRECISION = 0.1;

  /* OUTFLOW VELOCITY CONSTANTS */

  /**
   * exit velocity (m/s): fortran notation: XXX -> -999.9 min.
   * negative is used to signal it isn't specified, heat content should be set in such a case.
   * negative also means the outflow direction is horizontal.
   * if positive heat content should not be set.
   * positive also means the outflow direction is vertical (the default).
   */
  public static final double SOURCE_OUTFLOW_VELOCITY_MINIMUM = -999;

  /**
   * exit velocity (m/s) value used for user input.
   */
  public static final double SCOPE_NO_BUILDING_OUTFLOW_VELOCITY_MINIMUM = 0;

  /**
   * exit velocity (m/s): fortran notation: XXX -> 999.9 max.
   * negative is used to signal it isn't specified, heat content should be set in such a case.
   * negative also means the outflow direction is horizontal.
   * if positive heat content should not be set.
   * positive also means the outflow direction is vertical (the default).
   */
  public static final double SCOPE_NO_BUILDING_OUTFLOW_VELOCITY_MAXIMUM = 50;

  /**
   * Minimum value for scope of ops for outflow velocity.
   */
  public static final double SCOPE_BUILDING_OUTFLOW_VELOCITY_MINIMUM = 0;

  /**
   * Maximum value for scope of ops for outflow velocity.
   */
  public static final double SCOPE_BUILDING_OUTFLOW_VELOCITY_MAXIMUM = 8.4;

  /**
   * The number of digits after the decimal point for outflow velocity.
   */
  public static final int SOURCE_OUTFLOW_VELOCITY_DIGITS_PRECISION = 1;

  /**
   * The decimal point precision for outflow velocity.
   */
  public static final double SOURCE_OUTFLOW_VELOCITY_PRECISION = 0.1;

  /* EMISSION TEMPERATURE CONSTANTS */

  public static final double AVERAGE_SURROUNDING_TEMPERATURE = 11.85;

  /**
   * temperature effluent gas (C): fortran notation: XXX -> -999.9 min.
   * negative is used to signal it isn't specified, heat content should be set in such a case.
   * if positive heat content should not be set.
   */
  public static final double SOURCE_EMISSION_TEMPERATURE_MINIMUM = -999;

  /**
   * temperature effluent gas (C): fortran notation: XXX -> -999.9 max.
   * negative is used to signal it isn't specified, heat content should be set in such a case.
   * if positive heat content should not be set.
   */
  public static final double SOURCE_EMISSION_TEMPERATURE_MAXIMUM = 2000;

  /**
   * The number of digits after the decimal point for temperature.
   */
  public static final int SOURCE_TEMPERATURE_DIGITS_PRECISION = 2;

  /**
   * The decimal point precision for temperature.
   */
  public static final double SOURCE_TEMPERATURE_PRECISION = 0.01;

  /* DIURNAL VARIATION CONSTANTS */

  /**
   * source diurnal variation(tb): fortran notation: I4 -> -999 min.
   * negative values are for custom files (which we don't use)
   * OPS will return an error if negative is used and no file is present.
   */
  public static final int SOURCE_DIURNAL_VARIATION_MINIMUM = 0;

  /**
   * source diurnal variation(tb): fortran notation: I4 -> 9999 max.
   * from documentation: 3 is max.
   * from RIVM: 33 is max, 31, 32 and 33 are uses for traffic.
   */
  public static final int SOURCE_DIURNAL_VARIATION_MAXIMUM = 33;

  /* MISC CONSTANTS */

  /**
   * source category number(cat): fortran notation: I4 -> -999 min.
   * from documentation: 0 is min.
   * RIVM: 1 is min.
   * cat is only used for administrive purposes, so allow 0.
   */
  public static final int SOURCE_CAT_MINIMUM = 0;

  /**
   * source category number(cat): fortran notation: I4 -> 9999 max.
   */
  public static final int SOURCE_CAT_MAXIMUM = 9999;

  /**
   * source area (area): fortran notation: I4 -> -999 min.
   * from documentation: 1 is min.
   * RIVM: 1 is min.
   */
  public static final int SOURCE_AREA_MINIMUM = 1;

  /**
   * source area (area): fortran notation: I4 -> 9999 max.
   */
  public static final int SOURCE_AREA_MAXIMUM = 9999;

  /**
   * source particle size distribution(psd): fortran notation: I4 -> -999 min.
   * Negative values are used for custom files (which we don't use).
   * OPS won't return an error if negative is used and no file is present.
   * Limit depends on input. For PM10 this value must correspond with the
   * sector ID. However, for other substances this value is irrelevant and
   * 0 is a legal value. Therefore the technical minimum is 0.
   */
  public static final int SOURCE_PARTICLE_SIZE_DISTRIBUTION_MINIMUM = 0;

  /**
   * source particle size distribution(psd): fortran notation: I4 -> 9999 max.
   * For PM10 this value must correspond with the sector ID, which is a
   * 4-digit integer. Therefore the maximum is defined as 9999.
   */
  public static final int SOURCE_PARTICLE_SIZE_DISTRIBUTION_MAXIMUM = 9999;

  /**
   * source component name (comp) fortran notation: A12 -> max size is 12.
   * field is actually ignored by OPS and we don't use it.
   */
  public static final int SOURCE_COMP_MAXIMUM_SIZE = 12;

  /**
   * lowest technical value for year.
   */
  public static final int YEAR_MINIMUM = 0;

  /**
   * highest technical value for year.
   */
  public static final int YEAR_MAXIMUM = 9999;

  /* BUILDING DIMENSIONS CONSTANTS */

  /**
   * The number of digits after the decimal point for height / width / length
   */
  public static final int SOURCE_BUILDING_DIGITS_PRECISION = 1;

  /**
   * The decimal point precision for height / width / length
   */
  public static final double SOURCE_BUILDING_PRECISION = 0.1;

  /**
   * Minimum value for scope of ops for height of building.
   */
  public static final double SCOPE_BUILDING_HEIGHT_MINIMUM = 0;

  /**
   * Maximum value for scope of ops for height of building.
   */
  public static final double SCOPE_BUILDING_HEIGHT_MAXIMUM = 20.0;

  /**
   * Minimum value for scope of ops for width of building.
   */
  public static final double SCOPE_BUILDING_WIDTH_MINIMUM = 1.5;

  /**
   * Maximum value for scope of ops for width of building.
   */
  public static final double SCOPE_BUILDING_WIDTH_MAXIMUM = 87.15;

  /**
   * Minimum value for scope validation of width length ration.
   */
  public static final double SCOPE_BUILDING_WIDTH_LENGTH_RATIO_MINIMUM = 0.15;

  /**
   * Maximum value for scope validation of width length ration.
   */
  public static final double SCOPE_BUILDING_WIDTH_LENGTH_RATIO_MAXIMUM = 1.0;

  /**
   * Minimum value for scope of ops for length of building.
   */
  public static final double SCOPE_BUILDING_LENGTH_MINIMUM = 10.0;

  /**
   * Maximum value for scope of ops for length of building.
   */
  public static final double SCOPE_BUILDING_LENGTH_MAXIMUM = 105.0;

  /**
   * Minimum value for scope of ops for orientation of building.
   */
  public static final double SCOPE_BUILDING_ORIENTATION_MINIMUM = 0;

  /**
   * Maximum value for scope of ops for orientation of building.
   */
  public static final double SCOPE_BUILDING_ORIENTATION_MAXIMUM = 179;

  public static final OPSLimits INSTANCE = new OPSLimits();

  private OPSLimits() {
    // Static constants class.
  }

  @Override
  public boolean isBuildingUpperLimitWarning() {
    return true;
  }

  @Override
  public int buildingDigitsPrecision() {
    return SOURCE_BUILDING_DIGITS_PRECISION;
  }

  @Override
  public double buildingHeightMinimum() {
    return SCOPE_BUILDING_HEIGHT_MINIMUM;
  }

  @Override
  public double buildingHeightMaximum() {
    return SCOPE_BUILDING_HEIGHT_MAXIMUM;
  }

  @Override
  public double buildingHeightDefault() {
    return 0;
  }

  @Override
  public double buildingWidthMinimum() {
    return SCOPE_BUILDING_WIDTH_MINIMUM;
  }

  @Override
  public double buildingWidthMaximum() {
    return SCOPE_BUILDING_WIDTH_MAXIMUM;
  }

  @Override
  public double buildingLengthMinimum() {
    return SCOPE_BUILDING_LENGTH_MINIMUM;
  }

  @Override
  public double buildingLengthMaximum() {
    return SCOPE_BUILDING_LENGTH_MAXIMUM;
  }

  @Override
  public double buildingDiameterMinimum() {
    return 0;
  }

  @Override
  public double buildingDiameterMaximum() {
    return Double.MAX_VALUE;
  }


  @Override
  public int buildingMaximumPerSituation() {
    return Integer.MAX_VALUE;
  }
}
