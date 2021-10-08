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
package nl.overheid.aerius.shared.domain.result;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.validation.constraints.NotNull;

import nl.overheid.aerius.shared.domain.Substance;

/**
 * Class used to identify results from emissions. For example this can be deposition or concentration.
 */
public enum EmissionResultKey implements Comparable<EmissionResultKey> {

  // @formatter:off
  NOXNH3_DEPOSITION(Substance.NOXNH3, EmissionResultType.DEPOSITION),

  NOX_DEPOSITION(Substance.NOX, EmissionResultType.DEPOSITION),
  NOX_CONCENTRATION(Substance.NOX, EmissionResultType.CONCENTRATION),
  NOX_DRY_DEPOSITION(Substance.NOX, EmissionResultType.DRY_DEPOSITION),
  NOX_WET_DEPOSITION(Substance.NOX, EmissionResultType.WET_DEPOSITION),

  NH3_DEPOSITION(Substance.NH3, EmissionResultType.DEPOSITION),
  NH3_CONCENTRATION(Substance.NH3,EmissionResultType.CONCENTRATION),
  NH3_DRY_DEPOSITION(Substance.NH3, EmissionResultType.DRY_DEPOSITION),
  NH3_WET_DEPOSITION(Substance.NH3, EmissionResultType.WET_DEPOSITION),

  NO2_CONCENTRATION(Substance.NO2, EmissionResultType.CONCENTRATION),
  NO2_DIRECT_CONCENTRATION(Substance.NO2, EmissionResultType.DIRECT_CONCENTRATION),
  NO2_EXCEEDANCE_HOURS(Substance.NO2, EmissionResultType.EXCEEDANCE_HOURS),
  O3_CONCENTRATION(Substance.O3, EmissionResultType.CONCENTRATION),

  PM10_CONCENTRATION(Substance.PM10, EmissionResultType.CONCENTRATION),
  PM10_EXCEEDANCE_DAYS(Substance.PM10, EmissionResultType.EXCEEDANCE_DAYS),

  PM25_CONCENTRATION(Substance.PM25, EmissionResultType.CONCENTRATION),

  EC_CONCENTRATION(Substance.EC, EmissionResultType.CONCENTRATION);
  // @formatter:on

  private Substance substance;
  private EmissionResultType emissionResultType;

  private EmissionResultKey(final Substance substance, final EmissionResultType emissionResultType) {
    this.substance = substance;
    this.emissionResultType = emissionResultType;
  }

  public static List<EmissionResultKey> getEmissionResultKeys(final List<Substance> substances, final EmissionResultType ert) {
    final List<EmissionResultKey> keys = new ArrayList<>();

    for (final Substance substance : substances) {
      final EmissionResultKey erk = valueOf(substance, ert);
      if (erk != null) {
        keys.add(erk);
      }
    }
    enhance(keys);
    return keys;
  }

  private static void enhance(final List<EmissionResultKey> keys) {
    if (keys.contains(NOX_CONCENTRATION) && !keys.contains(NO2_CONCENTRATION)) {
      keys.add(EmissionResultKey.NO2_CONCENTRATION);
    }
  }

  /**
   * Returns the key for the given substance. For PM10 the default result type will be concentration for all others it will be deposition.
   * @param substance substance
   */
  public static EmissionResultKey valueOf(final Substance substance) {
    for (final EmissionResultKey erk : values()) {
      if ((erk.substance == substance)
          && (((((substance == Substance.PM10) || (substance == Substance.PM25)) && (erk.emissionResultType == EmissionResultType.CONCENTRATION)))
              || (erk.emissionResultType == EmissionResultType.DEPOSITION))) {
        return erk;
      }
    }
    return null;
  }

  public static EmissionResultKey valueOf(final Substance substance, final EmissionResultType emissionResultType) {
    for (final EmissionResultKey erk : values()) {
      if ((erk.substance == substance) && (erk.emissionResultType == emissionResultType)) {
        return erk;
      }
    }
    return null;
  }

  /**
   * Always returns a key, adapts the emission result type if it's not available for the substance.
   * @param substance substance
   * @param emissionResultType emission result type
   * @return {@link EmissionResultKey}
   */
  @NotNull
  public static EmissionResultKey safeValueOf(final Substance substance, final EmissionResultType emissionResultType) {
    EmissionResultKey erk = valueOf(substance, emissionResultType);

    if (erk == null) {
      if ((substance == Substance.PM10) || (substance == Substance.PM25)) {
        erk = valueOf(substance, EmissionResultType.CONCENTRATION);
      } else if ((substance == Substance.O3) || (substance == Substance.NO2)) {
        erk = valueOf(substance, EmissionResultType.CONCENTRATION);
      } else {
        erk = valueOf(substance, EmissionResultType.DEPOSITION);
      }
    }
    return erk;
  }

  /**
   * Returns key(s) in a list. Only for NH3 + NOX a list of 2 items will be returned.
   * @return list of key(s)
   */
  public HashSet<EmissionResultKey> hatch() {
    final HashSet<EmissionResultKey> keys = new HashSet<>();

    if (substance == Substance.NOXNH3) {
      keys.add(valueOf(Substance.NOX, emissionResultType));
      keys.add(valueOf(Substance.NH3, emissionResultType));
    } else {
      keys.add(this);
    }
    return keys;
  }

  public Substance getSubstance() {
    return substance;
  }

  public EmissionResultType getEmissionResultType() {
    return emissionResultType;
  }

  @Override
  public String toString() {
    return "EmissionResultKey [" + substance + ", " + this.emissionResultType + "]";
  }
}
