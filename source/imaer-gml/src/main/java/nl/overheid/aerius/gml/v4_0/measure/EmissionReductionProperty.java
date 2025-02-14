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

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.AbstractProperty;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.measure.IsGMLEmissionReduction;
import nl.overheid.aerius.gml.v4_0.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "EmissionReductionPropertyType", namespace = CalculatorSchema.NAMESPACE)
public class EmissionReductionProperty extends AbstractProperty<EmissionReduction> implements IsGmlProperty<IsGMLEmissionReduction> {

  /**
   * Default constructor, needed for JAXB.
   */
  public EmissionReductionProperty() {
    super(null);
  }

  /**
   * Convenience constructor.
   * @param emissionReduction The property to use.
   */
  public EmissionReductionProperty(final EmissionReduction emissionReduction) {
    super(emissionReduction);
  }

  @Override
  @XmlElement(name = "EmissionReduction", namespace = CalculatorSchema.NAMESPACE)
  public EmissionReduction getProperty() {
    return super.getProperty();
  }

  @Override
  public void setProperty(final EmissionReduction property) {
    super.setProperty(property);
  }

}
