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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Test class for {@link EmissionResultKey}.
 */
public class EmissionResultKeyTest {

  @Test
  public void testHatch() {
    assertEquals(1, EmissionResultKey.NH3_CONCENTRATION.hatch().size(), "Check if hatch works");
    assertEquals(2, EmissionResultKey.NOXNH3_DEPOSITION.hatch().size(), "Check if hatch NH3+NOx deposition: works");
    assertSame(EmissionResultType.DEPOSITION,
        EmissionResultKey.NOXNH3_DEPOSITION.hatch().toArray(new EmissionResultKey[0])[0].getEmissionResultType(),
        "Check if hatch NH3+NOx resultType: works");
  }
}
