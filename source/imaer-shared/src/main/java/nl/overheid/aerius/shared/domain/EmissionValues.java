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
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Data object to store emission by year/substance.
 */
public class EmissionValues implements HasEmission, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Multiplies emission with this number before casting to int to a specific precision to
   * be taken into account when calculating state hash.
   */
  private static final int EMISSION_STATE_HASH_PRECISION = 1000;

  private HashMap<EmissionValueKey, Double> emissions = new HashMap<>();
  private boolean yearDependent;

  public EmissionValues() {
    this(true);
  }

  public EmissionValues(final boolean yearDependent) {
    this.yearDependent = yearDependent;
  }

  public void addEmission(final EmissionValueKey key, final double emission) {
    setEmission(key, getEmission(key) + emission);
  }

  public void addEmissions(final EmissionValues emissionValues) {
    for (final Entry<EmissionValueKey, Double> entry : emissionValues.entrySet()) {
      addEmission(entry.getKey(), entry.getValue());
    }
  }

  public EmissionValues copy() {
    final EmissionValues copy = new EmissionValues(isYearDependent());
    for (final Entry<EmissionValueKey, Double> entry : emissions.entrySet()) {
      copy.emissions.put(entry.getKey(), entry.getValue());
    }
    return copy;
  }

  @Override
  public double getEmission(final EmissionValueKey key) {
    final double emission;
    if (key == null) {
      emission = 0;
    } else {
      final EmissionValueKey noYear = new EmissionValueKey(key.getSubstance());
      //if the emission is year dependent, retrieve both the year dependent and year independent value.
      //if the key is a NOYEAR key, only get 1 result should be returned the value would be counted twice.
      emission = (emissions.containsKey(noYear) ? emissions.get(noYear) : 0)
          + (isYearDependent() && key.getYear() != noYear.getYear() && emissions.containsKey(key) ? emissions.get(key) : 0);
    }
    return emission;
  }

  public Set<Entry<EmissionValueKey, Double>> entrySet() {
    return emissions.entrySet();
  }

  public ArrayList<EmissionValueKey> getKeys() {
    return new ArrayList<>(emissions.keySet());
  }

  /**
   * @return the yearDependent
   */
  public boolean isYearDependent() {
    return yearDependent;
  }

  /**
   * Set the emission for the given substance. Use this when the emission are not year dependent.
   * @param substance
   * @param value
   */
  public void setEmission(final Substance substance, final Double value) {
    emissions.put(new EmissionValueKey(substance), value);
  }

  /**
   * Sets the emission for the given {@link EmissionValueKey}. If the emission is <= 0 it will be ignored, or if a value was present it will be
   * removed from the map.
   * @param key {@link EmissionValueKey}
   * @param emission
   */
  public void setEmission(final EmissionValueKey key, final double emission) {
    final EmissionValueKey actualKey = yearDependent || key.isNoYear() ? key : new EmissionValueKey(key.getSubstance());
    if (emission > 0) {
      emissions.put(actualKey, emission);
    } else {
      emissions.remove(actualKey);
    }
  }

  /**
   * Adds emissions for substances not present and replaces emission values for substances present.
   * @param emissionValues new emission values
   */
  public void setOrReplace(final EmissionValues emissionValues) {
    for (final Entry<EmissionValueKey, Double> entry : emissionValues.emissions.entrySet()) {
      setEmission(entry.getKey(), entry.getValue());
    }
  }

  /**
   * @param yearDependent the yearDependent to set
   */
  public void setYearDependent(final boolean yearDependent) {
    this.yearDependent = yearDependent;
  }

  @Override
  public String toString() {
    return "EmissionValues [emissions=" + emissions + ", yearDependent=" + yearDependent + "]";
  }
}
