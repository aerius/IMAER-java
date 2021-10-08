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
 */
public class MultiYearMeteo extends Meteo {

  private static final long serialVersionUID = 1L;

  private int startYear;
  private int endYear;

  public MultiYearMeteo(int startYear, int endYear, String opsFile) {
    super(opsFile);
    this.startYear = startYear;
    this.endYear = endYear;
  }

  public MultiYearMeteo(int startYear, int endYear) {
    super("");
    this.startYear = startYear;
    this.endYear = endYear;
  }

  public MultiYearMeteo() {
  }

  public int getStartYear() {
    return startYear;
  }

  public void setStartYear(int startYear) {
    this.startYear = startYear;
  }

  public int getEndYear() {
    return endYear;
  }

  public void setEndYear(int endYear) {
    this.endYear = endYear;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MultiYearMeteo that = (MultiYearMeteo) o;
    return startYear == that.startYear &&
        endYear == that.endYear;
  }

  @Override
  public int hashCode() {
    return Objects.hash(startYear, endYear);
  }

  @Override
  public String toString() {
    return String.valueOf(startYear) + "-" + String.valueOf(endYear);
  }
}
