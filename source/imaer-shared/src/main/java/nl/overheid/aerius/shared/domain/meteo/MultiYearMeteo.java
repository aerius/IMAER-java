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
package nl.overheid.aerius.shared.domain.meteo;

import java.util.Objects;

/**
 * Represents multiple years (e.g. 1995-2004)
 * @deprecated Use Meteo directly instead.
 */
@Deprecated
public class MultiYearMeteo extends Meteo {

  private static final long serialVersionUID = 1L;

  public MultiYearMeteo(final int startYear, final int endYear, final String opsFile) {
    super(startYear, endYear);
  }

  public MultiYearMeteo(final int startYear, final int endYear) {
    super(startYear, endYear);
  }

  public MultiYearMeteo() {
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final MultiYearMeteo that = (MultiYearMeteo) o;
    return getStartYear() == that.getStartYear() &&
        getEndYear() == that.getEndYear();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getStartYear(), getEndYear());
  }

}
