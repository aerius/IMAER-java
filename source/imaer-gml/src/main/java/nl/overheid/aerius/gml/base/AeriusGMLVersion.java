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
package nl.overheid.aerius.gml.base;

import java.util.Locale;

/**
 * Enum with available AERIUS GML versions of IMAER.
 */
public enum AeriusGMLVersion {

  /**
   * AERIUS GML version 5.1 (IMAER).
   */
  V5_1(nl.overheid.aerius.gml.v5_1.base.CalculatorSchema.SCHEMA_LOCATION),
  /**
   * AERIUS GML version 5.0 (IMAER).
   */
  V5_0(nl.overheid.aerius.gml.v5_0.base.CalculatorSchema.SCHEMA_LOCATION),
  /**
   * AERIUS GML version 4.0 (IMAER).
   */
  V4_0(nl.overheid.aerius.gml.v4_0.base.CalculatorSchema.SCHEMA_LOCATION),
  /**
   * AERIUS GML version 3.1 (IMAER).
   */
  V3_1(nl.overheid.aerius.gml.v3_1.base.CalculatorSchema.SCHEMA_LOCATION),
  /**
   * AERIUS GML version 3.0 (IMAER).
   */
  V3_0(nl.overheid.aerius.gml.v3_0.base.CalculatorSchema.SCHEMA_LOCATION),
  /**
   * AERIUS GML version 2.2 (IMAER).
   */
  V2_2(nl.overheid.aerius.gml.v2_2.base.CalculatorSchema.SCHEMA_LOCATION),
  /**
   * AERIUS GML version 2.1 (IMAER).
   */
  V2_1(nl.overheid.aerius.gml.v2_1.base.CalculatorSchema.SCHEMA_LOCATION),
  /**
   * AERIUS GML version 2.0 (IMAER).
   */
  V2_0(nl.overheid.aerius.gml.v2_0.base.CalculatorSchema.SCHEMA_LOCATION),
  /**
   * AERIUS GML version 1.1 (IMAER).
   */
  V1_1(nl.overheid.aerius.gml.v1_1.base.CalculatorSchema.SCHEMA_LOCATION),
  /**
   * AERIUS GML version 1.0 (IMAER).
   * @deprecated Old version.
   */
  @Deprecated
  V1_0(nl.overheid.aerius.gml.v1_0.base.CalculatorSchema.SCHEMA_LOCATION),
  /**
   * AERIUS GML version 0.5 (IMAER).
   * @deprecated Old version.
   */
  @Deprecated
  V0_5(nl.overheid.aerius.gml.v0_5.base.CalculatorSchema.SCHEMA_LOCATION);

  private final String schemaLocation;

  private AeriusGMLVersion(final String schemaLocation) {
    this.schemaLocation = schemaLocation;
  }

  /**
   * Safely returns an AeriusGMLVersion. It is case independent and returns null in
   * case the input was null or the AeriusGMLVersion could not be found.
   *
   * @param value value to convert
   * @return AeriusGMLVersion or null if no valid input
   */
  public static AeriusGMLVersion safeValueOf(final String value) {
    try {
      return value == null ? null : valueOf(value.toUpperCase(Locale.ENGLISH));
    } catch (final IllegalArgumentException e) {
      return null;
    }
  }

  String getSchemaLocation() {
    return schemaLocation;
  }

}
