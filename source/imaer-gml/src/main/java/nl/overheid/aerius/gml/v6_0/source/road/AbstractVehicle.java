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
package nl.overheid.aerius.gml.v6_0.source.road;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.road.IsGmlVehicle;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v6_0.source.TimeUnit;

/**
 *
 */
@XmlSeeAlso({StandardVehicle.class, SpecificVehicle.class, CustomVehicle.class, ColdStartStandardVehicle.class})
@XmlType(name = "VehicleType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"vehiclesPerTimeUnit", "timeUnit"})
public abstract class AbstractVehicle implements IsGmlVehicle {

  private double vehiclesPerTimeUnit;
  private TimeUnit timeUnit;

  @Override
  @XmlElement(name = "vehiclesPerTimeUnit", namespace = CalculatorSchema.NAMESPACE)
  public double getVehiclesPerTimeUnit() {
    return vehiclesPerTimeUnit;
  }

  public void setVehiclesPerTimeUnit(final double vehiclesPerTimeUnit) {
    this.vehiclesPerTimeUnit = vehiclesPerTimeUnit;
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
