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
package nl.overheid.aerius.gml.v1_1.source.ship;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.IsGmlTimeUnit;
import nl.overheid.aerius.gml.base.source.ship.v31.IsGmlInlandShippingRoute;
import nl.overheid.aerius.gml.v1_1.base.CalculatorSchema;
import nl.overheid.aerius.gml.v1_1.geo.LineString;
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.MooringRouteDirection;

/**
 *
 */
@XmlType(name = "inlandShippingRouteType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"direction", "percentageLaden", "route",
    "shippingMovements", "inlandWaterwayProperty"})
public class InlandShippingRoute implements IsGmlInlandShippingRoute {

  private int shippingMovements;
  private int percentageLaden;
  private MooringRouteDirection direction;
  private LineString route;
  private InlandWaterwayProperty inlandWaterwayProperty;

  @XmlElement(name = "shippingMovements", namespace = CalculatorSchema.NAMESPACE)
  public int getShippingMovements() {
    return shippingMovements;
  }

  public void setShippingMovements(final int shippingMovements) {
    this.shippingMovements = shippingMovements;
  }

  @Override
  @XmlElement(name = "percentageLaden", namespace = CalculatorSchema.NAMESPACE)
  public int getPercentageLaden() {
    return percentageLaden;
  }

  public void setPercentageLaden(final int percentageLaden) {
    this.percentageLaden = percentageLaden;
  }

  @Override
  @XmlElement(name = "direction", namespace = CalculatorSchema.NAMESPACE)
  public MooringRouteDirection getDirection() {
    return direction;
  }

  public void setDirection(final MooringRouteDirection direction) {
    this.direction = direction;
  }

  @Override
  @XmlElement(name = "route", namespace = CalculatorSchema.NAMESPACE)
  public LineString getRoute() {
    return route;
  }

  public void setRoute(final LineString route) {
    this.route = route;
  }

  @Override
  @XmlElement(name = "waterway", namespace = CalculatorSchema.NAMESPACE)
  public InlandWaterwayProperty getInlandWaterwayProperty() {
    return inlandWaterwayProperty;
  }

  public void setInlandWaterwayProperty(final InlandWaterwayProperty inlandWaterwayProperty) {
    this.inlandWaterwayProperty = inlandWaterwayProperty;
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
