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
package nl.overheid.aerius.shared;

import java.util.Date;

/**
 * Global constants used in the IM AERIUS.
 */
public class ImaerConstants {

  /**
   * Conversion from centimeter to meter.
   */
  public static final int CM_TO_M = 100;

  /**
   * Conversion from inch to centimeters.
   */
  public static final double INCH_TO_CM = 2.54;

  /**
   * Conversion from gram to kilogram.
   */
  public static final int GRAM_TO_KILOGRAM = 1000;

  /**
   * Conversion from.
   */
  public static final int MICROGRAM_TO_GRAM = 1000 * 1000;

  /**
   * Conversion from gram/meter to kilogram/kilometer.
   */
  public static final int GRAM_PER_KM_TO_KG_PER_METER = 1000 * 1000;

  /**
   * Conversion from kilogram/meter to kilogram/kilometer.
   */
  public static final double KG_PER_KM_TO_KG_PER_METER = 1000;

  /**
   * Conversion from meter to kilometer.
   */
  public static final int M_TO_KM = 1000;

  /**
   * Conversion from kilogram to kiloton.
   */
  public static final int KILO_TO_TON = 1000;

  /**
   * Conversion ratio of m2 to hectare.
   */
  public static final double M2_TO_HA = 100 * 100;

  /**
   * The amount of days in a year AERIUS uses by default.
   */
  public static final int DAYS_PER_YEAR = 365;

  /**
   * Number of months in a year.
   */
  public static final int MONTHS_PER_YEAR = 12;

  /**
   * Number of hours in a year.
   */
  public static final int HOURS_PER_YEAR = DAYS_PER_YEAR * 24;

  /**
   * Number of seconds in a year.
   */
  public static final int SECONDS_PER_YEAR = HOURS_PER_YEAR * 60 * 60;

  /**
   * Conversion from percentage to fraction.
   */
  public static final int PERCENTAGE_TO_FRACTION = 100;

  private static final int YEAR_OFFSET = 1900;

  protected ImaerConstants() {
    // Constants class
  }

  /**
   * Returns current year.
   *
   * Must use Date's deprecated .getYear() method because Calendar is not supported in GWT and GWT's SimpleDateFormat equivalent is not supported
   * in GWT.
   */
  public static int getCurrentYear() {
    return YEAR_OFFSET + new Date().getYear();
  }
}
