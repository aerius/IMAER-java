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
package nl.overheid.aerius.gml.v2_0.metadata;

import java.util.List;
import java.util.Locale;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.IsCalculationMetaData;
import nl.overheid.aerius.gml.v2_0.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.calculation.CalculationType;
import nl.overheid.aerius.shared.domain.result.EmissionResultType;

/**
 *
 */
@XmlType(name = "CalculationMetadataType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"calculationType", "substances", "resultTypes",
    "maximumRange", "researchArea"})
public class CalculationMetadata implements IsCalculationMetaData {

  private CalculationType calculationType;
  private List<Substance> substances;
  private List<EmissionResultType> resultTypes;
  private Double maximumRange;
  private Boolean researchArea;

  @Override
  @XmlElement(name = "type", namespace = CalculatorSchema.NAMESPACE)
  public String getCalculationType() {
    return calculationType.type();
  }

  public void setCalculationType(final String calculationType) {
    this.calculationType = CalculationType.valueOf(calculationType.toUpperCase(Locale.ROOT));
  }

  public void setCalculationType(final CalculationType calculationType) {
    this.calculationType = calculationType;
  }

  @XmlElement(name = "substance", namespace = CalculatorSchema.NAMESPACE)
  public List<Substance> getSubstances() {
    return substances;
  }

  public void setSubstances(final List<Substance> substances) {
    this.substances = substances;
  }

  @XmlElement(name = "resultType", namespace = CalculatorSchema.NAMESPACE)
  public List<EmissionResultType> getResultTypes() {
    return resultTypes;
  }

  public void setResultTypes(final List<EmissionResultType> resultTypes) {
    this.resultTypes = resultTypes;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Double getMaximumRange() {
    return maximumRange;
  }

  public void setMaximumRange(final Double maximumRange) {
    this.maximumRange = maximumRange;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Boolean getResearchArea() {
    return researchArea;
  }

  public void setResearchArea(final Boolean researchArea) {
    this.researchArea = researchArea;
  }
}
