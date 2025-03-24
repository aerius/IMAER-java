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
package nl.overheid.aerius.gml.base.characteristics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;

import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.overheid.aerius.shared.domain.v2.geojson.Geometry;
import nl.overheid.aerius.shared.domain.v2.geojson.LineString;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.geojson.Polygon;

/**
 * Test class for {@link OPSSpreadUtil}.
 */
@ExtendWith(MockitoExtension.class)
class OPSSpreadUtilTest {

  // Note. For this test: SPREAD != HALF_EMISSION_HEIGHT != DEFAULT_SPREAD
  private static final double SPREAD = 3.0;
  private static final double EMISSION_HEIGHT = 50.0;
  private static final double HALF_EMISSION_HEIGHT = EMISSION_HEIGHT / 2.0;
  private static final Double DEFAULT_SPREAD = 10.0;

  private @Mock IsGmlBaseOPSSourceCharacteristics characteristics;

  @ParameterizedTest
  @MethodSource("data")
  void testLegacySpread(final Double spread, final boolean legacySpread, final Geometry geometry, final double expected) {
    final OPSSpreadUtil util = new OPSSpreadUtil(legacySpread);
    lenient().doReturn(EMISSION_HEIGHT).when(characteristics).getEmissionHeight();
    doReturn(spread).when(characteristics).getSpread();
    final Double computedSpread = util.getSpread(characteristics, DEFAULT_SPREAD, geometry);

    assertEquals(expected, computedSpread, 0.0001, "Not the expected spread value");
  }

  private static List<Arguments> data() {
    return List.of(
        // No Legacy, no spread -> spread derived from emission height
        Arguments.of(null, false, new Point(), HALF_EMISSION_HEIGHT),
        Arguments.of(null, false, new LineString(), HALF_EMISSION_HEIGHT),
        Arguments.of(null, false, new Polygon(), HALF_EMISSION_HEIGHT),
        // No legacy but with spread value -> take spread value
        Arguments.of(SPREAD, false, new Point(), SPREAD),
        Arguments.of(SPREAD, false, new LineString(), SPREAD),
        Arguments.of(SPREAD, false, new Polygon(), SPREAD),
        // Legacy, and no spread. For point/line there was no value set in the past
        // and we now need to derive it ourselves from emission height
        Arguments.of(null, true, new Point(), HALF_EMISSION_HEIGHT),
        Arguments.of(null, true, new LineString(), HALF_EMISSION_HEIGHT),
        // Legacy but for polygon, when no spread set it used to take sector default
        Arguments.of(null, true, new Polygon(), DEFAULT_SPREAD),
        // Legacy and spread value set, but for point/line was not supported
        // Therefore we ignore spread and calculate it based on emission height.
        Arguments.of(SPREAD, true, new Point(), HALF_EMISSION_HEIGHT),
        Arguments.of(SPREAD, true, new LineString(), HALF_EMISSION_HEIGHT),
        // Legacy with spread and polygon -> take the given spread
        Arguments.of(SPREAD, true, new Polygon(), SPREAD));
  }
}
