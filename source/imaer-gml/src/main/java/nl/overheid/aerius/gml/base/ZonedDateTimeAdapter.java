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

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 */
public class ZonedDateTimeAdapter extends XmlAdapter<String, ZonedDateTime> {

  private final DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;

  @Override
  public ZonedDateTime unmarshal(final String v) throws Exception {
    return ZonedDateTime.parse(v, formatter);
  }

  @Override
  public String marshal(final ZonedDateTime v) throws Exception {
    return formatter.format(v);
  }

}
