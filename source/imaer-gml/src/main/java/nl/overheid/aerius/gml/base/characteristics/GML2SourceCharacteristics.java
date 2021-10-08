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
import nl.overheid.aerius.shared.domain.ops.DiurnalVariation;
import nl.overheid.aerius.shared.domain.v2.building.Building;
import nl.overheid.aerius.shared.domain.v2.building.BuildingFeature;
import nl.overheid.aerius.shared.domain.v2.characteristics.HeatContentType;
import nl.overheid.aerius.shared.domain.v2.characteristics.OPSSourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.geojson.Polygon;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.util.GeometryUtil;
import nl.overheid.aerius.util.gml.GMLIdUtil;

/**
 * Class to convert to and from GML objects (specific for source characteristics).
 */
public class GML2SourceCharacteristics {

  private final GMLConversionData conversionData;

  public GML2SourceCharacteristics(final GMLConversionData conversionData) {
    this.conversionData = conversionData;
  }

  public OPSSourceCharacteristics fromGML(final IsBaseGmlEmissionSourceCharacteristics characteristics,
      final OPSSourceCharacteristics sectorCharacteristics, final Geometry geometry) throws AeriusException {
    final OPSSourceCharacteristics defaultCharacteristics = sectorCharacteristics == null ? new OPSSourceCharacteristics() : sectorCharacteristics;
    final OPSSourceCharacteristics returnCharacteristics = new OPSSourceCharacteristics();
    returnCharacteristics.setEmissionHeight(characteristics.getEmissionHeight());
    returnCharacteristics.setSpread(getOrDefault(characteristics.getSpread(), defaultCharacteristics.getSpread()));
    returnCharacteristics.setParticleSizeDistribution(defaultCharacteristics.getParticleSizeDistribution());

    fromGMLToBuildingProperties(characteristics, returnCharacteristics, defaultCharacteristics, geometry);

    fromGMLToHeatContent(characteristics, returnCharacteristics);

    return returnCharacteristics;
  }

  private void fromGMLToBuildingProperties(final IsBaseGmlEmissionSourceCharacteristics characteristics,
      final OPSSourceCharacteristics returnCharacteristics, final OPSSourceCharacteristics defaultCharacteristics, final Geometry geometry)
      throws AeriusException {
    if (characteristics instanceof IsGmlEmissionSourceCharacteristicsV31) {
      final IsGmlEmissionSourceCharacteristicsV31 oldCharacteristics = (IsGmlEmissionSourceCharacteristicsV31) characteristics;
      fromGMLToBuildingProperties(oldCharacteristics, returnCharacteristics, geometry);
      fromGMLToDiurnalVariation(oldCharacteristics, returnCharacteristics, defaultCharacteristics);
    } else if (characteristics instanceof IsGmlEmissionSourceCharacteristics) {
      final IsGmlEmissionSourceCharacteristics newCharacteristics = (IsGmlEmissionSourceCharacteristics) characteristics;
      fromGMLToBuildingProperties(newCharacteristics, returnCharacteristics);
      fromGMLToDiurnalVariation(newCharacteristics, returnCharacteristics, defaultCharacteristics);
    }
  }

