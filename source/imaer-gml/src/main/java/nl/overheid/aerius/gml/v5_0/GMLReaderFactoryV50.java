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
package nl.overheid.aerius.gml.v5_0;

import nl.overheid.aerius.gml.base.AeriusGMLVersion;
import nl.overheid.aerius.gml.base.GMLConversionData;
import nl.overheid.aerius.gml.base.GMLLegacyCodesSupplier;
import nl.overheid.aerius.gml.base.GMLVersionReader;
import nl.overheid.aerius.gml.base.GMLVersionReaderFactory;
import nl.overheid.aerius.gml.base.characteristics.GML2SourceCharacteristicsAdapter;
import nl.overheid.aerius.gml.v5_0.base.CalculatorSchema;
import nl.overheid.aerius.gml.v5_0.collection.FeatureCollectionImpl;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 * {@link GMLVersionReaderFactory} for AERIUS GML version 5.0.
 */
public class GMLReaderFactoryV50 extends GMLVersionReaderFactory {

  /**
   * Constructor.
   * @param legacyCodesSupplier
   * @throws AeriusException error
   */
  public GMLReaderFactoryV50(final GMLLegacyCodesSupplier legacyCodesSupplier) throws AeriusException {
    super(legacyCodesSupplier, AeriusGMLVersion.V5_0, CalculatorSchema.NAMESPACE, FeatureCollectionImpl.class);
  }

  @Override
  public GMLVersionReader createReader(final GMLConversionData conversionData) {
    return new GMLReader<>(conversionData, new GML2SourceCharacteristicsAdapter<>(conversionData, true));
  }
}
