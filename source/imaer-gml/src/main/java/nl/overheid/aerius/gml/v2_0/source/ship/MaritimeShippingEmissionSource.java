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
package nl.overheid.aerius.gml.v2_0.source.ship;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.ship.v31.IsGmlMaritimeShippingEmissionSource;
import nl.overheid.aerius.gml.v2_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v2_0.source.EmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.maritime.ShippingMovementType;

/**
 *
 */
@XmlType(name = "MaritimeShippingEmissionSourceType", namespace = CalculatorSchema.NAMESPACE)
public class MaritimeShippingEmissionSource extends EmissionSource implements IsGmlMaritimeShippingEmissionSource {

  private ShippingMovementType movementType;
  private List<MaritimeShippingProperty> maritimeShippings = new ArrayList<>();

  @Override
  @XmlAttribute
  public ShippingMovementType getMovementType() {
    return movementType;
  }

  public void setMovementType(final ShippingMovementType movementType) {
    this.movementType = movementType;
  }

  @Override
  @XmlElement(name = "maritimeShipping", namespace = CalculatorSchema.NAMESPACE)
  public List<MaritimeShippingProperty> getMaritimeShippings() {
    return maritimeShippings;
  }

  public void setMaritimeShippings(final List<MaritimeShippingProperty> maritimeShippings) {
    this.maritimeShippings = maritimeShippings;
  }

}
