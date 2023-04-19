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
package nl.overheid.aerius.shared.domain.v2.characteristics.adms;

import nl.overheid.aerius.shared.domain.v2.building.BuildingLimits;

/**
 * Class of ADMS minimum, maximum and default values.
 */
public final class ADMSLimits implements BuildingLimits {

  private static final long serialVersionUID = 1L;

  public static final double SOURCE_HEIGHT_MINIMUM = 0.0005;
  public static final int SOURCE_HEIGHT_MAXIMUM = 15_000;
  public static final double SOURCE_HEIGHT_DEFAULT = 0.5;
  public static final double SOURCE_WIDTH_MINIMUM = 0.001;
  public static final int SOURCE_WIDTH_INITIAL = 0;
  public static final int SOURCE_WIDTH_MAXIMUM = 1_000;

  public static final double SOURCE_DIAMETER_MINIMUM = 0.001;
  public static final int SOURCE_DIAMETER_MAXIMUM = 100;

  public static final int SOURCE_VOLUMETRIC_FLOW_RATE_MINIMUM = 0;
  public static final int SOURCE_VOLUMETRIC_FLOW_RATE_MAXIMUM = 1_000_000_000; // 10^9
  public static final double SOURCE_VOLUMETRIC_FLOW_RATE_DEFAULT = 11.781;

  public static final int SOURCE_VERTICAL_VELOCITY_MINIMUM = 0;
  public static final int SOURCE_VERTICAL_VELOCITY_MAXIMUM = 1_000;
  public static final double SOURCE_VERTICAL_VELOCITY_DEFAULT = 15;

  public static final int SOURCE_TEMPERATURE_MINIMUM = -100;
  public static final int SOURCE_TEMPERATURE_MAXIMUM = 5000;
  public static final double SOURCE_TEMPERATURE_PRECISION = 0.1;
  public static final int SOURCE_TEMPERATURE_DEFAULT = 15;

  public static final long SOURCE_MOL_WEIGHT_MINIMUM = 1;
  public static final long SOURCE_MOL_WEIGHT_MAXIMUM = 300;
  public static final double SOURCE_MOL_WEIGHT_DEFAULT = 28.966;

  public static final double SOURCE_DENSITY_MINIMUM = 0.01;
  public static final int SOURCE_DENSITY_MAXIMUM = 2;
  public static final double SOURCE_DENSITY_DEFAULT = 1.225;

  public static final int SOURCE_SPECIFIC_HEAT_CAPACITY_MINIMUM = 1;
  public static final int SOURCE_SPECIFIC_HEAT_CAPACITY_MAXIMUM = 100_000;
  public static final double SOURCE_SPECIFIC_HEAT_CAPACITY_DEFAULT = 1012;

  public static final double SOURCE_PERCENT_NOX_AS_NO2_DEFAULT = 0d;

  public static final int SOURCE_MOMENTUM_FLUX_MINIMUM = 0;
  public static final int SOURCE_MOMENTUM_FLUX_MAXIMUM = 1_000_000;
  public static final double SOURCE_MOMENTUM_FLUX_DEFAULT = 1.0;

  public static final int SOURCE_BUOYANCY_FLUX_MINIMUM = 0;
  public static final int SOURCE_BUOYANCY_FLUX_MAXIMUM = 10_000;
  public static final double SOURCE_BUOYANCY_FLUX_DEFAULT = 1.0;

  public static final long SOURCE_MASS_FLUX_MINIMUM = 0;
  public static final long SOURCE_MASS_FLUX_MAXIMUM = 100_000;
  public static final double SOURCE_MASS_FLUX_DEFAULT = 1;

  public static final double SOURCE_L1_MINIMUM = 0.001;
  public static final long SOURCE_L1_MAXIMUM = 1_000;
  public static final long SOURCE_L1_DEFAULT = 1;

  private static final int BUILDING_DIGITS_PRECISION = 3;

