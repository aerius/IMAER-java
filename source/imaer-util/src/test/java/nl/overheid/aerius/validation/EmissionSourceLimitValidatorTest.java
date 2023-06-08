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
package nl.overheid.aerius.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import nl.overheid.aerius.shared.domain.v2.geojson.LineString;
import nl.overheid.aerius.shared.domain.v2.geojson.Polygon;
import nl.overheid.aerius.shared.domain.v2.source.EmissionSourceFeature;
import nl.overheid.aerius.shared.domain.v2.source.GenericEmissionSource;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;
import nl.overheid.aerius.shared.geometry.EmissionSourceLimits;

/**
 * Test class for {@link EmissionSourceLimitValidator}.
 */
class EmissionSourceLimitValidatorTest {

  @Test
  void testAllOke() throws AeriusException {
    final EmissionSourceLimits limits = new EmissionSourceLimits();
    limits.setMaxSources(20);
    limits.setMaxLineLength(1000);
    limits.setMaxPolygonSurface(1000);

    final List<EmissionSourceFeature> esl = new ArrayList<>();
    final EmissionSourceFeature es1 = new EmissionSourceFeature();
    final Polygon polygon = new Polygon();
    polygon.setCoordinates(new double[][][] {{{1.0, 0.0}, {2.0, 0.0}, {2.0, 100.0}, {1.0, 100.0}, {1.0, 0.0}}});
    es1.setGeometry(polygon);
    esl.add(es1);
    final EmissionSourceFeature es2 = new EmissionSourceFeature();
    esl.add(es2);
    final LineString lineString = new LineString();
    lineString.setCoordinates(new double[][] {{1.0, 0.0}, {1.0, 100.0}});
    es2.setGeometry(lineString);

    EmissionSourceLimitValidator.check(esl.size(), limits);
    Assertions.assertIterableEquals(List.of(), EmissionSourceLimitValidator.checkGeometries(esl, limits), "Should not contain geometry exceptions");
  }

  @Test
  void testCheckSize() throws AeriusException {
    final EmissionSourceLimits limits = new EmissionSourceLimits();
    limits.setMaxSources(2);
    final AeriusException e = assertThrows(AeriusException.class, () -> EmissionSourceLimitValidator.check(3, limits));
    assertEquals("3", e.getArgs()[1], "Number of sources counted");
    assertEquals(ImaerExceptionReason.LIMIT_SOURCES_EXCEEDED, e.getReason(), "MaxSources");
  }

  @Test
  void testCheckLineLimit() throws AeriusException {
    final EmissionSourceLimits limits = new EmissionSourceLimits();
    limits.setMaxLineLength(100);
    final List<EmissionSourceFeature> esl = new ArrayList<>();
    final EmissionSourceFeature feature = new EmissionSourceFeature();
    final GenericEmissionSource source = new GenericEmissionSource();
    source.setLabel("SomeLabel");
    feature.setProperties(source);
    esl.add(feature);
    final LineString lineString = new LineString();
    lineString.setCoordinates(new double[][] {{1.0, 0.0}, {1.0, 101.0}});
    feature.setGeometry(lineString);
    final List<AeriusException> exceptions = EmissionSourceLimitValidator.checkGeometries(esl, limits);
    assertEquals(1, exceptions.size(), "Should contain 1 exception");
    final AeriusException e = exceptions.get(0);
    assertEquals(ImaerExceptionReason.LIMIT_LINE_LENGTH_EXCEEDED, e.getReason(), "MaxLineLength");
    assertEquals("SomeLabel", e.getArgs()[0], "Number of sources counted");
  }

  @Test
  void testPolygonSurface() throws AeriusException {
    final EmissionSourceLimits limits = new EmissionSourceLimits();
    limits.setMaxPolygonSurface(100);
    final List<EmissionSourceFeature> esl = new ArrayList<>();
    final EmissionSourceFeature feature = new EmissionSourceFeature();
    final GenericEmissionSource source = new GenericEmissionSource();
    source.setLabel("SomeLabel");
    feature.setProperties(source);
    final Polygon polygon = new Polygon();
    polygon.setCoordinates(new double[][][] {{{1.0, 0.0}, {2.0, 0.0}, {2.0, 10000000.0}, {1.0, 10000000.0}, {1.0, 0.0}}});
    feature.setGeometry(polygon);
    esl.add(feature);
    final List<AeriusException> exceptions = EmissionSourceLimitValidator.checkGeometries(esl, limits);
    assertEquals(1, exceptions.size(), "Should contain 1 exception");
    assertEquals(ImaerExceptionReason.LIMIT_POLYGON_SURFACE_EXCEEDED, exceptions.get(0).getReason(), "MaxPolygonSurface");
  }
}
