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
package nl.overheid.aerius.gml.v1_1.source.road;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import net.opengis.gml.v_3_2_1.LengthType;

import nl.overheid.aerius.gml.base.source.road.IsGmlLinearReference;
import nl.overheid.aerius.gml.v1_1.base.CalculatorSchema;

@XmlSeeAlso(SRM2RoadLinearReference.class)
@XmlType(name = "SimpleLinearReferenceType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"fromPositionValue", "toPositionValue"})
public abstract class AbstractLinearReference implements IsGmlLinearReference {
  private LengthType fromPositionValue;
  private LengthType toPositionValue;

  @XmlElement(name = "fromPosition", namespace = CalculatorSchema.NAMESPACE)
  public LengthType getFromPositionValue() {
    return fromPositionValue;
  }

  public void setFromPositionValue(final LengthType fromPositionValue) {
    this.fromPositionValue = fromPositionValue;
  }

  @XmlElement(name = "toPosition", namespace = CalculatorSchema.NAMESPACE)
  public LengthType getToPositionValue() {
    return toPositionValue;
  }

  public void setToPositionValue(final LengthType toPositionValue) {
    this.toPositionValue = toPositionValue;
  }

  @XmlTransient
  @Override
  public double getFromPosition() {
    return fromPositionValue.getValue();
  }

  @XmlTransient
  @Override
  public double getToPosition() {
    return toPositionValue.getValue();
  }

}
