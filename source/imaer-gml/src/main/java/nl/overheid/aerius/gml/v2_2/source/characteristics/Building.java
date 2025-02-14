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
package nl.overheid.aerius.gml.v2_2.source.characteristics;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.building.IsGmlBuildingV31;
import nl.overheid.aerius.gml.v2_2.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "BuildingType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"height", "width", "length", "orientation"})
public class Building implements IsGmlBuildingV31 {

  private double height;
  private double width;
  private double length;
  private Integer orientation;

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getHeight() {
    return height;
  }

  public void setHeight(final double height) {
    this.height = height;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getWidth() {
    return width;
  }

  public void setWidth(final double width) {
    this.width = width;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getLength() {
    return length;
  }

  public void setLength(final double length) {
    this.length = length;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public Integer getOrientation() {
    return orientation;
  }

  public void setOrientation(final Integer orientation) {
    this.orientation = orientation;
  }

}
