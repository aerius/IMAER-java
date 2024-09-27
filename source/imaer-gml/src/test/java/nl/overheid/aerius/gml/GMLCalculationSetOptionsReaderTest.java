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
package nl.overheid.aerius.gml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import nl.overheid.aerius.gml.base.FeatureCollection;
import nl.overheid.aerius.gml.base.IsCalculationMetaData;
import nl.overheid.aerius.gml.base.IsCalculationOption;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.MetaData;
import nl.overheid.aerius.shared.domain.Theme;
import nl.overheid.aerius.shared.domain.calculation.ADMSOptions;
import nl.overheid.aerius.shared.domain.calculation.CalculationJobType;
import nl.overheid.aerius.shared.domain.calculation.CalculationMethod;
import nl.overheid.aerius.shared.domain.calculation.CalculationSetOptions;
import nl.overheid.aerius.shared.domain.calculation.MetDatasetType;
import nl.overheid.aerius.shared.domain.calculation.MetSurfaceCharacteristics;
import nl.overheid.aerius.shared.domain.calculation.NCACalculationOptions;
import nl.overheid.aerius.shared.domain.v2.characteristics.adms.ADMSLimits;

/**
 * Test class for {@link GMLCalculationSetOptionsReader}.
 */
class GMLCalculationSetOptionsReaderTest {

  private static final String CALCULATION_METHOD_FORMAL = "FORMAL_ASSESSMENT";
  private static final String CALCULATION_JOB_TYPE = "MAX_TEMPORARY_EFFECT";

  @Test
  void testGetCalculationMethod() {
    final FeatureCollection featureCollection = mock(FeatureCollection.class);
    when(featureCollection.getMetaData()).thenReturn(null);

    final GMLCalculationSetOptionsReader reader = new GMLCalculationSetOptionsReader(featureCollection);

    final CalculationSetOptions options = reader.readCalculationSetOptions(Theme.NCA);
    assertNull(options, "Null metadata field should be read as null options object");
  }

  @Test
  void testReadCalculationSetOptionsNullCalculationMetadata() {
    final FeatureCollection featureCollection = mock(FeatureCollection.class);
    final MetaData metaData = mock(MetaData.class);
    when(featureCollection.getMetaData()).thenReturn(metaData);
    when(metaData.getCalculation()).thenReturn(null);

    final GMLCalculationSetOptionsReader reader = new GMLCalculationSetOptionsReader(featureCollection);

    final CalculationSetOptions options = reader.readCalculationSetOptions(Theme.NCA);
    assertNull(options, "Null calculation metadata field should be read as null options object");
  }

  @Test
  void testReadCalculationSetOptionsNullCalculationOptions() {
    final FeatureCollection featureCollection = mock(FeatureCollection.class);
    final MetaData metaData = mock(MetaData.class);
    final IsCalculationMetaData calculationMetaData = mock(IsCalculationMetaData.class);
    when(calculationMetaData.getCalculationMethod()).thenReturn(CALCULATION_METHOD_FORMAL);
    when(featureCollection.getMetaData()).thenReturn(metaData);
    when(metaData.getCalculation()).thenReturn(calculationMetaData);
    when(calculationMetaData.getOptions()).thenReturn(null);

    final GMLCalculationSetOptionsReader reader = new GMLCalculationSetOptionsReader(featureCollection);

    final CalculationSetOptions options = reader.readCalculationSetOptions(Theme.NCA);
    assertNotNull(options, "Null calculation options field should be read as empty options object");
  }

  @Test
  void testReadCalculationSetOptionsUnknownCalculationMethod() {
    final FeatureCollection featureCollection = mock(FeatureCollection.class);
    final MetaData metaData = mock(MetaData.class);
    final IsCalculationMetaData calculationMetaData = mock(IsCalculationMetaData.class);
    when(calculationMetaData.getCalculationMethod()).thenReturn("UNKNOWN");
    when(featureCollection.getMetaData()).thenReturn(metaData);
    when(metaData.getCalculation()).thenReturn(calculationMetaData);
    when(calculationMetaData.getOptions()).thenReturn(null);

    final GMLCalculationSetOptionsReader reader = new GMLCalculationSetOptionsReader(featureCollection);

    final CalculationSetOptions options = reader.readCalculationSetOptions(Theme.NCA);
    assertNotNull(options, "Null calculation options field should be read as empty options object");
    assertNull(options.getCalculationMethod(), "Calculation type will be null if unknown");
  }

