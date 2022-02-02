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
package nl.overheid.aerius.gml.v5_0.geo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import net.opengis.gml.v_3_2_1.PointType;

import nl.overheid.aerius.gml.base.GMLSchema;
import nl.overheid.aerius.gml.base.geo.GmlPoint;
import nl.overheid.aerius.gml.v5_0.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "PointType", namespace = CalculatorSchema.NAMESPACE)
public class Point implements GmlPoint {

  private PointType gmlPoint = new PointType();

  @Override
  public PointType getGmlPoint() {
    return gmlPoint;
  }

  @Override
  @XmlElement(name = "Point", namespace = GMLSchema.NAMESPACE)
  public void setGmlPoint(final PointType gmlPoint) {
    this.gmlPoint = gmlPoint;
  }
}
