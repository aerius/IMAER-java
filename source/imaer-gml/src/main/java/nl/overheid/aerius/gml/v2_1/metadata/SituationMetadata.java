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
package nl.overheid.aerius.gml.v2_1.metadata;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.v2_1.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "SituationMetadataType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"name", "reference"})
public class SituationMetadata {

  private String name;
  private String reference;

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getReference() {
    return reference;
  }

  public void setReference(final String reference) {
    this.reference = reference;
  }

}
