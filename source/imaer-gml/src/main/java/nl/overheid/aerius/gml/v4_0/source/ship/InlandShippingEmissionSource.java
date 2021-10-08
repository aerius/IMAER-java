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
package nl.overheid.aerius.gml.v4_0.source.ship;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.source.ship.IsGmlInlandShippingEmissionSource;
import nl.overheid.aerius.gml.v4_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v4_0.base.ReferenceType;
import nl.overheid.aerius.gml.v4_0.source.EmissionSource;

/**
 *
 */
@XmlType(name = "InlandShipEmissionSourceType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"inlandShippings", "inlandWaterwayProperty",
    "mooringA", "mooringB"})
public class InlandShippingEmissionSource extends EmissionSource implements IsGmlInlandShippingEmissionSource {

  private List<InlandShippingProperty> inlandShippings = new ArrayList<>();
  private InlandWaterwayProperty inlandWaterwayProperty;
  private ReferenceType mooringA;
  private ReferenceType mooringB;

  @Override
  @XmlElement(name = "inlandShipping", namespace = CalculatorSchema.NAMESPACE)
  public List<InlandShippingProperty> getInlandShippings() {
    return inlandShippings;
  }

  public void setInlandShippings(final List<InlandShippingProperty> inlandShippings) {
    this.inlandShippings = inlandShippings;
  }

  @Override
  @XmlElement(name = "waterway", namespace = CalculatorSchema.NAMESPACE)
  public InlandWaterwayProperty getInlandWaterwayProperty() {
    return inlandWaterwayProperty;
  }

  public void setInlandWaterwayProperty(final InlandWaterwayProperty inlandWaterwayProperty) {
    this.inlandWaterwayProperty = inlandWaterwayProperty;
  }

  @Override
  @XmlElement(name = "mooringA", namespace = CalculatorSchema.NAMESPACE)
  public ReferenceType getMooringA() {
    return mooringA;
  }

  public void setMooringA(final ReferenceType mooringA) {
    this.mooringA = mooringA;
  }

  @Override
  @XmlElement(name = "mooringB", namespace = CalculatorSchema.NAMESPACE)
  public ReferenceType getMooringB() {
    return mooringB;
  }

  public void setMooringB(final ReferenceType mooringB) {
    this.mooringB = mooringB;
  }

}
