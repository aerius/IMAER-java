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

  public static final int AVERAGE_VALUE = 100;

  private String gmlId;
  private String label;
  private CustomDiurnalVariationType type;
  private List<Integer> values;

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

  public List<Integer> getValues() {
    return values;
  }

  public void setValues(final List<Integer> values) {
    this.values = values;
  }

}
