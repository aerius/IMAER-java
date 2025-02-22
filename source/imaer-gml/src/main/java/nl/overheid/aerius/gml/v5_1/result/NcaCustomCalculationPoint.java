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
package nl.overheid.aerius.gml.v5_1.result;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.result.IsGmlEntityReference;
import nl.overheid.aerius.gml.base.result.IsGmlNcaCustomCalculationPoint;
import nl.overheid.aerius.gml.v5_1.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "NcaCustomCalculationPointType", namespace = CalculatorSchema.NAMESPACE)
public class NcaCustomCalculationPoint extends AbstractCalculationPoint implements IsGmlNcaCustomCalculationPoint {

  private Double roadLocalFractionNO2;

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Double getRoadLocalFractionNO2() {
    return roadLocalFractionNO2;
  }

  public void setRoadLocalFractionNO2(final Double roadLocalFractionNO2) {
    this.roadLocalFractionNO2 = roadLocalFractionNO2;
  }

  @Override
  public List<? extends IsGmlProperty<IsGmlEntityReference>> getEntityReferences() {
    return null;
  }

}
