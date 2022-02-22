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
package nl.overheid.aerius.gml.v3_0.source.road;

import javax.xml.bind.annotation.XmlElementRef;

import nl.overheid.aerius.gml.base.AbstractProperty;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.source.road.v40.IsGmlSRM1RoadLinearReference;

/**
 *
 */
public class SRM1RoadLinearReferenceProperty extends AbstractProperty<SRM1RoadLinearReference>
    implements IsGmlProperty<IsGmlSRM1RoadLinearReference> {

  /**
   * Default constructor, needed for JAXB.
   */
  public SRM1RoadLinearReferenceProperty() {
    super(null);
  }

  /**
   * Convenience constructor.
   * @param linearReference The property to use.
   */
  public SRM1RoadLinearReferenceProperty(final SRM1RoadLinearReference linearReference) {
    super(linearReference);
  }

  @XmlElementRef
  @Override
  public SRM1RoadLinearReference getProperty() {
    return super.getProperty();
  }

  @Override
  public void setProperty(final SRM1RoadLinearReference property) {
    super.setProperty(property);
  }
}
