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
package nl.overheid.aerius.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nl.overheid.aerius.shared.domain.result.EmissionResultKey;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLMonitorSubstance;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLRejectionGrounds;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPoint;
import nl.overheid.aerius.shared.domain.v2.point.CalculationPointFeature;
import nl.overheid.aerius.shared.domain.v2.point.CustomCalculationPoint;
import nl.overheid.aerius.shared.domain.v2.point.NSLCalculationPoint;
import nl.overheid.aerius.shared.domain.v2.point.NcaCustomCalculationPoint;
import nl.overheid.aerius.shared.domain.v2.point.ReceptorPoint;
import nl.overheid.aerius.shared.domain.v2.point.SubPoint;
import nl.overheid.aerius.shared.exception.AeriusException;

/**
 *
 */
class CalculationPointDuplicatorTest {

  private static final double X_COORD = 99.2;
  private static final double Y_COORD = 101.5;

  @Test
  void testVisitReceptorPoint() throws AeriusException {
    final ReceptorPoint receptor = new ReceptorPoint();
    setBaseProperties(receptor);
    receptor.setReceptorId(2923);
    receptor.setEdgeEffect(true);

    final CalculationPointFeature original = construct(receptor);
    final CalculationPointDuplicator duplicator = new CalculationPointDuplicator();

    final CalculationPointFeature duplicated = original.accept(duplicator);

    basicAssertions(original, duplicated);
    assertTrue(duplicated.getProperties() instanceof ReceptorPoint);
    final ReceptorPoint duplicatedReceptor = (ReceptorPoint) duplicated.getProperties();
    basePropertiesAssertions(receptor, duplicatedReceptor);
    assertEquals(receptor.getReceptorId(), duplicatedReceptor.getReceptorId(), "receptor ID");
    assertNull(duplicatedReceptor.getEdgeEffect(), "Edge effect is considered result, shouldn't be duplicated");
  }

  @Test
  void testVisitCustomCalculationPoint() throws AeriusException {
    final CustomCalculationPoint customPoint = new CustomCalculationPoint();
    setBaseProperties(customPoint);
    customPoint.setCustomPointId(939);
    customPoint.setHeight(32.2);

    final CalculationPointFeature original = construct(customPoint);
    final CalculationPointDuplicator duplicator = new CalculationPointDuplicator();

    final CalculationPointFeature duplicated = original.accept(duplicator);

    basicAssertions(original, duplicated);
    assertTrue(duplicated.getProperties() instanceof CustomCalculationPoint);
    final CustomCalculationPoint duplicatedCustomPoint = (CustomCalculationPoint) duplicated.getProperties();
    basePropertiesAssertions(customPoint, duplicatedCustomPoint);
    assertEquals(customPoint.getCustomPointId(), duplicatedCustomPoint.getCustomPointId(), "custom point ID");
    assertEquals(customPoint.getHeight(), duplicatedCustomPoint.getHeight(), "height");
  }

  @Test
  void testVisitNcaCustomCalculationPoint() throws AeriusException {
    final NcaCustomCalculationPoint ncaCustomPoint = new NcaCustomCalculationPoint();
    setBaseProperties(ncaCustomPoint);
    ncaCustomPoint.setCustomPointId(4398);
    ncaCustomPoint.setHeight(-4.2);
    ncaCustomPoint.setRoadLocalFractionNO2(0.234);

    final CalculationPointFeature original = construct(ncaCustomPoint);
    final CalculationPointDuplicator duplicator = new CalculationPointDuplicator();

    final CalculationPointFeature duplicated = original.accept(duplicator);

    basicAssertions(original, duplicated);
    assertTrue(duplicated.getProperties() instanceof NcaCustomCalculationPoint);
    final NcaCustomCalculationPoint duplicatedNcaCustomPoint = (NcaCustomCalculationPoint) duplicated.getProperties();
    basePropertiesAssertions(ncaCustomPoint, duplicatedNcaCustomPoint);
    assertEquals(ncaCustomPoint.getCustomPointId(), duplicatedNcaCustomPoint.getCustomPointId(), "custom point ID");
    assertEquals(ncaCustomPoint.getHeight(), duplicatedNcaCustomPoint.getHeight(), "height");
    assertEquals(ncaCustomPoint.getRoadLocalFractionNO2(), duplicatedNcaCustomPoint.getRoadLocalFractionNO2(), "Road local fraction NO2");
  }

