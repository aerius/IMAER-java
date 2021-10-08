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
package nl.overheid.aerius.gml.v2_2.base;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.AbstractProperty;

/**
 *
 */
@XmlType(name = "NEN3610IDPropertyType", namespace = CalculatorSchema.NAMESPACE)
public class NEN3610IDProperty extends AbstractProperty<NEN3610ID> {

  /**
   * Default constructor, needed for JAXB.
   */
  public NEN3610IDProperty() {
    super(null);
  }

  /**
   * Convenience constructor.
   * @param nen3610ID The property to use.
   */
  public NEN3610IDProperty(final NEN3610ID nen3610ID) {
    super(nen3610ID);
  }

  @Override
  @XmlElement(name = "NEN3610ID", namespace = CalculatorSchema.NAMESPACE)
  public NEN3610ID getProperty() {
    return super.getProperty();
  }

  @Override
  public void setProperty(final NEN3610ID property) {
    super.setProperty(property);
  }

}
