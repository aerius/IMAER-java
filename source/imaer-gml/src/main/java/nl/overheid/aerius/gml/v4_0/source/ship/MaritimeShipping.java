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
package nl.overheid.aerius.gml.v4_0.source.ship;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.ship.IsGmlMaritimeShipping;
import nl.overheid.aerius.gml.v4_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v4_0.source.TimeUnit;

/**
 *
 */
@XmlSeeAlso({StandardMaritimeShipping.class, CustomMaritimeShipping.class})
@XmlType(name = "MaritimeShippingType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"description", "shipsPerTimeUnit", "timeUnit"})
public abstract class MaritimeShipping implements IsGmlMaritimeShipping {

  private String description;
  private int shipsPerTimeUnit;
  private TimeUnit timeUnit;

  @Override
  @XmlElement(name = "description", namespace = CalculatorSchema.NAMESPACE)
  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  @Override
  @XmlElement(name = "shipsPerTimeUnit", namespace = CalculatorSchema.NAMESPACE)
  public int getShipsPerTimeUnit() {
    return shipsPerTimeUnit;
  }

  public void setShipsPerTimeUnit(final int shipsPerTimeUnit) {
    this.shipsPerTimeUnit = shipsPerTimeUnit;
  }

  @Override
  @XmlElement(name = "timeUnit", namespace = CalculatorSchema.NAMESPACE)
  public TimeUnit getTimeUnit() {
    return timeUnit;
  }

  public void setTimeUnit(final TimeUnit timeUnit) {
    this.timeUnit = timeUnit;
  }

}
