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
package nl.aerius.shared.domain.geojson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FeatureCollection<T extends Serializable> implements Serializable {

  private static final long serialVersionUID = 1L;

  private String type = "FeatureCollection";
  private Crs crs;
  private List<T> features = new ArrayList<>();

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public Crs getCrs() {
    return crs;
  }

  public void setCrs(final Crs crs) {
    this.crs = crs;
  }

  public List<T> getFeatures() {
    return features;
  }

  public void setFeatures(final List<T> features) {
    this.features = features;
  }

}
