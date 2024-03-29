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
package nl.overheid.aerius.shared.domain.v2.source.farmland;

import nl.overheid.aerius.shared.emissions.IsFarmEmissionFactorTypeObject;
import nl.overheid.aerius.shared.exception.AeriusException;

public class StandardFarmlandActivity extends AbstractFarmlandActivity implements IsFarmEmissionFactorTypeObject {

  private static final long serialVersionUID = 1L;
  private String farmSourceCategoryCode;
  private Integer numberOfAnimals;
  private Integer numberOfDays;
  private Double tonnes;
  private Double metersCubed;
  private Integer numberOfApplications;

  public String getFarmSourceCategoryCode() {
    return farmSourceCategoryCode;
  }

  public void setFarmSourceCategoryCode(final String farmSourceCategoryCode) {
    this.farmSourceCategoryCode = farmSourceCategoryCode;
  }

  @Override
  public Integer getNumberOfAnimals() {
    return numberOfAnimals;
  }

  @Override
  public void setNumberOfAnimals(final Integer numberOfAnimals) {
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

  @Override
  public Double getTonnes() {
    return tonnes;
  }

  @Override
  public void setTonnes(final Double tonnes) {
    this.tonnes = tonnes;
  }

  @Override
  public Double getMetersCubed() {
    return metersCubed;
  }

  @Override
  public void setMetersCubed(final Double metersCubed) {
    this.metersCubed = metersCubed;
  }

  @Override
  public Integer getNumberOfApplications() {
    return numberOfApplications;
  }

  @Override
  public void setNumberOfApplications(final Integer numberOfApplications) {
    this.numberOfApplications = numberOfApplications;
  }

  @Override
  public <T> T accept(final FarmlandActivityVisitor<T> visitor) throws AeriusException {
    return visitor.visit(this);
  }
}