  @Test
  void testReadCalculationSetOptionsNullMaxRange() {
    final FeatureCollection featureCollection = mock(FeatureCollection.class);
    final MetaData metaData = mock(MetaData.class);
    final IsCalculationMetaData calculationMetaData = mock(IsCalculationMetaData.class);
    when(calculationMetaData.getCalculationMethod()).thenReturn(CALCULATION_METHOD_FORMAL);
    when(featureCollection.getMetaData()).thenReturn(metaData);
    when(metaData.getCalculation()).thenReturn(calculationMetaData);

    when(calculationMetaData.getMaximumRange()).thenReturn(null);

    final GMLCalculationSetOptionsReader reader = new GMLCalculationSetOptionsReader(featureCollection);

    final CalculationSetOptions options = reader.readCalculationSetOptions(Theme.NCA);
    assertNotNull(options, "returned options shouldn't be null");
    assertEquals(CalculationMethod.FORMAL_ASSESSMENT, options.getCalculationMethod(), "Calculation type should match");
    assertEquals(0.0, options.getCalculateMaximumRange(), "Maximum range read");
  }

  @Test
  void testReadCalculationSetOptionsUnknownOption() {
    final FeatureCollection featureCollection = mock(FeatureCollection.class);
    final MetaData metaData = mock(MetaData.class);
    final IsCalculationMetaData calculationMetaData = mock(IsCalculationMetaData.class);
    when(calculationMetaData.getCalculationMethod()).thenReturn(CALCULATION_METHOD_FORMAL);
    when(featureCollection.getMetaData()).thenReturn(metaData);
    when(metaData.getCalculation()).thenReturn(calculationMetaData);

    final List<IsGmlProperty<IsCalculationOption>> suppliedOptions = new ArrayList<>();
    suppliedOptions.add(mockCalculationOption("Our Unknown Key", "some value"));
    when(calculationMetaData.getOptions()).thenAnswer(a -> suppliedOptions);

    final GMLCalculationSetOptionsReader reader = new GMLCalculationSetOptionsReader(featureCollection);

    final CalculationSetOptions options = reader.readCalculationSetOptions(Theme.NCA);
    assertNotNull(options, "returned options shouldn't be null");
    assertEquals(CalculationMethod.FORMAL_ASSESSMENT, options.getCalculationMethod(), "Calculation type should match");
  }

  @Test
  void testReadCalculationSetOptionsDuplicateOptions() {
    final FeatureCollection featureCollection = mock(FeatureCollection.class);
    final MetaData metaData = mock(MetaData.class);
    final IsCalculationMetaData calculationMetaData = mock(IsCalculationMetaData.class);
    when(calculationMetaData.getCalculationMethod()).thenReturn(CALCULATION_METHOD_FORMAL);
    when(featureCollection.getMetaData()).thenReturn(metaData);
    when(metaData.getCalculation()).thenReturn(calculationMetaData);

    final List<IsGmlProperty<IsCalculationOption>> suppliedOptions = new ArrayList<>();
    suppliedOptions.add(mockCalculationOption("adms_permit_area", "somewhere"));
    suppliedOptions.add(mockCalculationOption("adms_permit_area", "somewhere else"));
    when(calculationMetaData.getOptions()).thenAnswer(a -> suppliedOptions);

    final GMLCalculationSetOptionsReader reader = new GMLCalculationSetOptionsReader(featureCollection);

    final CalculationSetOptions options = reader.readCalculationSetOptions(Theme.NCA);
    assertNotNull(options, "returned options shouldn't be null");
    assertEquals(CalculationMethod.FORMAL_ASSESSMENT, options.getCalculationMethod(), "Calculation type should match");
    assertEquals("somewhere else", options.getNcaCalculationOptions().getPermitArea(), "PermitArea");
  }

