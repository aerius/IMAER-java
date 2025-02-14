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
package nl.overheid.aerius.gml.v6_0.source.housing;

import jakarta.xml.bind.annotation.XmlElementRef;

import nl.overheid.aerius.gml.base.AbstractProperty;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.source.housing.IsGmlAdditionalHousingSystem;

/**
 *
 */
public class AdditionalHousingSystemProperty extends AbstractProperty<AbstractAdditionalHousingSystem>
    implements IsGmlProperty<IsGmlAdditionalHousingSystem> {

  /**
   * Default constructor, needed for JAXB.
   */
  public AdditionalHousingSystemProperty() {
    super(null);
  }

  /**
   * Convenience constructor.
   * @param additionalSystem The property to use.
   */
  public AdditionalHousingSystemProperty(final AbstractAdditionalHousingSystem additionalSystem) {
    super(additionalSystem);
  }

  @XmlElementRef
  @Override
  public AbstractAdditionalHousingSystem getProperty() {
    return super.getProperty();
  }

  @Override
  public void setProperty(final AbstractAdditionalHousingSystem property) {
    super.setProperty(property);
  }

}
