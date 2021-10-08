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
package nl.overheid.aerius.gml.v2_0.geo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.GMLSchema;
import nl.overheid.aerius.gml.base.geo.GmlEmissionSourceGeometry;
import nl.overheid.aerius.gml.v2_0.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "EmissionSourceGeometryType", namespace = GMLSchema.NAMESPACE)
public class EmissionSourceGeometry implements GmlEmissionSourceGeometry {

  private Point point;
  private LineString lineString;
  private Polygon polygon;

  /**
   * @param id The ID to set for this EmissionGeometry
   */
  public void setId(final String id) {
    if (this.point != null) {
      point.getGmlPoint().setId(id + ".POINT");
    } else if (this.lineString != null) {
      lineString.getGMLLineString().setId(id + ".CURVE");
    } else if (this.polygon != null) {
      polygon.getGmlPolygon().setId(id + ".SURFACE");
    }
  }

  @Override
  @XmlElement(name = "GM_Point", namespace = CalculatorSchema.NAMESPACE)
  public Point getPoint() {
    return point;
  }

  public void setPoint(final Point point) {
    this.point = point;
  }

  @Override
  @XmlElement(name = "GM_Curve", namespace = CalculatorSchema.NAMESPACE)
  public LineString getLineString() {
    return lineString;
  }

  public void setLineString(final LineString lineString) {
    this.lineString = lineString;
  }

  @Override
  @XmlElement(name = "GM_Surface", namespace = CalculatorSchema.NAMESPACE)
  public Polygon getPolygon() {
    return polygon;
  }

  public void setPolygon(final Polygon polygon) {
    this.polygon = polygon;
  }

}
