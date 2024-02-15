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
package nl.overheid.aerius.gml.base.util;

import java.util.function.Consumer;

import nl.overheid.aerius.gml.base.characteristics.IsGmlTimeVaryingProfile;
import nl.overheid.aerius.gml.base.characteristics.IsGmlReferenceTimeVaryingProfile;
import nl.overheid.aerius.gml.base.characteristics.IsGmlStandardTimeVaryingProfile;

/**
 *
 */
public final class TimeVaryingProfileUtil {

  private TimeVaryingProfileUtil() {
    // Util class
  }

  public static void setTimeVaryingProfile(final IsGmlTimeVaryingProfile gmlTimeVaryingProfile,
      final Consumer<String> standardTimeVaryingProfileCodeSetter,
      final Consumer<String> customTimeVaryingProfileIdSetter) {
    if (gmlTimeVaryingProfile == null) {
      standardTimeVaryingProfileCodeSetter.accept(null);
      customTimeVaryingProfileIdSetter.accept(null);
    } else if (gmlTimeVaryingProfile instanceof IsGmlStandardTimeVaryingProfile) {
      final String timeVaryingProfileCode = ((IsGmlStandardTimeVaryingProfile) gmlTimeVaryingProfile).getCode();
      standardTimeVaryingProfileCodeSetter.accept(timeVaryingProfileCode);
      customTimeVaryingProfileIdSetter.accept(null);
    } else if (gmlTimeVaryingProfile instanceof IsGmlReferenceTimeVaryingProfile) {
      standardTimeVaryingProfileCodeSetter.accept(null);
      final IsGmlReferenceTimeVaryingProfile gmlReferenceTimeVaryingProfile = (IsGmlReferenceTimeVaryingProfile) gmlTimeVaryingProfile;
      customTimeVaryingProfileIdSetter.accept(gmlReferenceTimeVaryingProfile.getCustomTimeVaryingProfile().getReferredId());
    }
  }

}
