/*
 * Crown copyright
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
package nl.overheid.aerius.shared.domain.v2.source.manure;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import nl.overheid.aerius.shared.domain.v2.source.base.AbstractSubSource;
import nl.overheid.aerius.shared.emissions.IsFarmEmissionFactorTypeObject;

@JsonTypeInfo(property = "manureStorageType", use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
    @JsonSubTypes.Type(value = CustomManureStorage.class, name = "CUSTOM"),
    @JsonSubTypes.Type(value = StandardManureStorage.class, name = "STANDARD"),
})
public abstract class AbstractManureStorage extends AbstractSubSource implements IsFarmEmissionFactorTypeObject {

  private static final long serialVersionUID = 2L;

  private Double tonnes;
  private Double metersSquared;
  private Integer numberOfDays;

  @Override
  public Double getTonnes() {
    return tonnes;
  }

  @Override
  public void setTonnes(final Double tonnes) {
    this.tonnes = tonnes;
  }

  @Override
  public Double getMetersSquared() {
    return metersSquared;
  }

  @Override
  public void setMetersSquared(final Double metersSquared) {
    this.metersSquared = metersSquared;
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
