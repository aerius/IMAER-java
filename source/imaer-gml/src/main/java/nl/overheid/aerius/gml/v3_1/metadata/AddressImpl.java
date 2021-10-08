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
package nl.overheid.aerius.gml.v3_1.metadata;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.Address;
import nl.overheid.aerius.gml.v3_1.base.CalculatorSchema;

/**
 * Gml AddressType.
 */
@XmlType(name = "AddressType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"streetAddress", "postcode", "city"})
public class AddressImpl implements Address {

  private String streetAddress;
  private String postcode;
  private String city;

  @Override
  @XmlElement(name = "streetAddress", namespace = CalculatorSchema.NAMESPACE)
  public String getStreetAddress() {
    return streetAddress;
  }

  @Override
  public void setStreetAddress(final String streetAddress) {
    this.streetAddress = streetAddress;
  }

  @Override
  @XmlElement(name = "postcode", namespace = CalculatorSchema.NAMESPACE)
  public String getPostcode() {
    return postcode;
  }

  @Override
  public void setPostcode(final String postcode) {
    this.postcode = postcode;
  }

  @Override
  @XmlElement(name = "city", namespace = CalculatorSchema.NAMESPACE)
  public String getCity() {
    return city;
  }

  @Override
  public void setCity(final String city) {
    this.city = city;
  }
}
