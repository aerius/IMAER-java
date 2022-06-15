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
package nl.overheid.aerius.util;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.util.AffineTransformation;
import org.locationtech.jts.util.GeometricShapeFactory;

import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.geometry.GeometryCalculator;

public class GeometryCalculatorImpl implements GeometryCalculator {

  @Override
  public double determineMeasure(final Geometry geometry) {
    double measure = 0.0;
    if (geometry != null) {
      switch (geometry.type()) {
      case POINT:
        measure = 1.0;
        break;
      case LINESTRING:
        measure = getLength(geometry);
        break;
      case POLYGON:
        measure = getArea(geometry);
        break;
      default:
        break;
      }
    }
    return measure;
  }

  private double getLength(final Geometry geometry) {
    try {
      return toJtsGeometry(geometry).getLength();
    } catch (final AeriusException | IllegalArgumentException e) {
      // Incorrect geometry, this should have been detected before we get to this point. Just return 0.0 now.
      return 0.0;
    }
  }

  private double getArea(final Geometry geometry) {
    try {
      return toJtsGeometry(geometry).getArea();
    } catch (final AeriusException | IllegalArgumentException e) {
      // Incorrect geometry, this should have been detected before we get to this point. Just return 0.0 now.
      return 0.0;
    }
  }

  private org.locationtech.jts.geom.Geometry toJtsGeometry(final Geometry geometry) throws AeriusException {
    return GeometryUtil.getGeometry(geometry);
  }

  @Override
  public Geometry determineBoundingBox(final List<Geometry> geometries) throws AeriusException {
    if (geometries.isEmpty()) {
      return null;
    }

    final GeometryCollection geometryCollection = toGeometryCollection(geometries);
    final org.locationtech.jts.geom.Geometry envelope = geometryCollection.getEnvelope();
    return GeometryUtil.getAeriusGeometry(envelope);
  }

  private GeometryCollection toGeometryCollection(final List<Geometry> geometries) throws AeriusException {
    final List<org.locationtech.jts.geom.Geometry> list = new ArrayList<>();
    for (final Geometry geometry : geometries) {
      final org.locationtech.jts.geom.Geometry jtsGeometry = toJtsGeometry(geometry);
      list.add(jtsGeometry);
    }

    final org.locationtech.jts.geom.Geometry[] objects = list.toArray(new org.locationtech.jts.geom.Geometry[0]);
    return new GeometryCollection(objects, new GeometryFactory());
  }

  @Override
  public Geometry scaleBoundingBox(final Geometry boundingBox, final double scale, final double minDimension) throws AeriusException {
    final org.locationtech.jts.geom.Geometry geometry = toJtsGeometry(boundingBox);

    if (org.locationtech.jts.geom.Geometry.TYPENAME_POINT.equals(geometry.getGeometryType())) {
      final GeometricShapeFactory geometricShapeFactory = new GeometricShapeFactory();
      geometricShapeFactory.setCentre(geometry.getCoordinate());
      geometricShapeFactory.setSize(minDimension);
      geometricShapeFactory.setNumPoints(4);
      return GeometryUtil.getAeriusGeometry(geometricShapeFactory.createRectangle());
    }

    return scaleRectangle(geometry, scale, minDimension);
  }

  private Geometry scaleRectangle(final org.locationtech.jts.geom.Geometry geometry, final double scale, final double minDimension)
      throws AeriusException {
    final Envelope envelopeInternal = geometry.getEnvelopeInternal();

    final AffineTransformation scaling = AffineTransformation.scaleInstance(
        Math.max(scale * envelopeInternal.getWidth(), minDimension / envelopeInternal.getWidth()),
        Math.max(scale * envelopeInternal.getHeight(), minDimension / envelopeInternal.getHeight()),
        envelopeInternal.centre().x,
        envelopeInternal.centre().y);

    return GeometryUtil.getAeriusGeometry(scaling.transform(geometry));
  }
}
