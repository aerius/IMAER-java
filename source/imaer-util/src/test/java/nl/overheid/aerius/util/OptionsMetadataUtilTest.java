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

import nl.overheid.aerius.shared.domain.calculation.CalculationRoadOPS;
import nl.overheid.aerius.shared.domain.calculation.CalculationSetOptions;
import nl.overheid.aerius.shared.domain.calculation.CalculationType;
import nl.overheid.aerius.shared.domain.calculation.ConnectSuppliedOptions;
import nl.overheid.aerius.shared.domain.calculation.OPSOptions;
import nl.overheid.aerius.shared.domain.meteo.SingleYearMeteo;

/**
 *
 */
class OptionsMetadataUtilTest {

  private static final int BASIC_OPTIONS = 7;
  private static final int CONNECT_OPTIONS = 2;
  private static final int OPS_OPTIONS = 10;

  @Test
  void testDefaultOptionsWithoutAddingDefaults() {
    final CalculationSetOptions options = new CalculationSetOptions();

    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(options, false);

    assertTrue(result.isEmpty(), "With default options and not adding defaults to map, the map should be empty");
  }

  @Test
  void testDefaultOptionsWithAddingDefaults() {
    final CalculationSetOptions options = new CalculationSetOptions();

    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(options, true);

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
    final CalculationSetOptions options = new CalculationSetOptions();
    options.setMeteo(new SingleYearMeteo(2020));
    options.setStacking(false);
    // RoadOPS only applies with custom_points.
    options.setCalculationType(CalculationType.CUSTOM_POINTS);
    options.setRoadOPS(CalculationRoadOPS.OPS_ROAD);
    options.setForceAggregation(true);
    options.setWnbMaxDistance(true);
    options.setUseReceptorHeights(true);
    options.setMonitorSrm2Year(2023);

    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(options, false);

    assertEquals(BASIC_OPTIONS, result.size(), "Number of options when options are not default");
    assertEquals("2020", result.get("meteo_year"));
    assertEquals("true", result.get("without_source_stacking"));
    assertEquals("OPS_ROAD", result.get("ops_road"));
    assertEquals("true", result.get("forced_aggregation"));
    assertEquals("true", result.get("use_receptor_height"));
    assertEquals("2023", result.get("monitor_srm2_year"));
  }

  @Test
  void testDefaultOptionsConnect() {
    final CalculationSetOptions options = new CalculationSetOptions();
    final ConnectSuppliedOptions connectOptions = new ConnectSuppliedOptions();
    options.setConnectSuppliedOptions(connectOptions);

    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(options, true);

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

    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(options, false);

    assertEquals(CONNECT_OPTIONS, result.size(), "Number of options when connectOptions is supplied with non default values");
    assertEquals("1999", result.get("calculation_year"));
    assertEquals("SomeRecept or name", result.get("receptor_set"));
  }

  @Test
  void testDefaultOptionsOps() {
    final CalculationSetOptions options = new CalculationSetOptions();
    final OPSOptions opsOptions = new OPSOptions();
    options.setOpsOptions(opsOptions);

    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(options, true);

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
    options.setOpsOptions(opsOptions);

    final Map<String, String> result = OptionsMetadataUtil.optionsToMap(options, false);

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
  }
}
