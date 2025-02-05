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
package nl.overheid.aerius.gml.v6_0.result;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.result.IsGmlCalculationPoint;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v6_0.base.FeatureMemberImpl;
import nl.overheid.aerius.gml.v6_0.geo.Point;
import nl.overheid.aerius.gml.v6_0.geo.Polygon;
import nl.overheid.aerius.shared.domain.v2.geojson.GeometryType;

/**
 *
 */
@XmlType(propOrder = {"point", "representation", "results", "label", "description", "jurisdictionId", "corrections",
    "height", "assessmentCategory"})
public abstract class AbstractCalculationPoint extends FeatureMemberImpl implements IsGmlCalculationPoint {

  private List<ResultProperty> results = new ArrayList<>();
  private Polygon representation;
  private String label;
  private String description;
  private Integer jurisdictionId;
  private Double height;
  private String assessmentCategory;
  private List<CalculationPointCorrectionProperty> corrections = new ArrayList<>();

  @Override
  @XmlElement(name = "result", namespace = CalculatorSchema.NAMESPACE)
  public List<ResultProperty> getResults() {
    return results;
  }

  public void setResults(final List<ResultProperty> results) {
    this.results = results;
  }

  @XmlElement(name = "representation", namespace = CalculatorSchema.NAMESPACE)
  public Polygon getRepresentation() {
    return representation;
  }

  public void setRepresentation(final Polygon representation) {
    this.representation = representation;
  }

  @Override
  public boolean isValidGeometry(final GeometryType type) {
    return type == GeometryType.POINT;
  }

  @XmlElement(name = "GM_Point", namespace = CalculatorSchema.NAMESPACE)
  public Point getPoint() {
    return super.getEmissionSourceGeometry().getPoint();
  }

  /**
   * @param point The point to set.
   */
  public void setPoint(final Point point) {
    super.getEmissionSourceGeometry().setPoint(point);
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
  public Double getHeight() {
    return height;
  }

  public void setHeight(final Double height) {
    this.height = height;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getAssessmentCategory() {
    return assessmentCategory;
  }

  public void setAssessmentCategory(final String assessmentCategory) {
    this.assessmentCategory = assessmentCategory;
  }

  @Override
  @XmlElement(name = "correction", namespace = CalculatorSchema.NAMESPACE)
  public List<CalculationPointCorrectionProperty> getCorrections() {
    return corrections;
  }

  public void setCorrections(final List<CalculationPointCorrectionProperty> corrections) {
    this.corrections = corrections;
  }

}
