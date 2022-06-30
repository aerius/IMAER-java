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
package nl.overheid.aerius.gml.base;

import nl.overheid.aerius.shared.domain.sector.Sector;

/**
 * Helper class to check if sector is valid or to get the default sector.
 */
public interface GMLSectorSupplier {

  /**
   * @return Returns true if the give sector Id is a unknown sector id
   */
  default boolean isValidSectorId(final Integer sectorId) {
    return true;
  }

  /**
   * @return Returns the default sector that will can used when an unknown sector is given
   */
  default Sector getDefaultSector() {
    return Sector.SECTOR_DEFAULT;
  }
}
