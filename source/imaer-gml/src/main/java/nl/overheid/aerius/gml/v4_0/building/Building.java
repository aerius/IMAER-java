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
package nl.overheid.aerius.gml.v4_0.building;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.building.IsGmlBuilding;
import nl.overheid.aerius.gml.v4_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v4_0.base.FeatureMemberImpl;
import nl.overheid.aerius.gml.v4_0.geo.Polygon;
import nl.overheid.aerius.shared.domain.v2.geojson.GeometryType;

/**
 *
 */
@XmlType(name = "BuildingType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"label", "height", "geometry"})
public class Building extends FeatureMemberImpl implements IsGmlBuilding {

  private String label;
  private double height;

  @Override
  public boolean isValidGeometry(final GeometryType type) {
    return type == GeometryType.POLYGON;
  }

  @XmlElement(name = "geometry", namespace = CalculatorSchema.NAMESPACE)
  public Polygon getGeometry() {
    return super.getEmissionSourceGeometry().getPolygon();
  }

  /**
   * @param polygon The polygon to set.
   */
  public void setGeometry(final Polygon polygon) {
    super.getEmissionSourceGeometry().setPolygon(polygon);
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

  @XmlTransient
  @Override
  public Double getDiameter() {
    // Not available for this version
    return null;
  }

}
