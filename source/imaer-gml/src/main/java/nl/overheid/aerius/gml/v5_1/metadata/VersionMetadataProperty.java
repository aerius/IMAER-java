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
package nl.overheid.aerius.gml.v5_1.metadata;

import javax.xml.bind.annotation.XmlElement;

import nl.overheid.aerius.gml.base.AbstractProperty;
import nl.overheid.aerius.gml.v5_1.base.CalculatorSchema;

/**
 *
 */
public class VersionMetadataProperty extends AbstractProperty<VersionMetadata> {

  /**
   * Default constructor, needed for JAXB.
   */
  public VersionMetadataProperty() {
    super(null);
  }

  /**
   * Convenience constructor.
   * @param property The property to use.
   */
  public VersionMetadataProperty(final VersionMetadata property) {
    super(property);
  }

  @Override
  @XmlElement(name = "VersionMetadata", namespace = CalculatorSchema.NAMESPACE)
  public VersionMetadata getProperty() {
    return super.getProperty();
  }

  @Override
  public void setProperty(final VersionMetadata property) {
    super.setProperty(property);
  }

}
