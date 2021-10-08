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
package nl.overheid.aerius.gml.v4_0.source.lodging;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.lodging.IsGmlAdditionalLodgingSystem;
import nl.overheid.aerius.gml.v4_0.base.CalculatorSchema;

/**
 *
 */
@XmlRootElement(name = "AdditionalLodgingSystem", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "AdditionalLodgingSystemType", namespace = CalculatorSchema.NAMESPACE)
public class AdditionalLodgingSystem extends AbstractLodgingSystem implements IsGmlAdditionalLodgingSystem {

  private int numberOfAnimals;

  @Override
  @XmlElement(name = "numberOfAnimals", namespace = CalculatorSchema.NAMESPACE)
  public int getNumberOfAnimals() {
    return numberOfAnimals;
  }

  public void setNumberOfAnimals(final int numberOfAnimals) {
    this.numberOfAnimals = numberOfAnimals;
  }
}
