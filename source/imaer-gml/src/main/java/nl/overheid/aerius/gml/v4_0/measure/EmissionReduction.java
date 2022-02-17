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
package nl.overheid.aerius.gml.v4_0.measure;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.measure.IsGMLEmissionReduction;
import nl.overheid.aerius.gml.v4_0.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.Substance;

/**
 *
 */
@XmlType(name = "EmissionReductionType", namespace = CalculatorSchema.NAMESPACE)
public class EmissionReduction implements IsGMLEmissionReduction {

  private Substance substance;
  private double factor;

  @Override
  @XmlAttribute
  public Substance getSubstance() {
    return substance;
  }

  public void setSubstance(final Substance substance) {
    this.substance = substance;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getFactor() {
    return factor;
  }

  public void setFactor(final double factor) {
    this.factor = factor;
  }

}
