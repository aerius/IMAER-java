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
package nl.overheid.aerius.gml.base.geo;

import nl.overheid.aerius.gml.base.FeatureMember;
import nl.aerius.shared.domain.geojson.Geometry;
import nl.aerius.shared.domain.geojson.LineString;
import nl.aerius.shared.domain.geojson.Point;
import nl.aerius.shared.domain.geojson.Polygon;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Utility class to convert GML to geometry objects.
 */
public final class GML2Geometry {

  private final Geo2GeometryUtil geo2GeometryUtil;

  public GML2Geometry(final int srid) {
    geo2GeometryUtil = new Geo2GeometryUtil(srid);
  }

  /**
   * Get the geometry.
   * @param gml2Geometry convert gml geometry to object
   * @return the Geometry of this featureMember.
   * @throws AeriusException When underlying geometry could not be parsed to a valid object or when it's not allowed for this feature member type.
   * (does not check for actual valid geometries)
   */
  public Geometry getGeometry(final FeatureMember fm) throws AeriusException {
    final GmlEmissionSourceGeometry emissionSourceGeometry = fm.getEmissionSourceGeometry();
    final Geometry geometry;
    try {
      geometry = constructGeometry(fm.getId(), emissionSourceGeometry);
      isValidGeometry(fm, geometry);
    } catch (final AeriusException e) {
      if (e.getReason() == ImaerExceptionReason.GML_GEOMETRY_INVALID) {
        throw new AeriusException(ImaerExceptionReason.GML_GEOMETRY_INVALID, fm.getId());
      } else {
        throw e;
      }
    }
    return geometry;
  }

  private Geometry constructGeometry(final String id, final GmlEmissionSourceGeometry emissionSourceGeometry)
      throws AeriusException {
    final Geometry geometry;
    if (emissionSourceGeometry.getPolygon() != null) {
      geometry = fromXMLPolygon(emissionSourceGeometry.getPolygon());
    } else if (emissionSourceGeometry.getLineString() != null) {
      geometry = fromXMLLineString(emissionSourceGeometry.getLineString());
    } else if (emissionSourceGeometry.getPoint() != null) {
      geometry = fromXMLPoint(emissionSourceGeometry.getPoint());
    } else {
      throw new AeriusException(ImaerExceptionReason.GML_GEOMETRY_UNKNOWN, id);
    }
    return geometry;
  }

  private void isValidGeometry(final FeatureMember fm, final Geometry geometry)
      throws AeriusException {
    // check if the geometry is actually valid for this type of feature member.
    if (!fm.isValidGeometry(geometry.type())) {
      throw new AeriusException(ImaerExceptionReason.GML_GEOMETRY_NOT_PERMITTED, fm.getId());
    }
  }

  /**
   * Convert a GML-object to a (geojson) Point geometry.
   * @param gmlPoint The GML-object to convert to Geometry
   * @return The Point geometry.
   * @throws AeriusException When no valid Point could be made from the gml-object.
   */
  public Point fromXMLPoint(final GmlPoint gmlPoint) throws AeriusException {
    return geo2GeometryUtil.fromXMLPoint(gmlPoint.getGmlPoint());
  }

  /**
   * Convert a GML-object to a (geojson) LineString geometry.
   * @param gmlString The GML-object to convert to Geometry
   * @return The LineString geometry.
   * @throws AeriusException When no valid LineString could be made from the gml-object.
   */
  public LineString fromXMLLineString(final GmlLineString gmlString) throws AeriusException {
    return geo2GeometryUtil.fromXMLLineString(gmlString.getGMLLineString());
  }

  /**
   * Convert a GML-object to a (geojson) Polygon geometry.
   * @param gmlPolygon The GML-object to convert to Geometry
   * @return The Polygon geometry.
   * @throws AeriusException When no valid Polygon could be made from the gml-object.
   */
  public Polygon fromXMLPolygon(final GmlPolygon gmlPolygon) throws AeriusException {
    return geo2GeometryUtil.fromXMLPolygon(gmlPolygon.getGmlPolygon());
  }
}