  /**
   * @param spatiallyVaryingRoughness Test spatiallyVaryingRoughness. if null it should be true, else follow boolean value
   */
  @ParameterizedTest
  @CsvSource("null,TRUE,FALSE")
  void testNCAReadCalculationSetOptions(final Boolean spatiallyVaryingRoughness) {
    final FeatureCollection featureCollection = mock(FeatureCollection.class);
    final MetaData metaData = mock(MetaData.class);
    final IsCalculationMetaData calculationMetaData = mock(IsCalculationMetaData.class);
    when(calculationMetaData.getCalculationMethod()).thenReturn(CALCULATION_METHOD_FORMAL);
    when(calculationMetaData.getCalculationJobType()).thenReturn(CALCULATION_JOB_TYPE);
    when(featureCollection.getMetaData()).thenReturn(metaData);
    when(metaData.getCalculation()).thenReturn(calculationMetaData);

    final List<IsGmlProperty<IsCalculationOption>> suppliedOptions = new ArrayList<>();
    suppliedOptions.add(mockCalculationOption("adms_permit_area", "somewhere"));
    suppliedOptions.add(mockCalculationOption("adms_meteo_site_location", "some meteo loc"));
    suppliedOptions.add(mockCalculationOption("adms_meteo_years", "2040,2042"));
    suppliedOptions.add(mockCalculationOption("adms_min_monin_obukhov_length", "3.4"));
    suppliedOptions.add(mockCalculationOption("adms_surface_albedo", "4.5"));
    suppliedOptions.add(mockCalculationOption("adms_priestley_taylor_parameter", "5.6"));
    suppliedOptions.add(mockCalculationOption("adms_met_site_id", "939"));
    suppliedOptions.add(mockCalculationOption("adms_met_dataset_type", "OBS_RAW_GT_90PCT"));
    suppliedOptions.add(mockCalculationOption("adms_met_years", "2022"));
    suppliedOptions.add(mockCalculationOption("adms_met_site_roughness", "3.1"));
    suppliedOptions.add(mockCalculationOption("adms_met_site_min_monin_obukhov_length", "4.2"));
    suppliedOptions.add(mockCalculationOption("adms_met_site_surface_albedo", "5.3"));
    suppliedOptions.add(mockCalculationOption("adms_met_site_priestley_taylor_parameter", "6.4"));
    suppliedOptions.add(mockCalculationOption("adms_plume_depletion_nh3", "true"));
    suppliedOptions.add(mockCalculationOption("adms_plume_depletion_nox", "true"));
    if (spatiallyVaryingRoughness != null) {
      suppliedOptions.add(mockCalculationOption("adms_spatially_varying_roughness", spatiallyVaryingRoughness.toString()));
    }
    suppliedOptions.add(mockCalculationOption("adms_complex_terrain", "true"));
    when(calculationMetaData.getOptions()).thenAnswer(a -> suppliedOptions);
    when(calculationMetaData.getMaximumRange()).thenReturn(40.0);

    final GMLCalculationSetOptionsReader reader = new GMLCalculationSetOptionsReader(featureCollection);

    final CalculationSetOptions options = reader.readCalculationSetOptions(Theme.NCA);
    assertNotNull(options, "returned options shouldn't be null");
    assertEquals(CalculationMethod.FORMAL_ASSESSMENT, options.getCalculationMethod(), "Calculation method should match");
    assertEquals(CalculationJobType.MAX_TEMPORARY_EFFECT, options.getCalculationJobType(), "Calculation job type should match");
    final NCACalculationOptions ncaOptions = options.getNcaCalculationOptions();
    assertEquals("somewhere", ncaOptions.getPermitArea(), "PermitArea");
    final ADMSOptions admsOptions = ncaOptions.getAdmsOptions();
    assertEquals(3.4, admsOptions.getMinMoninObukhovLength(), "MinMoninObukhovLength");
    assertEquals(4.5, admsOptions.getSurfaceAlbedo(), "SurfaceAlbedo");
    assertEquals(5.6, admsOptions.getPriestleyTaylorParameter(), "PriestleyTaylorParameter");
    assertEquals(939, admsOptions.getMetSiteId(), "MetSiteId");
    assertEquals(MetDatasetType.OBS_RAW_GT_90PCT, admsOptions.getMetDatasetType(), "MetDatasetType");
    assertEquals(List.of("2022"), admsOptions.getMetYears(), "MetYears");
    final MetSurfaceCharacteristics msc = admsOptions.getMetSiteCharacteristics("2022");
    assertEquals(3.1, msc.getRoughness(), "MsRoughness");
    assertEquals(4.2, msc.getMinMoninObukhovLength(), "MsMinMoninObukhovLength");
    assertEquals(5.3, msc.getSurfaceAlbedo(), "MsSurfaceAlbedo");
    assertEquals(6.4, msc.getPriestleyTaylorParameter(), "MsPriestleyTaylorParameter");
    assertTrue(admsOptions.isPlumeDepletionNH3(), "PlumeDepletionNH3");
    assertTrue(admsOptions.isPlumeDepletionNOX(), "PlumeDepletionNOX");
    assertEquals(spatiallyVaryingRoughness == null || spatiallyVaryingRoughness, admsOptions.isSpatiallyVaryingRoughness(),
        "SpatiallyVaryingRoughness");
    assertTrue(admsOptions.isComplexTerrain(), "ComplexTerrain");

    assertEquals(40.0, options.getCalculateMaximumRange(), "Maximum range read");
  }

