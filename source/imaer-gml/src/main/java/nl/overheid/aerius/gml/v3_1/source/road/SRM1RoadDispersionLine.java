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
package nl.overheid.aerius.gml.v3_1.source.road;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.road.IsGmlSRM1RoadDispersionLine;
import nl.overheid.aerius.gml.v3_1.base.CalculatorSchema;
import nl.overheid.aerius.gml.v3_1.base.FeatureMemberImpl;
import nl.overheid.aerius.gml.v3_1.base.ReferenceType;
import nl.overheid.aerius.gml.v3_1.geo.LineString;
import nl.overheid.aerius.shared.domain.v2.geojson.GeometryType;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLRoadProfile;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLTreeProfile;

/**
 *
 */
@XmlType(name = "SRM1RoadDispersionLineType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"label", "description", "jurisdictionId",
    "roadProfile", "treeProfile", "geometry", "calculationPoint", "road"})
public class SRM1RoadDispersionLine extends FeatureMemberImpl implements IsGmlSRM1RoadDispersionLine{

  private String label;
  private String description;
  private Integer jurisdictionId;
  private NSLRoadProfile roadProfile;
  private NSLTreeProfile treeProfile;
  private ReferenceType road;
  private ReferenceType calculationPoint;

  @Override
  public boolean isValidGeometry(final GeometryType type) {
    return type == GeometryType.LINESTRING;
  }

  @XmlElement(name = "geometry", namespace = CalculatorSchema.NAMESPACE)
  public LineString getGeometry() {
    return super.getEmissionSourceGeometry().getLineString();
  }

  public void setLineString(final LineString lineString) {
    super.getEmissionSourceGeometry().setLineString(lineString);;
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
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public NSLRoadProfile getRoadProfile() {
    return roadProfile;
  }

  public void setRoadProfile(final NSLRoadProfile roadProfile) {
    this.roadProfile = roadProfile;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public NSLTreeProfile getTreeProfile() {
    return treeProfile;
  }

  public void setTreeProfile(final NSLTreeProfile treeProfile) {
    this.treeProfile = treeProfile;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public ReferenceType getRoad() {
    return road;
  }

  public void setRoad(final ReferenceType road) {
    this.road = road;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public ReferenceType getCalculationPoint() {
    return calculationPoint;
  }

  public void setCalculationPoint(final ReferenceType calculationPoint) {
    this.calculationPoint = calculationPoint;
  }

}
