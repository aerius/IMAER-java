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
package nl.overheid.aerius.gml.v3_0.source.mobile;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.mobile.v31.IsGmlOffRoadMobileEmissionSource;
import nl.overheid.aerius.gml.v3_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v3_0.source.EmissionSource;

/**
 *
 */
@XmlType(name = "OffRoadMobileEmissionSourceType", namespace = CalculatorSchema.NAMESPACE)
public class OffRoadMobileEmissionSource extends EmissionSource implements IsGmlOffRoadMobileEmissionSource {

  private List<OffRoadMobileSourceProperty> offRoadMobileSources = new ArrayList<>();

  @Override
  @XmlElement(name = "offRoadMobileSource", namespace = CalculatorSchema.NAMESPACE)
  public List<OffRoadMobileSourceProperty> getOffRoadMobileSources() {
    return offRoadMobileSources;
  }

  public void setOffRoadMobileSources(final List<OffRoadMobileSourceProperty> offRoadMobileSources) {
    this.offRoadMobileSources = offRoadMobileSources;
  }

}
