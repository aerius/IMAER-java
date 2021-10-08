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
 * Options supplied to Connect, that don't have a direct use in calculations.
 * These options might have impact on the receptors being used for instance,
 * and in some cases we want to add the used option to metadata so the user is aware of these options.
 *
 * Do not use these options outside of Connect or as informative functionality (debugging/metadata).
 * If the option needs to be used with a calculation, CalculationSetOptions is probably a better location.
 */
public class ConnectSuppliedOptions implements Serializable {

  private static final long serialVersionUID = 1L;

  private String receptorSetName;
  private Integer calculationYear;

  public String getReceptorSetName() {
    return receptorSetName;
  }

  public void setReceptorSetName(final String receptorSetName) {
    this.receptorSetName = receptorSetName;
  }

  public Integer getCalculationYear() {
    return calculationYear;
  }

  public void setCalculationYear(final Integer calculationYear) {
    this.calculationYear = calculationYear;
  }

}
