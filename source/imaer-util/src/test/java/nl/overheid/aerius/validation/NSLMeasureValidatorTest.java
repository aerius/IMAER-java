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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.base.EmissionReduction;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLMeasure;
import nl.overheid.aerius.shared.domain.v2.nsl.NSLMeasureFeature;
import nl.overheid.aerius.shared.domain.v2.source.road.StandardVehicleMeasure;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

/**
 *
 */
class NSLMeasureValidatorTest {

  private static final String SOURCE_ID = "OurSourceId";
  private static final String MEASURE_LABEL = "Some kind of measure";

  @Test
  void testValidMeasure() {
    final NSLMeasureFeature measureFeature = new NSLMeasureFeature();

    final NSLMeasure measure = new NSLMeasure();
    measure.setGmlId(SOURCE_ID);
    measure.setLabel(MEASURE_LABEL);
    final StandardVehicleMeasure standardMeasure = new StandardVehicleMeasure();
    final EmissionReduction reduction1 = new EmissionReduction();
    reduction1.setFactor(0.4);
    reduction1.setSubstance(Substance.NOX);
    final EmissionReduction reduction2 = new EmissionReduction();
    reduction2.setFactor(1.1);
    reduction2.setSubstance(Substance.NH3);
    standardMeasure.addEmissionReduction(reduction1);
    standardMeasure.addEmissionReduction(reduction2);
    measure.getVehicleMeasures().add(standardMeasure);
    measureFeature.setProperties(measure);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();

    NSLMeasureValidator.validateMeasures(List.of(measureFeature), errors, warnings);

    assertTrue(errors.isEmpty(), "No errors");
    assertTrue(warnings.isEmpty(), "No warnings");
  }

  @Test
  void testNegativeFactor() {
    final NSLMeasureFeature measureFeature = new NSLMeasureFeature();

    final NSLMeasure measure = new NSLMeasure();
    measure.setGmlId(SOURCE_ID);
    measure.setLabel(MEASURE_LABEL);
    final StandardVehicleMeasure standardMeasure = new StandardVehicleMeasure();
    final EmissionReduction reduction1 = new EmissionReduction();
    reduction1.setFactor(-0.4);
    reduction1.setSubstance(Substance.NOX);
    final EmissionReduction reduction2 = new EmissionReduction();
    reduction2.setFactor(1.1);
    reduction2.setSubstance(Substance.NH3);
    standardMeasure.addEmissionReduction(reduction1);
    standardMeasure.addEmissionReduction(reduction2);
    measure.getVehicleMeasures().add(standardMeasure);
    measureFeature.setProperties(measure);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();

    NSLMeasureValidator.validateMeasures(List.of(measureFeature), errors, warnings);

    assertEquals(1, errors.size(), "Number of errors");
    assertTrue(warnings.isEmpty(), "No warnings");
    assertEquals(ImaerExceptionReason.UNEXPECTED_NEGATIVE_VALUE, errors.get(0).getReason(), "Error reason");
    assertArrayEquals(new Object[] {MEASURE_LABEL, String.valueOf(-0.4)}, errors.get(0).getArgs(), "Arguments");
  }

}
