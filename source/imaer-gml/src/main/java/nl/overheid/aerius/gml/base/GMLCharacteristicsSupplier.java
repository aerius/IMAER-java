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

import nl.overheid.aerius.shared.domain.v2.characteristics.CharacteristicsType;
import nl.overheid.aerius.shared.domain.v2.characteristics.SourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.geojson.GeometryType;

/**
 * Supplier for Source characteristics.
 */
public interface GMLCharacteristicsSupplier {

  /**
   * Determine the default characteristics for a sector (based on AERIUS sector ID).
   *
   * @deprecated use/implement the version with geometry type, this version will be removed in a future version
   */
  @Deprecated
  default <S extends SourceCharacteristics> S determineDefaultCharacteristicsBySectorId(final int sectorId) {
    return null;
  }

  /**
   * Determine the default characteristics for a sector (based on AERIUS sector ID and geometry type).
   */
  default <S extends SourceCharacteristics> S determineDefaultCharacteristicsBySectorId(final int sectorId, final GeometryType geometryType) {
    return determineDefaultCharacteristicsBySectorId(sectorId);
  }

  /**
   * @return returns the data type of the characteristics.
   */
  default CharacteristicsType getCharacteristicsType() {
    return CharacteristicsType.OPS;
  }
}
