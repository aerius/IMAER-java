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
package nl.overheid.aerius.gml;

import java.util.ArrayList;
import java.util.List;

import nl.overheid.aerius.gml.base.AeriusGMLVersion;
import nl.overheid.aerius.gml.base.FeatureCollection;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.GMLHelper;
import nl.overheid.aerius.gml.base.GMLVersionReader;
import nl.overheid.aerius.gml.base.GMLVersionReaderFactory;
import nl.overheid.aerius.shared.domain.Theme;
import nl.overheid.aerius.shared.domain.calculation.CalculationSetOptions;
import nl.overheid.aerius.shared.domain.scenario.SituationType;
import nl.overheid.aerius.shared.domain.v2.building.BuildingFeature;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKCorrection;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKDispersionLineFeature;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKMeasureFeature;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.scenario.Definitions;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Class to read data from a feature collection that was created from an IMAER GML.
 * This class maintains the state of a IMAER GML read and is initialized with that data.
 */
public final class GMLReader {

  private final GMLHelper gmlHelper;
  private final GMLVersionReaderFactory factory;
  private final FeatureCollection featureCollection;
  private final GMLMetaDataReader metaDataReader;
  private final GMLVersionReader versionReader;
  private final GMLConversionData conversionData;
  private final GMLCalculationSetOptionsReader calculationSetOptionsReader;

  /**
   * Constructor.
   *
   * @param gmlHelper The GML Helper
   * @param factory specific version factory
   * @param featureCollection the feature collection with parsed IMAER GML data
   * @param errors list to add errors on
   * @param warnings list to add warnings on
   * @throws AeriusException
   */
  GMLReader(final GMLHelper gmlHelper, final GMLVersionReaderFactory factory, final FeatureCollection featureCollection,
      final List<AeriusException> errors, final List<AeriusException> warnings) throws AeriusException {
    this.gmlHelper = gmlHelper;
    this.factory = factory;
    this.featureCollection = featureCollection;
    metaDataReader = new GMLMetaDataReader(featureCollection);
    calculationSetOptionsReader = new GMLCalculationSetOptionsReader(featureCollection);
    conversionData = new GMLConversionData(gmlHelper, factory.getLegacyCodeConverter(), errors, warnings);
    versionReader = factory.createReader(conversionData);
  }

  public AeriusGMLVersion getVersion() {
    return factory.getVersion();
  }

  public GMLMetaDataReader metaDataReader() {
    return metaDataReader;
  }

  /**
   * Returns the {@link EmissionSourceFeature}s from the feature collection.
   * @return List of sources
   */
  public List<EmissionSourceFeature> readEmissionSourceList() {
    return toEmissionSources();
  }

  /**
   * Returns the {@link CalculationSetOptions} from the feature collection.
   * @param theme the theme for which to read options.
   * @return the {@link CalculationSetOptions}
   */
  public CalculationSetOptions readCalculationSetOptions(final Theme theme) {
    return calculationSetOptionsReader.readCalculationSetOptions(theme);
  }

  /**
   * Ensure the sources have the correct emissions (should only be called for valid sources).
   * @param emissionSourceList List of sources to set emissions for
   * @param year year used to set the emissions values.
   * @throws AeriusException error
   */
  public void enforceEmissions(final List<EmissionSourceFeature> emissionSourceList, final int year) throws AeriusException {
    //ensure emissions are set (where needed)
    gmlHelper.enforceEmissions(year, emissionSourceList);
  }

  /**
   * Gets the name from the feature collection.
   */
  public String getName() {
    return featureCollection == null ? "" : featureCollection.getName();
  }

  /**
   * Gets the situation type from the feature collection.
   */
  public SituationType getSituationType() {
    final SituationType situationType = featureCollection == null ? null : featureCollection.getSituationType();
    // Allow deprecated type in GML, but force it to the right type for further usage.
    return situationType == SituationType.NETTING ? SituationType.OFF_SITE_REDUCTION : situationType;
  }

