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
package nl.overheid.aerius.shared.domain.v2.base;

import java.math.BigDecimal;
import java.math.RoundingMode;

import nl.overheid.aerius.shared.ImaerConstants;

public enum TimeUnit {

  HOUR(ImaerConstants.HOURS_PER_YEAR),
  DAY(ImaerConstants.DAYS_PER_YEAR),
  MONTH(ImaerConstants.MONTHS_PER_YEAR),
  YEAR(1);

  private final int multiplier;
  private final BigDecimal multiplierBD;

  TimeUnit(final int multiplier) {
    this.multiplier = multiplier;
    this.multiplierBD = BigDecimal.valueOf(multiplier);
  }

  public int getPerYear(final int amount) {
    return amount * multiplier;
  }

  public double getPerYear(final double amount) {
    return amount * multiplier;
  }

  public BigDecimal getPerYear(final BigDecimal amount) {
    return amount.multiply(multiplierBD);
  }

  public double toUnit(final double amount, final TimeUnit unit) {
    return getPerYear(amount) / unit.multiplier;
  }

  public BigDecimal toUnit(final BigDecimal amount, final TimeUnit unit) {
    return getPerYear(amount).divide(unit.multiplierBD, 5, RoundingMode.HALF_UP);
  }

  public static TimeUnit valueOf(final Enum<?> from) {
    return valueOf(from.name());
  }

}
