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
package nl.overheid.aerius.shared.domain.v2.building;

import java.io.Serializable;

public class Building implements Serializable {

  private static final long serialVersionUID = 1L;

  private String gmlId;
  private String label;
  /**
   * height of the building (m)
   */
  private double height;
  /**
   * Radius of a circle building (m)
   */
  private double radius;

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

  public double getHeight() {
    return height;
  }

  public void setHeight(final double height) {
    this.height = height;
  }

  public double getRadius() {
    return radius;
  }

  public void setRadius(final double radius) {
    this.radius = radius;
  }

  public boolean isCircle() {
    return radius > 0;
  }
}
