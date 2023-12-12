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
package nl.overheid.aerius.gml.v3_0.collection;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import nl.overheid.aerius.gml.base.FeatureCollection;
import nl.overheid.aerius.gml.base.FeatureMember;
import nl.overheid.aerius.gml.base.GMLSchema;
import nl.overheid.aerius.gml.base.MetaData;
import nl.overheid.aerius.gml.v3_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v3_0.base.FeatureMemberImpl;
import nl.overheid.aerius.gml.v3_0.metadata.MetaDataImpl;

/**
 *
 */
@XmlRootElement(name = "FeatureCollectionCalculator", namespace = CalculatorSchema.NAMESPACE)
@XmlType(name = "AeriusFeatureCollectionType", namespace = CalculatorSchema.NAMESPACE, propOrder = {"metadataProperty", "featureMemberProperties"})
public class FeatureCollectionImpl implements FeatureCollection {

  private String id = CalculatorSchema.GML_ID_NAMESPACE + ".Collection";

  private MetaDataImpl metaDataImpl;

  private List<FeatureMemberProperty> featureMembers = new ArrayList<>();

  @XmlAttribute(namespace = GMLSchema.NAMESPACE)
  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  @XmlElement(name = "metadata", namespace = CalculatorSchema.NAMESPACE)
  public MetadataProperty getMetadataProperty() {
    return metaDataImpl == null ? null : new MetadataProperty(metaDataImpl);
  }

  public void setMetadataProperty(final MetadataProperty metadataProperty) {
    this.metaDataImpl = metadataProperty.getProperty();
  }

  @Override
  @XmlTransient
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

  @XmlElement(name = "featureMember", namespace = CalculatorSchema.NAMESPACE)
  public List<FeatureMemberProperty> getFeatureMemberProperties() {
    return featureMembers;
  }

  @Override
  @XmlTransient
  public List<FeatureMember> getFeatureMembers() {
    final ArrayList<FeatureMember> members = new ArrayList<>();
    for (final FeatureMemberProperty property : featureMembers) {
      members.add(property.getProperty());
    }
    return members;
  }

  @Override
  public void setFeatureMembers(final List<FeatureMember> sources) {
    this.featureMembers = new ArrayList<>();
    for (final FeatureMember fm : sources) {
      if (fm instanceof FeatureMemberImpl) {
        featureMembers.add(new FeatureMemberProperty((FeatureMemberImpl) fm));
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
