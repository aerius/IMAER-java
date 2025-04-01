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
package nl.overheid.aerius.shared.domain.sector;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import nl.overheid.aerius.shared.domain.HasName;
import nl.overheid.aerius.shared.domain.v2.characteristics.OPSSourceCharacteristics;

/**
 * Data class for sub sectors and the default OPS characteristics.
 *
 */
public class Sector implements HasName, Serializable {

  private static final long serialVersionUID = 4L;

  /**
   * Sector default is the sector in case no specific sector is specified, because it's unknown. Therefore the sector industry generic can be used.
   */
  public static final int DEFAULT_SECTOR_ID = 1800;

  /**
   * Sector for case where no sector is specified yet. UNDEFINED MEANS UNDEFINED
   * so don't change it into a defined sector without modifying the behavior of
   * the application!
   */
  public static final Sector SECTOR_UNDEFINED = new Sector(0, null, "");

  /**
   * Sector default is the sector in case no specific sector is specified, because it's unknown. Therefore the sector industry generic can be used.
   */
  public static final Sector SECTOR_DEFAULT = new Sector(DEFAULT_SECTOR_ID, SectorGroup.INDUSTRY, "");

  private int sectorId;
  private SectorGroup sectorGroup;
  private String description;
  private OPSSourceCharacteristics defaultCharacteristics;

  // Needed for GWT.
  public Sector() {
  }

  public Sector(final int sectorId) {
    this.sectorId = sectorId;
    this.description = String.valueOf(sectorId);
  }

  public Sector(final int sectorId, final SectorGroup sectorGroup, final String description) {
    this.sectorId = sectorId;
    this.sectorGroup = sectorGroup;
    this.description = description;
  }

  public static Sector defaultSector() {
    return new Sector(DEFAULT_SECTOR_ID, SectorGroup.INDUSTRY, "");
  }

  public static Sector undefinedSector() {
    return new Sector(0, null, "");
  }

  public String getDescription() {
    return description;
  }

  /**
   * Returns the default OPSSourceCharacteristics.
   *
   * @return The default OPSSourceCharacteristics
   */
  public OPSSourceCharacteristics getDefaultCharacteristics() {
    if (defaultCharacteristics == null) {
      defaultCharacteristics = new OPSSourceCharacteristics();
    }

    return defaultCharacteristics;
  }

  @Override
  public String getName() {
    return description;
  }

  public int getSectorId() {
    return sectorId;
  }

  public SectorGroup getSectorGroup() {
    return sectorGroup;
  }

  public void setDefaultCharacteristics(final OPSSourceCharacteristics sourceCharacteristics) {
    defaultCharacteristics = sourceCharacteristics;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  @Override
  public void setName(final String name) {
    description = name;
  }

  public void setSectorId(final int sectorId) {
    this.sectorId = sectorId;
  }

  public void setSectorGroup(final SectorGroup sectorGroup) {
    this.sectorGroup = sectorGroup;
  }

  @Override
  public boolean equals(final Object obj) {
    return obj != null && this.getClass() == obj.getClass() ? sectorId == ((Sector) obj).getSectorId() : false;
  }

  @Override
  public int hashCode() {
    // sectorId is unique
    return 31 * sectorId;
  }

  @Override
  public String toString() {
    return "Sector [" + sectorId + ", " + sectorGroup + ":" + description + "]";
  }
}
