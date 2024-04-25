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
package nl.overheid.aerius.shared.domain.v2.source.farm;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import nl.overheid.aerius.shared.domain.v2.source.base.AbstractSubSource;
import nl.overheid.aerius.shared.emissions.IsFarmEmissionFactorTypeObject;

/**
 * @Deprecated Replaced by Animal Housing approach
 */
@Deprecated
@JsonTypeInfo(property = "farmLodgingType", use = Id.NAME)
@JsonSubTypes({
    @Type(value = CustomFarmLodging.class, name = "CUSTOM"),
    @Type(value = StandardFarmLodging.class, name = "STANDARD"),
})
public abstract class FarmLodging extends AbstractSubSource implements IsFarmEmissionFactorTypeObject {

  private static final long serialVersionUID = 3L;

  private int numberOfAnimals;
  private Integer numberOfDays;

  @Override
  public Integer getNumberOfAnimals() {
    return numberOfAnimals;
  }

  public void setNumberOfAnimals(final int numberOfAnimals) {
    this.numberOfAnimals = numberOfAnimals;
  }

  @Override
  public Integer getNumberOfDays() {
    return numberOfDays;
  }

  @Override
  public void setNumberOfDays(final Integer numberOfDays) {
    this.numberOfDays = numberOfDays;
  }
}
