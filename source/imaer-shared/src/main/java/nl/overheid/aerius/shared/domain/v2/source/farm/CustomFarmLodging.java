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

import java.util.HashMap;
import java.util.Map;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.emissions.FarmEmissionFactorType;

/**
 * @Deprecated Replaced by Animal Housing approach
 */
@Deprecated(forRemoval = true)
public class CustomFarmLodging extends FarmLodging {

  private static final long serialVersionUID = 2L;

  private String animalCode;
  private String description;
  private FarmEmissionFactorType farmEmissionFactorType;
  private final Map<Substance, Double> emissionFactors = new HashMap<>();

  public String getAnimalCode() {
    return animalCode;
  }

  public void setAnimalCode(final String animalCode) {
    this.animalCode = animalCode;
  }

  public String getDescription() {
    return description;
  }

  public FarmEmissionFactorType getFarmEmissionFactorType() {
    return farmEmissionFactorType;
  }

  public void setFarmEmissionFactorType(final FarmEmissionFactorType farmEmissionFactorType) {
    this.farmEmissionFactorType = farmEmissionFactorType;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public Map<Substance, Double> getEmissionFactors() {
    return emissionFactors;
  }
}
