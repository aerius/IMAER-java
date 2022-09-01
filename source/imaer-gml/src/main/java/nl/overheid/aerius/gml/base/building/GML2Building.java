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
package nl.overheid.aerius.gml.base.building;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.overheid.aerius.gml.base.FeatureMember;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.geo.GML2Geometry;
import nl.overheid.aerius.shared.domain.v2.building.Building;
import nl.overheid.aerius.shared.domain.v2.building.BuildingFeature;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.geojson.Polygon;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Utility class to convert from GML objects (specific for Buildings).
 */
public class GML2Building {

  private static final Logger LOG = LoggerFactory.getLogger(GML2Building.class);

  private final GMLConversionData conversionData;
  private final GML2Geometry gml2geometry;

  /**
   * @param conversionData The data to use when converting. Should be filled.
   */
  public GML2Building(final GMLConversionData conversionData) {
    this.conversionData = conversionData;
    this.gml2geometry = new GML2Geometry(conversionData.getSrid());
  }

  public List<BuildingFeature> fromGML(final List<FeatureMember> members) {
    final List<BuildingFeature> measures = new ArrayList<>();
    for (final FeatureMember member : members) {
      if (member instanceof IsGmlBuilding) {
        try {
          measures.add(fromGML((IsGmlBuilding) member));
        } catch (final AeriusException e) {
          conversionData.getErrors().add(e);
        }
      }
    }
    return measures;
  }

  /**
   * Convert GML building to a BuildingFeature.
   * @param gmlBuilding The GML-representation of a building.
   * @return The building feature.
   * @throws AeriusException When an exception occurs during parsing.
   */
  public BuildingFeature fromGML(final IsGmlBuilding gmlBuilding) throws AeriusException {
    final BuildingFeature feature = new BuildingFeature();
    feature.setGeometry(toGeometry(gmlBuilding));
    final Building building = new Building();
    building.setGmlId(gmlBuilding.getId());
    building.setHeight(gmlBuilding.getHeight());
    building.setLabel(gmlBuilding.getLabel());
    if (feature.getGeometry() instanceof Point && gmlBuilding.getDiameter() != null) {
      building.setDiameter(gmlBuilding.getDiameter());
    }
    feature.setProperties(building);
    return feature;
  }

  private Geometry toGeometry(final IsGmlBuilding origin) throws AeriusException {
    try {
      final Geometry geometry = gml2geometry.getGeometry(origin);
      if (!(geometry instanceof Polygon || geometry instanceof Point)) {
        LOG.trace("Unexpected geometry type after conversion ({}), throwing AeriusException", geometry);
        throw new AeriusException(ImaerExceptionReason.GML_GEOMETRY_INVALID, origin.getId());
      }
      return geometry;
    } catch (final AeriusException e) {
      LOG.trace("Geometry exception, rethrown as AeriusException", e);
      throw new AeriusException(ImaerExceptionReason.GML_GEOMETRY_INVALID, origin.getId());
    }
  }

}
