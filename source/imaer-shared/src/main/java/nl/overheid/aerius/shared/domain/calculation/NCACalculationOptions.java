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
package nl.overheid.aerius.shared.domain.calculation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Calculation options related to the NCA theme.
 */
public class NCACalculationOptions implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Regional area for which a permit calculation would apply to.
   */
  private String permitArea;

  /**
   * Location of the meteorological site location.
   */
  private String meteoSiteLocation;

  /**
   * List of meteo years to calculate.
   */
  private List<String> meteoYears = new ArrayList<String>();

  /**
   * Additional ADMS options.
   */
  private ADMSOptions admsOptions = new ADMSOptions();

  public String getPermitArea() {
    return permitArea;
  }

  public void setPermitArea(final String permitArea) {
    this.permitArea = permitArea;
  }

  public String getMeteoSiteLocation() {
    return meteoSiteLocation;
  }

  public void setMeteoSiteLocation(final String meteoSiteLocation) {
    this.meteoSiteLocation = meteoSiteLocation;
  }

  public List<String> getMeteoYears() {
    return meteoYears;
  }

  public void setMeteoYears(final List<String> meteoYears) {
    this.meteoYears = meteoYears;
  }

  public ADMSOptions getAdmsOptions() {
    return admsOptions;
  }

  public void setAdmsOptions(final ADMSOptions admsOptions) {
    this.admsOptions = admsOptions;
  }
}
