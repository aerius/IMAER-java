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
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import nl.overheid.aerius.shared.domain.Theme;
import nl.overheid.aerius.shared.domain.calculation.ADMSOptions;
import nl.overheid.aerius.shared.domain.calculation.CalculationRoadOPS;
import nl.overheid.aerius.shared.domain.calculation.CalculationSetOptions;
import nl.overheid.aerius.shared.domain.calculation.CalculationType;
import nl.overheid.aerius.shared.domain.calculation.ConnectSuppliedOptions;
import nl.overheid.aerius.shared.domain.calculation.NCACalculationOptions;
import nl.overheid.aerius.shared.domain.calculation.OPSOptions;
import nl.overheid.aerius.shared.domain.calculation.RoadLocalFractionNO2Option;
import nl.overheid.aerius.shared.domain.calculation.WNBCalculationOptions;
import nl.overheid.aerius.shared.domain.meteo.Meteo;

/**
 * Test class for {@link OptionsMetadataUtil}.
 */
class OptionsMetadataUtilTest {

  private static final int BASIC_OPTIONS = 8;
  private static final int CONNECT_OPTIONS = 2;
  private static final int OPS_OPTIONS = 11;

  @Test
  void testDefaultOptionsWithoutAddingDefaults() {
    final CalculationSetOptions options = new CalculationSetOptions();

    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(Theme.WNB, options, false);

    assertTrue(result.isEmpty(), "With default options and not adding defaults to map, the map should be empty");
  }

  @Test
  void testDefaultOptionsWithAddingDefaults() {
    final CalculationSetOptions options = new CalculationSetOptions();

    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(Theme.WNB, options, true);

    assertEquals(BASIC_OPTIONS, result.size(), "Number of options with default options and when adding defaults to map");
    assertEquals("", result.get("meteo_year"));
    assertEquals("false", result.get("without_source_stacking"));
    assertEquals("DEFAULT", result.get("ops_road"));
    assertEquals("false", result.get("forced_aggregation"));
    assertEquals("false", result.get("use_receptor_height"));
    assertEquals("0", result.get("monitor_srm2_year"));
  }

  @Test
  void testNonDefaultOptions() {
    final CalculationSetOptions cso = new CalculationSetOptions();
    final WNBCalculationOptions options = cso.getWnbCalculationOptions();
    options.setMeteo(new Meteo(2020));
    cso.setStacking(false);
    // RoadOPS only applies with custom_points.
    cso.setCalculationType(CalculationType.CUSTOM_POINTS);
    options.setRoadOPS(CalculationRoadOPS.OPS_ROAD);
    options.setForceAggregation(true);
    options.setUseWnbMaxDistance(true);
    options.setUseReceptorHeights(true);
    options.setDisableSubReceptors(true);
    cso.getRblCalculationOptions().setMonitorSrm2Year(2023);

    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(Theme.WNB, cso, false);

    assertEquals(BASIC_OPTIONS, result.size(), "Number of options when options are not default");
    assertEquals("2020", result.get("meteo_year"));
    assertEquals("true", result.get("without_source_stacking"));
    assertEquals("OPS_ROAD", result.get("ops_road"));
    assertEquals("true", result.get("forced_aggregation"));
    assertEquals("true", result.get("use_receptor_height"));
    assertEquals("true", result.get("disable_sub_receptors"));
    assertEquals("2023", result.get("monitor_srm2_year"));
  }

  @Test
  void testDefaultOptionsConnect() {
    final CalculationSetOptions options = new CalculationSetOptions();
    final ConnectSuppliedOptions connectOptions = new ConnectSuppliedOptions();
    options.setConnectSuppliedOptions(connectOptions);

    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(Theme.WNB, options, true);

    assertEquals(BASIC_OPTIONS + CONNECT_OPTIONS, result.size(), "Number of options when connectOptions is supplied and when adding defaults to map");
    assertEquals("", result.get("calculation_year"));
    assertEquals("", result.get("receptor_set"));
  }

