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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import nl.overheid.aerius.shared.domain.Theme;
import nl.overheid.aerius.shared.domain.calculation.ADMSOptions;
import nl.overheid.aerius.shared.domain.calculation.CalculationMethod;
import nl.overheid.aerius.shared.domain.calculation.CalculationRoadOPS;
import nl.overheid.aerius.shared.domain.calculation.CalculationSetOptions;
import nl.overheid.aerius.shared.domain.calculation.ConnectSuppliedOptions;
import nl.overheid.aerius.shared.domain.calculation.MetDatasetType;
import nl.overheid.aerius.shared.domain.calculation.MetSurfaceCharacteristics;
import nl.overheid.aerius.shared.domain.calculation.NCACalculationOptions;
import nl.overheid.aerius.shared.domain.calculation.OPSOptions;
import nl.overheid.aerius.shared.domain.calculation.OwN2000CalculationOptions;
import nl.overheid.aerius.shared.domain.calculation.RoadLocalFractionNO2Option;
import nl.overheid.aerius.shared.domain.calculation.SubReceptorsMode;
import nl.overheid.aerius.shared.domain.meteo.Meteo;

/**
 * Test class for {@link OptionsMetadataUtil}.
 */
class OptionsMetadataUtilTest {

  private static final int BASIC_OPTIONS = 10;
  private static final int CONNECT_OPTIONS = 2;
  private static final int OPS_OPTIONS = 12;

  @Test
  void testDefaultOptionsWithoutAddingDefaults() {
    final CalculationSetOptions options = new CalculationSetOptions();

    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(Theme.OWN2000, options, false);

    assertTrue(result.isEmpty(), "With default options and not adding defaults to map, the map should be empty");
  }

  @Test
  void testDefaultOptionsWithAddingDefaults() {
    final CalculationSetOptions options = new CalculationSetOptions();

    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(Theme.OWN2000, options, true);

    assertEquals(BASIC_OPTIONS, result.size(), "Number of options with default options and when adding defaults to map");
    assertEquals("", result.get("meteo_year"), "meteo_year should be empty");
    assertEquals("false", result.get("without_source_stacking"), "without_source_stacking should be set");
    assertEquals("DEFAULT", result.get("ops_road"), "ops_road should be set");
    assertEquals("false", result.get("forced_aggregation"), "forced_aggregation should be set");
    assertEquals("false", result.get("use_receptor_height"), "use_receptor_height should be set");
    assertEquals("0", result.get("monitor_srm2_year"), "monitor_srm2 should be set");
  }

  @Test
  void testNonDefaultOptions() {
    final CalculationSetOptions cso = new CalculationSetOptions();
    final OwN2000CalculationOptions options = cso.getOwN2000CalculationOptions();
    options.setMeteo(new Meteo(2020));
    cso.setStacking(false);
    // RoadOPS only applies with custom_points.
    cso.setCalculationMethod(CalculationMethod.CUSTOM_POINTS);
    options.setRoadOPS(CalculationRoadOPS.OPS_ROAD);
    options.setForceAggregation(true);
    options.setUseMaxDistance(true);
    options.setUseReceptorHeights(true);
    options.setSubReceptorsMode(SubReceptorsMode.ENABLED);
    options.setSubReceptorZoomLevel(1);
    options.setSplitSubReceptorWork(true);
    options.setSplitSubReceptorWorkDistance(1000);
    cso.getRblCalculationOptions().setMonitorSrm2Year(2023);

    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(Theme.OWN2000, cso, false);
    // is BASIC_OPTIONS + 1 because SplitSubReceptorWorkDistance is by default not set when SplitSubReceptorWork is false
    assertEquals(BASIC_OPTIONS + 1, result.size(), "Number of options when options are not default");
    assertEquals("2020", result.get("meteo_year"), "Invalid meteo year option");
    assertEquals("true", result.get("without_source_stacking"), "Invalid without_source_stacking option");
    assertEquals("OPS_ROAD", result.get("ops_road"), "Invalid ops_road option");
    assertEquals("true", result.get("forced_aggregation"), "Invalid forced_aggregation option");
    assertEquals("true", result.get("use_receptor_height"), "Invalid use_receptor_height option");
    assertEquals("ENABLED", result.get("sub_receptors_mode"), "Invalid sub_receptors_mode option");
    assertEquals("1", result.get("sub_receptor_zoom_level"), "Invalid sub_receptor_zoom_level option");
    assertEquals("2023", result.get("monitor_srm2_year"), "Invalid monitor_srm2 option");
    assertEquals("true", result.get("split_sub_receptor_work"), "Invalid split_sub_receptor_work option");
    assertEquals("1000", result.get("split_sub_receptor_work_distance"), "Invalid split_sub_receptor_work_distance option");
  }

