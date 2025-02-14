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
package nl.overheid.aerius.gml.v5_1.metadata;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.v5_1.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.scenario.SituationType;

/**
 *
 */
@XmlType(name = "OtherSituationMetadataType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"situationType", "name", "reference"})
public class OtherSituationMetadata {

  private String name;
  private String reference;
  private SituationType situationType;

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

  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public SituationType getSituationType() {
    return situationType;
  }

  public void setSituationType(final SituationType situationType) {
    this.situationType = situationType;
  }

}
