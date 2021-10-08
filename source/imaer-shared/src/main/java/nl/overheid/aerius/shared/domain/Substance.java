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

/**
 * All supported Substances.
 *
 * TODO If external applications are not dependent on the numbering, change single substance types to 2, 4, 8, 16 etc. and combinations to the sum of
 * the single substances (ea. 36 for NOXNH3) Change contains() to ((substance.id & id) == substance.id)
 */
public enum Substance implements Serializable {

  /**
   * Nitrogen dioxide.
   */
  NO2(7, "NO2"),
  /**
   * Particulate matter, 10 micrometers.
   */
  PM10(10, "PM10"),
  /**
   * Nitrogen oxides.
   */
  NOX(11, "NOx"),
  /**
   * Oxidant, ozone.
   */
  O3(14, "O3"),
  /**
   * Particulate matter, 2.5 micrometers.
   */
  PM25(15, "PM25"),
  /**
   * Ammonia.
   */
  NH3(17, "NH3"),
  /**
   * Elementary Carbon
   */
  EC(18, "EC"),
  /**
   * Combined NH3 and NOx substance to show aggregated results.
   */
  NOXNH3(1711, "NOx+NH3");

  /**
   * List of substances that are emission substances.
   */
  public static final Substance[] EMISSION_SUBSTANCES = {NH3, NOX, NO2, PM10, PM25, EC};
  /**
   * List of substances that are emission result substances.
   */
  public static final Substance[] RESULT_SUBSTANCES = {NH3, NOX, NO2, PM10, PM25, EC};

  /**
   * Factor to convert emission from kg/y to g/s.
   *
   * ((s/m) * (m/h) * (h/d) * (d/y)) / (g/kg)
   *
   * or
   *
   * (60 * 60 * 24 * 365) / 1000
   */
  public static final double EMISSION_IN_G_PER_S_FACTOR = 31536;

  private final int id;
  private final String name;

  private Substance(final int id, final String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * Returns the Substance matching the id.
   *
   * @param id of substance
   * @return Substance of id
   */
  public static Substance substanceFromId(final int id) {
    for (final Substance substance : values()) {
      if (substance.id == id) {
        return substance;
      }
    }
    return null;
  }

  /**
   * Check if the given substance is the same as or is part of the current substance. Examples:
   * <li>current: NOXNH3 - parameter: NH3 -> true
   * <li>current: NOXNH3 - parameter: NOX -> true
   * <li>current: NH3 - parameter: NOXNH3 -> false
   * <li>current NOX - parameter: NH3 -> false
   *
   * @param substance substance to check
   * @return boolean
   */
  public boolean contains(final Substance substance) {
    return (this == substance) || ((this == NOXNH3) && ((substance == NH3) || (substance == NOX)));
  }

  /**
   * Same as {@link #contains(Substance)}, only it also checks if the given the current substance is part of the given substance. Thus if the
   * substance given is NOXNH3 and the current is NH3 this method returns true.
   *
   * @param substance The substance to check
   * @return boolean
   */
  public boolean containsCurrentOrGiven(final Substance substance) {
    return this.contains(substance) || substance.contains(this);
  }

  /**
   * Returns true if the given substances contains the substance or if the given substance contains any of the substances in the list.
   *
   * @param substances
   * @param substance
   * @return
   */
  public static boolean containsOrPartOf(final ArrayList<Substance> substances, final Substance substance) {
    for (final Substance s : substances) {
      if (s.containsCurrentOrGiven(substance)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns substance as list, in case of NH3 + NOx it returns a list with both substances.
   *
   * @return substance as list
   */
  public ArrayList<Substance> hatch() {
    final ArrayList<Substance> substances = new ArrayList<>();

    if (this == Substance.NOXNH3) {
      substances.add(Substance.NH3);
      substances.add(Substance.NOX);
    } else {
      substances.add(this);
    }
    return substances;
  }

  /**
   * Returns the substance as the value used in the database. Use this method when querying for substance.
   *
   * @return Substance as value as used in the database
   */
  public String toDatabaseString() {
    return name().toLowerCase();
  }

  /**
   * Safely returns a Substance. It is case independent and returns null in case the input was null or the substance could not be found.
   *
   * @param value value to convert
   * @return Substance or null if no valid input
   */
  public static Substance safeValueOf(final String value) {
    Substance returnValue = null;
    if (value != null) {
      try {
        returnValue = valueOf(value.toUpperCase().replace("+", ""));
      } catch (final IllegalArgumentException e) {
        // not a substance apparently, return null.
      }
    }
    return returnValue;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "Substance [" + name + ":" + id + "]";
  }
}
