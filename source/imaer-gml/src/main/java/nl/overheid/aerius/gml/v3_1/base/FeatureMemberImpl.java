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
package nl.overheid.aerius.gml.v3_1.base;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import nl.overheid.aerius.gml.base.FeatureMember;
import nl.overheid.aerius.gml.base.GMLSchema;
import nl.overheid.aerius.gml.base.geo.Geometry2GML;
import nl.overheid.aerius.gml.v3_1.geo.EmissionSourceGeometry;
import nl.overheid.aerius.gml.v3_1.geo.LineString;
import nl.overheid.aerius.gml.v3_1.geo.Point;
import nl.overheid.aerius.gml.v3_1.geo.Polygon;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
public abstract class FeatureMemberImpl implements FeatureMember {

  private String id;

  private EmissionSourceGeometry emissionSourceGeometry = new EmissionSourceGeometry();

  @Override
  @XmlAttribute(namespace = GMLSchema.NAMESPACE)
  public String getId() {
    return id;
  }

  /**
   * @param id The ID to set.
   */
  public void setId(final String id) {
    this.id = id;
    emissionSourceGeometry.setId(id);
  }

  /**
   * @return The identifier property. Convenience implementation for JAXB to generate the right GML.
   */
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public NEN3610IDProperty getIdentifier() {
    return new NEN3610IDProperty(new NEN3610ID(id));
  }

  /**
   * @param nEN3610IDProperty The identifier to set. Won't actually be set, it's not used on import.
   */
  public void setIdentifier(final NEN3610IDProperty nEN3610IDProperty) {
    //no need to set.
  }

  //can be used in class extending this one to set a EmissionSourceGeometryProperty.
  @Override
  @XmlTransient
  public EmissionSourceGeometry getEmissionSourceGeometry() {
    return emissionSourceGeometry;
  }

  public void setEmissionSourceGeometry(final EmissionSourceGeometry emissionSourceGeometry) {
    this.emissionSourceGeometry = emissionSourceGeometry;
  }

  /**
   * @param geometry2gml the geometry to gml converter.
   * @param geometry The geometry to set.
   * @throws AeriusException When geometry is not allowed for this feature member type.
   * (does not check for actual valid geometries)
   */
  public void setGeometry(final Geometry2GML geometry2gml, final Geometry geometry) throws AeriusException {
    if (geometry == null) {
      throw new IllegalArgumentException("Geometry not allowed to be null.");
    }
    if (!isValidGeometry(geometry.type())) {
      throw new AeriusException(ImaerExceptionReason.GML_GEOMETRY_NOT_PERMITTED, getId());
    } else if (geometry instanceof nl.overheid.aerius.shared.domain.v2.geojson.Point) {
      emissionSourceGeometry.setPoint(geometry2gml.toXMLPoint((nl.overheid.aerius.shared.domain.v2.geojson.Point) geometry, new Point()));
    } else if (geometry instanceof nl.overheid.aerius.shared.domain.v2.geojson.LineString) {
      emissionSourceGeometry
          .setLineString(geometry2gml.toXMLLineString((nl.overheid.aerius.shared.domain.v2.geojson.LineString) geometry, new LineString()));
    } else if (geometry instanceof nl.overheid.aerius.shared.domain.v2.geojson.Polygon) {
      emissionSourceGeometry.setPolygon(geometry2gml.toXMLPolygon((nl.overheid.aerius.shared.domain.v2.geojson.Polygon) geometry, new Polygon()));
    } else {
      throw new AeriusException(ImaerExceptionReason.GML_GEOMETRY_UNKNOWN, getId());
    }
  }
}