  /**
   * Gets the netting factor from the feature collection.
   */
  public Double getNettingFactor() {
    return featureCollection == null ? null : featureCollection.getNettingFactor();
  }

  private List<EmissionSourceFeature> toEmissionSources() {
    final List<EmissionSourceFeature> emissionSourceList = new ArrayList<>();
    if ((featureCollection != null) && (featureCollection.getFeatureMembers() != null)) {
      emissionSourceList.addAll(versionReader.sourcesFromGML(featureCollection.getFeatureMembers()));
      emissionSourceList.addAll(conversionData.getExtraSources());
      emissionSourceList.addAll(conversionData.getInlandRoutes().keySet());
      emissionSourceList.addAll(conversionData.getMaritimeInlandRoutes().keySet());
      emissionSourceList.addAll(conversionData.getMaritimeMaritimeRoutes().keySet());
    }
    return emissionSourceList;
  }

  /**
   * Retrieve all receptor points (domain objects) from a list of FeatureMembers.
   * Only features extending AbstractCalculationPoint will be handled (AeriusPoint, CustomCalculationPoint, etc).
   * @param includeResults if true also read results from GML
   * @return List of all calculation points defined in the GML
   */
  public List<CalculationPointFeature> getAeriusPoints(final boolean includeResults) {
    final List<CalculationPointFeature> points = new ArrayList<>();
    if ((featureCollection != null) && (featureCollection.getFeatureMembers() != null)) {
      points.addAll(versionReader.calculationPointsFromGML(featureCollection.getFeatureMembers(), includeResults));
    }
    return points;
  }

  /**
   * Retrieve all CIMLK Measure (domain objects) from the feature collection.
   * @return List of all CIMLK measures defined in the GML
   */
  public List<CIMLKMeasureFeature> getCIMLKMeasures() {
    List<CIMLKMeasureFeature> measures = new ArrayList<>();
    if ((featureCollection != null) && (featureCollection.getFeatureMembers() != null)) {
      measures = versionReader.cimlkMeasuresFromGML(featureCollection.getFeatureMembers());
    }
    return measures;
  }

  /**
   * Retrieve all CIMLK Dispersion Lines (domain objects) from the feature collection.
   * @return List of all CIMLK dispersion lines defined in the GML
   */
  public List<CIMLKDispersionLineFeature> getCIMLKDispersionLines() {
    List<CIMLKDispersionLineFeature> dispersionLines = new ArrayList<>();
    if ((featureCollection != null) && (featureCollection.getFeatureMembers() != null)) {
      dispersionLines = versionReader.cimlkDispersionLinesFromGML(featureCollection.getFeatureMembers());
    }
    return dispersionLines;
  }

  /**
   * Retrieve all CIMLK Corrections (domain objects) from the feature collection.
   * @return List of all CIMLK corrections defined in the GML
   */
  public List<CIMLKCorrection> getCIMLKCorrections() {
    List<CIMLKCorrection> corrections = new ArrayList<>();
    if ((featureCollection != null) && (featureCollection.getFeatureMembers() != null)) {
      corrections = versionReader.cimlkCorrectionsFromGML(featureCollection.getFeatureMembers());
    }
    return corrections;
  }

  /**
   * Retrieve all buildings (domain objects) from the feature collection.
   * @return List of all buildings defined in the GML
   */
  public List<BuildingFeature> getBuildings() {
    List<BuildingFeature> buildings = new ArrayList<>();
    if (featureCollection != null && featureCollection.getFeatureMembers() != null) {
      buildings = versionReader.buildingsFromGML(featureCollection.getFeatureMembers());
      buildings.addAll(conversionData.getExtraBuildings());
    }
    return buildings;
  }

  /**
   * Retrieve all Definitions (domain object) from the feature collection.
   * @return List of all definitions defined in the GML
   */
  public Definitions getDefinitions() {
    Definitions definitions = new Definitions();
    if ((featureCollection != null) && (featureCollection.getDefinitions() != null)) {
      definitions = versionReader.definitionsFromGML(featureCollection.getDefinitions());
    }
    return definitions;
  }

}
