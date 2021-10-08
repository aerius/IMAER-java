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
package nl.overheid.aerius.gml.v2_2.collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.AbstractProperty;
import nl.overheid.aerius.gml.v2_2.base.CalculatorSchema;
import nl.overheid.aerius.gml.v2_2.metadata.MetaDataImpl;

/**
 *
 */
@XmlType(name = "AeriusCalculatorMetadataPropertyType", namespace = CalculatorSchema.NAMESPACE)
public class MetadataProperty extends AbstractProperty<MetaDataImpl> {

  /**
   * Default constructor, needed for JAXB.
   */
  public MetadataProperty() {
    super(null);
  }

  /**
   * Convenience constructor.
   * @param metaDataImpl The property to use.
   */
  public MetadataProperty(final MetaDataImpl metaDataImpl) {
    super(metaDataImpl);
  }

  @Override
  @XmlElement(name = "AeriusCalculatorMetadata", namespace = CalculatorSchema.NAMESPACE)
  public MetaDataImpl getProperty() {
    return super.getProperty();
  }

  @Override
  public void setProperty(final MetaDataImpl property) {
    super.setProperty(property);
  }

}
