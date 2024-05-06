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

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A Theme determines the context in which the data will be used.
 */
public enum Theme {
  /**
   * Centraal Instrument Monitoring Luchtkwaliteit (CIMLK).
   */
  CIMLK,
  /**
   * UK Nature Conservation Act calculation (NCA).
   */
  NCA,
  /**
   * Dutch Omgevingswet Natura 2000 stikstof.
   */
  OWN2000,
  /**
   * Dutch Regeling Beoordeling Luchtkwaliteit (RBL).
   * @deprecated Replaced by CIMLK
   */
  @Deprecated
  RBL,
  /**
   * Dutch Wet NatuurBescherming (WNB)
   * @deprecated Replaced with OWN2000
   */
  @Deprecated
  WNB;

  public String getKey() {
    return name().toLowerCase(Locale.ROOT);
  }

  public static Optional<Theme> fromKey(final String key) {
    return Stream.of(values())
        .filter(v -> v.name().equalsIgnoreCase(key))
        .findFirst();
  }
}
