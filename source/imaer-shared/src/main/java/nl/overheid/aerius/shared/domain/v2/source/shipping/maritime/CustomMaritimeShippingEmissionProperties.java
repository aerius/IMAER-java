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
package nl.overheid.aerius.shared.domain.v2.source.shipping.maritime;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import nl.overheid.aerius.shared.domain.Substance;

public class CustomMaritimeShippingEmissionProperties implements Serializable {

  private static final long serialVersionUID = 1L;

  private final Map<Substance, Double> emissionFactors = new HashMap<>();
  private double heatContent;
  private double emissionHeight;

  public Map<Substance, Double> getEmissionFactors() {
    return emissionFactors;
  }

  public double getHeatContent() {
    return heatContent;
  }

  public void setHeatContent(final double heatContent) {
    this.heatContent = heatContent;
  }

  public double getEmissionHeight() {
    return emissionHeight;
  }

  public void setEmissionHeight(final double emissionHeight) {
    this.emissionHeight = emissionHeight;
  }

}
