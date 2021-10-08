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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.ship.v31.IsGmlInlandWaterway;
import nl.overheid.aerius.gml.v2_0.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.WaterwayDirection;

/**
 *
 */
@XmlType(name = "InlandWaterwayType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"type", "direction"})
public class InlandWaterway implements IsGmlInlandWaterway {

  private String type;
  private WaterwayDirection direction;

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public WaterwayDirection getDirection() {
    return direction;
  }

  public void setDirection(final WaterwayDirection direction) {
    this.direction = direction;
  }

}
