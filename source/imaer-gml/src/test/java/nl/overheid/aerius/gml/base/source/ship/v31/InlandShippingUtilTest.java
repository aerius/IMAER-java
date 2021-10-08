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
package nl.overheid.aerius.gml.base.source.ship.v31;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.overheid.aerius.gml.base.GMLHelper;
import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.geojson.LineString;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.InlandWaterway;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

class InlandShippingUtilTest {

  private final GMLHelper helper = mock(GMLHelper.class);;

  private final List<AeriusException> warnings = new ArrayList<>();
  private InlandShippingUtil inlandRouteUtil;
  private final String sourceId = "M0";
  private final InlandWaterway inlandWaterway = mock(InlandWaterway.class);

  @BeforeEach
  public void before() throws AeriusException {
    inlandRouteUtil = new InlandShippingUtil(helper, warnings);
    when(inlandWaterway.getWaterwayCode()).thenReturn("SomeRiver");
  }

  @Test
  void testDetermineInlandWaterWayType() throws AeriusException {
    final Geometry geometry = new LineString();
    final InlandWaterway otherWaterway = mock(InlandWaterway.class);

    when(helper.suggestInlandShippingWaterway(any())).thenReturn(Arrays.asList(inlandWaterway, otherWaterway));

    final InlandWaterway determinedWaterway = inlandRouteUtil.determineInlandWaterWayType(sourceId, geometry);

    assertFalse(warnings.isEmpty(), "Should have warnings ");
    assertTrue(warnings.get(0).getReason() == ImaerExceptionReason.INLAND_SHIPPING_WATERWAY_INCONCLUSIVE,
        "Expect ImaerExceptionReason.INLAND_SHIPPING_WATERWAY_INCONCLUSIVE");
    assertEquals(inlandWaterway, determinedWaterway, "determineInlandWaterWayType should return the first in case of multiple");
  }

  /**
   * Test with Bogus route on the Veluwe where no feasable waterways are to be found to make sure
   * at least one waterway is always found.
   *
   * @throws AeriusException
   */
  @Test
  void testDetermineInlandWaterWayType_NeverNone() throws AeriusException {
    final Geometry geometry = new LineString();
    when(helper.suggestInlandShippingWaterway(any())).thenReturn(Arrays.asList(inlandWaterway));
    final InlandWaterway determinedWaterway = inlandRouteUtil.determineInlandWaterWayType(sourceId, geometry);
    assertNotNull(determinedWaterway, "At least one WaterWayType should be found for any line");
  }

}
