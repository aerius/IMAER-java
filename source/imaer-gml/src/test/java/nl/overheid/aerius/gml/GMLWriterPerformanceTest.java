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
package nl.overheid.aerius.gml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.overheid.aerius.gml.base.FeatureMember;
import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.base.TimeUnit;
import nl.overheid.aerius.shared.domain.v2.geojson.LineString;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.domain.v2.source.SRM2RoadEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardVehicles;
import nl.overheid.aerius.shared.domain.v2.source.road.ValuesPerVehicleType;
import nl.overheid.aerius.shared.domain.v2.source.road.Vehicles;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.test.GMLTestDomain;

/**
 * Test class for {@link GMLWriter}. Disabled because these are performance tests.
 */
@Disabled
class GMLWriterPerformanceTest {
  private static final Logger LOG = LoggerFactory.getLogger(GMLWriterPerformanceTest.class);

  @Test
  void testConvertMetaData() throws IOException, AeriusException {
    final InternalGMLWriter writer = new InternalGMLWriter(GMLTestDomain.getExampleGridSettings(), GMLTestDomain.TEST_REFERENCE_GENERATOR,
        Boolean.TRUE, GMLWriter.LATEST_WRITER_VERSION);

    final int numberOfSources = 800000;
    final List<EmissionSourceFeature> sourceFeatures = new ArrayList<>(numberOfSources);
    for (int i = 0; i < numberOfSources; i++) {
      final EmissionSourceFeature sourceFeature = new EmissionSourceFeature();
      sourceFeature.setId(String.valueOf(i));
      final LineString geometry = new LineString();
      geometry.setCoordinates(new double[][] {{100, 200}, {300, 400}, {500, 600}});
      sourceFeature.setGeometry(geometry);
      final SRM2RoadEmissionSource es = new SRM2RoadEmissionSource();
      es.setEmissions(Map.of(Substance.NOX, 123.4, Substance.PM10, 4.321));
      es.setRoadAreaCode("NL");
      es.setRoadTypeCode("SOME_ROAD_CODE");
      final List<Vehicles> traffic = es.getSubSources();
      final StandardVehicles tr = new StandardVehicles();
      final ValuesPerVehicleType valuePerVehicleType = new ValuesPerVehicleType();
      valuePerVehicleType.setStagnationFraction(1.0);
      valuePerVehicleType.setVehiclesPerTimeUnit(200);
      tr.getValuesPerVehicleTypes().put("LIGHT_TRAFFIC", valuePerVehicleType);
      tr.setMaximumSpeed(120);
      tr.setStrictEnforcement(false);
      tr.setTimeUnit(TimeUnit.DAY);

      traffic.add(tr);

      sourceFeature.setProperties(es);
      sourceFeatures.add(sourceFeature);
    }

    final long start = System.currentTimeMillis();
    final List<FeatureMember> result = writer.emissionSourcesToFeatures(sourceFeatures);
    final long end = System.currentTimeMillis();

    LOG.info("Completed in: {}s", (end - start) / 1000);

    assertEquals(numberOfSources, result.size(), "Result size should be 101.");
  }
}
