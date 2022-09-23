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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.lodging.IsGmlCustomFarmLodging;
import nl.overheid.aerius.gml.v5_1.base.CalculatorSchema;
import nl.overheid.aerius.gml.v5_1.source.EmissionProperty;

/**
 *
 */
@XmlRootElement(name = "CustomFarmLodging", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "CustomFarmLodgingType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"animalCode", "description", "emissionFactors"})
public class CustomFarmLodging extends AbstractFarmLodging implements IsGmlCustomFarmLodging {

  private String animalCode;
  private String description;
  private List<EmissionProperty> emissionFactors = new ArrayList<>();

  @Override
  @XmlElement(name = "animalType", namespace = CalculatorSchema.NAMESPACE)
  public String getAnimalCode() {
    return animalCode;
  }

  public void setAnimalCode(final String animalCode) {
    this.animalCode = animalCode;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  @Override
  @XmlElement(name = "emissionFactor", namespace = CalculatorSchema.NAMESPACE)
  public List<EmissionProperty> getEmissionFactors() {
    return emissionFactors;
  }

  public void setEmissionFactors(final List<EmissionProperty> emissionFactors) {
    this.emissionFactors = emissionFactors;
  }

}
