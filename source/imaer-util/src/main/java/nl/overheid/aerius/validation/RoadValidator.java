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

import nl.overheid.aerius.shared.domain.v2.base.LinearReference;
import nl.overheid.aerius.shared.domain.v2.source.RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.SRM1RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.SRM2RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.CustomVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.RoadStandardEmissionFactorsKey;
import nl.overheid.aerius.shared.domain.v2.source.road.SRM2RoadSideBarrier;
import nl.overheid.aerius.shared.domain.v2.source.road.SpecificVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.ValuesPerVehicleType;
import nl.overheid.aerius.shared.domain.v2.source.road.Vehicles;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Validator for {@link RoadEmissionSource} objects.
 */
class RoadValidator extends SourceValidator<RoadEmissionSource> {

  private final RoadValidationHelper validationHelper;

  RoadValidator(final List<AeriusException> errors, final List<AeriusException> warnings, final RoadValidationHelper validationHelper) {
    super(errors, warnings);
    this.validationHelper = validationHelper;
  }

  @Override
  boolean validate(final RoadEmissionSource source) {
    boolean valid = true;
    if (source instanceof SRM1RoadEmissionSource) {
      valid = validateSrm1Specifics((SRM1RoadEmissionSource) source);
    } else if (source instanceof SRM2RoadEmissionSource) {
      valid = validateSrm2Specifics((SRM2RoadEmissionSource) source);
    }
    for (final Vehicles vehicleEmissions : source.getSubSources()) {
      valid = validateVehicles(source, vehicleEmissions, source.getLabel()) && valid;
    }
    valid = validateTunnelFactor(source) && valid;
    return valid;
  }

  private boolean validateSrm1Specifics(final SRM1RoadEmissionSource source) {
    boolean valid = true;
    if (source.getPartialChanges() != null) {
      for (final LinearReference linearReference : source.getPartialChanges()) {
        valid = validateLinearReference(linearReference) && valid;
      }
    }
    valid = validateNoStrictEnforcement(source) && valid;
    return valid;
  }

  private boolean validateSrm2Specifics(final SRM2RoadEmissionSource source) {
    boolean valid = true;
    if (source.getPartialChanges() != null) {
      for (final LinearReference linearReference : source.getPartialChanges()) {
        valid = validateLinearReference(linearReference) && valid;
      }
    }
    if (source.getBarrierLeft() != null) {
      valid = validateBarrier(source.getBarrierLeft(), source.getLabel()) && valid;
    }
    if (source.getBarrierRight() != null) {
      valid = validateBarrier(source.getBarrierRight(), source.getLabel()) && valid;
    }
    return valid;
  }

  private boolean validateLinearReference(final LinearReference linearReference) {
    boolean valid = true;
    valid = validatePositionFraction(linearReference.getFromPosition()) && valid;
    valid = validatePositionFraction(linearReference.getToPosition()) && valid;
    return valid;
  }

  private boolean validatePositionFraction(final double position) {
    boolean valid = true;
    if (position < 0 || position > 1) {
      getErrors().add(new AeriusException(ImaerExceptionReason.GML_ROAD_SEGMENT_POSITION_NOT_FRACTION, String.valueOf(position)));
      valid = false;
    }
    return valid;
  }

  private boolean validateNoStrictEnforcement(final SRM1RoadEmissionSource source) {
    boolean valid = true;
    if (source.getSubSources().stream()
        .filter(StandardVehicles.class::isInstance)
        .map(StandardVehicles.class::cast)
        .anyMatch(x -> x.getStrictEnforcement() != null && x.getStrictEnforcement().booleanValue())) {
      getErrors().add(new AeriusException(ImaerExceptionReason.SRM1_SOURCE_WITH_STRICT_ENFORCEMENT, source.getLabel()));
      valid = false;
    }
    return valid;
  }

  private boolean validateVehicles(final RoadEmissionSource source, final Vehicles vehicleEmissions, final String sourceLabel) {
    // check vehicles
    boolean valid = true;
    final double totalVehiclesPerTimeUnit = determineTotalVehicles(vehicleEmissions);
    if (totalVehiclesPerTimeUnit < 0) {
      getErrors().add(new AeriusException(ImaerExceptionReason.SRM2_SOURCE_NEGATIVE_VEHICLES, sourceLabel));
      valid = false;
    } else if (Double.compare(totalVehiclesPerTimeUnit, 0.0) == 0) {
      getWarnings().add(new AeriusException(ImaerExceptionReason.SRM2_SOURCE_NO_VEHICLES, sourceLabel));
    }

    if (vehicleEmissions instanceof SpecificVehicles) {
      valid = validateSpecificVehicles(source, (SpecificVehicles) vehicleEmissions, sourceLabel) && valid;
    } else if (vehicleEmissions instanceof StandardVehicles) {
      valid = validateStandardVehicles(source, (StandardVehicles) vehicleEmissions, sourceLabel) && valid;
    }
    return valid;
  }

