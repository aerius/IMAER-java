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
import java.util.List;

import com.helger.commons.io.stream.StringInputStream;

import nl.overheid.aerius.shared.domain.v2.geojson.IsFeature;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceVisitor;
import nl.overheid.aerius.shared.domain.v2.source.FarmLodgingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.FarmlandEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.GenericEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.InlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MooringInlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.MooringMaritimeShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.OffRoadMobileEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.PlanEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.SRM1RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.SRM2RoadEmissionSource;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

public class EmissionSourceSchematronVisitor implements EmissionSourceVisitor<Void> {

  private final String gml;
  private final SchematronValidators validators;
  private final List<AeriusException> errors;
  private final List<AeriusException> warnings;

  public EmissionSourceSchematronVisitor(final String gml, final SchematronValidators validators, final List<AeriusException> errors,
      final List<AeriusException> warnings) {
    this.gml = gml;
    this.validators = validators;
    this.errors = errors;
    this.warnings = warnings;
  }

  @Override
  public Void visit(final FarmLodgingEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    //NO-OP
    return null;
  }

  @Override
  public Void visit(final FarmlandEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    //NO-OP
    return null;
  }

  @Override
  public Void visit(final PlanEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    //NO-OP
    return null;
  }

  @Override
  public Void visit(final OffRoadMobileEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    //NO-OP
    return null;
  }

  @Override
  public Void visit(final SRM1RoadEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    validate(validators.getSrm1RoadValidator());
    return null;
  }

  @Override
  public Void visit(final SRM2RoadEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    validate(validators.getSrm2RoadValidator());
    return null;
  }

  @Override
  public Void visit(final InlandShippingEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    //NO-OP
    return null;
  }

  @Override
  public Void visit(final MooringInlandShippingEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    //NO-OP
    return null;
  }

  @Override
  public Void visit(final MaritimeShippingEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    //NO-OP
    return null;
  }

  @Override
  public Void visit(final MooringMaritimeShippingEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    //NO-OP
    return null;
  }

  @Override
  public Void visit(final GenericEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    //NO-OP
    return null;
  }

  private void validate(final ImaerSchematronValidator validator)
      throws AeriusException {
    try (InputStream is = new StringInputStream(gml, StandardCharsets.UTF_8)) {
      validator.validateXMLStream(is, errors, warnings);
    } catch (final IOException e) {
      throw new AeriusException(ImaerExceptionReason.GML_VALIDATION_FAILED, e.getMessage());
    }
  }

}
