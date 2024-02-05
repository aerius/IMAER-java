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

import javax.xml.validation.Schema;

import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * Base class for readers of a Specific version of AERIUS GML.
 */
public abstract class GMLVersionReaderFactory {

  private final AeriusGMLVersion version;
  private final String schemaLocation;
  private final String namespace;
  private final Schema schema;
  private final GMLLegacyCodeConverter legacyCodeConverter;
  private final Class<?> featureCollectionClass;

  /**
   * Constructor.
   * @param legacyCodeSupplier The legacy code supplier
   * @param version AERIUS version
   * @param schemaLocation schema location of the specific AERIUS GML version
   * @param nameSpace name space of the specific AERIUS GML version
   * @param featureCollectionClass class of the GML specific feature collection
   * @throws AeriusException in case of an error.
   */
  protected GMLVersionReaderFactory(final GMLLegacyCodesSupplier legacyCodeSupplier, final AeriusGMLVersion version, final String schemaLocation,
      final String namespace, final Class<?> featureCollectionClass) throws AeriusException {
    this.version = version;
    this.schemaLocation = schemaLocation;
    this.namespace = namespace;
    this.featureCollectionClass = featureCollectionClass;
    schema = GMLSchemaFactory.createSchema(schemaLocation);
    legacyCodeConverter = new GMLLegacyCodeConverter(legacyCodeSupplier.getLegacyCodes(version),
        legacyCodeSupplier.getLegacyMobileSourceOffRoadConversions(), legacyCodeSupplier.getLegacyPlanConversions());
  }

  /**
   * Returns the {@link FeatureCollection} class.
   * @return FeatureCollection class
   */
  public Class<?> getFeatureCollectionClass() {
    return featureCollectionClass;
  }

  public GMLLegacyCodeConverter getLegacyCodeConverter() {
    return legacyCodeConverter;
  }

  public String getNamespace() {
    return namespace;
  }

  public String getSchemaLocation() {
    return schemaLocation;
  }

  public AeriusGMLVersion getVersion() {
    return version;
  }

  public Schema getSchema() {
    return schema;
  }

  /**
   *
   * @param conversionData
   * @return
   */
  public abstract GMLVersionReader createReader(final GMLConversionData conversionData);
}
