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
package nl.overheid.aerius.gml.v5_0.measure;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.measure.v40.IsGmlSRM1RoadMeasureArea;
import nl.overheid.aerius.gml.v5_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v5_0.base.FeatureMemberImpl;
import nl.overheid.aerius.gml.v5_0.geo.Polygon;
import nl.overheid.aerius.shared.domain.v2.geojson.GeometryType;

/**
 *
 */
@XmlType(name = "SRM1RoadMeasureAreaType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"label", "description", "jurisdictionId",
    "measures", "geometry"})
public class SRM1RoadMeasureArea extends FeatureMemberImpl implements IsGmlSRM1RoadMeasureArea {

  private String label;
  private String description;
  private Integer jurisdictionId;
  private List<SRM1RoadMeasureProperty> measures = new ArrayList<>();

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
  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Integer getJurisdictionId() {
    return jurisdictionId;
  }

  public void setJurisdictionId(final Integer jurisdictionId) {
    this.jurisdictionId = jurisdictionId;
  }

  @Override
  @XmlElement(name = "measure", namespace = CalculatorSchema.NAMESPACE)
  public List<SRM1RoadMeasureProperty> getMeasures() {
    return measures;
  }

  public void setMeasures(final List<SRM1RoadMeasureProperty> measures) {
    this.measures = measures;
  }

}
