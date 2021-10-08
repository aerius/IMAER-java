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
package nl.overheid.aerius.gml.v1_0.base;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import nl.overheid.aerius.gml.base.FeatureMember;
import nl.overheid.aerius.gml.base.GMLSchema;
import nl.overheid.aerius.gml.v1_0.geo.EmissionSourceGeometry;

/**
 *
 */
public abstract class FeatureMemberImpl implements FeatureMember {

  private String id;

  private EmissionSourceGeometry emissionSourceGeometry = new EmissionSourceGeometry();

  @Override
  @XmlAttribute(namespace = GMLSchema.NAMESPACE)
  public String getId() {
    return id;
  }

  /**
   * @param id The ID to set.
   */
  public void setId(final String id) {
    this.id = id;
    emissionSourceGeometry.setId(id);
  }

  /**
   * @return The identifier property. Convenience implementation for JAXB to generate the right GML.
   */
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public NEN3610IDProperty getIdentifier() {
    return new NEN3610IDProperty(new NEN3610ID(id));
  }

  /**
   * @param nEN3610IDProperty The identifier to set. Won't actually be set, it's not used on import.
   */
  public void setIdentifier(final NEN3610IDProperty nEN3610IDProperty) {
    //no need to set.
  }

  //can be used in class extending this one to set a EmissionSourceGeometryProperty.
  @Override
  @XmlTransient
  public EmissionSourceGeometry getEmissionSourceGeometry() {
    return emissionSourceGeometry;
  }

  public void setEmissionSourceGeometry(final EmissionSourceGeometry emissionSourceGeometry) {
    this.emissionSourceGeometry = emissionSourceGeometry;
  }
}
