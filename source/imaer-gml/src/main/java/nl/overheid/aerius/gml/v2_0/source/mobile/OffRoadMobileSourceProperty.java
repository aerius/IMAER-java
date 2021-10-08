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
package nl.overheid.aerius.gml.v2_0.source.mobile;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.AbstractProperty;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.source.mobile.v31.IsGmlOffRoadMobileSource;
import nl.overheid.aerius.gml.v2_0.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "OffRoadMobileSourcePropertyType", namespace = CalculatorSchema.NAMESPACE)
public class OffRoadMobileSourceProperty extends AbstractProperty<AbstractOffRoadMobileSource> implements IsGmlProperty<IsGmlOffRoadMobileSource> {

  /**
   * Default constructor, needed for JAXB.
   */
  public OffRoadMobileSourceProperty() {
    super(null);
  }

  /**
   * Convenience constructor.
   * @param offRoadMobileSource The property to use.
   */
  public OffRoadMobileSourceProperty(final AbstractOffRoadMobileSource offRoadMobileSource) {
    super(offRoadMobileSource);
  }

  @XmlElementRef
  @Override
  public AbstractOffRoadMobileSource getProperty() {
    return super.getProperty();
  }

  @Override
  public void setProperty(final AbstractOffRoadMobileSource property) {
    super.setProperty(property);
  }

}
