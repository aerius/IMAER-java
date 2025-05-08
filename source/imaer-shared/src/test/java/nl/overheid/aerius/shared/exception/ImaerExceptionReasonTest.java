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
package nl.overheid.aerius.shared.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ImaerExceptionReason}
 */
class ImaerExceptionReasonTest {

  @Test
  void testFromErrorCode() {
    assertEquals(ImaerExceptionReason.INTERNAL_ERROR, ImaerExceptionReason.fromErrorCode(666), "Specific from error code");
    for (final ImaerExceptionReason reason : ImaerExceptionReason.values()) {
      assertEquals(reason, ImaerExceptionReason.fromErrorCode(reason.getErrorCode()), "Roundtrip with fromErrorCode");
    }
  }

  @Test
  void testReasonCodeUniqueAndSorted() {
    ImaerExceptionReason lastReason = null;
    int lastReasonCode = 0;
    for (final ImaerExceptionReason reason : ImaerExceptionReason.values()) {
      assertTrue(reason.getErrorCode() > lastReasonCode, "Reason code not unique or sorted properly in file: " + lastReason + ", " + reason);
      lastReason = reason;
      lastReasonCode = reason.getErrorCode();
    }
  }

}
