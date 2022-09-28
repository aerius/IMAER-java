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
package nl.overheid.aerius.gml.v5_1.source.farmland;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.farmland.IsGmlStandardFarmlandActivity;
import nl.overheid.aerius.gml.v5_1.base.CalculatorSchema;

/**
 *
 */
@XmlRootElement(name = "StandardFarmlandActivity", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "StandardFarmlandActivityType", namespace = CalculatorSchema.NAMESPACE)
public class StandardFarmlandActivity extends AbstractFarmlandActivity implements IsGmlStandardFarmlandActivity {

  private String standardActivityCode;
  private Integer numberOfAnimals;
  private Integer numberOfDays;

  @Override
  @XmlAttribute(name = "standardActivityType")
  public String getStandardActivityCode() {
    return standardActivityCode;
  }

  public void setStandardActivityCode(final String standardActivityCode) {
    this.standardActivityCode = standardActivityCode;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Integer getNumberOfAnimals() {
    return numberOfAnimals;
  }

  public void setNumberOfAnimals(final Integer numberOfAnimals) {
    this.numberOfAnimals = numberOfAnimals;
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