  public static final double BUILDING_HEIGHT_MINIMUM = 0.001;
  public static final double BUILDING_HEIGHT_MAXIMUM = 500;
  public static final double BUILDING_HEIGHT_DEFAULT = 10;

  public static final double BUILDING_WIDTH_MINIMUM = 0.001;
  public static final double BUILDING_WIDTH_MAXIMUM = 1_000;

  public static final double BUILDING_LENGTH_MINIMUM = 0.001;
  public static final double BUILDING_LENGTH_MAXIMUM = 1_000;

  public static final int ROAD_GRADIENT_MIN = -50;
  public static final int ROAD_GRADIENT_MAX = 50;
  public static final int ROAD_ELEVATION_MIN = 0;
  public static final int ROAD_ELEVATION_MAX = 15_000;
  public static final int ROAD_WIDTH_MIN = 2;
  public static final int ROAD_WIDTH_MAX = 100;
  public static final int ROAD_COVERAGE_MIN = 0;
  public static final int ROAD_COVERAGE_MAX = 100;

  public static final double ELEVATION_ANGLE_DEFAULT = 0.0;
  public static final double ELEVATION_ANGLE_MIN = 0.0;
  public static final double ELEVATION_ANGLE_MAX = 90.0;
  public static final double HORIZONTAL_ANGLE_DEFAULT = 0.0;
  public static final double HORIZONTAL_ANGLE_MIN = 0.0;
  public static final double HORIZONTAL_ANGLE_MAX = 360.0;

  public static final double ROUGHNESS_DEFAULT = 0.5D;

  public static final double MIN_MONIN_OBUKHOV_LENGTH_MIN = 1D;
  public static final double MIN_MONIN_OBUKHOV_LENGTH_MAX = 200D;
  public static final double MIN_MONIN_OBUKHOV_LENGTH_DEFAULT = 30D;
  public static final double SURFACE_ALBEDO_MIN = 0D;
  public static final double SURFACE_ALBEDO_MAX = 1D;
  public static final double SURFACE_ALBEDO_DEFAULT = 0.23D;
  public static final double PRIESTLEY_TAYLOR_PARAMETER_MIN = 0D;
  public static final double PRIESTLEY_TAYLOR_PARAMETER_MAX = 3D;
  public static final double PRIESTLEY_TAYLOR_PARAMETER_DEFAULT = 1D;

  public static final boolean ADMS_PLUME_DEPLETION_NH3_DEFAULT = false;
  public static final boolean ADMS_PLUME_DEPLETION_NOX_DEFAULT = false;
  public static final boolean SPATIALLY_VARYING_ROUGHNESS_DEFAULT = true;
  public static final boolean ADMS_COMPLEX_TERRAIN_DEFAULT = false;

  public static final ADMSLimits INSTANCE = new ADMSLimits();

  private ADMSLimits() {
    // Static constants class.
  }

  @Override
  public boolean isCircularBuildingSupported() {
    return true;
  }

  @Override
  public int buildingDigitsPrecision() {
    return BUILDING_DIGITS_PRECISION;
  }

  @Override
  public double buildingHeightMinimum() {
    return BUILDING_HEIGHT_MINIMUM;
  }

  @Override
  public double buildingHeightMaximum() {
    return BUILDING_HEIGHT_MAXIMUM;
  }

  @Override
  public double buildingHeightDefault() {
    return BUILDING_HEIGHT_DEFAULT;
  }

  @Override
  public double buildingWidthMinimum() {
    return BUILDING_WIDTH_MINIMUM;
  }

  @Override
  public double buildingWidthMaximum() {
    return BUILDING_WIDTH_MAXIMUM;
  }

  @Override
  public double buildingLengthMinimum() {
    return BUILDING_LENGTH_MINIMUM;
  }

  @Override
  public double buildingLengthMaximum() {
    return BUILDING_LENGTH_MAXIMUM;
  }
}
