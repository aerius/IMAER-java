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
package nl.overheid.aerius.shared.emissions;

public interface IsFarmEmissionFactorTypeObject {

  default Integer getNumberOfAnimals() {
    return null;
  }

  default void setNumberOfAnimals(final Integer numberOfAnimals) {
  }

  default Integer getNumberOfDays() {
    return null;
  }

  default void setNumberOfDays(final Integer numberOfDays) {
  }

  default Integer getNumberOfApplications() {
    return null;
  }

  default void setNumberOfApplications(final Integer numberOfApplications) {
  }

  default Double getMetersCubed() {
    return null;
  }

  default void setMetersCubed(final Double metersCubed) {
  }

  default Integer getTonnes() {
    return null;
  }

  default void setTonnes(final Integer tonnes) {
  }

  default Double getMetersSquared() {
    return null;
  }

  default void setMetersSquared(final Double metersSquared) {
  }

}
