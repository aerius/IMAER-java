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
import java.util.Map;

import nl.overheid.aerius.shared.domain.Substance;
import nl.overheid.aerius.shared.domain.v2.geojson.Point;

/**
 * A project in archive.
 */
public class ArchiveProject implements Serializable {

  private static final long serialVersionUID = 4L;

  private String id;
  private String name;
  private String type;
  private String permitReference;
  private String planningReference;
  private Map<Substance, Double> netEmissions;
  private String aeriusVersion;
  private Point centroid;

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public String getPermitReference() {
    return permitReference;
  }

  public void setPermitReference(final String permitReference) {
    this.permitReference = permitReference;
  }

  public String getPlanningReference() {
    return planningReference;
  }

  public void setPlanningReference(final String planningReference) {
    this.planningReference = planningReference;
  }

  public Map<Substance, Double> getNetEmissions() {
    return netEmissions;
  }

  public void setNetEmissions(final Map<Substance, Double> netEmissions) {
    this.netEmissions = netEmissions;
  }

  public String getAeriusVersion() {
    return aeriusVersion;
  }

  public void setAeriusVersion(final String aeriusVersion) {
    this.aeriusVersion = aeriusVersion;
  }

  public Point getCentroid() {
    return centroid;
  }

  public void setCentroid(final Point centroid) {
    this.centroid = centroid;
  }

}
