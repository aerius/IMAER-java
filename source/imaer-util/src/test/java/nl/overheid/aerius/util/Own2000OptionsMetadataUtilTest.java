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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import nl.overheid.aerius.shared.domain.Theme;
import nl.overheid.aerius.shared.domain.calculation.CalculationMethod;
import nl.overheid.aerius.shared.domain.calculation.CalculationRoadOPS;
import nl.overheid.aerius.shared.domain.calculation.CalculationSetOptions;
import nl.overheid.aerius.shared.domain.calculation.ConnectSuppliedOptions;
import nl.overheid.aerius.shared.domain.calculation.OPSOptions;
import nl.overheid.aerius.shared.domain.calculation.OwN2000CalculationOptions;
import nl.overheid.aerius.shared.domain.calculation.PermitLowerBoundType;
import nl.overheid.aerius.shared.domain.calculation.SubReceptorsMode;
import nl.overheid.aerius.shared.domain.meteo.Meteo;

/**
 * Test class for {@link Own2000OptionsMetadataUtil}.
 */
class Own2000OptionsMetadataUtilTest {

  private static final int BASIC_OPTIONS = 11;
  private static final int CONNECT_OPTIONS = 2;
  private static final int OPS_OPTIONS = 13;

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
    options.setPermitLowerBoundType(PermitLowerBoundType.POLICY);
    options.setPermitLowerBoundValue(123);
    options.setUseMaxDistance(true);
    options.setUseReceptorHeights(true);
    options.setSubReceptorsMode(SubReceptorsMode.ENABLED);
    options.setSubReceptorZoomLevel(1);
    options.setSplitSubReceptorWork(true);
    options.setSplitSubReceptorWorkDistance(1000);
    options.setRepositionSubReceptors(true);
    cso.getRblCalculationOptions().setMonitorSrm2Year(2023);

    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(Theme.OWN2000, cso, false);
    // is BASIC_OPTIONS + 1 because SplitSubReceptorWorkDistance is by default not set when SplitSubReceptorWork is false
    // TODO AER-4028: PermitLowerBound options temporarily not written to GML
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
    assertEquals("true", result.get("reposition_sub_receptors"), "Invalid reposition_sub_receptor option");
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
    opsOptions.setRoadsOpt("0 1");
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
    assertEquals("0 1", result.get("ops_roads_opt"), "ops_roads_opt should be set");
  }
}
