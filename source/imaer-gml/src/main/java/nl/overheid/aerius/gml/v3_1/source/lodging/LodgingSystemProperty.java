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
package nl.overheid.aerius.gml.v3_1.source.lodging;

import jakarta.xml.bind.annotation.XmlElementRef;

import nl.overheid.aerius.gml.base.AbstractProperty;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.source.lodging.IsGmlLodgingSystem;

/**
 *
 */
public class LodgingSystemProperty extends AbstractProperty<AbstractLodgingSystem> implements IsGmlProperty<IsGmlLodgingSystem> {

  /**
   * Default constructor, needed for JAXB.
   */
  public LodgingSystemProperty() {
    super(null);
  }

  @XmlElementRef
  @Override
  public AbstractLodgingSystem getProperty() {
    return super.getProperty();
  }

  @Override
  public void setProperty(final AbstractLodgingSystem property) {
    super.setProperty(property);
  }

}
