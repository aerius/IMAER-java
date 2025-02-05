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
package nl.overheid.aerius.gml.v2_0.source.ship;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.IsGmlTimeUnit;
import nl.overheid.aerius.gml.base.source.ship.v31.IsGmlInlandShipping;
import nl.overheid.aerius.gml.v2_0.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;

/**
 *
 */
@XmlType(name = "InlandShipType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"numberOfShipsAtoB", "numberOfShipsBtoA",
    "percentageLadenAtoB", "percentageLadenBtoA"})
public class InlandShipping extends AbstractShipping implements IsGmlInlandShipping {

  // Values in this version were per day by definition
  private static final IsGmlTimeUnit TIME_UNIT = TimeUnit.DAY::name;

  private int numberOfShipsAtoB;
  private int numberOfShipsBtoA;
  private int percentageLadenAtoB;
  private int percentageLadenBtoA;

  @XmlElement(name = "numberOfShipsAtoB", namespace = CalculatorSchema.NAMESPACE)
  public int getNumberOfShipsAtoB() {
    return numberOfShipsAtoB;
  }

  @XmlElement(name = "numberOfShipsBtoA", namespace = CalculatorSchema.NAMESPACE)
  public int getNumberOfShipsBtoA() {
    return numberOfShipsBtoA;
  }

  public void setNumberOfShipsAtoB(final int numberOfShipsAtoB) {
    this.numberOfShipsAtoB = numberOfShipsAtoB;
  }

  public void setNumberOfShipsBtoA(final int numberOfShipsBtoA) {
    this.numberOfShipsBtoA = numberOfShipsBtoA;
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

  @XmlTransient
  @Override
  public int getNumberOfShipsAtoBperTimeUnit() {
    return numberOfShipsAtoB;
  }

  @XmlTransient
  @Override
  public int getNumberOfShipsBtoAperTimeUnit() {
    return numberOfShipsBtoA;
  }

  @XmlTransient
  @Override
  public IsGmlTimeUnit getTimeUnitShipsAtoB() {
    return TIME_UNIT;
  }

  @XmlTransient
  @Override
  public IsGmlTimeUnit getTimeUnitShipsBtoA() {
    return TIME_UNIT;
  }

}
