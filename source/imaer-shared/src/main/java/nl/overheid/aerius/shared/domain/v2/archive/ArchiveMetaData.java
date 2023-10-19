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
package nl.overheid.aerius.shared.domain.v2.archive;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Metadata information related to Archive.
 */
public class ArchiveMetaData implements Serializable {

  private static final long serialVersionUID = 1L;

  private ZonedDateTime retrievalDateTime;
  private List<ArchiveProject> archiveProjects = new ArrayList<>();

  public ZonedDateTime getRetrievalDateTime() {
    return retrievalDateTime;
  }

  public void setRetrievalDateTime(final ZonedDateTime retrievalDateTime) {
    this.retrievalDateTime = retrievalDateTime;
  }

  public List<ArchiveProject> getArchiveProjects() {
    return archiveProjects;
  }

  public void setArchiveProjects(final List<ArchiveProject> archiveProjects) {
    this.archiveProjects = archiveProjects;
  }

}
