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
package nl.overheid.aerius.gml.v5_0.togml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import nl.overheid.aerius.gml.base.AeriusGMLVersion;
import nl.overheid.aerius.gml.base.Definitions;
import nl.overheid.aerius.gml.base.FeatureMember;
import nl.overheid.aerius.gml.base.GMLVersionWriter;
import nl.overheid.aerius.gml.base.MetaData;
import nl.overheid.aerius.gml.base.MetaDataInput;
import nl.overheid.aerius.gml.base.StringUtils;
import nl.overheid.aerius.gml.base.geo.Geometry2GML;
import nl.overheid.aerius.gml.base.metadata.LegacySituationType;
import nl.overheid.aerius.gml.v5_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v5_0.collection.FeatureCollectionImpl;
import nl.overheid.aerius.gml.v5_0.definitions.CustomDiurnalVariation;
import nl.overheid.aerius.gml.v5_0.definitions.DefinitionsImpl;
import nl.overheid.aerius.gml.v5_0.metadata.AddressImpl;
import nl.overheid.aerius.gml.v5_0.metadata.CalculationMetadata;
import nl.overheid.aerius.gml.v5_0.metadata.CalculationOption;
import nl.overheid.aerius.gml.v5_0.metadata.CalculationOptionProperty;
import nl.overheid.aerius.gml.v5_0.metadata.MetaDataImpl;
import nl.overheid.aerius.gml.v5_0.metadata.ProjectMetadata;
import nl.overheid.aerius.gml.v5_0.metadata.SituationMetadata;
import nl.overheid.aerius.gml.v5_0.metadata.VersionMetadata;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.Theme;
import nl.overheid.aerius.shared.domain.calculation.CalculationMethod;
import nl.overheid.aerius.shared.domain.calculation.CalculationSetOptions;
import nl.overheid.aerius.shared.domain.geo.HexagonZoomLevel;
import nl.overheid.aerius.shared.domain.result.EmissionResultKey;
import nl.overheid.aerius.shared.domain.result.EmissionResultType;
import nl.overheid.aerius.shared.domain.scenario.SituationType;
import nl.overheid.aerius.shared.domain.v2.building.BuildingFeature;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKCorrection;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKDispersionLineFeature;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKMeasureFeature;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioMetaData;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.util.OptionsMetadataUtil;
import nl.overheid.aerius.util.gml.GMLIdUtil;

/**
 * GML proxy for version 5.0.
 */
public class GMLVersionWriterV50 implements GMLVersionWriter {

  private final Source2GML source2gml;
  private final Building2GML building2gml;
  private final Result2GML result2gml;
  private final CIMLKMeasure2GML measure2gml;
  private final CIMLKDispersionLine2GML dispersionLine2gml;

  public GMLVersionWriterV50(final HexagonZoomLevel zoomLevel1, final String srsName) {
    final Geometry2GML geometry2gml = new Geometry2GML(srsName);
    source2gml = new Source2GML(geometry2gml);
    building2gml = new Building2GML(geometry2gml);
    result2gml = new Result2GML(geometry2gml, zoomLevel1);
    measure2gml = new CIMLKMeasure2GML(geometry2gml);
    dispersionLine2gml = new CIMLKDispersionLine2GML(geometry2gml);
  }

  @Override
  public AeriusGMLVersion getGMLVersion() {
    return AeriusGMLVersion.V5_0;
  }

  @Override
  public String getNameSpace() {
    return CalculatorSchema.NAMESPACE;
  }

  @Override
  public String getPublicSchemaLocation() {
    return CalculatorSchema.PUBLIC_SCHEMA_LOCATION;
  }

  @Override
  public FeatureCollectionImpl createFeatureCollection() {
    return new FeatureCollectionImpl();
  }

  @Override
  public MetaData metaData2GML(final MetaDataInput metaDataInput) throws AeriusException {
    final MetaDataImpl metaDataImpl = new MetaDataImpl();
    metaDataImpl.setSituation(getSituation(metaDataInput));
    metaDataImpl.setCalculation(getCalculation(metaDataInput));
    metaDataImpl.setProject(getProject(metaDataInput));
    metaDataImpl.setVersion(getVersion(metaDataInput));
    return metaDataImpl;
  }

  private static SituationMetadata getSituation(final MetaDataInput input) throws AeriusException {
    final SituationMetadata situation;
    if (isEmptySituationData(input)) {
      situation = null;
    } else {
      ensureSituationType(input);
      situation = new SituationMetadata();
      situation.setName(input.getName());
      situation.setReference(input.getReference());
      setSituationType(input, situation);
      situation.setNettingFactor(input.getNettingFactor());
    }
    return situation;
  }

  private static void setSituationType(final MetaDataInput input, final SituationMetadata situation) {
    final SituationType inputSituationType = input.getSituationType();
    if (inputSituationType == null) {
      situation.setSituationType(null);
    } else if (inputSituationType == SituationType.OFF_SITE_REDUCTION) {
      // OFF_SITE_REDUCTION is not in the 5.0 XSD, so we map it back to NETTING.
      situation.setSituationType(LegacySituationType.NETTING);
    } else {
      situation.setSituationType(LegacySituationType.safeValueOf(inputSituationType.name()));
    }
  }

  private static ProjectMetadata getProject(final MetaDataInput input) {
    final ProjectMetadata project = new ProjectMetadata();

    project.setYear(input.getYear());
    setScenarioMetaData(project, input.getScenarioMetaData());
    return project;
  }

