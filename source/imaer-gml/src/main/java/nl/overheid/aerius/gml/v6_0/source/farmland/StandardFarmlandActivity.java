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
package nl.overheid.aerius.gml.v6_0.source.farmland;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.farmland.IsGmlStandardFarmlandActivity;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;

/**
 *
 */
@XmlRootElement(name = "StandardFarmlandActivity", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "StandardFarmlandActivityType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"numberOfAnimals", "numberOfDays",
    "tonnes", "metersCubed", "numberOfApplications"})
public class StandardFarmlandActivity extends AbstractFarmlandActivity implements IsGmlStandardFarmlandActivity {

  private String standardActivityCode;
  private Integer numberOfAnimals;
  private Integer numberOfDays;
  private Double tonnes;
  private Double metersCubed;
  private Integer numberOfApplications;

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
  public Double getMetersCubed() {
    return metersCubed;
  }

  public void setMetersCubed(final Double metersCubed) {
    this.metersCubed = metersCubed;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Integer getNumberOfApplications() {
    return numberOfApplications;
  }

  public void setNumberOfApplications(final Integer numberOfApplications) {
    this.numberOfApplications = numberOfApplications;
  }

}
