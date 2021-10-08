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
package nl.overheid.aerius.validation;

import java.util.ArrayList;
import java.util.List;

import nl.overheid.aerius.shared.domain.v2.geojson.IsFeature;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
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

/**
 * Visitor implementation to validate emission sources.
 */
public class ValidationVisitor implements EmissionSourceVisitor<Boolean> {

  private final List<AeriusException> errors;
  private final List<AeriusException> warnings;
  private final ValidationHelper validationHelper;

  public ValidationVisitor(final List<AeriusException> errors, final List<AeriusException> warnings, final ValidationHelper validationHelper) {
    this.errors = errors;
    this.warnings = warnings;
    this.validationHelper = validationHelper;
  }

  /**
   * Validate the list of sources. Will remove any source that is considered invalid.
   */
  public void visitSources(final List<EmissionSourceFeature> sources) {
    final List<EmissionSourceFeature> invalid = new ArrayList<>();
    for (final EmissionSourceFeature source : sources) {
      try {
        final boolean valid = source.accept(this);
        if (!valid) {
          invalid.add(source);
        }
      } catch (final AeriusException e) {
        errors.add(e);
        invalid.add(source);
      }
    }
    sources.removeAll(invalid);
  }

  @Override
  public Boolean visit(final FarmLodgingEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return new FarmLodgingValidator(errors, warnings, validationHelper.farmLodgingValidation()).validate(emissionSource, feature);
  }

  @Override
  public Boolean visit(final FarmlandEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return new FarmlandValidator(errors, warnings, validationHelper.farmlandValidation()).validate(emissionSource, feature);
  }

  @Override
  public Boolean visit(final GenericEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    //NO-OP
    return true;
  }

  @Override
  public Boolean visit(final MooringInlandShippingEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return new MooringInlandShippingValidator(errors, warnings, validationHelper.inlandShippingValidation()).validate(emissionSource, feature);
  }

  @Override
  public Boolean visit(final InlandShippingEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return new RouteInlandShippingValidator(errors, warnings, validationHelper.inlandShippingValidation()).validate(emissionSource, feature);
  }

  @Override
  public Boolean visit(final MooringMaritimeShippingEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return new MooringMaritimeShippingValidator(errors, warnings, validationHelper.maritimeShippingValidation()).validate(emissionSource, feature);
  }

  @Override
  public Boolean visit(final MaritimeShippingEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return new RouteMaritimeShippingValidator(errors, warnings, validationHelper.maritimeShippingValidation()).validate(emissionSource, feature);
  }

  @Override
  public Boolean visit(final OffRoadMobileEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return new OffRoadValidator(errors, warnings, validationHelper.offRoadMobileValidation()).validate(emissionSource, feature);
  }

  @Override
  public Boolean visit(final PlanEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return new PlanValidator(errors, warnings, validationHelper.planValidation()).validate(emissionSource, feature);
  }

  @Override
  public Boolean visit(final SRM1RoadEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return new RoadValidator(errors, warnings, validationHelper.roadValidation()).validate(emissionSource, feature);
  }

  @Override
  public Boolean visit(final SRM2RoadEmissionSource emissionSource, final IsFeature feature) throws AeriusException {
    return new RoadValidator(errors, warnings, validationHelper.roadValidation()).validate(emissionSource, feature);
  }

}
