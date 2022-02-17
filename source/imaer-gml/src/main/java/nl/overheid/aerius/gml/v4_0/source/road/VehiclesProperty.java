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
package nl.overheid.aerius.gml.v4_0.source.road;

import javax.xml.bind.annotation.XmlElementRef;

import nl.overheid.aerius.gml.base.AbstractProperty;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.source.road.IsGmlVehicle;

/**
 *
 */
public class VehiclesProperty extends AbstractProperty<AbstractVehicle> implements IsGmlProperty<IsGmlVehicle> {

  /**
   * Default constructor, needed for JAXB.
   */
  public VehiclesProperty() {
    super(null);
  }

  /**
   * Convenience constructor.
   * @param vehicles The property to use.
   */
  public VehiclesProperty(final AbstractVehicle vehicles) {
    super(vehicles);
  }

  @XmlElementRef
  @Override
  public AbstractVehicle getProperty() {
    return super.getProperty();
  }

  @Override
  public void setProperty(final AbstractVehicle property) {
    super.setProperty(property);
  }
}
