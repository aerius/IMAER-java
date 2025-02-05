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
package nl.overheid.aerius.gml.v4_0.source.farmland;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.AbstractProperty;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.source.farmland.IsGmlFarmlandActivity;
import nl.overheid.aerius.gml.v4_0.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "FarmlandActivityPropertyType", namespace = CalculatorSchema.NAMESPACE)
public class FarmlandActivityProperty extends AbstractProperty<FarmlandActivity> implements IsGmlProperty<IsGmlFarmlandActivity> {

  /**
   * Default constructor, needed for JAXB.
   */
  public FarmlandActivityProperty() {
    super(null);
  }

  /**
   * Convenience constructor.
   * @param activity The property to use.
   */
  public FarmlandActivityProperty(final FarmlandActivity activity) {
    super(activity);
  }

  @Override
  @XmlElement(name = "FarmlandActivity", namespace = CalculatorSchema.NAMESPACE)
  public FarmlandActivity getProperty() {
    return super.getProperty();
  }

  @Override
  public void setProperty(final FarmlandActivity property) {
    super.setProperty(property);
  }

}
