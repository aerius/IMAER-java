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

import nl.overheid.aerius.gml.base.geo.Geometry2GML;
import nl.overheid.aerius.gml.v5_0.building.Building;
import nl.overheid.aerius.shared.domain.v2.building.BuildingFeature;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.util.gml.GMLIdUtil;

/**
 *
 */
final class Building2GML {

  private final Geometry2GML geometry2gml;

  public Building2GML(final Geometry2GML geometry2gml) {
    this.geometry2gml = geometry2gml;
  }

  /**
   * Convert a Building to a GML object.
   * @param building The building to convert to a GML object.
   * @return the GML object representing the Building.
   * @throws AeriusException when the objects could not be converted to GML correctly.
   */
  public Building toGML(final BuildingFeature feature) throws AeriusException {
    final Building gmlBuilding = new Building();
    final nl.overheid.aerius.shared.domain.v2.building.Building building = feature.getProperties();
    final String id = GMLIdUtil.toValidGmlId(building.getGmlId(), GMLIdUtil.BUILDING_PREFIX);

    gmlBuilding.setGeometry(geometry2gml, feature.getGeometry());
    gmlBuilding.setId(id);
    gmlBuilding.setLabel(building.getLabel());
    gmlBuilding.setHeight(building.getHeight());
    gmlBuilding.setDiameter(building.isCircle() ? building.getDiameter() : null);

    return gmlBuilding;
  }

}
