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

import java.io.InputStream;
import java.io.UTFDataFormatException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.opengis.gml.v_3_2_1.ObjectFactory;

import nl.overheid.aerius.gml.base.FeatureCollection;
import nl.overheid.aerius.gml.base.GMLHelper;
import nl.overheid.aerius.gml.base.GMLVersionReaderFactory;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.validation.ValidationHelper;

/**
 * Factory class
 */
public final class GMLReaderFactory {
  private static final Logger LOG = LoggerFactory.getLogger(GMLReaderFactory.class);
  private static final XMLInputFactory XMLINPUT_FACTORY = SafeXMLInputFactory.newFactory();
  private static final Object FACTORY_LOCK = new Object();
  private static GMLReaderFactory instance;

  private final GMLReaderProxy readerProxy;
  private final GMLHelper gmlHelper;

  GMLReaderFactory(final GMLHelper gmlHelper) throws AeriusException {
    this(gmlHelper, new GMLReaderProxy(gmlHelper));
  }

  /**
   * Constructor to use this factory with a specific version reader. Only used in tests.
   */
  GMLReaderFactory(final GMLHelper gmlHelper, final GMLReaderProxy readerProxy) {
    this.gmlHelper = gmlHelper;
    this.readerProxy = readerProxy;
  }

  /**
   * Returns the static factory instance.
   * @param gmlHelper
   * @return
   * @throws AeriusException
   */
  public static GMLReaderFactory getFactory(final GMLHelper gmlHelper) throws AeriusException {
    synchronized (FACTORY_LOCK) {
      if (instance == null) {
        instance = new GMLReaderFactory(gmlHelper);
      }
    }
    return instance;
  }

  /**
   * Creates a reader that has the inputstream containing GML parsed and stored in a FeatureCollection in the reader.
   *
   * @param inputStream The stream containing GML
   * @param validate validate xml against schema (slower)
   * @param errors List to add any errors on
   * @param warnings List to add any warnings on
   * @return GMLReader with parsed GML
   * @throws AeriusException When the GML in the inputstream could not be converted to objects.
   */
  public GMLReader createReader(final InputStream inputStream, final boolean validate,
      final List<AeriusException> errors, final List<AeriusException> warnings) throws AeriusException {
    final CustomValidationEventCollector vec = new CustomValidationEventCollector();
    final GMLVersionReaderFactory factory;
    try {
      final XMLStreamReader reader = XMLINPUT_FACTORY.createXMLStreamReader(inputStream);
      //ensure the reader is on the first tag.
      reader.nextTag();
      //determine version of the XML based on the namespaces available.
      factory = readerProxy.determineReaderFactory(reader.getNamespaceContext());
      final GMLReader gmlr = new GMLReader(gmlHelper, factory, convertToCollection(reader, factory, vec, validate), errors, warnings);
      checkEvents(vec.getEvents());
      return gmlr;
    } catch (final XMLStreamException e) {
      throw newGmlParseError(e);
    }
  }

  public ValidationHelper createValidationHelper() {
    return gmlHelper.getValidationHelper();
  }

  private void checkEvents(final List<ValidationEvent> events) throws AeriusException {
    // information like required elements not being present will not throw an exception, but will trigger an event.
    // check for these events and throw exception if present.
    if ((events != null) && !events.isEmpty()) {
      throw newValidationFailed(events);
    }
  }

  private FeatureCollection convertToCollection(final XMLStreamReader xmlStreamReader, final GMLVersionReaderFactory factory,
      final CustomValidationEventCollector vec, final boolean validate) throws AeriusException {
    FeatureCollection collection = null;
    try {
      final JAXBContext jaxbContext = JAXBContextCache.find(factory.getFeatureCollectionClass(), ObjectFactory.class);
      final Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
      // To validate directly against xsd, set the schema.
      unMarshaller.setSchema(validate ? factory.getSchema() : null);
      // catch all validation events.
      unMarshaller.setEventHandler(vec);
      collection = (FeatureCollection) unMarshaller.unmarshal(xmlStreamReader);
    } catch (final NumberFormatException e) {
      throw new AeriusException(ImaerExceptionReason.GML_GENERIC_PARSE_ERROR, e.getMessage());
    } catch (final JAXBException e) {
      processException(vec, e);
    }
    return collection;
  }

  private void processException(final CustomValidationEventCollector vec, final JAXBException e) throws AeriusException {
    if ((e.getLinkedException() instanceof UTFDataFormatException) || (e.getLinkedException() instanceof UnsupportedEncodingException)) {
      throw new AeriusException(ImaerExceptionReason.GML_ENCODING_INCORRECT);
    } else if (e.getLinkedException() instanceof XMLStreamException) {
      throw newGmlParseError((XMLStreamException) e.getLinkedException());
    } else {
      LOG.error("JAXBException occurred.", e);
      throw newValidationFailed(vec.getEvents());
    }
  }

  private AeriusException newGmlParseError(final XMLStreamException e) {
    final Pattern pattern1 = Pattern.compile("ParseError at \\[row,col\\]:\\[(\\d+),(\\d+)\\]");
    final Pattern pattern2 = Pattern.compile("The element type \"([^\"]+)\"[^\"]+\"([^\"]+)");
    final Matcher matcher1 = pattern1.matcher(e.getMessage());
    final Matcher matcher2 = pattern2.matcher(e.getMessage());
    if (matcher1.find() && matcher2.find()) {
      return new AeriusException(ImaerExceptionReason.GML_PARSE_ERROR, matcher1.group(1), matcher1.group(2), matcher2.group(1), matcher2.group(2));
    } else {
      LOG.error("Could not parse gml syntax error:", e);
      return new AeriusException(ImaerExceptionReason.GML_GENERIC_PARSE_ERROR, e.getMessage());
    }
  }

  private AeriusException newValidationFailed(final List<ValidationEvent> list) {
    final StringBuilder errorLine = new StringBuilder();
    for (final ValidationEvent event : list) {
      errorLine.append(event.getMessage());
      errorLine.append(' ');
    }
    LOG.error("validation error: {}", errorLine);
    return new AeriusException(ImaerExceptionReason.GML_VALIDATION_FAILED, errorLine.toString().trim());
  }
}
