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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import nl.overheid.aerius.util.OptionsMetadataUtil.Option;

/**
 * Base class for Options Metadata Util classes with generic methods.
 */
class OptionsMetadataUtilBase {

  protected static final String LIST_OPTION_SEPARATOR = ",";
  protected static final String OPTION_KEY_SPLIT = "-";

  protected OptionsMetadataUtilBase() {
    // Util class
  }

  static double getOrDefault(final Map<Option, String> map, final Option option, final double defaultValue) {
    return Optional.ofNullable(map.get(option)).map(Double::parseDouble).orElse(defaultValue);
  }

  static boolean isOrDefault(final Map<Option, String> map, final Option option, final boolean defaultValue) {
    return Optional.ofNullable(map.get(option)).map(Boolean::parseBoolean).orElse(defaultValue);
  }

  static void parseListOption(final Option key, final Map<Option, String> map, final Consumer<List<String>> setter) {
    final String value = map.get(key);
    if (value != null && !value.isBlank()) {
      final String[] splitOption = value.split(LIST_OPTION_SEPARATOR);
      if (splitOption.length > 0) {
        setter.accept(Arrays.asList(splitOption));
      }
    }
  }

  static void addValue(final Map<String, String> mapToAddTo, final Option key, final Object value, final boolean addDefaults) {
    addValue(mapToAddTo, key.toKey(), value, addDefaults);
  }

  static void addValue(final Map<String, String> mapToAddTo, final String key, final Object value, final boolean addDefaults) {
    // false should be the default value, so only add if value is true, or if defaults should be added anyway.
    if (value != null) {
      mapToAddTo.put(key, value.toString());
    } else if (addDefaults) {
      mapToAddTo.put(key, "");
    }
  }

  static void addIntValue(final Map<String, String> mapToAddTo, final Option key, final int value, final boolean addDefaults) {
    addIntValue(mapToAddTo, key, value, addDefaults, 0);
  }

  static void addIntValue(final Map<String, String> mapToAddTo, final Option key, final int value, final boolean addDefaults,
      final int defaultValue) {
    if (addDefaults || value != defaultValue) {
      mapToAddTo.put(key.toKey(), String.valueOf(value));
    }
  }

  static void addBooleanValue(final Map<String, String> mapToAddTo, final Option key, final boolean value, final boolean addDefaults) {
    // false should be the default value, so only add if value is true, or if defaults should be added anyway.
    if (addDefaults || value) {
      mapToAddTo.put(key.toKey(), String.valueOf(value));
    }
  }
}
