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
package nl.overheid.aerius.gml.v3_1.result;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.result.IsGmlCalculationPointCorrection;
import nl.overheid.aerius.gml.v3_1.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.result.EmissionResultType;

/**
 *
 */
@XmlType(name = "CalculationPointCorrectionType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"label", "description", "jurisdictionId",
    "substance", "resultType", "value"})
public class CalculationPointCorrection implements IsGmlCalculationPointCorrection {

  private String label;
  private String description;
  private Integer jurisdictionId;
  private Substance substance;
  private EmissionResultType resultType;
  private double value;

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

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getValue() {
    return value;
  }

  public void setValue(final double value) {
    this.value = value;
  }

}
