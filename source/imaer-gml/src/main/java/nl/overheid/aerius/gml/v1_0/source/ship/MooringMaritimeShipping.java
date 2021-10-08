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
package nl.overheid.aerius.gml.v1_0.source.ship;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.IsGmlTimeUnit;
import nl.overheid.aerius.gml.base.source.ship.v31.IsGmlMooringMaritimeShipping;
import nl.overheid.aerius.gml.v1_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v1_0.geo.LineString;
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;

/**
 *
 */
@XmlType(name = "MooringMaritimeShippingType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"shipsPerYear", "averageResidenceTime",
    "inlandRoute", "maritimeRoutes"})
public class MooringMaritimeShipping extends AbstractShipping implements IsGmlMooringMaritimeShipping {

  private int shipsPerYear;
  private int averageResidenceTime;
  private LineString route;
  private List<MaritimeShippingRouteProperty> maritimeRoutes = new ArrayList<>();

  @XmlElement(name = "shipsPerYear", namespace = CalculatorSchema.NAMESPACE)
  public int getShipsPerYear() {
    return shipsPerYear;
  }

  public void setShipsPerYear(final int shipsPerYear) {
    this.shipsPerYear = shipsPerYear;
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

  @XmlTransient
  @Override
  public int getShipsPerTimeUnit() {
    return shipsPerYear;
  }

  @XmlTransient
  @Override
  public IsGmlTimeUnit getTimeUnit() {
    return TimeUnit.YEAR::name;
  }

}
