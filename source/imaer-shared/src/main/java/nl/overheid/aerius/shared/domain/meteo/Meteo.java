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

import java.io.Serializable;

/**
 * Meteo class for single years (e.g. 2014) and multiple years (e.g. 1995-2004).
 * If startYear is the same as the endYear it can be considered single-year meteo.
 */
public class Meteo implements Serializable {

  private static final long serialVersionUID = 2L;

  private String code;
  private String description;
  private int startYear;
  private int endYear;

  protected Meteo() {
    // Default constructor, needed for serialization
  }

  /**
   * @param year The year for this meteo set. Will be used for both start and end year. Can be used by models to determine the correct file.
   */
  public Meteo(final int year) {
    this(String.valueOf(year), String.valueOf(year), year);
  }

  /**
   * @param startYear The start year for this meteo set. Can be used by models to determine the correct file.
   * @param endYear The end year for this meteo set. Can be used by models to determine the correct file.
   */
  public Meteo(final int startYear, final int endYear) {
    this(startYear + "-" + endYear, startYear + "-" + endYear, startYear, endYear);
  }

  /**
   * @param code Unique code for this meteo.
   * If the same year is specified multiple times (different parts of the country for instance),
   * this should be reflected in the code.
   * @param description A user friendly description.
   * @param year The year for this meteo set. Will be used for both start and end year. Can be used by models to determine the correct file.
   */
  public Meteo(final String code, final String description, final int year) {
    this(code, description, year, year);
  }

  /**
   * @param code Unique code for this meteo.
   * If the same year is specified multiple times (different parts of the country for instance),
   * this should be reflected in the code.
   * @param description A user friendly description.
   * @param startYear The start year for this meteo set. Can be used by models to determine the correct file.
   * @param endYear The end year for this meteo set. Can be used by models to determine the correct file.
   */
  public Meteo(final String code, final String description, final int startYear, final int endYear) {
    this.code = code;
    this.description = description;
    this.startYear = startYear;
    this.endYear = endYear;
  }

  /**
   * To be removed soon-ish.
   */
  @Deprecated
  public String getOpsFile() {
    return null;
  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }

  public int getStartYear() {
    return startYear;
  }

  public int getEndYear() {
    return endYear;
  }

  public boolean isSingleYear() {
    return !isMultiYear();
  }

  public boolean isMultiYear() {
    return endYear > startYear;
  }

  @Override
  public String toString() {
    return code;
  }
}
