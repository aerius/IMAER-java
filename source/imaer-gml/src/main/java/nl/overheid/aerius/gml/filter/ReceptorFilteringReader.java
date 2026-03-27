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
package nl.overheid.aerius.gml.filter;

import java.io.Reader;

/**
 * A Reader that filters out XML featureMember blocks containing ReceptorPoint elements.
 * This is used to skip parsing receptor points when results are not needed,
 * significantly reducing memory usage and processing time for large GML files.
 *
 * <p>The filtering matches blocks of the form:
 * <pre>&lt;imaer:featureMember&gt;[whitespace]&lt;imaer:ReceptorPoint...&gt;...&lt;/imaer:featureMember&gt;</pre>
 *
 */
public class ReceptorFilteringReader extends AbstractXMLFilteringReader {

  private static final String TAG_TO_FILTER = "<imaer:featureMember>";
  private static final String CONTENT_REGEX = "[\\n\\r\\s]*<imaer:ReceptorPoint[^>]*>[\\s\\S]*?";

  public ReceptorFilteringReader(final Reader source) {
    super(source, TAG_TO_FILTER, CONTENT_REGEX);
  }
}
