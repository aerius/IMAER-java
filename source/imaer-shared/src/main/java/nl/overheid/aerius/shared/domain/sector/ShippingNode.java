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
package nl.overheid.aerius.shared.domain.sector;

import java.io.Serializable;

import nl.overheid.aerius.geo.shared.Point;

/**
 * Shipping nodes are part of the main route network(s).
 * They can be used to attach a transfer route to a certain network when defining a dock.
 */
public class ShippingNode extends Point implements Serializable {

  private static final long serialVersionUID = 8490698074071338907L;

  private int id;

  /**
   *
   */
  public ShippingNode() {
    //default constructor.
  }

  /**
   * @param x X coordinate
   * @param y Y coordinate
   * @param id The shipping node ID.
   */
  public ShippingNode(final int id, final double x, final double y) {
    super(x, y);
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "ShippingNode[id=" + id + "]" + super.toString();
  }

}
