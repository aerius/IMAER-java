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

import javax.xml.validation.Schema;

import nl.overheid.aerius.gml.v5_1.togml.GMLVersionWriterV51;
import nl.overheid.aerius.gml.v6_0.togml.GMLVersionWriterV60;
import nl.overheid.aerius.shared.domain.geo.HexagonZoomLevel;
import nl.overheid.aerius.shared.exception.AeriusException;

public final class GMLVersionWriterFactory {

  private GMLVersionWriterFactory() {
    // Util class
  }

  public static GMLVersionWriter createGMLVersionWriter(final HexagonZoomLevel zoomLevel1, final String srsName,
      final AeriusGMLVersion aeriusGMLVersion, final boolean withRepresentation) {
    return switch (aeriusGMLVersion) {
      case V5_1 -> new GMLVersionWriterV51(zoomLevel1, srsName);
      case V6_0 -> new GMLVersionWriterV60(zoomLevel1, srsName, withRepresentation);
      default -> throw new IllegalArgumentException("Aerius GML version is not supported");
    };
  }

  public static Schema getSchema(final AeriusGMLVersion aeriusGMLVersion) throws AeriusException {
    return GMLSchemaFactory.createSchema(aeriusGMLVersion);
  }
}
