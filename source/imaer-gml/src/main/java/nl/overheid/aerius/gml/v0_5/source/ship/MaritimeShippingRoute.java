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

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.IsGmlTimeUnit;
import nl.overheid.aerius.gml.base.source.ship.v31.IsGmlMaritimeShippingRoute;
import nl.overheid.aerius.gml.v0_5.base.CalculatorSchema;
import nl.overheid.aerius.gml.v0_5.geo.LineString;
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;

/**
 *
 */
@XmlType(name = "maritimeShippingRouteType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"shippingMovements", "route"})
public class MaritimeShippingRoute implements IsGmlMaritimeShippingRoute {

  private int shippingMovements;
  private LineString route;

  @XmlElement(name = "shippingMovements", namespace = CalculatorSchema.NAMESPACE)
  public int getShippingMovements() {
    return shippingMovements;
  }

  public void setShippingMovements(final int shippingMovements) {
    this.shippingMovements = shippingMovements;
  }

  @Override
  @XmlElement(name = "route", namespace = CalculatorSchema.NAMESPACE)
  public LineString getRoute() {
    return route;
  }

  public void setRoute(final LineString route) {
    this.route = route;
  }

  @XmlTransient
  @Override
  public int getShippingMovementsPerTimeUnit() {
    return shippingMovements;
  }

  @XmlTransient
  @Override
  public IsGmlTimeUnit getTimeUnit() {
    // Values in this version were per year by definition
    return TimeUnit.YEAR::name;
  }

}
