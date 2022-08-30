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

import nl.overheid.aerius.gml.base.geo.GmlEmissionSourceGeometry;
import nl.aerius.shared.domain.geojson.GeometryType;

/**
 * Interface for an AERIUS GML featurecollection, intended for use with JAXB-objects.
 */
public interface FeatureMember {

  String getId();

  void setId(final String id);

  GmlEmissionSourceGeometry getEmissionSourceGeometry();

  /**
   * Ensure the geometry is valid for this specific feature member type.
   * @param type The type of the geometry.
   * @return true if the type of geometry is valid.
   */
  boolean isValidGeometry(GeometryType type);
}
