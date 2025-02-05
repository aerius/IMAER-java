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

import jakarta.validation.Valid;

public class Feature<T extends Serializable, G extends Geometry> implements Serializable, IsFeature {

  private static final long serialVersionUID = 1L;

  private String type = "Feature";
  private String id;
  private G geometry;
  private T properties;

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  @Override
  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  @Valid
  public T getProperties() {
    return properties;
  }

  public void setProperties(final T properties) {
    this.properties = properties;
  }

  @Override
  public G getGeometry() {
    return geometry;
  }

  public void setGeometry(final G geometry) {
    this.geometry = geometry;
  }

}
