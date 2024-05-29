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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.result.IsGmlCriticalLevel;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.result.EmissionResultType;

/**
 *
 */
@XmlType(name = "CriticalLevelType", namespace = CalculatorSchema.NAMESPACE)
public class CriticalLevel implements IsGmlCriticalLevel {

  private Substance substance;
  private EmissionResultType resultType;
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

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getValue() {
    return value;
  }

  public void setValue(final double value) {
    this.value = value;
  }

}