  @Test
  void testVisitNSLCalculationPoint() throws AeriusException {
    final NSLCalculationPoint nslPoint = new NSLCalculationPoint();
    setBaseProperties(nslPoint);
    nslPoint.setCustomPointId(6675);
    nslPoint.setHeight(19.84);
    nslPoint.setMonitorSubstance(NSLMonitorSubstance.PM10_ONLY);
    nslPoint.setRejectionGrounds(NSLRejectionGrounds.COMPLIANCE_CRITERION);

    final CalculationPointFeature original = construct(nslPoint);
    final CalculationPointDuplicator duplicator = new CalculationPointDuplicator();

    final CalculationPointFeature duplicated = original.accept(duplicator);

    basicAssertions(original, duplicated);
    assertTrue(duplicated.getProperties() instanceof NSLCalculationPoint);
    final NSLCalculationPoint duplicatedNslPoint = (NSLCalculationPoint) duplicated.getProperties();
    basePropertiesAssertions(nslPoint, duplicatedNslPoint);
    assertEquals(nslPoint.getCustomPointId(), duplicatedNslPoint.getCustomPointId(), "custom point ID");
    assertEquals(nslPoint.getHeight(), duplicatedNslPoint.getHeight(), "height");
    assertEquals(nslPoint.getMonitorSubstance(), duplicatedNslPoint.getMonitorSubstance(), "Monitor substance");
    assertEquals(nslPoint.getRejectionGrounds(), duplicatedNslPoint.getRejectionGrounds(), "Rejection grounds");
  }

  @Test
  void testVisitSubPoint() throws AeriusException {
    final SubPoint subPoint = new SubPoint();
    setBaseProperties(subPoint);
    subPoint.setSubPointId(5);
    subPoint.setReceptorId(2923);
    subPoint.setLevel(2);

    final CalculationPointFeature original = construct(subPoint);
    final CalculationPointDuplicator duplicator = new CalculationPointDuplicator();

    final CalculationPointFeature duplicated = original.accept(duplicator);

    basicAssertions(original, duplicated);
    assertTrue(duplicated.getProperties() instanceof SubPoint);
    final SubPoint duplicatedSubPoint = (SubPoint) duplicated.getProperties();
    basePropertiesAssertions(subPoint, duplicatedSubPoint);
    assertEquals(subPoint.getSubPointId(), duplicatedSubPoint.getSubPointId(), "Sub point ID");
    assertEquals(subPoint.getReceptorId(), duplicatedSubPoint.getReceptorId(), "receptor ID");
    assertEquals(subPoint.getLevel(), duplicatedSubPoint.getLevel(), "level");
  }

  private void basicAssertions(final CalculationPointFeature original, final CalculationPointFeature duplicated) {
    assertNotNull(duplicated, "duplicated feature");
    assertNotSame(original, duplicated, "duplicated feature shouldn't reference same object");
    assertEquals(original.getId(), duplicated.getId(), "feature ID");
    assertNotSame(original.getGeometry(), duplicated.getGeometry(), "duplicated geometry object shouldn't reference same as original");
    assertEquals(X_COORD, duplicated.getGeometry().getX(), "X coordinate in geometry");
    assertEquals(Y_COORD, duplicated.getGeometry().getY(), "Y coordinate in geometry");
    assertNotSame(original.getProperties(), duplicated.getProperties(), "duplicated properties object shouldn't reference same as original");
  }

  private void basePropertiesAssertions(final CalculationPoint original, final CalculationPoint duplicate) {
    assertEquals(original.getDescription(), duplicate.getDescription(), "description");
    assertEquals(original.getGmlId(), duplicate.getGmlId(), "gml ID");
    assertEquals(original.getJurisdictionId(), duplicate.getJurisdictionId(), "jurisdiction ID");
    assertEquals(original.getLabel(), duplicate.getLabel(), "label");
    assertTrue(duplicate.getResults().isEmpty(), "Results shouldn't be duplicated");
  }

  private void setBaseProperties(final CalculationPoint calculationPoint) {
    calculationPoint.setDescription("Our Weird Description");
    calculationPoint.setGmlId("MyLillGmlId");
    calculationPoint.setJurisdictionId(9);
    calculationPoint.setLabel("receptor with a label?");
    calculationPoint.getResults().put(EmissionResultKey.NOXNH3_DEPOSITION, 435.23);
  }

  private CalculationPointFeature construct(final CalculationPoint properties) {
    final CalculationPointFeature feature = new CalculationPointFeature();
    feature.setId("OurId");
    feature.setGeometry(new Point(X_COORD, Y_COORD));
    feature.setProperties(properties);
    return feature;
  }

}
