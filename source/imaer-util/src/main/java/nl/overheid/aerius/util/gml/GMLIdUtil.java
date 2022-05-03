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
package nl.overheid.aerius.util.gml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util class for working with GML ids.
 */
public final class GMLIdUtil {

  public static final String SOURCE_PREFIX = "ES";
  public static final String POINT_PREFIX = "CP";
  public static final String MEASURE_PREFIX = "RMA";
  public static final String BUILDING_PREFIX = "Building";
  public static final String DIURNAL_VARIATION_PREFIX = "DV";

  private static final Pattern ID_ALLOWED_PATTERN = Pattern.compile("([\\w\\.])");
  private static final Pattern NOT_STARTING_WITH_NUMBER = Pattern.compile("^[a-zA-Z]");

  private GMLIdUtil() {
    // Util class
  }

  /**
   * @param id The ID to use in the valid gml:id.
   * @param prefix The prefix to use in the resulting prefix.
   * @return The valid GML ID.
   */
  public static String toValidGmlId(final String id, final String prefix) {
    return toValidGmlId(id, prefix, "1");
  }

  /**
   * @param id The ID to use in the valid gml:id.
   * @param prefix The prefix to use in the resulting prefix.
   * @param backupId The ID to use as backup.
   * @return The valid GML ID.
   */
  public static String toValidGmlId(final String id, final String prefix, final String backupId) {
    final String actualId;
    final String sanitizedId = sanitize(id);

    if (sanitizedId == null || sanitizedId.isEmpty()) {
      actualId = prefix + "." + backupId;
    } else if (NOT_STARTING_WITH_NUMBER.matcher(sanitizedId).find()) {
      actualId = sanitizedId;
    } else {
      actualId = prefix + "." + sanitizedId;
    }
    return actualId;
  }

  /**
   * As stated on the interwebs:
   * The practical restrictions of NCName are that it cannot contain several symbol characters like
   * :, @, $, %, &, /, +, ,, ;, whitespace characters or different parenthesis.
   * Furthermore an NCName cannot begin with a number, dot or minus character although they can appear later in an NCName.
   *
   * To make it easier for ourselves we'll be using AERIUS specific prefixes which solves the last bit.
   * For the first bit (certain characters that shouldn't be in the ID)
   * we'll do some sanitizing on the supplied ID by replacing all disallowed characters (and perhaps a few more).
   *
   * @param id The ID to sanitize.
   * @return The sanitized ID.
   */
  private static String sanitize(final String id) {
    if (id == null) {
      return null;
    }
    final Matcher matcher = ID_ALLOWED_PATTERN.matcher(id);
    final StringBuilder result = new StringBuilder();
    while (matcher.find()) {
      result.append(matcher.group());
    }
    return result.toString();
  }

}
