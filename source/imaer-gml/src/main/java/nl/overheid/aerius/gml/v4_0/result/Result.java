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
package nl.overheid.aerius.gml.v4_0.result;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.result.IsGmlResult;
import nl.overheid.aerius.gml.v4_0.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.result.EmissionResultType;

/**
 *
 */
@XmlType(name = "ResultType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"sourceCategory", "value"})
public class Result implements IsGmlResult {

  private Substance substance;
  private EmissionResultType resultType;
  private String sourceCategory;
  private double value;

  @Override
  @XmlAttribute
  public Substance getSubstance() {
    return substance;
  }

  public void setSubstance(final Substance substance) {
    this.substance = substance;
  }

  @Override
  @XmlAttribute
  public EmissionResultType getResultType() {
    return resultType;
  }

  public void setResultType(final EmissionResultType resultType) {
    this.resultType = resultType;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getSourceCategory() {
    return sourceCategory;
  }

  public void setSourceCategory(final String sourceCategory) {
    this.sourceCategory = sourceCategory;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getValue() {
    return value;
  }

  public void setValue(final double value) {
    this.value = value;
  }

}