  @Test
  void testReadCalculationSetOptionsEmptyOptions() {
    final FeatureCollection featureCollection = mock(FeatureCollection.class);
    final MetaData metaData = mock(MetaData.class);
    final IsCalculationMetaData calculationMetaData = mock(IsCalculationMetaData.class);
    when(calculationMetaData.getCalculationMethod()).thenReturn(CALCULATION_METHOD_FORMAL);
    when(featureCollection.getMetaData()).thenReturn(metaData);
    when(metaData.getCalculation()).thenReturn(calculationMetaData);

    final List<IsGmlProperty<IsCalculationOption>> suppliedOptions = new ArrayList<>();
    when(calculationMetaData.getOptions()).thenAnswer(a -> suppliedOptions);

    final GMLCalculationSetOptionsReader reader = new GMLCalculationSetOptionsReader(featureCollection);

    final CalculationSetOptions options = reader.readCalculationSetOptions(Theme.NCA);
    assertNotNull(options, "returned options shouldn't be null");
    assertEquals(CalculationMethod.FORMAL_ASSESSMENT, options.getCalculationMethod(), "Calculation type should match");
    assertNull(options.getCalculationJobType(), "CalculationJobType");
    final NCACalculationOptions ncaOptions = options.getNcaCalculationOptions();
    assertNull(ncaOptions.getPermitArea(), "PermitArea");
    final ADMSOptions admsOptions = ncaOptions.getAdmsOptions();
    assertEquals(ADMSLimits.MIN_MONIN_OBUKHOV_LENGTH_DEFAULT, admsOptions.getMinMoninObukhovLength(),
        "MinMoninObukhovLength");
    assertEquals(ADMSLimits.SURFACE_ALBEDO_DEFAULT, admsOptions.getSurfaceAlbedo(), "SurfaceAlbedo");
    assertEquals(ADMSLimits.PRIESTLEY_TAYLOR_PARAMETER_DEFAULT, admsOptions.getPriestleyTaylorParameter(),
        "PriestleyTaylorParameter");
    assertEquals(0, admsOptions.getMetSiteId(), "MetSiteId");
    assertEquals(List.of(), admsOptions.getMetYears(), "MeteoYears");
    assertFalse(admsOptions.isPlumeDepletionNH3(), "PlumeDepletionNH3");
    assertFalse(admsOptions.isPlumeDepletionNOX(), "PlumeDepletionNOX");
    assertTrue(admsOptions.isSpatiallyVaryingRoughness(), "SpatiallyVaryingRoughness");
    assertFalse(admsOptions.isComplexTerrain(), "ComplexTerrain");

    assertEquals(0.0, options.getCalculateMaximumRange(), "Maximum range read");
  }

