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
package nl.overheid.aerius.gml.v5_0.definitions;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.GMLSchema;
import nl.overheid.aerius.gml.base.characteristics.IsGmlCustomDiurnalVariation;
import nl.overheid.aerius.gml.v5_0.base.CalculatorSchema;

@XmlType(name = "CustomDiurnalVariation", namespace = CalculatorSchema.NAMESPACE, propOrder = {"label", "customType", "values"})
public class CustomDiurnalVariation implements IsGmlCustomDiurnalVariation {

  private String id;
  private String label;
  private String customType;
  private List<Integer> values;

  @Override
  @XmlAttribute(namespace = GMLSchema.NAMESPACE)
  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

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
  public String getCustomType() {
    return customType;
  }

  public void setCustomType(final String customType) {
    this.customType = customType;
  }

  @Override
  @XmlElement(name = "value", namespace = CalculatorSchema.NAMESPACE)
  public List<Integer> getValues() {
    return values;
  }

  public void setValues(final List<Integer> values) {
    this.values = values;
  }

}
