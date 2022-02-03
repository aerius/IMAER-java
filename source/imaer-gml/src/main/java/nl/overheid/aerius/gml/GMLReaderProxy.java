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

import javax.xml.namespace.NamespaceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.overheid.aerius.gml.base.AeriusGMLVersion;
import nl.overheid.aerius.gml.base.GMLLegacyCodesSupplier;
import nl.overheid.aerius.gml.base.GMLVersionReaderFactory;
import nl.overheid.aerius.gml.v0_5.GMLReaderFactoryV05;
import nl.overheid.aerius.gml.v1_0.GMLReaderFactoryV10;
import nl.overheid.aerius.gml.v1_1.GMLReaderFactoryV11;
import nl.overheid.aerius.gml.v2_0.GMLReaderFactoryV20;
import nl.overheid.aerius.gml.v2_1.GMLReaderFactoryV21;
import nl.overheid.aerius.gml.v2_2.GMLReaderFactoryV22;
import nl.overheid.aerius.gml.v3_0.GMLReaderFactoryV30;
import nl.overheid.aerius.gml.v3_1.GMLReaderFactoryV31;
import nl.overheid.aerius.gml.v4_0.GMLReaderFactoryV40;
import nl.overheid.aerius.gml.v5_0.GMLReaderFactoryV50;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * The {@link GMLReaderProxy} is used to determine the specific IMAER version reader factory to be used to process an IMAER GML.
 * This proxy maintains the list of all supported IMAER versions.
 */
class GMLReaderProxy {

  private static final Logger LOG = LoggerFactory.getLogger(GMLReaderProxy.class);

  private final List<GMLVersionReaderFactory> factories = new ArrayList<>();

  public GMLReaderProxy(final GMLLegacyCodesSupplier legacyCodesSupplier) throws AeriusException {
    factories.add(new GMLReaderFactoryV05(legacyCodesSupplier));
    factories.add(new GMLReaderFactoryV10(legacyCodesSupplier));
    factories.add(new GMLReaderFactoryV11(legacyCodesSupplier));
    factories.add(new GMLReaderFactoryV20(legacyCodesSupplier));
    factories.add(new GMLReaderFactoryV21(legacyCodesSupplier));
    factories.add(new GMLReaderFactoryV22(legacyCodesSupplier));
    factories.add(new GMLReaderFactoryV30(legacyCodesSupplier));
    factories.add(new GMLReaderFactoryV31(legacyCodesSupplier));
    factories.add(new GMLReaderFactoryV40(legacyCodesSupplier));
    factories.add(new GMLReaderFactoryV50(legacyCodesSupplier));
  }

  /**
   * Find the reader matching the name space.
   * @param nameSpaceContext The name space context to determine the IMAER GML version for.
   * @return The reader that fits the name space
   * @throws AeriusException in case the version could not be determined
   */
  public GMLVersionReaderFactory determineReaderFactory(final NamespaceContext nameSpaceContext) throws AeriusException {
    if (nameSpaceContext != null) {
      for (final GMLVersionReaderFactory factory : factories) {
        if (nameSpaceContext.getPrefix(factory.getNamespace()) != null) {
          return factory;
        }
      }
    }
    final String namespaceUri = nameSpaceContext == null ? "" : nameSpaceContext.getNamespaceURI("imaer");
    LOG.error("Could not determine version for GML file. Namespace in gml: {}", namespaceUri);
    throw new AeriusException(ImaerExceptionReason.GML_VERSION_NOT_SUPPORTED, namespaceUri == null ? "no_imaer" : namespaceUri);
  }

  /**
   * Find the reader matching the IMAER version.
   * @param version The IMAER version to find matching reader for.
   * @return The reader that fits the IMAER version
   * @throws AeriusException in case the version could not be determined
   */
  public GMLVersionReaderFactory determineReaderFactory(final AeriusGMLVersion version) throws AeriusException {
    return factories.stream()
        .filter(i -> i.getVersion() == version)
        .findFirst()
        .orElseThrow(() -> new AeriusException(ImaerExceptionReason.GML_VERSION_NOT_SUPPORTED, version.toString()));
  }
}
