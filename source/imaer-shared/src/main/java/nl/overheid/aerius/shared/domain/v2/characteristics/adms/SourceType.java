/*
 * Crown copyright
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
package nl.overheid.aerius.shared.domain.v2.characteristics.adms;

import java.util.Locale;

/**
 * ADMS Source Type.
 */
public enum SourceType {
  // @formatter:off
  POINT(0),
  AREA(1),
  VOLUME(2),
  LINE(3),
  ROAD(4),
  JET(6);
  // @formatter:on

  private final int type;

  SourceType(final int type) {
    this.type = type;
  }

  public int getType() {
    return type;
  }

  public static SourceType byType(final int type) {
    for (final SourceType st : values()) {
      if (st.type == type) {
        return st;
      }
    }
    return null;
  }

  public static SourceType safeValueOf(final String value) {
    try {
      return value == null ? null : valueOf(value.toUpperCase(Locale.ROOT));
    } catch (final IllegalArgumentException e) {
      return null;
    }
  }
}
