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
package nl.overheid.aerius.shared.domain.v2.geojson;

import java.io.Serializable;

public class Crs implements Serializable {

  private static final long serialVersionUID = 1L;

  public static class CrsContent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    public String getName() {
      return name;
    }

    public void setName(final String name) {
      this.name = name;
    }

  }

  private String type = "name";
  private CrsContent properties;

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public CrsContent getProperties() {
    return properties;
  }

  public void setProperties(final CrsContent properties) {
    this.properties = properties;
  }

}
