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
package nl.overheid.aerius.gml.v3_0.source.ship;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.ship.v31.IsGmlMaritimeShippingRoute;
import nl.overheid.aerius.gml.v3_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v3_0.geo.LineString;
import nl.overheid.aerius.gml.v3_0.source.TimeUnit;

/**
 *
 */
@XmlType(name = "maritimeShippingRouteType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"shippingMovementsPerTimeUnit", "route",
    "timeUnit"})
public class MaritimeShippingRoute implements IsGmlMaritimeShippingRoute {

  private int shippingMovementsPerTimeUnit;
  private TimeUnit timeUnit;
  private LineString route;

  @Override
  @XmlElement(name = "shippingMovementsPerTimeUnit", namespace = CalculatorSchema.NAMESPACE)
  public int getShippingMovementsPerTimeUnit() {
    return shippingMovementsPerTimeUnit;
  }

  public void setShippingMovementsPerTimeUnit(final int shippingMovementsPerTimeUnit) {
    this.shippingMovementsPerTimeUnit = shippingMovementsPerTimeUnit;
  }

  @Override
  @XmlElement(name = "timeUnit", namespace = CalculatorSchema.NAMESPACE)
  public TimeUnit getTimeUnit() {
    return timeUnit;
  }

  public void setTimeUnit(final TimeUnit timeUnit) {
    this.timeUnit = timeUnit;
  }

  @Override
  @XmlElement(name = "route", namespace = CalculatorSchema.NAMESPACE)
  public LineString getRoute() {
    return route;
  }

  public void setRoute(final LineString route) {
    this.route = route;
  }

}
