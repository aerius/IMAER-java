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

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.building.BuildingFeature;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKCorrection;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKDispersionLineFeature;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKMeasureFeature;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Interface to be implemented by all AERIUS GML versions that are supported to generate a GML from data.
 */
public interface GMLVersionWriter {

  /**
   * Returns the specific AERIUS GML version for the implementing class.
   * @return AERIUS GML version
   */
  AeriusGMLVersion getGMLVersion();

  /**
   * Public schema location for this AERIUS GML version.
   * @return schema location
   */
  String getPublicSchemaLocation();

  /**
   * Returns the name space related to this GML.
   * @return name space
   */
  String getNameSpace();

  /**
   * Creates a new instance of the implementing {@link FeatureCollection}.
   * @return new instance
   */
  FeatureCollection createFeatureCollection();

  /**
   * Converts object meta data to GML meta data object.
   * @param metaDataInput Object containing all information needed for the gml metadata object.
   * @return GML meta data object
   */
  MetaData metaData2GML(MetaDataInput metaDataInput) throws AeriusException;

  /**
   * Converts domain definitions object to GML definitions object.
   * @param metaDataInput Domain object containing definitions.
   * @return GML definitions object
   */
  Definitions definitions2GML(nl.overheid.aerius.shared.domain.v2.scenario.Definitions definitions) throws AeriusException;

  /**
   * Converts an emission source to one or more GML data objects that represent that emission source.
   * While an emission source can have emission values for multiple years and substances, the GML data object has emissions for one specific year.
   * @param source the emission source
   * @param substances the substances for which emissions must be copied.
   * @return GML emission source data objects
   * @throws AeriusException internal or data error
   */
  List<FeatureMember> source2GML(EmissionSourceFeature source, Substance[] substances) throws AeriusException;

  /**
   * Converts a receptor point to a GML data object of that point with results for the given substances.
   * @param point receptor point to convert
   * @param substances substances to get results for
   * @param corrections calculation corrections for the point
   * @return GML receptor point
   * @throws AeriusException internal or data error
   */
  FeatureMember result2GML(CalculationPointFeature point, Substance[] substances, List<CIMLKCorrection> corrections) throws AeriusException;

  /**
   * Converts a CIMLK measure to a GML data object of that measure.
   * @param measure measure to convert
   * @return GML measure
   * @throws AeriusException internal or data error
   */
  FeatureMember cimlkMeasure2GML(CIMLKMeasureFeature measure) throws AeriusException;

  /**
   * Converts a CIMLK dispersion line to a GML data object of that dispersion line.
   * @param dispersionLine dispersion line to convert
   * @return GML dispersion line
   * @throws AeriusException internal or data error
   */
  FeatureMember cimlkDispersionLine2GML(CIMLKDispersionLineFeature dispersionLine) throws AeriusException;

  /**
   * Converts a CIMLK correction to a GML data object of that measure.
   * @param correction correction to convert
   * @return GML correction
   * @throws AeriusException internal or data error
   */
  Object cimlkCorrection2GML(CIMLKCorrection correction) throws AeriusException;

  /**
   * Converts a building to a GML data object of that building.
   * @param building building to convert
   * @return GML building
   * @throws AeriusException internal or data error
   */
  FeatureMember building2GML(BuildingFeature building) throws AeriusException;

}
