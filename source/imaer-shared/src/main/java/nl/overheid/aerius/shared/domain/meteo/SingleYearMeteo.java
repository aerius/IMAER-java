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
 * Represents a single year (e.g. 2014)
 */
public class SingleYearMeteo extends Meteo {

  private static final long serialVersionUID = 1L;

  private int year;

  public SingleYearMeteo(int year, String opsFile) {
    super(opsFile);
    this.year = year;
  }

  public SingleYearMeteo(int year) {
    super("");
    this.year = year;
  }

  public SingleYearMeteo() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SingleYearMeteo that = (SingleYearMeteo) o;
    return year == that.year;
  }

  @Override
  public int hashCode() {
    return Objects.hash(year);
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return String.valueOf(year);
  }
}
