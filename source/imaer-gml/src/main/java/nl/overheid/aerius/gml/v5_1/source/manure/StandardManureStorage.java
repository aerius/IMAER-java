/*
 * Crown copyright
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
package nl.overheid.aerius.gml.v5_1.source.manure;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.manure.IsGmlStandardManureStorage;
import nl.overheid.aerius.gml.v5_1.base.CalculatorSchema;

/**
 *
 */
@XmlRootElement(name = "StandardManureStorage", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "StandardManureStorageType", namespace = CalculatorSchema.NAMESPACE)
public class StandardManureStorage extends AbstractManureStorage implements IsGmlStandardManureStorage {

  private String manureStorageCode;

  @Override
  @XmlAttribute(name = "manureStorageType")
  public String getManureStorageCode() {
    return manureStorageCode;
  }

  public void setManureStorageCode(final String manureStorageCode) {
    this.manureStorageCode = manureStorageCode;
  }

}
