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
package nl.overheid.aerius.shared.domain.v2.building;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import nl.overheid.aerius.shared.domain.ops.OPSLimits;
import nl.overheid.aerius.shared.domain.v2.characteristics.adms.ADMSLimits;

/**
 * Interface for Building limits.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ADMSLimits.class, name = "adms"),
    @JsonSubTypes.Type(value = OPSLimits.class, name = "ops")
})
public interface BuildingLimits extends Serializable {

  default boolean isCircularBuildingSupported() {
    return false;
  }

  default boolean isBuildingUpperLimitWarning() {
    return false;
  }

  int buildingDigitsPrecision();

  double buildingHeightMinimum();

  double buildingHeightMaximum();

  double buildingHeightDefault();

  double buildingWidthMinimum();

  double buildingWidthMaximum();

  double buildingLengthMinimum();

  double buildingLengthMaximum();

  double buildingDiameterMinimum();

  double buildingDiameterMaximum();

  int buildingMaximumPerSituation();
}
