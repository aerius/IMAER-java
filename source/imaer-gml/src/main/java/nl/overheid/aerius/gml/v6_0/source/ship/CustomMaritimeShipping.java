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
package nl.overheid.aerius.gml.v6_0.source.ship;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.ship.IsGmlCustomMaritimeShipping;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;

@XmlRootElement(name = "CustomMaritimeShipping", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "CustomMaritimeShippingType", namespace = CalculatorSchema.NAMESPACE, propOrder = {
    "emissionProperties", "grossTonnage"})
public class CustomMaritimeShipping extends MaritimeShipping implements IsGmlCustomMaritimeShipping {

  private CustomMaritimeShippingEmissionPropertiesProperty emissionProperties;
  private int grossTonnage;

  @Override
  @XmlElement(name = "emissionProperties", namespace = CalculatorSchema.NAMESPACE)
  public CustomMaritimeShippingEmissionPropertiesProperty getEmissionProperties() {
    return emissionProperties;
  }

  public void setEmissionProperties(final CustomMaritimeShippingEmissionPropertiesProperty emissionProperties) {
    this.emissionProperties = emissionProperties;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public int getGrossTonnage() {
    return grossTonnage;
  }

  public void setGrossTonnage(final int grossTonnage) {
    this.grossTonnage = grossTonnage;
  }

}
