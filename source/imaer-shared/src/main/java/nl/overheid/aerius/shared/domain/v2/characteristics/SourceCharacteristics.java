/*
 * Copyright the State of the Netherlands
 * Crown copyright
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
package nl.overheid.aerius.shared.domain.v2.characteristics;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(property = "type", use = Id.NAME)
@JsonSubTypes({
  @Type(value = ADMSSourceCharacteristics.class, name = CharacteristicsType.Names.ADMS),
  @Type(value = OPSSourceCharacteristics.class, name = CharacteristicsType.Names.OPS),
})
public abstract class SourceCharacteristics implements Serializable {

  private static final long serialVersionUID = 2L;

  private String buildingId;
  private String customDiurnalVariationId;

  public <E extends SourceCharacteristics> E copyTo(final E copy) {
    copy.setBuildingId(buildingId);
    copy.setCustomDiurnalVariationId(customDiurnalVariationId);
    return copy;
  }

  public String getBuildingId() {
    return buildingId;
  }

  public void setBuildingId(final String buildingId) {
    this.buildingId = buildingId;
  }

  public String getCustomDiurnalVariationId() {
    return customDiurnalVariationId;
  }

  public void setCustomDiurnalVariationId(final String customDiurnalVariationId) {
    this.customDiurnalVariationId = customDiurnalVariationId;
  }

}
