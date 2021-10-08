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
package nl.overheid.aerius.shared.domain.v2.source.road;

import java.io.Serializable;

public class ValuesPerVehicleType implements Serializable {

  private static final long serialVersionUID = 1L;

  private double vehiclesPerTimeUnit;
  private double stagnationFraction;

  public double getVehiclesPerTimeUnit() {
    return vehiclesPerTimeUnit;
  }

  public void setVehiclesPerTimeUnit(final double vehiclesPerTimeUnit) {
    this.vehiclesPerTimeUnit = vehiclesPerTimeUnit;
  }

  public double getStagnationFraction() {
    return stagnationFraction;
  }

  public void setStagnationFraction(final double stagnationFraction) {
    this.stagnationFraction = stagnationFraction;
  }

}
