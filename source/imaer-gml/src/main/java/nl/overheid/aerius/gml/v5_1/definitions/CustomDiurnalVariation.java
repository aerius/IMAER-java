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
package nl.overheid.aerius.gml.v5_1.definitions;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.GMLSchema;
import nl.overheid.aerius.gml.base.characteristics.IsGmlCustomTimeVaryingProfile;
import nl.overheid.aerius.gml.v5_1.base.CalculatorSchema;

@XmlType(name = "CustomDiurnalVariation", namespace = CalculatorSchema.NAMESPACE, propOrder = {"label", "customType", "values"})
public class CustomDiurnalVariation implements IsGmlCustomTimeVaryingProfile {

  private String id;
  private String label;
  private String customType;
  private List<Double> values;

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
  public List<Double> getValues() {
    return values;
  }

  public void setValues(final List<Double> values) {
    this.values = values;
  }

}
