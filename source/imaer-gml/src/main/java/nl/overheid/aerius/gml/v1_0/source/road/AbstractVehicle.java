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
package nl.overheid.aerius.gml.v1_0.source.road;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.IsGmlTimeUnit;
import nl.overheid.aerius.gml.base.source.road.IsGmlVehicle;
import nl.overheid.aerius.gml.v1_0.base.CalculatorSchema;
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;

/**
 *
 */
@XmlSeeAlso({StandardVehicle.class, SpecificVehicle.class, CustomVehicle.class})
@XmlType(name = "VehicleType", namespace = CalculatorSchema.NAMESPACE)
public abstract class AbstractVehicle implements IsGmlVehicle {

  private double vehiclesPerDay;

  @XmlElement(name = "vehiclesPerDay", namespace = CalculatorSchema.NAMESPACE)
  public double getVehiclesPerDay() {
    return vehiclesPerDay;
  }

  public void setVehiclesPerDay(final double vehiclePerDay) {
    this.vehiclesPerDay = vehiclePerDay;
  }

  @XmlTransient
  @Override
  public double getVehiclesPerTimeUnit() {
    return vehiclesPerDay;
  }

  @XmlTransient
  @Override
  public IsGmlTimeUnit getTimeUnit() {
    return TimeUnit.DAY::name;
  }

}
