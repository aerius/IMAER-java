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

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.manure.IsGmlManureStorage;
import nl.overheid.aerius.gml.v5_1.base.CalculatorSchema;

/**
 *
 */
@XmlSeeAlso({StandardManureStorage.class, CustomManureStorage.class})
@XmlType(name = "ManureStorageType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"tonnes", "metersSquared", "numberOfDays"})
public abstract class AbstractManureStorage implements IsGmlManureStorage {

  private Double tonnes;
  private Double metersSquared;
  private Integer numberOfDays;

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Double getTonnes() {
    return tonnes;
  }

  public void setTonnes(final Double tonnes) {
    this.tonnes = tonnes;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Double getMetersSquared() {
    return metersSquared;
  }

  public void setMetersSquared(final Double metersSquared) {
    this.metersSquared = metersSquared;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Integer getNumberOfDays() {
    return numberOfDays;
  }

  public void setNumberOfDays(final Integer numberOfDays) {
    this.numberOfDays = numberOfDays;
  }

}
