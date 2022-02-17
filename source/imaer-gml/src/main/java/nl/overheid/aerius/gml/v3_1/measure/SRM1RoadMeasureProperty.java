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
package nl.overheid.aerius.gml.v3_1.measure;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.AbstractProperty;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.measure.v40.IsGmlSRM1RoadMeasure;
import nl.overheid.aerius.gml.v3_1.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "SRM1RoadMeasurePropertyType", namespace = CalculatorSchema.NAMESPACE)
public class SRM1RoadMeasureProperty extends AbstractProperty<SRM1RoadMeasure> implements IsGmlProperty<IsGmlSRM1RoadMeasure> {

  /**
   * Default constructor, needed for JAXB.
   */
  public SRM1RoadMeasureProperty() {
    super(null);
  }

  /**
   * Convenience constructor.
   * @param srm1RoadMeasure The property to use.
   */
  public SRM1RoadMeasureProperty(final SRM1RoadMeasure srm1RoadMeasure) {
    super(srm1RoadMeasure);
  }

  @Override
  @XmlElement(name = "SRM1RoadMeasure", namespace = CalculatorSchema.NAMESPACE)
  public SRM1RoadMeasure getProperty() {
    return super.getProperty();
  }

  @Override
  public void setProperty(final SRM1RoadMeasure property) {
    super.setProperty(property);
  }

}
