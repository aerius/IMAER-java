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
package nl.overheid.aerius.gml.v2_0.collection;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.AbstractProperty;
import nl.overheid.aerius.gml.v2_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v2_0.base.FeatureMemberImpl;
import nl.overheid.aerius.gml.v2_0.result.CustomCalculationPoint;
import nl.overheid.aerius.gml.v2_0.result.ReceptorPoint;
import nl.overheid.aerius.gml.v2_0.source.EmissionSource;
import nl.overheid.aerius.gml.v2_0.source.lodging.FarmLodgingEmissionSource;
import nl.overheid.aerius.gml.v2_0.source.mobile.OffRoadMobileEmissionSource;
import nl.overheid.aerius.gml.v2_0.source.plan.PlanEmissionSource;
import nl.overheid.aerius.gml.v2_0.source.road.RoadNetwork;
import nl.overheid.aerius.gml.v2_0.source.road.SRM2RoadEmissionSource;
import nl.overheid.aerius.gml.v2_0.source.ship.InlandShippingEmissionSource;
import nl.overheid.aerius.gml.v2_0.source.ship.MaritimeShippingEmissionSource;
import nl.overheid.aerius.gml.v2_0.source.ship.MooringInlandShippingEmissionSource;
import nl.overheid.aerius.gml.v2_0.source.ship.MooringMaritimeShippingEmissionSource;

/**
 *
 */
@XmlType(name = "FeatureMemberPropertyType", namespace = CalculatorSchema.NAMESPACE)
public class FeatureMemberProperty extends AbstractProperty<FeatureMemberImpl> {

  /**
   * Default constructor, needed for JAXB.
   */
  public FeatureMemberProperty() {
    super(null);
  }

  /**
   * Convenience constructor.
   * @param featureMemberImpl The property to use.
   */
  public FeatureMemberProperty(final FeatureMemberImpl featureMemberImpl) {
    super(featureMemberImpl);
  }

  @Override
  @XmlElements({
      @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "EmissionSource", type = EmissionSource.class),
      @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "FarmLodgingEmissionSource", type = FarmLodgingEmissionSource.class),
      @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "RoadNetwork", type = RoadNetwork.class),
      @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "SRM2Road", type = SRM2RoadEmissionSource.class),
      @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "PlanEmissionSource", type = PlanEmissionSource.class),
      @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "OffRoadMobileSourceEmissionSource", type = OffRoadMobileEmissionSource.class),
      @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "MaritimeShippingEmissionSource", type = MaritimeShippingEmissionSource.class),
      @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "MooringMaritimeShippingEmissionSource", type = MooringMaritimeShippingEmissionSource.class),
      @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "InlandShippingEmissionSource", type = InlandShippingEmissionSource.class),
      @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "MooringInlandShippingEmissionSource", type = MooringInlandShippingEmissionSource.class),
      @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "CalculationPoint", type = CustomCalculationPoint.class),
      @XmlElement(namespace = CalculatorSchema.NAMESPACE, name = "ReceptorPoint", type = ReceptorPoint.class)
  })
  public FeatureMemberImpl getProperty() {
    return super.getProperty();
  }

  @Override
  public void setProperty(final FeatureMemberImpl property) {
    super.setProperty(property);
  }

}
