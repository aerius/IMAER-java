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
package nl.overheid.aerius.gml.v2_2.source;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.IsGmlEmission;
import nl.overheid.aerius.gml.v2_2.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.Substance;

/**
 *
 */
@XmlType(name = "EmissionType", namespace = CalculatorSchema.NAMESPACE)
public class Emission implements IsGmlEmission {

  private Substance substance;
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
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getValue() {
    return value;
  }

  public void setValue(final double value) {
    this.value = value;
  }

}
