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
package nl.overheid.aerius.gml.base.characteristics;

import nl.overheid.aerius.gml.base.IsGmlReferenceType;
import nl.overheid.aerius.shared.domain.v2.characteristics.adms.BuoyancyType;
import nl.overheid.aerius.shared.domain.v2.characteristics.adms.EffluxType;
import nl.overheid.aerius.shared.domain.v2.characteristics.adms.SourceType;

public interface IsGmlADMSSourceCharacteristics extends IsGmlSourceCharacteristics, HasGmlBuildingReference {

  @Override
  IsGmlReferenceType getBuilding();

  double getHeight();

  double getSpecificHeatCapacity();

  SourceType getSourceType();
  Double getDiameter();
  Double getElevationAngle();
  Double getHorizontalAngle();
  Double getWidth();
  Double getVerticalDimension();
  BuoyancyType getBuoyancyType();
  Double getDensity();
  Double getTemperature();
  EffluxType getEffluxType();
  Double getVerticalVelocity();
  Double getVolumetricFlowRate();
  Double getMassFlux();

}
