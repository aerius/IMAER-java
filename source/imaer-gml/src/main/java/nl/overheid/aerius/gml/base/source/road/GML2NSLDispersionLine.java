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
package nl.overheid.aerius.gml.base.source.road;

import java.util.ArrayList;
import java.util.List;

import nl.overheid.aerius.gml.base.FeatureMember;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLDispersionLine;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLDispersionLineFeature;

/**
 * Utility class to convert from GML objects (specific for NSL dispersion lines).
 */
public class GML2NSLDispersionLine {

  public List<NSLDispersionLineFeature> fromGML(final List<FeatureMember> members) {
    final List<NSLDispersionLineFeature> dispersionLines = new ArrayList<>();
    for (final FeatureMember member : members) {
      if (member instanceof IsGmlSRM1RoadDispersionLine) {
        dispersionLines.add(fromGML((IsGmlSRM1RoadDispersionLine) member));
      }
    }
    return dispersionLines;
  }

  /**
   * Convert GML road dispersion lines to NSLDispersionLine.
   * @param gmlDispersionLine The GML-representation of a dispersion line.
   * @return The NSL dispersion line.
   */
  public NSLDispersionLineFeature fromGML(final IsGmlSRM1RoadDispersionLine gmlDispersionLine) {
    final NSLDispersionLine dispersionLine = new NSLDispersionLine();
    dispersionLine.setDescription(gmlDispersionLine.getDescription());
    dispersionLine.setLabel(gmlDispersionLine.getLabel());
    dispersionLine.setJurisdictionId(gmlDispersionLine.getJurisdictionId());
    dispersionLine.setRoadProfile(gmlDispersionLine.getRoadProfile());
    dispersionLine.setTreeProfile(gmlDispersionLine.getTreeProfile());

    final String roadId = gmlDispersionLine.getRoad().getReferredId();
    final String calculationPointId = gmlDispersionLine.getCalculationPoint().getReferredId();

    dispersionLine.setRoadGmlId(roadId);
    dispersionLine.setCalculationPointGmlId(calculationPointId);

    final NSLDispersionLineFeature feature = new NSLDispersionLineFeature();
    feature.setProperties(dispersionLine);
    return feature;
  }

}
