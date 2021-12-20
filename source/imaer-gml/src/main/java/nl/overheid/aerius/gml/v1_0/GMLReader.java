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
package nl.overheid.aerius.gml.v1_0;

import java.util.List;

import nl.overheid.aerius.gml.base.FeatureMember;
import nl.overheid.aerius.gml.base.GML2Result;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.GMLVersionReader;
import nl.overheid.aerius.gml.base.characteristics.GML2SourceCharacteristicsV31;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;

/**
 * {@link GMLVersionReader} for AERIUS GML version 1.0.
 */
final class GMLReader implements GMLVersionReader {

  private final GML2Source gml2Source;
  private final GML2Result gml2Result;

  public GMLReader(final GMLConversionData conversionData) {
    gml2Source = new GML2Source(conversionData, new GML2SourceCharacteristicsV31(conversionData));
    gml2Result = new GML2Result(conversionData);
  }

  @Override
  public List<CalculationPointFeature> calculationPointsFromGML(final List<FeatureMember> members, final boolean includeResults) {
    return gml2Result.fromGML(members, includeResults);
  }

  @Override
  public List<EmissionSourceFeature> sourcesFromGML(final List<FeatureMember> members) {
    return gml2Source.fromGML(members);
  }

}