  private void fromGMLToBuildingProperties(final IsGmlEmissionSourceCharacteristicsV31 characteristics,
      final OPSSourceCharacteristics returnCharacteristics, final Geometry geometry) throws AeriusException {
    final IsGmlBuildingV31 gmlBuilding = characteristics.getBuilding();
    if (gmlBuilding != null && conversionData != null && geometry != null) {
      final int buildingIdInt = conversionData.getExtraBuildings().size() + 1;
      final String buildingId = String.valueOf(buildingIdInt);
      final BuildingFeature buildingFeature = new BuildingFeature();
      buildingFeature.setId(buildingId);
      final Polygon polygon = GeometryUtil.constructPolygonFromDimensions(geometry, gmlBuilding.getLength(), gmlBuilding.getWidth(),
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

  private void fromGMLToBuildingProperties(final IsGmlEmissionSourceCharacteristics characteristics,
      final OPSSourceCharacteristics returnCharacteristics) {
    final IsGmlReferenceType gmlBuilding = characteristics.getBuilding();
    if (gmlBuilding != null) {
      returnCharacteristics.setBuildingId(gmlBuilding.getReferredId());
    }
  }

  private void fromGMLToDiurnalVariation(final IsGmlEmissionSourceCharacteristicsV31 characteristics,
      final OPSSourceCharacteristics returnCharacteristics, final OPSSourceCharacteristics defaultCharacteristics) {
    if (characteristics.getDiurnalVariation() == null) {
      returnCharacteristics.setDiurnalVariation(defaultCharacteristics.getDiurnalVariation());
    } else {
      final String diurnalVariationCode = characteristics.getDiurnalVariation();
      returnCharacteristics.setDiurnalVariation(DiurnalVariation.safeValueOf(diurnalVariationCode));
    }
  }

  private void fromGMLToDiurnalVariation(final IsGmlEmissionSourceCharacteristics characteristics,
      final OPSSourceCharacteristics returnCharacteristics, final OPSSourceCharacteristics defaultCharacteristics) {
    if (characteristics.getDiurnalVariation() == null) {
      returnCharacteristics.setDiurnalVariation(defaultCharacteristics.getDiurnalVariation());
    } else if (characteristics.getDiurnalVariation() instanceof IsGmlStandardDiurnalVariation) {
      final String diurnalVariationCode = ((IsGmlStandardDiurnalVariation) characteristics.getDiurnalVariation()).getCode();
      returnCharacteristics.setDiurnalVariation(DiurnalVariation.safeValueOf(diurnalVariationCode));
    } else if (characteristics.getDiurnalVariation() instanceof IsGmlReferenceDiurnalVariation) {
      final IsGmlReferenceDiurnalVariation gmlReferenceDiurnalVariation = (IsGmlReferenceDiurnalVariation) characteristics.getDiurnalVariation();
      returnCharacteristics.setCustomDiurnalVariationId(gmlReferenceDiurnalVariation.getCustomDiurnalVariation().getReferredId());
    }
  }

  private void fromGMLToHeatContent(final IsBaseGmlEmissionSourceCharacteristics characteristics,
      final OPSSourceCharacteristics returnCharacteristics) {
    final IsGmlHeatContent gmlHeatContent = characteristics.getHeatContent();
    if (gmlHeatContent instanceof IsGmlSpecifiedHeatContent) {
      returnCharacteristics.setHeatContentType(HeatContentType.NOT_FORCED);
      returnCharacteristics.setHeatContent(((IsGmlSpecifiedHeatContent) gmlHeatContent).getValue());
    } else if (gmlHeatContent instanceof IsGmlCalculatedHeatContent) {
      returnCharacteristics.setHeatContentType(HeatContentType.FORCED);
      final IsGmlCalculatedHeatContent calculatedHeatContent = (IsGmlCalculatedHeatContent) gmlHeatContent;
      fromGmlToEmissionTemperature(returnCharacteristics, calculatedHeatContent);
      returnCharacteristics.setOutflowDiameter(calculatedHeatContent.getOutflowDiameter());
      returnCharacteristics.setOutflowVelocity(calculatedHeatContent.getOutflowVelocity());
      returnCharacteristics.setOutflowDirection(calculatedHeatContent.getOutflowDirection());
      returnCharacteristics.setOutflowVelocityType(calculatedHeatContent.getOutflowVelocityType());
    }
  }

  protected void fromGmlToEmissionTemperature(final OPSSourceCharacteristics returnCharacteristics, final IsGmlCalculatedHeatContent gmlHeatContent) {
    returnCharacteristics.setEmissionTemperature(gmlHeatContent.getEmissionTemperature());
  }

  private <T extends Number> T getOrDefault(final T value, final T defaultValue) {
    return value == null ? defaultValue : value;
  }
}