  @Test
  void testDefaultOptionsConnect() {
    final CalculationSetOptions options = new CalculationSetOptions();
    final ConnectSuppliedOptions connectOptions = new ConnectSuppliedOptions();
    options.setConnectSuppliedOptions(connectOptions);

    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(Theme.OWN2000, options, true);

    assertEquals(BASIC_OPTIONS + CONNECT_OPTIONS, result.size(), "Number of options when connectOptions is supplied and when adding defaults to map");
    assertEquals("", result.get("calculation_year"), "calculation_year should be empty");
    assertEquals("", result.get("receptor_set"), "receptor_set should be empty");
  }

  @Test
  void testNonDefaultOptionsConnect() {
    final CalculationSetOptions options = new CalculationSetOptions();
    final ConnectSuppliedOptions connectOptions = new ConnectSuppliedOptions();
    connectOptions.setCalculationYear(1999);
    connectOptions.setReceptorSetName("SomeRecept or name");
    options.setConnectSuppliedOptions(connectOptions);

    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(Theme.OWN2000, options, false);

    assertEquals(CONNECT_OPTIONS, result.size(), "Number of options when connectOptions is supplied with non default values");
    assertEquals("1999", result.get("calculation_year"), "Invalide calculation_year option");
    assertEquals("SomeRecept or name", result.get("receptor_set"), "Invalide receptor_set option");
  }

  @Test
  void testDefaultOptionsOps() {
    final CalculationSetOptions options = new CalculationSetOptions();
    final OPSOptions opsOptions = new OPSOptions();
    options.getOwN2000CalculationOptions().setOpsOptions(opsOptions);

    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(Theme.OWN2000, options, true);

    assertEquals(BASIC_OPTIONS + OPS_OPTIONS, result.size(), "Number of options when opsOptions is supplied and when adding defaults to map");
    assertEquals("false", result.get("ops_raw_input"), "ops_raw_input should be false");
    assertEquals("", result.get("ops_year"), "ops_year should be empty");
    assertEquals("", result.get("ops_comp_code"), "ops_comp_code should be empty");
    assertEquals("", result.get("ops_mol_weight"), "ops_mol_weight should be empty");
    assertEquals("", result.get("ops_phase"), "ops_phase should be empty");
    assertEquals("", result.get("ops_loss"), "ops_loss should be empty");
    assertEquals("", result.get("ops_diff_coeff"), "ops_diff_coeff should be empty");
    assertEquals("", result.get("ops_washout"), "ops_washout should be empty");
    assertEquals("", result.get("ops_conv_rate"), "ops_conv_rate should be empty");
    assertEquals("", result.get("ops_roughness"), "ops_roughness should be empty");
    assertEquals("", result.get("ops_chemistry"), "ops_chemistry should be empty");
  }

  @Test
  void testNonDefaultOptionsOps() {
    final CalculationSetOptions options = new CalculationSetOptions();
    final OPSOptions opsOptions = new OPSOptions();
    opsOptions.setRawInput(true);
    opsOptions.setYear(1881);
    opsOptions.setCompCode(20);
    opsOptions.setMolWeight(23.12);
    opsOptions.setPhase(4);
    opsOptions.setLoss(9);
    opsOptions.setDiffCoeff("noc lue");
    opsOptions.setWashout("reject");
    opsOptions.setConvRate("8 out of 10");
    opsOptions.setRoughness(8.19);
    opsOptions.setChemistry(OPSOptions.Chemistry.PROGNOSIS);
    opsOptions.setRoads("3030");
    options.getOwN2000CalculationOptions().setOpsOptions(opsOptions);

    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(Theme.OWN2000, options, false);

    assertEquals(OPS_OPTIONS, result.size(), "Number of options when opsOptions is supplied with non default values");
    assertEquals("true", result.get("ops_raw_input"), "ops_raw_input should be true");
    assertEquals("1881", result.get("ops_year"), "ops_year should be set");
    assertEquals("20", result.get("ops_comp_code"), "ops_comp_code should be set");
    assertEquals("23.12", result.get("ops_mol_weight"), "ops_mol_weight should be set");
    assertEquals("4", result.get("ops_phase"), "ops_phase should be set");
    assertEquals("9", result.get("ops_loss"), "ops_loss should be set");
    assertEquals("noc lue", result.get("ops_diff_coeff"), "ops_diff_coeff should be set");
    assertEquals("reject", result.get("ops_washout"), "ops_washout should be set");
    assertEquals("8 out of 10", result.get("ops_conv_rate"), "ops_conv_rate should be set");
    assertEquals("8.19", result.get("ops_roughness"), "ops_roughness should be set");
    assertEquals("PROGNOSIS", result.get("ops_chemistry"), "ops_chemistry should be set");
    assertEquals("3030", result.get("ops_roads"), "ops_roads should be set");
  }

