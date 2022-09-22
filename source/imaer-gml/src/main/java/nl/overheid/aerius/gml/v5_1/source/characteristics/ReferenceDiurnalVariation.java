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
package nl.overheid.aerius.gml.v5_1.source.characteristics;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.characteristics.IsGmlReferenceDiurnalVariation;
import nl.overheid.aerius.gml.v5_1.base.CalculatorSchema;
import nl.overheid.aerius.gml.v5_1.base.ReferenceType;

@XmlRootElement(name = "ReferenceDiurnalVariation", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "ReferenceDiurnalVariationType", namespace = CalculatorSchema.NAMESPACE)
public class ReferenceDiurnalVariation extends AbstractDiurnalVariation implements IsGmlReferenceDiurnalVariation {

  private ReferenceType customDiurnalVariation;

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public ReferenceType getCustomDiurnalVariation() {
    return customDiurnalVariation;
  }

  public void setCustomDiurnalVariation(final ReferenceType customDiurnalVariation) {
    this.customDiurnalVariation = customDiurnalVariation;
  }

}