  private static void setScenarioMetaData(final ProjectMetadata project, final ScenarioMetaData scenarioData) {
    if (scenarioData != null) {
      project.setName(scenarioData.getProjectName());
      project.setCorporation(scenarioData.getCorporation());
      project.setDescription(scenarioData.getDescription());
      if (hasValidAddress(scenarioData)) {
        final AddressImpl address = new AddressImpl();
        project.setFacilityLocation(address);
        address.setStreetAddress(scenarioData.getStreetAddress());
        address.setPostcode(scenarioData.getPostcode());
        address.setCity(scenarioData.getCity());
      }
    }
  }

  private static boolean isEmptySituationData(final MetaDataInput input) {
    return StringUtils.isEmpty(input.getName()) && StringUtils.isEmpty(input.getReference())
        && input.getSituationType() == null;
  }

  private static void ensureSituationType(final MetaDataInput input) throws AeriusException {
    // Sanity check on situation type. It should be set when writing,
    // but older files are read without a default,
    // since there is no obvious default without knowledge about the file containing the GML.
    if (input.getSituationType() == null) {
      // situation type wasn't available in metadata, which will generate GML which is invalid according to XSD.
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR);
    }
  }

  private static boolean hasValidAddress(final ScenarioMetaData scenarioData) {
    return !StringUtils.isEmpty(scenarioData.getStreetAddress()) && !StringUtils.isEmpty(scenarioData.getPostcode())
        && !StringUtils.isEmpty(scenarioData.getCity());
  }

  private CalculationMetadata getCalculation(final MetaDataInput input) {
    final CalculationMetadata calculation;
    if (input.isResultsIncluded()) {
      calculation = new CalculationMetadata();
      calculation.setCalculationType(convertToOldCalculationType(input));
      if (input.getOptions().isMaximumRangeRelevant()) {
        calculation.setMaximumRange(input.getOptions().getCalculateMaximumRange());
      }
      calculation.setSubstances(input.getOptions().getSubstances());
      calculation.setResultTypes(determineResultTypes(input.getOptions().getEmissionResultKeys()));
      calculation.setOptions(options2GML(input.getTheme(), input.getOptions()));
    } else {
      calculation = null;
    }
    return calculation;
  }

  private static String convertToOldCalculationType(final MetaDataInput input) {
    final CalculationMethod calculationMethod = input.getOptions().getCalculationMethod();

    return calculationMethod == null ? null : calculationMethod.type();
  }

  private static VersionMetadata getVersion(final MetaDataInput input) {
    final VersionMetadata version = new VersionMetadata();
    version.setAeriusVersion(input.getVersion());
    version.setDatabaseVersion(input.getDatabaseVersion());
    return version;
  }

  private static List<EmissionResultType> determineResultTypes(final Set<EmissionResultKey> keys) {
    final List<EmissionResultType> types = new ArrayList<>();
    for (final EmissionResultKey key : keys) {
      if (!types.contains(key.getEmissionResultType())) {
        types.add(key.getEmissionResultType());
      }
    }
    return types.stream().sorted().collect(Collectors.toList());
  }

  private List<CalculationOptionProperty> options2GML(final Theme theme, final CalculationSetOptions options) {
    final Map<String, String> gmlOptionsMap = OptionsMetadataUtil.optionsToMap(theme, options, false);
    return gmlOptionsMap.entrySet().stream()
        .map(entry -> new CalculationOption(entry.getKey(), entry.getValue()))
        .map(CalculationOptionProperty::new)
        .collect(Collectors.toList());
  }

  @Override
  public Definitions definitions2GML(final nl.overheid.aerius.shared.domain.v2.scenario.Definitions definitions) throws AeriusException {
    final DefinitionsImpl gmlDefinitions;
    if (definitions != null && definitions.hasContent()) {
      gmlDefinitions = new DefinitionsImpl();
      final List<CustomDiurnalVariation> customDiurnalVariations = definitions.getCustomTimeVaryingProfiles().stream()
          .map(this::convert)
          .collect(Collectors.toList());
      gmlDefinitions.setCustomDiurnalVariations(customDiurnalVariations);
    } else {
      gmlDefinitions = null;
    }
    return gmlDefinitions;
  }

  private CustomDiurnalVariation convert(final nl.overheid.aerius.shared.domain.v2.characteristics.CustomTimeVaryingProfile customVariation) {
    final CustomDiurnalVariation gmlCustomVariation = new CustomDiurnalVariation();
    final String validGmlId = GMLIdUtil.toValidGmlId(customVariation.getGmlId(), getNameSpace(), GMLIdUtil.DIURNAL_VARIATION_PREFIX);
    gmlCustomVariation.setId(validGmlId);
    gmlCustomVariation.setLabel(customVariation.getLabel());
    gmlCustomVariation.setCustomType(customVariation.getType().name());
    gmlCustomVariation.setValues(customVariation.getValues());
    return gmlCustomVariation;
  }

  @Override
  public FeatureMember result2GML(final CalculationPointFeature point, final Substance[] substances, final List<CIMLKCorrection> corrections)
      throws AeriusException {
    return result2gml.toGML(point, substances, corrections);
  }

  @Override
  public List<FeatureMember> source2GML(final EmissionSourceFeature source, final Substance[] substances) throws AeriusException {
    return source2gml.toGML(source, substances);
  }

  @Override
  public FeatureMember cimlkMeasure2GML(final CIMLKMeasureFeature measure) throws AeriusException {
    return measure2gml.toGML(measure);
  }

  @Override
  public FeatureMember cimlkDispersionLine2GML(final CIMLKDispersionLineFeature dispersionLine) throws AeriusException {
    return dispersionLine2gml.toGML(dispersionLine);
  }

  @Override
  public Object cimlkCorrection2GML(final CIMLKCorrection correction) throws AeriusException {
    return result2gml.toGMLCorrection(correction);
  }

  @Override
  public FeatureMember building2GML(final BuildingFeature building) throws AeriusException {
    return building2gml.toGML(building);
  }

}
