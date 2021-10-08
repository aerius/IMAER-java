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
package nl.overheid.aerius.shared.domain.sector;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Enum for all main sectors. This list is hardcoded, and matches the sector
 * list in the database, which is also an enum in the database.
 */
public enum SectorGroup implements Serializable {
  /**
   * Energy sector.
   */
  ENERGY,
  /**
   * Agriculture sector.
   */
  AGRICULTURE,
  /**
   * Farmland (used in database, but we don't want to show it in UI)
   */
  FARMLAND(false),
  /**
   * Housing and offices.
   */
  LIVE_AND_WORK,
  /**
   * Industry in general.
   */
  INDUSTRY,
  /**
   * Mobile machines not intended for on the official roads.
   */
  MOBILE_EQUIPMENT,
  /**
   * Rail transports.
   */
  RAIL_TRANSPORTATION,
  /**
   * Aviation.
   */
  AVIATION,
  /**
   * All vehicles driving on the official roads.
   */
  ROAD_TRANSPORTATION,
  /**
   * Maritime and inland shipping.
   */
  SHIPPING,
  /**
   * Future projects.
   */
  PLAN,
  /**
   * Everything not in a specific category.
   */
  OTHER;

  private boolean selectable;

  private SectorGroup() {
    this(true);
  }

  private SectorGroup(final boolean selectable) {
    this.selectable = selectable;
  }

  /**
   * Returns the name in lower case.
   *
   * @return the name in lower case
   */
  @Override
  public String toString() {
    return name().toLowerCase();
  }

  public static ArrayList<SectorGroup> selectableValues() {
    final ArrayList<SectorGroup> selectables = new ArrayList<>();
    for (final SectorGroup sectorGroup : values()) {
      if (sectorGroup.selectable) {
        selectables.add(sectorGroup);
      }
    }
    return selectables;
  }
}
