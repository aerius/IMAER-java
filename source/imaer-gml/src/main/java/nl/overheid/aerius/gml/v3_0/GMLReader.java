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
package nl.overheid.aerius.gml.v3_0;

import java.util.List;
import java.util.stream.Collectors;

import nl.overheid.aerius.gml.base.FeatureMember;
import nl.overheid.aerius.gml.base.GML2NSLCorrection;
import nl.overheid.aerius.gml.base.GML2NSLDispersionLine;
import nl.overheid.aerius.gml.base.GML2NSLMeasure;
import nl.overheid.aerius.gml.base.GML2Result;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.GMLVersionReader;
import nl.overheid.aerius.gml.base.characteristics.GML2OPSSourceCharacteristics;
import nl.overheid.aerius.gml.v3_0.result.AbstractCalculationPoint;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLCorrection;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLDispersionLineFeature;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLMeasureFeature;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;

/**
 * {@link GMLVersionReader} for AERIUS GML version 3.0.
 */
public class GMLReader implements GMLVersionReader {

  private final GML2Source gml2Source;
  private final GML2Result gml2Result;
  private final GML2NSLMeasure gml2NSLMeasure;
  private final GML2NSLDispersionLine gml2NSLDispersionLine;
  private final GML2NSLCorrection gml2NSLCorrection;

  public GMLReader(final GMLConversionData conversionData, final GML2OPSSourceCharacteristics gml2SourceCharacteristics) {
    gml2Source = new GML2Source(conversionData, gml2SourceCharacteristics);
    gml2Result = new GML2Result(conversionData);
    gml2NSLMeasure = new GML2NSLMeasure(conversionData);
    gml2NSLDispersionLine = new GML2NSLDispersionLine();
    gml2NSLCorrection = new GML2NSLCorrection();
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
  public List<NSLMeasureFeature> nslMeasuresFromGML(final List<FeatureMember> members) {
    return gml2NSLMeasure.fromGML(members);
  }

  @Override
  public List<NSLDispersionLineFeature> nslDispersionLinesFromGML(final List<FeatureMember> members) {
    return gml2NSLDispersionLine.fromGML(members);
  }

  @Override
  public List<NSLCorrection> nslCorrectionsFromGML(final List<FeatureMember> members) {
    return members.stream()
        .filter(AbstractCalculationPoint.class::isInstance)
        .map(AbstractCalculationPoint.class::cast)
        .map(gml2NSLCorrection::fromGML)
        .flatMap(List::stream)
        .collect(Collectors.toList());
  }

}
