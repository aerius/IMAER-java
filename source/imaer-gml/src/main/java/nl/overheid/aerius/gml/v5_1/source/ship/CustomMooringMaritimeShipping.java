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
package nl.overheid.aerius.gml.v5_1.source.ship;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.ship.IsGmlCustomMooringMaritimeShipping;
import nl.overheid.aerius.gml.v5_1.base.CalculatorSchema;

@XmlRootElement(name = "CustomMooringMaritimeShipping", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "CustomMooringMaritimeShippingType", namespace = CalculatorSchema.NAMESPACE, propOrder = {
    "emissionProperties"})
public class CustomMooringMaritimeShipping extends MooringMaritimeShipping implements IsGmlCustomMooringMaritimeShipping {

  private CustomMaritimeShippingEmissionPropertiesProperty emissionProperties;

  @Override
  @XmlElement(name = "emissionProperties", namespace = CalculatorSchema.NAMESPACE)
  public CustomMaritimeShippingEmissionPropertiesProperty getEmissionProperties() {
    return emissionProperties;
  }

  public void setEmissionProperties(final CustomMaritimeShippingEmissionPropertiesProperty emissionProperties) {
    this.emissionProperties = emissionProperties;
  }

}
