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
package nl.overheid.aerius.gml.v0_5.source;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.AbstractProperty;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.source.IsGmlEmission;
import nl.overheid.aerius.gml.v0_5.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "EmissionPropertyType", namespace = CalculatorSchema.NAMESPACE)
public class EmissionProperty extends AbstractProperty<Emission> implements IsGmlProperty<IsGmlEmission> {

  /**
   * Default constructor, needed for JAXB.
   */
  public EmissionProperty() {
    super(null);
  }

  /**
   * Convenience constructor.
   * @param emission The property to use.
   */
  public EmissionProperty(final Emission emission) {
    super(emission);
  }

  @Override
  @XmlElement(name = "Emission", namespace = CalculatorSchema.NAMESPACE)
  public Emission getProperty() {
    return super.getProperty();
  }

  @Override
  public void setProperty(final Emission property) {
    super.setProperty(property);
  }

}
