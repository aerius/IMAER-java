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

/**
 * Whether to use roadOPS, from 5km or entire calculation.
 */
public enum CalculationRoadOPS {

  /**
   * Default option, use normal calculation settings.
   */
  DEFAULT,

  /**
   * Use OPS to calculate from 5km distance
   * This gives results outside the scope
   */
  OPS_ROAD,

  /**
   * Use OPS to calculate for the entire calculation
   * This gives results outside the scope
   */
  OPS_ALL

}
