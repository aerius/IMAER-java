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
package nl.overheid.aerius.gml.v2_1.source.characteristics;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.AbstractProperty;
import nl.overheid.aerius.gml.v2_1.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "HeatContentSpecificationPropertyType", namespace = CalculatorSchema.NAMESPACE)
public class HeatContentSpecificationProperty extends AbstractProperty<HeatContentSpecification> {

  /**
   * Default constructor, needed for JAXB.
   */
  public HeatContentSpecificationProperty() {
    super(null);
  }

  /**
   * Convenience constructor.
   * @param emissionSourceCharacteristics The property to use.
   */
  public HeatContentSpecificationProperty(final HeatContentSpecification heatContentSpecification) {
    super(heatContentSpecification);
  }

  @Override
  @XmlElement(name = "HeatContentSpecification", namespace = CalculatorSchema.NAMESPACE)
  public HeatContentSpecification getProperty() {
    return super.getProperty();
  }

  @Override
  public void setProperty(final HeatContentSpecification property) {
    super.setProperty(property);
  }

}
