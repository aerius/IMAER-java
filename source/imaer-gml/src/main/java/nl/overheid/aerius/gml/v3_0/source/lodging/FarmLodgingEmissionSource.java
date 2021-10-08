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
package nl.overheid.aerius.gml.v3_0.source.lodging;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.lodging.IsGmlFarmLodgingEmissionSource;
import nl.overheid.aerius.gml.v3_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v3_0.source.EmissionSource;

/**
 *
 */
@XmlType(name = "FarmLodgingEmissionSourceType", namespace = CalculatorSchema.NAMESPACE)
public class FarmLodgingEmissionSource extends EmissionSource implements IsGmlFarmLodgingEmissionSource {

  private List<FarmLodgingProperty> farmLodgings = new ArrayList<>();

  @Override
  @XmlElement(name = "farmLodging", namespace = CalculatorSchema.NAMESPACE)
  public List<FarmLodgingProperty> getFarmLodgings() {
    return farmLodgings;
  }

  public void setFarmLodgings(final List<FarmLodgingProperty> farmLodgings) {
    this.farmLodgings = farmLodgings;
  }

  @Override
  public LocalDate getEstablished() {
    // Not available in this version.
    return null;
  }

}
