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
package nl.overheid.aerius.gml.v4_0.source.plan;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.AbstractProperty;
import nl.overheid.aerius.gml.base.IsGmlProperty;
import nl.overheid.aerius.gml.base.source.plan.IsGmlPlan;
import nl.overheid.aerius.gml.v4_0.base.CalculatorSchema;

/**
 *
 */
@XmlType(name = "PlanPropertyType", namespace = CalculatorSchema.NAMESPACE)
public class PlanProperty extends AbstractProperty<Plan> implements IsGmlProperty<IsGmlPlan> {

  /**
   * Default constructor, needed for JAXB.
   */
  public PlanProperty() {
    super(null);
  }

  /**
   * Convenience constructor.
   * @param plan The property to use.
   */
  public PlanProperty(final Plan plan) {
    super(plan);
  }

  @Override
  @XmlElement(name = "Plan", namespace = CalculatorSchema.NAMESPACE)
  public Plan getProperty() {
    return super.getProperty();
  }

  @Override
  public void setProperty(final Plan property) {
    super.setProperty(property);
  }

}