  @Test
  void testNcaOptions() {
    final CalculationSetOptions options = new CalculationSetOptions();
    final NCACalculationOptions ncaOptions = options.getNcaCalculationOptions();

    ncaOptions.setAdmsVersion("5.0.0.1");
    ncaOptions.setProjectCategory("SHALLOW_WATER_FISHING");
    ncaOptions.setPermitArea("London");
    ncaOptions.setDevelopmentPressureSourceIds(List.of("source_id_1", "source_id_2"));
    ncaOptions.setRoadLocalFractionNO2ReceptorsOption(RoadLocalFractionNO2Option.ONE_CUSTOM_VALUE);
    ncaOptions.setRoadLocalFractionNO2PointsOption(RoadLocalFractionNO2Option.ONE_CUSTOM_VALUE);
    ncaOptions.setRoadLocalFractionNO2(0.4);
    final ADMSOptions adms = new ADMSOptions();
    ncaOptions.setAdmsOptions(adms);
    adms.setMinMoninObukhovLength(12.3);
    adms.setSurfaceAlbedo(23.4);
    adms.setPriestleyTaylorParameter(34.5);
    adms.setPlumeDepletionNH3(true);
    adms.setPlumeDepletionNOX(true);
    adms.setSpatiallyVaryingRoughness(true);
    adms.setComplexTerrain(true);
    adms.setMetSiteId(100);
    adms.setMetDatasetType(MetDatasetType.OBS_RAW_GT_90PCT);
    adms.setMetYears(List.of("2022", "2023"));

    final MetSurfaceCharacteristics msc = MetSurfaceCharacteristics.builder()
        .roughness(0.8)
        .minMoninObukhovLength(1.1)
        .surfaceAlbedo(1.2)
        .priestleyTaylorParameter(1.3)
        .build();
    adms.setMetSiteCharacteristics(Map.of("2022", msc, "2023", msc));
    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(Theme.NCA, options, false);

    assertEquals("5.0.0.1", result.get("adms_version"), "adms_version should be set");
    assertEquals("SHALLOW_WATER_FISHING", result.get("project_category"), "project_category should be set");
    assertEquals("London", result.get("permit_area"), "permit area should be set");
    assertEquals("source_id_1,source_id_2", result.get("development_pressure_source_ids"), "Development pressure source IDs should be set");
    assertEquals("12.3", result.get("adms_min_monin_obukhov_length"), "adms_min_monin_obukhov_length should be set");
    assertEquals("23.4", result.get("adms_surface_albedo"), "adms_surface_albedo should be set");
    assertEquals("34.5", result.get("adms_priestley_taylor_parameter"), "adms_priestley_taylor_parameter should be set");
    assertEquals("true", result.get("adms_plume_depletion_nh3"), "adms_plume_depletion_nh3 should be set");
    assertEquals("true", result.get("adms_plume_depletion_nox"), "adms_plume_depletion_nox should be set");
    assertEquals("true", result.get("adms_spatially_varying_roughness"), "adms_spatially_varying_roughness should be set");
    assertEquals("true", result.get("adms_complex_terrain"), "adms_complex_terrain should be set");
    assertEquals("100", result.get("adms_met_site_id"), "adms_met_site_id should be set");
    assertEquals("OBS_RAW_GT_90PCT", result.get("adms_met_dataset_type"), "adms_met_dataset_type should be set");
    assertEquals("2022,2023", result.get("adms_met_years"), "adms_years should be set");
    assertEquals("0.8", result.get("2022-adms_met_site_roughness"), "adms_met_site_roughness should be set");
    assertEquals("1.1", result.get("2022-adms_met_site_min_monin_obukhov_length"), "adms_met_site_min_monin_obukhov_length should be set");
    assertEquals("1.2", result.get("2022-adms_met_site_surface_albedo"), "adms_met_site_surface_albedo should be set");
    assertEquals("1.3", result.get("2022-adms_met_site_priestley_taylor_parameter"), "adms_met_site_priestley_taylor_parameter should be set");
    assertEquals("ONE_CUSTOM_VALUE", result.get("road_local_fraction_no2_receptors_option"),
        "road_local_fraction_no2_receptors_option should be set");
    assertEquals("ONE_CUSTOM_VALUE", result.get("road_local_fraction_no2_points_option"), "road_local_fraction_no2_points_option should be set");
    assertEquals("0.4", result.get("road_local_fraction_no2_custom_value"), "road_local_fraction_no2 should be set");
  }

