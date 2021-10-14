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
package nl.overheid.aerius.shared.domain.v2.characteristics.adms;

/**
 * Emission parameters given at actual temperature and pressure or normal temperature and pressure (1atm, 273.15K): 0=actual,1=NTP
 */
public enum ReleaseAtNTP {
  ACTUAL(0),
  NTP(1);

  private final int type;

  private ReleaseAtNTP(final int type) {
    this.type = type;
  }

  public static ReleaseAtNTP valueOf(final int type) {
    return type == NTP.type ? NTP : ACTUAL;
  }

  public int type() {
    return type;
  }
}
