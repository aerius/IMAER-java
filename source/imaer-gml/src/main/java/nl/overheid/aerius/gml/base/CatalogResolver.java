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

import java.io.IOException;
import java.net.MalformedURLException;

import javax.xml.parsers.SAXParserFactory;

import org.apache.xml.resolver.Catalog;
import org.apache.xml.resolver.CatalogManager;
import org.apache.xml.resolver.readers.OASISXMLCatalogReader;
import org.apache.xml.resolver.readers.SAXCatalogReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

/**
 * Resource resolver implementation that provides common xsd's from the classpath.
 * <p>
 * Based on the XMLCatalogResolver from Apache Xerces 2.11.0 (http://xerces.apache.org/#xerces2-j).
 */
final class CatalogResolver implements LSResourceResolver {
  private static final Logger LOG = LoggerFactory.getLogger(CatalogResolver.class);

  private final Catalog catalog;
  private final DOMImplementationLS domImpl;

  /**
   * Constructs a catalog resolver with the given list of entry files.
   *
   * @param catalogURIs
   *          an ordered array list of absolute URIs
   */
  CatalogResolver(final String... catalogURIs) {
    try {
      final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
      domImpl = (DOMImplementationLS) registry.getDOMImplementation("LS");

      final CatalogManager catalogManager = new CatalogManager();
      catalogManager.setAllowOasisXMLCatalogPI(false);
      catalogManager.setCatalogClassName("org.apache.xml.resolver.Catalog");
      catalogManager.setCatalogFiles("");
      catalogManager.setIgnoreMissingProperties(true);
      catalogManager.setPreferPublic(true);
      catalogManager.setRelativeCatalogs(false);
      catalogManager.setUseStaticCatalog(false);
      catalogManager.setVerbosity(0);

      catalog = new Catalog(catalogManager);
      attachReaderToCatalog(catalog);
      parseCatalogURIs(catalog, catalogURIs);
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException | IOException e) {
      throw new RuntimeException("Could not initialize catalog resolver.", e);
    }
  }

  private static void parseCatalogURIs(final Catalog catalog, final String... catalogURIs) throws MalformedURLException, IOException {
    if (catalogURIs != null) {
      for (int i = 0; i < catalogURIs.length; ++i) {
        final String catalogURI = catalogURIs[i];

        if (catalogURI != null && catalogURI.length() > 0) {
          catalog.parseCatalog(catalogURI);
        }
      }
    }
  }

  @Override
  public LSInput resolveResource(final String type, final String namespaceURI, final String privateId, final String systemId, final String baseURI) {
    String resolvedId = null;

    try {
      // The namespace is useful for resolving namespace aware
      // grammars such as XML schema. Let it take precedence over
      // the external identifier if one exists.
      if (namespaceURI != null) {
        resolvedId = catalog.resolveURI(namespaceURI);
      }

      // Resolve against an external identifier if one exists. This
      // is useful for resolving DTD external subsets and other
      // external entities. For XML schemas if there was no namespace`
      // mapping we might be able to resolve a system identifier
      // specified as a location hint.
      if (resolvedId == null && systemId != null) {
        if (privateId == null) {
          resolvedId = catalog.resolveSystem(systemId);
        } else {
          resolvedId = catalog.resolvePublic(privateId, systemId);
        }
      }
    } catch (final IOException ex) {
      // Ignore IOException. It cannot be thrown from this method.
      LOG.error("Exception in resolveResource", ex);
    }

    return resolvedId == null ? null : createLSInput(privateId, resolvedId, baseURI);
  }

  private LSInput createLSInput(final String publicId, final String systemId, final String baseURI) {
    final LSInput input = domImpl.createLSInput();
    input.setPublicId(publicId);
    input.setSystemId(systemId);
    input.setBaseURI(baseURI);

    return input;
  }

  private static void attachReaderToCatalog(final Catalog catalog) {
    final SAXParserFactory spf = SAXParserFactory.newInstance();
    spf.setNamespaceAware(true);
    spf.setValidating(false);

    final SAXCatalogReader saxReader = new SAXCatalogReader(spf);
    saxReader.setCatalogParser(OASISXMLCatalogReader.namespaceName, "catalog", OASISXMLCatalogReader.class.getCanonicalName());
    catalog.addReader("application/xml", saxReader);
  }
}
