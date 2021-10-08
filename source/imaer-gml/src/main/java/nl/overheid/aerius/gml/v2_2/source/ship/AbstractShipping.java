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
package nl.overheid.aerius.gml.v2_2.source.ship;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import nl.overheid.aerius.gml.base.source.ship.v31.IsGmlShipping;
import nl.overheid.aerius.gml.v2_2.base.CalculatorSchema;

/**
 *
 */
public abstract class AbstractShipping implements IsGmlShipping {

  private String description;

  private String code;

  @Override
  @XmlElement(name = "description", namespace = CalculatorSchema.NAMESPACE)
  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  @Override
  @XmlAttribute(name = "shipType")
  public String getCode() {
    return code;
  }

  public void setCode(final String code) {
    this.code = code;
  }

}
