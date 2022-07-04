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
package nl.overheid.aerius.gml.base;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import nl.overheid.aerius.gml.v5_0.base.CalculatorSchema;

public interface IsCalculationMetaData {

  /**
   * @return the type of calculation
   */
  default String getCalculationType() {
    return null;
  }

  /**
   * @return the maximum range for this calculation
   */
  default Double getMaximumRange() {
    return null;
  }

  /**
   * @return a list of calculation options.
   */
  @XmlElement(name = "option", namespace = CalculatorSchema.NAMESPACE)
  default List<? extends IsGmlProperty<IsCalculationOption>> getOptions() {
    return new ArrayList<>();
  }
}
