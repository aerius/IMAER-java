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
package nl.overheid.aerius.gml.v1_1.result;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.result.IsGmlCalculationPoint;
import nl.overheid.aerius.gml.base.result.IsGmlCalculationPointCorrection;
import nl.overheid.aerius.gml.v1_1.base.CalculatorSchema;
import nl.overheid.aerius.gml.v1_1.base.FeatureMemberImpl;
import nl.overheid.aerius.gml.v1_1.geo.Point;
import nl.overheid.aerius.gml.v1_1.geo.Polygon;
import nl.overheid.aerius.shared.domain.v2.geojson.GeometryType;

/**
 *
 */
@XmlType(propOrder = {"point", "representation", "results"})
public abstract class AbstractCalculationPoint extends FeatureMemberImpl implements IsGmlCalculationPoint {

  private List<ResultProperty> results = new ArrayList<>();
  private Polygon representation;

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

  @XmlTransient
  @Override
  public String getLabel() {
    // Not available in this version
    return null;
  }

  @XmlTransient
  @Override
  public String getDescription() {
    // Not available in this version
    return null;
  }

  @XmlTransient
  @Override
  public Integer getJurisdictionId() {
    // Not available in this version
    return null;
  }

  @XmlTransient
  @Override
  public List<? extends IsGmlProperty<IsGmlCalculationPointCorrection>> getCorrections() {
    // Not available in this version
    return List.of();
  }
}
