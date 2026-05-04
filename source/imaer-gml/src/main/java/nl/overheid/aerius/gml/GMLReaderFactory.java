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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.ValidationEvent;
import jakarta.xml.bind.ValidationEventLocator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.opengis.gml.v_3_2.ObjectFactory;

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

  public GMLReaderFactory(final GMLHelper gmlHelper) throws AeriusException {
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
    } // Rely on custom event collection instead
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
    // Group events that share a source location (line + column), discard the ones whose
    // information is already covered by a higher-severity event at the same location, then
    // combine the survivors into one message per location. Each combined message is prefixed
    // with [line N, col M] so users can find the offending value.
    final Map<LocationKey, List<ValidationEvent>> grouped = new LinkedHashMap<>();
    for (final ValidationEvent event : list) {
      grouped.computeIfAbsent(LocationKey.of(event.getLocator()), k -> new ArrayList<>()).add(event);
    }

    final List<AeriusException> errors = new ArrayList<>();
    for (final Map.Entry<LocationKey, List<ValidationEvent>> entry : grouped.entrySet()) {
      final List<ValidationEvent> kept = discardRedundantLowerSeverityEvents(entry.getValue());
      final String body = kept.stream()
          .map(e -> e.getMessage().trim())
          .collect(Collectors.joining(" "));
      final String message = entry.getKey().prefix() + body;
      errors.add(new AeriusException(ImaerExceptionReason.GML_VALIDATION_FAILED, message));
      LOG.debug("validation error: {}", message);
    }
    return errors;
  }

  /**
   * Drops events at a single source location whose severity is below the maximum present in the
   * group. <strong>This is a deliberate, lossy step</strong>: the dropped events are not surfaced
   * to the user.
   *
   * <p>What gets dropped, concretely: when JAXB fails to parse a primitive value (e.g. {@code
   * "None"} where a {@code double} is expected), the validator reports it twice for the same
   * location:
   * <ul>
   *   <li>a severity-1 ({@code ERROR}) event whose message is just the offending value, e.g.
   *       {@code "None"}, originating from the wrapped {@code NumberFormatException};</li>
   *   <li>one or more severity-2 ({@code FATAL_ERROR}) {@code cvc-*} events from the XSD
   *       validator, e.g. {@code "cvc-datatype-valid.1.2.1: 'None' is not a valid value for
   *       'double'."} and {@code "cvc-type.3.1.3: The value 'None' of element 'imaer:foo' is not
   *       valid."}.</li>
   * </ul>
   * The lower-severity message conveys nothing the higher-severity ones don't already say
   * (offending value, element, expected type), so dropping it removes only redundant noise.
   */
  private static List<ValidationEvent> discardRedundantLowerSeverityEvents(final List<ValidationEvent> sameLocation) {
    final int maxSeverity = sameLocation.stream().mapToInt(ValidationEvent::getSeverity).max().orElse(0);
    return sameLocation.stream()
        .filter(e -> e.getSeverity() == maxSeverity)
        .toList();
  }

  private record LocationKey(int line, int column) {
    static LocationKey of(final ValidationEventLocator locator) {
      return locator == null ? new LocationKey(-1, -1) : new LocationKey(locator.getLineNumber(), locator.getColumnNumber());
    }

    String prefix() {
      if (line < 0 && column < 0) {
        return "";
      }
      if (line >= 0 && column >= 0) {
        return "[line " + line + ", col " + column + "] ";
      }
      return line >= 0 ? "[line " + line + "] " : "[col " + column + "] ";
    }
  }
}
