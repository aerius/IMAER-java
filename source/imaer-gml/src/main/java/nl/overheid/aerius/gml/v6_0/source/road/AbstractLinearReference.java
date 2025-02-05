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
package nl.overheid.aerius.gml.v6_0.source.road;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.road.IsGmlLinearReference;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;

@XmlSeeAlso(SRM2RoadLinearReference.class)
@XmlType(name = "SimpleLinearReferenceType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"fromPosition", "toPosition"})
public abstract class AbstractLinearReference implements IsGmlLinearReference {
  private double fromPosition;
  private double toPosition;

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getFromPosition() {
    return fromPosition;
  }

  public void setFromPosition(final double fromPosition) {
    this.fromPosition = fromPosition;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public double getToPosition() {
    return toPosition;
  }

  public void setToPosition(final double toPosition) {
    this.toPosition = toPosition;
  }
}
