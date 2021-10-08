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
package nl.overheid.aerius.gml.v3_1.source.ship;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.ship.v31.IsGmlMooringMaritimeShipping;
import nl.overheid.aerius.gml.v3_1.base.CalculatorSchema;
import nl.overheid.aerius.gml.v3_1.geo.LineString;
import nl.overheid.aerius.gml.v3_1.source.TimeUnit;

/**
 *
 */
@XmlType(name = "MooringMaritimeShippingType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"shipsPerTimeUnit", "averageResidenceTime",
    "inlandRoute", "maritimeRoutes", "timeUnit"})
public class MooringMaritimeShipping extends AbstractShipping implements IsGmlMooringMaritimeShipping {

  private int shipsPerTimeUnit;
  private TimeUnit timeUnit;
  private int averageResidenceTime;
  private LineString route;
  private List<MaritimeShippingRouteProperty> maritimeRoutes = new ArrayList<>();

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

  @Override
  @XmlElement(name = "averageResidenceTime", namespace = CalculatorSchema.NAMESPACE)
  public int getAverageResidenceTime() {
    return averageResidenceTime;
  }

  public void setAverageResidenceTime(final int averageResidenceTime) {
    this.averageResidenceTime = averageResidenceTime;
  }

  @Override
  @XmlElement(name = "inlandRoute", namespace = CalculatorSchema.NAMESPACE)
  public LineString getInlandRoute() {
    return route;
  }

  public void setInlandRoute(final LineString route) {
    this.route = route;
  }

  @Override
  @XmlElement(name = "maritimeRoute", namespace = CalculatorSchema.NAMESPACE)
  public List<MaritimeShippingRouteProperty> getMaritimeRoutes() {
    return maritimeRoutes;
  }

  public void setMaritimeRoutes(final List<MaritimeShippingRouteProperty> maritimeRoutes) {
    this.maritimeRoutes = maritimeRoutes;
  }

}
