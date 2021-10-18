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

public enum BuoyancyType {
  DENSITY(1),
  TEMPERATURE(0);

  private final int type;

  private BuoyancyType(final int type) {
    this.type = type;
  }

  public static BuoyancyType valueOf(final int type) {
    return DENSITY.type == type ? DENSITY : TEMPERATURE;
  }

  public int type() {
    return type;
  }
}