  @Test
  void testNonDefaultOptionsConnect() {
    final CalculationSetOptions options = new CalculationSetOptions();
    final ConnectSuppliedOptions connectOptions = new ConnectSuppliedOptions();
    connectOptions.setCalculationYear(1999);
    connectOptions.setReceptorSetName("SomeRecept or name");
    options.setConnectSuppliedOptions(connectOptions);

    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(Theme.WNB, options, false);

    assertEquals(CONNECT_OPTIONS, result.size(), "Number of options when connectOptions is supplied with non default values");
    assertEquals("1999", result.get("calculation_year"));
    assertEquals("SomeRecept or name", result.get("receptor_set"));
  }

  @Test
  void testDefaultOptionsOps() {
    final CalculationSetOptions options = new CalculationSetOptions();
    final OPSOptions opsOptions = new OPSOptions();
    options.getWnbCalculationOptions().setOpsOptions(opsOptions);

    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(Theme.WNB, options, true);

    assertEquals(BASIC_OPTIONS + OPS_OPTIONS, result.size(), "Number of options when opsOptions is supplied and when adding defaults to map");
    assertEquals("false", result.get("ops_raw_input"));
    assertEquals("", result.get("ops_year"));
    assertEquals("", result.get("ops_comp_code"));
    assertEquals("", result.get("ops_mol_weight"));
    assertEquals("", result.get("ops_phase"));
    assertEquals("", result.get("ops_loss"));
    assertEquals("", result.get("ops_diff_coeff"));
    assertEquals("", result.get("ops_washout"));
    assertEquals("", result.get("ops_conv_rate"));
    assertEquals("", result.get("ops_roughness"));
    assertEquals("", result.get("ops_chemistry"));
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
    options.getWnbCalculationOptions().setOpsOptions(opsOptions);

    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(Theme.WNB, options, false);

    assertEquals(OPS_OPTIONS, result.size(), "Number of options when opsOptions is supplied with non default values");
    assertEquals("true", result.get("ops_raw_input"));
    assertEquals("1881", result.get("ops_year"));
    assertEquals("20", result.get("ops_comp_code"));
    assertEquals("23.12", result.get("ops_mol_weight"));
    assertEquals("4", result.get("ops_phase"));
    assertEquals("9", result.get("ops_loss"));
    assertEquals("noc lue", result.get("ops_diff_coeff"));
    assertEquals("reject", result.get("ops_washout"));
    assertEquals("8 out of 10", result.get("ops_conv_rate"));
    assertEquals("8.19", result.get("ops_roughness"));
    assertEquals("PROGNOSIS", result.get("ops_chemistry"));
  }

  @Test
  void testNcaOptions() {
    final CalculationSetOptions options = new CalculationSetOptions();
    final NCACalculationOptions ncaOptions = options.getNcaCalculationOptions();

    ncaOptions.setAdmsVersion("5.0.0.1");
    ncaOptions.setPermitArea("London");
    ncaOptions.setMeteoSiteLocation("Near London");
    ncaOptions.setMeteoYears(List.of("2022", "2023"));
    ncaOptions.setRoadLocalFractionNO2Option(RoadLocalFractionNO2Option.ONE_CUSTOM_VALUE);
    ncaOptions.setRoadLocalFractionNO2(0.4);
    final ADMSOptions adms = new ADMSOptions();
    ncaOptions.setAdmsOptions(adms);
    adms.setMinMoninObukhovLength(12.3);
    adms.setSurfaceAlbedo(23.4);
    adms.setPriestleyTaylorParameter(34.5);
    adms.setPlumeDepletionNH3(true);
    adms.setPlumeDepletionNOX(true);
    adms.setComplexTerrain(true);
    adms.setMetSiteId(100);
    adms.setMsRoughness(0.8);
    adms.setMsMinMoninObukhovLength(1.1);
    adms.setMsSurfaceAlbedo(1.2);
    adms.setMsPriestleyTaylorParameter(1.3);
    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(Theme.NCA, options, false);

    assertEquals("5.0.0.1", result.get("adms_version"));
    assertEquals("London", result.get("adms_permit_area"));
    assertEquals("Near London", result.get("adms_meteo_site_location"));
    assertEquals("2022,2023", result.get("adms_meteo_years"));
    assertEquals("12.3", result.get("adms_min_monin_obukhov_length"));
    assertEquals("23.4", result.get("adms_surface_albedo"));
    assertEquals("34.5", result.get("adms_priestley_taylor_parameter"));
    assertEquals("true", result.get("adms_plume_depletion_nh3"));
    assertEquals("true", result.get("adms_plume_depletion_nox"));
    assertEquals("true", result.get("adms_complex_terrain"));
    assertEquals("100", result.get("adms_met_site_id"));
    assertEquals("0.8", result.get("adms_met_site_roughness"));
    assertEquals("1.1", result.get("adms_met_site_min_monin_obukhov_length"));
    assertEquals("1.2", result.get("adms_met_site_surface_albedo"));
    assertEquals("1.3", result.get("adms_met_site_priestley_taylor_parameter"));
    assertEquals("ONE_CUSTOM_VALUE", result.get("road_local_fraction_no2_option"));
    assertEquals("0.4", result.get("road_local_fraction_no2_custom_value"));
  }

