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

import java.util.List;

import nl.overheid.aerius.shared.domain.v2.base.EmissionReduction;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKMeasure;
import nl.overheid.aerius.shared.domain.v2.cimlk.CIMLKMeasureFeature;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
public final class CIMLKMeasureValidator {

  CIMLKMeasureValidator() {
    // Util class
  }

  public static void validateMeasures(final List<CIMLKMeasureFeature> measures, final List<AeriusException> exceptions,
      final List<AeriusException> warnings) {
    checkNegativeFactors(measures, exceptions);
  }

  private static void checkNegativeFactors(final List<CIMLKMeasureFeature> measureFeatures, final List<AeriusException> exceptions) {
    for (final CIMLKMeasureFeature feature : measureFeatures) {
      final CIMLKMeasure measure = feature.getProperties();
      measure.getVehicleMeasures().stream()
          .flatMap(x -> x.getEmissionReductions().stream())
          .map(EmissionReduction::getFactor)
          .filter(x -> x < 0)
          .findFirst()
          .ifPresent(negativeValue -> exceptions
              .add(new AeriusException(ImaerExceptionReason.UNEXPECTED_NEGATIVE_VALUE, measure.getLabel(), String.valueOf(negativeValue))));
    }
  }

}
