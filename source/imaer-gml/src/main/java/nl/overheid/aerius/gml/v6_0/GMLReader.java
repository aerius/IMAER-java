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
package nl.overheid.aerius.gml.v6_0;

import java.util.List;
import java.util.stream.Collectors;

import nl.overheid.aerius.gml.base.FeatureMember;
import nl.overheid.aerius.gml.base.GML2Definitions;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.GMLVersionReader;
import nl.overheid.aerius.gml.base.building.GML2Building;
import nl.overheid.aerius.gml.base.characteristics.GML2SourceCharacteristics;
import nl.overheid.aerius.gml.base.measure.GML2CIMLKMeasure;
import nl.overheid.aerius.gml.base.result.GML2CIMLKCorrection;
import nl.overheid.aerius.gml.base.result.GML2Result;
import nl.overheid.aerius.gml.base.source.road.GML2CIMLKDispersionLine;
import nl.overheid.aerius.gml.v6_0.result.AbstractCalculationPoint;
import nl.overheid.aerius.shared.domain.v2.building.BuildingFeature;
import nl.overheid.aerius.shared.domain.v2.characteristics.SourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKCorrection;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKDispersionLineFeature;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKMeasureFeature;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.scenario.Definitions;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;

/**
 * {@link GMLVersionReader} for AERIUS GML version 6.0.
 */
final class GMLReader<S extends SourceCharacteristics> implements GMLVersionReader {

  private final GML2Source<S> gml2Source;
  private final GML2Building gml2Building;
  private final GML2Result gml2Result;
  private final GML2CIMLKMeasure gml2CIMLKMeasure;
  private final GML2CIMLKDispersionLine gml2CIMLKDispersionLine;
  private final GML2CIMLKCorrection gml2CIMLKCorrection;
  private final GML2Definitions gml2Definitions;

  public GMLReader(final GMLConversionData conversionData, final GML2SourceCharacteristics<S> gml2SourceCharacteristics) {
    gml2Source = new GML2Source<>(conversionData, gml2SourceCharacteristics);
    gml2Building = new GML2Building(conversionData);
    gml2Result = new GML2Result(conversionData);
    gml2CIMLKMeasure = new GML2CIMLKMeasure(conversionData);
    gml2CIMLKDispersionLine = new GML2CIMLKDispersionLine();
    gml2CIMLKCorrection = new GML2CIMLKCorrection();
    gml2Definitions = new GML2Definitions(conversionData);
  }

  @Override
  public List<CalculationPointFeature> calculationPointsFromGML(final List<FeatureMember> members, final boolean includeResults) {
    return gml2Result.fromGML(members, includeResults);
  }

  @Override
  public List<EmissionSourceFeature> sourcesFromGML(final List<FeatureMember> members) {
    return gml2Source.fromGML(members);
  }

  @Override
  public List<CIMLKMeasureFeature> cimlkMeasuresFromGML(final List<FeatureMember> members) {
    return gml2CIMLKMeasure.fromGML(members);
  }

  @Override
  public List<CIMLKDispersionLineFeature> cimlkDispersionLinesFromGML(final List<FeatureMember> members) {
    return gml2CIMLKDispersionLine.fromGML(members);
  }

  @Override
  public List<CIMLKCorrection> cimlkCorrectionsFromGML(final List<FeatureMember> members) {
    return members.stream()
        .filter(AbstractCalculationPoint.class::isInstance)
        .map(AbstractCalculationPoint.class::cast)
        .map(gml2CIMLKCorrection::fromGML)
        .flatMap(List::stream)
        .collect(Collectors.toList());
  }

  @Override
  public List<BuildingFeature> buildingsFromGML(final List<FeatureMember> members) {
    return gml2Building.fromGML(members);
  }

  @Override
  public Definitions definitionsFromGML(final nl.overheid.aerius.gml.base.Definitions definitions) {
    return gml2Definitions.fromGML(definitions);
  }

}
