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
package nl.overheid.aerius.schematron;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.oclc.purl.dsdl.svrl.FailedAssert;
import org.oclc.purl.dsdl.svrl.SchematronOutputType;
import org.oclc.purl.dsdl.svrl.SuccessfulReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.io.stream.StringInputStream;
import com.helger.schematron.pure.SchematronResourcePure;
import com.helger.schematron.pure.errorhandler.CollectingPSErrorHandler;
import com.helger.schematron.pure.errorhandler.IPSErrorHandler;

import nl.overheid.aerius.shared.domain.calculation.Profile;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Schematron based validator for IMAER GML files.
 */
public class ImaerSchematronValidator {
  private static final Logger LOG = LoggerFactory.getLogger(ImaerSchematronValidator.class);

  private final SchematronResourcePure schematronResourcePure;

  /**
   * Creates a new Schematron validator dedicated for the given profile.
   *
   * @param profile profile to configure validator for
   * @throws AeriusException
   */
  public ImaerSchematronValidator(final Profile profile) throws AeriusException {
    schematronResourcePure = createSchematronResource(ImaerSchematronProfileValidation.getValidations(profile).getSchema());
  }

  public ImaerSchematronValidator(final ImaerSchematronValidation validation) throws AeriusException, IOException {
    this(Collections.singletonList(validation));
  }

  public ImaerSchematronValidator(final List<ImaerSchematronValidation> validations) throws AeriusException, IOException {
    schematronResourcePure = createSchematronResource(ImaerSchematronResourceBuilder.buildSchematron(validations));
  }

  public void validate(final Supplier<String> stringToValidateSupplier, final List<AeriusException> errors,
      final List<AeriusException> warnings) throws AeriusException {
    try (InputStream is = new StringInputStream(stringToValidateSupplier.get(), StandardCharsets.UTF_8)) {
      validateXMLStream(is, errors, warnings);
    } catch (final IOException e) {
      throw new AeriusException(ImaerExceptionReason.GML_VALIDATION_FAILED, e.getMessage());
    }
  }

  /**
   * Validates the given input stream.
   *
   * @param inputStream the input stream to validate
   * @param errors any validation errors are stored here
   * @param warnings any validation warnings are stored here
   * @throws AeriusException
   */
  public void validateXMLStream(final InputStream inputStream, final List<AeriusException> errors,
      final List<AeriusException> warnings) throws AeriusException {
    final Source streamSource = new StreamSource(inputStream);
    final SchematronOutputType schematronOutputType;

    try {
      schematronOutputType = schematronResourcePure.applySchematronValidationToSVRL(streamSource);
    } catch (final Exception e) {
      LOG.debug("Schematron validation failed", e);
      throw new AeriusException(ImaerExceptionReason.SCHEMATRON_ASSERT, "Invalid Schematron source.");
    }
    final List<Object> failedAsserts = schematronOutputType.getActivePatternAndFiredRuleAndFailedAssert();
    for (final Object object : failedAsserts) {
      if (object instanceof FailedAssert) {
        final FailedAssert failedAssert = (FailedAssert) object;
        errors.add(new AeriusException(ImaerExceptionReason.SCHEMATRON_ASSERT, failedAssert.getText().getContent().toString()));

      } else if (object instanceof SuccessfulReport) {
        final SuccessfulReport warning = (SuccessfulReport) object;
        warnings.add(new AeriusException(ImaerExceptionReason.SCHEMATRON_REPORT, warning.getText().getContent().toString()));
      }
    }
  }

  private static SchematronResourcePure createSchematronResource(final String schema) throws AeriusException {
    final SchematronResourcePure schematronResourcePure = SchematronResourcePure.fromString(schema, StandardCharsets.UTF_8);
    final IPSErrorHandler errorHandler = new CollectingPSErrorHandler();
    schematronResourcePure.setErrorHandler(errorHandler);

    final boolean validSchematron = schematronResourcePure.isValidSchematron();

    if (validSchematron) {
      return schematronResourcePure;
    } else {
      throw new AeriusException(ImaerExceptionReason.SCHEMATRON_ASSERT, "Invalid Schematron source.");
    }
  }

}
