/*
 * Copyright (c) Contributors to the project
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
package nl.overheid.aerius.gml;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.geo.ReceptorGridSettings;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.domain.v2.source.OffRoadMobileEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.offroad.StandardOffRoadMobileSource;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.test.GMLTestDomain;

/**
 * Test class for {@link GMLWriter} off-road mobile source conversion.
 */
class GMLOffRoadWriterTest {

  private static final int XCOORD_1 = GMLTestDomain.XCOORD_1;
  private static final int YCOORD_1 = GMLTestDomain.YCOORD_1;

  @Test
  void testConvertOffRoadUMethodOmitsFuelAndAdBlue() throws IOException, AeriusException {
    final String result = convertOffRoadSource(offRoadSubSource(120, 50, 200, 10));
    assertTrue(result.contains(GMLWriterTest.getExpectedElement("power", "120")), "U method should write power");
    assertFalse(result.contains("<imaer:literFuelPerYear>"), "U method should not write literFuelPerYear");
    assertFalse(result.contains("<imaer:literAdBluePerYear>"), "U method should not write literAdBluePerYear");
  }

  @Test
  void testConvertOffRoadAubMethodOmitsPower() throws IOException, AeriusException {
    final String result = convertOffRoadSource(offRoadSubSource(0, 30, 100, 5));
    assertFalse(result.contains("<imaer:power>"), "AUB method should not write power");
    assertTrue(result.contains(GMLWriterTest.getExpectedElement("literFuelPerYear", "30")), "AUB method should write literFuelPerYear");
    assertTrue(result.contains(GMLWriterTest.getExpectedElement("literAdBluePerYear", "5")), "AUB method should write literAdBluePerYear");
  }

  private String convertOffRoadSource(final StandardOffRoadMobileSource subSource) throws IOException, AeriusException {
    final OffRoadMobileEmissionSource offRoad = new OffRoadMobileEmissionSource();
    offRoad.getSubSources().add(subSource);
    offRoad.getEmissions().put(Substance.NOX, 1.0);
    final EmissionSourceFeature feature = GMLTestDomain.getSource(1, new Point(XCOORD_1, YCOORD_1), "OffRoad", offRoad);
    final GMLWriter builder = new GMLWriter(ReceptorGridSettings.NL, GMLTestDomain.TEST_REFERENCE_GENERATOR);
    return GMLWriterTest.getConversionResult(builder, List.of(feature));
  }

  private StandardOffRoadMobileSource offRoadSubSource(final int power, final int literFuel, final int operatingHours, final int literAdBlue) {
    final StandardOffRoadMobileSource subSource = new StandardOffRoadMobileSource();
    subSource.setOffRoadMobileSourceCode("SI56DSN");
    subSource.setDescription("test");
    subSource.setPower(power);
    subSource.setLiterFuelPerYear(literFuel);
    subSource.setOperatingHoursPerYear(operatingHours);
    subSource.setLiterAdBluePerYear(literAdBlue);
    return subSource;
  }
}
