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
package nl.overheid.aerius.shared.domain.v2.characteristics;

import java.io.Serializable;
import java.util.List;

public class CustomDiurnalVariation implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * There is an allowed inaccuracy in the summation of all values.
   * As long as the sum of all values is equal to the expected sum (which should be 1 * number of values),
   * it's considered OK.
   */
  public static final double ALLOWED_EPSILON = 0.5;

  private String gmlId;
  private String label;
  private CustomDiurnalVariationType type;
  private List<Double> values;

  public String getGmlId() {
    return gmlId;
  }

  public void setGmlId(final String gmlId) {
    this.gmlId = gmlId;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(final String label) {
    this.label = label;
  }

  public CustomDiurnalVariationType getType() {
    return type;
  }

  public void setType(final CustomDiurnalVariationType type) {
    this.type = type;
  }

  public List<Double> getValues() {
    return values;
  }

  public void setValues(final List<Double> values) {
    this.values = values;
  }

}
