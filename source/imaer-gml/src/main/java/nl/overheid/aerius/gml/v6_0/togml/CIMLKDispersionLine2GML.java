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
package nl.overheid.aerius.gml.v6_0.togml;

import nl.overheid.aerius.gml.base.geo.Geometry2GML;
import nl.overheid.aerius.gml.v6_0.geo.LineString;
import nl.overheid.aerius.gml.v6_0.source.road.SRM1RoadDispersionLine;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKDispersionLine;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKDispersionLineFeature;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.util.gml.GMLIdUtil;

/**
 *
 */
final class CIMLKDispersionLine2GML {

  private final Geometry2GML geometry2gml;

  public CIMLKDispersionLine2GML(final Geometry2GML geometry2gml) {
    this.geometry2gml = geometry2gml;
  }

  /**
   * Convert a CIMLKDispersionLine to a GML object.
   * @param dispersionLine The dispersionLine to convert to a GML object.
   * @return the GML object representing the CIMLKDispersionLine.
   * @throws AeriusException when the objects could not be converted to GML correctly.
   */
  public SRM1RoadDispersionLine toGML(final CIMLKDispersionLineFeature dispersionLineFeature) throws AeriusException {
    final SRM1RoadDispersionLine gmlDispersionLine = new SRM1RoadDispersionLine();
    final CIMLKDispersionLine dispersionLine = dispersionLineFeature.getProperties();
    final String calculationPointId = GMLIdUtil.toValidGmlId(dispersionLine.getCalculationPointGmlId(), GMLIdUtil.POINT_PREFIX);
    final String roadId = GMLIdUtil.toValidGmlId(dispersionLine.getGmlId(), GMLIdUtil.SOURCE_PREFIX);
    final String id = "DL." + calculationPointId + "." + roadId;

    gmlDispersionLine.setId(id);
    gmlDispersionLine.setDescription(dispersionLine.getDescription());
    gmlDispersionLine.setLabel(dispersionLine.getLabel());
    gmlDispersionLine.setJurisdictionId(dispersionLine.getJurisdictionId());
    gmlDispersionLine.setRoadProfile(dispersionLine.getRoadProfile());
    gmlDispersionLine.setTreeProfile(dispersionLine.getTreeProfile());

    gmlDispersionLine.setRoad(ToGMLUtil.toReferenceType(roadId));
    gmlDispersionLine.setCalculationPoint(ToGMLUtil.toReferenceType(calculationPointId));

    if (dispersionLineFeature.getGeometry() != null) {
      gmlDispersionLine.setLineString(geometry2gml.toXMLLineString(dispersionLineFeature.getGeometry(), new LineString()));
    }

    return gmlDispersionLine;
  }

}
