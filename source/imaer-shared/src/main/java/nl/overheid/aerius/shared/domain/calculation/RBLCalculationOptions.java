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
package nl.overheid.aerius.shared.domain.calculation;

import java.io.Serializable;

/**
 * Calculation options related to the RBL theme.
 */
public class RBLCalculationOptions implements Serializable {

  private static final long serialVersionUID = 1L;

  private int monitorSrm2Year;

  private String preSRMVersion;
  private String luVersion;
  private String depositionVelocityVersion;
  private String windveldenVersion;

  public boolean isIncludeMonitorSrm2Network() {
    return monitorSrm2Year > 0;
  }

  public int getMonitorSrm2Year() {
    return monitorSrm2Year;
  }

  public void setMonitorSrm2Year(final int monitorSrm2Year) {
    this.monitorSrm2Year = monitorSrm2Year;
  }

  public String getPreSRMVersion() {
    return preSRMVersion;
  }

  public void setPreSRMVersion(final String preSRMVersion) {
    this.preSRMVersion = preSRMVersion;
  }

  public String getLuVersion() {
    return luVersion;
  }

  public void setLuVersion(final String luVersion) {
    this.luVersion = luVersion;
  }

  public String getWindveldenVersion() {
    return windveldenVersion;
  }

  public void setWindveldenVersion(final String windveldenVersion) {
    this.windveldenVersion = windveldenVersion;
  }

  public String getDepositionVelocityVersion() {
    return depositionVelocityVersion;
  }

  public void setDepositionVelocityVersion(final String depositionVelocityVersion) {
    this.depositionVelocityVersion = depositionVelocityVersion;
  }
}
