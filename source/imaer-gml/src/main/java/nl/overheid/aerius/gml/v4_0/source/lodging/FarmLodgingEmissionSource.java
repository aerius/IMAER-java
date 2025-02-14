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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import nl.overheid.aerius.gml.base.LocalDateAdapter;
import nl.overheid.aerius.gml.base.source.lodging.IsGmlFarmLodgingEmissionSource;
import nl.overheid.aerius.gml.v4_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v4_0.source.EmissionSource;

/**
 *
 */
@XmlType(name = "FarmLodgingEmissionSourceType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"farmLodgings", "established"})
public class FarmLodgingEmissionSource extends EmissionSource implements IsGmlFarmLodgingEmissionSource {

  private List<FarmLodgingProperty> farmLodgings = new ArrayList<>();
  private LocalDate established;

  @Override
  @XmlElement(name = "farmLodging", namespace = CalculatorSchema.NAMESPACE)
  public List<FarmLodgingProperty> getFarmLodgings() {
    return farmLodgings;
  }

  public void setFarmLodgings(final List<FarmLodgingProperty> farmLodgings) {
    this.farmLodgings = farmLodgings;
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
