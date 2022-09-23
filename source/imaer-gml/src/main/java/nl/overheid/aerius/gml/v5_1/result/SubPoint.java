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
package nl.overheid.aerius.gml.v5_1.result;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.result.IsGmlSubPoint;
import nl.overheid.aerius.gml.v5_1.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "SubPointType", namespace = CalculatorSchema.NAMESPACE)
public class SubPoint extends AbstractCalculationPoint implements IsGmlSubPoint {

  private int subPointId;
  private int receptorPointId;
  private int level;

  @Override
  @XmlAttribute
  public int getSubPointId() {
    return subPointId;
  }

  public void setSubPointId(final int subPointId) {
    this.subPointId = subPointId;
  }

  @Override
  @XmlAttribute
  public int getReceptorPointId() {
    return receptorPointId;
  }

  public void setReceptorPointId(final int receptorPointId) {
    this.receptorPointId = receptorPointId;
  }

  @Override
  @XmlElement(namespace = CalculatorSchema.NAMESPACE)
  public int getLevel() {
    return level;
  }

  public void setLevel(final int level) {
    this.level = level;
  }

}
