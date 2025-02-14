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
package nl.overheid.aerius.gml.v1_1.base;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlElements;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.FeatureCollection;
import nl.overheid.aerius.gml.base.FeatureMember;
import nl.overheid.aerius.gml.base.GMLSchema;
import nl.overheid.aerius.gml.base.MetaData;
import nl.overheid.aerius.gml.v1_1.result.CustomCalculationPoint;
import nl.overheid.aerius.gml.v1_1.result.ReceptorPoint;
import nl.overheid.aerius.gml.v1_1.source.EmissionSource;
import nl.overheid.aerius.gml.v1_1.source.lodging.FarmLodgingEmissionSource;
import nl.overheid.aerius.gml.v1_1.source.mobile.OffRoadMobileEmissionSource;
import nl.overheid.aerius.gml.v1_1.source.plan.PlanEmissionSource;
import nl.overheid.aerius.gml.v1_1.source.road.RoadNetwork;
import nl.overheid.aerius.gml.v1_1.source.road.SRM2RoadEmissionSource;
import nl.overheid.aerius.gml.v1_1.source.ship.InlandShippingEmissionSource;
import nl.overheid.aerius.gml.v1_1.source.ship.MaritimeShippingEmissionSource;
import nl.overheid.aerius.gml.v1_1.source.ship.MooringInlandShippingEmissionSource;
import nl.overheid.aerius.gml.v1_1.source.ship.MooringMaritimeShippingEmissionSource;

/**
 *
 */
@XmlRootElement(name = "FeatureCollectionCalculator", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "AeriusFeatureCollectionType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"metaData", "featureMemberImpls"})
public class FeatureCollectionImpl implements FeatureCollection {

  private String id = CalculatorSchema.GML_ID_NAMESPACE + ".Collection";

  private MetaDataImpl metaDataImpl;

  private List<FeatureMemberImpl> featureMembers = new ArrayList<>();

  @XmlAttribute(namespace = GMLSchema.NAMESPACE)
  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  @Override
  @XmlElement(name = "aeriusCalculatorMetaData", namespace = CalculatorSchema.NAMESPACE)
  public MetaDataImpl getMetaData() {
    return metaDataImpl;
  }

  public void setMetaData(final MetaDataImpl metaData) {
    this.metaDataImpl = metaData;
  }

  @Override
  public void setMetaData(final MetaData metaData) {
    setMetaData((MetaDataImpl) metaData);
  }

  @XmlElementWrapper(name = "featureMembers", namespace = CalculatorSchema.NAMESPACE)
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
  public List<FeatureMemberImpl> getFeatureMemberImpls() {
    return featureMembers;
  }

  @Override
  @XmlTransient
  public List<FeatureMember> getFeatureMembers() {
    return new ArrayList<>(featureMembers);
  }

  @Override
  public void setFeatureMembers(final List<FeatureMember> sources) {
    this.featureMembers = new ArrayList<>();
    for (final FeatureMember fm : sources) {
      if (fm instanceof FeatureMemberImpl) {
        featureMembers.add((FeatureMemberImpl) fm);
      }
    }
  }

  @Override
  @XmlTransient
  public String getName() {
    return metaDataImpl == null ? null : metaDataImpl.getSituationName();
  }

  @Override
  @XmlTransient
  public Integer getYear() {
    return metaDataImpl == null ? null : metaDataImpl.getYear();
  }
}
