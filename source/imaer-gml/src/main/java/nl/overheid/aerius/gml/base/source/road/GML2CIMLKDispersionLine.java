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
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKDispersionLine;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKDispersionLineFeature;

/**
 * Utility class to convert from GML objects (specific for CIMLK dispersion lines).
 */
public class GML2CIMLKDispersionLine {

  public List<CIMLKDispersionLineFeature> fromGML(final List<FeatureMember> members) {
    final List<CIMLKDispersionLineFeature> dispersionLines = new ArrayList<>();
    for (final FeatureMember member : members) {
      if (member instanceof IsGmlSRM1RoadDispersionLine) {
        dispersionLines.add(fromGML((IsGmlSRM1RoadDispersionLine) member));
      }
    }
    return dispersionLines;
  }

  /**
   * Convert GML road dispersion lines to CIMLKDispersionLine.
   * @param gmlDispersionLine The GML-representation of a dispersion line.
   * @return The CIMLK dispersion line.
   */
  public CIMLKDispersionLineFeature fromGML(final IsGmlSRM1RoadDispersionLine gmlDispersionLine) {
    final CIMLKDispersionLine dispersionLine = new CIMLKDispersionLine();
    dispersionLine.setDescription(gmlDispersionLine.getDescription());
    dispersionLine.setLabel(gmlDispersionLine.getLabel());
    dispersionLine.setJurisdictionId(gmlDispersionLine.getJurisdictionId());
    dispersionLine.setRoadProfile(gmlDispersionLine.getRoadProfile());
    dispersionLine.setTreeProfile(gmlDispersionLine.getTreeProfile());

    final String roadId = gmlDispersionLine.getRoad().getReferredId();
    final String calculationPointId = gmlDispersionLine.getCalculationPoint().getReferredId();

    dispersionLine.setGmlId(roadId);
    dispersionLine.setCalculationPointGmlId(calculationPointId);

    final CIMLKDispersionLineFeature feature = new CIMLKDispersionLineFeature();
    feature.setProperties(dispersionLine);
    return feature;
  }

}
