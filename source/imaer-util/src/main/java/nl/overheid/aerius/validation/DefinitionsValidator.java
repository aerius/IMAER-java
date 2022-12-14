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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import nl.overheid.aerius.shared.domain.v2.characteristics.CustomDiurnalVariation;
import nl.overheid.aerius.shared.domain.v2.characteristics.CustomDiurnalVariationType;
import nl.overheid.aerius.shared.domain.v2.scenario.Definitions;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
public final class DefinitionsValidator {

  private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.###", DecimalFormatSymbols.getInstance(Locale.ROOT));

  private DefinitionsValidator() {
    //Util class
  }

  public static void validateDefinitions(final Definitions definitions, final List<AeriusException> errors,
      final List<AeriusException> warnings) {
    definitions.getCustomDiurnalVariations().stream()
        .flatMap(diurnalVariation -> validateCustomDiurnalVariationType(diurnalVariation).stream())
        .forEach(errors::add);
  }

  private static Optional<AeriusException> validateCustomDiurnalVariationType(final CustomDiurnalVariation diurnalVariation) {
    final CustomDiurnalVariationType customType = diurnalVariation.getType();
    if (customType == null) {
      return Optional.of(new AeriusException(ImaerExceptionReason.CUSTOM_DIURNAL_VARIATION_TYPE_UNKNOWN, "null"));
    } else if (customType.getExpectedNumberOfValues() != diurnalVariation.getValues().size()) {
      return Optional.of(new AeriusException(ImaerExceptionReason.CUSTOM_DIURNAL_VARIATION_INVALID_COUNT,
          String.valueOf(customType.getExpectedNumberOfValues()),
          String.valueOf(diurnalVariation.getValues().size())));
    }
    final double valuesSum = customType.sum(diurnalVariation.getValues());
    final double expectedSum = customType.getExpectedTotalNumberOfValues();
    if (Math.abs(expectedSum - valuesSum) >= CustomDiurnalVariation.ALLOWED_EPSILON) {
      return Optional.of(new AeriusException(ImaerExceptionReason.CUSTOM_DIURNAL_VARIATION_INVALID_SUM,
          DECIMAL_FORMAT.format(expectedSum),
          DECIMAL_FORMAT.format(valuesSum)));
    }
    return Optional.empty();
  }
}
