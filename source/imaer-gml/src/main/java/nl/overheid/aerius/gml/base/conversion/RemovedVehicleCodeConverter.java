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
package nl.overheid.aerius.gml.base.conversion;

import java.util.List;
import java.util.Set;

import nl.overheid.aerius.shared.domain.v2.source.ColdStartEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.domain.v2.source.RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.CustomVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.SpecificVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.Vehicles;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Converts {@link SpecificVehicles} with removed vehicle codes to {@link CustomVehicles} with zero
 * emissions. This handles backward compatibility when importing old GML files containing vehicle
 * codes (e.g., euro classes) that are no longer valid.
 *
 * <p>Only vehicle codes that are explicitly supplied as "removed" will be converted.
 * Unknown codes that are not in the removed codes list will still produce validation errors.
 */
public class RemovedVehicleCodeConverter {

  private final Set<String> removedVehicleCodes;
  private final List<AeriusException> warnings;

  public RemovedVehicleCodeConverter(
      final Set<String> removedVehicleCodes,
      final List<AeriusException> warnings) {
    this.removedVehicleCodes = removedVehicleCodes;
    this.warnings = warnings;
  }

  /**
   * Processes all emission sources and converts any SpecificVehicles with removed codes to
   * CustomVehicles with zero emissions.
   */
  public void convertRemovedVehicleCodes(final List<EmissionSourceFeature> emissionSourceList) {
    for (final EmissionSourceFeature feature : emissionSourceList) {
      if (feature.getProperties() instanceof final RoadEmissionSource roadSource) {
        convertSubSources(roadSource.getSubSources(), roadSource.getLabel());
      } else if (feature.getProperties() instanceof final ColdStartEmissionSource coldStartSource) {
        convertSubSources(coldStartSource.getSubSources(), coldStartSource.getLabel());
      }
    }
  }

  private void convertSubSources(final List<Vehicles> subSources, final String sourceLabel) {
    for (int i = 0; i < subSources.size(); i++) {
      final Vehicles vehicles = subSources.get(i);
      if (vehicles instanceof final SpecificVehicles specific
          && removedVehicleCodes.contains(specific.getVehicleCode())) {
        subSources.set(i, convertToCustomVehicles(specific));
        addWarning(sourceLabel, specific.getVehicleCode());
      }
    }
  }

  private void addWarning(final String sourceLabel, final String vehicleCode) {
    warnings.add(
        new AeriusException(
            ImaerExceptionReason.GML_UNKNOWN_MOBILE_SOURCE_CODE, sourceLabel, vehicleCode));
  }

  private static CustomVehicles convertToCustomVehicles(final SpecificVehicles specific) {
    final CustomVehicles custom = new CustomVehicles();
    custom.setTimeUnit(specific.getTimeUnit());
    custom.setVehiclesPerTimeUnit(specific.getVehiclesPerTimeUnit());
    custom.setDescription("Voormalig " + specific.getVehicleCode());
    return custom;
  }

}
