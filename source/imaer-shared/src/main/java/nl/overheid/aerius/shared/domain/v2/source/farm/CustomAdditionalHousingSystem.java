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

/**
 * Custom farm additional animal housing system, based on user supplied emission reduction factors.
 */
public class CustomAdditionalHousingSystem extends AdditionalHousingSystem {

  private static final long serialVersionUID = 1L;

  private String description;
  private boolean airScrubber;
  private final Map<Substance, Double> emissionReductionFactors = new HashMap<>();

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public boolean isAirScrubber() {
    return airScrubber;
  }

  public void setAirScrubber(final boolean airScrubber) {
    this.airScrubber = airScrubber;
  }

  public Map<Substance, Double> getEmissionReductionFactors() {
    return emissionReductionFactors;
  }

}
