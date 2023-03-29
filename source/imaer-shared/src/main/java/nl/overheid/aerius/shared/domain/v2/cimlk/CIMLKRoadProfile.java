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
package nl.overheid.aerius.shared.domain.v2.cimlk;

public enum CIMLKRoadProfile {

  WIDE_STREET_CANYON(1), NARROW_STREET_CANYON(2), ONE_SIDE_BUILDINGS(3), OTHER(4);

  private final int legacyId;

  private CIMLKRoadProfile(final int legacyId) {
    this.legacyId = legacyId;
  }

  public static CIMLKRoadProfile legacySafeValueOf(final int value) {
    CIMLKRoadProfile result = null;
    for (final CIMLKRoadProfile roadProfile : values()) {
      if (value == roadProfile.legacyId) {
        result = roadProfile;
        break;
      }
    }
    return result;
  }

}
