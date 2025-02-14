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
package nl.overheid.aerius.gml.v2_2.geo;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import net.opengis.gml.v_3_2.LineStringType;

import nl.overheid.aerius.gml.base.GMLSchema;
import nl.overheid.aerius.gml.base.geo.GmlLineString;
import nl.overheid.aerius.gml.v2_2.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "LineStringType", namespace = CalculatorSchema.NAMESPACE)
public class LineString implements GmlLineString {

  private LineStringType gmlLineString = new LineStringType();

  @Override
  public LineStringType getGMLLineString() {
    return gmlLineString;
  }

  @Override
  @XmlElement(name = "LineString", namespace = GMLSchema.NAMESPACE)
  public void setGMLLineString(final LineStringType gmlLineString) {
    this.gmlLineString = gmlLineString;
  }

}
