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
package nl.overheid.aerius.gml.base.characteristics;

import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.IsGmlReferenceType;
import nl.overheid.aerius.gml.base.building.IsGmlBuildingV31;
import nl.overheid.aerius.shared.domain.v2.building.Building;
import nl.overheid.aerius.shared.domain.v2.building.BuildingFeature;
import nl.overheid.aerius.shared.domain.v2.characteristics.SourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.geojson.Polygon;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.util.GeometryUtil;
import nl.overheid.aerius.util.gml.GMLIdUtil;

/**
 * Class to convert to and from GML objects (specific for source characteristics).
 */
public abstract class GML2SourceCharacteristics<T extends SourceCharacteristics> {

  private final GMLConversionData conversionData;

  protected GML2SourceCharacteristics(final GMLConversionData conversionData) {
    this.conversionData = conversionData;
  }

  public T fromGML(final IsGmlSourceCharacteristics characteristics, final T sectorCharacteristics,
      final Geometry geometry) throws AeriusException {
    final T returnCharacteristics = fromGMLSpecific(characteristics, sectorCharacteristics, geometry);

    fromGMLToBuildingProperties(characteristics, returnCharacteristics, geometry);
    return returnCharacteristics;
  }

  protected abstract T fromGMLSpecific(final IsGmlSourceCharacteristics characteristics, final T sectorCharacteristics, Geometry geometry)
      throws AeriusException;

  private void fromGMLToBuildingProperties(final IsGmlSourceCharacteristics characteristics,
      final SourceCharacteristics returnCharacteristics, final Geometry geometry) throws AeriusException {
    if (characteristics instanceof IsGmlOPSSourceCharacteristicsV31) {
      final IsGmlOPSSourceCharacteristicsV31 oldCharacteristics = (IsGmlOPSSourceCharacteristicsV31) characteristics;
      fromGMLToBuildingProperties(oldCharacteristics, returnCharacteristics, geometry);
    } else if (characteristics instanceof HasGmlBuildingReference) {
      final HasGmlBuildingReference newCharacteristics = (HasGmlBuildingReference) characteristics;
      fromGMLToBuildingProperties(newCharacteristics, returnCharacteristics);
    }
  }

  private void fromGMLToBuildingProperties(final IsGmlOPSSourceCharacteristicsV31 characteristics,
      final SourceCharacteristics returnCharacteristics, final Geometry geometry) throws AeriusException {
    final IsGmlBuildingV31 gmlBuilding = characteristics.getBuilding();
    if (gmlBuilding != null && conversionData != null && geometry != null) {
      final int buildingIdInt = conversionData.getExtraBuildings().size() + 1;
      final String buildingId = String.valueOf(buildingIdInt);
      final BuildingFeature buildingFeature = new BuildingFeature();
      buildingFeature.setId(buildingId);
      final Polygon polygon = GeometryUtil.constructPolygonFromXAxisDimensions(geometry, gmlBuilding.getLength(), gmlBuilding.getWidth(),
          gmlBuilding.getOrientation());
      buildingFeature.setGeometry(polygon);
      final Building building = new Building();
      final String gmlId = GMLIdUtil.toValidGmlId(buildingId, GMLIdUtil.BUILDING_PREFIX);
      building.setGmlId(gmlId);
      // For backwards compatibility: set some generic label.
      building.setLabel("Gebouw " + buildingIdInt);
      building.setHeight(gmlBuilding.getHeight());
      buildingFeature.setProperties(building);
      conversionData.getExtraBuildings().add(buildingFeature);
      returnCharacteristics.setBuildingId(gmlId);
    }
  }

  private void fromGMLToBuildingProperties(final HasGmlBuildingReference characteristics,
      final SourceCharacteristics returnCharacteristics) {
    final IsGmlReferenceType gmlBuilding = characteristics.getBuilding();
    if (gmlBuilding != null) {
      returnCharacteristics.setBuildingId(gmlBuilding.getReferredId());
    }
  }
}