  private double determineTotalVehicles(final Vehicles vehicleEmissions) {
    double totalVehiclesPerTimeUnit = 0;
    if (vehicleEmissions instanceof StandardVehicles) {
      for (final ValuesPerVehicleType values : ((StandardVehicles) vehicleEmissions).getValuesPerVehicleTypes().values()) {
        totalVehiclesPerTimeUnit += values.getVehiclesPerTimeUnit();
      }
    } else if (vehicleEmissions instanceof SpecificVehicles) {
      totalVehiclesPerTimeUnit = ((SpecificVehicles) vehicleEmissions).getVehiclesPerTimeUnit();
    } else if (vehicleEmissions instanceof CustomVehicles) {
      totalVehiclesPerTimeUnit = ((CustomVehicles) vehicleEmissions).getVehiclesPerTimeUnit();
    }
    return totalVehiclesPerTimeUnit;
  }

  private boolean validateStandardVehicles(final RoadEmissionSource source, final StandardVehicles vehicles, final String sourceLabel) {
    final String roadAreaCode = source.getRoadAreaCode();
    final String roadTypeCode = source.getRoadTypeCode();
    boolean valid = true;
    for (final Entry<String, ValuesPerVehicleType> entry : vehicles.getValuesPerVehicleTypes().entrySet()) {
      final String vehicleType = entry.getKey();
      final Boolean strictEnforcement = vehicles.getStrictEnforcement();
      final Integer maximumSpeed = vehicles.getMaximumSpeed();
      if (!validationHelper.isValidRoadStandardVehicleCombination(
          new RoadStandardEmissionFactorsKey(roadAreaCode, roadTypeCode, vehicleType, maximumSpeed, strictEnforcement, 0.0))) {
        getErrors().add(new AeriusException(ImaerExceptionReason.GML_UNKNOWN_ROAD_CATEGORY, sourceLabel, roadAreaCode, roadTypeCode,
            String.valueOf(maximumSpeed), String.valueOf(strictEnforcement), vehicleType));
        valid = false;
      }
      try {
        validationHelper.assertRoadStandardEmissionFactorsAvailable(
            new RoadStandardEmissionFactorsKey(roadAreaCode, roadTypeCode, vehicleType, maximumSpeed, strictEnforcement, null), sourceLabel);
      } catch (final AeriusException e) {
        getErrors().add(e);
        valid = false;
      }
      if (entry.getValue().getStagnationFraction() < 0 || entry.getValue().getStagnationFraction() > 1) {
        getErrors().add(new AeriusException(ImaerExceptionReason.UNEXPECTED_FRACTION_VALUE, sourceLabel));
        valid = false;
      }
    }
    return valid;
  }

  private boolean validateSpecificVehicles(final RoadEmissionSource source, final SpecificVehicles vehicles, final String sourceLabel) {
    final String vehicleCode = vehicles.getVehicleCode();
    boolean valid = true;
    if (!validationHelper.isValidRoadSpecificVehicleCode(vehicleCode)) {
      getErrors().add(new AeriusException(ImaerExceptionReason.GML_UNKNOWN_MOBILE_SOURCE_CODE, sourceLabel, vehicleCode));
      valid = false;
    }
    try {
      validationHelper.assertRoadSpecificEmissionFactorsAvailable(vehicleCode, source.getRoadTypeCode(), sourceLabel);
    } catch (final AeriusException e) {
      getErrors().add(e);
      valid = false;
    }
    return valid;
  }

  private boolean validateTunnelFactor(final RoadEmissionSource source) {
    boolean valid = true;
    if (source.getTunnelFactor() < 0) {
      getErrors().add(new AeriusException(ImaerExceptionReason.UNEXPECTED_NEGATIVE_VALUE, source.getLabel(),
          String.valueOf(source.getTunnelFactor())));
      valid = false;
    }
    return valid;
  }

  private boolean validateBarrier(final SRM2RoadSideBarrier barrier, final String sourceLabel) {
    boolean valid = true;
    if (barrier.getDistance() < 0) {
      getErrors().add(new AeriusException(ImaerExceptionReason.UNEXPECTED_NEGATIVE_VALUE, sourceLabel, String.valueOf(barrier.getDistance())));
      valid = false;
    }
    if (barrier.getHeight() < 0) {
      getErrors().add(new AeriusException(ImaerExceptionReason.UNEXPECTED_NEGATIVE_VALUE, sourceLabel, String.valueOf(barrier.getHeight())));
      valid = false;
    }
    return valid;
  }

}
