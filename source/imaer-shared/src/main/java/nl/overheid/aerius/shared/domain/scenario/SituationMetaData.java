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
package nl.overheid.aerius.shared.domain.scenario;

import java.io.Serializable;

public class SituationMetaData implements Serializable {

  private static final long serialVersionUID = 2L;

  private String name;
  private int year;
  private String aeriusVersion;
  private String databaseVersion;

  private SituationMetaData(
      final String name,
      final int year,
      final String aeriusVersion,
      final String databaseVersion) {
    this.name = name;
    this.year = year;
    this.aeriusVersion = aeriusVersion;
    this.databaseVersion = databaseVersion;
  }

  public static Builder builder() {
    return new Builder();
  }

  public String getName() {
    return name;
  }

  public int getYear() {
    return year;
  }

  public String getAeriusVersion() {
    return aeriusVersion;
  }

  public String getDatabaseVersion() {
    return databaseVersion;
  }

  @Override
  public String toString() {
    return "SituationMetaData{"
        + "name=" + name + ", "
        + "year=" + year + ", "
        + "aeriusVersion=" + aeriusVersion + ", "
        + "databaseVersion=" + databaseVersion
        + "}";
  }

  @Override
  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SituationMetaData that = (SituationMetaData) o;
    return this.name.equals(that.getName())
        && this.year == that.getYear()
        && this.aeriusVersion.equals(that.getAeriusVersion())
        && this.databaseVersion.equals(that.getDatabaseVersion());
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= name.hashCode();
    h$ *= 1000003;
    h$ ^= year;
    h$ *= 1000003;
    h$ ^= aeriusVersion.hashCode();
    h$ *= 1000003;
    h$ ^= databaseVersion.hashCode();
    return h$;
  }

  static final class Builder {
    private String name;
    private Integer year;
    private String aeriusVersion;
    private String databaseVersion;

    Builder() {
    }

    public SituationMetaData.Builder name(final String name) {
      if (name == null) {
        throw new NullPointerException("Null name");
      }
      this.name = name;
      return this;
    }

    public SituationMetaData.Builder year(final int year) {
      this.year = year;
      return this;
    }

    public SituationMetaData.Builder aeriusVersion(final String aeriusVersion) {
      if (aeriusVersion == null) {
        throw new NullPointerException("Null aeriusVersion");
      }
      this.aeriusVersion = aeriusVersion;
      return this;
    }

    public SituationMetaData.Builder databaseVersion(final String databaseVersion) {
      if (databaseVersion == null) {
        throw new NullPointerException("Null databaseVersion");
      }
      this.databaseVersion = databaseVersion;
      return this;
    }

    public SituationMetaData build() {
      String missing = "";
      if (this.name == null) {
        missing += " name";
      }
      if (this.year == null) {
        missing += " year";
      }
      if (this.aeriusVersion == null) {
        missing += " aeriusVersion";
      }
      if (this.databaseVersion == null) {
        missing += " databaseVersion";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new SituationMetaData(
          this.name,
          this.year,
          this.aeriusVersion,
          this.databaseVersion);
    }
  }

}
