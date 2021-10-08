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
package nl.overheid.aerius.gml;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public final class JAXBContextCache {
  private static final Map<String, JAXBContext> cache = new HashMap<>();

  private JAXBContextCache() {
  }

  @SuppressWarnings("rawtypes")
  public synchronized static JAXBContext find(final Class... classes) throws JAXBException {
    final String str = Stream.of(classes)
        .map(Class::getCanonicalName)
        .collect(Collectors.joining("-"));

    if (cache.containsKey(str)) {
      return cache.get(str);
    }

    final JAXBContext inst = JAXBContext.newInstance(classes);
    cache.put(str, inst);
    return inst;
  }
}
