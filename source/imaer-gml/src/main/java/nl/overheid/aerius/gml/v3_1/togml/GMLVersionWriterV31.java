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
package nl.overheid.aerius.gml.v3_1.togml;

import java.util.ArrayList;
import java.util.List;
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
import nl.overheid.aerius.gml.v3_1.base.CalculatorSchema;
import nl.overheid.aerius.gml.v3_1.collection.FeatureCollectionImpl;
import nl.overheid.aerius.gml.v3_1.metadata.AddressImpl;
import nl.overheid.aerius.gml.v3_1.metadata.CalculationMetadata;
import nl.overheid.aerius.gml.v3_1.metadata.MetaDataImpl;
import nl.overheid.aerius.gml.v3_1.metadata.ProjectMetadata;
import nl.overheid.aerius.gml.v3_1.metadata.SituationMetadata;
import nl.overheid.aerius.gml.v3_1.metadata.VersionMetadata;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.calculation.CalculationType;
import nl.overheid.aerius.shared.domain.geo.HexagonZoomLevel;
import nl.overheid.aerius.shared.domain.result.EmissionResultKey;
import nl.overheid.aerius.shared.domain.result.EmissionResultType;
import nl.overheid.aerius.shared.domain.v2.building.BuildingFeature;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLCorrection;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLDispersionLineFeature;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLMeasureFeature;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.scenario.ScenarioMetaData;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * GML proxy for version 3.1.
 */
public class GMLVersionWriterV31 implements GMLVersionWriter {

  private final Source2GML source2gml;
  private final Result2GML result2gml;
  private final NSLMeasure2GML measure2gml;
  private final NSLDispersionLine2GML dispersionLine2gml;

  public GMLVersionWriterV31(final HexagonZoomLevel zoomLevel1, final String srsName) {
    final Geometry2GML geometry2gml = new Geometry2GML(srsName);
    source2gml = new Source2GML(geometry2gml);
    result2gml = new Result2GML(geometry2gml, zoomLevel1);
    measure2gml = new NSLMeasure2GML(geometry2gml);
    dispersionLine2gml = new NSLDispersionLine2GML(geometry2gml);
  }

  @Override
  public AeriusGMLVersion getGMLVersion() {
    return AeriusGMLVersion.V3_1;
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
  public MetaData metaData2GML(final MetaDataInput metaDataInput) {
    final MetaDataImpl metaDataImpl = new MetaDataImpl();
    metaDataImpl.setSituation(getSituation(metaDataInput));
    metaDataImpl.setCalculation(getCalculation(metaDataInput));
    metaDataImpl.setProject(getProject(metaDataInput));
    metaDataImpl.setVersion(getVersion(metaDataInput));
    return metaDataImpl;
  }

  private SituationMetadata getSituation(final MetaDataInput input) {
    final SituationMetadata situation;
    if (isEmptySituationData(input)) {
      situation = null;
    } else {
      situation = new SituationMetadata();
      situation.setName(input.getName());
      situation.setReference(input.getScenarioMetaData().getReference());
    }
    return situation;
  }

  private ProjectMetadata getProject(final MetaDataInput input) {
    final ScenarioMetaData scenarioData = input.getScenarioMetaData();
    final ProjectMetadata project = new ProjectMetadata();
    project.setYear(input.getYear());
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
    return project;
  }

  private boolean isEmptySituationData(final MetaDataInput input) {
    return StringUtils.isEmpty(input.getName()) && StringUtils.isEmpty(input.getScenarioMetaData().getReference());
  }

  private boolean hasValidAddress(final ScenarioMetaData scenarioData) {
    return !StringUtils.isEmpty(scenarioData.getStreetAddress()) && !StringUtils.isEmpty(scenarioData.getPostcode())
        && !StringUtils.isEmpty(scenarioData.getCity());
  }

  private CalculationMetadata getCalculation(final MetaDataInput input) {
    final CalculationMetadata calculation;
    if (input.isResultsIncluded()) {
      calculation = new CalculationMetadata();
      final CalculationType calculationType = input.getOptions().getCalculationType();
      calculation.setCalculationType(calculationType);
      if (input.getOptions().isMaximumRangeRelevant()) {
        calculation.setMaximumRange(input.getOptions().getCalculateMaximumRange());
      }
      if (input.getOptions().isIncludeMonitorSrm2Network()) {
        calculation.setMonitorSrm2Year(input.getOptions().getMonitorSrm2Year());
      }
      calculation.setSubstances(input.getOptions().getSubstances());
      calculation.setResultTypes(determineResultTypes(input.getOptions().getEmissionResultKeys()));
    } else {
      calculation = null;
    }
    return calculation;
  }

  private VersionMetadata getVersion(final MetaDataInput input) {
    final VersionMetadata version = new VersionMetadata();
    version.setAeriusVersion(input.getVersion());
    version.setDatabaseVersion(input.getDatabaseVersion());
    return version;
  }

  private List<EmissionResultType> determineResultTypes(final Set<EmissionResultKey> keys) {
    final List<EmissionResultType> types = new ArrayList<>();
    for (final EmissionResultKey key : keys) {
      if (!types.contains(key.getEmissionResultType())) {
        types.add(key.getEmissionResultType());
      }
    }
    return types.stream().sorted().collect(Collectors.toList());
  }

  @Override
  public Definitions definitions2GML(final nl.overheid.aerius.shared.domain.v2.scenario.Definitions definitions) throws AeriusException {
    // Not available in this version
    return null;
  }

  @Override
  public FeatureMember result2GML(final CalculationPointFeature point, final Substance[] substances, final List<NSLCorrection> corrections)
      throws AeriusException {
    return result2gml.toGML(point, substances, corrections);
  }

  @Override
  public List<FeatureMember> source2GML(final EmissionSourceFeature source, final Substance[] substances) throws AeriusException {
    return source2gml.toGML(source, substances);
  }

  @Override
  public FeatureMember nslMeasure2GML(final NSLMeasureFeature measure) throws AeriusException {
    return measure2gml.toGML(measure);
  }

  @Override
  public FeatureMember nslDispersionLine2GML(final NSLDispersionLineFeature dispersionLine) throws AeriusException {
    return dispersionLine2gml.toGML(dispersionLine);
  }

  @Override
  public Object nslCorrection2GML(final NSLCorrection correction) throws AeriusException {
    return result2gml.toGMLCorrection(correction);
  }

  @Override
  public FeatureMember building2GML(final BuildingFeature building) throws AeriusException {
    // Shouldn't be used for this version. Unsure if we should return a null, an empty object or throw an exception, but null for now seems OK.
    return null;
  }

}
