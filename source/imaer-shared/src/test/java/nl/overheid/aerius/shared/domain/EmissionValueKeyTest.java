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
package nl.overheid.aerius.shared.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link EmissionValueKey}.
 */
@DisplayName("EmissionValueKey")
class EmissionValueKeyTest {

  @Nested
  @DisplayName("equals")
  class EqualsTest {

    @Test
    @DisplayName("should return true for identical substances with no year")
    void testEqualsIdenticalSubstancesNoYear() {
      final EmissionValueKey key1 = new EmissionValueKey(Substance.NOX);
      final EmissionValueKey key2 = new EmissionValueKey(Substance.NOX);
      assertEquals(key1, key2, "Keys with same substance and no year should be equal");
    }

    @Test
    @DisplayName("should return true for identical substances with same year")
    void testEqualsIdenticalSubstancesWithYear() {
      final EmissionValueKey key1 = new EmissionValueKey(2020, Substance.NH3);
      final EmissionValueKey key2 = new EmissionValueKey(2020, Substance.NH3);
      assertEquals(key1, key2, "Keys with same substance and year should be equal");
    }

    @Test
    @DisplayName("should return false for different years")
    void testEqualsDifferentYears() {
      final EmissionValueKey key1 = new EmissionValueKey(Substance.NH3);
      final EmissionValueKey key2 = new EmissionValueKey(2020, Substance.NH3);
      assertNotEquals(key1, key2, "Keys with different years should not be equal");
    }

    @Test
    @DisplayName("should return false for different substances")
    void testEqualsDifferentSubstances() {
      final EmissionValueKey key1 = new EmissionValueKey(Substance.NH3);
      final EmissionValueKey key2 = new EmissionValueKey(Substance.NOX);
      assertNotEquals(key1, key2, "Keys with different substances should not be equal");
    }

    @Test
    @DisplayName("should return false for null or empty key")
    void testEqualsNullOrEmpty() {
      final EmissionValueKey key1 = new EmissionValueKey(Substance.NH3);
      final EmissionValueKey key2 = new EmissionValueKey();
      assertNotEquals(key1, key2, "Key should not equal null or empty key");
    }
  }

  @Nested
  @DisplayName("string conversion")
  class StringConversionTest {

    @Test
    @DisplayName("should correctly serialize and deserialize")
    void testValueConversion() {
      final EmissionValueKey original = new EmissionValueKey(2024, Substance.NOX);
      final String serialized = original.toStringValue();
      final EmissionValueKey deserialized = EmissionValueKey.fromStringValue(serialized);

      assertEquals(original, deserialized, "Deserialized key should equal original");
      assertEquals("2024:NOX", serialized, "Serialized string should match expected format");
      assertEquals(2024, deserialized.getYear(), "Year should be preserved");
      assertEquals(Substance.NOX, deserialized.getSubstance(), "Substance should be preserved");
    }

    @Test
    @DisplayName("should handle whitespace")
    void testWhitespaceHandling() {
      final EmissionValueKey key = EmissionValueKey.fromStringValue(" 2024 : NOX ");
      assertEquals(2024, key.getYear(), "Should handle whitespace around year");
      assertEquals(Substance.NOX, key.getSubstance(), "Should handle whitespace around substance");
    }

    @Test
    @DisplayName("should throw exception for null input")
    void testNullInput() {
      assertThrows(IllegalArgumentException.class, () -> EmissionValueKey.fromStringValue(null),
          "Should throw exception for null input");
    }

    @Test
    @DisplayName("should throw exception for empty input")
    void testEmptyInput() {
      assertThrows(IllegalArgumentException.class, () -> EmissionValueKey.fromStringValue(""),
          "Should throw exception for empty input");
    }

    @Test
    @DisplayName("should throw exception for whitespace-only input")
    void testWhitespaceOnlyInput() {
      assertThrows(IllegalArgumentException.class, () -> EmissionValueKey.fromStringValue("   "),
          "Should throw exception for whitespace-only input");
    }

    @Test
    @DisplayName("should throw exception for empty year")
    void testEmptyYear() {
      final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
          () -> EmissionValueKey.fromStringValue(":NOX"),
          "Should throw exception for empty year");
      assertEquals("Year and substance cannot be empty, got: ':NOX'", exception.getMessage(),
          "Should have correct error message for empty year");
    }

    @Test
    @DisplayName("should throw exception for empty substance")
    void testEmptySubstance() {
      final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
          () -> EmissionValueKey.fromStringValue("2024:"),
          "Should throw exception for empty substance");
      assertEquals("Year and substance cannot be empty, got: '2024:'", exception.getMessage(),
          "Should have correct error message for empty substance");
    }

    @Test
    @DisplayName("should throw exception for invalid format")
    void testInvalidFormat() {
      assertThrows(IllegalArgumentException.class, () -> EmissionValueKey.fromStringValue("invalid:format"),
          "Should throw exception for invalid format");
    }

    @Test
    @DisplayName("should throw exception for missing separator")
    void testMissingSeparator() {
      assertThrows(IllegalArgumentException.class, () -> EmissionValueKey.fromStringValue("2024NOX"),
          "Should throw exception for missing separator");
    }

    @Test
    @DisplayName("should throw exception for multiple separators")
    void testMultipleSeparators() {
      assertThrows(IllegalArgumentException.class, () -> EmissionValueKey.fromStringValue("2024:NOX:extra"),
          "Should throw exception for multiple separators");
    }

    @Test
    @DisplayName("should throw exception for invalid year")
    void testInvalidYear() {
      assertThrows(NumberFormatException.class, () -> EmissionValueKey.fromStringValue("not-a-number:NOX"),
          "Should throw exception for invalid year");
    }

    @Test
    @DisplayName("should throw exception for invalid substance")
    void testInvalidSubstance() {
      assertThrows(IllegalArgumentException.class, () -> EmissionValueKey.fromStringValue("2024:INVALID_SUBSTANCE"),
          "Should throw exception for invalid substance");
    }
  }

  @Nested
  @DisplayName("toString")
  class ToStringTest {

    @Test
    @DisplayName("should return debug format")
    void testToString() {
      final EmissionValueKey key = new EmissionValueKey(2024, Substance.NOX);
      assertEquals("EmissionValueKey [year=2024, substance=NOX]", key.toString(),
          "toString should match expected debug format");
    }

    @Test
    @DisplayName("should handle no year case")
    void testToStringNoYear() {
      final EmissionValueKey key = new EmissionValueKey(Substance.NOX);
      assertEquals("EmissionValueKey [year=0, substance=NOX]", key.toString(),
          "toString should handle no year case");
    }
  }
}
