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

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.ship.v31.IsGmlMooringInlandShipping;
import nl.overheid.aerius.gml.v1_0.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "MooringInlandShippingType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"averageResidenceTime", "routes"})
public class MooringInlandShipping extends AbstractShipping implements IsGmlMooringInlandShipping {

  private int averageResidenceTime;
  private List<InlandShippingRouteProperty> routes = new ArrayList<>();

  @Override
  @XmlElement(name = "averageResidenceTime", namespace = CalculatorSchema.NAMESPACE)
  public int getAverageResidenceTime() {
    return averageResidenceTime;
  }

  public void setAverageResidenceTime(final int averageResidenceTime) {
    this.averageResidenceTime = averageResidenceTime;
  }

  @Override
  @XmlElement(name = "route", namespace = CalculatorSchema.NAMESPACE)
  public List<InlandShippingRouteProperty> getRoutes() {
    return routes;
  }

  public void setRoutes(final List<InlandShippingRouteProperty> routes) {
    this.routes = routes;
  }

}
