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
package nl.overheid.aerius.gml.base;

import java.util.List;

import nl.overheid.aerius.gml.base.metadata.LegacySituationType;
import nl.overheid.aerius.shared.domain.scenario.SituationType;

/**
 * Interface for an AERIUS GML featurecollection, intended for use with JAXB-objects.
 */
public interface FeatureCollection {

  /**
   * @return The metadata for this collection.
   */
  MetaData getMetaData();

  /**
   * Set meta data.
   * @param metaData medaData
   */
  void setMetaData(MetaData metaData);

  /**
   * @return The name of the situation contained in this collection.
   */
  String getName();

  /**
   * @return The year for which the emissionsources were exported..
   */
  Integer getYear();

  /**
   * @return The type of the situation contained in this collection.
   */
  default SituationType getSituationType() {
    return getMetaData() == null ? null : getMetaData().getSituationType();
  }

  /**
   * @return The netting factor to be used for the situation in this collection.
   */
  default Double getNettingFactor() {
    return getMetaData() == null ? null : getMetaData().getNettingFactor();
  }

  /**
   * @return The featuremembers containing all emissionsources and receptors exported/imported in jaxb-objects.
   */
  List<FeatureMember> getFeatureMembers();

  /**
   * Set feature members.
   * @param featureMembers featureMembers
   */
  void setFeatureMembers(List<FeatureMember> featureMembers);

  /**
   * @return The definitions for the situation in this collection.
   */
  default Definitions getDefinitions() {
    return null;
  }

  default void setDefinitions(final Definitions definitions) {
    // NO-OP
  }

}
