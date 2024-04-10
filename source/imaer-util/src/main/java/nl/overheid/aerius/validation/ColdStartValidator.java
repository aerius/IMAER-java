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
import java.util.Map.Entry;

import nl.overheid.aerius.shared.domain.v2.source.ColdStartEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.CustomVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.SpecificVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardColdStartVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.Vehicles;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Validator for {@link ColdStartEmissionSource}.
 */
class ColdStartValidator extends SourceValidator<ColdStartEmissionSource> {

  private final ColdStartValidationHelper validationHelper;

  ColdStartValidator(final List<AeriusException> errors, final List<AeriusException> warnings, final ColdStartValidationHelper validationHelper) {
    super(errors, warnings);
    this.validationHelper = validationHelper;
  }

  @Override
  boolean validate(final ColdStartEmissionSource source) {
    boolean valid = true;
    for (final Vehicles vehicleEmissions : source.getSubSources()) {
      valid = validateVehicles(vehicleEmissions, source.getLabel(), source.isVehicleBasedCharacteristics()) && valid;
    }
    return valid;
  }

  private boolean validateVehicles(final Vehicles vehicleEmissions, final String sourceLabel, final boolean vehicleBasedCharacteristics) {
    boolean valid = true;

    if (vehicleEmissions instanceof final SpecificVehicles specificVehicles) {
      valid = validateSpecificVehicles(specificVehicles, sourceLabel) && valid;
    } else if (vehicleEmissions instanceof final StandardColdStartVehicles standardVehicles) {
      valid = validateStandardVehicles(standardVehicles, sourceLabel) && valid;
    } else if (vehicleEmissions instanceof final CustomVehicles customVehicles) {
      valid = validateCustomVehicles(customVehicles, sourceLabel, vehicleBasedCharacteristics) && valid;
    }

    return valid;
  }

  private boolean validateSpecificVehicles(final SpecificVehicles vehicles, final String sourceLabel) {
    final String vehicleCode = vehicles.getVehicleCode();
    boolean valid = true;
    if (!validationHelper.isValidColdStartSpecificVehicleCode(vehicleCode)) {
      getErrors().add(new AeriusException(ImaerExceptionReason.GML_UNKNOWN_MOBILE_SOURCE_CODE, sourceLabel, vehicleCode));
      valid = false;
    }
    return valid;
  }

  private boolean validateStandardVehicles(final StandardColdStartVehicles vehicles, final String sourceLabel) {
    boolean valid = true;
    for (final Entry<String, Double> entry : vehicles.getValuesPerVehicleTypes().entrySet()) {
      final String vehicleType = entry.getKey();

      if (!validationHelper.isValidColdStartStandardVehicleCode(vehicleType)) {
        getErrors().add(new AeriusException(ImaerExceptionReason.GML_UNKNOWN_COLD_START_CATEGORY, sourceLabel, vehicleType));
        valid = false;
      }
    }
    return valid;
  }

  private boolean validateCustomVehicles(final CustomVehicles vehicles, final String sourceLabel, final boolean vehicleBasedCharacteristics) {
    final String vehicleType = vehicles.getVehicleType();
    boolean valid = true;
    if (vehicleBasedCharacteristics && !validationHelper.isValidColdStartStandardVehicleCode(vehicleType)) {
      getErrors().add(new AeriusException(ImaerExceptionReason.GML_UNKNOWN_COLD_START_CATEGORY, sourceLabel, vehicleType));
      valid = false;
    }
    return valid;
  }
}
