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
package nl.overheid.aerius.gml.v0_5.source.ship;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.ship.v31.IsGmlMooringInlandShippingEmissionSource;
import nl.overheid.aerius.gml.v0_5.base.CalculatorSchema;
import nl.overheid.aerius.gml.v0_5.source.EmissionSource;

/**
 *
 */
@XmlType(name = "MooringInlandShippingEmissionSourceType", namespace = CalculatorSchema.NAMESPACE)
public class MooringInlandShippingEmissionSource extends EmissionSource implements IsGmlMooringInlandShippingEmissionSource {

  private List<MooringInlandShippingProperty> mooringInlandShippings = new ArrayList<>();

  @Override
  @XmlElement(name = "mooringInlandShipping", namespace = CalculatorSchema.NAMESPACE)
  public List<MooringInlandShippingProperty> getShips() {
    return mooringInlandShippings;
  }

  public void setShips(final List<MooringInlandShippingProperty> mooringInlandShippings) {
    this.mooringInlandShippings = mooringInlandShippings;
  }
}
