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

import jakarta.xml.bind.annotation.XmlTransient;

/**
 * @param <T> The class that this property is wrapping.
 */
public class AbstractProperty<T> implements GMLAbstractProperty<T> {

  private T property;

  /**
   * Convenience constructor.
   * @param property The property to use.
   */
  public AbstractProperty(final T property) {
    this.property = property;
  }

  @Override
  @XmlTransient
  public T getProperty() {
    return property;
  }

  @Override
  public void setProperty(final T property) {
    this.property = property;
  }

}
