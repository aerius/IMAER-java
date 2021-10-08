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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test for class {@link Substance}.
 */
public class SubstanceTest {

  @Test
  public void testContains() {
    assertTrue(Substance.NOX.contains(Substance.NOX), "Test if NOx contains NOx");
    assertFalse(Substance.NOX.contains(Substance.NH3), "Test if NOx doesn't contains NH3");
    assertTrue(Substance.NOXNH3.contains(Substance.NOX), "Test if NOXNH3 contains NOx");
    assertTrue(Substance.NOXNH3.contains(Substance.NH3), "Test if NOXNH3 contains NH3");
    assertTrue(Substance.NOXNH3.contains(Substance.NOXNH3), "Test if NOXNH3 contains NOXNH3");
    assertFalse(Substance.NOX.contains(Substance.NOXNH3), "Test if NOX contains NOXNH3");
    assertFalse(Substance.NH3.contains(Substance.NOXNH3), "Test if NH3 contains NOXNH3");
    assertFalse(Substance.NOX.contains(Substance.PM10), "Test if NOXNH3 doesn't contains PM10");
  }

  @Test
  public void testContainsPartly() {
    assertTrue(Substance.NOX.containsCurrentOrGiven(Substance.NOX), "Test if NOx contains NOx");
    assertFalse(Substance.NOX.containsCurrentOrGiven(Substance.NH3), "Test if NOx doesn't contains NH3");
    assertTrue(Substance.NOXNH3.containsCurrentOrGiven(Substance.NOX), "Test if NOXNH3 contains NOx");
    assertTrue(Substance.NOXNH3.containsCurrentOrGiven(Substance.NH3), "Test if NOXNH3 contains NH3");
    assertTrue(Substance.NOXNH3.containsCurrentOrGiven(Substance.NOXNH3), "Test if NOXNH3 contains NOXNH3");
    assertTrue(Substance.NOX.containsCurrentOrGiven(Substance.NOXNH3), "Test if NOX contains NOXNH3");
    assertTrue(Substance.NH3.containsCurrentOrGiven(Substance.NOXNH3), "Test if NH3 contains NOXNH3");
    assertFalse(Substance.NOX.containsCurrentOrGiven(Substance.PM10), "Test if NOX doesn't contains PM10");
  }
}
