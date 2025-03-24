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

import nl.overheid.aerius.gml.base.characteristics.GML2ADMSSourceCharacteristics;
import nl.overheid.aerius.gml.base.characteristics.GML2OPSSourceCharacteristics;
import nl.overheid.aerius.gml.base.characteristics.GML2SourceCharacteristics;
import nl.overheid.aerius.shared.domain.v2.characteristics.CharacteristicsType;
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
   * @param nameSpace name space of the specific AERIUS GML version
   * @param featureCollectionClass class of the GML specific feature collection
   * @throws AeriusException in case of an error.
   */
  protected GMLVersionReaderFactory(final GMLLegacyCodesSupplier legacyCodeSupplier, final AeriusGMLVersion version, final String namespace,
      final Class<?> featureCollectionClass) throws AeriusException {
    this.version = version;
    this.schemaLocation = version.getSchemaLocation();
    this.namespace = namespace;
    this.featureCollectionClass = featureCollectionClass;
    schema = GMLSchemaFactory.createSchema(schemaLocation);
    legacyCodeConverter = new GMLLegacyCodeConverter(legacyCodeSupplier.getLegacyCodes(version),
        legacyCodeSupplier.getLegacyMobileSourceOffRoadConversions(), legacyCodeSupplier.getLegacyPlanConversions(),
        legacyCodeSupplier.getLegacyFarmLodgingConversions());
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

  public GMLVersionReader createReader(final GMLConversionData conversionData) {
    return createReader(conversionData, gml2SourceCharacteristics(conversionData));
  }

  protected GMLVersionReader createReader(final GMLConversionData conversionData, final GML2SourceCharacteristics<?> sourceCharacteristics) {
    throw new UnsupportedOperationException("Either override this createReader, or the calling createReader method");
  }

  protected GML2SourceCharacteristics<?> gml2SourceCharacteristics(final GMLConversionData conversionData) {
    final CharacteristicsType ct = conversionData.getCharacteristicsType();

    if (ct == CharacteristicsType.OPS) {
      return createGML2OPSSourceCharacteristics(conversionData);
    } else if (ct == CharacteristicsType.ADMS) {
      return createGML2ADMSSourceCharacteristics(conversionData);
    } else {
      throw new IllegalArgumentException("Can't read GML for characteristics of type " + ct + ". This is not implemented.");
    }
  }

  protected GML2OPSSourceCharacteristics createGML2OPSSourceCharacteristics(final GMLConversionData conversionData) {
    return new GML2OPSSourceCharacteristics(conversionData, false);
  }

  protected GML2ADMSSourceCharacteristics createGML2ADMSSourceCharacteristics(final GMLConversionData conversionData) {
    return new GML2ADMSSourceCharacteristics(conversionData);
  }
}
