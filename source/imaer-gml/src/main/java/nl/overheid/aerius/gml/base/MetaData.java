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

import nl.overheid.aerius.shared.domain.scenario.SituationType;

/**
 * Interface for an AERIUS GML metadata object, intended for use with JAXB-objects.
 */
public interface MetaData {

  /**
   * @return The year the export was done for.
   */
  Integer getYear();

  /**
   * @return The AERIUS version in which the export was done.
   */
  String getVersion();

  /**
   * @return The database version used at the time of export.
   */
  String getDatabaseVersion();

  /**
   * @return The name of the emission sources list in the featurecollection.
   */
  String getSituationName();

  /**
   * @return the reference of the emission sources list in the featurecollection.
   */
  String getReference();

  void setReference(String reference);

  /**
   * @return the corporation
   */
  String getCorporation();

  /**
   * @return The name of the emission sources list in the featurecollection.
   */
  String getProjectName();

  /**
   * @return The facility location in the featurecollection.
   */
  Address getFacilityLocation();

  /**
   * @return The duration of a temporary project. Null indicates a non-temporary (permanent) project.
   */
  Integer getTemporaryPeriod();

  /**
   * @return The calculation radius type of the project.
   */
  String getPermitCalculationRadiusType();

  /**
   * @return The description of the scenario in the featurecollection.
   */
  String getDescription();

  /**
   * @return The type of the situation. Null indicates it hasn't been specified (older GMLs).
   */
  default SituationType getSituationType() {
    return null;
  }

  /**
   * @return The netting factor. Null indicates it hasn't been specified.
   * Can be specified for all situation types, but should only be used for netting.
   */
  default Double getNettingFactor() {
    return null;
  }

}
