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
package nl.overheid.aerius.shared.domain.v2.source.offroad;

/**
 * Standard off road mobile sources can have different ways to calculate emissions.
 * The most common emission calculation methods are related to Stage mobile equipment.
 * For stage classes there are 2 methods:
 *
 * <ol>
 * <li>Adblue(adblue) Uren(operation hours) Brandstof(fuel) or AUB-method.
 * <li>Uren(operation hours) or U-method, which is based on machine power(kW).
 * </ol>
 *
 * The convention for calculating emissions for this object is as follows for off road categories that support both power and fuel bases emissions.
 * If the power is specified the power value should be used. Otherwise the fuel value will be used.
 */
public class StandardOffRoadMobileSource extends OffRoadMobileSource {

  private static final long serialVersionUID = 2L;

  private String offRoadMobileSourceCode;

  private Integer literFuelPerYear;
  private Integer operatingHoursPerYear;
  private Integer literAdBluePerYear;
  private Integer power;

  public String getOffRoadMobileSourceCode() {
    return offRoadMobileSourceCode;
  }

  public void setOffRoadMobileSourceCode(final String offRoadMobileSourceCode) {
    this.offRoadMobileSourceCode = offRoadMobileSourceCode;
  }

  public Integer getLiterFuelPerYear() {
    return literFuelPerYear;
  }

  public void setLiterFuelPerYear(final Integer literFuelPerYear) {
    this.literFuelPerYear = literFuelPerYear;
  }

  public Integer getOperatingHoursPerYear() {
    return operatingHoursPerYear;
  }

  public void setOperatingHoursPerYear(final Integer operatingHoursPerYear) {
    this.operatingHoursPerYear = operatingHoursPerYear;
  }

  public Integer getLiterAdBluePerYear() {
    return literAdBluePerYear;
  }

  public void setLiterAdBluePerYear(final Integer literAdBluePerYear) {
    this.literAdBluePerYear = literAdBluePerYear;
  }

  public Integer getPower() {
    return power;
  }

  public void setPower(final Integer power) {
    this.power = power;
  }

}
