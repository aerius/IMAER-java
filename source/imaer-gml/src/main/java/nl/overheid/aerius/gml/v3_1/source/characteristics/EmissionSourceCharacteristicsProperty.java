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
package nl.overheid.aerius.gml.v3_1.source.characteristics;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.AbstractProperty;
import nl.overheid.aerius.gml.v3_1.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "EmissionSourceCharacteristicsPropertyType", namespace = CalculatorSchema.NAMESPACE)
public class EmissionSourceCharacteristicsProperty extends AbstractProperty<EmissionSourceCharacteristics> {

  /**
   * Default constructor, needed for JAXB.
   */
  public EmissionSourceCharacteristicsProperty() {
    super(null);
  }

  /**
   * Convenience constructor.
   * @param emissionSourceCharacteristics The property to use.
   */
  public EmissionSourceCharacteristicsProperty(final EmissionSourceCharacteristics emissionSourceCharacteristics) {
    super(emissionSourceCharacteristics);
  }

  @Override
  @XmlElement(name = "EmissionSourceCharacteristics", namespace = CalculatorSchema.NAMESPACE)
  public EmissionSourceCharacteristics getProperty() {
    return super.getProperty();
  }

  @Override
  public void setProperty(final EmissionSourceCharacteristics property) {
    super.setProperty(property);
  }

}
