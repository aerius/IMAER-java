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
package nl.overheid.aerius.gml.v6_0.source.housing;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.AbstractProperty;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.source.housing.IsGmlFarmAnimalHousing;
import nl.overheid.aerius.gml.v6_0.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "FarmAnimalHousingPropertyType", namespace = CalculatorSchema.NAMESPACE)
public class FarmAnimalHousingProperty extends AbstractProperty<AbstractFarmAnimalHousing> implements IsGmlProperty<IsGmlFarmAnimalHousing> {

  /**
   * Default constructor, needed for JAXB.
   */
  public FarmAnimalHousingProperty() {
    super(null);
  }

  /**
   * Convenience constructor.
   * @param farmAnimalHousing The property to use.
   */
  public FarmAnimalHousingProperty(final AbstractFarmAnimalHousing farmAnimalHousing) {
    super(farmAnimalHousing);
  }

  @XmlElementRef
  @Override
  public AbstractFarmAnimalHousing getProperty() {
    return super.getProperty();
  }

  @Override
  public void setProperty(final AbstractFarmAnimalHousing property) {
    super.setProperty(property);
  }

}