  @Test
  void testReadCalculationSetOptionsWnb() {
    final FeatureCollection featureCollection = mock(FeatureCollection.class);
    final MetaData metaData = mock(MetaData.class);
    final IsCalculationMetaData calculationMetaData = mock(IsCalculationMetaData.class);
    when(calculationMetaData.getCalculationMethod()).thenReturn(CALCULATION_METHOD_FORMAL);
    when(featureCollection.getMetaData()).thenReturn(metaData);
    when(metaData.getCalculation()).thenReturn(calculationMetaData);

    final List<IsGmlProperty<IsCalculationOption>> suppliedOptions = new ArrayList<>();
    suppliedOptions.add(mockCalculationOption("adms_permit_area", "somewhere"));
    suppliedOptions.add(mockCalculationOption("adms_meteo_site_location", "some meteo loc"));
    suppliedOptions.add(mockCalculationOption("adms_meteo_years", "2040,2042"));
    suppliedOptions.add(mockCalculationOption("adms_min_monin_obukhov_length", "3.4"));
    suppliedOptions.add(mockCalculationOption("adms_surface_albedo", "4.5"));
    suppliedOptions.add(mockCalculationOption("adms_priestley_taylor_parameter", "5.6"));
    suppliedOptions.add(mockCalculationOption("adms_met_site_id", "939"));
    suppliedOptions.add(mockCalculationOption("adms_met_site_roughness", "3.1"));
    suppliedOptions.add(mockCalculationOption("adms_met_site_min_monin_obukhov_length", "4.2"));
    suppliedOptions.add(mockCalculationOption("adms_met_site_surface_albedo", "5.3"));
    suppliedOptions.add(mockCalculationOption("adms_met_site_priestley_taylor_parameter", "6.4"));
    suppliedOptions.add(mockCalculationOption("adms_plume_depletion_nh3", "true"));
    suppliedOptions.add(mockCalculationOption("adms_plume_depletion_nox", "true"));
    suppliedOptions.add(mockCalculationOption("adms_spatially_varying_roughness", "true"));
    suppliedOptions.add(mockCalculationOption("adms_complex_terrain", "true"));
    when(calculationMetaData.getOptions()).thenAnswer(a -> suppliedOptions);
    when(calculationMetaData.getMaximumRange()).thenReturn(40.0);

    final GMLCalculationSetOptionsReader reader = new GMLCalculationSetOptionsReader(featureCollection);

    final CalculationSetOptions options = reader.readCalculationSetOptions(Theme.OWN2000);
    assertNotNull(options, "returned options shouldn't be null");
    assertEquals(CalculationMethod.FORMAL_ASSESSMENT, options.getCalculationMethod(), "Calculation type should match");
    final NCACalculationOptions ncaOptions = options.getNcaCalculationOptions();
    assertNull(ncaOptions.getPermitArea(), "PermitArea");
    final ADMSOptions admsOptions = ncaOptions.getAdmsOptions();
    assertEquals(0.0, admsOptions.getMinMoninObukhovLength(), "MinMoninObukhovLength");
    assertEquals(0.0, admsOptions.getSurfaceAlbedo(), "SurfaceAlbedo");
    assertEquals(0.0, admsOptions.getPriestleyTaylorParameter(), "PriestleyTaylorParameter");
    assertEquals(0, admsOptions.getMetSiteId(), "MetSiteId");
    assertEquals(List.of(), admsOptions.getMetYears(), "MeteoYears");
    assertFalse(admsOptions.isPlumeDepletionNH3(), "PlumeDepletionNH3");
    assertFalse(admsOptions.isPlumeDepletionNOX(), "PlumeDepletionNOX");
    assertFalse(admsOptions.isSpatiallyVaryingRoughness(), "SpatiallyVaryingRoughness");
    assertFalse(admsOptions.isComplexTerrain(), "ComplexTerrain");

    assertEquals(40.0, options.getCalculateMaximumRange(), "Maximum range read");
  }

