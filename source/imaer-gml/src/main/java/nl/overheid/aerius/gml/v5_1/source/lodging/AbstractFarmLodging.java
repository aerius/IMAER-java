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
package nl.overheid.aerius.gml.v5_1.source.lodging;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.lodging.IsGmlFarmLodging;
import nl.overheid.aerius.gml.v5_1.base.CalculatorSchema;

/**
 *
 */
@XmlSeeAlso({FarmLodging.class, CustomFarmLodging.class})
@XmlType(name = "FarmLodgingType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"numberOfAnimals", "numberOfDays"})
public class AbstractFarmLodging implements IsGmlFarmLodging {

  private int numberOfAnimals;
  private Integer numberOfDays;

  @Override
  @XmlElement(name = "numberOfAnimals", namespace = CalculatorSchema.NAMESPACE)
  public int getNumberOfAnimals() {
    return numberOfAnimals;
  }

  public void setNumberOfAnimals(final int numberOfAnimals) {
    this.numberOfAnimals = numberOfAnimals;
  }

  @Override
  @XmlElement(name = "numberOfDays", namespace = CalculatorSchema.NAMESPACE)
  public Integer getNumberOfDays() {
    return numberOfDays;
  }

  public void setNumberOfDays(final Integer numberOfDays) {
    this.numberOfDays = numberOfDays;
  }

}
