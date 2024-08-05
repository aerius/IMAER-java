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
import java.util.ArrayList;
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
  private static final Pattern PARSE_ERROR_PATTERN_1 = Pattern.compile("ParseError at \\[row,col\\]:\\[(\\d+),(\\d+)\\]");
  private static final Pattern PARSE_ERROR_PATTERN_2 = Pattern.compile("The element type \"([^\"]+)\"[^\"]+\"([^\"]+)");
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
      errors.addAll(checkEvents(vec.getEvents()));
      if (errors.isEmpty()) {
        return gmlr;
      }
      return null;
    } catch (final XMLStreamException e) {
      throw newGmlParseError(e);
    }
  }

  public ValidationHelper createValidationHelper() {
    return gmlHelper.getValidationHelper();
  }

  private static List<AeriusException> checkEvents(final List<ValidationEvent> events) {
    // Return any error events found.
    if ((events != null) && !events.isEmpty()) {
      return newValidationFailed(events);
    }
    return List.of();
  }

  private static FeatureCollection convertToCollection(final XMLStreamReader xmlStreamReader, final GMLVersionReaderFactory factory,
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
      processException(e);
    }
    return collection;
  }

  private static void processException(final JAXBException e) throws AeriusException {
    if ((e.getLinkedException() instanceof UTFDataFormatException) || (e.getLinkedException() instanceof UnsupportedEncodingException)) {
      throw new AeriusException(ImaerExceptionReason.GML_ENCODING_INCORRECT);
    } else if (e.getLinkedException() instanceof final XMLStreamException streamException) {
      throw newGmlParseError(streamException);
    }
  }

  private static AeriusException newGmlParseError(final XMLStreamException e) {
    final Matcher matcher1 = PARSE_ERROR_PATTERN_1.matcher(e.getMessage());
    final Matcher matcher2 = PARSE_ERROR_PATTERN_2.matcher(e.getMessage());
    if (matcher1.find() && matcher2.find()) {
      return new AeriusException(ImaerExceptionReason.GML_PARSE_ERROR, matcher1.group(1), matcher1.group(2), matcher2.group(1), matcher2.group(2));
    } else {
      LOG.error("Could not parse gml syntax error:", e);
      return new AeriusException(ImaerExceptionReason.GML_GENERIC_PARSE_ERROR, e.getMessage());
    }
  }

  private static List<AeriusException> newValidationFailed(final List<ValidationEvent> list) {
    final List<AeriusException> errors = new ArrayList<>();
    for (final ValidationEvent event : list) {
      final String errorMessage = event.getMessage().trim();
      final AeriusException error = new AeriusException(ImaerExceptionReason.GML_VALIDATION_FAILED, errorMessage);
      errors.add(error);
      LOG.debug("validation error: {}", errorMessage);
    }
    return errors;
  }
}
