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
package nl.overheid.aerius.shared.domain.v2.source.shipping.inland;

/**
 * The direction for a route at a point.
 */
public enum WaterwayDirection {
  /**
   * Route is going upstream at this point.
   */
  UPSTREAM,
  /**
   * Route is going downstream at this point.
   */
  DOWNSTREAM,
  /**
   * Direction matters not for the route at this point.
   */
  IRRELEVANT;

  /**
   * @return The opposite of this direction.
   */
  public WaterwayDirection getOpposite() {
    final WaterwayDirection opposite;
    switch (this) {
    case DOWNSTREAM:
      opposite = UPSTREAM;
      break;
    case UPSTREAM:
      opposite = DOWNSTREAM;
      break;
    default:
      opposite = IRRELEVANT;
      break;
    }
    return opposite;
  }
}
