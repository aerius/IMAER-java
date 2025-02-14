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
package nl.overheid.aerius.gml.v4_0.source.ship;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.ship.IsGmlStandardMaritimeShipping;
import nl.overheid.aerius.gml.v4_0.base.CalculatorSchema;

@XmlRootElement(name = "StandardMaritimeShipping", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "StandardMaritimeShippingType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"code"})
public class StandardMaritimeShipping extends MaritimeShipping implements IsGmlStandardMaritimeShipping {

  private String code;

  @Override
  @XmlAttribute(name = "shipType")
  public String getCode() {
    return code;
  }

  public void setCode(final String code) {
    this.code = code;
  }

}
