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
package nl.overheid.aerius.gml.v6_0.metadata;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.IsCalculationOption;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "CalculationOptionType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"key", "value"})
public class CalculationOption implements IsCalculationOption {

  private String key;
  private String value;

  /**
   * Default constructor, needed for JAXB.
   */
  protected CalculationOption() {
  }

  public CalculationOption(final String key, final String value) {
    super();
    this.key = key;
    this.value = value;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getKey() {
    return key;
  }

  public void setKey(final String key) {
    this.key = key;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getValue() {
    return value;
  }

  public void setValue(final String value) {
    this.value = value;
  }

}
