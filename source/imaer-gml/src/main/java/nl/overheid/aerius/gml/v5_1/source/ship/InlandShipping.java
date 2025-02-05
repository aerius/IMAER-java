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
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.ship.IsGmlInlandShipping;
import nl.overheid.aerius.gml.v5_1.base.CalculatorSchema;
import nl.overheid.aerius.gml.v5_1.source.TimeUnit;

/**
 *
 */
@XmlSeeAlso({StandardInlandShipping.class, CustomInlandShipping.class})
@XmlType(name = "InlandShippingType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"description",
    "numberOfShipsAtoBperTimeUnit", "numberOfShipsBtoAperTimeUnit",
    "percentageLadenAtoB", "percentageLadenBtoA",
    "timeUnitShipsAtoB", "timeUnitShipsBtoA"})
public abstract class InlandShipping implements IsGmlInlandShipping {

  private String description;
  private int numberOfShipsAtoBperTimeUnit;
  private int numberOfShipsBtoAperTimeUnit;
  private TimeUnit timeUnitShipsAtoB;
  private TimeUnit timeUnitShipsBtoA;
  private int percentageLadenAtoB;
  private int percentageLadenBtoA;

  @Override
  @XmlElement(name = "description", namespace = CalculatorSchema.NAMESPACE)
  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  @Override
  @XmlElement(name = "numberOfShipsAtoBperTimeUnit", namespace = CalculatorSchema.NAMESPACE)
  public int getNumberOfShipsAtoBperTimeUnit() {
    return numberOfShipsAtoBperTimeUnit;
  }

  @Override
  @XmlElement(name = "numberOfShipsBtoAperTimeUnit", namespace = CalculatorSchema.NAMESPACE)
  public int getNumberOfShipsBtoAperTimeUnit() {
    return numberOfShipsBtoAperTimeUnit;
  }

  public void setNumberOfShipsAtoBperTimeUnit(final int numberOfShipsAtoBperTimeUnit) {
    this.numberOfShipsAtoBperTimeUnit = numberOfShipsAtoBperTimeUnit;
  }

  public void setNumberOfShipsBtoAperTimeUnit(final int numberOfShipsBtoAperTimeUnit) {
    this.numberOfShipsBtoAperTimeUnit = numberOfShipsBtoAperTimeUnit;
  }

  @Override
  @XmlElement(name = "timeUnitShipsAtoB", namespace = CalculatorSchema.NAMESPACE)
  public TimeUnit getTimeUnitShipsAtoB() {
    return timeUnitShipsAtoB;
  }

  @Override
  @XmlElement(name = "timeUnitShipsBtoA", namespace = CalculatorSchema.NAMESPACE)
  public TimeUnit getTimeUnitShipsBtoA() {
    return timeUnitShipsBtoA;
  }

  public void setTimeUnitShipsAtoB(final TimeUnit timeUnitShipsAtoB) {
    this.timeUnitShipsAtoB = timeUnitShipsAtoB;
  }

  public void setTimeUnitShipsBtoA(final TimeUnit timeUnitShipsBtoA) {
    this.timeUnitShipsBtoA = timeUnitShipsBtoA;
  }

  @Override
  @XmlElement(name = "percentageLadenAtoB", namespace = CalculatorSchema.NAMESPACE)
  public int getPercentageLadenAtoB() {
    return percentageLadenAtoB;
  }

  @Override
  @XmlElement(name = "percentageLadenBtoA", namespace = CalculatorSchema.NAMESPACE)
  public int getPercentageLadenBtoA() {
    return percentageLadenBtoA;
  }

  public void setPercentageLadenAtoB(final int percentageLadenAtoB) {
    this.percentageLadenAtoB = percentageLadenAtoB;
  }

  public void setPercentageLadenBtoA(final int percentageLadenBtoA) {
    this.percentageLadenBtoA = percentageLadenBtoA;
  }

}
