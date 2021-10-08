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
package nl.overheid.aerius.shared.domain.v2.source.shipping.inland;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import nl.overheid.aerius.shared.domain.Substance;

public class CustomInlandShippingEmissionProperties implements Serializable {

  private static final long serialVersionUID = 1L;

  private final Map<Substance, Double> emissionFactorsEmpty = new HashMap<>();
  private final Map<Substance, Double> emissionFactorsLaden = new HashMap<>();
  private double heatContentEmpty;
  private double heatContentLaden;
  private double emissionHeightEmpty;
  private double emissionHeightLaden;

  public Map<Substance, Double> getEmissionFactorsEmpty() {
    return emissionFactorsEmpty;
  }

  public Map<Substance, Double> getEmissionFactorsLaden() {
    return emissionFactorsLaden;
  }

  public double getHeatContentEmpty() {
    return heatContentEmpty;
  }

  public void setHeatContentEmpty(final double heatContentEmpty) {
    this.heatContentEmpty = heatContentEmpty;
  }

  public double getHeatContentLaden() {
    return heatContentLaden;
  }

  public void setHeatContentLaden(final double heatContentLaden) {
    this.heatContentLaden = heatContentLaden;
  }

  public double getEmissionHeightEmpty() {
    return emissionHeightEmpty;
  }

  public void setEmissionHeightEmpty(final double emissionHeightEmpty) {
    this.emissionHeightEmpty = emissionHeightEmpty;
  }

  public double getEmissionHeightLaden() {
    return emissionHeightLaden;
  }

  public void setEmissionHeightLaden(final double emissionHeightLaden) {
    this.emissionHeightLaden = emissionHeightLaden;
  }

}
