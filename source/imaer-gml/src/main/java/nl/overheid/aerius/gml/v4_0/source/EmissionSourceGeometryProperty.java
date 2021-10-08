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
package nl.overheid.aerius.gml.v4_0.source;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.AbstractProperty;
import nl.overheid.aerius.gml.base.GMLSchema;
import nl.overheid.aerius.gml.v4_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v4_0.geo.EmissionSourceGeometry;

/**
 *
 */
@XmlType(name = "EmissionGeometryPropertyType", namespace = GMLSchema.NAMESPACE)
public class EmissionSourceGeometryProperty extends AbstractProperty<EmissionSourceGeometry> {

  /**
   * Default constructor, needed for JAXB.
   */
  public EmissionSourceGeometryProperty() {
    super(null);
  }

  /**
   * Convenience constructor.
   * @param emissionSourceGeometry The property to use.
   */
  public EmissionSourceGeometryProperty(final EmissionSourceGeometry emissionSourceGeometry) {
    super(emissionSourceGeometry);
  }

  @Override
  @XmlElement(name = "EmissionSourceGeometry", namespace = CalculatorSchema.NAMESPACE)
  public EmissionSourceGeometry getProperty() {
    return super.getProperty();
  }

  @Override
  public void setProperty(final EmissionSourceGeometry property) {
    super.setProperty(property);
  }

}
