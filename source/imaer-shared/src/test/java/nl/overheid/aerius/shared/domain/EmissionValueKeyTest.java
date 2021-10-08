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
 * Test for {@link EmissionValueKey}.
 */
public class EmissionValueKeyTest {

  @Test
  public void testEquals() {
    assertTrue(new EmissionValueKey(Substance.NOX).equals(new EmissionValueKey(Substance.NOX)), "Equals for NOx.");
    assertTrue(new EmissionValueKey(Substance.NH3).equals(new EmissionValueKey(Substance.NH3)), "Equals for NH3.");
    assertTrue(new EmissionValueKey(Substance.PM10).equals(new EmissionValueKey(Substance.PM10)), "Equals for PM10.");
    assertFalse(new EmissionValueKey(Substance.NH3).equals(new EmissionValueKey(2020, Substance.NH3)), "No equal year");
    assertFalse(new EmissionValueKey(Substance.NH3).equals(new EmissionValueKey(Substance.NOX)), "No equal substance");
    assertFalse(new EmissionValueKey(Substance.NH3).equals(new EmissionValueKey()), "Nothing equal");
  }
}
