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
package nl.overheid.aerius.shared.domain.v2.source.farm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Deprecated Replaced by Animal Housing approach
 */
@Deprecated(forRemoval = true)
public class StandardFarmLodging extends FarmLodging {

  private static final long serialVersionUID = 1L;

  private String farmLodgingCode;
  private String systemDefinitionCode;
  private List<LodgingFodderMeasure> fodderMeasures = new ArrayList<>();
  private List<AdditionalLodgingSystem> additionalLodgingSystems = new ArrayList<>();
  private List<ReductiveLodgingSystem> reductiveLodgingSystems = new ArrayList<>();

  public String getFarmLodgingCode() {
    return farmLodgingCode;
  }

  public void setFarmLodgingCode(final String farmLodgingCode) {
    this.farmLodgingCode = farmLodgingCode;
  }

  public String getSystemDefinitionCode() {
    return systemDefinitionCode;
  }

  public void setSystemDefinitionCode(final String systemDefinitionCode) {
    this.systemDefinitionCode = systemDefinitionCode;
  }

  public List<LodgingFodderMeasure> getFodderMeasures() {
    return fodderMeasures;
  }

  public void setFodderMeasures(final List<LodgingFodderMeasure> fodderMeasures) {
    this.fodderMeasures = fodderMeasures;
  }

  public List<AdditionalLodgingSystem> getAdditionalLodgingSystems() {
    return additionalLodgingSystems;
  }

  public void setAdditionalLodgingSystems(final List<AdditionalLodgingSystem> additionalLodgingSystems) {
    this.additionalLodgingSystems = additionalLodgingSystems;
  }

  public List<ReductiveLodgingSystem> getReductiveLodgingSystems() {
    return reductiveLodgingSystems;
  }

  public void setReductiveLodgingSystems(final List<ReductiveLodgingSystem> reductiveLodgingSystems) {
    this.reductiveLodgingSystems = reductiveLodgingSystems;
  }

}