  @Test
  void testReadCalculationSetOptionsUnexpectedValues() {
    final FeatureCollection featureCollection = mock(FeatureCollection.class);
    final MetaData metaData = mock(MetaData.class);
    final IsCalculationMetaData calculationMetaData = mock(IsCalculationMetaData.class);
    when(calculationMetaData.getCalculationMethod()).thenReturn(CALCULATION_METHOD_FORMAL);
    when(featureCollection.getMetaData()).thenReturn(metaData);
    when(metaData.getCalculation()).thenReturn(calculationMetaData);

    final List<IsGmlProperty<IsCalculationOption>> suppliedOptions = new ArrayList<>();
    suppliedOptions.add(mockCalculationOption("adms_meteo_years", "MySpecialYear"));
    suppliedOptions.add(mockCalculationOption("adms_plume_depletion_nh3", "maybe"));
    suppliedOptions.add(mockCalculationOption("adms_plume_depletion_nox", "maybe"));
    suppliedOptions.add(mockCalculationOption("adms_spatially_varying_roughness", "maybe"));
    suppliedOptions.add(mockCalculationOption("adms_complex_terrain", "maybe"));
    when(calculationMetaData.getOptions()).thenAnswer(a -> suppliedOptions);
    when(calculationMetaData.getMaximumRange()).thenReturn(40.0);

    final GMLCalculationSetOptionsReader reader = new GMLCalculationSetOptionsReader(featureCollection);

    final CalculationSetOptions options = reader.readCalculationSetOptions(Theme.NCA);
    assertNotNull(options, "returned options shouldn't be null");
    final NCACalculationOptions ncaOptions = options.getNcaCalculationOptions();
    final ADMSOptions admsOptions = ncaOptions.getAdmsOptions();
    assertFalse(admsOptions.isPlumeDepletionNH3(), "PlumeDepletionNH3");
    assertFalse(admsOptions.isPlumeDepletionNOX(), "PlumeDepletionNOX");
    assertFalse(admsOptions.isSpatiallyVaryingRoughness(), "SpatiallyVaryingRoughness");
    assertFalse(admsOptions.isComplexTerrain(), "ComplexTerrain");
  }

  @ParameterizedTest
  @MethodSource("unparseableValues")
  void testReadCalculationSetOptionsUnparseableValues(final String key, final String value, final Class<? extends Throwable> expectedException) {
    final FeatureCollection featureCollection = mock(FeatureCollection.class);
    final MetaData metaData = mock(MetaData.class);
    final IsCalculationMetaData calculationMetaData = mock(IsCalculationMetaData.class);
    when(calculationMetaData.getCalculationMethod()).thenReturn(CALCULATION_METHOD_FORMAL);
    when(featureCollection.getMetaData()).thenReturn(metaData);
    when(metaData.getCalculation()).thenReturn(calculationMetaData);

    final List<IsGmlProperty<IsCalculationOption>> suppliedOptions = new ArrayList<>();
    // Always add met site, else the other met site properties won't even be tried.
    if (!"adms_met_site_id".equalsIgnoreCase(key)) {
      suppliedOptions.add(mockCalculationOption("adms_met_site_id", "1"));
    }
    suppliedOptions.add(mockCalculationOption(key, value));
    suppliedOptions.add(mockCalculationOption("adms_met_years", "2022"));
    when(calculationMetaData.getOptions()).thenAnswer(a -> suppliedOptions);

    final GMLCalculationSetOptionsReader reader = new GMLCalculationSetOptionsReader(featureCollection);

    assertThrows(expectedException, () -> reader.readCalculationSetOptions(Theme.NCA),
        " Should throw the expected exception because values are not readable.");
  }

  private static Stream<Arguments> unparseableValues() {
    return Stream.of(
        testCaseUnparseable("adms_min_monin_obukhov_length", "yes please", NumberFormatException.class),
        testCaseUnparseable("adms_surface_albedo", "no please", NumberFormatException.class),
        testCaseUnparseable("adms_priestley_taylor_parameter", "another", NumberFormatException.class),
        testCaseUnparseable("adms_met_site_id", "york", NumberFormatException.class),
        testCaseUnparseable("adms_met_site_roughness", "use_default", NumberFormatException.class),
        testCaseUnparseable("adms_met_site_min_monin_obukhov_length", "use_default", NumberFormatException.class),
        testCaseUnparseable("adms_met_site_surface_albedo", "use_default", NumberFormatException.class),
        testCaseUnparseable("adms_met_site_priestley_taylor_parameter", "use_default", NumberFormatException.class));
  }

  private static Arguments testCaseUnparseable(final String key, final String value, final Class<? extends Throwable> expectedException) {
    return Arguments.of(key, value, expectedException);
  }

  private IsGmlProperty<IsCalculationOption> mockCalculationOption(final String key, final String value) {
    final IsGmlProperty<IsCalculationOption> property = mock(MockProperty.class);
    final IsCalculationOption option = mock(IsCalculationOption.class);
    when(option.getKey()).thenReturn(key);
    when(option.getValue()).thenReturn(value);
    when(property.getProperty()).thenReturn(option);
    return property;
  }

  /*
   * Class to avoid generic warning when mocking property...
   */
  public abstract class MockProperty implements IsGmlProperty<IsCalculationOption> {

  }

}