  @Test
  void testNcaOptionsRoadLocalFraction() {
    final CalculationSetOptions options = new CalculationSetOptions();
    final NCACalculationOptions ncaOptions = options.getNcaCalculationOptions();

    ncaOptions.setRoadLocalFractionNO2ReceptorsOption(RoadLocalFractionNO2Option.LOCATION_BASED);
    ncaOptions.setRoadLocalFractionNO2PointsOption(RoadLocalFractionNO2Option.LOCATION_BASED);
    ncaOptions.setRoadLocalFractionNO2(0.4);
    final Map<String, String> result1 = OptionsMetadataUtil.optionsToMap(Theme.NCA, options, false);

    assertEquals("LOCATION_BASED", result1.get("road_local_fraction_no2_receptors_option"), "road_local_fraction_no2_receptors_option should be set");
    assertFalse(result1.containsKey("road_local_fraction_no2_custom_value"), "road_local_fraction_no2_custom_value should be set");

    ncaOptions.setRoadLocalFractionNO2ReceptorsOption(RoadLocalFractionNO2Option.ONE_CUSTOM_VALUE);
    ncaOptions.setRoadLocalFractionNO2PointsOption(RoadLocalFractionNO2Option.ONE_CUSTOM_VALUE);
    final Map<String, String> result2 = OptionsMetadataUtil.optionsToMap(Theme.NCA, options, false);

    assertEquals("ONE_CUSTOM_VALUE", result2.get("road_local_fraction_no2_receptors_option"),
        "road_local_fraction_no2_receptors_option should be set");
    assertEquals("0.4", result2.get("road_local_fraction_no2_custom_value"), "road_local_fraction_no2_custom_value should be set");

    ncaOptions.setRoadLocalFractionNO2ReceptorsOption(RoadLocalFractionNO2Option.INDIVIDUAL_CUSTOM_VALUES);
    ncaOptions.setRoadLocalFractionNO2PointsOption(RoadLocalFractionNO2Option.INDIVIDUAL_CUSTOM_VALUES);
    final Map<String, String> result3 = OptionsMetadataUtil.optionsToMap(Theme.NCA, options, false);

    assertEquals("INDIVIDUAL_CUSTOM_VALUES", result3.get("road_local_fraction_no2_receptors_option"), "road_local_fraction_no2 should be set");
    assertFalse(result3.containsKey("road_local_fraction_no2_custom_value"), "road_local_fraction_no2_custom_value not be present");
  }

  @Test
  void testNcaOptionsRoundtrip() {
    final CalculationSetOptions options = new CalculationSetOptions();
    final NCACalculationOptions ncaOptions = options.getNcaCalculationOptions();

    ncaOptions.setPermitArea("London");
    ncaOptions.setProjectCategory("HIGHWAY_GO_KARTING");
    ncaOptions.setDevelopmentPressureSourceIds(List.of("farm_4", "manure_storage_9"));
    ncaOptions.setRoadLocalFractionNO2ReceptorsOption(RoadLocalFractionNO2Option.ONE_CUSTOM_VALUE);
    ncaOptions.setRoadLocalFractionNO2PointsOption(RoadLocalFractionNO2Option.ONE_CUSTOM_VALUE);
    ncaOptions.setRoadLocalFractionNO2(0.4);
    final ADMSOptions adms = new ADMSOptions();
    ncaOptions.setAdmsOptions(adms);
    adms.setMinMoninObukhovLength(12.3);
    adms.setSurfaceAlbedo(23.4);
    adms.setPriestleyTaylorParameter(34.5);
    adms.setPlumeDepletionNH3(true);
    adms.setPlumeDepletionNOX(true);
    adms.setSpatiallyVaryingRoughness(true);
    adms.setComplexTerrain(true);
    adms.setMetSiteId(100);
    adms.setMetDatasetType(MetDatasetType.OBS_RAW_GT_90PCT);
    adms.setMetYears(List.of("2022", "2023"));
    final MetSurfaceCharacteristics msc = MetSurfaceCharacteristics.builder()
        .roughness(0.8)
        .minMoninObukhovLength(1.1)
        .surfaceAlbedo(1.2)
        .priestleyTaylorParameter(1.3)
        .build();
    adms.setMetSiteCharacteristics(Map.of("2022", msc, "2023", msc));

    final Map<String, String> result1 = OptionsMetadataUtil.optionsToMap(Theme.NCA, options, false);
    final CalculationSetOptions roundTripOptions = new CalculationSetOptions();
    OptionsMetadataUtil.addOptionsFromMap(Theme.NCA, result1.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
        roundTripOptions);
    final Map<String, String> result2 = OptionsMetadataUtil.optionsToMap(Theme.NCA, roundTripOptions, false);

    assertEquals(result1, result2, "Reading Calculation options to, from and to map should return the same exact map.");
  }
}
