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

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.source.base.AbstractSubSource;

@JsonTypeInfo(property = "vehicleType", use = Id.NAME)
@JsonSubTypes({
    @Type(value = CustomVehicles.class, name = "CUSTOM"),
    @Type(value = SpecificVehicles.class, name = "SPECIFIC"),
    @Type(value = StandardVehicles.class, name = "STANDARD"),
    @Type(value = StandardColdStartVehicles.class, name = "STANDARD_CS"),
})
public abstract class Vehicles extends AbstractSubSource {

  private static final long serialVersionUID = 3L;

  private TimeUnit timeUnit = TimeUnit.DAY;

  public TimeUnit getTimeUnit() {
    return timeUnit;
  }

  public void setTimeUnit(final TimeUnit timeUnit) {
    this.timeUnit = timeUnit;
  }

}
