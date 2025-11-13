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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import nl.overheid.aerius.shared.domain.Theme;
import nl.overheid.aerius.shared.domain.calculation.ADMSOptions;
import nl.overheid.aerius.shared.domain.calculation.CalculationSetOptions;
import nl.overheid.aerius.shared.domain.calculation.MetDatasetType;
import nl.overheid.aerius.shared.domain.calculation.MetSurfaceCharacteristics;
import nl.overheid.aerius.shared.domain.calculation.NCACalculationOptions;
import nl.overheid.aerius.shared.domain.calculation.RoadLocalFractionNO2Option;

/**
 * Test class for {@link NcaOptionsMetadataUtil}.
 */
class NcaOptionsMetadataUtilTest {


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
    options.getCalculatedSnapshotValues().setDevelopmentPressureClassification("SOME_CLASS");
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
    assertEquals("SOME_CLASS", result.get("development_pressure_classification"), "development_pressure_classification should be set");
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
    options.getCalculatedSnapshotValues().setDevelopmentPressureClassification("SOME_CLASS");

    final Map<String, String> result1 = OptionsMetadataUtil.optionsToMap(Theme.NCA, options, false);
    final CalculationSetOptions roundTripOptions = new CalculationSetOptions();
    OptionsMetadataUtil.addOptionsFromMap(Theme.NCA, result1.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
        roundTripOptions);
    final Map<String, String> result2 = OptionsMetadataUtil.optionsToMap(Theme.NCA, roundTripOptions, false);

    assertEquals(result1, result2, "Reading Calculation options to, from and to map should return the same exact map.");
  }
}
