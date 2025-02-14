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
package nl.overheid.aerius.gml.v6_0.result;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.AbstractProperty;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.result.IsGmlCriticalLevel;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "CriticalLevelPropertyType", namespace = CalculatorSchema.NAMESPACE)
public class CriticalLevelProperty extends AbstractProperty<CriticalLevel> implements IsGmlProperty<IsGmlCriticalLevel> {

  /**
   * Default constructor, needed for JAXB.
   */
  public CriticalLevelProperty() {
    super(null);
  }

  /**
   * Convenience constructor.
   * @param criticalLevel The property to use.
   */
  public CriticalLevelProperty(final CriticalLevel criticalLevel) {
    super(criticalLevel);
  }

  @Override
  @XmlElement(name = "CriticalLevel", namespace = CalculatorSchema.NAMESPACE)
  public CriticalLevel getProperty() {
    return super.getProperty();
  }

  @Override
  public void setProperty(final CriticalLevel property) {
    super.setProperty(property);
  }

}
