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
package nl.overheid.aerius.shared.domain.ops;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class OPSSourceCharacteristicsTest {

  /**
   * <p>
   * Gebouwhoogte wordt op het moment niet meegenomen.
   * Mocht er weer een formule geimplementeerd worden, dan kan deze test weer aangepast worden om randzaken te testen.
   * </p>
   */
  @Test
  public void testEffectiveHeightSituationChoice() {
    final OPSSourceCharacteristics characteristics = new OPSSourceCharacteristics();

    // Default values (0)
    characteristics.setBuildingHeight(0);
    characteristics.setEmissionHeight(0);
    assertEquals(0, characteristics.getEffectiveHeight(), 0.001);

    // Default values (0)
    characteristics.setBuildingHeight(6);
    characteristics.setEmissionHeight(12);
    assertEquals(12, characteristics.getEffectiveHeight(), 0.001);

    // Default values (0)
    characteristics.setBuildingHeight(6);
    characteristics.setEmissionHeight(1);
    assertEquals(1, characteristics.getEffectiveHeight(), 0.001);

    // Equal values
    characteristics.setBuildingHeight(100);
    characteristics.setEmissionHeight(100);
    assertEquals(100, characteristics.getEffectiveHeight(), 0.001);

  }

}
