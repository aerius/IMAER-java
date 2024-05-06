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
package nl.overheid.aerius.gml.v6_0.source.housing;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import nl.overheid.aerius.gml.base.LocalDateAdapter;
import nl.overheid.aerius.gml.base.source.housing.IsGmlFarmAnimalHousingSource;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v6_0.source.EmissionSource;

/**
 *
 */
@XmlType(name = "FarmAnimalHousingEmissionSourceType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"farmAnimalHousings", "established"})
public class FarmAnimalHousingEmissionSource extends EmissionSource implements IsGmlFarmAnimalHousingSource {

  private List<FarmAnimalHousingProperty> farmAnimalHousings = new ArrayList<>();
  private LocalDate established;

  @Override
  @XmlElement(name = "animalHousing", namespace = CalculatorSchema.NAMESPACE)
  public List<FarmAnimalHousingProperty> getFarmAnimalHousings() {
    return farmAnimalHousings;
  }

  public void setFarmAnimalHousings(final List<FarmAnimalHousingProperty> farmAnimalHousings) {
    this.farmAnimalHousings = farmAnimalHousings;
  }

  @Override
  @XmlElement(name = "established", namespace = CalculatorSchema.NAMESPACE)
  @XmlJavaTypeAdapter(LocalDateAdapter.class)
  public LocalDate getEstablished() {
    return established;
  }

  public void setEstablished(final LocalDate established) {
    this.established = established;
  }

}
