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

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.farmland.IsGmlFarmlandEmissionSource;
import nl.overheid.aerius.gml.v5_1.base.CalculatorSchema;
import nl.overheid.aerius.gml.v5_1.source.EmissionSource;

/**
 *
 */
@XmlType(name = "FarmlandEmissionSourceType", namespace = CalculatorSchema.NAMESPACE)
public class FarmlandEmissionSource extends EmissionSource implements IsGmlFarmlandEmissionSource {

  private List<FarmlandActivityProperty> activities = new ArrayList<>();

  @Override
  @XmlElement(name = "activity", namespace = CalculatorSchema.NAMESPACE)
  public List<FarmlandActivityProperty> getActivities() {
    return activities;
  }

  public void setActivities(final List<FarmlandActivityProperty> activities) {
    this.activities = activities;
  }

}
