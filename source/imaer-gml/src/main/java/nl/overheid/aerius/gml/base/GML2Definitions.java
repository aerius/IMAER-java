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
package nl.overheid.aerius.gml.base;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nl.overheid.aerius.gml.base.characteristics.IsGmlCustomTimeVaryingProfile;
import nl.overheid.aerius.shared.domain.v2.characteristics.CustomTimeVaryingProfile;
import nl.overheid.aerius.shared.domain.v2.characteristics.CustomTimeVaryingProfileType;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 * Utility class to convert from GML objects (specific for Definitions).
 */
public class GML2Definitions {

  private final GMLConversionData conversionData;

  /**
   * @param conversionData The data to use when converting. Should be filled.
   */
  public GML2Definitions(final GMLConversionData conversionData) {
    this.conversionData = conversionData;
  }

  /**
   * Convert GML definitions to a domain object.
   * @param gmlDefinitions The GML-representation of definitions.
   * @return The domain object Definitions.
   */
  public nl.overheid.aerius.shared.domain.v2.scenario.Definitions fromGML(final Definitions gmlDefinitions) {
    final nl.overheid.aerius.shared.domain.v2.scenario.Definitions definitions = new nl.overheid.aerius.shared.domain.v2.scenario.Definitions();
    try {
      if (gmlDefinitions != null) {
        final List<CustomTimeVaryingProfile> customTimeVaryingProfiles = new ArrayList<>();
        for (final IsGmlCustomTimeVaryingProfile gmlCustomTimeVaryingProfile : gmlDefinitions.getCustomTimeVaryingProfiles()) {
          customTimeVaryingProfiles.add(convert(gmlCustomTimeVaryingProfile));
        }
        definitions.setCustomTimeVaryingProfiles(customTimeVaryingProfiles);
      }
    } catch (final AeriusException e) {
      conversionData.getErrors().add(e);
    }
    return definitions;
  }

  private CustomTimeVaryingProfile convert(final IsGmlCustomTimeVaryingProfile gmlCustomTimeVaryingProfile) throws AeriusException {
    final CustomTimeVaryingProfile timeVaryingProfile = new CustomTimeVaryingProfile();
    final CustomTimeVaryingProfileType customType = convertCustomTimeVaryingProfileType(gmlCustomTimeVaryingProfile.getCustomType());
    timeVaryingProfile.setGmlId(gmlCustomTimeVaryingProfile.getId());
    timeVaryingProfile.setLabel(gmlCustomTimeVaryingProfile.getLabel());
    timeVaryingProfile.setType(customType);
    timeVaryingProfile.setValues(gmlCustomTimeVaryingProfile.getValues().stream().map(x -> (double) x).collect(Collectors.toList()));
    return timeVaryingProfile;
  }

  private CustomTimeVaryingProfileType convertCustomTimeVaryingProfileType(final String gmlCustomType)
      throws AeriusException {
    final CustomTimeVaryingProfileType customType = CustomTimeVaryingProfileType.safeValueOf(gmlCustomType);
    if (customType == null) {
      throw new AeriusException(ImaerExceptionReason.CUSTOM_TIME_VARYING_PROFILE_TYPE_UNKNOWN, gmlCustomType);
    }
    return customType;
  }

}
