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
package nl.overheid.aerius.shared.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonKey;

/**
 * EmissionSource objects keep track of multiple emission values,
 * because for certain information the emission values are different.
 */
public class EmissionValueKey implements Comparable<EmissionValueKey>, Serializable {

  private static final long serialVersionUID = -7656975594074285482L;

  /**
   * Not all sources have year specific data. For those data the combination
   * EmissionValueKey is valid for all years requested.
   */
  private static final int NO_YEAR = 0;

  private int year;
  private Substance substance;

  // Needed for GWT.
  public EmissionValueKey() {
    // Needed for GWT
  }

  /**
   * Initialize {@link EmissionValueKey} for substance, with year initialized as
   * having no year.
   * 
   * @param substance substance
   */
  public EmissionValueKey(final Substance substance) {
    this(NO_YEAR, substance);
  }

  /**
   * Initialize {@link EmissionValueKey} for substance and year.
   * 
   * @param year      year
   * @param substance substance
   */
  public EmissionValueKey(final int year, final Substance substance) {
    this.year = year;
    this.substance = substance;
  }

  public static ArrayList<EmissionValueKey> getEmissionValueKeys(final int year, final List<Substance> substances) {
    return getEmissionValueKeys(year, substances.toArray(new Substance[substances.size()]));
  }

  public static ArrayList<EmissionValueKey> getEmissionValueKeys(final int year, final Substance[] substances) {
    final Collection<EmissionValueKey> evks = new HashSet<EmissionValueKey>();
    for (final Substance substance : substances) {
      if (substance == Substance.NOXNH3) {
        evks.add(new EmissionValueKey(year, Substance.NH3));
        evks.add(new EmissionValueKey(year, Substance.NOX));
      } else {
        evks.add(new EmissionValueKey(year, substance));
      }
    }
    return fixNO2Keys(new ArrayList<EmissionValueKey>(evks));
  }

  /**
   * Hack to make sure NO2 emission values are also included. since the callers
   * only set NOx.
   * 
   * @param keys current list of keys to calculate
   * @return same list with NO2 added if not already present
   * @deprecated FIXME Find fix to make sure NO2 substance is also included in
   *             list of substances when calculating NOx.
   */
  @Deprecated
  private static ArrayList<EmissionValueKey> fixNO2Keys(final ArrayList<EmissionValueKey> keys) {
    boolean hasNO2Keys = false;
    boolean hasNOXKeys = false;
    for (final EmissionValueKey evk : keys) {
      if (evk.getSubstance() == Substance.NOX) {
        hasNOXKeys = true;
      } else if (evk.getSubstance() == Substance.NO2) {
        hasNO2Keys = true;
      }
    }
    if (hasNOXKeys && !hasNO2Keys && !keys.isEmpty()) {
      keys.add(new EmissionValueKey(keys.get(0).getYear(), Substance.NO2));
    }
    return keys;
  }

  @Override
  public boolean equals(final Object obj) {
    return obj != null && this.getClass() == obj.getClass()
        && (year == ((EmissionValueKey) obj).year)
        && (substance == ((EmissionValueKey) obj).substance);
  }

  @Override
  public int compareTo(final EmissionValueKey o) {
    final int cmp;
    if (year < o.year) {
      cmp = -1;
    } else if (year > o.year) {
      cmp = 1;
    } else {
      if (substance.getId() < o.substance.getId()) {
        cmp = -1;
      } else if (substance.getId() > o.substance.getId()) {
        cmp = 1;
      } else {
        cmp = 0;
      }
    }
    return cmp;
  }

  /**
   * Makes a copy of the {@link EmissionValueKey}.
   *
   * @return new instance
   */
  public EmissionValueKey copy() {
    return new EmissionValueKey(year, substance);
  }

  /**
   * Returns the {@link EmissionValueKey} wrapped in an {@link ArrayList}.
   * Or, in the case of a NH3+NOx key, an ArrayList of a separate NH3 key and NOx
   * key.
   *
   * @return collection
   */
  public ArrayList<EmissionValueKey> hatch() {
    final ArrayList<EmissionValueKey> keys = new ArrayList<EmissionValueKey>();

    if (substance == Substance.NOXNH3) {
      keys.add(new EmissionValueKey(year, Substance.NOX));
      keys.add(new EmissionValueKey(year, Substance.NH3));
    } else {
      keys.add(this);
    }
    return keys;
  }

  public int getYear() {
    return year;
  }

  public Substance getSubstance() {
    return substance;
  }

  @Override
  public int hashCode() {
    final int randomNr = 4096;
    return year + (randomNr * substance.getId());
  }

  @JsonIgnore
  public boolean isNoYear() {
    return year == NO_YEAR;
  }

  public void setYear(final int year) {
    this.year = year;
  }

  public void setSubstance(final Substance substance) {
    this.substance = substance;
  }

  /**
   * Converts this EmissionValueKey to a parseable string representation in the
   * format "year:substance".
   * This format is compatible with {@link #fromStringValue(String)} for
   * serialization/deserialization.
   * 
   * @return String representation in format "year:substance"
   * @see #fromStringValue(String)
   */
  @JsonKey
  public String toStringValue() {
    return year + ":" + substance.name();
  }

  /**
   * Creates an EmissionValueKey from its string representation.
   * Expects input in the format "year:substance" as produced by
   * {@link #toStringValue()}.
   * 
   * @param value The string representation in format "year:substance"
   * @return A new EmissionValueKey instance
   * @throws IllegalArgumentException if the input is null, empty, or has invalid
   *                                  format
   * @throws NumberFormatException    if the year part cannot be parsed as an
   *                                  integer
   * @see #toStringValue()
   */
  public static EmissionValueKey fromStringValue(final String value) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException("Input value cannot be null or empty");
    }

    final String[] parts = value.split(":");
    final String yearStr = parts.length > 0 ? parts[0].trim() : "";
    final String substanceStr = parts.length > 1 ? parts[1].trim() : "";

    if (yearStr.isEmpty() || substanceStr.isEmpty()) {
      throw new IllegalArgumentException("Year and substance cannot be empty, got: '" + value + "'");
    }

    if (parts.length != 2) {
      throw new IllegalArgumentException("Input must be in format 'year:substance', got: " + value);
    }

    try {
      final int year = Integer.parseInt(yearStr);
      final Substance substance = Substance.valueOf(substanceStr);
      return new EmissionValueKey(year, substance);
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Invalid year format: " + yearStr);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid substance: " + substanceStr);
    }
  }

  @Override
  public String toString() {
    return "EmissionValueKey [year=" + year + ", substance=" + substance.name() + "]";
  }
}
