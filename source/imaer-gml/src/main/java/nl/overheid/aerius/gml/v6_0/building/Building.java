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
package nl.overheid.aerius.gml.v6_0.building;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.building.IsGmlBuilding;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v6_0.base.FeatureMemberImpl;
import nl.overheid.aerius.gml.v6_0.geo.BuildingGeometry;
import nl.overheid.aerius.gml.v6_0.geo.EmissionSourceGeometry;
import nl.overheid.aerius.shared.domain.v2.geojson.GeometryType;

/**
 *
 */
@XmlType(name = "BuildingType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"label", "height", "buildingGeometryProperty", "diameter"})
public class Building extends FeatureMemberImpl implements IsGmlBuilding {

  private String label;
  private double height;
  private Double diameter;

  @Override
  public boolean isValidGeometry(final GeometryType type) {
    return type == GeometryType.POINT || type == GeometryType.POLYGON;
  }

  @XmlElement(name = "geometry", namespace = CalculatorSchema.NAMESPACE)
  public BuildingGeometryProperty getBuildingGeometryProperty() {
    final BuildingGeometry geometry = new BuildingGeometry();
    geometry.setPoint(super.getEmissionSourceGeometry().getPoint());
    geometry.setPolygon(super.getEmissionSourceGeometry().getPolygon());
    return new BuildingGeometryProperty(geometry);
  }

  /**
   * Method doesn't actually set the property, but sets the EmissionSourceGeometry of this class based on it.
   * @param buildingGeometryProperty The property to use.
   */
  public void setBuildingGeometryProperty(final BuildingGeometryProperty buildingGeometryProperty) {
    final EmissionSourceGeometry geometry = new EmissionSourceGeometry();
    if (buildingGeometryProperty != null && buildingGeometryProperty.getProperty() != null) {
      geometry.setPoint(buildingGeometryProperty.getProperty().getPoint());
      geometry.setPolygon(buildingGeometryProperty.getProperty().getPolygon());
    }
    super.setEmissionSourceGeometry(geometry);
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getLabel() {
    return label;
  }

  public void setLabel(final String label) {
    this.label = label;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getHeight() {
    return height;
  }

  public void setHeight(final double height) {
    this.height = height;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Double getDiameter() {
    return diameter;
  }

  public void setDiameter(final Double diameter) {
    this.diameter = diameter;
  }

}