  @Test
  void testNcaOptionsRoadLocalFraction() {
    final CalculationSetOptions options = new CalculationSetOptions();
    final NCACalculationOptions ncaOptions = options.getNcaCalculationOptions();

    ncaOptions.setRoadLocalFractionNO2Option(RoadLocalFractionNO2Option.LOCATION_BASED);
    ncaOptions.setRoadLocalFractionNO2(0.4);
    final Map<String, String> result1 = OptionsMetadataUtil.optionsToMap(Theme.NCA, options, false);

    assertEquals("LOCATION_BASED", result1.get("road_local_fraction_no2_option"));
    assertFalse(result1.containsKey("road_local_fraction_no2_custom_value"));

    ncaOptions.setRoadLocalFractionNO2Option(RoadLocalFractionNO2Option.ONE_CUSTOM_VALUE);
    final Map<String, String> result2 = OptionsMetadataUtil.optionsToMap(Theme.NCA, options, false);

    assertEquals("ONE_CUSTOM_VALUE", result2.get("road_local_fraction_no2_option"));
    assertEquals("0.4", result2.get("road_local_fraction_no2_custom_value"));

    ncaOptions.setRoadLocalFractionNO2Option(RoadLocalFractionNO2Option.INDIVIDUAL_CUSTOM_VALUES);
    final Map<String, String> result3 = OptionsMetadataUtil.optionsToMap(Theme.NCA, options, false);

    assertEquals("INDIVIDUAL_CUSTOM_VALUES", result3.get("road_local_fraction_no2_option"));
    assertFalse(result3.containsKey("road_local_fraction_no2_custom_value"));
  }


  @Test
  public void NcaOptionsRoundtripTest() {
    final CalculationSetOptions options = new CalculationSetOptions();
    final NCACalculationOptions ncaOptions = options.getNcaCalculationOptions();

    ncaOptions.setPermitArea("London");
    ncaOptions.setMeteoSiteLocation("Near London");
    ncaOptions.setMeteoYears(List.of("2022", "2023"));
    ncaOptions.setRoadLocalFractionNO2Option(RoadLocalFractionNO2Option.ONE_CUSTOM_VALUE);
    ncaOptions.setRoadLocalFractionNO2(0.4);
    final ADMSOptions adms = new ADMSOptions();
    ncaOptions.setAdmsOptions(adms);
    adms.setMinMoninObukhovLength(12.3);
    adms.setSurfaceAlbedo(23.4);
    adms.setPriestleyTaylorParameter(34.5);
    adms.setPlumeDepletionNH3(true);
    adms.setPlumeDepletionNOX(true);
    adms.setComplexTerrain(true);
    adms.setMetSiteId(100);
    adms.setMsRoughness(0.8);
    adms.setMsMinMoninObukhovLength(1.1);
    adms.setMsSurfaceAlbedo(1.2);
    adms.setMsPriestleyTaylorParameter(1.3);

    final Map<String, String> result1 = OptionsMetadataUtil.optionsToMap(Theme.NCA, options, false);
    final CalculationSetOptions roundTripOptions = new CalculationSetOptions();
    OptionsMetadataUtil.addOptionsFromMap(Theme.NCA, result1.entrySet().stream()
            .collect(Collectors.toMap(entry -> OptionsMetadataUtil.Option.valueOf(entry.getKey().toUpperCase(Locale.ROOT)), Map.Entry::getValue)),
        roundTripOptions);
    final Map<String, String> result2 = OptionsMetadataUtil.optionsToMap(Theme.NCA, roundTripOptions, false);

    assertEquals(result1, result2, "Reading Calculation options to, from and to map should return the same exact map.");
  }
}
