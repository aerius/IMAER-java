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

import java.net.URL;
import java.util.EnumMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
public final class GMLSchemaFactory {

  private static final Logger LOG = LoggerFactory.getLogger(GMLSchemaFactory.class);

  private static final String CATALOG_LOCATION = "/gml-catalog.xml";
  private static final Map<AeriusGMLVersion, String> SCHEMA_LOCATIONS = new EnumMap<>(AeriusGMLVersion.class);

  static {
    SCHEMA_LOCATIONS.put(AeriusGMLVersion.V0_5, nl.overheid.aerius.gml.v0_5.base.CalculatorSchema.SCHEMA_LOCATION);
    SCHEMA_LOCATIONS.put(AeriusGMLVersion.V1_0, nl.overheid.aerius.gml.v1_0.base.CalculatorSchema.SCHEMA_LOCATION);
    SCHEMA_LOCATIONS.put(AeriusGMLVersion.V1_1, nl.overheid.aerius.gml.v1_1.base.CalculatorSchema.SCHEMA_LOCATION);
    SCHEMA_LOCATIONS.put(AeriusGMLVersion.V2_0, nl.overheid.aerius.gml.v2_0.base.CalculatorSchema.SCHEMA_LOCATION);
    SCHEMA_LOCATIONS.put(AeriusGMLVersion.V2_1, nl.overheid.aerius.gml.v2_1.base.CalculatorSchema.SCHEMA_LOCATION);
    SCHEMA_LOCATIONS.put(AeriusGMLVersion.V2_2, nl.overheid.aerius.gml.v2_2.base.CalculatorSchema.SCHEMA_LOCATION);
    SCHEMA_LOCATIONS.put(AeriusGMLVersion.V3_0, nl.overheid.aerius.gml.v3_0.base.CalculatorSchema.SCHEMA_LOCATION);
    SCHEMA_LOCATIONS.put(AeriusGMLVersion.V3_1, nl.overheid.aerius.gml.v3_1.base.CalculatorSchema.SCHEMA_LOCATION);
    SCHEMA_LOCATIONS.put(AeriusGMLVersion.V4_0, nl.overheid.aerius.gml.v4_0.base.CalculatorSchema.SCHEMA_LOCATION);
    SCHEMA_LOCATIONS.put(AeriusGMLVersion.V5_0, nl.overheid.aerius.gml.v5_0.base.CalculatorSchema.SCHEMA_LOCATION);
    SCHEMA_LOCATIONS.put(AeriusGMLVersion.V5_1, nl.overheid.aerius.gml.v5_1.base.CalculatorSchema.SCHEMA_LOCATION);
  }

  private GMLSchemaFactory() {
    // Util-like
  }

  public static Schema createSchema(final AeriusGMLVersion version) throws AeriusException {
    return createSchema(SCHEMA_LOCATIONS.get(version));
  }

  public static Schema createSchema(final String schemaLocation) throws AeriusException {
    final CatalogResolver catalogResolver = new CatalogResolver(GMLSchemaFactory.class.getResource(CATALOG_LOCATION).toString());
    final URL xsdURL = GMLSchemaFactory.class.getResource(schemaLocation);
    try {
      final SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      // disable DOCTYPE declarations, fixes XXE vulnerability
      sf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      sf.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
      // Explicitly allow file, as we're referencing some other local files.
      sf.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "file");
      // use a catalogresolver to avoid calling non-local xsd's
      sf.setResourceResolver(catalogResolver);
      return sf.newSchema(xsdURL);
    } catch (final SAXException e) {
      LOG.error("Parsing the AERIUS GML schema {} failed.", schemaLocation, e);
      throw new AeriusException(ImaerExceptionReason.INTERNAL_ERROR, e.getMessage());
    }
  }

}
