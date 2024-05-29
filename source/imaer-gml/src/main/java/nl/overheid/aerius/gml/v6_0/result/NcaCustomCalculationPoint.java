/*
 * Crown copyright
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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.result.IsGmlNcaCustomCalculationPoint;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "NcaCustomCalculationPointType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"roadLocalFractionNO2", "entityReferences"})
public class NcaCustomCalculationPoint extends AbstractCalculationPoint implements IsGmlNcaCustomCalculationPoint {

  private Double roadLocalFractionNO2;
  private List<EntityReferenceProperty> entityReferences = new ArrayList<>();

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Double getRoadLocalFractionNO2() {
    return roadLocalFractionNO2;
  }

  public void setRoadLocalFractionNO2(final Double roadLocalFractionNO2) {
    this.roadLocalFractionNO2 = roadLocalFractionNO2;
  }

  @Override
  @XmlElement(name = "entityReference", namespace = CalculatorSchema.NAMESPACE)
  public List<EntityReferenceProperty> getEntityReferences() {
    return entityReferences;
  }

  public void setEntityReferences(final List<EntityReferenceProperty> entityReferences) {
    this.entityReferences = entityReferences;
  }

}
