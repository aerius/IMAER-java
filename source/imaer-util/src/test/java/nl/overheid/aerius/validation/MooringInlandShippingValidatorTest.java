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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.overheid.aerius.shared.domain.v2.source.MooringInlandShippingEmissionSource;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.InlandWaterway;
import nl.overheid.aerius.shared.domain.v2.source.shipping.inland.StandardMooringInlandShipping;
import nl.overheid.aerius.shared.exception.AeriusException;
import nl.overheid.aerius.shared.exception.ImaerExceptionReason;

@ExtendWith(MockitoExtension.class)
class MooringInlandShippingValidatorTest {

  private static final String SOURCE_ID = "OurSourceId";

  @Mock InlandShippingValidationHelper validationHelper;

  @Test
  void testValidShipAndWaterwayCode() {
    final MooringInlandShippingEmissionSource source = constructSource();
    final StandardMooringInlandShipping subSource = new StandardMooringInlandShipping();
    final String shipCode = "SomeShipCode";
    mockShipCategory(shipCode);
    subSource.setShipCode(shipCode);
    source.getSubSources().add(subSource);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final MooringInlandShippingValidator validator = new MooringInlandShippingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertTrue(valid, "Valid test case");
    assertTrue(errors.isEmpty(), "No errors");
    assertTrue(warnings.isEmpty(), "No warnings");
  }

  @Test
  void testInvalidShipCode() {
    final MooringInlandShippingEmissionSource source = constructSource();
    final StandardMooringInlandShipping subSource = new StandardMooringInlandShipping();
    final String shipCode = "SomeShipCode";
    final String waterwayCode = "SomeWaterwayCode";
    subSource.setShipCode(shipCode);
    source.getSubSources().add(subSource);
    final InlandWaterway waterway = new InlandWaterway();
    waterway.setWaterwayCode(waterwayCode);

    final List<AeriusException> errors = new ArrayList<>();
    final List<AeriusException> warnings = new ArrayList<>();
    final MooringInlandShippingValidator validator = new MooringInlandShippingValidator(errors, warnings, validationHelper);

    final boolean valid = validator.validate(source);

    assertFalse(valid, "Invalid test case");
    assertEquals(1, errors.size(), "Number of errors");
    assertEquals(ImaerExceptionReason.GML_UNKNOWN_SHIP_CODE, errors.get(0).getReason(), "Error reason");
    assertArrayEquals(new Object[] {
        SOURCE_ID, shipCode
    }, errors.get(0).getArgs(), "Arguments");
    assertTrue(warnings.isEmpty(), "No warnings");
  }

  private MooringInlandShippingEmissionSource constructSource() {
    final MooringInlandShippingEmissionSource source = new MooringInlandShippingEmissionSource();
    source.setGmlId(SOURCE_ID);
    source.setLabel("Source label");
    return source;
  }

  private void mockShipCategory(final String code) {
    when(validationHelper.isValidInlandShipCode(code)).thenReturn(true);
  }

}
