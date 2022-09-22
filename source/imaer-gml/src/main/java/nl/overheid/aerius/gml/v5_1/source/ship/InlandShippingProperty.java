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
package nl.overheid.aerius.gml.v5_1.source.ship;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.AbstractProperty;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.source.ship.IsGmlInlandShipping;
import nl.overheid.aerius.gml.v5_1.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "InlandShippingPropertyType", namespace = CalculatorSchema.NAMESPACE)
public class InlandShippingProperty extends AbstractProperty<InlandShipping> implements IsGmlProperty<IsGmlInlandShipping> {

  /**
   * Default constructor, needed for JAXB.
   */
  public InlandShippingProperty() {
    super(null);
  }

  /**
   * Convenience constructor.
   * @param inlandShipping The property to use.
   */
  public InlandShippingProperty(final InlandShipping inlandShipping) {
    super(inlandShipping);
  }

  @Override
  @XmlElementRef
  public InlandShipping getProperty() {
    return super.getProperty();
  }

  @Override
  public void setProperty(final InlandShipping property) {
    super.setProperty(property);
  }

}
